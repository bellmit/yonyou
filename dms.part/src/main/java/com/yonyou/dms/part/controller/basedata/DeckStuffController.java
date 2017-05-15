
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : DeckStuffController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月5日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.basedata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.part.service.basedata.DeckStuffService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月5日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/deckStuff")
public class DeckStuffController {
    
    @Autowired
    private DeckStuffService deckStuffController;
    
    /**
     * 
     * @author dingchaoyu
     * @date 2016年7月6日
     * @param queryParam
     * @return 查询结果
     */
     @RequestMapping(value = "/querydetal",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto querySoPartDetail(@RequestParam Map<String, String> map) {
         PageInfoDto pageInfoDto = deckStuffController.querySoPartDetail(map);
         return pageInfoDto;
     }
     
     /**
      * 
      * @author dingchaoyu
      * @date 2016年7月6日
      * @param queryParam
      * @return 查询结果
      */
      @RequestMapping(value = "/querySoNo",method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto queryServiceOrderBySoNo(@RequestParam Map<String, String> map) {
          PageInfoDto pageInfoDto = deckStuffController.queryServiceOrderBySoNo(map);
          return pageInfoDto;
      }
      
}
