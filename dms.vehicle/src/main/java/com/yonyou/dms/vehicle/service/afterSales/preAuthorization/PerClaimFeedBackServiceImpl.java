package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.common.domains.PO.basedata.FsFileuploadPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.PerClaimFeedBackDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalFeedbackDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalFeedbackPO;
import com.yonyou.f4.filestore.FileStore;

/**
 * 索赔预授权反馈
 * @author Administrator
 *
 */
@Service
public class PerClaimFeedBackServiceImpl implements PerClaimFeedBackService{
@Autowired
PerClaimFeedBackDao  perClaimFeedBackDao;
@Autowired
private FileStoreService fileStoreService;

//定义文件存储的实现类
@Autowired
FileStore fileStore;

@Override
public PageInfoDto PerClaimFeedBack(Map<String, String> queryParam) {
	// TODO Auto-generated method stub
	return perClaimFeedBackDao.PerClaimFeedBack(queryParam);
}
/**
 * 通过id查询基本信息
 * @param id
 * @return
 */
public Map getPerClaimFeed(Long id) {
	 Map<String, Object> m=new HashMap<String, Object>();
	 List<Map> list=perClaimFeedBackDao.getJiBenXinXi(id);
	 m= list.get(0);
	   return m;
}
@Override
public PageInfoDto XiangMuXinXi(Map<String, String> queryParam, Long id) {
	// TODO Auto-generated method stub
	return perClaimFeedBackDao.XiangMuXinXi(queryParam, id);
}


/**
 * 添加反馈信息
 */
public Long addFanKui(TtWrForeapprovalFeedbackDTO ptdto) {
	TtWrForeapprovalFeedbackPO ptPo=new TtWrForeapprovalFeedbackPO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
}
	private void setApplyPo(TtWrForeapprovalFeedbackPO ptPo, TtWrForeapprovalFeedbackDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		   if(ptdto.getAmount()!=null&&ptdto.getFeedbackNo()!=null){
			   ptPo.setString("OEM_COMPANY_ID",loginInfo.getCompanyId());
			   ptPo.setString("AMOUNT",ptdto.getAmount());
			   ptPo.setString("FEEDBACK_NO", ptdto.getFeedbackNo());
			   ptPo.setString("REMARK",ptdto.getRemark());
			   ptPo.setLong("FORE_ID",ptdto.getForeId());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.saveIt();
		   }else{
			   throw new ServiceBizException("重要信息未填写完整，请输入！"); 
		   }
	
	}
	
	/**
	 * 查询附件信息
	 */
	@Override
	public Map getFuJian(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=perClaimFeedBackDao.getFuJian(id);
		 m= list.get(0);
		 return m;
	}

	/**
	 * 文件上传
	 * @param importFile
	 * @throws ServiceBizException
	 */
	public void uploadFiles(MultipartFile importFile) throws ServiceBizException {
		//  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
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
	
	/**
	 * 查询反馈信息
	 */
	public Map getFunKui(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=perClaimFeedBackDao.getFanKui(id);
		 m= list.get(0);
		 return m;
	}



	
}
