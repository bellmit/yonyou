package com.yonyou.dms.part.service.basedata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.dao.PartObsoleteMaterialDefineDao;
import com.yonyou.dms.part.domains.DTO.basedata.TtObsoleteMaterialDefineDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtObsoleteMaterialDefineDcsPO;
/**
* 呆滞品定义service
* @author ZhaoZ
* @date 2017年4月11日
*/
@Service
public class PartObsoleteMaterialDefineServiceImpl implements PartObsoleteMaterialDefineService{

	@Autowired
	private PartObsoleteMaterialDefineDao defineDao;
	
	/**
	 * 查询呆滞品定义Lists
	 */
	@Override
	public PageInfoDto queryPartObsoleteMaterialList(Map<String, String> queryParams) throws ServiceBizException {
		return defineDao.defineQuery(queryParams);
	}

	/**
	 * 下发
	 */
	@Override
	public void isSendStatus(Long id) throws ServiceBizException {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		boolean flag = false;
		//SEDCS063 de = new SEDCS063();  接口
		//String flag = de.sendDataEach(id);
		//if(flag.equals("1")){
			TtObsoleteMaterialDefineDcsPO mdPO=TtObsoleteMaterialDefineDcsPO.findById(id);
			mdPO.setInteger("IS_SEND",OemDictCodeConstants.IS_DOWN_01);
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time= System.currentTimeMillis();
			try {
				Date date = sdf.parse(sdf.format(new Date(time)));
				java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
				mdPO.setTimestamp("SEND_DATE",st);
				mdPO.setTimestamp("UPDATE_DATE",st);
			} catch (ParseException e) {
				e.printStackTrace();
			} 	
			mdPO.setLong("UPDATE_BY",loginUser.getUserId());
			flag = mdPO.saveIt();
			if(flag){			
			}else{
				throw new ServiceBizException("下发失败");
			}
		//}
		
	}
	
	/**
	 * 呆滞品定义修改
	 */
	@Override
	public void update(Long id,TtObsoleteMaterialDefineDcsDTO dto) throws ServiceBizException {
		defineDao.updatePartObsolete(id,dto);
	}

	/**
	 * 呆滞品查询
	 */
	@Override
	public PageInfoDto findALLList(Map<String, String> queryParams) throws ServiceBizException {
		return defineDao.getALLList(queryParams);
	}

	/**
	 * 呆滞品下载查询
	 */
	@Override
	public List<Map> queryDownLoad(Map<String, String> queryParams) throws ServiceBizException {
		return defineDao.queryDownLoadList(queryParams);
	}

	
	

}
