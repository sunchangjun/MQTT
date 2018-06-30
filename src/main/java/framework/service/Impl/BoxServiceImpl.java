package framework.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framework.dao.BoxPortRepository;
import framework.dao.BoxRepository;
import framework.dao.CommodityRoadRepository;
import framework.entity.po.Box;
import framework.entity.po.BoxPort;
import framework.entity.po.CommodityRoad;
import framework.service.interf.BoxService;

@Service
public class BoxServiceImpl implements BoxService {

	@Autowired
	private BoxRepository boxRepository;
	
	@Autowired
	private	CommodityRoadRepository commodityRoadRepository;
	
	@Autowired
	private		BoxPortRepository boxPortRepository;

	public Box addBox(Box box) {
		//保存柜机
		Box returnBox  = boxRepository.save(box);
		return returnBox;
	}

}
