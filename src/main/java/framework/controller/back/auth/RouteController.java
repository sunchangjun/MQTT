package framework.controller.back.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class RouteController {

	@RequestMapping("/route/**")
	public String route(HttpServletRequest request) {
		String url = request.getServletPath();
		String hash = "";
		if (url.length() > 7) {
			hash += "#" + url.substring(7);
		}
		return "redirect:/front/admin/index" + hash;
	}

}
