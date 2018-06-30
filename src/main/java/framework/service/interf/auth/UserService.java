package framework.service.interf.auth;

import framework.entity.po.auth.User;

public interface UserService {
	/**
	 * 保存用户实体
	 * @param user
	 * @return
	 */
	User saveUser(User user);
	/**
	 * 更新管理员用户组
	 * @param userId
	 * @param ids
	 * @return
	 */
	boolean updGroup(Long  userId ,String  ids);

}
