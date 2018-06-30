package framework.service.Impl.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import framework.common.CollectionUtils;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.dao.auth.AuthorityRepository;
import framework.dao.auth.ResourceAuthorityRepository;
import framework.dao.auth.ResourceRepository;
import framework.entity.po.auth.Resource;
import framework.entity.po.auth.ResourceAuthority;
import framework.service.interf.auth.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceRepository resourceRepository;

	@Autowired
	ResourceAuthorityRepository resourceAuthorityRepository;
	@Autowired
	AuthorityRepository  authorityRepository;

	public Resource saveResource(Resource resource) {
		return resourceRepository.save(resource);
	}

	@Transactional
	public boolean updAuth(Long resourceId, String authorityIds) {
		String[] strArray = authorityIds.split(",");
		List<String> nowList = Arrays.asList(strArray);

		List<Long> dbidList = new ArrayList<Long>();
		Query<ResourceAuthority> query = new Query<>();
		query.add(Select.eq("resourceId", resourceId));
		List<ResourceAuthority> raList = resourceAuthorityRepository.findAll(query);
		if (CollectionUtils.isNotEmpty(raList)) {
			for (ResourceAuthority resourceAuthority : raList) {
				dbidList.add(resourceAuthority.getAuthorityId());
				if (!nowList.contains(resourceAuthority.getAuthorityId().toString())) {
					resourceAuthorityRepository.delete(resourceAuthority);
				}
			}
		}
		
		List<ResourceAuthority> newraList=new ArrayList<ResourceAuthority>();
		for (String string : nowList) {
			if(StringUtils.isBlank(string)){
				continue;
			}
			if(!dbidList.contains(Long.valueOf(string))){
				ResourceAuthority resourceAuthority=new ResourceAuthority();
				resourceAuthority.setResource(
				resourceRepository.findOne(resourceId));
				resourceAuthority.setAuthority(
				authorityRepository.findOne(Long.valueOf(string)));
				newraList.add(resourceAuthority);
				
			}		
		}
		if(CollectionUtils.isNotEmpty(newraList)){
			resourceAuthorityRepository.save(newraList);
		}
		
		return true;
	}

}
