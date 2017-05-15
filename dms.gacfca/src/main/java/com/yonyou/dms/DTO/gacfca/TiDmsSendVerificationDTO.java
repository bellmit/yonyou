package com.yonyou.dms.DTO.gacfca;

/**
 * 数据传输验证表(DMS)
 */
public class TiDmsSendVerificationDTO {
	private String fileType;
	private String fileFcaid;
	private String failReason;
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileFcaid() {
		return fileFcaid;
	}
	public void setFileFcaid(String fileFcaid) {
		this.fileFcaid = fileFcaid;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	
}
