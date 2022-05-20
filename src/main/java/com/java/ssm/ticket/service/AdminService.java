package com.java.ssm.ticket.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.ssm.ticket.dao.AdminDao;
import com.java.ssm.ticket.model.Admin;

/**
 * admin服务层
 */
@Component
public class AdminService {

	@Autowired
	AdminDao ad;

	@Autowired
	HttpServletRequest request;

	/**
	 * 登录管理员
	 * 
	 * @param admin
	 * @return true账号和密码正确
	 */
	public Boolean checkAdmin(Admin admin) {
		// 通过管理员账号查询用户
		Admin ainfo = ad.findAdminByUsername(admin.getUsername());
		// 如果表单的密码和数据库通过管理员账号查询带密码一致就表明账号和密码正确
		if (admin.getPassword().equals(ainfo.getPassword())) {
			request.getSession().setAttribute("LOGIN_ADMIN", ainfo);
			return true;
		}
		return false;
	}
	
}
