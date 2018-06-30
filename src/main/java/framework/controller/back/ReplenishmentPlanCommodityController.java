package framework.controller.back;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import framework.common.JSONText;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.ReplenishmentPlanCommodityRepository;
import framework.entity.po.ReplenishmentPlanCommodity;

@RestController
@RequestMapping("/back/replenishmentPlanCommodity")
public class ReplenishmentPlanCommodityController {
	
	@Autowired
	ReplenishmentPlanCommodityRepository replenishmentPlanCommodityRepository;
	
	/**
	 * 
	 * @param replenishmentPlanId
	 * @return
	 */
	@PostMapping("/getPageByCeplenishmentPlanId")
	public Result getPageByCeplenishmentPlanId(Long replenishmentPlanId) {
		if(null == replenishmentPlanId ) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONArray ja=new JSONArray();
		try {
			Query<ReplenishmentPlanCommodity> query=new Query<ReplenishmentPlanCommodity>();
			query.add(Select.eq("replenishmentPlan", replenishmentPlanId));
			List<ReplenishmentPlanCommodity> list=replenishmentPlanCommodityRepository.findAll(query);
			if(CollectionUtils.isNotEmpty(list)) {
				for (ReplenishmentPlanCommodity replenishmentPlanCommodity : list) {
				JSONObject jo=	JSONText.JavaBeanToJsonObject(replenishmentPlanCommodity);
				ja.add(jo);		
				}
				
			}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, ja);
	}

}
