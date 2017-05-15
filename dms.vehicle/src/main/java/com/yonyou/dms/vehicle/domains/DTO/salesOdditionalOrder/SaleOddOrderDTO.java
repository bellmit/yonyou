package com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder;

import java.util.List;

public class SaleOddOrderDTO {
	
	    //装潢项目
		private List<SoUpholsterDTO> soDecrodateList;
		//精品材料
		private List<SoPartDTO> soDecrodatePartList; 
		//服务项目
		private List<SoServiceDTO> soServicesList;
		
		public List<SoUpholsterDTO> getSoDecrodateList() {
			return soDecrodateList;
		}
		public void setSoDecrodateList(List<SoUpholsterDTO> soDecrodateList) {
			this.soDecrodateList = soDecrodateList;
		}
		public List<SoPartDTO> getSoDecrodatePartList() {
			return soDecrodatePartList;
		}
		public void setSoDecrodatePartList(List<SoPartDTO> soDecrodatePartList) {
			this.soDecrodatePartList = soDecrodatePartList;
		}
		public List<SoServiceDTO> getSoServicesList() {
			return soServicesList;
		}
		public void setSoServicesList(List<SoServiceDTO> soServicesList) {
			this.soServicesList = soServicesList;
		}

}
