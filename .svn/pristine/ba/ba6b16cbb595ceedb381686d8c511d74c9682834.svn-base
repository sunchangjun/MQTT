package framework.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.Box;
import framework.entity.po.BoxPort;
/**
 * 串口表
 * @author Administrator
 *
 */
public interface BoxPortRepository extends BaseRepository<BoxPort,Long>{

	List<BoxPort> findAllByBox(Box box);



//	List<BoxPort> findAllByBoxId(Long boxId);
	
//	@Query("select bp from  BoxPort bp  where bp.boxId = ?1")
//	BoxPort findBoxPortByBoxId(Long boxId);
	
	
//	@Query("select bp from  BoxPort bp  where bp.boxId = ?1")
//	List<BoxPort> getBoxProtListByBoxId(Long boxId);
//	
//	@Query("select bp from BoxPort bp  where bp.boxId  in (?1)")
//	List<BoxPort> getBoxProtListByBoxIdList(List<Long> boxIdList);
//	
//	BoxPort findByBoxPortId(Long boxPortId);
//	
//	BoxPort findByDevicePort(String devicePort);
	
//	List<BoxPort> findAllByBoxId(Long boxId);
	

	BoxPort findByDevicePortAndBox(String key, Box box);

}
