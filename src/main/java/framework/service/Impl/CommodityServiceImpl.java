package framework.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import framework.dao.CommodityRepository;
import framework.entity.po.Commodity;
import framework.service.interf.CommodityService;



@Service
public class CommodityServiceImpl implements CommodityService {
	
	@Autowired
	private CommodityRepository commodityRepository;
	
	
	/**
	 * 保存商品信息
	 * @param commodity
	 * @return
	 */
	public Commodity saveCommodity(Commodity commodity){	
		Commodity dbCommodity=	commodityRepository.save(commodity);
		return dbCommodity;
	}

}
