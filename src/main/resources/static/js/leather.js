// 로그인 체크
var loginCheck = function(){
    $.ajax({
        url:"/member/check",
        type:"GET"
    }).done(function (result) {
        console.log("result: "+ result);
        $("#loginStatusId").text(result);
    });
};

$(document).ready(
    loginCheck()
);
// 로그인 시도
var loginAction = function(){
    console.log("loginAction start");
    const idRegex = /^[0-9a-z]+$/;

    $.ajax({
        url:"/member/login",
        data: JSON.stringify({"id":$(".loginId").val(),"pw":$(".loginPw").val()}),
        type: "post",
        dataType: "json",
        contentType: 'application/json'
    }).done(function (result) {
        switch (result) {
            case -1:
        //        아이디가 존재하지 않음
                DoesNotMatch("존재하지 않는 아이디 입니다.");
                $(".loginId").css('border','2px solid red');
                $(".loginPw").css('border','');
                $(".loginId").effect("shake",{distance:10}, 200);   // last num is speed
                break;
            case -2:    // 비밀번호 틀림
                DoesNotMatch("비밀번호가 일치하지 않습니다.");
                $(".loginPw").css('border','2px solid red');
                $(".loginId").css('border','');
                $(".loginPw").effect("shake",{distance:10}, 200);
                break;
            case 1:
                loginCheck();
                $("#loginStatus").attr('onclick','');
                document.getElementById('loginContainer').style.display = 'none';
                console.log(result);
                break;
            default:

        }
    })
};

// 로그아웃
var logoutAction = function(){
    console.log("logoutAction start");
    $.ajax({
        url:"/member/logout",
        type:"GET"
    }).done(function(result){
        console.log('result: ' + result);
        if(result ===1){
            // 로그아웃 성공
            $('#loginStatus').attr('onclick', "loginModalOpenAndClose('block')");
            loginCheck();
            DoesNotMatch("로그아웃 되었습니다.",'#4CAF50');
        }else{
            // 로그아웃 시도 실패
            loginCheck();
            DoesNotMatch("로그아웃 실패.");
        }
    });
};
/*===== 로그인 박스*/
$(document).ready(function () {
    // Get the modal
    var modal = document.getElementById('loginContainer');
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
});
var loginModalOpenAndClose = function (action) {
    $(".loginId").css('border','');
    $(".loginPw").css('border','');
    document.getElementById('loginContainer').style.display=action;
};

/*=====*/

// 알림 박스
function DoesNotMatch(msg, color='#f44336') {
    $("#DoesNotMatch").text(msg);
    $("#DoesNotMatch").css('background-color',color);
    var x = document.getElementById("DoesNotMatch");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}