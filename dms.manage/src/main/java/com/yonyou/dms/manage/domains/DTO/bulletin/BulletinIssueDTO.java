package com.yonyou.dms.manage.domains.DTO.bulletin;

import java.util.Date;
import java.util.List;

/**
 * 通报发布DTO
 * @author Administrator
 *
 */
public class BulletinIssueDTO {
	
	private String topic;
	private Long typeId;
	private Long bulletinId;
	private String typename;
	private Date beginDate;
	private Date endDate;
	private String isShow;
	private String isCheck;
	private String level;
	private String content;
	private Integer region;
	private String position;	//职位  3为都选  2为销售经理  1为售后经理  0为不选择
	private String dmsFileIds;  //文件
	private String dealerId;	//经销商ID

	private List<fileListDTO> fileList;
	
	private List<posListDTO> posList;
	
	private List<dealerListDTO> dealerList;
	
	private String dealerIds; //经销商IDs
	
	public Long getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Long bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String getDmsFileIds() {
		return dmsFileIds;
	}
	
	public void setDmsFileIds(String dmsFileIds) {
		this.dmsFileIds = dmsFileIds;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

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

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public List<fileListDTO> getFileList() {
		return fileList;
	}

	public void setFileList(List<fileListDTO> fileList) {
		this.fileList = fileList;
	}

	public List<posListDTO> getPosList() {
		return posList;
	}

	public void setPosList(List<posListDTO> posList) {
		this.posList = posList;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public List<dealerListDTO> getDealerList() {
		return dealerList;
	}

	public void setDealerList(List<dealerListDTO> dealerList) {
		this.dealerList = dealerList;
	}

	public String getDealerIds() {
		return dealerIds;
	}

	public void setDealerIds(String dealerIds) {
		this.dealerIds = dealerIds;
	}
	
	
	

}
