package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.PutStorageDetailDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPutstorageDetailPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * DMS->DCS
 * 经销商入库接收接口
 * return msg 0 error 1 success
 * @author luoyang
 *
 */
@Service
public class SADCS027CloudImpl extends BaseCloudImpl implements SADCS027Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS027CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Override
	public String handleExecutor(List<PutStorageDetailDTO> dto) throws Exception {
		logger.info("logger.info ->>> 配件入库明细接收[DMS->DCS], start..");
		String msg = "1";
		logger.info("====配件入库来源监控报表数据上报接收开始====");
		for (PutStorageDetailDTO vo : dto) {

			try {
				// 配件入库信息写入[TtPutstorageDetailPO]报表用
				boolean flag = insertVo(vo);
				if(!flag){
					msg = "0";
				}
				// 更新电商配件交货单入库日期
				this.updateTtPtDeliver(vo);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接收异常", e);
				msg = "0";
				//throw new ServiceBizException(e);
			}
		}

		return msg;

	}

	/**
	 * 更新电商配件交货单入库日期
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void updateTtPtDeliver(PutStorageDetailDTO vo) {

		try {

			logger.info("logger.info ->>> 配件入库明细接收[DMS->DCS]交货单入库日期更新, start..");

			if (null != vo.getDeliverNo() && !"".equals(vo.getDeliverNo())) {

				TtPtDeliverDcsPO.update("PUT_WAREHOUS_DATE=?,PW_OP_DATE=?", "DELIVER_NO=?", 
						vo.getPutStorageDate(),new Date(), vo.getDeliverNo());
			}

			logger.info("logger.info ->>> 配件入库明细接收[DMS->DCS]交货单入库日期更新, success!");

		} catch (Exception e) {
			logger.error("logger.error ->>> 配件入库明细接收[DMS->DCS]交货单入库日期更新, exception!", e);
			throw new ServiceBizException(e);
		} finally {
			logger.info("logger.info ->>> 配件入库明细接收[DMS->DCS]交货单入库日期更新, finish.");
		}

	}

	private boolean insertVo(PutStorageDetailDTO vo) {

		logger.info("====经销商配件入库明细报表数据上报接收开始====");

		try {

			Map<String, Object> map = dao.getSeDcsDealerCode(vo.getEntityCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
			TtPutstorageDetailPO po = null;
			if (null != findById(vo, dealerCode)) {
				Long updatedocumentId = findById(vo, dealerCode);
				po = TtPutstorageDetailPO.findById(updatedocumentId);
				po.setLong("UPDATE_BY", 11111111L);// 创建人
				po.setTimestamp("UPDATE_DATE", new Date());// 创建日期
			} else {
				po = new TtPutstorageDetailPO();
				po.setLong("CREATE_BY", 11111111L);// 创建人
				po.setTimestamp("CREATE_DATE", new Date());// 创建日期
			}
			po.setString("DEALER_CODE", dealerCode);// 经销商代码
			po.setInteger("PUT_STORAGE_TYPE", vo.getPutStorageType());// 入库类型
			po.setTimestamp("PUT_STORAGE_DATE", vo.getPutStorageDate());// 入库日期
			po.setString("PUT_STORAGE_NUM", vo.getPutStorageNum());// 入库单号
			po.setString("SUPPLIER_NAME", vo.getSupplierName());// 供应商名称
			po.setString("STORAGE_CODE", vo.getStorageCode());// 仓库代码
			po.setString("STORAGE_NAME", vo.getStorageName());// 仓库名称
			po.setString("PART_CODE", vo.getPartCode());// 配件代码
			po.setString("PART_NAME", vo.getPartName());// 配件名称
			po.setInteger("DNP_PRICE", vo.getDnpPrice());// DNP价格
			po.setDouble("NUMBERS", Double.parseDouble(
					vo.getNumbers().equals("") || vo.getNumbers() == null ? "0" : vo.getNumbers().toString()));// 数量
			po.setDouble("PRICE", vo.getPrice());// 行价
			boolean flag = po.saveIt();
			logger.info("====经销商配件入库明细报表数据上报接收结束====");
			return flag;
		} catch (Exception e) {
			logger.error("经销商配件入库明细报表数据上报接收失败", e);
			throw new ServiceBizException(e);
		}

	}

	/**
	 * 查询当前数据是否存在
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private Long findById(PutStorageDetailDTO vo, String dealerCode) {

		Long documentId = 0L;
		try {
			String sql = new String(" SELECT T.PUT_STORAGE_ID FROM TT_PUTSTORAGE_DETAIL T " + " WHERE T.DEALER_CODE = '"
					+ dealerCode + "' " + " AND T.PUT_STORAGE_TYPE = " + vo.getPutStorageType() + " "
					+ " AND T.PUT_STORAGE_NUM = '" + vo.getPutStorageNum() + "' " + " AND T.PART_CODE = '"
					+ vo.getPartCode() + "' " + " AND date_format(T.PUT_STORAGE_DATE,'YYYY-MM-DD') = '"
					+ new SimpleDateFormat("yyyy-MM-dd").format(vo.getPutStorageDate()) + "' ");
			List<Map> list = OemDAOUtil.findAll(sql, null);

			if (null != list && list.size() > 0) {
				for (Map map : list) {
					documentId = (Long) map.get("PUT_STORAGE_ID");
					return documentId;
				}

			}
		} catch (Exception e) {
			logger.error("配件入库来源监控报表数据上报校验数据失败", e);
			throw new ServiceBizException(e);
		}
		return null;

	}

}
