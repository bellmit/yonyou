package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyQueryOEMDao;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWarrantyCardDcsPO;

/**
 * 保修单管理OEM
 * @author zhanghongyi
 *
 */
@Service
public class WarrantyQueryOEMServiceImpl implements WarrantyQueryOEMService {
	
	@Autowired
	WarrantyQueryOEMDao warrantyQueryOEMDao;

	/**
	 * 操作代码查询
	 */
	@Override
	public PageInfoDto WarrantyQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return warrantyQueryOEMDao.getWarrantyQueryList(queryParam);
	}
	
	/**
	 * 保修单明细
	 */
	@Override
	public Map<String, Object> WarrantyDetailInfoById(BigDecimal id) {
		return warrantyQueryOEMDao.getWarrantyDetail(id);
	}
	
	/**
	 * 保修单故障明细
	 */
	@Override
	public Map<String, Object> WarrantyFaultInfoById(BigDecimal id) {
		return warrantyQueryOEMDao.getWarrantyFault(id);
	}
	
	/**
	 * 作废
	 */
	@Override
	public void delete(String wcCode) throws ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(wcCode)){
			LazyList<TtWrWarrantyCardDcsPO> listPO = TtWrWarrantyCardDcsPO.find("WC_CODE = ?", wcCode);
			if(listPO.size()==3){
				for (int i=0 ;i<listPO.size();i++){
					TtWrWarrantyCardDcsPO updatePo=listPO.get(i);
					if(updatePo!=null){
						updatePo.setLong("UPDATE_BY", loginUser.getUserId());
						updatePo.setDate("UPDATE_DATE", new Date());
						updatePo.setInteger("STATUS", OemDictCodeConstants.WARRANTY_REPAIR_STATUS_05);
						flag = updatePo.saveIt();
					}
					if(flag){			
					}else{
						throw new ServiceBizException("作废失败!");
					}
				}
			}
			else{
				throw new ServiceBizException("该保修单未接收到ESIGI接口数据，不能作废！");
			}
			
		}
		
	}
}
