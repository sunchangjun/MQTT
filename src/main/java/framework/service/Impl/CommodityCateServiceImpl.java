package framework.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framework.dao.CommodityCateRepository;
import framework.entity.po.CommodityCate;
import framework.service.interf.CommodityCateService;
@Service
public class CommodityCateServiceImpl implements CommodityCateService {
	@Autowired
	CommodityCateRepository commodityCateRepository;
	
	public  CommodityCate addOrUpdateCommodityCate(CommodityCate commodityCate) {
		CommodityCate dbCommodityCate=commodityCateRepository.save(commodityCate);
		return dbCommodityCate;
		
	}
	
	/**
	 * 删除
	 * @param commodityCateId
	 * @return
	 */
	public boolean delete(Long commodityCateId) {
		commodityCateRepository.delete(commodityCateId);	
		return true;
	}

}
