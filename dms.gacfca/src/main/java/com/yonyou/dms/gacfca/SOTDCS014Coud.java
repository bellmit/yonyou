package com.yonyou.dms.gacfca;

import java.text.ParseException;
import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveCarDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDCS014Coud {
	public LinkedList<TiDmsNTestDriveCarDto> getSOTDCS014(String[] driveCarId, String[] itemIdCar, String[] modelCode,
			String[] testCarType, String[] vin, String[] license, String[] status, String[] abateStarttime,
			String[] abateStoptime, String[] remarkCar, String[] status2) throws ServiceBizException, ParseException;
}
