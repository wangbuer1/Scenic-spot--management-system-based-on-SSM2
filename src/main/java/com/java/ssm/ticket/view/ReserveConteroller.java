package com.java.ssm.ticket.view;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.ssm.ticket.model.Scenic;
import com.java.ssm.ticket.service.OrderService;
import com.java.ssm.ticket.service.ScenicService;

/**
 * 预定控制器
 */
@Controller
@RequestMapping("/")
public class ReserveConteroller {
	
	@Autowired
	ScenicService ss;
	@Autowired
	OrderService os;
	
	/**
	 * 渲染reserve页面数据
	 */
	@GetMapping("/reserve/{sid}")
	public String reserve(@PathVariable String sid,Model md) {
		if(sid == null) {
			//空的话就初始化
			sid = "1";
		}
		Scenic sc = ss.getScenicBySid(sid);
		md.addAttribute("sc", sc);
		return "reserve";
	}
	
	
	
	
	@ResponseBody
	@PostMapping("/reserve/order")
	public Map<String, Object> toReserve(@RequestParam String sid,@RequestParam String count) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(sid == null || count==null) {
			result.put("status", 500);
			result.put("msg", "参数错误!下单失败!");
			return result;
		}
		//下单服务 true就表示执行成功
		if(os.putReserveOrder(sid, count)) {
			result.put("status", 200);
			result.put("totalPrice", totalPrice(ss.getScenicBySid(sid).getScenicPrice(), Integer.valueOf(count)));
		}else {
			result.put("status", 505);
			result.put("msg", "创建订单发生异常!下单失败!");
		}
		return result;
	}
	
	
	
	
	//计算总价
	public BigDecimal totalPrice(BigDecimal price,Integer count) {
		return price.multiply(BigDecimal.valueOf(count));
	}
	
	
}
