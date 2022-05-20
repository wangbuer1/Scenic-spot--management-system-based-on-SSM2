package com.java.ssm.ticket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.java.ssm.ticket.model.Order;

/**
 * 订单服务层
 *
 */
@Mapper
public interface OrderDao {

	@Select("SELECT * FROM `ticket`.`order_table` WHERE oid = #{oid}")
	Order findOrderByOid(long oid);

	@Options(useGeneratedKeys = true, keyProperty = "oid")
	@Insert("INSERT INTO `ticket`.`order_table`(`order_number`,`create_time`, `order_uid`, `total_money`, `scenic_name`,`people_number`) VALUES (#{orderNumber}, #{createTime}, #{orderUid},#{totalMoney},#{scenicName},#{peopleNumber})")
	int saveOrder(Order order);

	@Update("UPDATE `ticket`.`order_table` SET order_number = #{orderNumber},create_time = #{createTime},order_uid = #{orderUid},total_money = #{totalMoney},scenic_name = #{scenicName},people_number = #{peopleNumber} WHERE oid = #{oid}")
	int UpdateOrder(Order order);

	@Select("SELECT * FROM `ticket`.`order_table`")
	List<Order> getAllOrder();

	@Select("SELECT * FROM `ticket`.`order_table` WHERE order_uid = #{uid}")
	List<Order> findOrderByUid(@Param("uid") long uid);
	
	@Delete("DELETE FROM `ticket`.`order_table` WHERE oid = #{oid}")
	int delOrderByOid(long oid);
}
