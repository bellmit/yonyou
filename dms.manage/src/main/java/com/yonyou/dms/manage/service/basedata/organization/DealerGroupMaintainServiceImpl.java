package com.yonyou.dms.manage.service.basedata.organization;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.organization.DealerGroupMaintainDao;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.DealerGroupDTO;
import com.yonyou.dms.manage.domains.PO.basedata.TmDealerGroupPO;

@Service
public class DealerGroupMaintainServiceImpl implements DealerGroupMaintainService {
	
	@Autowired
	private DealerGroupMaintainDao dealerDao;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = dealerDao.search(param);
		return dto;
	}

	@Override
	public void addDealerGroup(DealerGroupDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		try {			
			TmDealerGroupPO po = new TmDealerGroupPO();
			po.setString("GROUP_CODE", dto.getGroupCode());
			po.setString("GROUP_NAME", dto.getGroupName());
			po.setString("GROUP_SHOTNAME", dto.getGroupShortName());
			po.setInteger("STATUS", dto.getStatus());
			po.setLong("CREATE_BY", loginUser.getUserId());
			po.setTimestamp("CREATE_DATE", currentTime);
			boolean flag = po.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("数据库插入数据失败！");
		}
		
	}

	@Override
	public DealerGroupDTO editDealerGroupInit(String groupId) {
		DealerGroupDTO dto = new DealerGroupDTO();
		TmDealerGroupPO po = TmDealerGroupPO.findById(groupId);
		dto.setGroupId(po.getLongId());
		dto.setGroupCode(po.getString("GROUP_CODE"));
		dto.setGroupName(po.getString("GROUP_NAME"));
		dto.setGroupShortName(po.getString("GROUP_SHOTNAME"));
		dto.setStatus(po.getInteger("STATUS"));
		return dto;
	}

	@Override
	public void editDealerGroup(DealerGroupDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		try {			
			TmDealerGroupPO po = TmDealerGroupPO.findById(dto.getGroupId());
			po.setString("GROUP_CODE", dto.getGroupCode());
			po.setString("GROUP_NAME", dto.getGroupName());
			po.setString("GROUP_SHOTNAME", dto.getGroupShortName());
			po.setInteger("STATUS", dto.getStatus());
			po.setLong("UPDATE_BY", loginUser.getUserId());
			po.setTimestamp("UPDATE_DATE", currentTime);
			boolean flag = po.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("数据库插入数据失败！");
		}
		
	}

}
