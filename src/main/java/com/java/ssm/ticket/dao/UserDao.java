package com.java.ssm.ticket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.java.ssm.ticket.model.User;

/**
 * 用户数据访问
 * @author hgy
 */
@Mapper
public interface UserDao {
	
	/**
	 * 通过id查询用户
	 * @param uid
	 * @return User
	 */
	@Select("SELECT * FROM `ticket`.`user_table` WHERE uid = #{uid}")
	User findUserById(long uid);
	
	
	@Select("SELECT * FROM `ticket`.`user_table`")
	List<User> allUsers();
	
	/**
	 * 保存用户 useGeneratedKeys设置uid自增
	 * @param user
	 * @return int 1代表插入成功
	 */
	@Options(useGeneratedKeys = true, keyProperty = "uid")
	@Insert("INSERT INTO `ticket`.`user_table`(username,password,createTime,idcard) VALUES (#{username},#{password},#{createTime},#{idcard})")
	int saveUser(User user);
	
	
	@Update("UPDATE `ticket`.`user_table` SET username = #{username},password = #{password},createTime = #{createTime},idcard = #{idcard} WHERE uid = #{uid}")
	int UpdateUser(User u);
	
	/**
	 * 通过idcard 身份证号查询 @Param绑定查询参数
	 * @param idcard
	 * @return user
	 */
	@Select("SELECT * FROM `ticket`.`user_table` WHERE idcard = #{idcard}")
	User findUserByIdCard(@Param(value = "idcard") String idcard);
	
	@Delete("DELETE FROM `ticket`.`user_table` WHERE uid = #{uid}")
	int delOneUser(long uid);
	
}