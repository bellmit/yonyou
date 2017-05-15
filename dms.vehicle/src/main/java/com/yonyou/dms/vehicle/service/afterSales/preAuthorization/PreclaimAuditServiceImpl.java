package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.PreclaimAuditDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForecheckLogPO;

/**
 * 索赔预授权审核作业 
 * @author Administrator
 *
 */
@Service
public class PreclaimAuditServiceImpl implements PreclaimAuditService{
	@Autowired
	PreclaimAuditDao  preclaimAuditDao;

	/**
	 * 索赔预授权审核作业查询
	 */
	@Override
	public PageInfoDto PreclaimAuditSearch(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return preclaimAuditDao.PreclaimAuditSearch(queryParam);
	}
	/**
	 * 同意,审核通过
	 */
	public void editTongyi(Long id, TtWrForeapprovalDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrForeapprovalPO ptPo = TtWrForeapprovalPO.findById(id);
		String currentAuthCode = ptPo.get("CURRENT_AUTH_CODE").toString();//当前审核级别代码
		String authCode = ptPo.get("AUTH_CODE").toString();//审核级别代码
		
		 ptPo.setLong("AUDIT_PERSON", loginInfo.getUserId());	//审核人
		 ptPo.setDate("AUDIT_DATE", new Date()); //审核时间	

		 String[] auts = authCode.split(",");
         for(int i=0; i<auts.length; i++){
         	if(auts[i].equals(currentAuthCode)&&i<auts.length-1){
         		ptPo.setString("CURRENT_AUTH_CODE", auts[i+1]);
         		break;
         	}if(i == auts.length-1){
         		ptPo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_04);   		
         	}
         }
		   ptPo.saveIt();
		   
		 //日志记录
		   TtWrForecheckLogPO logpo = new TtWrForecheckLogPO();
		   logpo.setLong("FORE_ID", id);
		   logpo.setString("AUTH_LEVEL", currentAuthCode);//审核级别代码
		   logpo.setLong("CHECK_USER", loginInfo.getUserId());
		   logpo.setDate("CHECK_DATE", new Date()); //审核时间	
		   logpo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_04);  
		   logpo.setString("CHECK_DESC","通过");     
		   logpo.saveIt();  
	}
	
	/**
	 * 通过id查询信息回显
	 */

	@Override
	public TtWrForeapprovalPO getForeapprovalById(Long id) {
		// TODO Auto-generated method stub
		return TtWrForeapprovalPO.findById(id);
	}
	
	/**
	 * 索赔预授权审核  拒绝\退回
	 */
	
	public void editJuTui(Long id, TtWrForeapprovalDTO ptdto,String type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String auditOpinion=ptdto.getAuditOpinion();	//驳回意见
		String currentAuthCode=ptdto.getCurrentAuthCode(); //审核级别代码  currentAuthCode
		int status = 0;
		TtWrForeapprovalPO tPo = TtWrForeapprovalPO.findById(id);
		if(auditOpinion!=null){
		if(type.equals("tui")){
			status = OemDictCodeConstants.FORE_APPLOVAL_STATUS_03; // 退回
			String authCode = tPo.get("AUTH_CODE").toString();//审核级别代码
			String[] auts = authCode.split(",");
			tPo.setString("CURRENT_AUTH_CODE", auts[0]);
		}
		if(type.equals("ju")){
			status = OemDictCodeConstants.FORE_APPLOVAL_STATUS_05; // 拒绝
		}
		     tPo.setInteger("STATUS",status);
		     tPo.setLong("AUDIT_PERSON", loginInfo.getUserId());//审核人
		     tPo.setDate("AUDIT_DATE", new Date()); //审核时间	
		     tPo.setString("AUDIT_OPINION", auditOpinion);//驳回意见
		     tPo.saveIt();
		   
		 //日志记录
		   TtWrForecheckLogPO logpo = new TtWrForecheckLogPO();
		   logpo.setLong("FORE_ID", id);
		   logpo.setString("AUTH_LEVEL", currentAuthCode);//审核级别代码
		   logpo.setLong("CHECK_USER", loginInfo.getUserId());
		   logpo.setDate("CHECK_DATE", new Date()); //审核时间	
		   logpo.setInteger("STATUS",status);  
		   logpo.setString("CHECK_DESC",auditOpinion);     
		   logpo.saveIt();  
	}else{
		  throw new ServiceBizException("驳回意见不能为空，请输入！"); 
	}
	}

}
