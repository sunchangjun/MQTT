package framework.core.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;


import framework.core.common.Constant;


public class DistributedCondition implements Condition, Constant {
	private static final Logger logger = LoggerFactory.getLogger(DistributedCondition.class);
	  @Override
	    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
	        Environment environment = conditionContext.getEnvironment();
	        boolean distributed = false;
	        if (environment != null) {
	            distributed = Boolean.valueOf(environment.getProperty(CONFIGURATION_DEFINED_PREFIX + "distributed", "false"));
	        }
	        if (logger.isDebugEnabled()) {
	            logger.debug("系统初始化检测中,系统{}分布式", distributed ? "属于" : "不属于");
	        }
	        return distributed;
	    }

}
