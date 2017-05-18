package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.Date;
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
import com.yonyou.dms.manage.dao.bulletin.DealerBulletinQueryDao;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinReplyDTO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinOrgRelPO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinReplayPO;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Service
@TxnConn
public class DealerBulletinQueryServiceImpl implements DealerBulletinQueryService {
	
	@Autowired
	private DealerBulletinQueryDao dao;
	
	@Autowired
    FileStoreService fileStoreService;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = dao.search(param);
		return dto;
	}

	@Override
	public PageInfoDto searchDetail(Map<String, String> param) {
		PageInfoDto dto = dao.searchDetail(param);
		return dto;
	}

	@Override
	public Map<String, Object> viewBulletin(Long bulletinId) {
		//将该条记录状态设置为已读
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		TtVsBulletinOrgRelPO po = TtVsBulletinOrgRelPO.findFirst("ORG_ID = ? AND BULLETIN_ID = ? ", loginUser.getDealerId(),bulletinId);
		po.setInteger("IS_READ", 1);
		po.setTimestamp("READ_DATE", currentTime);
		po.saveIt();
		//回显数据
		Map<String,Object> map = dao.viewBulletin(bulletinId);
		map.put("is_sign", po.getString("IS_SIGN"));
		return map;
	}

	@Override
	public boolean isSign(Long bulletinId,Integer isSign) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		TtVsBulletinOrgRelPO po = TtVsBulletinOrgRelPO.findFirst("ORG_ID = ? AND BULLETIN_ID = ? ", loginUser.getDealerId(),bulletinId);
		po.setInteger("IS_SIGN", isSign);
		po.setTimestamp("SIGN_CREATE", currentTime);
		boolean flag = po.saveIt();
		return flag;
	}

	@Override
	public void replyBulletin(BulletinReplyDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		boolean flag = false;
		Long bulletinId = dto.getBulletinId();
		TtVsBulletinReplayPO.delete("BULLETIN_ID = ? and DEALER_ID = ? ", bulletinId,loginUser.getDealerId());
		TtVsBulletinReplayPO po = new TtVsBulletinReplayPO();
		po.setLong("BULLETIN_ID", bulletinId);
		po.setLong("DEALER_ID", loginUser.getDealerId());
		po.setString("R_CONTENT", dto.getReply());
		po.setTimestamp("CREATE_DATE", currentTime);
		po.setLong("CREATE_BY", loginUser.getUserId());
		//上传附件
		fileStoreService.updateFileUploadInfo(dto.getDmsFileIds(), bulletinId.toString(), DictCodeConstants.FILE_TYPE_USER_INFO);
		String fileIds = dto.getDmsFileIds();
        String[] newFileIds = fileIds.split(CommonConstants.FILEUPLOADID_SPLIT_STR_TYPE);
        FileUploadInfoPO fpo = null;
        if(newFileIds.length>=1){
            String fileUploadId = newFileIds[1].split(CommonConstants.FILEUPLOADID_SPLIT_STR)[0];
            if(StringUtils.isNotBlank(fileUploadId)){
				fpo = FileUploadInfoPO.findById(Integer.parseInt(fileUploadId));
            }
        }
        if(fpo != null){
			po.setString("PATH_ID", fpo.getString("FILE_ID"));
			po.setString("FILENAME", fpo.get("FILE_NAME"));
        }
        
        flag = po.saveIt();
        if(!flag){
			throw new ServiceBizException("数据库插入数据失败！");
        }
		
	}

}
