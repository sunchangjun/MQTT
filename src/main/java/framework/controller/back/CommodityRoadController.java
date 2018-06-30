package framework.controller.back;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import framework.common.JSONExt;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.BoxPortRepository;
import framework.dao.BoxRepository;
import framework.dao.CommodityRepository;
import framework.dao.CommodityRoadRepository;
import framework.dao.ImageResourceRepository;
import framework.entity.po.CommodityRoad;
import framework.service.interf.CommodityRoadService;
import framework.service.interf.RecycleService;

/**
 * 货道管理
 * 
 * @author sunchangjunn
 *
 */
@Controller
@RequestMapping("/back/commodityRoad")
public class CommodityRoadController {

	private static Logger log = LoggerFactory.getLogger(CommodityRoadController.class);

	@Autowired
	private BoxPortRepository boxPortRepository;

	@Autowired
	private CommodityRoadService commodityRoadService;

	@Autowired
	private CommodityRoadRepository commodityRoadRepository;

	@Autowired
	private RecycleService recycleService;

	@Autowired
	private CommodityRepository commodityRepository;

	@Autowired
	private ImageResourceRepository imageResourceRepository;
	@Autowired
	BoxRepository boxRepository;



	/**
	 * 绑定或修改商品货道商品
	 * 
	 * @param commodity_road_id
	 *            货道编号
	 * @param commodity_id
	 *            商品id
	 * @param commodity_num
	 *            商品数量
	 * @return
	 */
	@RequestMapping(value = "/updateCommodityRoad", method = RequestMethod.POST)
	@ResponseBody
	public Result updateCommodityRoad(String boxCode, String code, Long commodityId,Integer num) {
		// 参数检查
		if (StringUtils.isBlank(boxCode) || null == commodityId || StringUtils.isBlank(code)) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
	
		CommodityRoad returnCommodityRoad = null;
			try {
				returnCommodityRoad = commodityRoadService.updateCommodityRoad(boxCode, code, commodityId,num);
			} catch (Exception e) {
				return Result.setData(false, Code.getMessage(Code.CNUM001));
			}
			JSONObject jo = JSONExt.toJSON(returnCommodityRoad);
		return Result.setData(true, jo);
	}
	
	
	@RequestMapping(value = "/deleteCommodityRoad", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteCommodityRoad(String boxCode, String code) {
		// 参数检查
		if (StringUtils.isBlank(boxCode) ||StringUtils.isBlank(code)) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		CommodityRoad returnCommodityRoad=null;
			try {
				returnCommodityRoad = commodityRoadService.updateCommodityRoad(boxCode, code, null,0);
			} catch (Exception e) {
				return Result.setData(false, Code.getMessage(Code.CNUM001));
			}
			JSONObject jo = JSONExt.toJSON(returnCommodityRoad);
		return Result.setData(true, jo);
	}
	
	/**
	 * 生成补货计划
	 * @param boxCode
	 * @return
	 */
	@RequestMapping(value = "/generateReplenishmentPlan", method = RequestMethod.POST)
	@ResponseBody
	public Result generateReplenishmentPlan(String boxCode) {
		//参数检查
		if(StringUtils.isBlank(boxCode)) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		Boolean bool=false;
		try {
			bool=commodityRoadService.generateReplenishmentPlan(boxCode);
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		
		return Result.setData(true, bool);
	}

}
