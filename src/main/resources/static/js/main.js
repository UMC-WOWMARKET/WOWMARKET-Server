function checkEmail() {
    // 카카오 API로 사용자 정보를 가져오는 요청 보냄
    fetch('/wowmarket/users/login/kakao/check_email', {
        method: 'POST',
        credentials: 'same-origin', // 쿠키를 포함한 인증 정보 전달
    }).then(response => response.json())
      .then(data => {
          if (data.hasOwnProperty('email') && data.email !== '') {
              // 이메일 정보가 존재할 때, 로그인 성공 상태로 화면을 업데이트
              document.getElementById('loginContent').innerHTML = '<h1>로그인 성공</h1>';
          } else {
              // 이메일 정보가 없을 때, 다시 로그인 페이지로 리다이렉트
              window.location.href = 'http://localhost:8080';
          }
      }).catch(error => {
          console.error('네트워크 에러', error);
      });
}

// 초기 화면 로딩 시, 이메일 정보 확인하여 화면 업데이트
window.onload = function() {
    if (userId !== null) {
        // userId가 존재할 때, 사용자 정보 확인 함수 호출
        checkEmail();
    }
};