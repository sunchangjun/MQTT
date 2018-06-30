package framework.service.Impl.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import framework.dao.auth.MenuRepository;
import framework.entity.po.auth.Menu;
import framework.service.interf.auth.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	MenuRepository menuRepository;
	
	public Menu saveMenu(Menu menu){	
		return menuRepository.save(menu);
	}

}
