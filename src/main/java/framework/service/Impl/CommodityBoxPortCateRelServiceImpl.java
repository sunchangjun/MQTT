package framework.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framework.dao.CommodityBoxPortCateRelRepository;
import framework.entity.po.CommodityBoxPortCateRel;
import framework.service.interf.CommodityBoxPortCateRelService;
@Service
public class CommodityBoxPortCateRelServiceImpl implements CommodityBoxPortCateRelService {
	@Autowired
	CommodityBoxPortCateRelRepository commodityBoxPortCateRelRepository;
	
	public List<CommodityBoxPortCateRel> saveCommodityBoxPortCateRelList(List<CommodityBoxPortCateRel> list){
		List<CommodityBoxPortCateRel> returnList=commodityBoxPortCateRelRepository.save(list);	
		return returnList;		
	}
	
	public CommodityBoxPortCateRel saveCommodityBoxPortCateRel( CommodityBoxPortCateRel rel) {
		CommodityBoxPortCateRel dbCommodityBoxPortCateRel=commodityBoxPortCateRelRepository.save(rel);	
		return dbCommodityBoxPortCateRel;
	}

}
