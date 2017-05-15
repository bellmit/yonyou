package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.Date;
import java.util.List;
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
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WorkHourLevelDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWorrkhourLevelDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWorkhourLevelDcsPO;

@Service
public class WorkHourLevelServiceImpl implements WorkHourLevelService {
	@Autowired
	WorkHourLevelDao workHourLevelDao;
	
	@Override
	public PageInfoDto query(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = workHourLevelDao.query(queryParam);
		return pageInfoDto;
	}

	/**
	 * 新增
	 */
	@Override
	public Long add(TtWrWorrkhourLevelDTO dto) throws  ServiceBizException {
		TtWrWorkhourLevelDcsPO po = new TtWrWorkhourLevelDcsPO();
		setWorkhourLevelPo(po, dto);
		return po.getLongId();
	}
	
	private void setWorkhourLevelPo(TtWrWorkhourLevelDcsPO po, TtWrWorrkhourLevelDTO dto) {
		boolean flag = false;
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!StringUtils.isNullOrEmpty(dto.getWtId())) {
			LazyList<TtWrWorkhourLevelDcsPO> listPO = TtWrWorkhourLevelDcsPO.find("WT_ID = ?", dto.getWtId());
			if (listPO.size()>0) {
				throw new ServiceBizException("该保修类型已维护！新增失败！");
			}
			po.setLong("WT_ID", dto.getWtId());
			po.setString("WT_CODE", dto.getWtCode());
			po.setInteger("DER_LEVEL", dto.getDerLevel());
			po.setDouble("WORK_PRICE", dto.getWorkPrice());
			po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
			po.setString("REMARK", dto.getRemark());
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
	 * 保修类型修改
	 */
	@Override
	public void update(TtWrWorrkhourLevelDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrWorkhourLevelDcsPO po = TtWrWorkhourLevelDcsPO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				po.setDouble("WORK_PRICE", dto.getWorkPrice());
				po.setInteger("STATUS", dto.getStatus());
				po.setString("REMARK", dto.getRemark());
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
	
	/**
	 * 查询保修类型
	 */
	@Override
	public List<Map> getWarrantyType(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return workHourLevelDao.getWarrantyType(queryParams);
	}
}
