package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.OwnerEntityDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS114Dao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS114Dao.class);

	/**
	 * 查询数据
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unused")
	public OwnerEntityDTO queryOwnerVehicleVO(OwnerEntityDTO dto) {
		String vin = CommonUtils.checkNull(dto == null ? "" : dto.getVin());
		String entityCode = CommonUtils.checkNull(dto == null ? "" : dto.getEntityCode());
		String dealerCode="";
		OwnerEntityDTO vehicle = new OwnerEntityDTO();
		if ("".equals(vin)) {
			logger.info("VIN参数为空！");
			vehicle.setVin("");
			vehicle.setEntityCode(entityCode);
			vehicle.setIsOwner(12781002);
			return vehicle;
		}
		if ("".equals(entityCode)) {
			logger.info("entityCode参数为空！");
			vehicle.setVin(vin);
			vehicle.setEntityCode("");
			vehicle.setIsOwner(12781002);
			return vehicle;
		}
		//获取经销商信息
		try {
			Map<String, Object> dcsInfoMap = getSaDcsDealerCode(dto.getEntityCode());
		    dealerCode = String.valueOf(dcsInfoMap.get("DEALER_CODE"));//经销商信息
		} catch (Exception e) {
			logger.info("获取经销信息报错");
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT td.DEALER_CODE,tv.VIN from TM_VEHICLE tv left join TM_DEALER td on tv.DEALER_ID=td.DEALER_ID \n");
        sql.append("where tv.VIN='"+dto.getVin()+"'\n");
        sql.append(" and td.DEALER_CODE='"+dealerCode+"'\n");
        List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
        List<OwnerEntityDTO> dtolist=null;
        if(null!=listmap&&listmap.size()>0){
        	for(Map map:listmap){
        		OwnerEntityDTO dtos = new OwnerEntityDTO();
        		dtos.setEntityCode(CommonUtils.checkNull(map.get("DEALER_CODE")));
				dtos.setVin(CommonUtils.checkNull(map.get("VIN")));
				dtolist.add(dtos);
        	}
        }
        int size=dtolist==null?0:dtolist.size();
		logger.info("返回list的大小："+size);
		if (dtolist == null || dtolist.size() <= 0) {
			logger.info("SEDCS114同步接口没有找到VIN：" + vin + " 和entityCode："+entityCode+"对应的车辆信息！");
			vehicle.setIsOwner(12781002);
		}else {
			logger.info("SEDCS114同步接口存在vin:"+vin+"和entityCode："+entityCode+"对应的车辆");
			vehicle.setIsOwner(12781001);
		}
		vehicle.setVin(vin);
		vehicle.setEntityCode(entityCode);
		return vehicle;
	}
	
}
