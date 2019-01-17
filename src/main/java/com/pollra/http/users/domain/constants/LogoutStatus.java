package com.pollra.http.users.domain.constants;

public enum LogoutStatus {
    LOGOUT_FAILED(0),
    LOGOUT_OK(1);

    private final int value;

    LogoutStatus(int value) {
        this.value = value;
    }

}
