package com.yonyou.dms.vehicle.service.orderManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.CommonResorceRemarkDTO;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.RemarkImpDto;

public interface CommonResorceRemarkService {
	/**
	 * 资源分配备注初始化
	 * 
	 * @param queryParam
	 * @author lianxinglu
	 * @return
	 */
	PageInfoDto CommonResorceRemarkInt(Map<String, String> queryParam);

	/**
	 * 额外支持（设定、取消）
	 * 
	 * @param dto
	 */
	void resourceSupport(CommonResorceRemarkDTO dto);

	/**
	 * 解锁
	 * 
	 * @param dto
	 */
	void resourceUnlock(CommonResorceRemarkDTO dto);

	/**
	 * 锁定
	 * 
	 * @param dto
	 */
	void resourceLock(CommonResorceRemarkDTO dto);

	/**
	 * 分配备注
	 * 
	 * @param dto
	 */
	void resourceAllotRemark(CommonResorceRemarkDTO dto);

	/**
	 * 下载
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
	void insertTmpCommonResourceRemark(RemarkImpDto rowDto);

	/**
	 * 检查数据的合法性
	 * 
	 * @param rowDto
	 * @return
	 */
	ArrayList<RemarkImpDto> checkData();

	/**
	 * 临时数据回显
	 * 
	 * @param queryParam
	 * @return
	 */
	List<Map> importShowCon(Map<String, String> queryParam);

	/**
	 * 导入
	 */
	void importSave();

	void resourceNoSupport(CommonResorceRemarkDTO dto);

}
