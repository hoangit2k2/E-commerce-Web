package rrs.model.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import rrs.model.utils.InterDAO;
import rrs.utils.CustomException;

public abstract class AbstractService<E, K> implements InterDAO<E, K> {
	
	@Autowired protected JpaRepository<E, K> rep;
	protected abstract K getId(E entity);
	
	@Override
	public List<E> getList() {
		return rep.findAll();
	}

	@Override
	public List<E> getList(Sort sort) {
		return rep.findAll(sort);
	}

	@Override
	public Page<E> getPage(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override
	public Optional<E> getOptional(K id) throws IllegalArgumentException {
		return rep.findById(id);
	}

	@Override
	public <S extends E> S save(S entity) throws CustomException, IllegalArgumentException {
		K id = this.getId(entity);
		Optional<E> optional = rep.findById(id);
		if(optional.isEmpty()) {
			return rep.save(entity);
		} else throw new CustomException(id+" đã tồn tại, không thể thêm mới.");
	}

	@Override
	public <S extends E> S update(S entity) throws CustomException, IllegalArgumentException {
		K id = this.getId(entity);
		Optional<E> optional = rep.findById(id);
		if(optional.isPresent()) {
			return rep.saveAndFlush(entity);
		} else throw new CustomException(id+" không tồn tại, không thể cập nhật.");
	}

	@Override
	public void remove(K id) throws CustomException, IllegalArgumentException {
		rep.deleteById(id);
	}

}
