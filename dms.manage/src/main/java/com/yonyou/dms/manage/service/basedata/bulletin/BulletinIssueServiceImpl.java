package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.PO.file.FileUploadInfoPO;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.bulletin.BulletinIssueDao;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinIssueDTO;
import com.yonyou.dms.manage.domains.DTO.bulletin.dealerListDTO;
import com.yonyou.dms.manage.domains.PO.bulletin.TmBulletinTypePO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinAttachmentPO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinOrgRelPO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinPO;

@Service
public class BulletinIssueServiceImpl implements BulletinIssueService {
	
	@Autowired
	private BulletinIssueDao dao;
	
	@Autowired
    FileStoreService fileStoreService;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		String typeId = param.get("typeId");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);	
		PageInfoDto dto = null;
		if(StringUtils.isNotBlank(typeId)){
			dto = dao.searchByTypeId(Long.parseLong(typeId));
		}else{
			dto = dao.searchByUserId(loginUser.getUserId());
		}
		return dto;
	}

	@Override
	public void addBulletinIssue(BulletinIssueDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		Long typeId = dto.getTypeId();
		boolean flag = false;
		if(typeId != null){
			try {				
				TtVsBulletinPO tbpo = new TtVsBulletinPO();
				tbpo.setLong("TYPE_ID", typeId);
				tbpo.setString("TOPIC", dto.getTopic());
				tbpo.setString("CONTENT", dto.getContent());
				tbpo.setInteger("STATUS", 1);
				tbpo.setInteger("IS_REPLAY",0);
				tbpo.setInteger("ACTIVITY_SCOPE", dto.getRegion());
				tbpo.setTimestamp("CREATE_DATE", currentTime);
				tbpo.setLong("CREATE_BY", loginUser.getUserId());
				tbpo.setString("ISSHOW", dto.getIsShow());
				tbpo.setString("SIGN", dto.getIsCheck());
				tbpo.setString("LEVEL", dto.getLevel());
				tbpo.setTimestamp("START_TIME_DATE", dto.getBeginDate());
				tbpo.setTimestamp("END_TIME_DATE", dto.getEndDate());
				flag = tbpo.saveIt();
				if(!flag){
					throw new ServiceBizException("数据库插入数据失败！");
				}
				
				//上传附件
				Long bulletinId = tbpo.getLongId();
				fileStoreService.updateFileUploadInfo(dto.getDmsFileIds(), bulletinId.toString(), DictCodeConstants.FILE_TYPE_USER_INFO);
				String fileIds = dto.getDmsFileIds();
	            String[] newFileIds = fileIds.split(CommonConstants.FILEUPLOADID_SPLIT_STR_TYPE);
	            //如果存在已经上传的附件
	            if(newFileIds.length>=1){
	                //修改保留附件的状态为有效
	                for(String fileUploadId:newFileIds[1].split(CommonConstants.FILEUPLOADID_SPLIT_STR)){
	                    if(StringUtils.isNotBlank(fileUploadId)){
							FileUploadInfoPO fpo = FileUploadInfoPO.findById(Integer.parseInt(fileUploadId));
							TtVsBulletinAttachmentPO po = new TtVsBulletinAttachmentPO();
							po.setLong("BULLETIN_ID", bulletinId);
							po.setString("PATH_ID", fpo.getString("FILE_ID"));
							po.setString("FILENAME", fpo.get("FILE_NAME"));
							po.setTimestamp("CREATE_DATE", currentTime);
							po.setLong("CREATE_BY", loginUser.getUserId());
							flag = po.saveIt();
							if(!flag){
								throw new ServiceBizException("数据库插入数据失败！");
							}	
	                    }
	                }
	            }
				
				//添加经销商信息
	            String ids = dto.getDealerIds();
//				List<dealerListDTO> dealerList = dto.getDealerList();
				List<String> dealerIds = new ArrayList<String>();
				if(dto.getRegion().intValue() == 1){				
					if(StringUtils.isNotBlank(ids)){
						String[] dealerList = ids.split(",");
						for(String d : dealerList){
							if(!dealerIds.contains(d)){
								dealerIds.add(d);
							}
						}
					}
				}else{
					dealerIds = dao.getDealerID();
				}
				if(dealerIds != null && !dealerIds.isEmpty()){
					for(int i = 0;i < dealerIds.size(); i++){
						String index = dealerIds.get(i);
						if(StringUtils.isNotBlank(index)){
							TtVsBulletinOrgRelPO torpo = new TtVsBulletinOrgRelPO();
							torpo.setLong("BULLETIN_ID", bulletinId);
							torpo.setLong("ORG_ID", index); //dealerId
							flag = torpo.saveIt();
							if(!flag){
								throw new ServiceBizException("数据库插入数据失败！");
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceBizException("数据库插入数据失败！");
			}
		}
		
	}

	@Override
	public String getTypeName(Long typeId) {
		TmBulletinTypePO po = TmBulletinTypePO.findById(typeId);
		if(po != null){			
			return po.getString("TYPENAME");
		}
		return null;
	}

}
