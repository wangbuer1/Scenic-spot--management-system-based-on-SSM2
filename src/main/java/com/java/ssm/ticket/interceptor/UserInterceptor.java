package com.java.ssm.ticket.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.java.ssm.ticket.view.UserController;
/**
 * 用户拦截器
 */
public class UserInterceptor implements HandlerInterceptor {

	/**
	 * 检查用户是否登录拦截器
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		//如果session里面有用户说明登录成功
		if (session.getAttribute(UserController.USER_LOGIN_KEY) != null) {
			// 登录成功不拦截
			return true;
		} else {
			// 登录失败
			// 拦截后进入登录页面
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path;
			response.sendRedirect(basePath + "/login");
			return false;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
