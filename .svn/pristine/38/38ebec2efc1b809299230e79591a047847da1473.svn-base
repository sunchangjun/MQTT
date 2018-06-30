package framework.core.configuration;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import org.beetl.core.Function;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import framework.core.common.Constant;
import framework.core.function.I18n;
import framework.core.servlet.InitServlet;
import framework.core.servlet.ValidateCodeServlet;
import framework.core.utils.JsonMapper;
import framework.core.utils.SpringContextHolder;


@Configuration
public class WebMVCConfiguration extends WebMvcConfigurerAdapter implements Constant{


    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(WebMVCConfiguration.class);

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        super.addInterceptors(registry);
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截

//        // 手机视图拦截器
//        registry.addInterceptor(new MobileInterceptor()).addPathPatterns("/**");
//        // 全局信息视图拦截器
//        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**");
//
//        // 拦截器配置，拦截顺序：先执行后定义的，排在第一位的最后执行
//        // 日志拦截器
//        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/login",
//                "/sys/menu/tree", "/sys/menu/treeData");
    }

    /**
     * 静态文件请求匹配规则
     * 通过addResourceHandlers(ResourceHandlerRegistry registry) 方法可以为应用程序中位于classpath路径下
     * 或文件系统下的静态资源配置对应的URL，供其他人通过浏览器访问
     */
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
         configurer.favorPathExtension(false);
     }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        // 路径模式串,URL请求 ---> 从哪个目录下加载资源文件
        registry.addResourceHandler("/internal/**").
                addResourceLocations("classpath:/cache/");
        // Spring Boot 默认配置的/**映射到/static（或/public ，/resources，/META-INF/resources），
        // /webjars/**会映射到classpath:/META-INF/resources/webjars/
        // 因为shiro默认放行/static,所以这里添加了一个映射(好吧,其实主要是为了开发页面时,js都是相当路径)
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // 默认实现mvc已经添加, 如果使用了@EnableWebMvc需要自己写
        //registry.addResourceHandler("/favicon.ico").addResourceLocations("/static/favicon.ico");

        // SpringBoot默认已经将classpath:/META-INF/resources/和classpath:/META-INF/resources/webjars/映射
        // 所以该方法不需要重写
        //registry.addResourceHandler("swagger-ui.html")
        //        .addResourceLocations("classpath:/META-INF/resources/");
        //registry.addResourceHandler("/webjars/**")
        //       .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 不是所有的URL请求都遵循默认的规则
     * 设置 RESTful URL匹配的时候包含定界符“.”
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        // 系统对外暴露的URL不会识别和匹配.*后缀
        // 表示系统不区分URL的最后一个字符是否是斜杠/
        configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
    }

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        super.addFormatters(registry);
//
//        // 测试用,把user注册
//        registry.addFormatter(new FrameworkInfoFormatter());
//    }

    /**
     * 加入自定义转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(stringHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(customJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 使用自定义mapper实现jackson
     */
    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        // 自定义mapper
        JsonMapper objectMapper = new JsonMapper();
        jsonConverter.setObjectMapper(objectMapper);
        // 关闭打印输出
        jsonConverter.setPrettyPrint(false);
        List<MediaType> supportedMediaTypes = Lists.newArrayList();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        return jsonConverter;
    }

//    /**se
//     * 修改默认返回编码
//     */
//    @Bean
//    @Primary
//    public StringHttpMessageConverter stringHttpMessageConverter() {
//        return new StringHttpMessageConverter(Charset.forName(CHARSET_UTF_8));
//    }

    /**
     * Beetl视图文件解析
     */
    @Bean(name = "viewResolver")
    public BeetlSpringViewResolver beetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setPrefix("/views/");
        beetlSpringViewResolver.setSuffix(".html");
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);

        return beetlSpringViewResolver;
    }



    /**
     * 注册chartSet filter,类似配置文件 filter and mapping
     */
    @Bean
    public FilterRegistrationBean characterEncodingFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new CharacterEncodingFilter("encodingFilter"));
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("encoding", "UTF-8");
        filterRegistration.addInitParameter("forceEncoding", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }



    /**
     * 上传配置,spring提供方式处理
     * 直接参数使用 @RequestParam("name") String name, @RequestParam("file") MultipartFile file
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 最大文件大小,-1为无限制
        factory.setMaxFileSize(10485760);
        factory.setMaxRequestSize(10485760);
        // 上传后的目录名
        //factory.setLocation("/upload");
        return factory.createMultipartConfig();
    }

    /**
     * 基于 commons-upload 上传方式
     * 上传文件拦截，设置最大上传文件大小 1000M=1000*1024*1024(B)=10485760 bytes
     */
    /* @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() throws IOException {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();

        // 默认编码 (ISO-8859-1)
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        // 最大内存大小 (10240)
        commonsMultipartResolver.setMaxInMemorySize(10240);
        // 上传后的目录名 (WebUtils#TEMP_DIR_CONTEXT_ATTRIBUTE)
        commonsMultipartResolver.setUploadTempDir(new ClassPathResource("/upload"));
        // 最大文件大小,-1为无限制
        commonsMultipartResolver.setMaxUploadSize(10485760);

        return commonsMultipartResolver;
    }*/

    /**
     * 初始化一个默认servlet,完成一些自定义工作
     */
    @Bean
    public ServletRegistrationBean initServlet() {
        InitServlet initServlet = new InitServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(initServlet);
        List<String> urlMappings = Lists.newArrayList();
        //访问，可以添加多个
        urlMappings.add("/servlet/initServlet/none");
        registrationBean.setUrlMappings(urlMappings);
        registrationBean.setLoadOnStartup(2);
        logger.info("init default servlet.");
        return registrationBean;
    }

    /**
     * 验证码servlet
     */
    @Bean
    public ServletRegistrationBean validateCodeServlet() {
        ValidateCodeServlet validateCodeServlet = new ValidateCodeServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(validateCodeServlet);
        List<String> urlMappings = Lists.newArrayList();
        //访问，可以添加多个
        urlMappings.add("/servlet/validateCodeServlet");
        registrationBean.setUrlMappings(urlMappings);
        registrationBean.setLoadOnStartup(3);
        return registrationBean;
    }

    /**
     * 定义视图文件解析配置器
     */
    @Bean(name = "beetlConfig", initMethod = "init")
    public BeetlGroupUtilConfiguration beetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();

        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());

        String root;
        try {
            root = patternResolver.getResource("classpath:templates").getFile().toString();
        } catch (IOException e) {
            // logger.error("resolver templates error: ", e);
            root = new ClassPathResource("classpath:templates").getPath();
        }
        if (root.contains("WEB-INF")) {
            // web形式加载
            WebAppResourceLoader webAppResourceLoader = new WebAppResourceLoader(root);
            beetlGroupUtilConfiguration.setResourceLoader(webAppResourceLoader);
        } else {
            // jar包形式加载
            ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader("templates");
            beetlGroupUtilConfiguration.setResourceLoader(classpathResourceLoader);
        }

        // 加载配置文件
        setBeetlConfiguration(beetlGroupUtilConfiguration);
        //beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
        return beetlGroupUtilConfiguration;

    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    /**
     * 配置文件中定义的方法和tag不受spring的bean管理,这里在代码中注册
     */
    private void setBeetlConfiguration(BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        // 基本属性
        Properties properties = new Properties();
        // 起止标志
        properties.setProperty("DELIMITER_STATEMENT_START", "<!--:");
        properties.setProperty("DELIMITER_STATEMENT_END", "-->");
        // classpath 跟路径
        properties.setProperty("RESOURCE.root", "");
        // 自定义标签文件位置
        properties.setProperty("RESOURCE.tagRoot", "tag");
        // 自定义标签文件后缀
        properties.setProperty("RESOURCE.tagSuffix", "html");
        beetlGroupUtilConfiguration.setConfigProperties(properties);

        // 注册函数
        Map<String, Function> functions = Maps.newHashMap();
        // 国际化函数
        functions.put("i18n", SpringContextHolder.getBean(I18n.class));
        beetlGroupUtilConfiguration.setFunctions(functions);

        // 注册函数包
        Map<String, Object> functionPackages = Maps.newHashMap();
        // common的功能包
//        functionPackages.put("commontag", SpringContextHolder.getBean(CommonExt.class));
//        // dict的功能包
//        functionPackages.put("dicttag", SpringContextHolder.getBean(DictExt.class));
//        // user的功能包
//        functionPackages.put("usertag", SpringContextHolder.getBean(UserExt.class));

        beetlGroupUtilConfiguration.setFunctionPackages(functionPackages);
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================


}
