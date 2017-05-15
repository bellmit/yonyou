package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpGcsImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpGcsImpDTO;

public interface GcsBaoDanManageService {

	void insertTmpVsProImpAudit(TmpGcsImpDTO rowDto);

	ImportResultDto<TmpGcsImpDTO> checkData(TmpGcsImpDTO rowDto) throws Exception ;

	List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam);

	List<Map> mappingOldPart(String string, String claimNumber, String string2);

	void setImpPO(TtOpGcsImpDTO po);
	//下载
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception;
	//测试
	public PageInfoDto findGcs(Map<String, String> queryParam);


}
