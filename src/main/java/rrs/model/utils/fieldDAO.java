package rrs.model.utils;

import java.util.List;


/** 
 * @param <E> is enumerate
 * @param <T> is entity
 */
public interface fieldDAO<E, T> {

	/**
	 * @param enumerate is type of enumerate to find
	 * @return {@link List<T>}, result of enumerate
	 * 
	 * @throws IllegalArgumentException
	 */
	public List<T> getData(E enumerate, Object...keys) throws IllegalArgumentException;
}
