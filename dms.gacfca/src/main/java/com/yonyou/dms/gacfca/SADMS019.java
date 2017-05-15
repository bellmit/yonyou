package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.ProperServManInfoDTO;
import com.yonyou.dms.DTO.gacfca.SADMS019DTO;

/**
 * @description 将符合条件的客户经理上传至微信平台 (特定字段发生变动则上传)
 * @author Administrator
 *
 */
public interface SADMS019 {
	LinkedList<ProperServManInfoDTO> getSADMS019(SADMS019DTO sadms019DTO);
}
