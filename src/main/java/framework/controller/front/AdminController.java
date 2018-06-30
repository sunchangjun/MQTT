package framework.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front/admin")
public class AdminController {

	@RequestMapping("/login")
	public String login() {
		return "admin/login";
	}

	@RequestMapping("/index")
	public String index() {
		return "admin/index";
	}

}
