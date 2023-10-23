window.addEventListener("DOMContentLoaded", function() {
    const editComments = document.getElementsByClassName("edit_comment");
    for (const el of editComments) {
        el.addEventListener("click", function() {
            const id = this.dataset.id;
            const url = `/board/comment/ajax/${id}`;
            const t1 = document.getElementById(`comment_content_${id}`);
            const t2 = document.getElementById(`edit_box_${id}`);
            const t3 = t2.querySelector(".edit_comment");
            if (t3) { // 에디터가 열린 상태인 경우는 저장
                const content = t3.value.trim();

                const formData = new FormData();
                formData.append("id", id);
                formData.append("content", content);

                ajaxLoad(`/board/comment/ajax_update`, "POST", formData)
                    .then((res) => {
                        t1.innerHTML = content;
                        t2.innerHTML = "";
                        t1.classList.remove("dn");
                    })
                    .catch((err) => console.error(err));

                return;
            }
            // 에디터가 열려 있지 않은 경우는 에디터 생성 및 불러오기
            t2.innerHTML = "";
            ajaxLoad(url, "GET")
                .then((res) => {
                    t1.classList.remove("dn");
                    t1.classList.add("dn");
                    const textarea = document.createElement("textarea");
                    textarea.className="edit_comment";
                    textarea.value = res;
                    t2.appendChild(textarea);
                })
                .catch((err) => console.error(err));

        });
    }
});