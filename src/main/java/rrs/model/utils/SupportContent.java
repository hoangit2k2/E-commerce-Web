package rrs.model.utils;

import java.util.List;
import java.util.Optional;

import rrs.model.entities.Account;
import rrs.model.entities.Content;

public interface SupportContent {
	/**
	 * @param content_id is key of Content for update view
	 * @return {@link Optional<rrs.model.entities.Content>}
	 * @throws IllegalArgumentException
	 */
	public Optional<Content> upviews(Long content_id) throws IllegalArgumentException;
	
	/**
	 * @param active to get all activated contents,when active is null then get all
	 * @return all contents with {@link Content#getActive()} is active
	 * @throws IllegalArgumentException
	 */
	public List<Content> getByActive(Boolean active) throws IllegalArgumentException;
	
	/**
	 * @param active to get all activated contents,when active is null then get all
	 * @return all contents with {@link Content#getActive()} is active
	 * @throws IllegalArgumentException
	 */
	public List<Content> getByCategoryId(String category_id) throws IllegalArgumentException;
	
	/**
	 * @param account_id is {@link Account#getUsername()} posted {@link Content}
	 * @return List content
	 * @throws IllegalArgumentException
	 */
	public List<Content> getByAccountId(String account_id) throws IllegalArgumentException;
}
