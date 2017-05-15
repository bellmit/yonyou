package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartRuleDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpOldpartImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpUrgencyVinImpDTO;

public interface OldPartRuleMaintainService {

	public PageInfoDto getQuerySql(Map<String, String> queryParam);

	public Long addTmOldpartRule(TmOldpartRuleDTO tcDto);

	public void modifyTmOldpartRule(Long id, TmOldpartRuleDTO tcDto)  throws Exception;

	public void insertTmpVsYearlyPlan(TmpOldpartImpDTO rowDto);

	public ImportResultDto<TmpOldpartImpDTO> checkData(TmpOldpartImpDTO rowDto) throws Exception;

	public void save(Map<String, String> queryParam);

	public void insertvin(TmpUrgencyVinImpDTO rowDto);

	public ImportResultDto<TmpUrgencyVinImpDTO> checkDatavin(TmpUrgencyVinImpDTO rowDto) throws Exception;

	public void savevin(Map<String, String> queryParam);

	public List<Map> oemSelect(Map<String, String> queryParam);

	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam);

	public void deleteVinById(Long id);
	
	public void deleteChargeById(Long id);


}
