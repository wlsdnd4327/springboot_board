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