package com.yonyou.dms.vehicle.service.afterSales.preclaim;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.common.domains.PO.basedata.FsFileuploadPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.afterSales.preclaim.PreclaimPreDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.preclaim.TtWrForeapprovalitemPO;
import com.yonyou.f4.filestore.FileStore;

/**
 * 索赔预授权维护
 * @author Administrator
 *
 */
@Service
public class PreclaimPreServiceImpl implements PreclaimPreService{
	@Autowired
	PreclaimPreDao  preclaimPreDao;
	
	@Autowired
	private FileStoreService fileStoreService;

	//定义文件存储的实现类
	@Autowired
	FileStore fileStore;


	//索赔预授权维护查询
	@Override
	public PageInfoDto PreclaimPreQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return preclaimPreDao.PreclaimPreQuery(queryParam);
	}
     //驳回意见查询
	@Override
	public TtWrForeapprovalPO getPreclaimPreById(Long id) {
		// TODO Auto-generated method stub
		return TtWrForeapprovalPO.findById(id);
	}
	
	//删除
	@Override
	public void delete(Long id) {
		TtWrForeapprovalPO  wrPO=TtWrForeapprovalPO.findById(id);
		wrPO.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_01);
		wrPO.saveIt();
	}
	
	//上报
	@Override
	public void preclaimCommit(Long id) {
		//判断是否绑定审核级别
		TtWrForeapprovalPO tpo=TtWrForeapprovalPO.findById(id);
		String currentAuthCode = tpo.getString("CURRENT_AUTH_CODE");

		if(currentAuthCode!=null && !"".equals(currentAuthCode)){
			tpo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_02);//已上报	
		}else{
			tpo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_04);//审核通过
		}
		tpo.saveIt();
		 
		//子表信息：
		List<Map> itemList = getPreclaimItemById(id);//
		if (itemList != null && itemList.size() > 0) {
			for (int i = 0; i < itemList.size(); i++) {
				String rid = itemList.get(i).get("ID").toString();
				TtWrForeapprovalitemPO selitempo = TtWrForeapprovalitemPO.findById(rid);// 通过id查询要修改的数据
				selitempo.setDate("CREATE_DATE", new Date());// 提报日期修改
				selitempo.saveIt();
			}

		}

	}
	
	//根据预授权ID查询相关项目
	public List<Map> getPreclaimItemById(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from TT_WR_FOREAPPROVALITEM_dcs titem \n");
		sql.append(" where titem.fid = " + id);
		return OemDAOUtil.findAll(sql.toString(), null);

	}
	
	//查询所有项目信息
	@Override
	public List<Map> getGongshi(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return preclaimPreDao.getGongshi(queryParam);
	}
	
	//文件上传
	@Override
	public void uploadFiles(MultipartFile importFile) {
		String fidStr = fileStoreService.writeFile(importFile);
		FsFileuploadPO po = new FsFileuploadPO();
		po.setString("FILENAME", importFile.getOriginalFilename().substring(importFile.getOriginalFilename().lastIndexOf("\\")+1, 
				importFile.getOriginalFilename().length()));//文件名称
		po.setString("FJID", fidStr);//文件服务器的文件ID
		try {
			po.setString("FILEURL", fileStore.getInputStream(fidStr));
		} catch (Exception e) {
			throw new ServiceBizException("设置文件URL失败", e);
		}	
		po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);//有效
		po.setLong("CREATE_BY", new Long(111111));
		po.setTimestamp("CREATE_DATE", new Date());
		po.saveIt();
		
	}
	//通过id查询附件信息
	@Override
	public PageInfoDto getFuJian(Map<String, String> queryParam, Long id) {
		// TODO Auto-generated method stub
		return preclaimPreDao.getFuJian(queryParam,id);
	}

	
	
	//索赔预授权维护修改	
	@Override
	
	public void edit(Long id, TtWrForeapprovalDTO ptdto,String type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrForeapprovalPO ptPo = TtWrForeapprovalPO.findById(id);
		 String authCode = "";
		//判断工单号是否存在 
        List<Map> jylist = selectForeapproval();
			if(jylist != null && jylist.size() > 0){
			   ptPo.setString("REPAIR_NO",jylist.get(0).get("REPAIR_NO"));
			}
			else{
				 String[] strArray= ptdto.getGroupIds().split(",");
			
				 // 审核级别代码
				authCode =getAuthCodeByCode(strArray);
				String[] str = authCode.split(",");
				
			   ptPo.setString("CURRENT_AUTH_CODE", str[0]);
		   ptPo.setString("AUTH_CODE", authCode);
		
		
		   ptPo.setString("REPAIR_NO", ptdto.getRepairNo());
		   ptPo.setDate("START_DATE",ptdto.getStartDate());
		   ptPo.setDate("INTO_DATE",ptdto.getIntoDate());
		   
		   ptPo.setDouble("INTO_MILEAGE", ptdto.getIntoMileage());
		   ptPo.setString("VIN", ptdto.getVin());
		   ptPo.setString("PLATE_NO", ptdto.getPlateNo());
		   ptPo.setString("ENGINE_NO", ptdto.getEngineNo());
		   ptPo.setString("BRAND", ptdto.getBrand());
		   
		   
		   ptPo.setString("SERIES", ptdto.getSeries());
		   ptPo.setString("MODEL", ptdto.getModel());
		   ptPo.setDate("APPLY_DATE", ptdto.getApplyDate());
		   
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setInteger("VER",0);
		   ptPo.setInteger("IS_DEL",0);
		   
			if(type.equals("u")){
				if(authCode!=null && !"".equals(authCode)){
					ptPo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_02);//已上报
					
				}else{
					ptPo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_04);//审核通过
				}
			}
		   
		   ptPo.saveIt();
		   //删除表格中的原有数据
		   TtWrForeapprovalitemPO.delete("FID = ? ",id);
		   //修改项目信息
		   
		 //新增项目信息
	        int len = strArray.length;
	        for (int j = 0; j <len; j++) {
	    	String[] labourArray =  strArray[j].split("#");
			TtWrForeapprovalitemPO additempo = new TtWrForeapprovalitemPO();
			   additempo.set("FID",  id);
			   additempo.set("ITEM_ID", labourArray[1]);
			   additempo.set("ITEM_CODE", labourArray[2]);
			   additempo.set("ITEM_TYPE", labourArray[5]);
			   additempo.set("ITEM_DESC", labourArray[3]);
			   additempo.set("CHECK_REMARK", labourArray[4]);
			   additempo.setLong("CREATE_BY", loginInfo.getUserId());
				additempo.setDate("CREATE_DATE", new Date()); 
				additempo.setString("AUTH_CODE",authCode);
				additempo.setLong("UPDATE_BY", loginInfo.getUserId());
				additempo.setDate("UPDATE_DATE", new Date());
				additempo.saveIt();
/*		   String[] strArray= ptdto.getGroupIds().split(",");
			for(int i=0;i<strArray.length;i++){
				String[] labourArray =  strArray[i].split("#");
				TtWrForeapprovalitemPO savePo = new TtWrForeapprovalitemPO();
			    savePo.set("FID",  id);
			    savePo.set("ITEM_ID", labourArray[1]);
				savePo.set("ITEM_CODE", labourArray[2]);
				savePo.set("ITEM_DESC", labourArray[3]);
				savePo.set("CHECK_REMARK", labourArray[4]);
				savePo.set("ITEM_TYPE", labourArray[5]);
				savePo.setLong("UPDATE_BY", loginInfo.getUserId());
				savePo.setDate("UPDATE_DATE", new Date());
				savePo.setLong("CREATE_BY", loginInfo.getUserId());
				savePo.setDate("CREATE_DATE", new Date()); 
				savePo.setInteger("IS_DEL",0);
				savePo.saveIt(); 
			}*/
		//删除表格中的原有数据
			FsFileuploadPO.delete(" YWZJ = ? ",id);
	  //修改附件信息
			   String[] strArray2= ptdto.getGroupIds2().split(",");
				for(int i=0;i<strArray2.length;i++){
					String[] labourArray2 =  strArray2[i].split("#");
					FsFileuploadPO savePo2 = new FsFileuploadPO();
					savePo2.set("YWZJ", id);
					savePo2.set("FILENAME", labourArray2[1]);
					savePo2.set("FILEURL", labourArray2[2]);
					savePo2.set("FILEID", labourArray2[3]);
					savePo2.set("FJID", labourArray2[4]);
					savePo2.setLong("UPDATE_BY", loginInfo.getUserId());
					savePo2.setDate("UPDATE_DATE", new Date());
					savePo2.setLong("CREATE_BY", loginInfo.getUserId());
					savePo2.setDate("CREATE_DATE", new Date());  
					savePo2.saveIt(); 
				}	 
	        }
	      }
	}
	
	
//	String[] strArray= null;
	public String getAuthCodeByCode(String[] strArray){
		String proCodes = ""; //维修项目代码
		String partCodes = ""; //配件项目代码
		String otherCodes = ""; //其他项目代码
		String authLevel = "";
		for (int i = 0; i < strArray.length; i++) {
			String[] labourArray =  strArray[i].split("#");
			String itemType=labourArray[5];
			if(itemType.equals(OemDictCodeConstants.PROJECT_TYPE_PRO.toString())){
				proCodes += "'"+itemType+"'"+",";
			}
			if(itemType.equals(OemDictCodeConstants.PROJECT_TYPE_PART.toString())){
				partCodes += "'"+itemType+"'"+",";
			}
			if(itemType.equals(OemDictCodeConstants.PROJECT_TYPE_OTHER.toString())){
				otherCodes += "'"+itemType+"'"+",";
			}
		}
		if(proCodes!=null&&!("").equals(proCodes)){
			String proSql = "select pr.AUTH_LEVEL from TT_WR_FORELABOUR_RULE_dcs pr where pr.LABOUR_CODE in("+proCodes.substring(0, proCodes.length()-1)+") and pr.IS_DEL = "+OemDictCodeConstants.IS_DEL_00;
           System.out.println(proSql);
		
			 List<Map> proList=OemDAOUtil.findAll(proSql.toString(), null);

			if(proList.size()>0 && !proList.equals("")){
			  for(int j=0; j<proList.size(); j++){
				 Map tep = (Map) proList.get(j);
				 String tr = (String) tep.get("AUTH_LEVEL");
				   authLevel = authLevel +tr +",";
			 }
			}
		}
		if(partCodes!=null&&!("").equals(partCodes)){
			String parSql = "select pa.AUTH_LEVEL from TT_WR_FOREPART_RULE_dcs pa where pa.PART_CODE in("+partCodes.substring(0, partCodes.length()-1)+") and pa.IS_DEL ="+OemDictCodeConstants.IS_DEL_00;
			 System.out.println(parSql);
			 List<Map> parList=OemDAOUtil.findAll(parSql.toString(), null);

			if(parList.size()>0 && !parList.equals("")){
			  for(int j=0; j<parList.size(); j++){
				 Map tep = (Map) parList.get(j);
				 String tr = (String) tep.get("AUTH_LEVEL");
				    authLevel = authLevel +tr +",";
			 }
			}
		}
		if(otherCodes!=null&&!("").equals(otherCodes)){
			String othSql = "select ot.AUTH_LEVEL from TT_WR_FOREOTHERITEMS_RULE_dcs ot where ot.OTHERITEM_CODE in("+otherCodes.substring(0, otherCodes.length()-1)+") and ot.IS_DEL = 0"+OemDictCodeConstants.IS_DEL_00;
			 System.out.println(othSql);
			List<Map> othList=OemDAOUtil.findAll(othSql.toString(), null);
			if(othList.size()>0 && !othList.equals("")){
			  for(int j=0; j<othList.size(); j++){
				 Map tep = (Map) othList.get(j);
				 String tr = (String) tep.get("AUTH_LEVEL");
				    authLevel = authLevel +tr +",";
			 }
			}
		  }
		String[] str = authLevel.split(",");
		String auth = "";
		TreeSet set = new TreeSet();
		for(int c=0; c<str.length; c++){
			set.add(str[c]);			
		}
		Iterator it = set.iterator();
		while(it.hasNext()){
			auth += it.next()+",";
			
		}
		
		return auth.substring(0, auth.length()-1);
		}
	
	

	
	//索赔预授权维护新增
	  public void add(TtWrForeapprovalDTO ptdto,String type) {
		    //新增套餐信息
		  TtWrForeapprovalPO ptPo=new TtWrForeapprovalPO();
				    setApplyPo(ptPo,ptdto,type);
	}
		private void setApplyPo(TtWrForeapprovalPO ptPo, TtWrForeapprovalDTO ptdto,String type) {
			   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			   Long ids=null;
			   String authCode = "";
			   if(!CommonUtils.isNullOrEmpty(getCheBy(ptdto))){
		              throw new ServiceBizException("该索赔预授权维护信息已经存在，不能重复新增！");
		          }else{
		        	  
		        	//判断工单号是否存在 
		            List<Map> jylist = selectForeapproval();
		  			if(jylist != null && jylist.size() > 0){
		  			   ptPo.setString("REPAIR_NO",jylist.get(0).get("REPAIR_NO"));
		  			}
		  			else{
		  				 String[] strArray= ptdto.getGroupIds().split(",");
		  			
		  				 // 审核级别代码
		  				authCode =getAuthCodeByCode(strArray);
		  				String[] str = authCode.split(",");
		  				
		  			   ptPo.setString("CURRENT_AUTH_CODE", str[0]);
		    		   ptPo.setString("AUTH_CODE", authCode);
		  			
		        	  //获取本公司基本信息
		        	  Map list=  getPreclaimPre();
		        	  ptPo.setLong("OEM_COMPANY_ID",list.get("OEM_COMPANY_ID"));
		        	  ptPo.setLong("DEALER_ID",list.get("DEALER_ID"));
		        	  ptPo.setString("DEALER_CODE",list.get("DEALER_CODE"));
		        	  ptPo.setString("DEALER_NAME",list.get("DEALER_NAME"));
		        	  ptPo.setLong("LINK_TEL",list.get("LINK_TEL"));
		        	  ptPo.setString("APPLY_MAN",list.get("APPLY_MAN"));
		        	  //需要生成一个预授权单号
		        	  String foreno=createForeNo();
		        	   ptPo.setString("FORE_NO", foreno);
		        	   ptPo.setString("REPAIR_NO",ptdto.getRepairNo());
		    		   ptPo.setDate("START_DATE",ptdto.getStartDate());
		    		   ptPo.setDate("INTO_DATE",ptdto.getIntoDate());
		    	
		    		   ptPo.setDouble("INTO_MILEAGE", ptdto.getIntoMileage());
		    		   ptPo.setString("VIN", ptdto.getVin());
		    		   ptPo.setString("PLATE_NO", ptdto.getPlateNo());
		    		   ptPo.setString("ENGINE_NO", ptdto.getEngineNo());
		    		   ptPo.setString("BRAND", ptdto.getBrand());
		    		   ptPo.setString("SERIES", ptdto.getSeries());
		    		   ptPo.setString("MODEL", ptdto.getModel());
		    		   ptPo.setDate("APPLY_DATE", ptdto.getApplyDate());
		    		   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
		    		   ptPo.setDate("CREATE_DATE", new Date());
		    		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		    		   ptPo.setDate("UPDATE_DATE", new Date());
		    		   ptPo.setInteger("VER",0);
		    		   ptPo.setInteger("IS_DEL",0);
		    		 
		    		   
		    			if(type.equals("u")){
		    				if(authCode!=null && !"".equals(authCode)){
		    					ptPo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_02);//已上报
		    				}else{
		    					ptPo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_04);//审核通过
		    				}				
		    			}else{
		    				ptPo.setInteger("STATUS",OemDictCodeConstants.FORE_APPLOVAL_STATUS_01);//未上报
		    			}
		    			  ptPo.saveIt();
				   //通过fore_no 查询出id   赋值给fid
		    		   TtWrForeapprovalPO  po = new TtWrForeapprovalPO();
				   po = TtWrForeapprovalPO.findFirst("FORE_NO=? ",foreno);
				   ids= Long.parseLong(po.get("ID").toString());
				   
				   
				   //新增项目信息
			        int len = strArray.length;
			        for (int i = 0; i <len; i++) {
			    	String[] labourArray =  strArray[i].split("#");
					TtWrForeapprovalitemPO additempo = new TtWrForeapprovalitemPO();
					   additempo.set("FID",  ids);
					   additempo.set("ITEM_ID", labourArray[1]);
					   additempo.set("ITEM_CODE", labourArray[2]);
					   additempo.set("ITEM_TYPE", labourArray[5]);
					   additempo.set("ITEM_DESC", labourArray[3]);
					   additempo.set("CHECK_REMARK", labourArray[4]);
					   additempo.setLong("CREATE_BY", loginInfo.getUserId());
						additempo.setDate("CREATE_DATE", new Date()); 
						additempo.setString("AUTH_CODE",authCode);
						additempo.saveIt();
		          }
		  			
		  			

				
		    	
		    }
		    
		    
		    //新增附件信息
		    
		  			
		    
				
		  			
		  			
		  			
		  			
			 /*  if(ptdto.getGroupIds()!=null){
			   String[] strArray= ptdto.getGroupIds().split(",");
				for(int i=0;i<strArray.length;i++){
					 labourArray =  strArray[i].split("#");
					TtWrForeapprovalitemPO savePo = new TtWrForeapprovalitemPO();
				    savePo.set("FID",  ids);
				    savePo.set("ITEM_ID", labourArray[1]);
					savePo.set("ITEM_CODE", labourArray[2]);
					savePo.set("ITEM_DESC", labourArray[3]);
					savePo.set("CHECK_REMARK", labourArray[4]);
					savePo.set("ITEM_TYPE", labourArray[5]);
					savePo.setLong("UPDATE_BY", loginInfo.getUserId());
					savePo.setDate("UPDATE_DATE", new Date());
					savePo.setLong("CREATE_BY", loginInfo.getUserId());
					savePo.setDate("CREATE_DATE", new Date()); 
					savePo.setInteger("IS_DEL",0);
					savePo.saveIt(); 
				 }
					}*/
			   

	  			
			  //新增附件信息
			  /* if(ptdto.getGroupIds2()!=null){
				   String[] strArray2= ptdto.getGroupIds2().split(",");
					for(int i=0;i<strArray2.length;i++){
						String[] labourArray2 =  strArray2[i].split("#");
						FsFileuploadPO savePo2 = new FsFileuploadPO();
						savePo2.set("YWZJ", ids);
						savePo2.set("FILENAME", labourArray2[1]);
						savePo2.set("FILEURL", labourArray2[2]);
						savePo2.set("FILEID", labourArray2[3]);
						savePo2.set("FJID", labourArray2[4]);
						savePo2.setLong("UPDATE_BY", loginInfo.getUserId());
						savePo2.setDate("UPDATE_DATE", new Date());
						savePo2.setLong("CREATE_BY", loginInfo.getUserId());
						savePo2.setDate("CREATE_DATE", new Date());  
						savePo2.saveIt(); 
					 }
						}	 */
		          }
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		//检查工单号是否存在
		   public List<Map> getCheBy(TtWrForeapprovalDTO ptdto) throws ServiceBizException {
		        StringBuilder sqlSb = new StringBuilder(" 	SELECT FORE_NO FROM TT_WR_FOREAPPROVAL_dcs WHERE 1=1");
		        List<Object> params = new ArrayList<>();
		        sqlSb.append("  and FORE_NO= ? ");
		        params.add(ptdto.getForeNo());
		        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
		        return applyList;
		    }
		   
			//判断工单号是否存在
			public List<Map> selectForeapproval() {
				StringBuffer sql = new StringBuffer();
				 LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				 Map list=  getPreclaimPre();
				sql.append(" SELECT t.ID,t.REPAIR_NO FROM TT_WR_FOREAPPROVAL_dcs t ");
				sql.append(" WHERE t.REPAIR_NO = "+ "'"+list.get("REPAIR_NO")+"'");
				sql.append("  AND DEALER_CODE='"+loginInfo.getDealerCode()+"'");
				sql.append(" and t.IS_DEL = "+ OemDictCodeConstants.IS_DEL_00);
				sql.append(" and t.STATUS != "+ OemDictCodeConstants.FORE_APPLOVAL_STATUS_05);
				 List<Map> LevelList=OemDAOUtil.findAll(sql.toString(), null);
			     return LevelList;
			}
	
	//通过dealer_code获取该公司基本信息
	
	public Map getPreclaimPre() {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=preclaimPreDao.getPreclaimPre();
		   m= list.get(0);
		   return m;
	}
	
	private synchronized String createForeNo(){
		String foreNo = "";
	
          //预授权单号生成
		  DateFormat df = new SimpleDateFormat("yyMM");
		  int count =getCountForeapproval()+1;			
		  String num = fillCharacter(count, 4);	
		  foreNo = "FN"+df.format(new Date())+num;
		
		return foreNo;//预授权申请单号
	}
	
	public static String fillCharacter(int num,int length){
		String newStr = String.valueOf(num);
		String appendZero = "";
		if(newStr.length()<length){
			for(int i=length;i>newStr.length();i--){
				appendZero+="0";
			}
			newStr = appendZero + newStr;
		}
		return newStr ;
	}
	
	public int getCountForeapproval(){
		
		  StringBuilder sqlSb = new StringBuilder(" select t.FORE_NO from TT_WR_FOREAPPROVAL_dcs t");
	        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), null);
		int count = 0;
		if(applyList.size()>0 && !applyList.equals("")){
			count = applyList.size();
		}
		return count;		
	}
	
	
	public String getFunName() {
		StackTraceElement stack[] = new Throwable().getStackTrace(); 
		StackTraceElement ste = stack[1];   
		StringBuilder strBuilder = new StringBuilder();
		return strBuilder.append(ste.getClassName()).
			append(".").append(ste.getMethodName()).
			append(ste.getLineNumber()).
			toString();  
	}

	

}
