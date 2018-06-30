package framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import framework.common.SpringUtil;
import framework.core.web.listener.InstantiationTracingBeanPostProcessor;
import framework.core.web.listener.WebContextListener;
@EnableTransactionManagement
@SpringBootApplication
public class ServerBootApplication extends SpringBootServletInitializer {
	private static final Logger logger = LoggerFactory.getLogger(ServerBootApplication.class);
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(ServerBootApplication.class);
		// 加载自动扫描的quartz listener
		// springApplication.addListeners(new
		// QuartzConfiguration.AutoConfigurationQuartzListener());
		// 打印版本信息,检查数据库启动
		springApplication.addListeners(new WebContextListener());
		// bean都初始化完成后的操作
		springApplication.addListeners(new InstantiationTracingBeanPostProcessor());
		SpringUtil.setApplicationContext(springApplication.run(args));
		
		logger.info("framework is starting...");
	}
}
