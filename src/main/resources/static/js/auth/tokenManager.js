function parseJwt(token) {
    try {
        const base64Payload = token.split('.')[1];
        const payload = atob(base64Payload);
        return JSON.parse(payload);
    } catch (e) {
        return null;
    }
}

// 토큰 남은 시간 계산 (초 단위)
function getTokenRemainingTime() {
    const token = localStorage.getItem("accessToken");
    if (!token) return 0;

    const payload = parseJwt(token);
    if (!payload || !payload.exp) return 0;

    const now = Math.floor(Date.now() / 1000);
    const remaining = payload.exp - now;

    return remaining > 0 ? remaining : 0;
}

// 페이지 로드 시 남은 시간 확인 및 처리
function checkTokenAndRedirect() {
    const remaining = getTokenRemainingTime();

    if (remaining <= 0) {
        // 토큰 만료 → 로그아웃 처리
        localStorage.removeItem("accessToken");
        alert("세션이 만료되었습니다. 다시 로그인하세요.");
        window.location.href = "/index.html";
    } else {
        console.log("토큰 남은 시간:", remaining, "초");
        $('#message').text(remaining);
    }
}

// 보호된 API 호출 시 헤더 자동 추가
function authAjax(options) {
    const token = localStorage.getItem("accessToken");
    if (!token) {
        alert("로그인이 필요합니다.");
        window.location.href = "/index.html";
        return;
    }

    if (!options.headers) options.headers = {};
    options.headers["Authorization"] = "Bearer " + token;

    return $.ajax(options);
}

$(document).ready(function () {
    checkTokenAndRedirect();
});