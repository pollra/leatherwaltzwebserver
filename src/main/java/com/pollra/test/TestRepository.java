package com.pollra.test;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository {

    @Select("SELECT pollra_userinfo.user_nik FROM pollra_userinfo WHERE user_num = #{userNum}")
    public String getUserNumToUserNik(int userNum);
}
