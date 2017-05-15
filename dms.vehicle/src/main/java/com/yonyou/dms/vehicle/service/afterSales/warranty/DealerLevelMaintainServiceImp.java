package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.Date;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.DealerLevelMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrDealerLevelDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrDealerLevelDcsPO;

@Service
public class DealerLevelMaintainServiceImp implements DealerLevelMaintainService {
	@Autowired
	DealerLevelMaintainDao dealerLevelDao;
	
	@Override
	public PageInfoDto DealerLevelQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = dealerLevelDao.DealerLevelQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 新增
	 */
	@Override
	public Long add(TtWrDealerLevelDTO dto) throws  ServiceBizException {
		TtWrDealerLevelDcsPO po = new TtWrDealerLevelDcsPO();
		setDealerLevelPo(po, dto);
		return po.getLongId();
	}
	
	private void setDealerLevelPo(TtWrDealerLevelDcsPO po, TtWrDealerLevelDTO dto) {
		boolean flag = false;
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!StringUtils.isNullOrEmpty(dto.getDealerId())) {
			LazyList<TtWrDealerLevelDcsPO> listPO = TtWrDealerLevelDcsPO.find("DEALER_ID = ?", dto.getDealerId());
			if (listPO.size()>0) {
				throw new ServiceBizException("该经销商已维护！新增失败！");
			}
			po.setLong("DEALER_ID", dto.getDealerId());
			po.setInteger("DER_LEVEL", dto.getDerLevel());
			po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
			po.setString("FIAT_WEBDAC_CODE", dto.getFiatWebdacCode());
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", new Date());
			po.setLong("UPDATE_BY", loginInfo.getUserId());
			po.setDate("UPDATE_DATE", new Date());
			flag=po.saveIt();
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
		if(flag){
		}else{
			throw new ServiceBizException("新增失败!");
		}
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update(TtWrDealerLevelDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrDealerLevelDcsPO po = TtWrDealerLevelDcsPO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				po.setInteger("DER_LEVEL", dto.getDerLevel());
				po.setInteger("STATUS", dto.getStatus());
				po.setString("FIAT_WEBDAC_CODE", dto.getFiatWebdacCode());
				po.setLong("UPDATE_BY", loginUser.getUserId());
				po.setDate("UPDATE_DATE", new Date());
				flag=po.saveIt();
			}
			if(flag){
			}else{
				throw new ServiceBizException("更新失败!");
			}
		}
	}
}
