/**
* ajaxLoad
* @param method : 요청 메서드
* @param url : 요청 URL
* @param responseType : json - 응답 결과를 json 변환, 아닌 경우는 문자열 반환
*/
function ajaxLoad(url, method, formData, responseType) {
    method = method || "GET";
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    const csrfToken = document.querySelector("meta[name='_csrf']").content;

    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.setRequestHeader(csrfHeader, csrfToken);
        xhr.send(formData);
        xhr.responseType=responseType;
        xhr.onreadystatechange = function() {
            if (xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
                resolve(xhr.response);
            }
        };

        xhr.onerror = function(err) {
            reject(err);
        };

        xhr.onabort = function(err) {
            reject(err);
        };

        xhr.ontimeout = function(err) {
            reject(err);
        };
    });
}


/** 공통 이벤트 처리 */
window.addEventListener("DOMContentLoaded", function(){
    // 삭제 확인 메시지
    const withdrawals = document.getElementsByClassName("withdrawal");
    for( const del of withdrawals){
        del.addEventListener("click",function(){
            if(confirm("정말로 탈퇴하시겠습니까?")){

            }
        });
    }
});
