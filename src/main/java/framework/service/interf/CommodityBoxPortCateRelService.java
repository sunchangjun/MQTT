package framework.service.interf;

import java.util.List;

import framework.entity.po.CommodityBoxPortCateRel;



public interface CommodityBoxPortCateRelService {
	/**
	 * 保存列表
	 * @param list
	 * @return
	 */
	List<CommodityBoxPortCateRel> saveCommodityBoxPortCateRelList(List<CommodityBoxPortCateRel> list);
	
	/**
	 * 保存单个
	 * @param rel
	 * @return
	 */
	CommodityBoxPortCateRel saveCommodityBoxPortCateRel( CommodityBoxPortCateRel rel);

}
