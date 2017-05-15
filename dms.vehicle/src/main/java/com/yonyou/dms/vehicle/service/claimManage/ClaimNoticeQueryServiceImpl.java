package com.yonyou.dms.vehicle.service.claimManage;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtClaimsNoticeDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimManage.ClaimNoticeQueryDao;


/**
* @author liujm
* @date 2017年4月25日
*/

@Service
public class ClaimNoticeQueryServiceImpl implements ClaimNoticeQueryService {
	
	
	@Autowired
	private ClaimNoticeQueryDao cnqDao;
	
	
	/**
	 * 索赔结算单查询
	 */
	@Override
	public PageInfoDto queryClaimNotice(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = cnqDao.getClaimNoticeQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 索赔结算单(明细) 详细信息
	 */
	@Override
	public PageInfoDto queryClaimNoticeDetail(Long claimBillingId) throws ServiceBizException {
		PageInfoDto pageInfoDto = cnqDao.getClaimNoticeQueryDetail(claimBillingId);
		return pageInfoDto;
	}

	/**;
	 * 索赔结算单(明细)
	 */
	@Override
	public Map queryClaimNoticeDetailMap(Long claimBillingId) throws ServiceBizException {
		Map map = cnqDao.getClaimNoticeQueryDetailMap(claimBillingId);
		return map;
	}
	/**
	 * 明细 （开票）
	 */
	@Override
	public void updateClaimNotice(Long claimBillingId, Integer isInvoice) throws ServiceBizException {
		if(isInvoice == 50201002){
			throw new ServiceBizException("已经开票,请勿重复开票！");
		}
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//开票
		TtClaimsNoticeDcsPO updatePo = new TtClaimsNoticeDcsPO();
		updatePo.set("IS_INVOICE", OemDictCodeConstants.CLAIMS_INVOICE_STATUS_02);
		updatePo.set("UPDATE_BY", loginInfo.getUserId());
		updatePo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
		updatePo.saveIt();
	}
	/**
	 * 下载
	 */
	@Override
	public void claimNoticeDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> downloadList = cnqDao.getClaimNoticeQueryDownloadList(queryParam);
		
		//下载EXCEL
		
		
		
		
	}

}
