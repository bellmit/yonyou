package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.BasicCodeDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrBaseCodeDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrBaseCodePO;
/**
 * 代码维护
 * @author Administrator
 *
 */
@Service
public class BasicCodeDaoServiceImpl implements BasicCodeService{
		@Autowired
		BasicCodeDao   basicCodeDao;
					/**
					 * 通过代码类型进行查询数据
					 */
			
			@Override
			public PageInfoDto BasicCodeQuery(Map<String, String> queryParam) {
				// TODO Auto-generated method stub
			return basicCodeDao.BasicCodeQuery(queryParam);
			}

			/**
			 * 新增代码维护
			 */
		   @Override
		  public Long addBasicCode(TtWrBaseCodeDTO ptdto) {
			   TtWrBaseCodePO ptPo=new TtWrBaseCodePO();
				    setApplyPo(ptPo,ptdto);
				    return ptPo.getLongId();
		}
			private void setApplyPo(TtWrBaseCodePO ptPo, TtWrBaseCodeDTO ptdto) {
				   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				   if(ptdto.getBaseCode().length()<=2){
					   
				
					   ptPo.setString("OEM_COMPANY_ID",loginInfo.getCompanyId());
					   ptPo.setString("BASE_CODE", ptdto.getBaseCode());
					   ptPo.setString("BASE_NAME",ptdto.getBaseName());
					   ptPo.setInteger("BASE_TYPE",ptdto.getBaseType());
					   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
					   ptPo.setDate("UPDATE_DATE", new Date());
					   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
					   ptPo.setDate("CREATE_DATE", new Date());  
					   ptPo.setInteger("VER",0);
					   ptPo.setInteger("IS_DEL",0);
					   ptPo.setInteger("IS_DOWN",0);
					   ptPo.saveIt();
				   }else{
					   throw new ServiceBizException("代码不能超过两个字符！"); 
				   }
			}
			
			/**
			 * 修改代码维护
			 */
			@Override
			public void edit(Long id,TtWrBaseCodeDTO ptdto) {
			 	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		   	 TtWrBaseCodePO ptPo = TtWrBaseCodePO.findById(ptdto.getBaseId());
			   if(ptdto.getBaseCode().length()<=2){
			   ptPo.setString("BASE_CODE", ptdto.getBaseCode());
			   ptPo.setString("BASE_NAME",ptdto.getBaseName());
			   ptPo.setInteger("BASE_TYPE",ptdto.getBaseType());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.setInteger("IS_DOWN",0);
			   ptPo.saveIt();
			   }else{
				   throw new ServiceBizException("代码不能超过两个字符！"); 
			   }	
			}
		/**
		 * 通过id进行信息回显
		 */
		@Override
			public TtWrBaseCodePO getBasicCodeById(Long id) {
				// TODO Auto-generated method stub
				return TtWrBaseCodePO.findById(id);
			}

		/**
		 * 删除故障代码
		 */
	@Override
		public void delete(Long id) {
		   	 TtWrBaseCodePO ptPo = TtWrBaseCodePO.findById(id);
		   	if(ptPo!=null){
			   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
			   ptPo.saveIt();
		   	}
		}






}
