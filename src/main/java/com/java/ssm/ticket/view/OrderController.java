package com.java.ssm.ticket.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.ssm.ticket.service.OrderService;

/**
 * 订单控制器
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService os;
	
	@GetMapping("/list")
	public String orderList(Model ui) {
		ui.addAttribute("OL", os.asUserOfOrderList());
		return "orderList";
	}
}
