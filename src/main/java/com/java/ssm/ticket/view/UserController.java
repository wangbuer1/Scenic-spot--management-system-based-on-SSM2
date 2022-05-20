package com.java.ssm.ticket.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.java.ssm.ticket.model.User;
import com.java.ssm.ticket.service.UserService;
import com.java.ssm.ticket.utils.DateUtil;

/**
 * 用户请求处理器
 * 
 * @author hgy
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	/**
	 * session保存登录用户的key
	 */
	public static final String USER_LOGIN_KEY = "loginUserInfo";
	
	@Autowired
	HttpServletResponse resp;

	@Autowired
	HttpServletRequest req;

	@Autowired
	UserService us;

	@PostMapping("/reg")
	public String register(User user, String captcha, Model md) {
		// 检查表单是否为空 长度是否为6位和8位

		// 开关
		boolean flag = true;
		if (user.getUsername() == null) {
			md.addAttribute("usermsg", "请填写你的名字!");
			flag = false;
		}
		if (user.getIdcard() == null || user.getIdcard().getBytes().length < 18) {
			md.addAttribute("idmsg", "身份证号错误!");
			flag = false;
		}
		if (user.getPassword() == null || user.getPassword().getBytes().length < 8) {
			md.addAttribute("pwdmsg", "密码长度是为8位!");
			flag = false;
		}

		if (captcha == null || captcha.getBytes().length < 4) {
			md.addAttribute("codemsg", "验证码错误!");
			flag = false;
		}

		// 如果表单验证不成功则转发到register页面
		// return !flag ? "forward:/register" : toReg(user,md);
		if (flag) {
			// 检查身份证是否被注册
			if (!us.checkUserIdCard(user.getIdcard())) {
				md.addAttribute("msg", "身份证号:" + user.getIdcard() + "已经被注册了!换一个试试~");
				flag = false;
			} else {// 如果没有被注册去执行注册
				user.setCreateTime(DateUtil.asDateToTimestamp());//设置用户注册的时间 
				if (us.registerOneUserInfo(user)) {// 是否为注册成功
					md.addAttribute("msg", "身份证号:" + user.getIdcard() + "注册成功!请去登录!");
					return "forward:/register";
				} else {// 没有注册成功
					md.addAttribute("msg", "注册失败!请去稍后重试!");
				}
			}

		}
		return "forward:/register";
	}

	// public String toReg(User user, Model ui) {
	// if (us.registerOneUserInfo(user)) {
	// ui.addAttribute("msg", "身份证号:" + user.getIdcard() + "注册成功!请去登录!");
	// return "forward:/register";
	// }
	// // ui.addAttribute("success-msg","注册失败!请去稍后重试!");
	// return "forward:/register";
	// }

	@PostMapping("/login")
	public String login(User user, String captcha, Model md) {
		// 检查表单是否为空 长度是否为6位和8位
		// 表单验证开关
		boolean flag = true;
		if (user.getIdcard() == null || user.getIdcard().getBytes().length == 0) {
			md.addAttribute("idmsg", "请检查你的身份证账号!");
			flag = false;
		}
		if (user.getPassword() == null || user.getPassword().getBytes().length == 0) {
			md.addAttribute("pwdmsg", "请检查你的密码!");
			flag = false;

		}
		// 从session里面取Kaptcha里面生成的值并且强转成String
		String cpaText = (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		// 检查验证码位数 和 是否和session中的一致 不一致则错误
		if (captcha == null || captcha.getBytes().length < 4 || !cpaText.equals(captcha)) {
			md.addAttribute("codemsg", "验证码错误!");
			flag = false;
		}
		if (flag) {// 只有flag 是true的时候表面form args验证通过
			// 通过UserService检查用户输入的密码
			if (us.checkUserPassword(user)) {
				SessionLoginUser(user);
				return "redirect:/index.html";
			} else {
				md.addAttribute("msg", "身份证号码或者密码错误!或者你身份证未注册~");
				return "forward:/login";
			}
		}
		// 如果执行到这里来说明用户表单数据没有验证通过
		return "forward:/login";
	}
	
	
	
	@GetMapping("/logout")
	public String logout() {
		//移除保存在session中的用户信息
		req.getSession().removeAttribute(USER_LOGIN_KEY);
		return "redirect:/index.html";
	}
	
	//登录普通用户
	public void SessionLoginUser(User user) {
		//user 是表单的user 我们不能直接使用 所以使用getUserByIdCard查询一下在使用
		User u = us.getUserByIdCard(user.getIdcard());
		//将登录的用户保存在session中方便鉴权使用
		req.getSession().setAttribute(USER_LOGIN_KEY, u);
	}
	
	
	
}
