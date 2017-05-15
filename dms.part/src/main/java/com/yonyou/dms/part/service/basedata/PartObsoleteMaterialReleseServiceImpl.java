package com.yonyou.dms.part.service.basedata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialApplyDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialReleaseDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.dao.PartObsoleteMaterialReleseDao;
import com.yonyou.dms.part.domains.DTO.basedata.TtObsoleteMaterialApplyDcsDTO;

/**
* 呆滞品发布信息查询service
* @author ZhaoZ
* @date 2017年4月11日
*/
@Service
public class PartObsoleteMaterialReleseServiceImpl implements PartObsoleteMaterialReleseService{

	@Autowired
	private PartObsoleteMaterialReleseDao releseDao;
	/**
	 * 呆滞品发布信息查询
	 */
	@Override
	public PageInfoDto queryPartObsoleteMaterialList(Map<String, String> queryParams) throws ServiceBizException {
		return releseDao.getPartObsoleteMaterialList(queryParams);
	}
	/**
	 * 呆滞品发布信息下载查询
	 */
	@Override
	public List<Map> queryDownLoad(Map<String, String> queryParams) throws ServiceBizException {
		return releseDao.queryDownLoadList(queryParams);
	}
	/**
	 * 查询指定编号的呆滞品
	 */
	@Override
	public Map<String, Object> queryReleaseById(Long id) throws ServiceBizException {
		return releseDao.queryReleaseList(id);
	}
	/**
	 * 呆滞品申请
	 */
	@Override
	public void insertApplyRelease(TtObsoleteMaterialApplyDcsDTO dto) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long time= System.currentTimeMillis();
		//新增申请
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtObsoleteMaterialApplyDcsPO applyPO = new TtObsoleteMaterialApplyDcsPO();
		Map<String, Object> release = releseDao.queryReleaseList(Long.parseLong(dto.getReleaseId()));
		applyPO.setLong("RELEASE_ID",Long.parseLong(dto.getReleaseId()));//发布信息ID
		applyPO.setString("PART_CODE",release.get("PART_CODE")!=null && release.get("PART_CODE")!=""?release.get("PART_CODE").toString():"");//申请配件代码
		applyPO.setString("PART_NAME",release.get("PART_NAME")!=null && release.get("PART_NAME")!=""? release.get("PART_NAME").toString():"");//申请配件名称
		Pattern pattern = Pattern.compile("[0-9]*"); 
		boolean fla = pattern.matcher(dto.getApplyNum()).matches();    
		if(!fla){
			throw new ServiceBizException("申请数量请输入数字！");
		}
		applyPO.setInteger("APPLY_NUMBER",Integer.parseInt(dto.getApplyNum()));//申请数量
		applyPO.setString("APPLY_DEALER_CODE",dto.getApplyDealerCode());//申请经销商代码 
		applyPO.setString("APPLY_DEALER_NAME",dto.getApplyDealerName());//申请经销商简称
		applyPO.setString("RELEASE_DEALER_CODE",release.get("DEALER_CODE")!=null && release.get("DEALER_CODE")!=""?release.get("DEALER_CODE").toString():"");//发布经销商代码
		applyPO.setString("RELEASE_DEALER_NAME",release.get("DEALER_SHORTNAME")!=null && release.get("DEALER_SHORTNAME")!=""?release.get("DEALER_SHORTNAME").toString():"");//发布经销商简称
		applyPO.setDouble("COST_PRICE",Double.parseDouble(release.get("COST_PRICE")!=null && release.get("COST_PRICE")!=""?release.get("COST_PRICE").toString():"0"));//成本单价
		applyPO.setDouble("SALES_PRICE",Double.parseDouble(release.get("SALES_PRICE")!=null && release.get("SALES_PRICE")!=""?release.get("SALES_PRICE").toString():"0"));//销售单价(发布价格)
		applyPO.setString("MEASURE_UNITS",release.get("MEASURE_UNITS")!=null && release.get("MEASURE_UNITS")!=""?release.get("MEASURE_UNITS").toString():"");//计量单位
		applyPO.setString("LINKMAN_NAME",dto.getLinkmanName());//联系人名称
		applyPO.setString("LINKMAN_TEL",dto.getLinkmanTel());//联系人电话
		applyPO.setString("ADDRESS",dto.getLinkmanAddress());//详细地址
		applyPO.setString("POST_CODE",dto.getZipCode());//邮编
		applyPO.setInteger("STATUS",OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_01);//状态
		
    	try {
    		Date date = sdf.parse(sdf.format(new Date(time)));
    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
    		applyPO.setTimestamp("APPLY_DATE",st);//申请时间
    		applyPO.setTimestamp("CREATE_DATE",st);//创建时间
    	} catch (ParseException e) {
    		e.printStackTrace();
    	} 	
		applyPO.setLong("CREATE_BY",loginUser.getUserId());//创建人
		boolean flag = false;
		flag = applyPO.insert();
		if(flag){			
		}else{
			throw new ServiceBizException("申请失败！");
		}
		//修改发布表发布数量
		Integer mayApplyNum = Integer.parseInt(release.get("APPLY_NUMBER")!=null && release.get("APPLY_NUMBER")!=""?release.get("APPLY_NUMBER").toString():"0");
		Integer applyNumber = Integer.parseInt(dto.getApplyNum());
		Integer remainNum = mayApplyNum-applyNumber;
		TtObsoleteMaterialReleaseDcsPO po = TtObsoleteMaterialReleaseDcsPO.findById(dto.getReleaseId());
		po.setInteger("APPLY_NUMBER", remainNum);
		flag = po.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("修改发布表发布数量失败！");
		}
	}

}
