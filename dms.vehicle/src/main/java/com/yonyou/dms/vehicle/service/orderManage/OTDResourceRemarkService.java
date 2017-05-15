package com.yonyou.dms.vehicle.service.orderManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.OTDResourceRemarkDTO;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.RemarkImpDto;

public interface OTDResourceRemarkService {
	/**
	 * otd备注设定初始化
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto remarkInit(Map<String, String> queryParam);

	/**
	 * 分配备注设定
	 * 
	 * @param dto
	 */
	void resourceAllotRemark(OTDResourceRemarkDTO dto);

	/**
	 * 分配备注设定下载
	 * 
	 * @param queryParam
	 * @param response
	 * @param request
	 */
	void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * 导入到临时表
	 * 
	 * @param rowDto
	 */
	void insertTmp(RemarkImpDto rowDto);

	/**
	 * 检查数据
	 * 
	 * @return
	 */
	ArrayList<RemarkImpDto> checkData();

	/**
	 * 临时表数据回显
	 * 
	 * @return
	 */
	List<Map> importShow();

	/**
	 * 导入
	 */
	void importTable();

}
