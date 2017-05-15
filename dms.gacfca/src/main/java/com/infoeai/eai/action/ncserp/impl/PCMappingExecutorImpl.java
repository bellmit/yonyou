package com.infoeai.eai.action.ncserp.impl;
/**
 * @author huyushan
 * @date 2015-02-03
 * PC Data
 * 
 */

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.dao.ncserp.ProductManageDao;
import com.infoeai.eai.po.TiColoriEstemiVeicoloPO;
import com.infoeai.eai.po.TiCoppieColoriVeicoloPO;
import com.infoeai.eai.po.TiOptVeicoloPO;
import com.infoeai.eai.po.TiSellerieVeicoloPO;
import com.infoeai.eai.po.TiVeicoliPO;
import com.infoeai.eai.po.TmPcBrandMapDcsPO;
import com.infoeai.eai.po.TmPcSeriesMapDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialGroupRPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialPO;
import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class PCMappingExecutorImpl    {
	private static Logger logger = LoggerFactory.getLogger(PCMappingExecutorImpl.class);
	Calendar sysDate = Calendar.getInstance();
	@Autowired
	ProductManageDao dao = new ProductManageDao();
	public static String standardOption = "";
	public static String factoryOptions = "";
	public static String dcsSeriesCode = "";
	
	
	/**
	 * 功能说明:CP映射主逻辑
	 * 创建人: HYS 
	 * 创建日期: 2015-03-17
	 * parameters：null
	 * @return
	 */
	public void pcMappingDCS()throws Exception{
		try{
			//品牌映射
			String brandResult = null;
			logger.info("=================PC映射开始====================");
			List<TiVeicoliPO> brandList = TiVeicoliPO.find(" IS_MAPPING = ? ", "0");
			TiVeicoliPO brandPo = null;
			if(brandList != null && brandList.size() > 0){
				for(int i = 0; i<brandList.size();i++){
					brandPo = brandList.get(i);
					String codVehicleBra = brandPo.getString("COD_VEHICLE_BRA");
					brandResult = isMappingBrand(codVehicleBra);
					if("01".equals(brandResult)){//处理没有固定值映射的品牌
						TiVeicoliPO tiveicoli  = TiVeicoliPO.findFirst(" COD_VEHICLE_BRA = ? and IS_MAPPING = ? ", codVehicleBra, "0") ;
						tiveicoli.setString("IS_MAPPING","1");
						tiveicoli.setString("REMARK","该品牌在映射关系中不存在");
						tiveicoli.setTimestamp("UPDATE_DATE",sysDate.getTime());
						tiveicoli.saveIt();
						
		    			TiOptVeicoloPO tioptveicoli = TiOptVeicoloPO.findFirst(" COD_VEHICLE_BRA = ? and IS_MAPPING = ? ", codVehicleBra, "0");
		    			tioptveicoli.setString("IS_MAPPING","1");
		    			tioptveicoli.setString("REMARK","该品牌在映射关系中不存在");
		    			tioptveicoli.setTimestamp("UPDATE_DATE",sysDate.getTime());
		    			tioptveicoli.saveIt();
		    			
		    			TiCoppieColoriVeicoloPO ColoriVeicolo = TiCoppieColoriVeicoloPO.findFirst(" COD_VEHICLE_BRA = ? and IS_MAPPING = ? ", codVehicleBra, "0");
		    			ColoriVeicolo.setString("IS_MAPPING","1");
		    			ColoriVeicolo.setString("REMARK","该品牌在映射关系中不存在");
		    			ColoriVeicolo.setTimestamp("UPDATE_DATE",sysDate.getTime());
		    			ColoriVeicolo.saveIt();
					}
				}
			}
			
			//车系映射
			String seriesResult = null;
			TiVeicoliPO seriesPo = null;
	        List<TiVeicoliPO> seriesList = TiVeicoliPO.find(" IS_MAPPING = ? ","0");
	        logger.info("======PC查询车系未映射记录======list:"+seriesList.size());
	        if(seriesList != null && seriesList.size() > 0){
	        	for(int i = 0; i<seriesList.size();i++){
	        	    seriesPo = seriesList.get(i);
	        		seriesResult = isMappingSeries(seriesPo);
					if("01".equals(seriesResult)){//处理没有固定值映射的车系
						seriesPo.setString("IS_MAPPING","1");
						seriesPo.setString("REMARK","该车系在映射关系中不存在");
						seriesPo.setTimestamp("UPDATE_DATE",sysDate.getTime());
						seriesPo.saveIt();
					}
	        	}
	        }
	
		  //车型映射
	       String modelResult = null;
	       TiVeicoliPO modelPo = null;
	        List<TiVeicoliPO> modelList = TiVeicoliPO.find(" IS_MAPPING = ? ", "0");
	        if(modelList != null && modelList.size() > 0){
	        	for(int i = 0; i<modelList.size();i++){
	        	    modelPo = modelList.get(i);
	        		modelResult = isMappingModel(modelPo);
	        		if("01".equals(modelResult)){
						modelPo.setString("IS_MAPPING","1");
						modelPo.setString("REMARK","该车型在产品主数据中不存在");
						modelPo.setTimestamp("UPDATE_DATE",sysDate.getTime());
						modelPo.saveIt();
	        		}else{
	        			modelPo.setString("IS_MAPPING","1");
	        			modelPo.setTimestamp("UPDATE_DATE",sysDate.getTime());
	        			modelPo.saveIt();
	        		}
	        	}
	        }
			
	      //车款映射
	      String packageResult = null;
	      List<TiOptVeicoloPO> packageList = TiOptVeicoloPO.find(" IS_MAPPING = ? ", "0");
//	    		  searchPackageList();
	      TiOptVeicoloPO packagePo = null;
	      if(packageList != null && packageList.size() > 0){
	    	  for(int i = 0; i< packageList.size();i++){
	    		  packagePo = packageList.get(i);
	    		  String  codVehicle= packagePo.getString("COD_VEHICLE");
	    		  //根据codeVehicle去表中查询到VehicleDescription
	    		  String vehicleDes = searchVehicleDesByCodVehicle(codVehicle);
	    		  String remark = null;
	    		  if(vehicleDes == null || vehicleDes.length() <= 0){
	    			  remark = "根据codVehicle:"+codVehicle+"未查询VehicleDes，车款映射失败";
	    			  logger.info("======PC车款映射======根据codVehicle:"+codVehicle+"未查询VehicleDes，车款映射失败======");
	    		  }else{
	    			  if(codVehicle != null && codVehicle.length() > 0){
	    				  packageResult = isMappingPackage(codVehicle,vehicleDes);
			    		  if("01".equals(packageResult)){
			    			  remark = "该车款的车型在产品主数据中不存在，车款映射失败";
			    			  logger.info("======PC车款映射======该车款的车型在产品主数据中不存在，车款映射失败======");
			    		  }else if("03".equals(packageResult)){
			    			  remark = "该车款代码已存在，车款映射失败";
			    			  logger.info("======PC车款映射======该车款代码已存在，车款映射失败======");
			    		  }
	    			  }else{
	    				  remark = "该车款的codVehicle为空，车款映射失败";
	    				  logger.info("======PC车款映射======该车款的codVehicle为空，车款映射失败======");
	    			  }
	    		  }
	    		  TiOptVeicoloPO tioptveicoli = TiOptVeicoloPO.findFirst(" COD_VEHICLE = ? and IS_MAPPING = ? ", codVehicle, "0");
    			  tioptveicoli.setString("IS_MAPPING","1");
    			  tioptveicoli.setString("REMARK",remark);
    			  tioptveicoli.setTimestamp("UPDATE_DATE",sysDate.getTime());
    			  tioptveicoli.saveIt();
	    	  }
	      }
	     //物料映射 
	      String materialResult = null; 
	      String vehicleCode = null;
	      String trimCode = null;
	      String exteriorColorCode = null;
	      TiCoppieColoriVeicoloPO coppieColoriVeicoloPo = null;
	      List<TiCoppieColoriVeicoloPO> materialList = TiCoppieColoriVeicoloPO.find(" IS_MAPPING = ? ", "0");
	      if(materialList != null && materialList.size()>0){
	    	  for(int i=0;i<materialList.size();i++){
	    		  coppieColoriVeicoloPo = materialList.get(i);
	    		  vehicleCode = coppieColoriVeicoloPo.getString("COD_VEHICLE");
	    		  trimCode = coppieColoriVeicoloPo.getString("COD_TRIM");
	    		  exteriorColorCode = coppieColoriVeicoloPo.getString("COD_EXTEMAL_COLOR");
	    		  materialResult = isMappingMaterial(vehicleCode, trimCode, exteriorColorCode);
	    		  
	    		  if("01".equals(materialResult)){
	    			  coppieColoriVeicoloPo.setString("IS_MAPPING","1");
	    			  coppieColoriVeicoloPo.setString("REMARK","该车款在以前PC传输的车款中不存在，物料映射失败");
	    			  coppieColoriVeicoloPo.setTimestamp("UPDATE_DATE",sysDate.getTime());
	    			  coppieColoriVeicoloPo.saveIt();
	    		  }else{
	    			  coppieColoriVeicoloPo.setString("IS_MAPPING","1");
	    			  coppieColoriVeicoloPo.setTimestamp("UPDATE_DATE",sysDate.getTime());
	    			  coppieColoriVeicoloPo.saveIt();
	    		  }
	    	  }
	      }
			logger.info("=================PC映射结束====================");
		} catch (Throwable t) { 
			//t.printStackTrace();
			logger.error("======PC映射失败======",t);
			throw new Exception("============PC映射失败=================="+t);
		}
	}	
	/**
	 * 功能说明:查询品牌未映射记录
	 * 创建人: HYS 
	 * 创建日期: 2015-03-17
	 * parameters：null
	 * @return
	 */
//	public List<TiVeicoliPO> searchBrandList()throws Exception{	
//		StringBuffer sql = new StringBuffer("");
//		List<TiVeicoliPO> list = null;
//		try{
//			sql.append("  select COD_VEHICLE_BRA from Ti_Veicoli where is_mapping = 0 group by COD_VEHICLE_BRA \n");
//			
//			list = factory.select(sql.toString(), null,
//					new DAOCallback<TiVeicoliPO>() {
//						public TiVeicoliPO wrapper(ResultSet rs, int idx) {
//							TiVeicoliPO bean = new TiVeicoliPO();
//							try {
//								bean.setCodVehicleBra(rs.getString("COD_VEHICLE_BRA"));
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}
//							return bean;
//						}
//	
//					});
//			
//			logger.info("======PC查询品牌未映射记录======list:"+list.size());
//			
//		} catch (Throwable t) { 
//			//t.printStackTrace();
//			//logger.info("======PC查询品牌未映射记录失败======");
//			throw new Exception("============PC查询品牌未映射记录失败==================",t);
//		}
//		return list;
//	}
	
	
	/**
	 * 功能说明:查询车系未映射记录
	 * 创建人: HYS 
	 * 创建日期: 2015-03-17
	 * parameters：null
	 * @return
	 */
	/*public List<TiVeicoliPO> searchSeriesList()throws Exception{	
		StringBuffer sql = new StringBuffer("");
		List<TiVeicoliPO> list = null;
		try{
			sql.append("  select COD_COMMERCIAL_MODEL from Ti_Veicoli where is_mapping = 0 group by COD_COMMERCIAL_MODEL \n");
			
			list = factory.select(sql.toString(), null,
					new DAOCallback<TiVeicoliPO>() {
						public TiVeicoliPO wrapper(ResultSet rs, int idx) {
							TiVeicoliPO bean = new TiVeicoliPO();
							try {
								bean.setCodCommercialModel(rs.getString("COD_COMMERCIAL_MODEL"));
							} catch (SQLException e) {
								e.printStackTrace();
							}
							return bean;
						}
	
					});
			
			logger.info("======PC查询车系未映射记录======list:"+list.size());
			
		} catch (Throwable t) { 
			//t.printStackTrace();
			//logger.info("======PC查询车系未映射记录失败======");
			throw new Exception("============PC查询车系未映射记录失败==================",t);
		}
		return list;
	}*/
	
	
	/**
	 * 功能说明:查询车款未映射记录
	 * 创建人: HYS 
	 * 创建日期: 2015-03-17
	 * parameters：null
	 * @return
	 */
//	public List<TiOptVeicoloPO> searchPackageList()throws Exception{
//		List<TiOptVeicoloPO> list = null;
//		try{
//			String sql = "select COD_VEHICLE from TI_OPT_VEICOLO where IS_MAPPING = 0 group by COD_VEHICLE";
//			list = factory.select(sql.toString(), null,
//					new DAOCallback<TiOptVeicoloPO>() {
//						public TiOptVeicoloPO wrapper(ResultSet rs, int idx) {
//							TiOptVeicoloPO bean = new TiOptVeicoloPO();
//							try {
//								bean.setCodVehicle(rs.getString("COD_VEHICLE"));
//							} catch (SQLException e) {
//								e.printStackTrace();
//							}
//							return bean;
//						}
//	
//					});
//			
//			logger.info("======PC查询车款未映射记录======list:"+list.size());
//		} catch (Throwable t) { 
//			//t.printStackTrace();
//			//logger.info("======PC查询车款未映射记录失败======");
//			throw new Exception("============PC查询车款未映射记录失败==================",t);
//		}
//		return list;
//	}
	/**
	 * 功能说明:映射品牌是否存在
	 * 创建人: HYS 
	 * 创建日期: 2015-03-12
	 * parameters：VehicleBrandCode
	 * @return
	 */
    public String isMappingBrand(String brandName)throws Exception{
    	String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
    	try{
	    	List<TmPcBrandMapDcsPO> list = TmPcBrandMapDcsPO.find(" BRAND_NAME = ? ",brandName);
	    	if(null!=list){
	    		returnResult =EAIConstant.DEAL_SUCCESS;
	    	}else{
	    		returnResult=EAIConstant.DEAL_FAIL;
	    	}
	    	logger.info("======PC品牌映射======"+brandName+"返回结果:"+returnResult);
    	} catch (Throwable t) { 
			//t.printStackTrace();
			//logger.info("======PC品牌映射记录失败======");
			throw new Exception("============PC品牌映射记录失败==================",t);
		}
    	return returnResult;
    }
    
    /**
	 * 功能说明:映射车系是否存在
	 * 创建人: HYS 
	 * 创建日期: 2015-03-12
	 * parameters：未截取字段CommercialModelsCode需要截取年款后四位或两位
	 * @return
	 */
    
    public String isMappingSeries(TiVeicoliPO seriesPo)throws Exception{
    	String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
    	dcsSeriesCode = "";//起初进入这个方法体，默认的车系代码应为空值
    	//车系第一次映射匹配
    	try{
    		TmPcSeriesMapDcsPO dcsSeriesPo = new TmPcSeriesMapDcsPO();
	    	String seriesCode = seriesPo.getString("COD_COMMERCIAL_MODEL");
	    	if(seriesCode == null || seriesCode.length() <= 0 || seriesCode.length()!=6){// 判断CodCommercialModel 必须为6位
	    		seriesCode = "XXXXXX";
	    	}
	    	String seriesMapping = seriesCode.substring(2,4);
	    	List<TmPcSeriesMapDcsPO> list1 = TmPcSeriesMapDcsPO.find(" PC_MAPPING = ? ", seriesMapping);
	    	if(list1 == null || list1.size() <=0){	    		
	    		//车系第二次映射匹配
	    		seriesMapping = seriesCode.substring(2,6);
	        	List<TmPcSeriesMapDcsPO> list2 = TmPcSeriesMapDcsPO.find(" PC_MAPPING = ? ", seriesMapping);

	        	if(list2 == null || list2.size() <= 0){	
	        		String cposCode = seriesPo.getString("COD_VEHICLE");
	        		if(cposCode == null || cposCode.length() <=0){
	        			returnResult=EAIConstant.DEAL_FAIL;
            			logger.info("======PC车系映射======"+seriesMapping+"返回结果:"+returnResult);
            			return returnResult;
	        		}
	        		//车系第三次映射匹配
	        		//截取规则列如：从2015MKTH4924C8BLSTD截取值： MKT*49
	        		seriesMapping = cposCode.substring(4,7)+"_"+cposCode.substring(8,10);
	            	List<TmPcSeriesMapDcsPO> list3 = TmPcSeriesMapDcsPO.find(" PC_MAPPING = ? ", seriesMapping);

	            	if(list3 == null || list3.size() <= 0){
	            	   //车系第四次映射匹配
	            		//截取规则列如：从2015WKJT7421R8BLBS1截取值： WK____23_
	            		seriesMapping = cposCode.substring(4,6)+"____"+cposCode.substring(10,12);
	            		List<TmPcSeriesMapDcsPO> list4 = TmPcSeriesMapDcsPO.find(" PC_MAPPING like '%?%' ", seriesMapping);
	        	    	
	            		if(list4 == null || list4.size() <= 0){
	            			returnResult=EAIConstant.DEAL_FAIL;
	            			logger.info("======PC车系映射======"+seriesMapping+"返回结果:"+returnResult);
	            			return returnResult;
	            		}else{
	            			
	            			//返回dcsSeriesCode赋值方法参数
		        	    	dcsSeriesPo = list4.get(0);
		        	    	dcsSeriesCode = dcsSeriesPo.getString("SERIES_CODE");
	            			
	            			returnResult=EAIConstant.DEAL_SUCCESS;
	            			logger.info("======PC车系映射======"+seriesMapping+"返回结果:"+returnResult);
	            			return returnResult;
	            		}
	            		
	            	}else{
	            		
		    	    	//返回dcsSeriesCode赋值方法参数
		    	    	dcsSeriesPo = (TmPcSeriesMapDcsPO) list3.get(0);
		    	    	dcsSeriesCode = dcsSeriesPo.getString("SERIES_CODE");  
		    	    	
	            		returnResult=EAIConstant.DEAL_SUCCESS;
	            		logger.info("======PC车系映射======"+seriesMapping+"返回结果:"+returnResult);
	            		return returnResult;
	            	}
	        	}else{
	        		
	        		//返回dcsSeriesCode赋值方法参数
			    	dcsSeriesPo = (TmPcSeriesMapDcsPO) list2.get(0);
			    	dcsSeriesCode = dcsSeriesPo.getString("SERIES_CODE");
	        		
	        		returnResult=EAIConstant.DEAL_SUCCESS;
	        		logger.info("======PC车系映射======"+seriesMapping+"返回结果:"+returnResult);
	        		return returnResult;
	        	}
	    		
	    	}else{
	    		
		    	//返回dcsSeriesCode赋值方法参数
		    	dcsSeriesPo = (TmPcSeriesMapDcsPO) list1.get(0);
		    	dcsSeriesCode = dcsSeriesPo.getString("SERIES_CODE");
		    	
	    		returnResult=EAIConstant.DEAL_SUCCESS;
	    		logger.info("======PC车系映射======"+seriesMapping+"返回结果:"+returnResult);
	    		return returnResult;
	    	}
      } catch (Throwable t) { 
			//t.printStackTrace();
			//logger.info("======PC车系映射记录失败======",t);
			throw new Exception("============PC车系映射记录失败==================",t);
	  }
    }
    
    /**
	 * 功能说明:映射车型是否存在
	 * 创建人: HYS 
	 * 创建日期: 2015-03-12
	 * parameters：未截取字段VehicleCode需要截取年款后8BL之前
	 * @return
	 */
    public String isMappingModel(TiVeicoliPO veicoliPo)throws Exception{
    	String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
    	try{
    		String vehicleCode = veicoliPo.getString("COD_VEHICLE");
    		if(vehicleCode == null || vehicleCode.length() <= 0){
    			returnResult=EAIConstant.DEAL_FAIL;
	    		logger.info("======PC车型映射======"+vehicleCode+"返回结果:"+returnResult);
	    		return returnResult;
    		}
    		String vehicleCodeDes = veicoliPo.getString("VEHICLE_DES");
	    	String model = vehicleCode.substring(4,vehicleCode.length()-6);
	    	TmVhclMaterialGroupPO vhcl = TmVhclMaterialGroupPO.findFirst(" GROUP_CODE = ? and GROUP_LEVEL = ? and STATUS = ? ", model.toUpperCase(), 3, OemDictCodeConstants.STATUS_ENABLE);
	    	if(vhcl != null){
	    		returnResult=EAIConstant.DEAL_SUCCESS;
	    		logger.info("======PC车型映射======"+model+"返回结果:"+returnResult);
	    		return returnResult;
	    	}else{//车型不存在则新增车型，不必考虑车型是否是别的品牌的因为之前品牌，车系是否存在之前已校验
	    		//获取该车型对应的上一级车系ID
	    		String mapSeriesResult = isMappingSeries(veicoliPo);
	    		if(EAIConstant.DEAL_FAIL.equals(mapSeriesResult) || "".equals(dcsSeriesCode)){
	    			returnResult=EAIConstant.DEAL_FAIL;
		    		logger.info("======PC车型映射======"+model+"返回结果，上级车系获取失败:"+returnResult);
		    		return returnResult;
	    		}
		    	TmVhclMaterialGroupPO ser = TmVhclMaterialGroupPO.findFirst(" GROUP_CODE = ? and GROUP_LEVEL = ? and STATUS = ? ", dcsSeriesCode, 2, OemDictCodeConstants.STATUS_ENABLE);
	    		Long parentGroupId = ser.getLong("GROUP_ID");
	    		String treeCode=dao.getTreeCode(ser.getString("TREE_CODE"));
	    		
	    		TmVhclMaterialGroupPO vhcMaterialGroup = new TmVhclMaterialGroupPO();
	    		vhcMaterialGroup.setLong("OEM_COMPANY_ID",new Long(OemDictCodeConstants.OEM_ACTIVITIES));
	    		vhcMaterialGroup.setString("GROUP_CODE",model.toUpperCase());
	    		vhcMaterialGroup.setString("GROUP_NAME",vehicleCodeDes);
	    		vhcMaterialGroup.setLong("PARENT_GROUP_ID",parentGroupId);
	    		vhcMaterialGroup.setString("TREE_CODE",treeCode);
	    		vhcMaterialGroup.setInteger("GROUP_LEVEL",3);
	    		vhcMaterialGroup.setInteger("STATUS",OemDictCodeConstants.STATUS_ENABLE);
	    		vhcMaterialGroup.setTimestamp("CREATE_AT",sysDate.getTime());
	    		vhcMaterialGroup.setLong("CREATE_BY",new Long("80000002"));
	    		vhcMaterialGroup.saveIt();
	    		returnResult=EAIConstant.DEAL_SUCCESS;
	    		logger.info("======PC车型映射======"+model+"返回结果:"+returnResult);
	    		return returnResult;
	    	}
        } catch (Throwable t) { 
			//t.printStackTrace();
			//logger.info("======PC车型映射记录失败======");
			throw new Exception("============PC车型映射记录失败==================",t);
	  }
    }
    /**
	 * 功能说明:映射车款是否存在
	 * 创建人: HYS 
	 * 创建日期: 2015-03-12
	 * parameters：VehicleCode
	 * @return
	 */
    public String isMappingPackage(String VehicleCode,String vehicleDes)throws Exception{
    	String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
    	try{    		
        	//1.根据codeVehicle，从TiOptVeicoloPO表中查询待解析is_mapping=0的车款信息
        	List<TiOptVeicoloPO> veicoloList = TiOptVeicoloPO.find(" IS_MAPPING = ? and COD_VEHICLE = ? and FG_SPECIALSERIE = ? ", "0", VehicleCode, 1);
        	//2.用，号并按照字母先后顺序组合标准配置和工厂配置
        	generateOption(veicoloList);
        	
        	//3.拆分codeVehicle，取出年款、车型信息(不带8BL)
        	String modelYear = VehicleCode.substring(0,4);//前四位为年款
        	String model = VehicleCode.substring(4,VehicleCode.length()-6);//去除前四位和后六位，剩下的即为车型
        	//按照规则拼接成车款代码groupCode，默认车款代码groupCode为2位年款 +‘/’+VehicleDes+3位OPT(SpecialSerieCode)+3位LowCposCode(LowCposCode)+Series(CodCommercialModel).
    		String groupCode = VehicleCode.substring(2,4)+"/"+vehicleDes;
    		logger.info("======PC车款映射根据VehicleCode:"+VehicleCode+"拼接成新的车款代码为："+groupCode+"======");
        	
    		TmVhclMaterialGroupPO parentPo = TmVhclMaterialGroupPO.findFirst(" GROUP_CODE = ? ", model.toUpperCase());	
    		//判断19中的cpos在DCS的3级车型是否存在，存在则新增不存在返回EAIConstant.DEAL_FAIL记录接口表错误日志车型不存在。
    		if(parentPo != null ){
	        	//4.用车型、年款、车款代码去DCS主数据信息表TM_VHCL_MATERIAL_GROUP中校验该条车款信息是否存在
	        	List<TmVhclMaterialGroupPO> groupList = TmVhclMaterialGroupPO.find(" MODEL_YEAR = ? and PARENT_GROUP_ID = ? and REMARK = ? and STATUS = ? ", modelYear, parentPo.getLong("GROUP_ID"), VehicleCode.toUpperCase(), OemDictCodeConstants.STATUS_ENABLE);
	        	
	        	//5.若存在，更新DCS车款记录中的车款代码、工厂配置、标准配置
	        	if(groupList != null && groupList.size() > 0 && groupList.get(0) != null){
	        		TmVhclMaterialGroupPO groupPO = groupList.get(0);
	        		groupPO.setString("GROUP_CODE",groupCode.toUpperCase());//车款代码
	        		groupPO.setString("STANDARD_OPTION",standardOption);//标准配置standardOption = dao.orderByStr(standardOption);
	        		groupPO.setString("FACTORY_OPTIONS",factoryOptions);//工厂配置factoryOptions = dao.orderByStr(factoryOptions);
	        		groupPO.setTimestamp("UPDATE_AT",sysDate.getTime());
	        		groupPO.setLong("UPDATE_BY",new Long(80000002));
	        		groupPO.saveIt();
	        		
	        		if(!groupCode.toUpperCase().equals(groupList.get(0).getString("GROUP_CODE")))	  {      		
	        			//同时同步更新物料中的物料代码为此车款代码
			    		MaterialPO conMaterial = MaterialPO.findFirst(" MATERIAL_CODE = ? ", groupList.get(0).getString("GROUP_CODE"));
			    		
			    		conMaterial.setString("MATERIAL_CODE",groupCode.toUpperCase());
			    		conMaterial.setTimestamp("UPDATE_DATE",sysDate.getTime());
			    		conMaterial.setLong("UPDATE_BY",new Long(80000002));
			    		conMaterial.saveIt();
	        		}
	        	}else{
	        	//6.若不存在，新增车款信息到DCS车款记录表中	        		      		
	        		//校验车款代码唯一性
		        	List<TmVhclMaterialGroupPO> chkgroupList = TmVhclMaterialGroupPO.find(" GROUP_CODE = ? and STATUS = ? ", groupCode.toUpperCase(), OemDictCodeConstants.STATUS_ENABLE);
		        	if(chkgroupList != null && chkgroupList.size() > 0){
		        		returnResult="03";//----返回错误提示信息：该车款代码已存在，插入失败
		    			logger.info("======PC车款映射======"+VehicleCode+"返回结果(该车款代码"+groupCode.toUpperCase()+"已存在，插入失败):"+returnResult);
		        		return returnResult;
		        	}else{
		        		TmVhclMaterialGroupPO conGroupPO = new TmVhclMaterialGroupPO();
		        		conGroupPO.setString("MODEL_YEAR", modelYear);
		        		conGroupPO.setLong("PARENT_GROUP_ID", parentPo.getLong("GROUP_ID"));
		        		conGroupPO.setString("REMARK", VehicleCode.toUpperCase());
		        		conGroupPO.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
		        		conGroupPO.setString("GROUP_CODE",groupCode.toUpperCase());
		        		conGroupPO.setString("GROUP_NAME","");
		        		conGroupPO.setInteger("GROUP_LEVEL",new Integer(parentPo.getInteger("GROUP_LEVEL").intValue() + 1));//如果上级物料组代码不为空 设置组级别为：上级物料组的组级别+1
		        		String treeCode=dao.getTreeCode(parentPo.getString("TREE_CODE"));
		        		conGroupPO.setString("TREE_CODE",treeCode);
		        		conGroupPO.setString("STANDARD_OPTION",standardOption);//标准配置standardOption = dao.orderByStr(standardOption);
		        		conGroupPO.setString("FACTORY_OPTIONS",factoryOptions);//工厂配置factoryOptions = dao.orderByStr(factoryOptions);
		        		conGroupPO.setString("REMARK",VehicleCode.toUpperCase());//默认remark为codeVehicle
		        		conGroupPO.setTimestamp("CREATE_AT",sysDate.getTime());
		        		conGroupPO.setLong("CREATE_BY",new Long("80000002"));
		        		conGroupPO.setLong("OEM_COMPANY_ID",new Long(OemDictCodeConstants.OEM_ACTIVITIES));
		        		conGroupPO.saveIt();
		        	}
	        	}
	        	returnResult=EAIConstant.DEAL_SUCCESS;
	        	logger.info("======PC车款映射======"+VehicleCode+"返回结果:"+returnResult);
	    		return returnResult;
    		}else{
    			returnResult=EAIConstant.DEAL_FAIL;
    			logger.info("======PC车款映射======"+VehicleCode+"返回结果:"+returnResult);
        		return returnResult;
    		}
 /*       	//7.更新TiOptVeicoloPO中的codeVehicle记录为已映射is_mapping=1
        	TiOptVeicoloPO veicolo = new TiOptVeicoloPO();
        	veicolo.setIsMapping("1");
        	veicolo.setUpdateDate(sysDate.getTime());
        	factory.update(conVeicolo, veicolo);
        	
        	returnResult = EAIConstant.DEAL_SUCCESS; 
    		logger.info("======PC映射DCS产品主数据，车款处理成功======");*/
    	}catch (Exception e) {
			// TODO: handle exception
    		//returnResult = EAIConstant.DEAL_FAIL; 
    		//logger.info("======PC映射DCS产品主数据，车款处理失败======");
    		throw new Exception("======PC映射DCS产品主数据，车款处理失败======",e);
		}
    	//return returnResult;
    }
    
    /**
	 * 功能说明:根据codVehicle查询VehicleDes 2位年款 + ‘/’ + VehicleDescription + 3位OPT (SpecialSerieCode) + 3位LowCposCode (LowCposCode) + Series(CodCommercialModel).
	 * 创建人: dwl 
	 * 创建日期: 2015-03-25
	 * parameters：null
	 * @return
	 */
	public String searchVehicleDesByCodVehicle(String VehicleCode)throws Exception{
		List<TiVeicoliPO> list = null;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" select COD_VEHICLE,\n");
			sql.append("        COD_SPECIAL_SERIE,\n");
			sql.append("        COD_LOW_CPOS,\n");
			sql.append("        VEHICLE_DES,\n");
			sql.append("        COD_COMMERCIAL_MODEL,\n");
			sql.append("        ENGINEE,\n");
			sql.append("        TRASMISSION \n");
			sql.append("  from TI_VEICOLI_DCS \n");
			sql.append("  where (COD_COMMERCIAL_MODEL !='' and COD_COMMERCIAL_MODEL is not null) \n");
			sql.append("  and COD_VEHICLE = ? \n");
			sql.append("  order by create_date desc \n");
			sql.append("  limit 0,100 \n");
			list = TiVeicoliPO.find(sql.toString(), VehicleCode);
			if(list != null && list.size() > 0 ){
				TiVeicoliPO po = list.get(0);
				String vhlCod = po.getString("VEHICLE_DES")+po.getString("COD_SPECIAL_SERIE")+po.getString("COD_LOW_CPOS")+po.getString("COD_COMMERCIAL_MODEL");
				logger.info("======根据codVehicle:"+VehicleCode+"查询VehicleDes======:"+vhlCod);
				
				String standOpt = po.getString("ENGINEE") + "," + po.getString("TRASMISSION");
				PCMappingExecutorImpl.standardOption = dao.orderByStr(standOpt);//对标准配置进行字母排序
	    		logger.info("======组合并按字母排序后的标准配置："+PCMappingExecutorImpl.standardOption+"======");
				return vhlCod;
			}else {
				logger.info("============根据codVehicle:"+VehicleCode+"未查询VehicleDes==================");
				return null;
			}
		} catch (Throwable t) { 
			logger.error("============根据codVehicle:"+VehicleCode+"未查询VehicleDes==================",t);
			throw new Exception("============根据codVehicle:"+VehicleCode+"未查询VehicleDes==================",t);
		}
	}
    
    /**
	 * 功能说明:映射物料是否存在
	 * 创建人: HYS 
	 * 创建日期: 2015-03-12
	 * parameters：未截取字段VehicleCode需要截取年款后8BL之前,TrimCode,ExteriorColorCode
	 * @return
	 */
    public String isMappingMaterial(String vehicleCode,String trimCode,String  exteriorColorCode)throws Exception{
    	String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
    	try {
    		//1.从TI_COPPIE_COLORI_VEICOLO表中查询出待解析的物料数据
/*    		TiCoppieColoriVeicoloPO coppieColoriVeicolo=new TiCoppieColoriVeicoloPO();
    		coppieColoriVeicolo.setIsMapping("0");
    		coppieColoriVeicolo.setCodVehicle(VehicleCode);
    		coppieColoriVeicolo.setCodTrim(TrimCode);
    		coppieColoriVeicolo.setCodExtemalColor(ExteriorColorCode);
    		List<TiCoppieColoriVeicoloPO> ls = factory.select(coppieColoriVeicolo);
    		if(ls!=null&&ls.size()>0){
    			coppieColoriVeicolo=ls.get(0);*/
    			//2.通过VehicleCode去表"TmVhclMaterialGroupPO"查出Level等于4的remark，
	    		//String CodeVehicle=VehicleCode.substring(4,VehicleCode.length()-6);
    		//查询车款是否存在
	    	TmVhclMaterialGroupPO materialGroup=null;
	    	List<TmVhclMaterialGroupPO> mateGrouplist= TmVhclMaterialGroupPO.find(" GROUP_LEVEL = ? and REMARK = ? and STATUS = ? ", 4, vehicleCode, OemDictCodeConstants.STATUS_ENABLE);
	    	if(mateGrouplist!=null&&mateGrouplist.size()>0){
    			materialGroup=mateGrouplist.get(0);
	    		Long groupId=materialGroup.getLong("GROUP_ID");//groupId
	    		//查询物料是否存在
	    		List<MaterialPO> matelist= MaterialPO.find(" MATERIAL_CODE = ? and COLOR_CODE = ? and TRIM_CODE = ? and STATUS = ? ", materialGroup.getString("GROUP_CODE"), exteriorColorCode, trimCode, OemDictCodeConstants.STATUS_ENABLE);
	    		if(matelist != null && matelist.size()>0){
	    			returnResult=EAIConstant.DEAL_SUCCESS;
	    		}else{	    		
		    		//查颜色名称
		    		TiColoriEstemiVeicoloPO coloriEstem=TiColoriEstemiVeicoloPO.findFirst(" COD_VEHICLE = ? and COD_EXTEMAL_COLOR = ? ", vehicleCode, exteriorColorCode);
		    		String colorName=coloriEstem.getString("EXTEMAL_COLOR_DES");//获取颜色name
		    		//查内饰名称
		    		TiSellerieVeicoloPO sellerie= TiSellerieVeicoloPO.findFirst(" COD_VEHICLE = ? and COD_TRIM = ? ", vehicleCode, trimCode);
		    		String sellerieName=sellerie.getString("TRIM_DES");//获取内饰Name值
		    		//新增物料
		    		MaterialPO material =new MaterialPO();
		    		material.setString("MATERIAL_CODE",materialGroup.getString("GROUP_CODE"));
		    		material.setString("COLOR_CODE",exteriorColorCode);
		    		material.setString("TRIM_CODE",trimCode);
		    		material.setInteger("STATUS",OemDictCodeConstants.STATUS_ENABLE);
	    			material.setString("COLOR_NAME",colorName);
	    			material.setString("TRIM_NAME",sellerieName);
	    			material.setTimestamp("CREATE_DATE",sysDate.getTime());
	    			material.setLong("CREATE_BY",new Long("80000002"));
	    			material.setLong("COMPANY_ID",new Long("2010010100070674"));
	    			material.saveIt();
	    			long material_id = material.getLongId();
	    			//新增物料与物料组关系表
		    		MaterialGroupRPO vhclMaterialGroupR=new MaterialGroupRPO();
		    		vhclMaterialGroupR.setLong("GROUP_ID",groupId);
		    		vhclMaterialGroupR.setLong("MATERIAL_ID",material_id);
		    		vhclMaterialGroupR.setTimestamp("CREATE_DATE",sysDate.getTime());
		    		vhclMaterialGroupR.setLong("CREATE_BY",new Long("80000002"));
		    		vhclMaterialGroupR.saveIt();
		    		
		    		returnResult=EAIConstant.DEAL_SUCCESS;
		    		logger.info("======PC物料映射======"+vehicleCode+"返回结果:"+returnResult);
	    		}
    		}else{
    			returnResult = EAIConstant.DEAL_FAIL;
    			logger.info("======PC物料映射======"+vehicleCode+"返回结果:"+returnResult);
    		}
	    	//}
		} catch (Exception e) {
			returnResult = EAIConstant.DEAL_FAIL; 
			//logger.info("======PC映射DCS产品主数据，物料处理失败======");
			throw new Exception("======PC映射DCS产品主数据，物料处理失败======",e);
		}
    	return returnResult;
    }
    
    //用，号组合标准配置和工厂配置
    private void generateOption(List<TiOptVeicoloPO> veicoloList){
    	StringBuffer factoryOptNew = new StringBuffer();
    	
    	if( veicoloList != null && veicoloList.size() > 0){
    		for (TiOptVeicoloPO tiOptVeicoloPO : veicoloList) {
    			String type = tiOptVeicoloPO.getString("TYPE_AVAILABILITY");
				if("O".equals(type) && factoryOptNew.indexOf(tiOptVeicoloPO.getString("COD_OPTIONAL")) < 0 && tiOptVeicoloPO.getTimestamp("ORD_START_DATE") != null) {
					factoryOptNew.append(tiOptVeicoloPO.getString("COD_OPTIONAL")).append(",");
				}
			}

    		PCMappingExecutorImpl.factoryOptions = factoryOptNew.length() > 3 ? dao.orderByStr(factoryOptNew.substring(0,factoryOptNew.length()-1)) :  factoryOptNew.toString();//对工厂配置进行字母排序
    	}else{
    		//当FACTORY为空情况，置为‘’字符串
    		PCMappingExecutorImpl.factoryOptions = "";
    	}
    	logger.info("======组合并按字母排序后的工厂配置："+PCMappingExecutorImpl.factoryOptions+"======");
    }

//    public static void main(String[] args) {
//    	ContextUtil.loadConf();
//    	String VehicleCode = new String("2015LXCP482CP8BLSTD");
//    	String ExteriorColorCode=new String("PAR");
//    	String TrimCode=new String("ALX9");
//    	PCMappingExecutorImpl pc=new PCMappingExecutorImpl();
//    	try {
//// 			POContext.beginTxn(DBService.getInstance().getDefTxnManager(),
////					-1);
//    		pc.pcMappingDCS();
////    		POContext.endTxn(true);
////    		POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
////          pc.isMappingPackage(VehicleCode);
////    		pc.isMappingPackage(VehicleCode);
////    		pc.isMappingPackage(VehicleCode);
////			pc.isMappingMaterial(VehicleCode, TrimCode, ExteriorColorCode);
////			POContext.endTxn(true);
//		} catch (Exception e) {
//			logger.info(".......单元测试插入异常.................");
//			e.printStackTrace();
//		}finally{
//			POContext.cleanTxn();
//		}
//    	//isMappingMaterial(VehicleCode,TrimCode,ExteriorColorCode);
//    	String modelYear = VehicleCode.substring(0,4);//前四位为年款
//    	String model = VehicleCode.substring(4);//去除前四位和后六位，剩下的即为车型
//    	System.out.println(modelYear + "------" + model);
////    	
////    	String vehicleCode = VehicleCode.substring(0,VehicleCode.length()-1);
////    	System.out.println(vehicleCode);
//    	
//	}
	
}
