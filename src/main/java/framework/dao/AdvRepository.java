package framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.Adv;
import framework.entity.po.ImageResource;

/**
 * 广告
 * @author Administrator
 *
 */

public interface AdvRepository extends BaseRepository<Adv, Long> {
	@Query(value="select a.imageResource from Adv a group by a.imageResource.imageResourceId order by a.type,a.sortOrder")
	List<ImageResource> findImgs();
	
	@Query("select max(a.sortOrder)  from Adv a")
	Integer  getMaxSort();
}
