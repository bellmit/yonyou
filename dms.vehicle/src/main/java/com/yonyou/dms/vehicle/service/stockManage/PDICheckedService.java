package com.yonyou.dms.vehicle.service.stockManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.PDICheckedDTO;

/**
 * PDI检查
 * @author Wangliang
 * @date 2017年1月11日
 */
@SuppressWarnings("rawtypes")
public interface PDICheckedService {

   public PageInfoDto queryPDIChecked(Map<String, String> queryParam) throws ServiceBizException;

   public  void update(String vin, PDICheckedDTO pdiCheckeddto) throws ServiceBizException;

   public Map editSearch(String vin);

   public List<Map> queryPDICheckedExport(Map<String, String> queryParam);

}
