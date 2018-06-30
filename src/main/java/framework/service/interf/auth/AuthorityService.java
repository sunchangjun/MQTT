package framework.service.interf.auth;

import framework.entity.po.auth.Authority;

public interface AuthorityService {
	
	/**
	 * 保存权限实体
	 * @param authority
	 * @return
	 */
	 Authority saveAuthority(Authority authority);

}
