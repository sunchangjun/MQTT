package framework.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import framework.common.SpringUtil;
import framework.lock.RedisLock;
import framework.lock.Lock;
import framework.mqtt.util.IMqttHandler;
import framework.mqtt.util.MThreadPool;
import framework.mqtt.util.MacSignature;
import framework.mqtt.util.RevThreadPool;
import framework.pojo.Request;

@Component
@Order(value = 1)
public class MQttUtil implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(MQttUtil.class);
	@Value("${framework.mq.broker}")
	String broker;

	@Value("${framework.mq.AccessKey}")
	String acessKey;

	@Value("${framework.mq.SecretKey}")
	String secretKey;

	@Value("${framework.mq.topic}")
	String topic;

	@Value("${framework.mq.SERVERID}")
	String SERVERID;

	@Value("${framework.mq.groupID}")
	String groupID;

	@Autowired
	private RedisLock redisLock;

	private MqttClient mqttClient;

	@Override
	public void run(String... arg0) throws Exception {
		start();
	}

	private void start() {
		String sign;
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			mqttClient = new MqttClient(broker, SERVERID, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			logger.info("MQttUtil: " + broker);
			sign = MacSignature.macSignature(SERVERID.split("@@@")[0], secretKey);
			final String[] topicFilters = new String[] { topic + "/toServer/", topic + "/p2p/" };
			final int[] qos = { 0, 0 };
			connOpts.setUserName(acessKey);
			connOpts.setServerURIs(new String[] { broker });
			connOpts.setPassword(sign.toCharArray());
			connOpts.setCleanSession(true);
			connOpts.setKeepAliveInterval(90);
			connOpts.setAutomaticReconnect(true);
			mqttClient.setCallback(new MqttCallbackExtended() {
				public void connectComplete(boolean reconnect, String serverURI) {
					try {
						mqttClient.subscribe(topicFilters, qos);
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}

				public void connectionLost(Throwable throwable) {
					try {
						mqttClient.connect(connOpts);
					} catch (MqttSecurityException e) {
						e.printStackTrace();
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}

				public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
					RevThreadPool.submit(new Runnable() {
						@Override
						public void run() {
							Request bean = Request.toBean(mqttMessage.getPayload());
							if (bean.getData() != null)
								logger.info(bean.getDevice() + ":" + bean.getDataType() + ":"
										+ JSON.toJSONString(new String(bean.getData())));
							String key = "messageArrived:" + bean.getReqid();

							Lock lock = new Lock(key, key);
							// if (redisLock.tryLock(lock)) {
							// try {
							if (bean.getDataType() != null) {
								String handler = bean.getDataType().getHandler();
								IMqttHandler ihandler = SpringUtil.getBean(handler, IMqttHandler.class);
								if (ihandler != null) {
									ihandler.exec(bean);
								}
							}
							// } finally {
							// redisLock.releaseLock(lock);
							// }
							// }
						}
					});
				}

				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
					logger.info("deliveryComplete::" + iMqttDeliveryToken.isComplete());
				}
			});
			mqttClient.connect(connOpts);
			mqttClient.subscribe(topicFilters, qos);
		} catch (Exception me) {
			me.printStackTrace();
		}
	}

	public void sendP2P(Request bean) {
		MThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				MqttMessage message = new MqttMessage(bean.toBytes());
				message.setQos(0);
				String p2pTopic = topic + "/p2p/" + groupID + bean.getDevice();
				try {
					logger.info(bean.getDataType().name(),JSON.toJSONString(new String(bean.getData())));
					if (mqttClient != null) {
						mqttClient.publish(p2pTopic, message);
					}
				} catch (MqttPersistenceException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void sendToCilentAll(Request bean) {
		MThreadPool.submit(new Runnable() {
			@Override
			public void run() {
				MqttMessage message = new MqttMessage(bean.toBytes());
				message.setQos(0);
				String p2pTopic = topic + "/ToCilentAll/";
				try {
					logger.info(p2pTopic + ":" + JSON.toJSONString(new String(bean.getData())));
					if (mqttClient != null) {
						mqttClient.publish(p2pTopic, message);
					}
				} catch (MqttPersistenceException e) {
					e.printStackTrace();
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}
		});

	}

}
