package com.pollra.persistence;

import com.pollra.http.users.domain.UsersVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    /**
     * UsersVO 를 받아서 데이터를 입력한다.
     * @param usersVO
     * @return
     */
    @Insert("INSERT INTO public.users (index, id, pw, email) VALUES (DEFAULT, #{id}, #{pw}, #{email})")
    public int insertOneUsersVO(UsersVO usersVO);

    /**
     * index 를 받아서 그에 맞는 인덱스를 리턴한다.
     * @param index
     * @return
     */
    @Select("SELECT * FROM portfolio.users WHERE index = #{index}")
    public UsersVO selectOneUsersVOToIndex(int index);

    /**
     * id 를 받아서 UsersVO 를 리턴한다.
     * @param id
     * @return
     */
    @Select("SELECT * FROM portfolio.users WHERE id = #{id}")
    public UsersVO selectOneUsersVOToId(String id);

    /**
     * index를 받아서 게시물 하나를 삭제
     * @param index
     * @return
     */
    @Delete("DELETE FROM portfolio.users WHERE index = #{index}")
    public int delectOneUserToIndex(int index);

    /**
     * index 와 id 를 받아서 index값을 가지고있는 데이터의 id 를 입력받은 데이터로 변경한다.
     * @param index
     * @param id
     * @return
     */
    @Update("UPDATE portfolio.users SET id = #{id} WHERE index = #{index}")
    public int updateOneUsersVOToIdAndTarget(int index, String id);
}
