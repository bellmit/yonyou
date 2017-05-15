package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * 
* @ClassName: TtVsInspectionPO 
* @Description:  车辆验收上报（DMS验收后，上报验收明细给上端）
* @author zhengzengliang 
* @date 2017年4月11日 下午6:56:49 
*
 */
@Table("tt_vs_inspection")
@IdName("INSPECTION_ID")
public class TtVsInspectionPO extends OemBaseModel{

}
