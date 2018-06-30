package framework.controller.back.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import framework.common.CollectionUtils;
import framework.common.JSONExt;
import framework.common.JSONText;
import framework.common.TianDianPage;
import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.auth.MenuRepository;
import framework.entity.po.auth.Menu;
import framework.security.MySecurityContextHandle;
import framework.service.interf.auth.MenuService;

@RestController
@RequestMapping("/back/auth/menu")
public class MenuController {
	@Autowired
	MenuService menuService;

	@Autowired
	MenuRepository menuRepository;

	@RequestMapping({ "/add", "/edit" })
	public Result add(Menu menu) {
		if (null == menu || StringUtils.isBlank(menu.getTitle()) || StringUtils.isBlank(menu.getUrl())) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONObject jo = null;
		try {
			if (null != menu.getParentId()) {
				menu.setMenu(menuRepository.findOne(menu.getParentId()));
			}
			Menu dbMenu = new Menu();
			dbMenu = menuService.saveMenu(menu);
			if (null != dbMenu) {
				jo = JSONExt.toJSON(dbMenu);
			}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}

		return Result.setData(true, jo);
	}

	@RequestMapping("/del")
	public Result del(Long menuId) {
		if (null == menuId) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean bool = false;
		try {
			menuRepository.delete(menuId);
			bool = true;
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, bool);
	}

	@RequestMapping("/getList")
	public Result getList(String title, String url, Long parentId, Integer page, Integer rows) {
		if (null == page || null == rows) {
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		TianDianPage tp = new TianDianPage();
		try {
			Query<Menu> query = new Query<Menu>();
			if (StringUtils.isNotBlank(title)) {
				query.add(Select.eq("title", title));
			}
			if (StringUtils.isNotBlank(url)) {
				query.add(Select.eq("url", url));
			}
			if (null != parentId) {
				query.add(Select.eq("parentId", parentId));
			}
			Pageable pageable = new PageRequest(page - 1, rows);
			Page<Menu> pages = menuRepository.findAll(query, pageable);
			JSONArray ja = new JSONArray();
			if (CollectionUtils.isNotEmpty(pages.getContent())) {
				for (Menu menu : pages.getContent()) {
					JSONObject jo=JSONExt.toJSON(menu);
					if(null  != menu.getMenu() ) {
						jo.put("prentName", menu.getMenu().getTitle());
					}					
					ja.add(jo);
				
				}
			}
			tp.setContent(ja);
			tp.setCurrentPage(pages.getNumber());
			tp.setPageCount(pages.getTotalPages());
			tp.setTotal(pages.getTotalElements());
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, tp);
	}

	@RequestMapping("/getUserLoginMenu")
	public Result getUserLoginMenu() {
		String menuStr = MySecurityContextHandle.getMenuStr();
		if (StringUtils.isBlank(menuStr))
			menuStr = "[]";
		return Result.setData(true, menuStr);
	}

}
