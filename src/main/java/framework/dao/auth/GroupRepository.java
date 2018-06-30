package framework.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.auth.Group;
import framework.entity.po.auth.User;

public interface GroupRepository extends BaseRepository<Group, Long> {
	
	@Query("select g from  Group g, in(g.groupUserList)  gu where  gu.userId =?1")
	List<Group>  getGroupByUserId(Long  user);

}
