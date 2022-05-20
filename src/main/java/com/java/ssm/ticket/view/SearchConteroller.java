package com.java.ssm.ticket.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.ssm.ticket.model.Scenic;
import com.java.ssm.ticket.service.ScenicService;

/**
 * 搜索框控制器
 */
@Controller
@RequestMapping("/")
public class SearchConteroller {

	@Autowired
	ScenicService ss;
	
	/**
	 * 处理搜索功能
	 */
	@GetMapping("/search")
	public String Scarch(@RequestParam String key,Model md) {
		if (key == null) {
			key = "鼎";// 如果是空就默认值
		}
		List<Scenic> sl = ss.searchKey(key);
		if(sl.size()  == 0 || sl == null ) {
			md.addAttribute("sc", null);
		} else {
			md.addAttribute("sc", sl);
		}
		
		return "search";
	}
}
