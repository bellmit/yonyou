package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.VehicleCustomerDTO;


/**
 * @Description: 车主资料接收接口
 * 接收经销商修改后的车主资料 
 * @author xuqinqin
 */
public interface SADCS073Cloud  extends BaseCloud{
	public String handleExecutor(List<VehicleCustomerDTO> dtos) throws Exception;
}
