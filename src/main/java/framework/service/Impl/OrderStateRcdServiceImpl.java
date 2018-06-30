package framework.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framework.dao.OrderStateRcdRepository;
import framework.entity.po.OrderStateRcd;
import framework.service.interf.OrderStateRcdService;

@Service
public class OrderStateRcdServiceImpl implements OrderStateRcdService {
	
	@Autowired
	private	OrderStateRcdRepository orderStateRcdRepository;
	
	public boolean saveOrderStateRcd(OrderStateRcd rderStateRcd){
		OrderStateRcd dbrderStateRcd=orderStateRcdRepository.save(rderStateRcd);
		if(null  != dbrderStateRcd){
			return  true;				
		}
		
		
		return  false;
				
	}

}
