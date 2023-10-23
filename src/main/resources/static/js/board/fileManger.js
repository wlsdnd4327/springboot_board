/**
* 파일 업로드 ,삭제
*/

const fileManager = {
    upload(files, imageOnly) {
        if (!files || files.length == 0) {
            throw new Error("업로드할 파일을 선택하세요.");
        }

        const formData = new FormData();
        if (imageOnly) { // 이미지 형식의 파일인 경우
            for (const file of files) {
                if (file.type.indexOf("image") == -1) {
                    throw new Error("이미지 형식의 파일만 업로드해 주세요.");
                }
            }
        }

        const gidEl = document.querySelector("input[name='gid']");
        if (!gidEl || !gidEl.value.trim()) {
            throw new Error("잘못된 접근입니다.");
        }

        /** gid 처리 */
        formData.append("gId", gidEl.value.trim());
        formData.append("location", imageOnly?"editor":"attach");

        for (const file of files) {
            formData.append("files", file);
        }

        ajaxLoad("/file/upload", "POST", formData, "json")
        .then((res) => {
            if (typeof fileUploadCallback == 'function') {
                fileUploadCallback(res.data);
            }
        })
        .catch((err) => {
            console.error(err);
        });
    },
    /**
    * 이미지 본문 추가
    *
    */
    insertEditor(e) {
        const url = e.currentTarget.dataset.url;
        if (!url || !CKEDITOR) return;

        CKEDITOR.instances.content.insertHtml(`<img src='${url}'>`);
    }
};

/**
* 파일 업로드 콜백 처리
*
*/
function fileUploadCallback(files) {
    if (files.length == 0) {
        return;
    }
    const tplEditor = document.getElementById("tpl_editor").innerHTML;
    const tpl = document.getElementById("tpl").innerHTML;
    const attachFilesEl = document.querySelector(".attachFiles.files");
    const attachImageFilesEl = document.querySelector(".attachFiles.image");

    const domParser = new DOMParser();
    let editorHtml = "";
    for(const file of files) {
       let html = "", isEditor = false;
       if (file.location == 'editor') { // 에디터 첨부 파일
           editorHtml += `<img src='${file.fileUrl}'>`;
           html = tplEditor;
           isEditor = true;
       } else {
           html = tpl;
       }
       const targetEl = isEditor ? attachImageFilesEl : attachFilesEl;

       html = html.replace(/#\[id\]/g, file.id)
                  .replace(/#\[fileName\]/g, file.fileName)
                  .replace(/#\[fileUrl\]/g, file.fileUrl);

       const dom = domParser.parseFromString(html, "text/html");
       const span = dom.querySelector(".file_box");
       targetEl.appendChild(span);
        const insertImageEl = span.querySelector(".insert_editor");
       if (insertImageEl) {
           insertImageEl.addEventListener("click", fileManager.insertEditor);
       }
    }

    if (editorHtml) {
        CKEDITOR.instances.content.insertHtml(editorHtml);
    }
}

/**
* 파일 삭제 콜백 처리
*
* @param fileId : 파일 번호
*/
function fileDeleteCallback(fileId) {
   if (!fileId) return;

   const fileEl = document.getElementById(`file_${fileId}`);
   if (fileEl) fileEl.parentElement.removeChild(fileEl);
}

window.addEventListener("DOMContentLoaded", function() {
    const uploadFiles = document.getElementsByClassName("uploadFiles");
    const fileEl = document.getElementById("file");
    if (fileEl) {
        for (const el of uploadFiles) {
            el.addEventListener("click", function() {
                fileEl.value = "";
                if (this.classList.contains("image")) {
                    fileEl.imageOnly = true;
                } else {
                    fileEl.imageOnly = false;
                }

                fileEl.click();
            });
        }

        fileEl.addEventListener("change", function(e) {
            const imageOnly = this.imageOnly || false;
            const files = e.target.files;
            try {
                fileManager.upload(files, imageOnly);
            } catch (err) {
                alert(err.message);
                console.error(err);
            }
        });
    } // endif

    /** 이미지 본문 추가 처리 S */
    const insertEditorEls = document.getElementsByClassName("insert_editor");
    for (const el of insertEditorEls) {
        el.addEventListener("click", fileManager.insertEditor);
    }
    /** 이미지 본문 추가 처리 E */
});