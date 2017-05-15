package com.yonyou.dms.vehicle.service.complaint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.complaint.ComplaintDisposalNoDao;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtCrComplaintsDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtVisitJdpowerDTO;
import com.yonyou.dms.vehicle.domains.PO.basedata.TmpVisitJdpowerDcsPO;
import com.yonyou.dms.vehicle.domains.PO.basedata.TtCrComplaintsDcsPO;
import com.yonyou.dms.vehicle.domains.PO.claimApproveMgr.TtVisitJdpowerDcsPO;

/**
 *  客户投诉处理Service
 * @author ZhaoZ
 * @date 2017年4月17日
 */

@Service
public class ComplaintDisposalNoServiceImpl implements ComplaintDisposalNoService{

	@Autowired
	ComplaintDisposalNoDao  customerdao;

	/**
	 * 客户投诉处理(总部) 不需回访查询
	 */
	@Override
	public PageInfoDto complaintDisposalNoList(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.complaintDisposalNo(queryParams);
	}

	/**
	 * 审核通过
	 */
	@Override
	public void dealAgree(TtCrComplaintsDcsDTO dto, Long compId) throws ServiceBizException {
		boolean flag = false;
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtCrComplaintsDcsPO newpo= TtCrComplaintsDcsPO.findById(compId);
		newpo.setString("NORETURN_BACKREMARK",dto.getNoReturnBackRemark()==null?"":dto.getNoReturnBackRemark());
		newpo.setInteger("STATUS",OemDictCodeConstants.COMP_STATUS_TYPE_05);
		newpo.setLong("UPDATE_BY",logonUser.getUserId());
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			newpo.setTimestamp("UPDATE_DATE",st);
			newpo.setTimestamp("OEM_NO_CALL_DATE",st);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		newpo.setInteger("DEAL_RESULT",40);
		flag = newpo.saveIt();
		if(!flag){
			throw new ServiceBizException("审核通过失败!");
		}
	}

	/**
	 * 驳回
	 */
	@Override
	public void dealNotAgree(TtCrComplaintsDcsDTO dto, Long compId) throws ServiceBizException {
		boolean flag = false;
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtCrComplaintsDcsPO newpo= TtCrComplaintsDcsPO.findById(compId);
		newpo.setString("NORETURN_BACKREMARK",dto.getNoReturnBackRemark()==null?"":dto.getNoReturnBackRemark());
		newpo.setInteger("STATUS",OemDictCodeConstants.COMP_STATUS_TYPE_03);
		newpo.setLong("UPDATE_BY",logonUser.getUserId());
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			newpo.setTimestamp("NORETURN_DEALDATE",st);
			newpo.setTimestamp("UPDATE_DATE",st);
			newpo.setTimestamp("OEM_NO_CALL_DATE",st);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		flag = newpo.saveIt();
		if(!flag){
			throw new ServiceBizException("审核驳回失败!");
		}
	}

	/**
	 * 数据插入临时表
	 */
	@Override
	public void insertTmpVisitJdpowerDcs(TtVisitJdpowerDTO rowDto) throws ServiceBizException {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = "";
		if(rowDto.getDealerCode()!=null && rowDto.getDealerCode()!=""){
			 dealerCode = rowDto.getDealerCode().trim().replace("A", "");
		}
		TmpVisitJdpowerDcsPO newPo = new TmpVisitJdpowerDcsPO();
		newPo.setString("LINE_NO",rowDto.getRowNO());
		newPo.setString("DEALER_CODE",dealerCode+"A");
		newPo.setString("LINK_NAME",rowDto.getLinkName());
		newPo.setString("LINK_PHONE",rowDto.getLinkPhone());
		newPo.setString("VIN",rowDto.getVin());
		if(!"".equals(rowDto.getBackDate())&& rowDto.getBackDate()!=null ){
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
			String backDate = sdf.format(rowDto.getBackDate());
			newPo.setString("BACK_DATE",backDate);//反馈时间
		}else{
			newPo.setString("BACK_DATE",rowDto.getBackDate());//反馈时间
		}
		
		
		
		newPo.setString("BACK_TYPE",rowDto.getBackType());
		newPo.setString("BACK_CONTENT",rowDto.getBackContent());
		newPo.setString("VISIT_TYPE",rowDto.getVisitType());
		newPo.setString("VISIT_SOURCE",rowDto.getVisitSource());
		newPo.setLong("CREATE_BY",logonUser.getUserId()!=null?logonUser.getUserId().toString():"222222");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			newPo.setTimestamp("CREATE_DATE",st);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		newPo.insert();
	}

	@Override
	public List<TtVisitJdpowerDTO> checkData() throws ServiceBizException {
		ArrayList<TtVisitJdpowerDTO> trvdDTOList = new ArrayList<TtVisitJdpowerDTO>();
		ImportResultDto<TtVisitJdpowerDTO> importResult = new ImportResultDto<TtVisitJdpowerDTO>();
		
		//数据正确性校验
		List<Map> trvdList = customerdao.checkData2();		
		if(trvdList.size()>0){
			for(Map p:trvdList){
				TtVisitJdpowerDTO rowDto = new TtVisitJdpowerDTO();
				 rowDto.setRowNO(Integer.valueOf(p.get("rowNum").toString()));
				 rowDto.setErrorMsg(p.get("errorDesc").toString());
				 trvdDTOList.add(rowDto);
			}
			importResult.setErrorList(trvdDTOList);
		}

		return trvdDTOList;
	}

	/**
	 * 回访结果查询
	 */
	@Override
	public PageInfoDto returnVisitResultList(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.returnVisitResults(queryParams);
	}

	/**
	 * 经销商端回访任务处理/回访结果下载
	 */
	@Override
	public List<Map> excelExportList(Map<String, String> queryParams,String dealerCode) throws ServiceBizException {
		return customerdao.returnVisitDealerDownLoad(queryParams,dealerCode);
	}

	/**
	 * 经销商端回访任务处理查询
	 */
	@Override
	public PageInfoDto returnVisitDealerList(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.returnVisitDealers(queryParams);
	}

	/**
	 * 处理结果保存
	 */
	@Override
	public void save(TtVisitJdpowerDTO dto, String type, Long visitId) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		TtVisitJdpowerDcsPO tvj = TtVisitJdpowerDcsPO.findById(visitId);
		if(tvj!=null){
			
			tvj.setString("DISPOSE_NAME",dto.getDisposeName());
			tvj.setInteger("DISPOSE_RESULT",(dto.getDisposeResult() !=null?Integer.parseInt(dto.getDisposeResult()):0));
			tvj.setString("RESULT_CONTENT",dto.getResultContent());
			if(type.equals("2")){
				try {
					Date date = sdf.parse(sdf.format(new Date(time)));
					java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
					tvj.setTimestamp("DISPOSE_DATE",st);
				} catch (ParseException e) {
					e.printStackTrace();
				} 
				tvj.setInteger("VISIT_STATUS",OemDictCodeConstants.TAIL_AFTER_STATUS_02);
			}
			try {
				Date date = sdf.parse(sdf.format(new Date(time)));
				java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
				tvj.setTimestamp("DISPOSE_DATE",st);
				tvj.setTimestamp("UPDATE_DATE",st);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			tvj.setLong("UPDATE_BY",2222L);
			tvj.saveIt();
		}
	}

	
}
