package com.java.ssm.ticket.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 景点表对应的实体类
 */
public class Scenic {
	private long sid;
	private String scenicName;
	private BigDecimal scenicPrice;
	private String scenicAddress;
	private String scenicDetails;
	private String scenicPic;
	private Integer scenicStar;
	private Timestamp createTime;

	public long getSid() {
		return sid;
	}

	public void setSid(long sid) {
		this.sid = sid;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public BigDecimal getScenicPrice() {
		return scenicPrice;
	}

	public void setScenicPrice(BigDecimal scenicPrice) {
		this.scenicPrice = scenicPrice;
	}

	public String getScenicAddress() {
		return scenicAddress;
	}

	public void setScenicAddress(String scenicAddress) {
		this.scenicAddress = scenicAddress;
	}

	public String getScenicDetails() {
		return scenicDetails;
	}

	public void setScenicDetails(String scenicDetails) {
		this.scenicDetails = scenicDetails;
	}

	public String getScenicPic() {
		return scenicPic;
	}

	public void setScenicPic(String scenicPic) {
		this.scenicPic = scenicPic;
	}

	public Integer getScenicStar() {
		return scenicStar;
	}

	public void setScenicStar(Integer scenicStar) {
		this.scenicStar = scenicStar;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Scenic [sid=" + sid + ", scenicName=" + scenicName + ", scenicPrice=" + scenicPrice + ", scenicAddress="
				+ scenicAddress + ", scenicDetdils=" + scenicDetails + ", scenicPic=" + scenicPic + ", scenicStar="
				+ scenicStar + ", createTime=" + createTime + "]";
	}

}
