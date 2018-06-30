package framework.pojo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import framework.SpringUtil;
import framework.mqtt.util.IMqttHandler;
import framework.mqtt.util.MacSignature;

public class TESTBOX {
	public static void main(String[] args) {
		init("DLA364");
	}
	public static void init(String DLA364) {
		/**
		 * 设置当前用户私有的 MQTT 的接入点。例如此处示意使用 XXX，实际使用请替换用户自己的接入点。接入点的获取方法是，在控制台创建 MQTT
		 * 实例，每个实例都会分配一个接入点域名。
		 */
		final String broker = "tcp://post-cn-mp90lygiw03.mqtt.aliyuncs.com";
		/**
		 * 设置阿里云的 AccessKey，用于鉴权
		 */
		final String acessKey = "LTAIdgZS4Hgl2NOG";
		/**
		 * 设置阿里云的 SecretKey，用于鉴权
		 */
		final String secretKey = "rI2XoTJo3A72oiY3KfZoDcbs69EPlc";
		/**
		 * 发消息使用的一级 Topic，需要先在 MQ 控制台里创建
		 */
		final String topic = "TOPIC_TIANDIAN_BOX";

		/**
		 * MQTT 的 ClientID，一般由两部分组成，GroupID@@@DeviceID 其中 GroupID 在 MQ 控制台里创建 DeviceID
		 * 由应用方设置，可能是设备编号等，需要唯一，否则服务端拒绝重复的 ClientID 连接
		 */
//		final String clientId = "GID_TIANDIAN_SERVER@@@SERVER";

		final String base = "GID_TIANDIAN_BOX@@@";

		String sign;
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			final MqttClient sampleClient = new MqttClient(broker, base+DLA364, persistence);
			final MqttConnectOptions connOpts = new MqttConnectOptions();
			System.out.println("Connecting to broker: " + broker);
			/**
			 * 计算签名，将签名作为 MQTT 的 password。 签名的计算方法，参考工具类 MacSignature，第一个参数是 ClientID
			 * 的前半部分，即 GroupID 第二个参数阿里云的 SecretKey
			 */
			sign = MacSignature.macSignature(base.split("@@@")[0], secretKey);
			connOpts.setUserName(acessKey);
			connOpts.setServerURIs(new String[] { broker });
			connOpts.setPassword(sign.toCharArray());
			connOpts.setCleanSession(true);
			connOpts.setKeepAliveInterval(90);
			connOpts.setAutomaticReconnect(true);
			sampleClient.setCallback(new MqttCallbackExtended() {
				public void connectComplete(boolean reconnect, String serverURI) {
					System.out.println("connect success");
				}
				public void connectionLost(Throwable throwable) {
				}
				public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
					
					Request bean = Request.toBean(mqttMessage.getPayload());
//					if(bean.getDataType()!=null) {
////						String handler=bean.getDataType().getHandler();
////						IMqttHandler ihandler = SpringUtil.getBean(handler, IMqttHandler.class);
////						if(ihandler!=null) {
////							ihandler.exec(bean);
////						}
//						
//					}
					System.out.println(new String(bean.getData()));
					
				}
				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
				}
			});
			sampleClient.connect(connOpts);
		} catch (Exception me) {
			me.printStackTrace();
		}
	
	}
}
