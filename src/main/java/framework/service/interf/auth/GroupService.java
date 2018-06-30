package framework.service.interf.auth;

import framework.entity.po.auth.Group;

public interface GroupService {
	
	/**
	 * 保存群组实体
	 * @param group
	 * @return
	 */
	Group saveGroup(Group group);
	
	/**
	 * 更新组权限
	 * @param groupId
	 * @param authorityIds
	 * @return
	 */
	boolean updAuth(Long groupId, String authorityIds);
	
	
	/**
	 * 更新组菜单
	 * @param groupId
	 * @param ids
	 * @return
	 */
	 boolean  updMenu(Long  groupId,String  ids);

}
