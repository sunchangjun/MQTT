package framework.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import framework.dao.auth.AuthorityRepository;
import framework.dao.auth.MenuRepository;
import framework.dao.auth.UserRepository;
import framework.entity.po.auth.Authority;
import framework.entity.po.auth.Menu;
import framework.entity.po.auth.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class MyUserDetailsService implements UserDetailsService {

	protected MessageSourceAccessor messages;
	private String rolePrefix;

	@Autowired
	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
	private MenuRepository menuRepository;

	public MyUserDetailsService() {
		messages = SpringSecurityMessageSource.getAccessor();
		rolePrefix = "";
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userRepository.findByUserName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MyUserDetails udetail = new MyUserDetails();
		if (null == user)
			throw new UsernameNotFoundException(
					messages.getMessage("JdbcDaoImpl.notFound", new Object[] { userName }, "用户 {0} 不存在"));
		AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
		udetail.setUserId(user.getUserId());
		udetail.setUser_name(userName);
		udetail.setUser_pwd(user.getUserPwd());
		udetail.setAccountNonExpired(true);
		udetail.setAccountNonLocked(true);
		udetail.setCredentialsNonExpired(true);
		if (null != user.getEnabled() && user.getEnabled() == 1) {
			udetail.setEnabled(true);
			List<Authority> auths = authorityRepository.findAuthorityByUser(user);
			Set<GrantedAuthority> dbAuths = new HashSet<GrantedAuthority>();
			for (Iterator<Authority> ite = auths.iterator(); ite.hasNext();) {
				Authority a = ite.next();
				GrantedAuthority authority = new SimpleGrantedAuthority(a.getAuthorityCode());
				dbAuths.add(authority);
			}
			udetail.setAuthorities(dbAuths);
			// 加载菜单
			JSONArray ja = new JSONArray();
			try {
				List<Menu> menus = menuRepository.findMenuByUser(user);
				if (null != menus && !menus.isEmpty()) {
					Map<Long, List<Menu>> menuList = new LinkedHashMap<Long, List<Menu>>();
					Map<Long, String> pMenuMap = new HashMap<Long, String>();
					for (Menu menu : menus) {
						Menu parent = menu.getMenu();
						if (null == parent)
							continue;
						List<Menu> cMenuL = menuList.get(parent.getMenuId());
						if (null == cMenuL)
							cMenuL = new ArrayList<Menu>();
						cMenuL.add(menu);
						menuList.put(parent.getMenuId(), cMenuL);
						pMenuMap.put(parent.getMenuId(), parent.getTitle());
					}
					Set<Long> pMenuIdS = menuList.keySet();
					if (null != pMenuIdS && !pMenuIdS.isEmpty()) {
						for (Long pMenuId : pMenuIdS) {
							JSONObject jo1 = new JSONObject();
							jo1.put("menu_id", pMenuId);
							jo1.put("title", pMenuMap.get(pMenuId));
							List<Menu> menuL = menuList.get(pMenuId);
							JSONArray ja1 = new JSONArray();
							if (null != menuL && !menuL.isEmpty()) {
								for (Menu menu : menuL) {
									JSONObject jo2 = new JSONObject();
									jo2.put("menu_id", menu.getMenuId());
									jo2.put("title", menu.getTitle());
									jo2.put("url", menu.getUrl());
									ja1.add(jo2);
								}
							}
							jo1.put("children", ja1);
							ja.add(jo1);
						}
					}
					JSONObject jo = new JSONObject();
					jo.put("rows", ja);
				}
			} catch (Exception e) {
			}
			udetail.setMenuStr(ja.toString());
		} else {
			udetail.setEnabled(false);
		}
		return udetail;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	protected String getRolePrefix() {
		return rolePrefix;
	}

}