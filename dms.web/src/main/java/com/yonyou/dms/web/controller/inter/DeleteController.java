package com.yonyou.dms.web.controller.inter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.web.domains.DTO.inter.FileDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/delete")
public class DeleteController extends BaseController{
	
	@RequestMapping(value="file",method = RequestMethod.POST)
	public ResponseEntity<FileDto> deleteFile(@RequestBody @Valid FileDto dto, UriComponentsBuilder uriCB) {
		Map<String, Object> map= new HashMap<>();
		try {
			File deleteFile = new File(dto.getFilePath());
			System.out.println(dto.getFilePath());
			map.put("STATUS", 1);
			if(deleteFile.exists()) {
				boolean flag = deleteFile.delete();
				if(flag!=true){
				throw new ServiceBizException("文件删除失败");
				}
			} else {
				throw new ServiceBizException("文件不存在");
			}
		}catch (Exception e){
			throw new ServiceBizException("文件删除失败",e);
		}
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/users/{id}").buildAndExpand(loginInfo.getUserId()).toUriString());
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
}
}