package com.pollra.http.users.service;

import com.pollra.http.constants.YesOrNo;
import com.pollra.http.users.domain.UsersVO;
import com.pollra.http.users.domain.constants.LoginStatus;
import com.pollra.http.users.domain.constants.LogoutStatus;
import com.pollra.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class SignService {

    private static final Logger log = LoggerFactory.getLogger(SignService.class);

    private UserRepository userRepository;

    public SignService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 로그인 하는 메소드
     *
     * @param request
     * @return
     */
    public LoginStatus loginAction(Map<String, Object> param, HttpServletRequest request) throws Exception{
        log.info("loginAction start");
        String insertId = "";
        String insertPw = "";
        // 데이터가 존재하는지 체크
        try {
            insertId = param.get("id").toString();
            insertPw = param.get("pw").toString();
            log.info("insertId: "+insertId +"/insertPw"+insertPw);
        } catch (NullPointerException e) {
            log.info("[!]"+e.getMessage());
            return LoginStatus.DATA_DOES_NOT_EXIST;     // 데이터가 입력되지않음
        }
        HttpSession session = request.getSession();
        // 로그인 되어있는지 체크
        if (!session.getAttribute("loginUser").equals("")) {
            log.info("[!] 이미 로그인되어있는 세션입니다.");
            return LoginStatus.ALREADY_SIGNED_IN;   // 이미 로그인이 되어있음
        }
        UsersVO usersVO = userRepository.selectOneUsersVOToId(insertId);
        // 해당 아이디가 존재하는지 체크
        if (usersVO == null) {
            log.info("[!] 아이디("+insertId+")가 존재하지 않습니다.");
            return LoginStatus.NO_ID_EXISTS;  // 해당 아이디가 존재하지 않음
        }
        // 해당 아이디와 패스워드가 일치하는지 체크
        if (!usersVO.getPw().equals(insertPw)) {
            log.info("[!] 비밀번호("+insertPw+")가 일치하지 않습니다.");
            return LoginStatus.PASSWORDS_DO_NOT_MATCH;  // 비밀번호가 틀렸음
        }
        // 로그인 시도
        if (usersVO.getPw().equals(insertPw)) {
            // 해당 유저의 index 를 세션에 저장
            log.info("create session");
            session.setAttribute("loginUser", usersVO.getId());
            log.info("login success user id: "+usersVO.getId());
            return LoginStatus.LOGIN_SUCCEED;  // 로그인 성공
        } else {
            log.info("[!] 예상치 못한 오류");
            return LoginStatus.UNEXPECTED_ERROR; // 예상치 못한 오류
        }
    }

    /**
     * 로그아웃 하는 메소드
     * @param request
     * @return
     */
    public LogoutStatus logoutAction(HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();
        String loginUser;
        try {
            loginUser = session.getAttribute("loginUser").toString().trim();
        }catch(NullPointerException e){
            log.info("[!]"+e.getMessage());
            return LogoutStatus.LOGOUT_FAILED;  // 로그인되어있지 않음
        }

        session.setAttribute("loginUser", "");
        return LogoutStatus.LOGOUT_OK;
    }

    /**
     * 로그인 상태 체크 메서드
     * @return
     */
    public String loginCheck(HttpServletRequest request){
        String loginUser = "";
        try {
            loginUser = request.getSession().getAttribute("loginUser").toString();
        }catch (NullPointerException e){
            request.getSession().setAttribute("loginUser", "");
            loginUser = request.getSession().getAttribute("loginUser").toString();
        }
        if(loginUser.equals("")){
            log.info("[!] 로그인정보가 존재하지 않습니다.");
            return "guest";
        }else{
            log.info("로그인 정보가 존재합니다.");
            return loginUser;
        }
    }


}