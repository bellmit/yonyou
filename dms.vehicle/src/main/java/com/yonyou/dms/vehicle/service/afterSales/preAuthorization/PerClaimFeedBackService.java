package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalFeedbackDTO;

/**
 * 索赔预授权反馈
 * @author Administrator
 *
 */
public interface PerClaimFeedBackService {
	/**
	 * 索赔预授权反馈查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto PerClaimFeedBack(Map<String, String> queryParam) ;
	
	/**
	 * 查询基本信息
	 */
	public Map getPerClaimFeed(Long id);
	/**
	 * 项目信息
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto XiangMuXinXi(Map<String, String> queryParam,Long id);
	
	/**
	 * 新增反馈信息
	 */
	public Long addFanKui(TtWrForeapprovalFeedbackDTO ptdto);
	
	/**
	 * 通过id 查询附件信息
	 */
    public Map getFuJian(Long id);
    /**
     * 文件上传
     */
	public void uploadFiles(MultipartFile importFile) throws ServiceBizException;
	
	/**
	 * 查询反馈信息
	 */
    public Map getFunKui(Long id);

}
