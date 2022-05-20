package com.java.ssm.ticket.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.ssm.ticket.model.Scenic;
import com.java.ssm.ticket.service.ScenicService;

/**
 * 开发过程中测试数据访问是否正常的控制器
 * http://localhost:8088/ticket-ssm/test/1
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	ScenicService ss;
	
	
	/**
	 * http://localhost:8088/ticket-ssm/test/1
	 * @return 测试是否能获取scenic全部的数据
	 */
	@ResponseBody
	@GetMapping("/1")
	public List<Scenic> put1(){
		return ss.getAll();
	}
	
	/**
	 * http://localhost:8088/ticket-ssm/test/2
	 * @return 测试关键字搜索功能
	 */
	@ResponseBody
	@GetMapping("/2")
	public List<Scenic> put2(){
		return ss.searchKey("测试");
	}
}
