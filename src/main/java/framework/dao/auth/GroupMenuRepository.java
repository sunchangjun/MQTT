package framework.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.auth.GroupMenu;

public interface GroupMenuRepository extends BaseRepository<GroupMenu, Long> {
	@Query("select   gm  from  Group g, in(g.groupMenuList) gm where  g.groupId=?1")
	List<GroupMenu> getGroupMenuByGroupId(Long  groupId);
	
	

}
