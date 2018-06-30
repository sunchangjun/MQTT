package framework.mqtt;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

@Component
@Order(value = 1)
public class MQttOnline implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(MQttOnline.class);
	@Value("${framework.mq.AccessKey}")
	String AccessKey;

	@Value("${framework.mq.SecretKey}")
	String SecretKey;

	@Value("${framework.mq.ReadTopicBox}")
	String ReadTopicBox;

	@Value("${framework.mq.ReadTopicServer}")
	String ReadTopicServer;

	@Value("${framework.mq.ReadConsumerId}")
	 String ReadConsumerId;

	@Override
	public void run(String... arg0) throws Exception {
		start();
	}

	private void start() {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ConsumerId, ReadConsumerId);
		properties.put(PropertyKeyConst.AccessKey, AccessKey);
		properties.put(PropertyKeyConst.SecretKey, SecretKey);
		Consumer consumer = ONSFactory.createConsumer(properties);
		MessageListener mListener = new MessageListener() {
			public Action consume(Message message, ConsumeContext consumeContext) {
				String event = message.getTag();
				if (event.equals("connect")) {
					connect(new String(message.getBody()));
				} else if (event.equals("disconnect")) {
					disconnect(new String(message.getBody()));
				} else if (event.equals("tcpclean")) {
					disconnect(new String(message.getBody()));
				}
				return Action.CommitMessage;
			}
		};
		consumer.subscribe(ReadTopicBox, "*", mListener);
		consumer.subscribe(ReadTopicServer, "*", mListener);
		consumer.start();
		logger.info("ReadOnline: connectComplete" );
	}
	protected void disconnect(String body) {
		logger.info("disconnect:"+body);
	}
	private void connect(String body) {
		logger.info("connect:"+body);
	}
}
