package com.yonyou.dms.vehicle.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.InspectionAboutDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInImportDTO;

@SuppressWarnings("rawtypes")
public interface StockInService {
    
    /**
     * 导入方法
    * TODO description
    * @author yangjie
    * @date 2017年2月6日
    * @param dto
    * @throws ServiceBizException
     */
    void addInfo(StockInImportDTO dto) throws ServiceBizException;
    
    /**
     * 导出方法
    * TODO description
    * @author yangjie
    * @date 2017年2月6日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    List<Map> queryStockInForExport(Map<String, String> queryParam) throws ServiceBizException;

    /**
    * @author yangjie
    * @date 2017年1月20日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.basedata.StockInService#QueryStockIn(java.util.Map)
    */
    PageInfoDto QueryStockIn(Map<String, String> queryParam) throws ServiceBizException;

    /**
     * @author yangjie
     * @date 2017年1月19日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.basedata.StockInService#QueryStockInDetails(java.util.Map)
     */
    List<Map> queryStockInDetails(Map<String, String> queryParam) throws ServiceBizException;

    /**
    * @author yangjie
    * @date 2017年1月20日
    * @param query
    * @param flag
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.basedata.StockInService#findAllVehicle(java.util.Map, java.lang.Boolean)
    */
    PageInfoDto findAllVehicle(Map<String, String> query, Boolean flag);

    /**
    * @author yangjie
    * @date 2017年1月20日
    * @param param
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.basedata.StockInService#btnSave(com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInDTO)
    */
    StockInDTO btnSave(StockInDTO param) throws ServiceBizException;

    /**
     * 查询入库信息 (验证时)
    * TODO description
    * @author yangjie
    * @date 2017年1月22日
    * @param vin
    * @return
     */
    Map findStockInInfo(String vin) ;
    
    
    Map findByVinAndSeNo(String vin,String seNo) throws ServiceBizException;
    
    /**
     * 查询所有质损信息
    * TODO description
    * @author yangjie
    * @date 2017年1月22日
    * @param vin
    * @return
     */
    List<Map> findAllInpectionList(String vin);
    
    /**
     * 删除质损信息
    * TODO description
    * @author yangjie
    * @date 2017年1月22日
    * @param itemId
    * @throws ServiceBizException
     */
    void delInpectionInfo(String itemId) throws ServiceBizException;
    
    /**
     * 删除入库单明细
     * @param seNo
     * @param vin
     * @throws ServiceBizException
     */
    void delDetailsInfo(String seNo,String vin) throws ServiceBizException;
    
    /**
     * 新增或修改质损信息
    * TODO description
    * @author yangjie
    * @date 2017年1月22日
    * @param dto
    * @throws ServiceBizException
     */
    void addOrEditInpectionInfo(InspectionAboutDTO dto) throws ServiceBizException;

    /**
     * 入库操作
    * TODO description
    * @author yangjie
    * @date 2017年2月4日
    * @param items
    * @param seNo
    * @param inType
    * @param sheetCreatedBy
    * @throws ServiceBizException
     */
    void btnStockIn(List<Map> items,String seNo,String inType, String sheetCreatedBy) throws ServiceBizException;
    
    /**
     * 查询仓库代码
     * @param storageName
     * @return
     */
    List<Map> findStorageCode();
    
    /**
     * 批量修改用,查询入库子表
     * @param query
     * @return
     */
    PageInfoDto findAllDetailsForInspect(Map<String, String> query);
    
    /**
     * 批量验收
     * @param map
     * @throws ServiceBizException
     */
    void btnAllInspect(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 批量入库查询
     * @param queryParam
     * @return
     */
	List<Map> queryStockInDetailsForBatch(Map<String, String> queryParam);

	 /**
     * 打印查询
     * @param map
     * @return
     */
	List<Map> findPrintAbout(String seNo, String vins);

	/**
	 * 查询排放标准
	 * @return
	 */
	List<Map> findDischargeStandard();

	/**
	 * PDI校验
	 * @param vin
	 */
	String checkPDI(String vin,String productCode);

	/**
	 * 查询产品名称
	 * @param vin
	 * @param prodectCode
	 */
	String findProductCode(String vin, String prodectCode);
}
