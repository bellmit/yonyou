package com.yonyou.dms.vehicle.service.afterSales.preclaim;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalPO;

/**
 * 索赔预授权维护
 * @author Administrator
 *
 */
public interface PreclaimPreService {
	//索赔预授权维护查询
	public PageInfoDto PreclaimPreQuery(Map<String, String> queryParam);
	
	//驳回意见查询
	public TtWrForeapprovalPO getPreclaimPreById(Long id);
	
	//删除
	public void delete(Long id);
	
	//上报
	public void preclaimCommit(Long id);

	//查询项目信息
	public List<Map> getGongshi(Map<String, String> queryParam);

	//文件上传
	public void uploadFiles(MultipartFile importFile);
	
	//附件信息查询
	public PageInfoDto getFuJian(Map<String, String> queryParam,Long id);

	//索赔预授权维护修改
	public void edit(Long id, TtWrForeapprovalDTO dto,String type);
	
	//通过dealer_code获取该公司基本信息
	public Map getPreclaimPre();
	
	//索赔预授权维护新增
   public void add(TtWrForeapprovalDTO ptdto,String type);
}
