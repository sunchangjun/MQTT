package framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import framework.dao.auth.ResourceAuthorityRepository;
import framework.entity.po.auth.Authority;
import framework.entity.po.auth.Resource;
import framework.entity.po.auth.ResourceAuthority;

@Service
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	ResourceAuthorityRepository resourceAuthorityRepository;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	/**
	 * 加载资源
	 * 
	 * @throws Exception
	 */
	@Transactional
	public void loadResource() throws Exception {
		if (resourceMap != null)
			return;

		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<ResourceAuthority> list = resourceAuthorityRepository.findAll();
		if (null != list && !list.isEmpty()) {
			for (ResourceAuthority ra : list) {
				Resource r = ra.getResource();
				Authority a = ra.getAuthority();
				String url = r.getUrl();

				Collection<ConfigAttribute> atts = resourceMap.get(url);
				if (null == atts) {
					atts = new ArrayList<ConfigAttribute>();
				}
				atts.add(new SecurityConfig(a.getAuthorityCode()));
				resourceMap.put(url, atts);
			}
		}
	}

	/**
	 * 更改权限
	 */
	public synchronized static void changeAuthority(String url, ArrayList<String> v) {
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
		for (String au : v) {
			ConfigAttribute ca = new SecurityConfig(au);
			atts.add(ca);
		}
		if (v.size() == 0)
			resourceMap.remove(url);
		else
			resourceMap.put(url, atts);
	}

	/**
	 * 获取权限
	 * 
	 * @param object:需要获取权限的资源
	 */
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

		if (null == resourceMap) {
			try {
				loadResource();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		Iterator<String> ite = resourceMap.keySet().iterator();
		AntPathRequestMatcher matcher;
		while (ite.hasNext()) {
			String resURL = ite.next();
			matcher = new AntPathRequestMatcher(resURL);
			if (matcher.matches(request)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
}