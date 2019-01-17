package com.pollra.http.users.domain.constants;

public enum LoginStatus {

    LOGIN_SUCCEED(1),           // 로그인 성공
    ALREADY_SIGNED_IN(0),       // 이미 로그인 되어있음
    NO_ID_EXISTS(-1),           // 아이디가 존재하지 않음
    PASSWORDS_DO_NOT_MATCH(-2), // 비밀번호가 일치하지 않음
    DATA_DOES_NOT_EXIST(-3),    // 데이터가 입력되지 않음
    UNEXPECTED_ERROR(-4);       // 예상치 못한 오류

    private final int value;

    LoginStatus(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
