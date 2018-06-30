package framework.service.interf.auth;

import framework.entity.po.auth.Resource;

public interface ResourceService {
	/**
	 * 保存权限实体
	 * @param resource
	 * @return
	 */
	Resource saveResource(Resource resource);
	
	/**
	 * 更改权限
	 * @param resourceId
	 * @param authorityIds
	 * @return
	 */
	boolean updAuth(Long resourceId, String authorityIds);

}
