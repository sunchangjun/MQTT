package framework.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.auth.Menu;
import framework.entity.po.auth.User;

public interface MenuRepository extends BaseRepository<Menu, Long> {

	@Query(value = "select distinct gm.menu from Group g, in(g.groupUserList) gu, in(g.groupMenuList) gm where gu.user = ?1")
	List<Menu> findMenuByUser(User user);
	
	
	@Query("select distinct  gm.menu   from Group g ,in(g.groupMenuList) gm where g.groupId=?1")
	List<Menu> getMenuByGroupId(Long  groupId);

}
