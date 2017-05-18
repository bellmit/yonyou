/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.ProductModelPriceDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmColorPo;
import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 0 失败 1 成功 业务描述：车型价格数据下发
 * 
 * @author xhy
 * @date 2017年2月14日
 */
@Service
public class CLDMS003CoudImpl implements CLDMS003Coud {
	final Logger logger = Logger.getLogger(CLDMS003CoudImpl.class);

	@SuppressWarnings("rawtypes")
	@Override

	public int getCLDMS003(LinkedList<ProductModelPriceDTO> voList,String dealerCode) throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try{
			if (voList != null && voList.size() > 0){
				for (int i = 0; i < voList.size(); i++){

					ProductModelPriceDTO dto = new ProductModelPriceDTO();
					dto = (ProductModelPriceDTO) voList.get(i);
					// 先往我们颜色字典里面加颜色数据 跟上端确认了
					if (null != dto.getColorCode() && !"".equals(dto.getColorCode())) {
						/*
						 * TmColorPo po4 = new TmColorPo();
						 * po4.setString("DEALER_CODE", dealerCode);
						 * po4.setString("COLOR_CODE",dto.getColorCode());
						 */

						List<Map> list4 = Base.findAll("select *  from tm_color where DEALER_CODE=? AND COLOR_CODE=?",
								new Object[] { dealerCode, dto.getColorCode() });
						TmColorPo colorPO = new TmColorPo();

						if (null != list4 && list4.size() > 0) {
							StringBuffer sql = new StringBuffer();
							sql.append("UPDATE TM_COLOR SET COLOR_NAME='" + dto.getColorName() + "' , OEM_TAG='"
									+ 12781001 + "' ,UPDATED_BY='" + 1L + "'  ");
							sql.append("where DEALER_CODE='" + dealerCode + "' AND COLOR_CODE='" + dto.getColorCode()
									+ "'");
							Base.exec(sql.toString());
						} else {
							try {

								colorPO.setString("DEALER_CODE", dealerCode);
								colorPO.setString("COLOR_CODE", dto.getColorCode());
								colorPO.setString("COLOR_NAME", dto.getColorName());
								colorPO.setLong("OEM_TAG", 12781001);
								// colorPO.setDate("CREATED_AT", new Date());
								colorPO.setString("CREATED_BY", 1L);
								colorPO.insert();
							} catch (Exception e) {
								logger.debug(e);
							}

						}
					}

					/*
					 * VsProductPO po = new VsProductPO();
					 * po.setString("DEALER_CODE", dealerCode);
					 * po.setString("PRODUCT_CODE",dto.getProductCode());
					 * po.setLong("D_KEY",CommonConstants.D_KEY);
					 */
					List<Map> list = Base.findAll(
							"select *  from tm_vs_product where DEALER_CODE=? AND PRODUCT_CODE=? ",
							new Object[] { dealerCode, dto.getProductCode() });

					VsProductPO po1 = new VsProductPO();
					Map modelPo1 = null;
					if (null != dto.getModelCode() && !"".equals(dto.getModelCode())) {
						// 根据车型获取燃油类型，发动机排量
						// TmModelPO modelPoCon = new TmModelPO();

						/*
						 * modelPoCon.setString("DEALER_CODE", dealerCode);
						 * modelPoCon.setString("MODEL_CODE",dto.getModelCode())
						 * ;
						 */
						List<Map> moList = Base.findAll("select * from  tm_model where DEALER_CODE=? AND MODEL_CODE=? ",
								new Object[] { dealerCode, dto.getModelCode() });
						if (moList != null && moList.size() > 0) {
							modelPo1 = moList.get(0);
						}
					}

					if (null != list && list.size() > 0) {
						Date downDate = (Date) ((VsProductPO) list.get(0)).get("DOOWN_STAMP");
						if (downDate != null && downDate.getTime() >= dto.getDownTimestamp().getTime()) {
							// logger.debug("============>>DE下发时序不对，更新失败！");
							continue;// 跳出本次循环
						} else {
							// 这里写修改
							StringBuffer sql = new StringBuffer("");
							sql.append(" UPDATE TM_VS_PRODUCT SET PRODUCT_CODE='" + dto.getProductCode()
									+ "' , OEM_TAG='" + 12781001 + "',PRODUCT_TYPE='" + 10381001 + "',PRODUCT_STATUS='"
									+ 13081001 + "',D_KEY='" + CommonConstants.D_KEY + "',");
							if (null != dto.getProductName() && !"".equals(dto.getProductName())) {
								sql.append(" PRODUCT_NAME='" + dto.getProductName() + "',");
							}
							if (null != dto.getBrandCode() && !"".equals(dto.getBrandCode())) {
								sql.append(" BRAND_CODE='" + dto.getBrandCode() + "',");
							}
							if (null != dto.getSeriesCode() && !"".equals(dto.getSeriesCode())) {
								sql.append(" SERIES_CODE='" + dto.getSeriesCode() + "',");
							}
							if (!StringUtils.isNullOrEmpty(modelPo1)) {
								if (!StringUtils.isNullOrEmpty((String) modelPo1.get("OIL_TYPE"))) {
									sql.append(" OIL_TYPE='" + (String) modelPo1.get("OIL_TYPE") + "',");
								}
								if (!StringUtils.isNullOrEmpty((String) modelPo1.get("EXHAUST_QUANTITTY"))) {
									sql.append(
											"EXHAUST_QUANTITTY='" + (String) modelPo1.get("EXHAUST_QUANTITTY") + "',");
								}
							}
							if (null != dto.getConfigCode() && !"".equals(dto.getConfigCode())) {
								sql.append(" CONFIG_CODE='" + dto.getConfigCode() + "',");
							}
							if (null != dto.getColorCode() && !"".equals(dto.getColorCode())) {
								sql.append(" COLOR_CODE='" + dto.getColorCode() + "',");
							}

							if (null != dto.getOemDirectivePrice() && !"".equals(dto.getOemDirectivePrice())) {
								sql.append(" OEM_DIRECTIVE_PRICE='" + dto.getOemDirectivePrice() + "',");
							}
							if (null != dto.getDownTimestamp() && !"".equals(dto.getDownTimestamp())){
								sql.append(" DOWN_STAMP='"+df.format(dto.getDownTimestamp())+"',");

							}
							if (null != dto.getPurchasePrice() && !"".equals(dto.getPurchasePrice())) {
								sql.append(" DIRECTIVE_PRICE='" + dto.getPurchasePrice() + "',");
							}
							if (null != dto.getModelYear() && !"".equals(dto.getModelYear())) {
								sql.append(" YEAR_MODEL='" + dto.getModelYear() + "',");
							}
							if (null != dto.getMininumPrice() && !"".equals(dto.getMininumPrice()))
								sql.append(" MININUM_PRICE='" + dto.getMininumPrice() + "',");
							else {
								sql.append(" MININUM_PRICE='" + 0.00 + "',");
							}
							if (dto.getIsValid() != null && dto.getIsValid().equals(10011001)) {
								sql.append(" IS_VALID='" + 12781001 + "',");
							} else {
								sql.append(" IS_VALID='" + 12781002 + "',");
							}
							sql.append(" UPDATED_BY='" + 1L + "' WHERE DEALER_CODE='" + dealerCode
									+ "' AND MODEL_CODE='" + dto.getModelCode() + "'");

							// po1.setDate("UPDATED_AT",Utility.getCurrentDateTime());
							Base.exec(sql.toString());
						}
					} else {
						po1.setString("DEALER_CODE", dealerCode);
						po1.setString("PRODUCT_CODE", dto.getProductCode());
						if (null != dto.getProductName() && !"".equals(dto.getProductName()))
							po1.setString("PRODUCT_NAME", dto.getProductName());
						if (null != dto.getBrandCode() && !"".equals(dto.getBrandCode()))
							po1.setString("BRAND_CODE", dto.getBrandCode());
						if (null != dto.getSeriesCode() && !"".equals(dto.getSeriesCode()))
							po1.setString("SERIES_CODE", dto.getSeriesCode());
						if (null != dto.getModelCode() && !"".equals(dto.getModelCode())) {
							po1.setString("MODEL_CODE", dto.getModelCode());
							// 根据车型获取燃油类型，发动机排量

						}
						if (modelPo1 != null)
							if (!StringUtils.isNullOrEmpty((String) modelPo1.get("OIL_TYPE"))) {
								po1.setString("OIL_TYPE", (String) modelPo1.get("OIL_TYPE"));
							}
						if (!StringUtils.isNullOrEmpty((String) modelPo1.get("EXHAUST_QUANTITTY"))) {
							po1.setString("EXHAUST_QUANTITTY", (String) modelPo1.get("EXHAUST_QUANTITTY"));
						}
						if (null != dto.getConfigCode() && !"".equals(dto.getConfigCode()))
							po1.setString("CONFIG_CODE", dto.getConfigCode());
						if (null != dto.getColorCode() && !"".equals(dto.getColorCode()))
							po1.setString("COLOR_CODE", dto.getColorCode());
						if (null != dto.getDownTimestamp() && !"".equals(dto.getDownTimestamp()))

							po1.setString("DOWN_STAMP",df.format(dto.getDownTimestamp()));

						if (null != dto.getOemDirectivePrice() && !"".equals(dto.getOemDirectivePrice()))
							po1.setString("OEM_DIRECTIVE_PRICE", dto.getOemDirectivePrice());
						if (!StringUtils.isNullOrEmpty(dto.getPurchasePrice()))
							po1.setDouble("DIRECTIVE_PRICE", dto.getPurchasePrice());
						if (null != dto.getMininumPrice() && !"".equals(dto.getMininumPrice()))
							po1.setDouble("MININUM_PRICE", dto.getMininumPrice());
						else {
							po1.setDouble("MININUM_PRICE", 0.00);
						}
						if (null != dto.getModelYear() && !"".equals(dto.getModelYear()))
							po1.setString("YEAR_MODEL", dto.getModelYear());
						if (dto.getIsValid() != null && dto.getIsValid().equals(10011001)) {
							po1.setLong("IS_VALID", 12781001);
						} else {
							po1.setLong("IS_VALID", 12781002);
						}
						po1.setLong("OEM_TAG", 12781001);
						// po1.setIsValid(12781001);
						po1.setLong("PRODUCT_TYPE", 10381001);
						po1.setLong("PRODUCT_STATUS", 13081001);
						po1.setLong("D_KEY", CommonConstants.D_KEY);

						po1.setString("CREATED_BY", 1L);
						// po1.setDate("CREATED_AT",
						// Utility.getCurrentDateTime());
						try {
							po1.insert();	
						} catch (Exception e) {
							// TODO: handle exception
							logger.debug(e);
						}
						
					}

				}

			}

			return 1;
		} catch (Exception e) {

			return 0;

		}

	}
}
