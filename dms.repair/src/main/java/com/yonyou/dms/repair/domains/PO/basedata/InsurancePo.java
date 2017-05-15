package com.yonyou.dms.repair.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 保险公司基础表
* @author zhl
* @date 2017年3月24日
*/
	
@Table("tm_insurance")
@CompositePK({"DEALER_CODE","INSURATION_CODE"})
public class InsurancePo extends BaseModel{

}
