package com.yonyou.dms.repair.controller.basedata;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.BookingLimitDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.PackageTypeDTO;
import com.yonyou.dms.repair.domains.PO.basedata.BookingLimitPO;
import com.yonyou.dms.repair.domains.PO.basedata.PackageTypePO;
import com.yonyou.dms.repair.service.basedata.BookingLimitService;
import com.yonyou.dms.repair.service.basedata.PackageTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 组合类别
 * 
 * @author sunqinghua
 * @date 2017年3月22日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/packageType")
public class PackageTypeController {
    @Autowired
    private PackageTypeService packageTypeService;

    /**
     * 查询组合类别
     * 
     * @author sunqinghua
     * @date 2017年3月22日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPackageType(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        PageInfoDto pageInfoDto = packageTypeService.queryPackageType(queryParam);
        return pageInfoDto;
    }

    /**
	* 简单描述：通过packageTypeCode查询班组信息
	* @author sqh
	* @date 2017年3月22日
	* @param id 组合类别id
	* @return 组合信息
	*/
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map findPackageTypeById(@PathVariable(value = "id") String id) {
		Map packageType = packageTypeService.findPackageTypeById(id);
		return packageType;
	}

    /**
     * 新增组合类别
     * 
     * @author sqh
     * @date 2017年3月22日
     * @param pyto
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PackageTypeDTO> addBookingLimit(@RequestBody PackageTypeDTO pyto,
                                                           UriComponentsBuilder uriCB) throws ServiceBizException {
        String id = packageTypeService.addPackageType(pyto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/packageType/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PackageTypeDTO>(pyto, headers, HttpStatus.CREATED);
    }

    /**
     * 修改组合类别
     * 
     * @author sqh
     * @date 2017年3月22日
     * @param id
     * @param limidto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PackageTypeDTO> modifyPackageType(@PathVariable("id") String id,
                                                              @RequestBody @Valid PackageTypeDTO pyto,
                                                              UriComponentsBuilder uriCB) {
    	packageTypeService.modifyPackageType(id, pyto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/reservationLimit/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PackageTypeDTO>(headers, HttpStatus.CREATED);
    }
}
