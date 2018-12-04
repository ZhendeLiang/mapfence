package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.example.demo.model.LandBlock;

@Validated
public class LandBlockDto { 
	@NotNull(message = "{name.null}")
	private String name;
	private String type;
	private String latitudeAndLongitude;
	private String jsonData;
	private String parentId;
	private int status;
	private int orderNo;

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

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
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
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public LandBlock toLandBlock() {
		LandBlock landBlock = new LandBlock();
		landBlock.setName(this.getName());
		landBlock.setType(this.getType());
		landBlock.setJsonData(this.getJsonData());
		landBlock.setStatus(this.getStatus());
		landBlock.setOrderNo(this.getOrderNo() >= 0 ? 1 : this.getOrderNo());
		landBlock.setParentId(StringUtils.isNotBlank(this.getParentId()) ? this.getParentId() : "");
		landBlock.setLatitudeAndLongitude(StringUtils.isNotBlank(this.getLatitudeAndLongitude())
				? this.getLatitudeAndLongitude().substring(0, this.getLatitudeAndLongitude().length() - 1)
				: "");
		return landBlock;
	}

	@Override
	public String toString() {
		return "LandBlockDto [name=" + name + ", type=" + type + ", latitudeAndLongitude=" + latitudeAndLongitude
				+ ", jsonData=" + jsonData + ", parentId=" + parentId + ", status=" + status + ", orderNo=" + orderNo
				+ "]";
	}
}
