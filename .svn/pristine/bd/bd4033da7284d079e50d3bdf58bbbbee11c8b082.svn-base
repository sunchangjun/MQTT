package framework.common.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends PagingAndSortingRepository<T,ID> {

    int count(Specification<T> specification);
    
    Page<T> findAll(Specification<T> specification, Pageable pageable);
    
    List<T> findAll(Specification<T> specification);
    
	<S extends T> List<S> save(Iterable<S> entities);
	
	<S extends T> S save(S entity);

	T findOne(ID id);
	
	boolean exists(ID id);
	
	List<T> findAll();
	
	Iterable<T> findAll(Iterable<ID> ids);
	
	long count();
	
	void	delete(ID id) ;
	
	void delete(T entity);

	List<T> findAll(Sort sort);
	
	Page<T> findAll(Pageable pageable);


    
}
