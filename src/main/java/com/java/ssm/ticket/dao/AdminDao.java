package com.java.ssm.ticket.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.java.ssm.ticket.model.Admin;

/**
 * 管理员数据层
 */
@Mapper
public interface AdminDao {
	
	/**
	 * 查询管理员
	 * @param id
	 * @return 管理员
	 */
	@Select("SELECT * FROM `admin_table` WHERE id = #{id}")
	Admin findAdminById(Integer id);
	
	/**
	 * 查询管理员
	 * @param username
	 * @return 管理员
	 */
	@Select("SELECT * FROM `admin_table` WHERE username = #{username}")
	Admin findAdminByUsername(String username);
}	
