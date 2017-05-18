/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.CLDMS009Dto;
import com.yonyou.dms.common.domains.PO.basedata.TmScBrandPo;
import com.yonyou.dms.common.domains.PO.basedata.TmScModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TmScSeriesPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 *  0 失败  1 成功
 * 业务描述：二手车品牌 车系 车型 一个接口下发。
 * @author xhy
 * @date 2017年2月14日
 */
@SuppressWarnings("unused")
@Service
public class CLDMS009CoudImpl implements CLDMS009Coud {

	@SuppressWarnings("rawtypes")
	@Override
	public int getCLDMS009(LinkedList<CLDMS009Dto> voList,String dealerCode) throws ServiceBizException {
		
		try{
			
			//判断是否为空,循环操作，根据业务相应的修改数据
			if (voList != null && voList.size() > 0){
				List<String> listBrand =new ArrayList<String>();
				List<String> listSeries =new ArrayList<String>();
				for (int i = 0; i < voList.size(); i++){
					CLDMS009Dto dto = new CLDMS009Dto();
					dto = (CLDMS009Dto)voList.get(i);
					//品牌
					if (Utility.testString(dto.getBrandCode())){
						if (!listBrand.contains(dto.getBrandCode())){
							listBrand.add(dto.getBrandCode());
							/*TmScBrandPo po1 = new TmScBrandPo();
							po1.setString("BRAND_CODE",dto.getBrandCode());
							po1.setString("DEALER_CODE", dealerCode);*/
							List list1=TmScBrandPo.findBySQL("select *  from tm_sc_brand where dealer_code=? and brand_code=?", new Object[]{dealerCode,dto.getBrandCode()});
						
							TmScBrandPo po2 = TmScBrandPo.findByCompositeKeys(dto.getBrandCode(),dealerCode);;
							po2.setString("BRAND_NAME",dto.getMakeName()+"_"+dto.getBrandName());
							po2.setLong("IS_VALID",12781001);
							if (null != list1 && list1.size()>0){
								po2.setString("UPDATED_BY",1L);
								po2.setDate("UPDATED_AT",Utility.getCurrentDateTime());
								//po1.saveIt();
								po2.saveIt();
							}else {
								po2.setString("DEALER_CODE", dealerCode);
								po2.setString("BRAND_CODE",dto.getBrandCode());
								po2.setString("CREATED_BY",1L);
								po2.setDate("CREATED_AT", Utility.getCurrentDateTime());
								po2.saveIt();
							}
						}
					}	
					
					//车系
					if (Utility.testString(dto.getSeriesCode())){
						if (!listSeries.contains(dto.getSeriesCode())){						
							listSeries.add(dto.getSeriesCode());
//							TmScSeriesPO po3 = new TmScSeriesPO();
//							po3.setString("SERIES_CODE",dto.getSeriesCode());
//							po3.setString("DEALER_CODE", dealerCode);
							List list2=TmScSeriesPO.findBySQL("select *  from tm_sc_series where dealer_code=? and series_code=?", new Object[]{dealerCode,dto.getSeriesCode()});
						
							TmScSeriesPO po4 = TmScSeriesPO.findByCompositeKeys(dto.getSeriesCode(),dto.getBrandCode(),dealerCode);
							po4.setString("SERIES_NAME",dto.getSeriesName());
							po4.setString("BRAND_CODE",dto.getBrandCode());
							po4.setLong("IS_VALID",12781001);
							if (null != list2 && list2.size()>0){
								po4.setDate("UPDATED_AT",Utility.getCurrentDateTime());
								po4.setString("UPDATED_BY",1L);
								//po3.saveIt();
								po4.saveIt();
							}else {
								po4.setString("DEALER_CODE", dealerCode);
								po4.setString("SERIES_CODE",dto.getSeriesCode());
								po4.setString("CREATED_BY",1L);
								po4.setDate("CREATED_AT", Utility.getCurrentDateTime());
								po4.saveIt();
							}
						}
					}
					
					//车型
					if (Utility.testString(dto.getModelCode())){
//						TmScModelPO po5 = new TmScModelPO();
//						po5.setString("MODEL_CODE",dto.getModelCode());
//						po5.setString("DEALER_CODE", dealerCode);
						List list2=TmScModelPO.findBySQL("select *  from tm_sc_model where dealer_code=? and model_code=?", new Object[]{dealerCode,dto.getModelCode()});
						
						TmScModelPO po6 =TmScModelPO.findByCompositeKeys(dto.getBrandCode(),dto.getModelCode(),dealerCode);
						po6.setString("MODEL_NAME",dto.getModelName()+dto.getModelYear());
						po6.setString("BRAND_CODE",dto.getBrandCode());
						po6.setString("SERIES_CODE",dto.getSeriesCode());
//						po6.setIsValid(12781001);//品牌车系的不设置。车型的要设置有效无效的vo.getEnable();10041001 有效
						if(dto.getEnable()!=null && dto.getEnable()==10041001){
							po6.setLong("IS_VALID",12781001);	
						}else{
							po6.setLong("IS_VALID",12781002);
						}
						
						if (null != list2 && list2.size()>0){
							po6.setString("UPDATED_BY",1L);
							po6.setDate("UPDATED_AT",Utility.getCurrentDateTime());
							//po5.saveIt();
							po6.saveIt();
						}else {
							po6.setString("DEALER_CODE", dealerCode);
					        po6.setString("MODEL_CODE",dto.getModelCode());
							po6.setString("CREATED_BY",1L);
							po6.setDate("CREATED_AT", Utility.getCurrentDateTime());
							po6.saveIt();
						}
					}
					
				}		
					
			}
			
			return 1;
		}catch(Exception e){
			return 0;
		}
		

	}

}
