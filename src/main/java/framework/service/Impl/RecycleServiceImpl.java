package framework.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import framework.dao.RecycleRepository;
import framework.entity.po.Recycle;
import framework.service.interf.RecycleService;

@Service
public class RecycleServiceImpl implements RecycleService {
	
	
	@Autowired
	private RecycleRepository recycleRepository;
	
	public Recycle addRecycle(Recycle recycle){
		
		Recycle dbReturnRecycle=recycleRepository.save(recycle);
		
		return dbReturnRecycle;
		
	}
	
	

}
