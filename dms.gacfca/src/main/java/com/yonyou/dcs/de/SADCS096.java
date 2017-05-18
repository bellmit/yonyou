package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.BigCustomerAuthorityApprovalVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS096Cloud;
import com.yonyou.dms.DTO.gacfca.BigCustomerAuthorityApprovalDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * 大客户组织架构权限审批数据接收
 * @author Benzc
 * @date 5月15日
 */
@Service
public class SADCS096 extends BaseImpl implements DEAction{
    
	@Autowired
	SADCS096Cloud Cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS096.class);
	
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("====获取大客户组织架构权限上传开始(SADCS096)====");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			List<BigCustomerAuthorityApprovalDto> dtoList = new ArrayList<>();
			setDTO(dtoList,bodys);
			Cloud.handleExecutor(dtoList);
		} catch(Throwable t) {
			logger.info("====获取大客户组织架构权限上传异常(SADCS096)====");
			t.printStackTrace();
		} finally {
		}
		logger.info("====获取大客户组织架构权限上传结束(SADCS096)====");
		return null;
	}
	
	private void setDTO(List<BigCustomerAuthorityApprovalDto> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			BigCustomerAuthorityApprovalVO vo = new BigCustomerAuthorityApprovalVO();
			BigCustomerAuthorityApprovalDto dto = new BigCustomerAuthorityApprovalDto();
			vo = (BigCustomerAuthorityApprovalVO) entry.getValue();
			dto.setEntityCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
		
	}

}
