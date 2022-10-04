package rrs.model.utils;

import java.util.Optional;

import rrs.model.entities.Content;

public interface UpviewContent {
	/**
	 * @param content_id is key of Content for update view
	 * @return {@link Optional<rrs.model.entities.Content>}
	 * @throws IllegalArgumentException
	 */
	public Optional<Content> upviews(Long content_id) throws IllegalArgumentException;
}
