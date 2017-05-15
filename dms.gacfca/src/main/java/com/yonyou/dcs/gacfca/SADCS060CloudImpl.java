package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dms.DTO.gacfca.SADCS060Dto;
import com.yonyou.dms.common.domains.PO.basedata.TiWxCustomerManagerPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;

/**
 * 
 * Title:SADCS060CloudImpl
 * Description: 试乘试驾分析数据上报
 * @author DC
 * @date 2017年4月10日 上午10:01:59
 * result msg 1：成功 0：失败
 */
@Service
public class SADCS060CloudImpl extends BaseCloudImpl implements SADCS060Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS060CloudImpl.class);
	
	@Autowired
	SaleVehicleSaleDao saleDao;

	@Override
	public String handleExecutor(List<SADCS060Dto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====一对一客户经理接口接收开始====");
		for (SADCS060Dto entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("投保单上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
			logger.info("====投保单上报接收结束====");
		}
		return msg;
		
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	private void insertData(SADCS060Dto vo) throws Exception {
		try {
			Map<String, Object> map = saleDao.getSaDcsDealerCode(vo.getDealerCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息

			TiWxCustomerManagerPO cusManagerPO = new TiWxCustomerManagerPO();
			if(null!=findById(vo,dealerCode)){
				Long updatedocumentId = findById(vo,dealerCode);
				cusManagerPO = TiWxCustomerManagerPO.findById(updatedocumentId);
				cusManagerPO = getPO(vo,dealerCode);
				cusManagerPO.setString("RESULT_VALUE", "update");
				cusManagerPO.setLong("UPDATE_BY", 999999999L);
				cusManagerPO.setDate("UPDATE_DATE", new Date());
				cusManagerPO.saveIt();
			} else {
				cusManagerPO = getPO(vo,dealerCode);
				cusManagerPO.setString("IS_SCAN", "0");
				cusManagerPO.setLong("CREATE_BY", 999999999L);
				cusManagerPO.setDate("CREATE_DATE", new Date());
				cusManagerPO.setInteger("IS_ARC", 0);
				cusManagerPO.setInteger("IS_DEL", 0);
				cusManagerPO.setInteger("VER", 0);
				cusManagerPO.setString("IS_UPDATE", "0");
				cusManagerPO.insert();// 新交车客户绑定客户经理接收
				logger.info("====一对一客户经理接口接收成功====");
			}
		
			
		} catch (Exception e) {
			logger.error("一对一客户经理接口接收失败", e);
			throw new Exception(e);
		}
		
	}

	@SuppressWarnings("rawtypes")
	private Long findById(SADCS060Dto vo, String dealerCode) throws Exception {
		Long documentId = 0L;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MANAGER_ID  \n");
			sql.append(" From TI_WX_CUSTOMER_MANAGER   ");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND VIN ='"+vo.getVin()+"' \n");
			sql.append(" AND DEALER_CODE ='").append(dealerCode).append("' \n");
			sql.append(" AND SERVICE_ADVISOR ='"+vo.getServiceAdviser()+"' \n");
			sql.append(" ORDER BY MANAGER_ID DESC");
			List<Map> list = OemDAOUtil.downloadPageQuery(sql.toString(), null);
			if(null!=list && list.size()>0){
				documentId = (Long) list.get(0).get("MANAGER_ID");
				return documentId;
			}
		} catch (Exception e) {
			logger.error("一对一客户经理校验数据失败", e);
			throw new Exception(e);
		}
		return null;
	}
	
	private TiWxCustomerManagerPO getPO(SADCS060Dto vo, String dealerCode){
		TiWxCustomerManagerPO cusManagerPO = new TiWxCustomerManagerPO();
		
		cusManagerPO.setString("CLIENT_TYPE", vo.getClientType());//客户类型（1：客户，2：公司）
		cusManagerPO.setString("NAME", vo.getName());//车主姓名
		cusManagerPO.setString("GENDER", vo.getGender());//车主性别
		cusManagerPO.setString("CELLPHONE", vo.getCellphone());//车主手机号/座机电话
		cusManagerPO.setString("PROVINCE_ID", vo.getProvinceId());//车主所在省
		cusManagerPO.setString("CITY_ID", vo.getCityId());//车主所在市
		cusManagerPO.setString("DISTRICT", vo.getDistrict());//车主所在区/县
		cusManagerPO.setString("ADDRESS", vo.getAddress());//车主地址
		cusManagerPO.setString("POST_CODE", vo.getPostCode());//车主所在地邮编
		cusManagerPO.setString("ID_OR_COMP_CODE", vo.getIdOrCompCode());//身份证号
		cusManagerPO.setString("EMAIL", vo.getEmail());//电子邮箱
		cusManagerPO.setString("DMS_OWNER_ID", vo.getDmsOwnerId());//车主编号
		cusManagerPO.setString("DEALER_CODE", dealerCode);//经销商代码
		cusManagerPO.setString("BUY_TIME", vo.getBuyTime());//交车时间(即保修登记上报时间)
		cusManagerPO.setString("VIN", vo.getVin());//车架号
		cusManagerPO.setString("SERVICE_ADVISOR", vo.getServiceAdviser());//客户经理ID
		cusManagerPO.setString("EMPLOYEE_NAME", vo.getEmployeeName());//客户经理姓名
		cusManagerPO.setString("MOBILE", vo.getMobile());//客户经理联系电话 
		
		String bindTime = vo.getWxBindTime();
        Date wxBindTime = DateUtil.parseDefaultDateTime(bindTime);
		cusManagerPO.setDate("WX_BIND_TIME", wxBindTime);//绑定时间
		
		return cusManagerPO;
	}

}
