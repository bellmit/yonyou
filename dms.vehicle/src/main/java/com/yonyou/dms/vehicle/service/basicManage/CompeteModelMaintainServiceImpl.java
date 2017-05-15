package com.yonyou.dms.vehicle.service.basicManage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmCompeteBrandPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.basicManage.CompeteModelMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.basicManage.TmCompeteBrandDTO;

@Service
public class CompeteModelMaintainServiceImpl implements CompeteModelMaintainService {

	@Autowired
	CompeteModelMaintainDao dao;
	
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam);
		return pgInfo;
	}

	/**
	 * 品牌新增
	 */
	@Override
	public Long addCompeteModelMaintain(TmCompeteBrandDTO tcbDto) throws ServiceBizException {
		TmCompeteBrandPO tcbPO = new TmCompeteBrandPO();
		//设置对象属性
		Boolean flag = dao.checkCode(tcbDto);
		if(!flag){
			throw new ServiceBizException("品牌代码已存在，请重新输入！");
		}
		setPO(tcbPO,tcbDto,1);
		tcbPO.saveIt();
		Long billId = tcbPO.getLongId();
		return billId;
	}

	/**
	 * 品牌修改
	 */
	@Override
	public void ModifyCompeteModelMaintain(Long id, TmCompeteBrandDTO tcbDto) throws ServiceBizException {
		TmCompeteBrandPO po = TmCompeteBrandPO.findById(id);
		setPO(po,tcbDto,2);
	    po.saveIt();
	}
	/**
	 * 数据映射
	 * @param mgPO
	 * @param mgDto
	 */
	private void setPO(TmCompeteBrandPO tcbPO, TmCompeteBrandDTO tcbDto,int type) {
		tcbPO.setString("BRAND_NAME", tcbDto.getBrandName());
		tcbPO.setString("REMARK", tcbDto.getRemark());
		tcbPO.setInteger("STATUS", tcbDto.getStatus());
	}

	/**
	 * 根据ID获取详细信息
	 */
	@Override
	public Map<String, Object> queryDetail(Long id) throws ServiceBizException {
		Map<String, Object> map = dao.queryDetail(id);
		return map;
	}

}
