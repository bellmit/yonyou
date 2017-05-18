package com.yonyou.dms.manage.domains.DTO.bulletin;

import java.util.List;

import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;

public class BulletinTypeDTO {
	
	private Long typeId;
	private String typename;
	private Integer status;
	private List<TcUserDTO> userList;		//发布人员信息
	private String userIds; //userid 使用逗号分割
	
	
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<TcUserDTO> getUserList() {
		return userList;
	}
	public void setUserList(List<TcUserDTO> userList) {
		this.userList = userList;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	
	
	

}
