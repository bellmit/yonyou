package com.yonyou.dms.vehicle.service.afterSales.weixinreserve;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.afterSales.weixinreserve.WXMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainPackageDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.weixinreserve.TmWxMaintainPackageDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainPackagePO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainPartPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainPackageDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainPartPO;

/**
 * 微信保养套餐维护
 * @author Administrator
 *
 */
@Service
public class WXMaintainServiceImpl implements WXMaintainService{
	@Autowired
	WXMaintainDao wxMaintainDao;
    //微信保养套餐维护查询
	@Override
	public PageInfoDto WXMaintainQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return wxMaintainDao.WXMaintainQuery(queryParam);
	}
	//年款查询
	@Override
	public List<Map> getModelYearList() {
		// TODO Auto-generated method stub
		return wxMaintainDao.getModelYearList();
	}
	//车系查询
	@Override
	public List<Map> getSeriesList(String modelYear) {
		// TODO Auto-generated method stub
		return wxMaintainDao.getSeriesList(modelYear);
	}
	//排量查询
	@Override
	public List<Map> getEngineList(String modelYear, String seriesCode) {
		// TODO Auto-generated method stub
		return wxMaintainDao.getEngineList(modelYear, seriesCode);
	}
	//删除
	@Override
	public void delete(Long id) {
		TmWxMaintainPackageDcsPO ptPo = TmWxMaintainPackageDcsPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	   	}
		
	}
	//得到工时列表的基本信息
	@Override
	public List<Map> getGongshi(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return wxMaintainDao.getGongshi(queryParam);
	}
	//得到零件列表的基本信息
	@Override
	public List<Map> getLingJian(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return wxMaintainDao.getLingJian(queryParam);
	}
	
	//新增微信保养套餐维护
	
	  public void add(TmWxMaintainPackageDcsDTO ptdto) {
		    //新增套餐信息
		  TmWxMaintainPackageDcsPO ptPo=new TmWxMaintainPackageDcsPO();
				    setApplyPo(ptPo,ptdto);
	}
		private void setApplyPo(TmWxMaintainPackageDcsPO ptPo, TmWxMaintainPackageDcsDTO ptdto) {
			   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			   Long ids=null;
			   if(!CommonUtils.isNullOrEmpty(getCheBy(ptdto))){
		              throw new ServiceBizException("该套餐已经存在，不能重复新增！");
		          }else{
			   if(ptdto.getPackageCode()!=null){
				   ptPo.setString("PACKAGE_CODE",ptdto.getPackageCode()); 
			   }else{
				    throw new ServiceBizException("套餐编码不能为空！");
			   }
			   if(ptdto.getPackageName()!=null){
				   ptPo.setString("PACKAGE_NAME", ptdto.getPackageName());
			   }else{
				   throw new ServiceBizException("套餐名称不能为空！");
			   }
			   if(ptdto.getMaintainStartmileage()!=null&&ptdto.getMaintainEndmileage()!=null){
				   ptPo.setDouble("MAINTAIN_STARTMILEAGE", ptdto.getMaintainStartmileage());
				   ptPo.setDouble("MAINTAIN_ENDMILEAGE", ptdto.getMaintainEndmileage());
			   }else{
				   throw new ServiceBizException("保养里程数不能为空！");
			   }
			   if(ptdto.getModelYear()!=null){
				   ptPo.setString("MODEL_YEAR", ptdto.getModelYear());
			   }else{
				   throw new ServiceBizException("请选择年款！");
			   }   
			   if(ptdto.getSeriesCode()!=null){
				   ptPo.setString("SERIES_CODE", ptdto.getSeriesCode());
			   }else{
				   throw new ServiceBizException("请选择车系！");
			   }
			   if(ptdto.getEngineDesc()!=null){
				   ptPo.setString("ENGINE_DESC", ptdto.getEngineDesc());
			   }else{
				   throw new ServiceBizException("请选择发动机排量！");
			   }
			   if(ptdto.getTotalAmount()!=null){
				   ptPo.setDouble("TOTAL_AMOUNT", ptdto.getTotalAmount());
			   }else{
				   throw new ServiceBizException("请选择保养套餐总价！");
			   }
			   if(ptdto.getPType()!=null){
				   if(ptdto.getPType().equals(90111001)){
					   ptPo.setInteger("P_TYPE", 1); 
				   }else if(ptdto.getPType().equals(90111002)){
					   ptPo.setInteger("P_TYPE", 0); 	   
				   }
			   }else{
				   throw new ServiceBizException("请选择套餐类型！");
			   }
			   if(ptdto.getOileType()!=null){
				   ptPo.setInteger("OILE_TYPE", ptdto.getOileType());
			   }else{
				   throw new ServiceBizException("请选择燃油类型！");
			   }
			   if( ptdto.getMaintainStartdate()!=null&&ptdto.getMaintainEnddate()!=null){
				   ptPo.setDate("MAINTAIN_STARTDATE", ptdto.getMaintainStartdate());
				   ptPo.setDate("MAINTAIN_ENDDATE", ptdto.getMaintainEnddate());
			   }else{
				   throw new ServiceBizException("有效日期不能为空！");
			   }
			       ptPo.setLong("OEM_COMPANY_ID",loginInfo.getCompanyId());
				   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
				   ptPo.setDate("UPDATE_DATE", new Date());
				   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
				   ptPo.setDate("CREATE_DATE", new Date());  
				   ptPo.setInteger("VER",0);
				   ptPo.setInteger("IS_DEL",0);
				   ptPo.setInteger("IS_DMS_SEND",0);
				   ptPo.setInteger("IS_WX_SEND",0);
				   ptPo.setInteger("M_TYPE",0);
				   ptPo.saveIt(); 
					
				   //通过套餐编号查询packageId 
				   TmWxMaintainPackageDcsPO  po = new TmWxMaintainPackageDcsPO();
				   po = TmWxMaintainPackageDcsPO.findFirst(" PACKAGE_CODE=? ", ptdto.getPackageCode());
				   ids= Long.parseLong(po.get("PACKAGE_ID").toString());
		          }
       
        	 
      
				 //新增工时信息
				   String[] strArray= ptdto.getGroupIds().split(",");
					for(int i=0;i<strArray.length;i++){
						String[] labourArray =  strArray[i].split("#");
						TmWxMaintainLabourPO savePo = new TmWxMaintainLabourPO();
					    savePo.set("PACKAGE_ID",  ids);
					    
						savePo.set("LABOUR_CODE", labourArray[1]);
						savePo.set("LABOUR_NAME", labourArray[2]);
						savePo.set("FRT", labourArray[3]);
						savePo.setLong("UPDATE_BY", loginInfo.getUserId());
						savePo.setDate("UPDATE_DATE", new Date());
						savePo.setLong("CREATE_BY", loginInfo.getUserId());
						savePo.setDate("CREATE_DATE", new Date());  
						savePo.saveIt(); 
					}
					 
			  //新增零件信息
					   String[] strArray2= ptdto.getGroupIds2().split(",");
					   if(strArray2.length>2){
						for(int i=0;i<strArray2.length;i++){
							String[] labourArray2 =  strArray2[i].split("#");
							TmWxMaintainPartPO savePo2 = new TmWxMaintainPartPO();
							savePo2.set("PACKAGE_ID", ids);
							savePo2.set("PART_CODE", labourArray2[1]);
							savePo2.set("PART_NAME", labourArray2[2]);
							savePo2.set("FEE", labourArray2[3]);
							savePo2.set("PRICE", labourArray2[4]);
							savePo2.set("AMOUNT", labourArray2[5]);
							savePo2.setLong("UPDATE_BY", loginInfo.getUserId());
							savePo2.setDate("UPDATE_DATE", new Date());
							savePo2.setLong("CREATE_BY", loginInfo.getUserId());
							savePo2.setDate("CREATE_DATE", new Date());  
							savePo2.saveIt(); 
						}	
					}	else{
						
						 throw new ServiceBizException("配件套餐只能添加一个零件！");
					}
						 
				
		
		}
	
		//检查套餐代码是否存在
		   public List<Map> getCheBy(TmWxMaintainPackageDcsDTO ptdto) throws ServiceBizException {
		        StringBuilder sqlSb = new StringBuilder(" 	SELECT package_code FROM TM_WX_MAINTAIN_PACKAGE_dcs WHERE 1=1");
		        List<Object> params = new ArrayList<>();
		        sqlSb.append("  and package_code= ? ");
		        params.add(ptdto.getPackageCode());
		        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
		        return applyList;
		    }
		   
		   //获得微信保养套餐基本信息
		@Override
		public Map getBaoYang(Long id) {
			 Map<String, Object> m=new HashMap<String, Object>();
	    	 List<Map> list=wxMaintainDao.getBaoYang(id);
	    	 m= list.get(0);
	    	   return m;
		}
		//获得项目信息
		@Override
		public PageInfoDto getXiangMu(Map<String, String> queryParam, Long id) {
			// TODO Auto-generated method stub
			return wxMaintainDao.getXiangMu(queryParam,id);
		}
		
		//获得零件信息
		@Override
		public PageInfoDto getLingJian(Map<String, String> queryParam, Long id) {
			// TODO Auto-generated method stub
			return wxMaintainDao.getLingJian(queryParam,id);
		}
		
		//项目信息回显
		@Override
		public TmWxMaintainLabourPO getXiangMuById(Long id) {
			// TODO Auto-generated method stub
			return TmWxMaintainLabourPO.findById(id);
		}
		
		//零件信息回显
		@Override
		public TmWxMaintainPartPO getLingJianById(Long id) {
			// TODO Auto-generated method stub
			return TmWxMaintainPartPO.findById(id);
		}
		
		//修改微信保养套餐维护信息
		@Override
		public void edit(Long id, TmWxMaintainPackageDcsDTO ptdto) {
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TmWxMaintainPackageDcsPO ptPo = TmWxMaintainPackageDcsPO.findById(id);
			   ptPo.setString("PACKAGE_CODE", ptdto.getPackageCode());
			   ptPo.setString("PACKAGE_NAME",ptdto.getPackageName());
			   ptPo.setDouble("MAINTAIN_ENDMILEAGE",ptdto.getMaintainEndmileage());
			   
			   ptPo.setDouble("MAINTAIN_STARTMILEAGE", ptdto.getMaintainStartmileage());
			   ptPo.setDate("MAINTAIN_STARTDATE",ptdto.getMaintainStartdate());
			   ptPo.setDate("MAINTAIN_ENDDATE",ptdto.getMaintainEnddate());
			   
			   ptPo.setString("MODEL_YEAR", ptdto.getModelYear());
			   ptPo.setString("SERIES_CODE", ptdto.getSeriesCode());
			   ptPo.setString("ENGINE_DESC", ptdto.getEngineDesc());
			   ptPo.setDouble("TOTAL_AMOUNT", ptdto.getTotalAmount());
			   if(ptdto.getPType().equals(90111001)){
				   ptPo.setInteger("P_TYPE", 1); 
			   }else if(ptdto.getPType().equals(90111002)){
				   ptPo.setInteger("P_TYPE", 0); 	   
			   }
			   ptPo.setInteger("OILE_TYPE", ptdto.getOileType());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.saveIt();
			   //删除表格中的原有数据
			   TmWxMaintainLabourPO.delete(" PACKAGE_ID = ? ",id);
			   //修改工时信息
			   String[] strArray= ptdto.getGroupIds().split(",");
				for(int i=0;i<strArray.length;i++){
					String[] labourArray =  strArray[i].split("#");
					TmWxMaintainLabourPO savePo = new TmWxMaintainLabourPO();
				    savePo.set("PACKAGE_ID",  id);
					savePo.set("LABOUR_CODE", labourArray[1]);
					savePo.set("LABOUR_NAME", labourArray[2]);
					savePo.set("FRT", labourArray[3]);
					savePo.setLong("UPDATE_BY", loginInfo.getUserId());
					savePo.setDate("UPDATE_DATE", new Date());
					savePo.setLong("CREATE_BY", loginInfo.getUserId());
					savePo.setDate("CREATE_DATE", new Date());  
					savePo.saveIt(); 
				}
			//删除表格中的原有数据
				TmWxMaintainPartPO.delete(" PACKAGE_ID = ? ",id);
		  //修改零件信息
				   String[] strArray2= ptdto.getGroupIds2().split(",");
					for(int i=0;i<strArray2.length;i++){
						String[] labourArray2 =  strArray2[i].split("#");
						TmWxMaintainPartPO savePo2 = new TmWxMaintainPartPO();
						savePo2.set("PACKAGE_ID", id);
						savePo2.set("PART_CODE", labourArray2[1]);
						savePo2.set("PART_NAME", labourArray2[2]);
						savePo2.set("FEE", labourArray2[3]);
						savePo2.set("PRICE", labourArray2[4]);
						savePo2.set("AMOUNT", labourArray2[5]);
						savePo2.setLong("UPDATE_BY", loginInfo.getUserId());
						savePo2.setDate("UPDATE_DATE", new Date());
						savePo2.setLong("CREATE_BY", loginInfo.getUserId());
						savePo2.setDate("CREATE_DATE", new Date());  
						savePo2.saveIt(); 
					}	 
			
		}
		
		//复制微信保养套餐信息
  public void add2(TmWxMaintainPackageDcsDTO ptdto,Long id) {
	  TmWxMaintainPackageDcsPO ptPo=new TmWxMaintainPackageDcsPO();
			    setApplyPo2(ptPo,ptdto,id);
	}
	private void setApplyPo2(TmWxMaintainPackageDcsPO ptPo, TmWxMaintainPackageDcsDTO ptdto,Long id) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		   
		   if(ptdto.getPackageCode()!=null){
			   ptPo.setString("PACKAGE_CODE",ptdto.getPackageCode()); 
		   }else{
			    throw new ServiceBizException("套餐编码不能为空！");
		   }
		   if(ptdto.getPackageName()!=null){
			   ptPo.setString("PACKAGE_NAME", ptdto.getPackageName());
		   }else{
			   throw new ServiceBizException("套餐名称不能为空！");
		   }
		   if(ptdto.getMaintainStartmileage()!=null&&ptdto.getMaintainEndmileage()!=null){
			   ptPo.setDouble("MAINTAIN_STARTMILEAGE", ptdto.getMaintainStartmileage());
			   ptPo.setDouble("MAINTAIN_ENDMILEAGE", ptdto.getMaintainEndmileage());
		   }else{
			   throw new ServiceBizException("保养里程数不能为空！");
		   }
		   if(ptdto.getModelYear()!=null){
			   ptPo.setString("MODEL_YEAR", ptdto.getModelYear());
		   }else{
			   throw new ServiceBizException("请选择年款！");
		   }   
		   if(ptdto.getSeriesCode()!=null){
			   ptPo.setString("SERIES_CODE", ptdto.getSeriesCode());
		   }else{
			   throw new ServiceBizException("请选择车系！");
		   }
		   if(ptdto.getEngineDesc()!=null){
			   ptPo.setString("ENGINE_DESC", ptdto.getEngineDesc());
		   }else{
			   throw new ServiceBizException("请选择发动机排量！");
		   }
		   if(ptdto.getTotalAmount()!=null){
			   ptPo.setDouble("TOTAL_AMOUNT", ptdto.getTotalAmount());
		   }else{
			   throw new ServiceBizException("请选择保养套餐总价！");
		   }
		   if(ptdto.getPType()!=null){
			   if(ptdto.getPType().equals(90111001)){
				   ptPo.setInteger("P_TYPE", 1); 
			   }else if(ptdto.getPType().equals(90111002)){
				   ptPo.setInteger("P_TYPE", 0); 	   
			   }
		   }else{
			   throw new ServiceBizException("请选择套餐类型！");
		   }
		   if(ptdto.getOileType()!=null){
			   ptPo.setInteger("OILE_TYPE", ptdto.getOileType());
		   }else{
			   throw new ServiceBizException("请选择燃油类型！");
		   }
		   if( ptdto.getMaintainStartdate()!=null&&ptdto.getMaintainEnddate()!=null){
			   ptPo.setDate("MAINTAIN_STARTDATE", ptdto.getMaintainStartdate());
			   ptPo.setDate("MAINTAIN_ENDDATE", ptdto.getMaintainEnddate());
		   }else{
			   throw new ServiceBizException("有效日期不能为空！");
		   }
		     //  ptPo.set("PACKAGE_ID", id);
		       ptPo.setLong("OEM_COMPANY_ID",loginInfo.getCompanyId());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.setInteger("IS_DMS_SEND",0);
			   ptPo.setInteger("IS_WX_SEND",0);
			   ptPo.setInteger("M_TYPE",0);
			   ptPo.saveIt(); 
 
			   //复制零件信息
			   List<Map> LingJianList=getLingJianXinXi(id);
			   for (Map mapList : LingJianList) {
				   TmWxMaintainPartPO savePo2 = new TmWxMaintainPartPO();//零件
				   savePo2.set("PART_CODE", mapList.get("PART_CODE"));
				   savePo2.set("PART_NAME", mapList.get("PART_NAME"));
				   savePo2.set("AMOUNT", mapList.get("AMOUNT"));
				   savePo2.set("PRICE", mapList.get("PRICE"));
				   savePo2.set("FEE", mapList.get("FEE"));
				   savePo2.set("CREATE_BY", mapList.get("CREATE_BY"));
				   savePo2.set("CREATE_DATE", mapList.get("CREATE_DATE"));
				   savePo2.set("UPDATE_BY", mapList.get("UPDATE_BY"));
				   savePo2.set("UPDATE_DATE", mapList.get("UPDATE_DATE"));   
				   savePo2.set("VER", mapList.get("VER"));
				   savePo2.set("IS_DEL", mapList.get("IS_DEL"));
				   savePo2.set("IS_DMS_SEND", mapList.get("IS_DMS_SEND"));
				   savePo2.set("IS_WX_SEND", mapList.get("IS_WX_SEND"));   
				   TmWxMaintainPackageDcsPO  po = new TmWxMaintainPackageDcsPO();
				   //通过package_code查询package_id
				   po = TmWxMaintainPackageDcsPO.findFirst(" PACKAGE_CODE=? ", ptdto.getPackageCode());
				  Long  ids= Long.parseLong(po.get("PACKAGE_ID").toString());
				  savePo2.set("PACKAGE_ID", ids);   
				  savePo2.saveIt();
			   }
			   //复制工时信息
			   List<Map> GongShiList=getGongShiXinXi(id);
			   for(Map gongList:GongShiList){
				   TmWxMaintainLabourPO savePo = new TmWxMaintainLabourPO();//工时
				   savePo.set("LABOUR_CODE", gongList.get("LABOUR_CODE"));
				   savePo.set("LABOUR_NAME", gongList.get("LABOUR_NAME"));
				   savePo.set("FRT", gongList.get("FRT"));
				   
				   savePo.set("CREATE_BY", gongList.get("CREATE_BY"));
				   savePo.set("CREATE_DATE", gongList.get("CREATE_DATE"));
				   savePo.set("UPDATE_BY", gongList.get("UPDATE_BY"));
				   savePo.set("UPDATE_DATE", gongList.get("UPDATE_DATE")); 
				   
				   savePo.set("VER", gongList.get("VER"));
				   savePo.set("IS_DEL", gongList.get("IS_DEL"));
				   savePo.set("IS_DMS_SEND", gongList.get("IS_DMS_SEND"));
				   savePo.set("IS_WX_SEND", gongList.get("IS_WX_SEND"));   
				   //通过package_code查询package_id
				   TmWxMaintainPackageDcsPO  po = new TmWxMaintainPackageDcsPO();
				   po = TmWxMaintainPackageDcsPO.findFirst(" PACKAGE_CODE=? ", ptdto.getPackageCode());
				  Long  ids= Long.parseLong(po.get("PACKAGE_ID").toString());
				  savePo.set("PACKAGE_ID", ids); 
				  savePo.saveIt();
			   } 
	}
	
	//通过package_id查询工时信息
	    private List<Map> getGongShiXinXi(Long id) {
	    	StringBuilder sqlSb = new StringBuilder("  SELECT * from Tm_Wx_Maintain_Labour_dcs where 1=1  and  IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
	        List<Object> params = new ArrayList<>();
	        sqlSb.append(" and  package_id=?");
	        params.add(id);
	        List<Map> GongShiList=OemDAOUtil.findAll(sqlSb.toString(), params);
	        return GongShiList;
    	}
	    //通过package_id查询零件信息
		private List<Map> getLingJianXinXi(Long id) {
		   	StringBuilder sqlSb = new StringBuilder("  SELECT * from Tm_Wx_Maintain_Part_dcs where 1=1  and  IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
	        List<Object> params = new ArrayList<>();
	        sqlSb.append(" and  package_id=?");
	        params.add(id);
	        List<Map> LingJianList=OemDAOUtil.findAll(sqlSb.toString(), params);
	        return LingJianList;
    	}

	
}
