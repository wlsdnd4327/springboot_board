/**
* ajaxLoad
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
