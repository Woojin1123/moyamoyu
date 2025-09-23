$(document).ready(function() {
    $('#login-form').submit(function (e) {
        e.preventDefault();
        
        const email = $('input[name="userEmail"]').val();
        const password = $('input[name="userPassword"]').val();

        $.ajax({
            type: "POST",
            url: "/api/auth/login",
            contentType: "application/json",
            data: JSON.stringify({
                email: email,
                password: password
            }),
            success: function (response) {
                $('#message').text(response.message);
                localStorage.setItem("accessToken",response.data)
                loadMainPage();
            },
            error: function(xhr) {
                let errorMsg = '로그인 실패!';
                if(xhr.responseJSON && xhr.responseJSON.message){
                    errorMsg = xhr.responseJSON.message; // 서버 실패 메시지
                }
                $('input[name="userPassword"]').val('');
                $('#message').text(errorMsg);
            }
            });
        });
    });
function loadMainPage() {
    $.ajax({
        type: "GET",
        url: "/main", // main.html을 반환하는 API
        headers: { "Authorization": "Bearer " + localStorage.getItem("accessToken") },
        success: function(html) {
            $('body').html(html); // 간단히 바디에 넣는 예시
        },
        error: function() {
            alert("인증 필요, 로그인 페이지로 이동");
            window.location.href = "/index.html";
        }
    });
}