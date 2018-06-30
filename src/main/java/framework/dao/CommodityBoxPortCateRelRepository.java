package framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.CommodityBoxPortCateRel;
/**
 * 商品售货机分类表
 * @author Administrator
 *
 */
public interface CommodityBoxPortCateRelRepository extends BaseRepository<CommodityBoxPortCateRel, Long> {
	@Query("select a from  CommodityBoxPortCateRel a, in(a.boxPortCate.boxPorts) b where b.box.boxCode = ?1")
	List<CommodityBoxPortCateRel> findAllByBoxCode(String device);
}
