package framework.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;


/**
 * 对ehcache进行配置,分布式时,redis+ehcache,单实例时直接使用ehcache,通过condition决定
 * <p>
 * 通过 @Cacheable 配置，spring会自动检测缓存
 * 通过 @CacheEvict 配置, 对缓存进行清空
 * Note:
 * 1. ehcache是内存氏缓存,没有分布式的时候速度快,但是分布式很难部署,很容易出错
 * 2. redis是支持分布式的,基于socket读写,无分布式时略慢于ehcache
 * 3. 使用ehcache,@Cacheable 对象的value参数要与ehcache.xml配置文件中对应一致
 * <p>
 * Created by zeyuphoenix on 16/6/16.
 */
@Configuration  //自动配置
@EnableCaching  // 开启cache,默认ehcache,如果引用shiro-ehcache会自动产生cache,和这个冲突,不使用spring配置自动加载
public class EhcacheConfiguration extends CachingConfigurerSupport implements BeanDefinitionRegistryPostProcessor {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * 过期时间设为0，表示永不过期.
     */
    public static final int EXPIRE_TIME = 1800;


    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(EhcacheConfiguration.class);

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * spring 的cache, 单独部署使用ehcache,分布式使用redis
     */
  /*  @Bean
    @Override
    public CacheManager cacheManager() {

        return ehcacheCacheManager();
    }*/

    /**
     * CompositeCacheManager用于组合CacheManager，即可以从多个CacheManager中轮询得到相应的Cache
     */
    /*@Bean
    @Override
    public CacheManager cacheManager() {
        Set<CacheManager> caches = new LinkedHashSet<>();
        caches.add(ehcacheManager());
        if (redisCacheManager != null) {
            caches.add(redisCacheManager);
        }

        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(caches);
        // 先从第一个cacheManager中查找有没有cacheName的cache，如果没有接着查找第二个，如果最后找不到，
        // 因为fallbackToNoOpCache=true，那么将返回一个NOP的Cache
        compositeCacheManager.setFallbackToNoOpCache(true);
        return compositeCacheManager;
    }*/

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object param : params) {
                sb.append(param.toString());
            }
            return sb.toString();
        };
    }

    /**
     * ehcache管理器
     */
    @Bean(name = "ehcacheCacheManager")
    @ConditionalOnMissingBean
    public EhCacheCacheManager ehcacheCacheManager() {
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
        return cacheManager;
    }

    /**
     * 根据shared与否的设置,Spring通过new 和 create() 的方式创建manager
     */
    @Bean(name = "ehCacheManagerFactoryBean")
    @ConditionalOnMissingBean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);

        return cacheManagerFactoryBean;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
        logger.info("Invoke Method postProcessBeanFactory");

        // 因为shiro的filter实现了BeanPostProcessor接口,它会在第一个初始化,这个时候ehcache还没有初始化,导致
        // cache错误,两个办法,一个是写在shiro的configuration的类中,另外一个则是先在这里把ehcache的bean初始化
        // 目前采用第一种
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        logger.info("Invoke Method postProcessBeanDefinitionRegistry");
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================

}
