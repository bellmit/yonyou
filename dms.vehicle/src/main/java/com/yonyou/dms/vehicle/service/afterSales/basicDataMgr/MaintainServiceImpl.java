package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

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
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.MaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainLabourDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainPackageDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainPartDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainPackagePO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainPartPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainPackageDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainPartPO;

/**
 * 保养套餐维护
 * @author Administrator
 *
 */
@Service
public class MaintainServiceImpl implements MaintainService{
	@Autowired
	MaintainDao  maintainDao;

	/**
	 * 得到所有车型名称
	 */
	@Override
	public List<Map> getAllCheXing(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return maintainDao.getAllCheXing(queryParams);
	}

	/**
	 * 查询保养套餐维护
	 */
	@Override
	public PageInfoDto MaintainQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return maintainDao.MaintainQuery(queryParam);
	}

	//查询所有工时信息
	@Override
	public List<Map> getGongshi(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return maintainDao.getGongshi(queryParam);
	}
	//获得保养套餐零部件信息列表
	@Override
	public List<Map> getLingJian(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return maintainDao.getLingJian(queryParam);
	}

	  public void add(TtWrMaintainPackageDTO ptdto) {
		    //新增套餐信息
				  TtWrMaintainPackagePO ptPo=new TtWrMaintainPackagePO();
				    setApplyPo(ptPo,ptdto);
	}
		private void setApplyPo(TtWrMaintainPackagePO ptPo, TtWrMaintainPackageDTO ptdto) {
			   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			   Long ids=null;
			   if(!CommonUtils.isNullOrEmpty(getCheBy(ptdto))){
		              throw new ServiceBizException("该套餐已经存在，不能重复新增！");
		          }else{
			   if(ptdto.getPackageCode()!=null){
				   ptPo.setString("PACKAGE_CODE", ptdto.getPackageCode()); 
			   }else{
				    throw new ServiceBizException("套餐编码不能为空！");
			   }
			   if(ptdto.getPackageName()!=null){
				   ptPo.setString("PACKAGE_NAME", ptdto.getPackageName());
			   }else{
				   throw new ServiceBizException("套餐名称不能为空！");
			   }
			   if(ptdto.getMaintainStartday()!=null&&ptdto.getMaintainEndday()!=null){
				   ptPo.setDouble("MAINTAIN_STARTDAY", ptdto.getMaintainStartday());
				   ptPo.setDouble("MAINTAIN_ENDDAY", ptdto.getMaintainEndday());
			   }else{
				   throw new ServiceBizException("保养天数不能为空！");
			   }
			   if(ptdto.getMaintainStartmileage()!=null&&ptdto.getMaintainEndmileage()!=null){
				   ptPo.setDouble("MAINTAIN_STARTMILEAGE", ptdto.getMaintainStartmileage());
				   ptPo.setDouble("MAINTAIN_ENDMILEAGE", ptdto.getMaintainEndmileage());
			   }else{
				   throw new ServiceBizException("保养里程数不能为空！");
			   }
			   if(ptdto.getModelId()!=null){
				   ptPo.setLong("MODEL_ID", ptdto.getModelId());
			   }else{
				   throw new ServiceBizException("请选择车型！");
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
				   ptPo.setInteger("IS_DOWN",0);
				   ptPo.saveIt(); 
					
				   //通过套餐编号查询packageId 
				   TtWrMaintainPackagePO  po = new TtWrMaintainPackagePO();
				   po = TtWrMaintainPackagePO.findFirst(" PACKAGE_CODE=? ", ptdto.getPackageCode());
				   ids= Long.parseLong(po.get("PACKAGE_ID").toString());
		          }
  
				 //新增工时信息
				   String[] strArray= ptdto.getGroupIds().split(",");
					for(int i=0;i<strArray.length;i++){
						String[] labourArray =  strArray[i].split("#");
						TtWrMaintainLabourPO savePo = new TtWrMaintainLabourPO();
					    savePo.set("PACKAGE_ID",  ids);
					    
						savePo.set("LABOUR_CODE", labourArray[1]);
						savePo.set("LABOUR_NAME", labourArray[2]);
						savePo.set("FRT", labourArray[3]);
						savePo.set("DEAL_TYPE", labourArray[4]);
						savePo.setLong("UPDATE_BY", loginInfo.getUserId());
						savePo.setDate("UPDATE_DATE", new Date());
						savePo.setLong("CREATE_BY", loginInfo.getUserId());
						savePo.setDate("CREATE_DATE", new Date());  
						savePo.saveIt(); 
					}
			  //新增零件信息
					   String[] strArray2= ptdto.getGroupIds2().split(",");
						for(int i=0;i<strArray2.length;i++){
							String[] labourArray2 =  strArray2[i].split("#");
							TtWrMaintainPartPO savePo2 = new TtWrMaintainPartPO();
							savePo2.set("PACKAGE_ID", ids);
							savePo2.set("PART_CODE", labourArray2[1]);
							savePo2.set("PART_NAME", labourArray2[2]);
							savePo2.set("FEE", labourArray2[3]);
							savePo2.set("DEAL_TYPE", labourArray2[4]);
							savePo2.set("PRICE", labourArray2[5]);
							savePo2.set("AMOUNT", labourArray2[6]);
							savePo2.setLong("UPDATE_BY", loginInfo.getUserId());
							savePo2.setDate("UPDATE_DATE", new Date());
							savePo2.setLong("CREATE_BY", loginInfo.getUserId());
							savePo2.setDate("CREATE_DATE", new Date());  
							savePo2.saveIt(); 
						}	 
		
		}
		
		//检查套餐代码是否存在
		   public List<Map> getCheBy(TtWrMaintainPackageDTO ptdto) throws ServiceBizException {
		        StringBuilder sqlSb = new StringBuilder(" 	SELECT package_code FROM TT_WR_MAINTAIN_PACKAGE_dcs WHERE 1=1");
		        List<Object> params = new ArrayList<>();
		        sqlSb.append("  and package_code= ? ");
		        params.add(ptdto.getPackageCode());
		        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
		        return applyList;
		    }
		
		
		
		
		
     //删除
		public void delete(Long id) {
			TtWrMaintainPackagePO ptPo = TtWrMaintainPackagePO.findById(id);
			   	if(ptPo!=null){
				   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
				   ptPo.saveIt();
			   	}
		}
//信息回显  保养套餐信息
		@Override
		public Map getBaoYang(Long id) {
			 Map<String, Object> m=new HashMap<String, Object>();
	    	 List<Map> list=maintainDao.getBaoYang(id);
	    	 m= list.get(0);
	    	   return m;
		}
//项目信息回显
		@Override
		public TtWrMaintainLabourPO getXiangMu(Long id) {
		
	    	   return TtWrMaintainLabourPO.findById(id);
		}
//零件信息回显
		@Override
		public TtWrMaintainPartPO getTaoCan(Long id) {
			
	    	   return TtWrMaintainPartPO.findById(id);
		}
		@Override
		public PageInfoDto getXiangMu(Map<String, String> queryParam,Long id) {
			// TODO Auto-generated method stub
			return maintainDao.getXiangMu(queryParam,id);
		}
		@Override
		public PageInfoDto getTaoCan(Map<String, String> queryParam,Long id) {
			// TODO Auto-generated method stub
			return maintainDao.getTaoCan(queryParam,id);
		}
		
         //修改
		public void edit(Long id,TtWrMaintainPackageDTO ptdto) {
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TtWrMaintainPackagePO ptPo = TtWrMaintainPackagePO.findById(id);
			   ptPo.setString("PACKAGE_CODE", ptdto.getPackageCode());
			   ptPo.setString("PACKAGE_NAME",ptdto.getPackageName());
			   ptPo.setDouble("MAINTAIN_ENDMILEAGE",ptdto.getMaintainEndmileage());
			   
			   ptPo.setDouble("MAINTAIN_STARTMILEAGE", ptdto.getMaintainStartmileage());
			   ptPo.setDate("MAINTAIN_STARTDATE",ptdto.getMaintainStartdate());
			   ptPo.setDate("MAINTAIN_ENDDATE",ptdto.getMaintainEnddate());
			   
			   ptPo.setLong("MODEL_ID",ptdto.getModelId());
			   ptPo.setDouble("MAINTAIN_STARTDAY", ptdto.getMaintainStartday());
			   ptPo.setDouble("MAINTAIN_ENDDAY", ptdto.getMaintainEndday());
			   
			   
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.setInteger("IS_DOWN",0);
			   ptPo.saveIt();
			   //删除表格中的原有数据
			   TtWrMaintainLabourPO.delete(" PACKAGE_ID = ? ",id);
			   //修改工时信息
			   String[] strArray= ptdto.getGroupIds().split(",");
				for(int i=0;i<strArray.length;i++){
					String[] labourArray =  strArray[i].split("#");
					TtWrMaintainLabourPO savePo = new TtWrMaintainLabourPO();
				    savePo.set("PACKAGE_ID",  id);
					savePo.set("LABOUR_CODE", labourArray[1]);
					savePo.set("LABOUR_NAME", labourArray[2]);
					savePo.set("FRT", labourArray[3]);
					savePo.set("DEAL_TYPE", labourArray[4]);
					savePo.setLong("UPDATE_BY", loginInfo.getUserId());
					savePo.setDate("UPDATE_DATE", new Date());
					savePo.setLong("CREATE_BY", loginInfo.getUserId());
					savePo.setDate("CREATE_DATE", new Date());  
					savePo.saveIt(); 
				}
			//删除表格中的原有数据
				TtWrMaintainPartPO.delete(" PACKAGE_ID = ? ",id);
		  //修改零件信息
				   String[] strArray2= ptdto.getGroupIds2().split(",");
					for(int i=0;i<strArray2.length;i++){
						String[] labourArray2 =  strArray2[i].split("#");
						TtWrMaintainPartPO savePo2 = new TtWrMaintainPartPO();
						savePo2.set("PACKAGE_ID", id);
						savePo2.set("PART_CODE", labourArray2[1]);
						savePo2.set("PART_NAME", labourArray2[2]);
						savePo2.set("FEE", labourArray2[3]);
						savePo2.set("DEAL_TYPE", labourArray2[4]);
						savePo2.set("PRICE", labourArray2[5]);
						savePo2.set("AMOUNT", labourArray2[6]);
						savePo2.setLong("UPDATE_BY", loginInfo.getUserId());
						savePo2.setDate("UPDATE_DATE", new Date());
						savePo2.setLong("CREATE_BY", loginInfo.getUserId());
						savePo2.setDate("CREATE_DATE", new Date());  
						savePo2.saveIt(); 
					}	 
			   
			   
			   
			}
/**
 * 工时信息删除
 */
		@Override
		public void delete2(Long id) {
			TtWrMaintainLabourPO ptPo = TtWrMaintainLabourPO.findById(id);
		   	if(ptPo!=null){
			   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
			   ptPo.saveIt();
		   	}
			
		}
/**
 * 零件信息删除
 */
		@Override
		public void delete3(Long id) {
			TtWrMaintainPartPO ptPo = TtWrMaintainPartPO.findById(id);
		   	if(ptPo!=null){
			   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
			   ptPo.saveIt();
		   	}
			
		}

		/**
		 * 修改工时信息
		 */
@Override
public void editLabour(Long id, TtWrMaintainLabourDTO ptdto) {
	TtWrMaintainLabourPO ptPo = TtWrMaintainLabourPO.findById(id);
	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	   ptPo.setString("LABOUR_CODE", ptdto.getLabourCode());
	   ptPo.setDouble("FRT",ptdto.getFrt());
	   ptPo.setInteger("DEAL_TYPE",ptdto.getDealType());
	   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
	   ptPo.setDate("UPDATE_DATE", new Date());
	   ptPo.setInteger("VER",0);
	   ptPo.setInteger("IS_DEL",0);
	   ptPo.saveIt();
}

/**
 * 修改零件信息
 */
@Override
public void editPart(Long id, TtWrMaintainPartDTO ptdto) {
	TtWrMaintainPartPO ptPo = TtWrMaintainPartPO.findById(id);
	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	   ptPo.setString("PART_CODE", ptdto.getPartCode());
	   ptPo.setDouble("FEE",ptdto.getFee());
	   ptPo.setInteger("DEAL_TYPE",ptdto.getDealType());
	   ptPo.setInteger("AMOUNT",ptdto.getAmount());
	   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
	   ptPo.setDate("UPDATE_DATE", new Date());
	   ptPo.setInteger("VER",0);
	   ptPo.setInteger("IS_DEL",0);
	   ptPo.saveIt();
	
}

/**
 * 复制
 */
public void add2(TtWrMaintainPackageDTO ptdto,Long id) {
		  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		  TtWrMaintainPackagePO ptPo=new TtWrMaintainPackagePO();
		   if(ptdto.getMaintainStartday()!=null&&ptdto.getMaintainEndday()!=null){
			   ptPo.setDouble("MAINTAIN_STARTDAY", ptdto.getMaintainStartday());
			   ptPo.setDouble("MAINTAIN_ENDDAY", ptdto.getMaintainEndday());
		   }else{
			   throw new ServiceBizException("保养天数不能为空！");
		   }
		   if(ptdto.getMaintainStartmileage()!=null&&ptdto.getMaintainEndmileage()!=null){
			   ptPo.setDouble("MAINTAIN_STARTMILEAGE", ptdto.getMaintainStartmileage());
			   ptPo.setDouble("MAINTAIN_ENDMILEAGE", ptdto.getMaintainEndmileage());
		   }else{
			   throw new ServiceBizException("保养里程数不能为空！");
		   }
		   
		   
		   if(ptdto.getPackageCode()!=null){
			   ptPo.setString("PACKAGE_CODE", ptdto.getPackageCode()); 
		   }else{
			    throw new ServiceBizException("套餐编码不能为空！");
		   }
		   if(ptdto.getPackageName()!=null){
			   ptPo.setString("PACKAGE_NAME", ptdto.getPackageName());
		   }else{
			   throw new ServiceBizException("套餐名称不能为空！");
		   }
		   if(ptdto.getModelId()!=null){
			   ptPo.setLong("MODEL_ID", ptdto.getModelId());
		   }else{
			   throw new ServiceBizException("请选择车型！");
		   }
		   if( ptdto.getMaintainStartdate()!=null&&ptdto.getMaintainEnddate()!=null){
			   ptPo.setDate("MAINTAIN_STARTDATE", ptdto.getMaintainStartdate());
			   ptPo.setDate("MAINTAIN_ENDDATE", ptdto.getMaintainEnddate());
		   }else{
			   throw new ServiceBizException("有效日期不能为空！");
		   }
		   
		      // ptPo.set("PACKAGE_ID", id);
		       ptPo.setLong("OEM_COMPANY_ID",loginInfo.getCompanyId());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.setInteger("IS_DOWN",0);
			   ptPo.saveIt(); 
			   
			   //复制零件信息
			   List<Map> LingJianList=getLingJianXinXi(id);
			   for (Map mapList : LingJianList) {
				   TtWrMaintainPartPO savePo2 = new TtWrMaintainPartPO();//零件
				   savePo2.set("PART_CODE", mapList.get("PART_CODE"));
				   savePo2.set("PART_NAME", mapList.get("PART_NAME"));
				   savePo2.set("DEAL_TYPE", mapList.get("DEAL_TYPE"));
				   savePo2.set("AMOUNT", mapList.get("AMOUNT"));
				   savePo2.set("PRICE", mapList.get("PRICE"));
				   savePo2.set("FEE", mapList.get("FEE"));
				   savePo2.set("CREATE_BY", mapList.get("CREATE_BY"));
				   savePo2.set("CREATE_DATE", mapList.get("CREATE_DATE"));
				   savePo2.set("UPDATE_BY", mapList.get("UPDATE_BY"));
				   savePo2.set("UPDATE_DATE", mapList.get("UPDATE_DATE"));   
				   savePo2.set("VER", mapList.get("VER"));
				   savePo2.set("IS_DEL", mapList.get("IS_DEL"));
				   savePo2.set("PART_NO", mapList.get("PART_NO"));
			
				   TtWrMaintainPackagePO  po = new TtWrMaintainPackagePO();
				   //通过package_code查询package_id
				   po = TtWrMaintainPackagePO.findFirst(" PACKAGE_CODE=? ", ptdto.getPackageCode());
				  Long  ids= Long.parseLong(po.get("PACKAGE_ID").toString());
				  savePo2.set("PACKAGE_ID", ids);   
				  savePo2.saveIt();
			   }
			   //复制工时信息
			   List<Map> GongShiList=getGongShiXinXi(id);
			   for(Map gongList:GongShiList){
				   TtWrMaintainLabourPO savePo = new TtWrMaintainLabourPO();//工时
				   savePo.set("LABOUR_CODE", gongList.get("LABOUR_CODE"));
				   savePo.set("LABOUR_NAME", gongList.get("LABOUR_NAME"));
				   savePo.set("FRT", gongList.get("FRT"));
				   savePo.set("DEAL_TYPE", gongList.get("DEAL_TYPE"));
				   
				   savePo.set("CREATE_BY", gongList.get("CREATE_BY"));
				   savePo.set("CREATE_DATE", gongList.get("CREATE_DATE"));
				   savePo.set("UPDATE_BY", gongList.get("UPDATE_BY"));
				   savePo.set("UPDATE_DATE", gongList.get("UPDATE_DATE")); 
				   
				   savePo.set("VER", gongList.get("VER"));
				   savePo.set("IS_DEL", gongList.get("IS_DEL"));
				   //通过package_code查询package_id
				   TtWrMaintainPackagePO  po = new TtWrMaintainPackagePO();
				   po = TtWrMaintainPackagePO.findFirst(" PACKAGE_CODE=? ", ptdto.getPackageCode());
				  Long  ids= Long.parseLong(po.get("PACKAGE_ID").toString());
				  savePo.set("PACKAGE_ID", ids); 
				  savePo.saveIt();
			   }
}
			//通过package_id查询工时信息
			private List<Map> getGongShiXinXi(Long id) {
				StringBuilder sqlSb = new StringBuilder("  SELECT * from TT_WR_MAINTAIN_LABOUR_DCS where 1=1  and  IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
			    List<Object> params = new ArrayList<>();
			    sqlSb.append(" and  package_id=?");
			    params.add(id);
			    List<Map> GongShiList=OemDAOUtil.findAll(sqlSb.toString(), params);
			    return GongShiList;
			}
			//通过package_id查询零件信息
			private List<Map> getLingJianXinXi(Long id) {
			   	StringBuilder sqlSb = new StringBuilder("  SELECT * from TT_WR_MAINTAIN_PART_DCS where 1=1  and  IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
			    List<Object> params = new ArrayList<>();
			    sqlSb.append(" and  package_id=?");
			    params.add(id);
			    List<Map> LingJianList=OemDAOUtil.findAll(sqlSb.toString(), params);
			    return LingJianList;
			}

/**
 * 下发
 */
   public void xiaFa(Long id) {
	   TtWrMaintainPackagePO ptPo = TtWrMaintainPackagePO.findById(id);
  	   if(ptPo!=null){
 	   ptPo.setInteger("IS_DOWN",OemDictCodeConstants.IS_DEL_01);
 	   ptPo.saveIt();
    	}
	
}
		
		
}