package framework.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class MySecurityContextHandle {

	/**
	 * 获取用户Id
	 * 
	 * @return
	 */
	public static Long getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null != auth) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((MyUserDetails) principal).getUserId();
			}
		}
		return 1l;
	}

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public static String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null != auth) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
		}
		return null;
	}

	/**
	 * 获取昵称
	 * 
	 * @return
	 */
	public static String getNickName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null != auth) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((MyUserDetails) principal).getNick_name();
			}
		}
		return null;
	}

	/**
	 * 获取菜单
	 * 
	 * @return
	 */
	public static String getMenuStr() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (null != auth) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				return ((MyUserDetails) principal).getMenuStr();
			}
		}
		return null;
	}

}
