package framework.dao.auth;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.auth.GroupAuthority;

public interface GroupAuthorityRepository extends BaseRepository<GroupAuthority, Long> {
	
	@Query("select  ga from  Group g,in(g.groupAuthorityList)  ga  where  g.groupId=?1")
	List<GroupAuthority>  getGroupAuthorityListByGroupId(Long groupId );

}
