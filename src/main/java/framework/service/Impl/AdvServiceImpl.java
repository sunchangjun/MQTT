package framework.service.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framework.dao.AdvRepository;
import framework.entity.po.Adv;
import framework.service.interf.AdvService;
@Service
public class AdvServiceImpl implements AdvService {
	@Autowired
	AdvRepository advRepository;
	
	public Adv saveAdv(Adv adv) {	
		return advRepository.save(adv);
	}
	
	
	
	public  boolean   exchangeSort(Long  currentId,Long  toId) {
		List<Adv> list=new  ArrayList<Adv>();
		Adv currentAdv=advRepository.findOne(currentId);
		Adv toAdv=advRepository.findOne(toId);
		//
		Integer currSort=currentAdv.getSortOrder();
		Integer  toSort=toAdv.getSortOrder();
		currentAdv.setSortOrder(toSort);
		toAdv.setSortOrder(currSort);
		list.add(currentAdv);
		list.add(toAdv);
		List<Adv>  returnList=advRepository.save(list);
		if(CollectionUtils.isNotEmpty(returnList)) {
			return  true;	
		}	
		return   false;
	}

}
