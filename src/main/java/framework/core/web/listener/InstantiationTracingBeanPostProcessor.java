package framework.core.web.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

/**
 * 当spring 容器将所有的bean都初始化完成后，做一个操作（例如：将数据库中的字典，加载到内存中）
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	// ================================================================
	// Constants
	// ================================================================

	// ================================================================
	// Fields
	// ================================================================

	// ================================================================
	// Constructors
	// ================================================================

	// ================================================================
	// Methods from/for super Interfaces or SuperClass
	// ================================================================

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// root application context 没有parent，他就是老大.
		if (event.getApplicationContext().getParent() == null) {
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。

			ApplicationContext applicationContext = event.getApplicationContext();

			if (applicationContext instanceof WebApplicationContext) {
				// 通过web启动
				WebApplicationContext webApplicationContext = (WebApplicationContext) applicationContext;
				registTemplateInfo(webApplicationContext, webApplicationContext.getServletContext());
			}
		}
	}

	// ================================================================
	// Public or Protected Methods
	// ================================================================

	// ================================================================
	// Getter & Setter
	// ================================================================

	// ================================================================
	// Private Methods
	// ================================================================

	/**
	 * 加入Beetl模板全局变量
	 */
	private void registTemplateInfo(ApplicationContext context, ServletContext servletContext) {

		// // 注册模板信息
		BeetlGroupUtilConfiguration config = (BeetlGroupUtilConfiguration) context.getBean("beetlConfig");
		GroupTemplate groupTemplate = config.getGroupTemplate();
		// 模板全局变量
		Map<String, Object> shared = new HashMap<>();
		shared.put("ctx", servletContext.getContextPath());
		shared.put("base", servletContext.getContextPath());
		shared.put("ctxStatic", servletContext.getContextPath() + "/static");
		groupTemplate.setSharedVars(shared);
	}

	// ================================================================
	// Inner or Anonymous Class
	// ================================================================

	// ================================================================
	// Test Methods
	// ================================================================
}
