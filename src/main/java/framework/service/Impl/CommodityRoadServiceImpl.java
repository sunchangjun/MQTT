package framework.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import framework.common.CollectionUtils;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.BoxRepository;
import framework.dao.CommodityRepository;
import framework.dao.CommodityRoadRepository;
import framework.dao.ReplenishmentPlanCommodityRepository;
import framework.dao.ReplenishmentPlanRepository;
import framework.entity.po.Box;
import framework.entity.po.Commodity;
import framework.entity.po.CommodityRoad;
import framework.entity.po.ReplenishmentPlan;
import framework.entity.po.ReplenishmentPlanCommodity;
import framework.enums.IsDeleteEnum;
import framework.service.interf.CommodityRoadService;

@Service
public class CommodityRoadServiceImpl implements CommodityRoadService {

	@Autowired
	private CommodityRoadRepository commodityRoadRepository;

	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private CommodityRepository commodityRepository;

	@Autowired
	private ReplenishmentPlanRepository replenishmentPlanRepository;

	@Autowired
	private ReplenishmentPlanCommodityRepository replenishmentPlanCommodityRepository;

	@Override
	public CommodityRoad saveCommodityRoad(CommodityRoad commodityRoad) {

		CommodityRoad returnCommodityRoad = null;

		returnCommodityRoad = commodityRoadRepository.save(commodityRoad);
		return returnCommodityRoad;
	}

	/**
	 * 保存列表
	 * 
	 * @param commodityRoadList
	 * @return
	 */
	public boolean saveCommodityRoadList(List<CommodityRoad> commodityRoadList) {
		List<CommodityRoad> dbCommodityRoadList = commodityRoadRepository.save(commodityRoadList);
		if (CollectionUtils.isNotEmpty(dbCommodityRoadList)) {
			return true;
		}
		return false;
	}

	/**
	 * 绑定或跟换货道商品
	 * 
	 * @param boxCode
	 * @param code
	 * @param commodityId
	 * @return
	 */
	@Transactional
	public CommodityRoad updateCommodityRoad(String boxCode, String code, Long commodityId, Integer num) {
		CommodityRoad returnCommodityRoad = null;

		Box box = boxRepository.findByBoxCode(boxCode);

		// 查询商品货道
		CommodityRoad dbCommodityRoad = commodityRoadRepository.findByBoxAndCode(box, code);
		if (null != dbCommodityRoad) {
			if (null != dbCommodityRoad.getCommodity()) {
				// 货道有货需要还商品库存
				if (dbCommodityRoad.getCommodityNum() > 0 && null != dbCommodityRoad.getCommodity().getCommodityId()) {
					Commodity dbCommodity = commodityRepository
							.findOne(dbCommodityRoad.getCommodity().getCommodityId());
					dbCommodity.setStocks(dbCommodity.getStocks() + dbCommodityRoad.getCommodityNum());
					commodityRepository.save(dbCommodity);
				}
			}
			// 修改货道商品
			Commodity commodity = null;
			if (null != commodityId) {
				commodity = commodityRepository.findOne(commodityId);
			}
			dbCommodityRoad.setCommodity(commodity);
			dbCommodityRoad.setCommodityNum(num);
			returnCommodityRoad = commodityRoadRepository.save(dbCommodityRoad);
			return returnCommodityRoad;
		}
		return returnCommodityRoad;
	}

	/**
	 * 生成补货清单
	 * 
	 * @param boxCode
	 * @return
	 */
	@Transactional
	public boolean generateReplenishmentPlan(String boxCode) {
		Integer count = 1;// commodityRoadRepository.getCommodityRoadCountByBoxCode(boxCode);
		if (count > 0) {
			Box box = boxRepository.findByBoxCode(boxCode);
			List<CommodityRoad> crList = box.getCommodityRoads();
			ReplenishmentPlan replenishmentPlan = new ReplenishmentPlan();
			replenishmentPlan.setCreateTime(new Date().getTime());
			replenishmentPlan.setExecutionStatus(0);// 未执行
			replenishmentPlan.setIsEnable(1);
			replenishmentPlan.setBoxPort(crList.get(0).getBoxPort());
			replenishmentPlanRepository.save(replenishmentPlan);
			List<ReplenishmentPlanCommodity> rpcList = new ArrayList<ReplenishmentPlanCommodity>();
			for (int i = 0; i < crList.size(); i++) {
				CommodityRoad commodityRoad = crList.get(i);
				if (commodityRoad.getCommodityNum() < 5) {
					ReplenishmentPlanCommodity replenishmentPlanCommodity = new ReplenishmentPlanCommodity();
					replenishmentPlanCommodity.setReplenishmentPlan(replenishmentPlan);
					Commodity c = commodityRoad.getCommodity();
					replenishmentPlanCommodity.setCommodity(c);
					replenishmentPlanCommodity.setCommodityRoadId(commodityRoad.getCommodityRoadId());
					replenishmentPlanCommodity.setCommodityNum(5 - commodityRoad.getCommodityNum());
					rpcList.add(replenishmentPlanCommodity);
				}
			}
			replenishmentPlanCommodityRepository.save(rpcList);
			return true;
		}
		return false;
	}

}
