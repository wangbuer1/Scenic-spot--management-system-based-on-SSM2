package com.java.ssm.ticket.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.ssm.ticket.dao.OrderDao;
import com.java.ssm.ticket.dao.ScenicDao;
import com.java.ssm.ticket.model.Order;
import com.java.ssm.ticket.model.Scenic;
import com.java.ssm.ticket.model.User;
import com.java.ssm.ticket.utils.DateUtil;
import com.java.ssm.ticket.view.UserController;

/**
 * 订单业务
 */
@Component
public class OrderService {

	@Autowired
	OrderDao od;

	@Autowired
	ScenicDao sd;

	@Autowired
	HttpServletRequest req;

	/**
	 * 下单业务
	 */
	public Boolean putReserveOrder(String sid, String count) {
		// 获取session中的用户id
		User ur = (User) req.getSession().getAttribute(UserController.USER_LOGIN_KEY);
		// 生成订单id
		String orderNumber = UUID.randomUUID().toString();
		// 通过景点id查询景点信息
		Scenic sc = sd.findScenicBySid(Long.valueOf(sid));
		//创建order实体存储到数据库
		Order or = new Order();
		or.setOrderUid(ur.getUid());
		or.setOrderNumber(orderNumber);
		or.setPeopleNumber(Integer.valueOf(count));
		or.setCreateTime(DateUtil.asDateToTimestamp());
		or.setScenicName(sc.getScenicName());
		or.setTotalMoney(totalPrice(sc.getScenicPrice(), Integer.valueOf(count)));
		return !(od.saveOrder(or) > 1);
	}
	/**
	 * 
	 * @param uid 用户id
	 * @return 返回用户订单记录列表
	 */
	public List<Order> asUserOfOrderList(){
		// 获取session中的用户id
		User ur = (User) req.getSession().getAttribute(UserController.USER_LOGIN_KEY);
		return od.findOrderByUid(ur.getUid());
	}
	
	public List<Order> allOrders(){
		return od.getAllOrder();
	}
	
	public boolean delOneOrder(String oid) {
		return (od.delOrderByOid(Long.valueOf(oid))>0);
	}
	
	// 计算总价
	public BigDecimal totalPrice(BigDecimal price, Integer count) {
		return price.multiply(BigDecimal.valueOf(count));
	}
}
