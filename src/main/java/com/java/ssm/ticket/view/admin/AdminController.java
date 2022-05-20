package com.java.ssm.ticket.view.admin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.code.kaptcha.Constants;
import com.java.ssm.ticket.model.Admin;
import com.java.ssm.ticket.model.Scenic;
import com.java.ssm.ticket.model.User;
import com.java.ssm.ticket.service.AdminService;
import com.java.ssm.ticket.service.OrderService;
import com.java.ssm.ticket.service.ScenicService;
import com.java.ssm.ticket.service.UserService;
import com.java.ssm.ticket.utils.DateUtil;

/**
 * 管理员控制器
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	//文件存储地址
	public static final String  PIC_URL= "http://localhost:8080/ticket-ssm/static/pic_upload/";
	
	@Autowired
	AdminService as;

	@Autowired
	UserService us;

	@Autowired
	OrderService os;

	@Autowired
	ScenicService ss;

	@Autowired
	HttpServletRequest req;
	// 这些都是返回jsp视图
	@GetMapping("/login")
	public String admin() {
		return "admin/login";
	}

	@GetMapping("/main")
	public String main() {
		return "admin/main";
	}

	@GetMapping("/userList")
	public String userList(Model ui) {
		ui.addAttribute("us", us.getAllUsers());
		return "admin/userList";
	}

	@GetMapping("/orderList")
	public String orderList(Model ui) {
		ui.addAttribute("os", os.allOrders());
		return "admin/orderList";
	}

	@GetMapping("/scenicList")
	public String scenicList(Model ui) {
		ui.addAttribute("ss", ss.getAll());
		return "admin/scenicList";
	}

	@GetMapping("/addScenic")
	public String addScenic() {
		return "admin/addScenic";
	}
	//返回编辑用户页面
	@GetMapping("/editUser")
	public String editUser(String uid, Model ui) {
		/*
		 * if(uid == null || uid.isBlank()) { return "redirect:/admin/userList"; }
		 */
		ui.addAttribute("uid", uid);
		return "admin/editUser";
	}
	//用户退出登录 从Session Remove的用户
	@GetMapping("/logout")
	public String logout() {
		req.getSession().removeAttribute("LOGIN_ADMIN");
		return "redirect:/admin/login";
	}
	//编辑用户请求
	@ResponseBody
	@PostMapping("/editUserform")
	public Map<String, Object> userForm(String uid, String username, String password) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (username ==null || password == null || uid == null) {
			rs.put("code", "500");
			rs.put("msg", "参数错误!");
			return rs;
		}
		User user = us.getUserByUid(uid);
		user.setUsername(username);
		user.setPassword(password);
		if (us.updateUser(user)) {
			rs.put("code", 200);
			rs.put("msg", "成功~");
			return rs;
		}
		rs.put("code", 500);
		rs.put("msg", "服务器错误!");
		return rs;
	}
	//删除用户请求
	@ResponseBody
	@PostMapping("/delUserform")
	public Map<String, Object> delUserForm(String uid) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if ( uid == null) {
			rs.put("code", "500");
			rs.put("msg", "参数错误!");
			return rs;
		}

		if (us.delUserByUid(uid)) {
			rs.put("code", 200);
			rs.put("msg", "成功~");
			return rs;
		}
		rs.put("code", 500);
		rs.put("msg", "服务器错误!");
		return rs;
	}
	//删除景点
	@ResponseBody
	@PostMapping("/delScenicform")
	public Map<String, Object> delScenicform(String sid) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (sid == null) {
			rs.put("code", "500");
			rs.put("msg", "参数错误!");
			return rs;
		}

		if (ss.deleteScenicById(sid)) {
			rs.put("code", 200);
			rs.put("msg", "成功~");
			return rs;
		}
		rs.put("code", 500);
		rs.put("msg", "服务器错误!");
		return rs;
	}
	//删除订单
	@ResponseBody
	@PostMapping("/delOrderform")
	public Map<String, Object> delOrderForm(String oid) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (oid == null) {
			rs.put("code", "500");
			rs.put("msg", "参数错误!");
			return rs;
		}

		if (os.delOneOrder(oid)) {
			rs.put("code", 200);
			rs.put("msg", "成功~");
			return rs;
		}
		rs.put("code", 500);
		rs.put("msg", "服务器错误!");
		return rs;
	}
	//登录表单验证
	@ResponseBody
	@PostMapping("/form")
	public Map<String, Object> form(String code, Admin admin) {
		Map<String, Object> rs = new HashMap<String, Object>();
		// 从session里面取Kaptcha里面生成的值并且强转成String
		String cpaText = (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (!code.equals(cpaText)) {
			rs.put("code", "500");
			rs.put("msg", "验证码错误!");
			return rs;
		}
		if (as.checkAdmin(admin)) {
			rs.put("code", 200);
			rs.put("msg", "登录成功~");
			return rs;
		}
		rs.put("code", 500);
		rs.put("msg", "账号或者密码错误!");
		return rs;
	}
	//添加景点请求
	@ResponseBody
	@PostMapping("/addScform")
	public Map<String, Object> addScform(String sc_name,String sc_address, String sc_desc,
			String sc_price,String sc_pic) {
		Map<String, Object> rs = new HashMap<String, Object>();
		System.out.println(sc_name + sc_address + sc_desc + sc_name + sc_price);
		if (sc_name == null || sc_address ==null || sc_desc==null || sc_name==null || sc_price==null) {
			rs.put("code", "500");
			rs.put("msg", "验证码错误!");
			return rs;
		}
		Scenic sc = new Scenic();
		sc.setScenicName(sc_name);
		sc.setCreateTime(DateUtil.asDateToTimestamp());
		sc.setScenicAddress(sc_address);
		sc.setScenicDetails(sc_desc);
		sc.setScenicPrice(new BigDecimal(sc_price));
		sc.setScenicPic(PIC_URL+sc_pic);
		sc.setScenicStar(0);
		if (ss.saveScenic(sc)) {
			rs.put("code", 200);
			rs.put("msg", "成功~");
			return rs;
		}
		rs.put("code", 500);
		rs.put("msg", "服务器错误!");
		return rs;
	}
	//景点图片上传接口
	@ResponseBody
	@PostMapping("/pic_upload")
	public Map<String, Object> pic_upload(@RequestParam MultipartFile file) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (file == null) {
			rs.put("code", 500);
			rs.put("msg", "上传图片失败！");
			return rs;
		}
		String fileName = null;
		String uploadRootPath = null;
		try {
			// 获取目录/创建路径
			uploadRootPath = req.getServletContext().getRealPath("static/pic_upload/");
			String contexPath= req.getSession().getServletContext().getRealPath("/");

			System.out.println("lujign ="+contexPath);
			// 获取文件名
			fileName = file.getOriginalFilename();
			// 获取文件后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 重新生成文件名
			fileName = UUID.randomUUID() + suffixName;
			// 将图片保存到static文件夹里
			file.transferTo(new File(uploadRootPath + fileName));
		} catch (IllegalStateException e) {
			rs.put("code", 500);
			rs.put("msg", "上传图片失败！");
			e.printStackTrace();
		} catch (IOException e) {
			rs.put("code", 500);
			rs.put("msg", "上传图片失败！");
			e.printStackTrace();
		}
		rs.put("code", 200);
		rs.put("msg", "上传成功!");
		rs.put("pic", fileName);
		System.out.println(uploadRootPath + fileName);
		return rs;
	}
	
	
	
}
