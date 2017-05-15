package com.yonyou.dms.manage.controller.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.OrganizationTreeDto;
import com.yonyou.dms.manage.service.basedata.org.OemDeptService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
 * 初始化部门信息
 * @author 夏威
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/oemDept")
public class OemDeptController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PositionController.class);
	@Autowired
	OemDeptService service;
	
    /**
     * 
    * 获取厂端部门信息
    * @author 夏威
    * @date 2017年3月2日
    * @return
     */
    @RequestMapping(value="/getOemDept", method=RequestMethod.GET)
    @ResponseBody
	public List<OrganizationTreeDto> getOemDeptTree(){
    	logger.info("============厂端部门树加载==================");
    	
		List<Map> list = service.getOemDeptTree();
        List<OrganizationTreeDto> orgList = new ArrayList<>(); 
        for(int i=0;i<list.size();i++){
            OrganizationTreeDto orgTreeOrg = new OrganizationTreeDto();
            orgTreeOrg.setId((String)list.get(i).get("ORG_ID").toString());
            String parent = "#";
            if(!StringUtils.isNullOrEmpty(list.get(i).get("PARENT_ORG_ID"))){
                parent = (String)list.get(i).get("PARENT_ORG_ID").toString();
            }
            orgTreeOrg.setParent(parent);
            orgTreeOrg.setText((String)list.get(i).get("ORG_NAME").toString());
            orgList.add(orgTreeOrg);
        }
        
        return orgList;
	}
    
    
    /**
     * 
    * 更具code查询
    * @author ron
    * @date 2017年3月2日
    * @param orgCode
    * @return
     */
    @RequestMapping(value = "/{orgCode}", method = RequestMethod.GET)
    @ResponseBody
    public Map  getOrgByCode(@PathVariable(value = "orgCode") String orgCode){
        return service.getOrgByCode(orgCode);
    }
	
	
}
