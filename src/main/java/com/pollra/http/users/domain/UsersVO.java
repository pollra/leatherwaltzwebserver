package com.pollra.http.users.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class UsersVO {
    private int index;
    private String id;
    private String pw;
    private String email;
}
