package com.lovepink.Dao;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface InterDAO<E, K> {

	/**
	 * @param entity to get id
	 * @return primary-key of the entity
	 */
	public K getId(E entity);
	
	/**
	 * @return all entities
	 */
	public List<E> getList();

	/**
	 * @return all entities
	 */
	public List<E> getList(Sort sort);
	
	/**
	 * @param pageable be the condition to find.
	 * @return {@link Page}
	 */
	public Page<E> getPage(Pageable pageable);

	/**
	 * @param id is key
	 * @return {@link Optional} entity -> {@link Optional#isPresent()} or {@link Optional#isEmpty()}
	 * @throws IllegalArgumentException
	 */
	public Optional<E> getOptional(K id) throws IllegalArgumentException;
	
	/**
	 * @param entity to save
	 * @return entity saved successfully
	 */
	public <S extends E> S save(S entity) throws IllegalArgumentException;
	
	/**
	 * @param entity to update
	 * @return entity updated successfully
	 * @throws IllegalArgumentException
	 */
	public <S extends E> S update(S entity) throws IllegalArgumentException;

	/**
	 * @param id is key
	 * @throws IllegalArgumentException 
	 */
	public void remove(K id) throws IllegalArgumentException;
}