package framework.mqtt.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import framework.dao.BoxPortCateRepository;
import framework.dao.BoxPortRepository;
import framework.dao.BoxRepository;
import framework.dao.CommodityRoadRepository;
import framework.entity.po.Box;
import framework.entity.po.BoxPort;
import framework.entity.po.BoxPortCate;
import framework.entity.po.CommodityRoad;
import framework.mqtt.MQttUtil;
import framework.mqtt.service.BindService;
import framework.pojo.DataType;
import framework.pojo.Request;

@Service
@Transactional
public class BindServiceImpl implements BindService {
	@Autowired
	BoxPortRepository boxPortRepository;
	@Autowired
	BoxRepository boxRepository;
	@Autowired
	CommodityRoadRepository commodityRoadRepository;
	@Autowired
	BoxPortCateRepository boxPortCateRepository;

	@Autowired
	MQttUtil mQttUtil;

	public void SynchronizePortsCheck(String device, String response) {
		Box box = boxRepository.findByBoxCode(device);
		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> mp = JSON.parseObject(response, Map.class);
		Iterator<String> it = mp.keySet().iterator();
		
		List<BoxPort> listbport = box.getBoxPorts();
		List<String> listString=new ArrayList<String>();

		while (it.hasNext()) {
			String key = (String) it.next();
			List<Map<String, String>> list = null;
			try {
				list = mp.get(key);
			} catch (Exception e) {
				list=null;
			}
			if (list == null) {
				continue;
			}
			BoxPort boxPort = boxPortRepository.findByDevicePortAndBox(key, box);
			if (boxPort == null) {
				boxPort = new BoxPort();
				boxPort.setBox(box);
				boxPort.setDevicePort(key);
				boxPort = boxPortRepository.save(boxPort);
			}
			listString.add(key);
			List<CommodityRoad> colist = new ArrayList<CommodityRoad>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> ls = list.get(i);
				String cv = ls.get("cv");
				int code = Integer.parseInt(cv.substring(cv.length() - 2, cv.length()), 16);
				String codes = "00" + code;
				Integer is_enable = cv.toUpperCase().startsWith("AF00060031") ? 1 : -1;
				String _code = codes.substring(codes.length() - 2, codes.length());
				CommodityRoad commodityRoad = commodityRoadRepository.findByCodeAndBoxPort(_code,boxPort);
				if (commodityRoad == null) {
					commodityRoad = new CommodityRoad();
					commodityRoad.setBoxPort(boxPort);
					commodityRoad.setCode(_code);
					commodityRoad.setBox(box);
					commodityRoad.setIsEnable(is_enable);
					colist.add(commodityRoad);
				} else {
					commodityRoad.setIsEnable(is_enable);
					colist.add(commodityRoad);
				}
			}
			commodityRoadRepository.save(colist);
		}
		for (int i = 0; i < listbport.size(); i++) {
			BoxPort boxPort=listbport.get(i);
			boolean isAbled=false;
			for (int j = 0; j <listString.size(); j++) {
				String key=listString.get(j);
				if(key.equals(boxPort.getDevicePort())) {
					isAbled=true;
					break;
				}
			}
			if(isAbled==false) {
				boxPort.setIsEnable((short)-1);
			}else {
				boxPort.setIsEnable((short)1);
			}
			boxPortRepository.save(boxPort);
		}
	}

	@Override
	public void SynchronizeBox(String device, String response) {
		Map<String, Object> rt = new HashMap<String, Object>();
		Box box = boxRepository.findByBoxCode(device);
		rt.put("box", box);
		rt.put("boxPort", box.getBoxPorts());
		rt.put("commodityRoad", box.getCommodityRoads());
		List<BoxPortCate> list=boxPortCateRepository.findAll();
		System.out.println("boxPortCate:"+JSON.toJSONString(list));
		rt.put("boxPortCate",list );
		Request bind = new Request();
		bind.setDataType(DataType.BINDBACK);
		bind.setDevice(device);
		bind.setData(Request.Object2Bytes(rt));
		mQttUtil.sendP2P(bind);
	}
}
