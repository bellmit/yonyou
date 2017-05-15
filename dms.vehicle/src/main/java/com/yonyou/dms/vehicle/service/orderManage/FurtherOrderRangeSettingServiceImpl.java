package com.yonyou.dms.vehicle.service.orderManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.factoryOrderDao.FurtherOrderRangeSetDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.FurtherOrderRangeSettingListDTO;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtVsResourceRangePO;

@Service
public class FurtherOrderRangeSettingServiceImpl implements FurtherOrderRangeSettingService {
	@Autowired
	private FurtherOrderRangeSetDao dao;

	@Override
	public void settingRange(FurtherOrderRangeSettingListDTO paramList) {
		String[] nodes = paramList.getNodes().split(",");
		TtVsResourceRangePO.deleteAll();
		for (int i = 0; i < nodes.length; i++) {
			TtVsResourceRangePO po = new TtVsResourceRangePO();
			po.setInteger("NODE_STATUS", Integer.parseInt(nodes[i]));
			boolean flag = po.saveIt();
			System.out.println(flag);
		}
		// for (String node : nodes) {
		// po.setInteger("NODE_STATUS", Integer.parseInt(node));
		// po.saveIt();
		// }
	}

	@Override
	public Map<String, Object> queryInit() {
		Map<String, Object> m = new HashMap();
		List<Map> list = dao.queryInit();
		String codeId = "";
		for (Map map : list) {
			Integer st = (Integer) map.get("IS_CHECK");
			if (st == 1) {
				if (codeId.equals("")) {
					codeId = CommonUtils.checkNull(map.get("CODE_ID"));
				} else {
					codeId = codeId + "," + CommonUtils.checkNull(map.get("CODE_ID"));
				}
			}

		}
		m.put("CODE_ID", codeId);

		return m;
	}

}
