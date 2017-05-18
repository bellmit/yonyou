/**
 * 
 */
package com.yonyou.dms.manage.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmOrderFormulasDefinedPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.FormulaDefineDTO;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings({"rawtypes","unused","unchecked"})
public class FormulaDefineServiceImpl implements FormulaDefineService {

	/**
	 * 查询所有
	 */
	@Override
	public PageInfoDto findAll(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,ITEM_ID,ORDER_PLAN_FORMULAS_NAME,FORMULAS_CODE,IS_AVAILABLE,EXP FROM TM_ORDER_FORMULAS_DEFINED WHERE 1=1 ");
		Utility.sqlToLike(sb, queryParam.get("orderPlanFormulasName"), "ORDER_PLAN_FORMULAS_NAME", null);
		Utility.sqlToEquals(sb, queryParam.get("exp"), "EXP", null);
		Utility.sqlToEquals(sb, queryParam.get("isAvailable"), "IS_AVAILABLE", null);
		sb.append(" ORDER BY EXP DESC");
		
		return DAOUtil.pageQuery(sb.toString(),null);
	}

	/**
	 * 将DTO转换成PO对象
	 * @param dto
	 * @param po
	 */
	private void dto2po(FormulaDefineDTO dto,TmOrderFormulasDefinedPO po) throws ServiceBizException {
		po.setLong("EXP", dto.getExp());
		po.setString("FORMULAS_CODE", dto.getFormulasCode());
		po.setInteger("IS_AVAILABLE", dto.getIsAvailable());
		po.setString("ORDER_PLAN_FORMULAS_NAME", dto.getOrderPlanFormulasName());
	}

	/**
	 * 新增方法
	 */
	@Override
	public void addFormulaDefine(FormulaDefineDTO dto) throws ServiceBizException {
		TmOrderFormulasDefinedPO po = new TmOrderFormulasDefinedPO();
		dto2po(dto, po);
		po.saveIt();
	}

	/**
	 * 修改前查询
	 */
	@Override
	public Map findById(String id) {
		String sql = "SELECT DEALER_CODE,ITEM_ID,ORDER_PLAN_FORMULAS_NAME,FORMULAS_CODE,IS_AVAILABLE,EXP FROM TM_ORDER_FORMULAS_DEFINED WHERE ITEM_ID = ?";
		List queryParam = new ArrayList<>();
		queryParam.add(id);
		return DAOUtil.findFirst(sql, queryParam);
	}

	/**
	 * 修改操作
	 */
	@Override
	public void editFormulaDefine(String id, FormulaDefineDTO dto) throws ServiceBizException {
		TmOrderFormulasDefinedPO po = TmOrderFormulasDefinedPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		if(po!=null){
			dto2po(dto, po);
			po.saveIt();
		}
	}

	/**
	 * 删除操作
	 */
	@Override
	public void deleteFormulaById(String id) throws ServiceBizException {
		TmOrderFormulasDefinedPO po = TmOrderFormulasDefinedPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		po.delete();
	}
}
