package com.yonyou.dms.vehicle.service.afterSales.priceLimit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmLimiteCposPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.afterSales.priceLimit.PriceLimiteManageDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TmLimiteCposDTO;

/**
 *  车系限价管理 
 * @author Administrator
 *
 */
@Service
public class PriceLimiteManageServiceImpl implements PriceLimiteManageService{
	@Autowired
	PriceLimiteManageDao  priceLimiteManageDao;
	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 车系限价管理 查询
	 */
	@Override
	public PageInfoDto PriceLimiteManageQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return priceLimiteManageDao.PriceLimiteManageQuery(queryParam);
	}

	/**
	 * 查询所有品牌代码
	 */
	@Override
	public List<Map> getBrandCode() {
		// TODO Auto-generated method stub
		return priceLimiteManageDao.getBrandCode();
	}

	//查询所有车系代码
	@Override
	public List<Map> getSeriesCode(String brandCode) {
		// TODO Auto-generated method stub
		return priceLimiteManageDao.getSeriesCode(brandCode);
	}

	/**
	 * 下载
	 */
	@Override
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = priceLimiteManageDao.download(queryParam);
		excelData.put("车系限价管理信息下载", (List<Map>) list);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("LIMITED_NAME", "限价名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("LIMITED_RANGE", "浮动比例%"));
		exportColumnList.add(new ExcelExportColumn("BRAND", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES", "车系"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_DESC", "维修类型"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建日期"));
		exportColumnList.add(new ExcelExportColumn("DESCEND_STATUS", "下发状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("DESCEND_DATE", "下发日期"));
		excelService.generateExcel(excelData, exportColumnList, "车系限价管理信息下载.xls", request, response);
		
	}

	/**
	 * 新增
	 */
	public Long add(TmLimiteCposDTO ptdto) {
		TmLimiteCposPO ptPo=new TmLimiteCposPO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
	}
	private void setApplyPo(TmLimiteCposPO ptPo, TmLimiteCposDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
		            throw new ServiceBizException("已存在此车系限价管理数据！新增失败！");
		        } 
		        else{
			   ptPo.setString("LIMITED_NAME", ptdto.getLimitedName());//现价名称
			   ptPo.setString("DEALER_CODE",ptdto.getDealerCode());//现价经销商
			   ptPo.setDouble("LIMITED_RANGE",ptdto.getLimitedRange());   //浮动比例 
			      String result2=null;
			      String seriseBrandStr ="" ;
				   List<Object> brandList= ptdto.getBrand();
				   for(int i=0;i<brandList.size();i++){
					   result2=(String) brandList.get(i);
						if(i ==brandList.size()-1){
							seriseBrandStr += result2+",";
						}else{
							seriseBrandStr += result2+",";
						}
				   }  
				   ptPo.setString("BRAND",seriseBrandStr);//品牌
				   String result=null;
				   String seriseCodeStr ="" ;
				   String seriseNameStr = "";
				   
	
				   
			  List<Object> seriesCodeList=ptdto.getSeriesCode();
			        for(int i=0;i<seriesCodeList.size();i++){
			        	result=(String) seriesCodeList.get(i);
			        	int fal = result.lastIndexOf("-");
			        	if(i ==seriesCodeList.size()-1){
				        	seriseNameStr += result.substring(0, fal);
				        	seriseCodeStr += result.substring(fal+1, result.length());
			        	}else{
				        	seriseNameStr += result.substring(0, fal)+",";
				        	seriseCodeStr += result.substring(fal+1, result.length())+",";
			        	}
			        }
				   ptPo.setString("SERIES_CODE",seriseCodeStr);//车系代码
			       ptPo.setString("SERIES",seriseNameStr);//车系-------
			   //通过维修类型编号查询维修类型	   
			   String result3=null;
			      String seriseRepairTypeStr ="" ;
				   List<Object> repairTypeList= ptdto.getRepairType();
				   for(int i=0;i<repairTypeList.size();i++){
					   result3=(String) repairTypeList.get(i);
						if(i ==repairTypeList.size()-1){
							seriseRepairTypeStr += result3;
						}else{
							seriseRepairTypeStr += result3+",";
						}
				   }
				   ptPo.setString("REPAIR_TYPE",seriseRepairTypeStr);//维修类型编号
		          Object result4=null;
			      String repairDescStr ="" ;
			      List<Map> repairList=getName(seriseRepairTypeStr);
				   for(int i=0;i<repairList.size();i++){
					   result4=repairList.get(i).get("CODE_DESC");	
					   if(i ==repairList.size()-1){
						   repairDescStr += result4;
						}else{
							repairDescStr += result4+",";
						}
				   }	   
			   ptPo.setString("REPAIR_DESC",repairDescStr);//维修类型-------
			   ptPo.setString("COMMENT",ptdto.getComment());//备注
			   ptPo.setInteger("DESCEND_STATUS",OemDictCodeConstants.COMMON_RESOURCE_STATUS_01);//未下发
			
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.saveIt();
		        }
	
	}
    public List<Map> getApplyDataBy(TmLimiteCposDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  SELECT DEALER_CODE  from TM_LIMITE_CPOS_dcs where 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and DEALER_CODE=?");
        params.add(ptdto.getDealerCode());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    }   

	/**
	 * 修改
	 */
	@Override
	public void edit(Long id, TmLimiteCposDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	  
		TmLimiteCposPO ptPo = TmLimiteCposPO.findById(id);
		   ptPo.setString("LIMITED_NAME", ptdto.getLimitedName());//现价名称
		   ptPo.setString("DEALER_CODE",ptdto.getDealerCode());//现价经销商
		   ptPo.setDouble("LIMITED_RANGE",ptdto.getLimitedRange());   //浮动比例 
		      String result2=null;
		      String seriseBrandStr ="" ;
			   List<Object> brandList= ptdto.getBrand();
			   for(int i=0;i<brandList.size();i++){
				   result2=(String) brandList.get(i);
					if(i ==brandList.size()-1){
						seriseBrandStr += result2;
					}else{
						seriseBrandStr += result2+",";
					}
			   }  
			
			   String result=null;
			   String seriseCodeStr ="" ;
			   String seriseNameStr = "";
			   
			   if(ptdto.getSeriesCode()!=null){
				   List<Object> seriesCodeList=ptdto.getSeriesCode();
			        for(int i=0;i<seriesCodeList.size();i++){
			        	result=(String) seriesCodeList.get(i);
			        	int fal = result.lastIndexOf("-");
			        	if(i ==seriesCodeList.size()-1){
				        	seriseNameStr += result.substring(0, fal);
				        	seriseCodeStr += result.substring(fal+1, result.length());
			        	}else{
				        	seriseNameStr += result.substring(0, fal)+",";
				        	seriseCodeStr += result.substring(fal+1, result.length())+",";
			        	}
			        }
				   ptPo.setString("SERIES_CODE",seriseCodeStr);//车系代码
			       ptPo.setString("SERIES",seriseNameStr);//车系-------
			       ptPo.setString("BRAND",seriseBrandStr);//品牌
			   }
			   
		
		   //通过维修类型编号查询维修类型	   
		   String result3=null;
		      String seriseRepairTypeStr ="" ;
			   List<Object> repairTypeList= ptdto.getRepairType();
			   for(int i=0;i<repairTypeList.size();i++){
				   result3=(String) repairTypeList.get(i);
					if(i ==repairTypeList.size()-1){
						seriseRepairTypeStr += result3;
					}else{
						seriseRepairTypeStr += result3+",";
					}
			   }
			   ptPo.setString("REPAIR_TYPE",seriseRepairTypeStr);//维修类型编号
	          Object result4=null;
		      String repairDescStr ="" ;
		      List<Map> repairList=getName(seriseRepairTypeStr);
			   for(int i=0;i<repairList.size();i++){
				   result4=repairList.get(i).get("CODE_DESC");	
				   if(i ==repairList.size()-1){
					   repairDescStr += result4;
					}else{
						repairDescStr += result4+",";
					}
			   }	   
		   ptPo.setString("REPAIR_DESC",repairDescStr);//维修类型-------
		   ptPo.setString("COMMENT",ptdto.getComment());//备注
		   ptPo.setInteger("DESCEND_STATUS",OemDictCodeConstants.COMMON_RESOURCE_STATUS_01);//未下发
		
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.saveIt();
		
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(Long id) {
		TmLimiteCposPO ptPo = TmLimiteCposPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	   	}
		
	}

	/**
	 * 下发
	 */
	@Override
	public void xiafa(Long id) {
		TmLimiteCposPO ptPo = TmLimiteCposPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setLong("DESCEND_STATUS",OemDictCodeConstants.COMMON_RESOURCE_STATUS_02);
		   ptPo.saveIt();
	   	}
	}
	 //通过维修类型编号查询维修类型
	  public List<Map> getName(String  seriseRepairTypeStr) throws ServiceBizException {
	        StringBuilder sqlSb = new StringBuilder("  SELECT CODE_DESC  from tc_code_dcs where 1=1");
	        sqlSb.append("   and CODE_ID  IN ("+(seriseRepairTypeStr)+")  ");
	        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), null);
	        return applyList;
	    }

	@Override
	public TmLimiteCposPO getTmLimiteById(Long id) {
		// TODO Auto-generated method stub
		return TmLimiteCposPO.findById(id);
	}

/*	@Override
	public List<Map> getSeriesCode2(String brandCode) {
		// TODO Auto-generated method stub
		return priceLimiteManageDao.getSeriesCode2(brandCode);
	}*/


	  /**
	   * 修改时的信息回显
	   */
/*	public Map getTmLimiteById(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=priceLimiteManageDao.getPriceLimite(id);
		 m= list.get(0);
		 return m;
	}*/
	

}
