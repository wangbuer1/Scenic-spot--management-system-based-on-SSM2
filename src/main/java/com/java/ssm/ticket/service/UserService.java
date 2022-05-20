package com.java.ssm.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.ssm.ticket.dao.UserDao;
import com.java.ssm.ticket.model.User;


/**
 * 用户服务业务层   Component 标明这是一个Service
 */
@Component 
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	
	/**
	 * 注册用户
	 * @param user
	 * @return true 新插入用户成功
	 */
	public Boolean registerOneUserInfo(User user) {
		return userDao.saveUser(user) > 0  ? true : false;
	}
	/**
	 * 查询用户是否被注册
	 * @param idcard
	 * @return false 身份证已经被注册
	 */
	public Boolean checkUserIdCard(String idcard) {
		return userDao.findUserByIdCard(idcard) != null ? false : true;
	}
	
	/**
	 * 检查用户账号和密码是否正确
	 * @param user
	 * @return true 表示正确
	 */
	public Boolean checkUserPassword(User user) {
		//通过身份证查询用户
		User u =userDao.findUserByIdCard(user.getIdcard());
		//如果表单的密码和数据库通过身份证id查询带密码一致就表明账号和密码正确
		if(user.getPassword().equals(u.getPassword())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 通过身份证查询用户
	 * @param idcard
	 * @return 用户
	 */
	public User getUserByIdCard(String idcard){
		return userDao.findUserByIdCard(idcard);
	}
	
	
	public Boolean updateUser(User user) {
		return (userDao.UpdateUser(user)>0);
	}
	
	public User getUserByUid(String id){
		return userDao.findUserById(Long.valueOf(id));
	}
	
	public List<User> getAllUsers(){
		return userDao.allUsers();
	}
	
	public boolean delUserByUid(String uid) {
		return (userDao.delOneUser(Long.valueOf(uid)) > 0);
	}
}
