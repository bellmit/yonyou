
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : StoreServiceImpl.java
 *
 * @Author : zhongsw
 *
 * @Date : 2016年7月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月10日    zhongsw    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmStoragePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.StoreDTO;


/**
 * 仓库实现类
 * 
 * @author zhengcong
 * @date 2017年3月21日
 */
@Service
public class StoreServiceImpl implements StoreService {

	/**
	 * 查询
     * @author zhengcong
     * @date 2017年3月21日
	 */
	@Override
	public PageInfoDto searchStore(Map<String, String> queryParam) throws ServiceBizException{
		StringBuilder sb=new StringBuilder("select DEALER_CODE,STORAGE_CODE,STORAGE_NAME,STORAGE_TYPE,LEAD_TIME, "); 
		sb.append("(case when IS_NEGATIVE=12781001 then 10571001 END ) IS_NEGATIVE,(case when OEM_TAG=12781001 then 10571001 END ) OEM_TAG,");
		sb.append("(case when WORKSHOP_TAG=12781001 then 10571001 END ) WORKSHOP_TAG from tm_storage where 1=1 ");
		List<Object> storeSql=new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("storage_code"))){
			sb.append(" and STORAGE_CODE like ?");
			storeSql.add("%"+queryParam.get("storage_code")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("storage_name"))){
			sb.append(" and STORAGE_NAME like ? ");
			storeSql.add("%"+queryParam.get("storage_name")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("oem_tag"))){
			sb.append(" and OEM_TAG = ? ");
			storeSql.add(Integer.parseInt(queryParam.get("oem_tag")));
		}

		PageInfoDto id=DAOUtil.pageQuery(sb.toString(), storeSql);
		return id;
	}



	  /**
	 * TODO 根据storagecode查询详细信息
	 * 
	 * @author zhengcong
	 * @date 2017年3月21日
	 */
  @Override
  public Map<String, String> findByStorageCode(String STORAGE_CODE) throws ServiceBizException {
      StringBuilder sb = new StringBuilder("select DEALER_CODE,STORAGE_CODE,STORAGE_NAME,STORAGE_TYPE,LEAD_TIME, "); 
		sb.append("(case when IS_NEGATIVE=12781001 then 10571001 END ) IS_NEGATIVE,(case when OEM_TAG=12781001 then 10571001 END ) OEM_TAG,");
		sb.append("(case when WORKSHOP_TAG=12781001 then 10571001 END ) WORKSHOP_TAG from tm_storage where 1=1 ");
      sb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
      sb.append(" and storage_code = ? ");
      List queryParam = new ArrayList();
      queryParam.add(STORAGE_CODE);
      return DAOUtil.findFirst(sb.toString(), queryParam);
  }



	/**
	 * 更新
     * @author zhengcong
     * @date 2017年3月21日
	 */


	@Override
	public void updateStore(String STORAGE_CODE, StoreDTO storedto) throws ServiceBizException{
	    TmStoragePO lap=TmStoragePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),STORAGE_CODE);

		    lap.setString("STORAGE_NAME", storedto.getStorage_name());

		    lap.setDouble("LEAD_TIME", storedto.getLead_time());
			if (!StringUtils.isNullOrEmpty(storedto.getIs_negative())) {
				 lap.setDouble("IS_NEGATIVE", 12781001);
				
			} else {
				 lap.setDouble("IS_NEGATIVE", 12781002);
			}
			if (!StringUtils.isNullOrEmpty(storedto.getWorkshop_tag())) {
				 lap.setDouble("WORKSHOP_TAG", 12781001);
				
			} else {
				 lap.setDouble("WORKSHOP_TAG", 12781002);
			}
		   

		    lap.saveIt();

	}

	
	  /**
	  * 查询仓库下拉列
	 * @author zhengcong
	 * @date 2017年4月10日
	 */
	 	
	 @Override
	 public List<Map> queryList() throws ServiceBizException {
		 StringBuffer sqlsb = new StringBuffer(" select DEALER_CODE,STORAGE_CODE,STORAGE_NAME from tm_storage where 1=1 ");
		
	     	
		 List<Map>  dataList=DAOUtil.findAll(sqlsb.toString(),null);
	      return dataList;
	  }



  

}