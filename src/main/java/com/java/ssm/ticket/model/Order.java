package com.java.ssm.ticket.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单实体
 */
public class Order {

	private long oid;
	private String orderNumber;
	private Timestamp createTime;
	private long orderUid;
	private BigDecimal totalMoney;
	private String scenicName;
	private Integer peopleNumber;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public long getOrderUid() {
		return orderUid;
	}

	public void setOrderUid(long orderUid) {
		this.orderUid = orderUid;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", orderNumber=" + orderNumber + ", createTime=" + createTime + ", orderUid="
				+ orderUid + ", totalMoney=" + totalMoney + ", scenicName=" + scenicName + ", peopleNumber="
				+ peopleNumber + "]";
	}

}
