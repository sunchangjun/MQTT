package framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.Commodity;

public interface CommodityRepository extends BaseRepository<Commodity, Long> {

	@Query(value = "select a.commodity from CommodityBoxPortCateRel a,in(a.boxPortCate.boxPorts) b where b.box.boxCode=?1 group by a.commodity.commodityId")
	List<Commodity> findAllByDevice(String device);

	// 查询柜机下有的商品id
	@Query(value = "select a.commodity.commodityId from CommodityBoxPortCateRel a,in(a.boxPortCate.boxPorts) b where b.box.boxCode=?1 group by a.commodity.commodityId")
	List<Long> getBoxCommodityIdList(String boxCode);

}
