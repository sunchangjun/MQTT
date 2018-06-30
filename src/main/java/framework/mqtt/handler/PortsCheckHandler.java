package framework.mqtt.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import framework.mqtt.service.BindService;
import framework.mqtt.util.IMqttHandler;
import framework.pojo.Request;
@Component
public class PortsCheckHandler implements IMqttHandler {
	private static final Logger logger = LoggerFactory.getLogger(PortsCheckHandler.class);
	@Autowired
	private BindService bindService;
	@Override
	public void exec(Request bean) {
		String response = new String(bean.getData());
		logger.info(response);
		bindService.SynchronizePortsCheck(bean.getDevice(), response);
		bindService.SynchronizeBox(bean.getDevice());
	}

}
