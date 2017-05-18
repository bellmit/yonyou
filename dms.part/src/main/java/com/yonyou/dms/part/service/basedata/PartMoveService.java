package com.yonyou.dms.part.service.basedata;


import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.part.domains.DTO.basedata.PartItemDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartMoveInfoDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartMoveStorageDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartStockInfoDTO;

public interface PartMoveService {
	public PageInfoDto getPartMoveInfos(String transferNo);

	public PageInfoDto queryPartInfos(String transferNo);

	public PageInfoDto queryPartItem(PartItemDTO partItemDTO);
	
	public PageInfoDto queryPartReplace(String partNo);

	public String checkStorageMove(String oldStorageCode, String newStorageCode);

	public PageInfoDto queryPartItemInfos(String partNo, String storageCode);

	public String addPartItemMove(PartMoveStorageDTO partMoveStorageDTO);

	public String delPartItemMove(String transferNo);

	public Map<String, Object> partOutStoragr(String transferNo) throws Exception;

	public Map printPartMoveTitle(String transferNo);

	public List<Map> printPartMoveInfo(String transferNo);

	public PageInfoDto queryPartStockItem(String oldStorageCode,String newStorageCode);
}
