package com.java.ssm.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.ssm.ticket.dao.ScenicDao;
import com.java.ssm.ticket.model.Scenic;

/**
 * scenic服务业务层
 */
@Component
public class ScenicService {
	
	
	@Autowired
	ScenicDao sd;
	
	/**
	 * 获取所有scenic信息
	 * @return 集合scenic
	 */
	public List<Scenic> getAll(){
		return sd.getAllScenic();
	}
	
	/**
	 * 获取通过sid获取scenic
	 * @param sid
	 * @return scenic
	 */
	public Scenic getScenicBySid(String sid) {
		return sd.findScenicBySid(Long.valueOf(sid));
	}
	
	
	public Boolean deleteScenicById(String sid) {
		return (sd.deleteScenicById(Long.valueOf(sid)) >0 );
	}
	
	public Boolean saveScenic(Scenic sc) {
		return (sd.saveScenic(sc) > 0);
	}
	
	/**
	 * star热门次数自加
	 * @param sid
	 */
	public void addOneStar(String sid){
		Scenic sc = sd.findScenicBySid(Long.valueOf(sid));
		//获取数据库原理的值然后加1 然后就实现用户每点击一次详情 景点star数就加一
		sc.setScenicStar(sc.getScenicStar()+1);
		//更新保存到数据库
		sd.UpdateScenic(sc);
	}
	
	/**
	 * 通过关键字查询景点
	 * @param key 关键字
	 * @return 返回查询结果集合
	 */
	public List<Scenic> searchKey(String key){
		//调用数据访问层
		return sd.searchKeyScenic(key);
	}
}
