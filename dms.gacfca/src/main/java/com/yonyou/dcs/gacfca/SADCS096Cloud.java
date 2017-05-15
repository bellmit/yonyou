package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.BigCustomerAuthorityApprovalDto;

/**
 * 
 * Title:SADCS096Cloud
 * Description: 大客户组织架构权限审批数据接收
 * @author DC
 * @date 2017年4月10日 下午6:28:05
 * result msg 1：成功 0：失败
 */
public interface SADCS096Cloud {
	public String handleExecutor(List<BigCustomerAuthorityApprovalDto> dtoList) throws Exception;

}
