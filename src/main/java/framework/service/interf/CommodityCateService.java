package framework.service.interf;

import framework.entity.po.CommodityCate;

public interface CommodityCateService {
	CommodityCate addOrUpdateCommodityCate(CommodityCate commodityCate);
	
	boolean delete(Long commodityCateId) ;
}
