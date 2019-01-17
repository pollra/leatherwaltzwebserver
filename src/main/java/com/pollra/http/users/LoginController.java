package com.pollra.http.users;

import com.pollra.http.constants.YesOrNo;
import com.pollra.http.users.domain.constants.LoginStatus;
import com.pollra.http.users.domain.constants.LogoutStatus;
import com.pollra.http.users.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private SignService signService;

    public LoginController(SignService loginService) {
        this.signService = loginService;
    }

    // 로그인 처리하는 컨트롤러
    @PostMapping("login")
    public ResponseEntity<Integer> setLoginAction(@RequestBody Map<String, Object> param, HttpServletRequest request){
        LoginStatus loginStatus = null;
        try {
            loginStatus = signService.loginAction(param, request);
        } catch (Exception e) {
            log.info("[!]"+e.getMessage());
            return new ResponseEntity<>(-99, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        switch (loginStatus){
            case LOGIN_SUCCEED:
                return new ResponseEntity<>(loginStatus.getValue(), HttpStatus.OK);

            case ALREADY_SIGNED_IN:
                return new ResponseEntity<>(loginStatus.getValue(), HttpStatus.BAD_REQUEST);

            case NO_ID_EXISTS:
                return new ResponseEntity<>(loginStatus.getValue(), HttpStatus.OK);

            case PASSWORDS_DO_NOT_MATCH:
                return new ResponseEntity<>(loginStatus.getValue(), HttpStatus.OK);

            case DATA_DOES_NOT_EXIST:
                return new ResponseEntity<>(loginStatus.getValue(), HttpStatus.BAD_REQUEST);

            default:
                return new ResponseEntity<>(loginStatus.getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 로그아웃 처리하는 컨트롤러
    @GetMapping("logout")
    public ResponseEntity<Integer> setLogoutAction(HttpServletRequest request){
        LogoutStatus logoutStatus = null;
        try {
            logoutStatus = signService.logoutAction(request);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        switch (logoutStatus){
            case LOGOUT_OK:
                return new ResponseEntity<>(1, HttpStatus.OK);
            case LOGOUT_FAILED:
                return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 로그인 되어있는지 확인하는 메소드
    @GetMapping("check")
    public ResponseEntity<String> getLoginStatus(HttpServletRequest request){
        String result = signService.loginCheck(request);
        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }
}
