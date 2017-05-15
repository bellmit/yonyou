package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dms.DTO.gacfca.ProperServiceManageDto;
import com.yonyou.dms.common.domains.PO.basedata.TiWxCustomerManagerPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class SADCS018CloudImpl extends BaseCloudImpl implements SADCS018Cloud {
	private static final Logger logger = LoggerFactory.getLogger(DCSBI001CloudImpl.class);

	@Autowired
	SaleVehicleSaleDao saleDao;

	@Override
	public String handleExecutor(List<ProperServiceManageDto> dto) throws Exception {
		String msg = "1";
		logger.info("====交车客户、客户经理重绑接收开始====");
		try {
			for (ProperServiceManageDto vo : dto) {

				insertData(vo);
			}
		} catch (Exception e) {
			logger.error("交车客户、客户经理重绑接收失败", e);
			msg = "0";
			throw new ServiceBizException(e);

		}

		logger.info("====交车客户、客户经理重绑接收结束====");
		return msg;
	}

	private void insertData(ProperServiceManageDto vo) {

		try {
			Map<String, Object> map = saleDao.getSaDcsDealerCode(vo.getDealerCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息

			// String mngId = SequenceManager.getSequence("");
			TiWxCustomerManagerPO cusManagerPO = new TiWxCustomerManagerPO();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String s = df.format(new Date());

			// if(isVinManagerExist(vo,dealerCode)){
			if (null != findById(vo, dealerCode)) {
				Long updatedocumentId = findById(vo, dealerCode);
				StringBuffer sql=new StringBuffer("");
				sql.append("UPDATE TI_WX_CUSTOMER_MANAGER SET Is_Scan='"+0+"',RESULT_VALUE='update',IS_UPDATE='1',");
				sql.append(" UPDATE_BY='"+999999999L+"',UPDATE_DATE='"+s+"',VIN='"+vo.getVin()+"',DEALER_CODE='"+dealerCode+"',SERVICE_ADVISOR='"+vo.getServiceAdviser()+"',");
				
				if (CommonUtils.checkIsNull(vo.getEmployeeName())) {
					sql.append("Employee_Name='',");
				} else {
					sql.append("Employee_Name='"+vo.getEmployeeName()+"',");
				}

				if (CommonUtils.checkIsNull(vo.getMobile())) {
					sql.append("Mobile='',");
				} else {
					sql.append("Mobile='"+vo.getMobile()+"',");
				}
				sql.append("DISPATCH_TIME='"+vo.getDispatchTime()+"',");// 分派时间

				if (CommonUtils.checkIsNull(vo.getDmsOwnerId())) {
					sql.append("DMS_OWNER_ID='',");
				} else {
					sql.append("DMS_OWNER_ID='"+vo.getDmsOwnerId()+"',");// 车主编号
				}

				if (CommonUtils.checkIsNull(vo.getCellphone())) {
					sql.append("CELLPHONE='',");
				} else {
					sql.append("CELLPHONE='"+vo.getCellphone()+"',");// 车主手机号/座机电话
				}

				if (CommonUtils.checkIsNull(vo.getName())) {
					sql.append("Name=''");
				} else {
					sql.append("Name='"+vo.getName()+"'");// 车主姓名
				}
				sql.append(" where MANAGER_ID='"+updatedocumentId+"'");
				Base.exec(sql.toString());
				logger.info("====交车客户、客户经理重绑接收成功====");
			} else {
				cusManagerPO.setString("Is_Scan", "0");
				cusManagerPO.setString("Create_By", 999999999L);
				cusManagerPO.setTimestamp("Create_Date",s );
				cusManagerPO.setInteger("Is_Arc", 0);
				cusManagerPO.setInteger("Is_Del", 0);
				cusManagerPO.setInteger("Ver", 0);
				cusManagerPO.setString("Is_Update", "1");
				// cusManagerPO.saveIt();
				cusManagerPO.setString("VIN", vo.getVin());
				cusManagerPO.setString("DEALER_CODE", dealerCode);
				cusManagerPO.setString("SERVICE_ADVISOR", vo.getServiceAdviser());

				if (CommonUtils.checkIsNull(vo.getEmployeeName())) {
					cusManagerPO.setString("Employee_Name", "");
				} else {
					cusManagerPO.setString("Employee_Name", vo.getEmployeeName());
				}

				if (CommonUtils.checkIsNull(vo.getMobile())) {
					cusManagerPO.setString("Mobile", "");
				} else {
					cusManagerPO.setString("Mobile", vo.getMobile());
				}
				cusManagerPO.setTimestamp("DISPATCH_TIME", vo.getDispatchTime());// 分派时间

				if (CommonUtils.checkIsNull(vo.getDmsOwnerId())) {
					cusManagerPO.setString("DMS_OWNER_ID", "");
				} else {
					cusManagerPO.setString("DMS_OWNER_ID", vo.getDmsOwnerId());// 车主编号
				}

				if (CommonUtils.checkIsNull(vo.getCellphone())) {
					cusManagerPO.setString("CELLPHONE", "");
				} else {
					cusManagerPO.setString("CELLPHONE", vo.getCellphone());// 车主手机号/座机电话
				}

				if (CommonUtils.checkIsNull(vo.getName())) {
					cusManagerPO.setString("Name", "");
				} else {
					cusManagerPO.setString("Name", vo.getName());// 车主姓名
				}
				cusManagerPO.saveIt();
			}
			

		} catch (Exception e) {
			logger.error("交车客户、客户经理重绑接收失败", e);
			throw new ServiceBizException(e);
		}

	}

	private Long findById(ProperServiceManageDto vo, String dealerCode) {

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MANAGER_ID  \n");
			sql.append(" From TI_WX_CUSTOMER_MANAGER   ");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND VIN ='" + vo.getVin() + "' \n");
			sql.append(" AND DEALER_CODE ='").append(dealerCode).append("' \n");
			sql.append(" ORDER BY MANAGER_ID DESC  ");
			List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
			Long documentId = 0L;
			if (null != list && list.size() > 0) {
				Map map=list.get(0);
				documentId = (Long) map.get("MANAGER_ID");
				return documentId;
			}
		} catch (Exception e) {
			logger.error("交车客户、客户经理重绑校验数据失败", e);
			throw new ServiceBizException(e);
		}
		return null;

	}

}
