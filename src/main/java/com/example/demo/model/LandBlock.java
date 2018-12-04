package com.example.demo.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

@Entity(name="land_block")
@Component
public class LandBlock {

	@GenericGenerator(name = "landBlockGenerator", strategy = "uuid")
	@Id
    @GeneratedValue(generator="landBlockGenerator")
	private String id;
	private String name;
	private String type;
	private String latitudeAndLongitude;
	private String parentId;
	private int level;
	private int hasChild;
	private String path;
	private String includeIds;
	private String jsonData;
	private String showStyle;
	private int status;
	private int orderNo;
	private Calendar addTime;
	private Calendar modifyTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLatitudeAndLongitude() {
		return latitudeAndLongitude;
	}
	public void setLatitudeAndLongitude(String latitudeAndLongitude) {
		this.latitudeAndLongitude = latitudeAndLongitude;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getHasChild() {
		return hasChild;
	}
	public void setHasChild(int hasChild) {
		this.hasChild = hasChild;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIncludeIds() {
		return includeIds;
	}
	public void setIncludeIds(String includeIds) {
		this.includeIds = includeIds;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public String getShowStyle() {
		return showStyle;
	}
	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Calendar getAddTime() {
		return addTime;
	}
	public void setAddTime(Calendar addTime) {
		this.addTime = addTime;
	}
	public Calendar getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Calendar modifyTime) {
		this.modifyTime = modifyTime;
	}
}
