
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : PartInventoryController.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月26日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月26日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.controller.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.part.domains.DTO.basedata.PartCodesDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryItemDTO;
import com.yonyou.dms.part.domains.PO.basedata.PartInventoryItemPO;
import com.yonyou.dms.part.service.basedata.PartInventoryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 对配件盘点单的基本操作
 * @author ZhengHe
 * @date 2016年7月26日
 */

@Controller
@TxnConn
@RequestMapping("basedata/partinventories")
public class PartInventoryController extends BaseController{

    @Autowired
    private PartInventoryService pis;

    @Autowired
    private CommonNoService commonNoService;
    /**
     * 简要描述:根据条件查询配件盘点单信息
     * @author ZhengHe
     * @date 2016年7月26日
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInventory(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto =pis.queryPartInventory(queryParam);
        return pageInfoDto;
    } 

    /**
     * 根据库存新增盘点单及其配件盘点单明细
     * @author ZhengHe
     * @date 2016年7月28日
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> addPartInventory(@RequestBody PartInventoryDTO piDto,UriComponentsBuilder uriCB){
        Integer idNums=getInventoryNumbers(piDto);
        List<PartCodesDTO> partCodes=piDto.getPartCodeList();
        List<PartCodesDTO> partCodess=new ArrayList<PartCodesDTO>();
        List<TtPartInventoryPO> piPoList=new ArrayList<TtPartInventoryPO>();
        int k=0;
        if(piDto.getNumber()==0){
            piPoList.add(pis.addPartInventory(partCodes,piDto,commonNoService.getSystemOrderNo("SL")));
            MultiValueMap<String,String> headers = new HttpHeaders();  
            headers.set("Location", uriCB.path("/basedata/partinventories/{inventoryId}").buildAndExpand(piPoList.get(0).getLongId()).toUriString());  
            return new ResponseEntity<Map<String,Object>>(piPoList.get(0).toMap(),headers, HttpStatus.CREATED);  
        }
        for (int i = 0; i < idNums; i++) {
            if(partCodes.size()<=piDto.getNumber()){
                partCodess.addAll(partCodes);
            }else{
                for(int j=k;j<piDto.getNumber()+k;j++){
                    if(j==partCodes.size()){
                        break;
                    }
                    partCodess.add(partCodes.get(j));
                }
            }
            if(partCodes.size()>0){
                piPoList.add(pis.addPartInventory(partCodess,piDto,commonNoService.getSystemOrderNo("SL")));
            }
            k=Integer.parseInt(piDto.getNumber().toString())+k;
            partCodess.clear();
        }
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/partinventories/{inventoryId}").buildAndExpand(piPoList.get(0).getLongId()).toUriString());  
        return new ResponseEntity<Map<String,Object>>(piPoList.get(0).toMap(),headers, HttpStatus.CREATED);  
    }
    
    
    /**
    * 盘点单添加配件
    * @author ZhengHe
    * @date 2016年10月25日
    * @param id
    * @param piDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value="/{inventoryId}/item",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> addPartItemById(@PathVariable(value = "inventoryId") Long id,@RequestBody PartInventoryDTO piDto,UriComponentsBuilder uriCB){
        List<PartCodesDTO> partCodes=piDto.getPartCodeList();
        pis.addPartInventoryById(id, partCodes, piDto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/partinventories/{inventoryId}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<Map<String,Object>>(headers, HttpStatus.CREATED);  
    }

    /**
     * 简要描述:根据条件查询配件盘点单中配件信息
     * @author ZhengHe
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/querypart/items/select",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto quertPartInventoryItem(@RequestParam Map<String, String> queryParam){
        PageInfoDto pageInfoDto=pis.queryPartInventoryItem(queryParam);
        return pageInfoDto;
    }

    /**
     * 
     * 根据id获取盘点单信息
     * @author ZhengHe
     * @date 2016年7月28日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{inventoryId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPartInventoryById(@PathVariable(value = "inventoryId") Long id) {
    	TtPartInventoryPO partinventoryPo=pis.queryPartInventoryById(id);
        return partinventoryPo.toMap();
    }
    
    /**
     * 
     * 根据id获取盘点单打印信息
     * @author ZhengHe
     * @date 2016年12月07日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{inventoryId}/print", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPartInventoryPrintById(@PathVariable(value = "inventoryId") Long id) {
    	TtPartInventoryPO partinventoryPo=pis.queryPartInventoryById(id);
        Map<String,Object> newMap=partinventoryPo.toMap();
        newMap.put("PRINT_RO_TIME", new Date());
        return newMap;
    }
    
    /**
     * 
     * 根据Id获取配件盘点单明细
     * @author ZhengHe
     * @date 2016年7月28日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{inventoryId}/item",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInventoryItemById(@PathVariable(value = "inventoryId") Long id,@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto=pis.queryPartInventoryItemById(id,queryParam);
        return pageInfoDto;
    } 

    /**
     * 根据Id获取配件盘点单明细--打印
     * @author ZhengHe
     * @date 2016年12月07日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{inventoryId}/item/print",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryPartInventoryItemPrintById(@PathVariable(value = "inventoryId") Long id) {
    	List<Map> partList=pis.queryPartInventoryItemPrintById(id);
        return partList;
    } 
    
    /**
     * 根据Id获取配件盘点单明细(配件报溢)
     * @author xukl
     * @date 2016年8月15日
     * @param id
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/{inventoryId}/profititem",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryProfitItemById(@PathVariable(value = "inventoryId") Long id,@RequestParam Map<String, String> queryParam) {
        List<Map> list=pis.qryInventoryItemById(id);
        return list;
    } 
    /**
     * 对盘点单进行修改
     * @author ZhengHe
     * @date 2016年8月2日
     * @param piDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value="/{status}",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PartInventoryDTO> modifyPartInventoryItem(@PathVariable("status") String status,@RequestBody PartInventoryDTO piDto, UriComponentsBuilder uriCB){
        pis.modifyPartInventory(status,piDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/partinventories/{id}").buildAndExpand(piDto.getPartInventoryId()).toUriString());  
        return new ResponseEntity<PartInventoryDTO>(piDto,headers, HttpStatus.CREATED);  
    }

    /**
     * 根据id删除盘点单信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{inventoryId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartInventoryById(@PathVariable("inventoryId") Long inventoryId,UriComponentsBuilder uriCB) {
        pis.deletePartInventoryById(inventoryId);
    }

    /**
     * 根据id删除盘点单明细信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param inventoryId
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{inventoryId}/item/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartInventoryItemById(@PathVariable("inventoryId") Long inventoryId,@PathVariable("id") Long id) {
        pis.deletePartInventoryItemById(inventoryId,id);
    }

    /**
     * 
     * 根据id获取盘点单信息
     * @author ZhengHe
     * @date 2016年7月28日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{inventoryId}/item/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPartInventoryByItemId(@PathVariable(value = "inventoryId") Long inventoryId,@PathVariable("id") Long id) {
        PartInventoryItemPO piiPo=pis.queryPartInventoryByItemId(inventoryId, id);
        return piiPo.toMap();
    }

    /**
     * 修改盘点明细信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param piDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{inventoryId}/item/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PartInventoryItemDTO> modifyPartInventoryItemNoIs(@PathVariable(value = "inventoryId") Long inventoryId,@PathVariable("id") Long id,@RequestBody PartInventoryItemDTO piiDto, UriComponentsBuilder uriCB){
        pis.modifyPartInventoryItem(inventoryId, id, piiDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/partinventories/{inventoryId}/item/{id}").buildAndExpand(inventoryId,id).toUriString());  
        return new ResponseEntity<PartInventoryItemDTO>(piiDto,headers, HttpStatus.CREATED);  
    }

    /**
     * 获取配件库存数量
     * @author ZhengHe
     * @date 2016年8月2日
     * @return
     */
    public Double getStockQuantity(List<PartCodesDTO> partCodes){
        return pis.getStockQuantity(partCodes);
    }

    /**
     * 获取盘点单数
     * @author ZhengHe
     * @date 2016年8月2日
     * @param piDto
     * @return
     */
    public Integer getInventoryNumbers(PartInventoryDTO piDto){
        Integer number=piDto.getNumber();//获取每个盘点单需要的配件数量
        if(number==0){
            return 1;
        }
        List<PartCodesDTO> partCodes=piDto.getPartCodeList();
//        BigDecimal stockQuantity=BigDecimal.valueOf(getStockQuantity(partCodes));//获取所有配件数量
//        BigDecimal idNum=NumberUtil.div(stockQuantity, BigDecimal.valueOf(number), 0);//计算需要盘点单数
//        BigDecimal idNumb=NumberUtil.div(stockQuantity, BigDecimal.valueOf(number), 6);
//        Integer idNums=Integer.parseInt(idNum.toString());
//        if(idNum.compareTo(idNumb)==-1){
//            idNums=idNums+1;
//        } 
        Integer stockQuantity=piDto.getPartCodeList().size();
        BigDecimal idNum=NumberUtil.div(BigDecimal.valueOf(stockQuantity), BigDecimal.valueOf(number), 0);//计算需要盘点单数
        BigDecimal idNumb=NumberUtil.div(BigDecimal.valueOf(stockQuantity), BigDecimal.valueOf(number), 6);
        Integer idNums=Integer.parseInt(idNum.toString());
        if(idNum.compareTo(idNumb)==-1){
            idNums=idNums+1;
        } 
       
        return idNums;
    }
    /**
    * 查询配件盘点明细
    * @author jcsi
    * @date 2016年8月23日
    * @param id
    * @return
     */
    @RequestMapping(value = "/{id}/items",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryItem(@PathVariable(value = "id") Long id,@RequestParam Map<String, String> queryParam) {
        return pis.queryItem(id,queryParam);
    } 
}
