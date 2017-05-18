package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.List;
import java.util.Map;

/**
 * @description 配件单
 * @author Administrator
 *
 */
public class PartMoveStorageDTO {
	
	private String transferNo;
	
	private String transferSign;
	
	private String delJsonStr;

	private List<Map<String,Object>> partMoveItemTable;

	
	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public List<Map<String, Object>> getPartMoveItemTable() {
		return partMoveItemTable;
	}

	public void setPartMoveItemTable(List<Map<String, Object>> partMoveItemTable) {
		this.partMoveItemTable = partMoveItemTable;
	}
	
	public String getTransferSign() {
		return transferSign;
	}

	public void setTransferSign(String transferSign) {
		this.transferSign = transferSign;
	}

	public String getDelJsonStr() {
		return delJsonStr;
	}

	public void setDelJsonStr(String delJsonStr) {
		this.delJsonStr = delJsonStr;
	}
}
