package framework.service.interf;

import framework.entity.po.Adv;

public interface AdvService {
	/**
	 * 保存广告实体
	 * @param adv
	 * @return
	 */
	Adv saveAdv(Adv adv);
	
	/**
	 * 交换排序
	 * @param currentId  当前对象id
	 * @param toId   被交换对象id
	 * @return
	 */
	boolean   exchangeSort(Long  currentId,Long  toId); 

}
