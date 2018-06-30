package framework.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.auth.Authority;
import framework.entity.po.auth.User;

public interface AuthorityRepository extends BaseRepository<Authority, Long> {

	@Query(value = "select distinct ga.authority from Group g, in(g.groupUserList) gu, in(g.groupAuthorityList) ga where gu.user = ?1")
	List<Authority> findAuthorityByUser(User user);
	
	@Query("select ga.authority  from Group g,in(g.groupAuthorityList ) ga where  g.groupId=?1")
	List<Authority> getAuthorityListByGroupId(Long groupId);
	
	@Query("select  distinct ra.authority  from  Resource  r,in(r.resourceAuthorityList) ra  where  r.resourceId=?1")
	List<Authority> getAuthorityByResourceId(Long resourceId);

}
