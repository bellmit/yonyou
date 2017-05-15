package com.yonyou.dms.manage.service.basedata.bulletin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.yonyou.dms.manage.dao.bulletin.BulletinMaintainDao;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinIssueDTO;
import com.yonyou.dms.manage.domains.DTO.bulletin.dealerListDTO;
import com.yonyou.dms.manage.domains.DTO.bulletin.fileListDTO;
import com.yonyou.dms.manage.domains.PO.bulletin.TmBulletinTypePO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinAttachmentPO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinOrgRelPO;
import com.yonyou.dms.manage.domains.PO.bulletin.TtVsBulletinPO;
import com.yonyou.f4.mvc.annotation.TxnConn;

@TxnConn
@Service
public class BulletinMaintainServiceImpl implements BulletinMaintainService {

	@Autowired
	private BulletinMaintainDao dao;
	
	@Autowired
    FileStoreService fileStoreService;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = dao.search(param);
		return dto;
	}

	@Override
	public List<Map> getAllBulletinType() {
		return dao.getAllBulletinType();
	}

	@Override
	public Map<String,Object> editBulletinInit(Long bulletinId) throws ParseException {
		TtVsBulletinPO po = TtVsBulletinPO.findById(bulletinId);
		TmBulletinTypePO tpo = TmBulletinTypePO.findById(po.getLong("TYPE_ID"));
		Map map = po.toMap();
		map.put("TYPENAME", tpo.getString("TYPENAME"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start = map.get("start_time_date") == null ? null : sdf.parse(String.valueOf(map.get("start_time_date")));
		Date end =  map.get("end_time_date") == null ? null : sdf.parse(String.valueOf(map.get("end_time_date")));
		map.put("start_time_date", start);
		map.put("end_time_date", end);
		return map;
	}

	@Override
	public PageInfoDto getDealers(Map<String, String> param,Long bulletinId) {
		PageInfoDto dto = dao.getDealers(param,bulletinId);
		return dto;
	}

	@Override
	public PageInfoDto getFiles(Map<String, String> param,Long bulletinId) {
		PageInfoDto list = dao.getFiles(param,bulletinId);
		return list;
	}

	@Override
	public void editBulletin(BulletinIssueDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		Long typeId = dto.getTypeId();
		Long bulletinId = dto.getBulletinId();
		boolean flag = false;
		try {
			//通告保存
			TtVsBulletinPO tbpo = TtVsBulletinPO.findById(bulletinId);
			tbpo.setLong("TYPE_ID", typeId);
			tbpo.setString("TOPIC", dto.getTopic());
			tbpo.setString("CONTENT", dto.getContent());
			tbpo.setInteger("STATUS", 1);
			tbpo.setInteger("IS_REPLAY",0);
			tbpo.setInteger("ACTIVITY_SCOPE", dto.getRegion());
			tbpo.setTimestamp("UPDATE_DATE", currentTime);
			tbpo.setLong("UPDATE_BY", loginUser.getUserId());
			tbpo.setString("ISSHOW", dto.getIsShow());
			tbpo.setString("SIGN", dto.getIsCheck());
			tbpo.setString("LEVEL", dto.getLevel());
			tbpo.setTimestamp("START_TIME_DATE", dto.getBeginDate());
			tbpo.setTimestamp("END_TIME_DATE", dto.getEndDate());
			flag = tbpo.saveIt();
			if(!flag){
				throw new ServiceBizException("数据库插入数据失败！");
			}
			
			/*
			 * 上传附件
			 */
			//先处理原有附件
			//原来的所有id
			List<Long> allList = new ArrayList<>();
			List<fileListDTO> newFileList = dto.getFileList();
			//页面现存在id
			List<Long> newList = new ArrayList<>();
			for(fileListDTO d : newFileList){
				newList.add(d.getAttachmentId());
			}
			List<TtVsBulletinAttachmentPO> tempList = TtVsBulletinAttachmentPO.find("BULLETIN_ID = ?", bulletinId);
			for(TtVsBulletinAttachmentPO po : tempList){
				allList.add(po.getLongId());
			}
			for(Long idx : allList){
				if(!newList.contains(idx)){
					TtVsBulletinAttachmentPO.delete("ATTACHMENT_ID = ? ", idx);
				}
			}
			//再处理新添加附件
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
            
			/*
			 * 添加经销商信息
			 */
            //先删除原有记录
            TtVsBulletinOrgRelPO.delete("BULLETIN_ID = ? ", bulletinId);
            //处理新记录
			List<dealerListDTO> dealerList = dto.getDealerList();
			List<String> dealerIds = new ArrayList<String>();
			if(dto.getRegion().intValue() == 1){				
				if(dealerList != null && !dealerList.isEmpty()){
					for(dealerListDTO d : dealerList){
						String idx = d.getDealerId();
						if(!dealerIds.contains(idx)){
							dealerIds.add(idx);
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

	@Override
	public void deleteBulletin(Long bulletinId) {
		TtVsBulletinPO po = TtVsBulletinPO.findById(bulletinId);
		po.setInteger("STATUS", 0);   //status为0已删除 1为存在
		po.saveIt();
	}

}
