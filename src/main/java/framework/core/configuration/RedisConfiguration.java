package framework.core.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import framework.core.condition.DistributedCondition;


/**
 * 对redis进行配置
 * <p>
 * 通过 @Cacheable 配置，spring会自动检测到redis的缓存配置
 * 通过 @CacheEvict 配置, 对缓存进行清空
 * Note:
 * 1. ehcache是内存氏缓存,没有分布式的时候速度快,但是分布式很难部署,很容易出错
 * 2. redis是支持分布式的,基于socket读写,无分布式时略慢于ehcache
 * 3. 使用ehcache,@Cacheable 对象的value参数要与ehcache.xml配置文件中对应一致
 * <p>
 * Created by zeyuphoenix on 16/6/16.
 */
@Configuration  //自动配置
@Conditional(DistributedCondition.class)      //分布式时候才用redis,否则使用ehcache,不加载configuration的配置
@AutoConfigureBefore(EhcacheConfiguration.class)
public class RedisConfiguration {
	 private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    // ================================================================
    // Constants
    // ================================================================

    /**
     * 过期时间设为0，表示永不过期.
     */
    private static final int EXPIRE_TIME = 1800;

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

    @Bean(name = "redisCacheManager" )
    @Primary
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        //设置key-value超时时间
        cacheManager.setDefaultExpiration(EXPIRE_TIME);
        return cacheManager;
    }

    /**
     * 更改 RedisTemplate 的序列化方式
     * 如果没有指定序列化方式,默认使用的是 JdkSerializationRedisSerializer
     *
     * @param factory redis 连接池
     * @return redis连接模板
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        // 构建template
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 设置序列化工具，这样ReportBean不需要实现Serializable接口
        // 转换器,使用自定义的,加载自定义属性
        // 因为shiro的session和我们的user都特别复杂,存在很多不规范get和set,这样转换会失败
        // JsonMapper om = new JsonMapper();
        // om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);

        // 设置key序列化方式为字符串
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // 设置value序列化方式为JSON
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        
        logger.info(template.toString());
        
        
        return template;
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
