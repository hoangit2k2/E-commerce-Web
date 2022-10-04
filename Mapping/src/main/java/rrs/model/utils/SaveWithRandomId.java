package rrs.model.utils;

public interface SaveWithRandomId<E> {
	/**
	 * <h1 style="text-align: center; color: yellow; font-size: 2em; text-transform: uppercase;">
	 * 		find all categories
	 * </h1>
	 * @param entity to save
	 * @param isRandom == true, then category_id is default, otherwise category_id'll be random
	 * @return category saved successfully
	 * @throws IllegalArgumentException when idEntityId == true and isEntityId already exists
	 */
	public <S extends E> S save(S entity, Boolean randomId) throws IllegalArgumentException;
}
