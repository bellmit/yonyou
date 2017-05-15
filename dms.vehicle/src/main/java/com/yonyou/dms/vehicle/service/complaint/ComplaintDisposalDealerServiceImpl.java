package com.yonyou.dms.vehicle.service.complaint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.complaint.ComplaintDisposalDealerDao;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtCrComplaintsDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.basedata.TtCrComplaintsDcsPO;
import com.yonyou.dms.vehicle.domains.PO.basedata.TtCrComplaintsHistoryDcsPO;

/**
 *  客户投诉处理Service
 * @author ZhaoZ
 * @date 2017年4月17日
 */

@Service
public class ComplaintDisposalDealerServiceImpl implements ComplaintDisposalDealerService{

	@Autowired
	ComplaintDisposalDealerDao  customerdao;
	/**
	 * 客户投诉处理(总部)查询
	 */
	@Override
	public PageInfoDto complaintDisposalOem(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.complaintDisposalOemList(queryParams);
	}
	
	/**
	 * 取得客户投诉表的基本信息
	 */
	@Override
	public Map<String, Object> getComplaintById(Long compId) throws ServiceBizException {
		return customerdao.getComplaint(compId);
	}

	/**
	 * 根据投诉id查询车辆表信息
	 */
	@Override
	public PageInfoDto getVicheilbyCompId(Long compId) throws ServiceBizException {
		return customerdao.getVicheil(compId);
	}

	/**
	 * 保存
	 */
	@Override
	public void saveDeliveryOrder(TtCrComplaintsDcsDTO dto, Long id) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			d = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}//处理时间为当前时间
		Date allotDate = null;
		try {
			allotDate = formatter.parse(dto.getAlloDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date currTime = new Date(System.currentTimeMillis());
		//更新投诉信息
		
		long diff = d.getTime() - (allotDate == null ? 0 : allotDate.getTime()) ;//计算反馈周期
		Long hours = diff / (1000 * 60 * 60);
		int FeedbackWeek = hours.intValue();
		TtCrComplaintsDcsPO po= TtCrComplaintsDcsPO.findById(id);
		po.setInteger("STATUS",OemDictCodeConstants.COMP_STATUS_TYPE_03);
		if(dto.getIsR().equals("YES")){
			po.setInteger("IS_RETURN",OemDictCodeConstants.IF_TYPE_YES);
		}else if(dto.getIsR().equals("No")){
			po.setInteger("IS_RETURN",OemDictCodeConstants.IF_TYPE_NO);
		}
		if(dto.getNoReturnMark()!=""){
			po.setString("NORETURN_REMARK",dto.getNoReturnMark());
		}
		po.setLong("UPDATE_BY",logonUser.getUserId());
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			po.setTimestamp("UPDATE_DATE",st);
			po.setTimestamp("DLR_DEAL_DATE",st);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		po.saveIt();//更新投诉表中数据。
		//处理历史信息
	    TtCrComplaintsHistoryDcsPO poh= new TtCrComplaintsHistoryDcsPO();
	    poh.setId(System.currentTimeMillis());
	    poh.setLong("COMP_ID",id);
	    poh.setLong("DEALER_ID",logonUser.getDealerId());
		poh.setString("DEAL_PLAN",dto.getDealPlan());
		poh.setString("DEAL_USER",dto.getDealUser());
		poh.setString("CUSTOM_FEEDBACK",dto.getCustomFeedback());
		//确定反馈状态，如果为第一次则写入是 ，否则不填写
		Long dealerId=logonUser.getDealerId();	
		List<Map> LabourList=customerdao.queryLabourList(id,dealerId);
		if(LabourList.size()==0){
			poh.setInteger("FEEDBACK_STATUS",OemDictCodeConstants.IF_TYPE_YES);
			poh.setInteger("FEEDBACK_WEEK",FeedbackWeek+1);
		}
		poh.setDate("DEAL_DATE",d);
		poh.setLong("CREATE_BY",logonUser.getUserId());
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			poh.setTimestamp("CREATE_DATE",st);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		poh.insert();
		
	}

	/**
	 * 取消发布
	 */
	@Override
	public void updateComplaint(TtCrComplaintsDcsDTO dto, Long id) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtCrComplaintsDcsPO newpo= TtCrComplaintsDcsPO.findById(id);
		newpo.setInteger("STATUS",OemDictCodeConstants.COMP_STATUS_TYPE_04);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date allotDate = null;
		try {
			allotDate = formatter.parse(dto.getAlloDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Timestamp d = null;
		//当前系统时间日期
		java.sql.Timestamp currTime =null;
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			d = new java.sql.Timestamp(date.getTime());
			currTime = new java.sql.Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		//处理时间为当前时间
		long diff = d.getTime() - (allotDate == null ? 0 : allotDate.getTime());//计算反馈周期
		Long hours = diff / (1000 * 60 * 60);
		int FeedbackWeek = hours.intValue();
		
		TtCrComplaintsHistoryDcsPO poh= new TtCrComplaintsHistoryDcsPO();
		
		if("YES".equals(dto.getIsR())){
			newpo.setInteger("IS_RETURN",OemDictCodeConstants.IF_TYPE_YES);
			poh.setString("DEAL_PLAN","不需回访");//处理方案
			poh.setString("DEAL_USER",logonUser.getUserName());//处理人
		}else if("No".equals(dto.getIsR())){
			newpo.setInteger("IS_RETURN",OemDictCodeConstants.IF_TYPE_NO);
			String dealUser = Utility.checkNull(dto.getDealUser());  				//处理人
			String dealPlan = Utility.checkNull(dto.getDealPlan());  				//处理方案
			String customFeedback = Utility.checkNull(dto.getCustomFeedback());  				//客户反馈
//			String pro = CommonUtils.checkNull(request.getParamValue("pro"),"0");
			poh.setString("DEAL_PLAN",dealPlan);//处理方案
			poh.setString("DEAL_USER",dealUser);//处理人
			poh.setString("CUSTOM_FEEDBACK",customFeedback);//客户反馈
		}
	
	    poh.setId(System.currentTimeMillis());
	    poh.setLong("COMP_ID",id);
	    poh.setLong("DEALER_ID",logonUser.getDealerId());
	    List<Map> LabourList=customerdao.queryLabourList(id,logonUser.getDealerId());
	    if(LabourList.size()==0){
	    	poh.setInteger("FEEDBACK_STATUS",OemDictCodeConstants.IF_TYPE_YES);
			poh.setInteger("FEEDBACK_WEEK",FeedbackWeek+1);
	    	
	    }
	    
	    poh.setTimestamp("DEAL_DATE",d);
		poh.setLong("CREATE_BY",logonUser.getUserId());
		poh.setTimestamp("CREATE_DATE",currTime);
		poh.insert();
		newpo.setString("NORETURN_REMARK",dto.getNoReturnMark());
		newpo.setLong("UPDATE_BY",logonUser.getUserId());
		newpo.setTimestamp("NORETURN_DEALDATE", currTime);
		newpo.setTimestamp("UPDATE_DATE",currTime);
		newpo.saveIt();//更新投诉表中数据。
	}

	/**
	 * 客户投诉处理(总部) 不需回访查询
	 */
	@Override
	public PageInfoDto complaintDisposal(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.complaintDisposalList(queryParams);
	}

	/**
	 * 客户投诉处理(总部)不需回访处理 页面初始化
	 */
	@Override
	public Map<String, Object> disposalDisposal(Long compId) throws ServiceBizException {
		return customerdao.getComplaintList(compId);
	}

	/**
	 * 客户投诉处理(总部)不需回访处理 页面初始化
	 */
	@Override
	public PageInfoDto disposalDisposal1(Long compId) throws ServiceBizException {
		return customerdao.getComplaintList1(compId);
	}

	/**
	 * 客户投诉处理(总部) 不需回访查询DLR
	 */
	@Override
	public PageInfoDto complaintDisposalList(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.getComplaintDLR(queryParams);
	}

	/**
	 * 客户投诉查询下载
	 */
	@Override
	public List<Map> excelExportList(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.complaintDisposalQuery(queryParams);
	}

}
