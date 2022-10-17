package rrs.model.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rrs.model.entities.Like;
import rrs.model.repositories.LikeRepository;

@Service
public class LikeService extends AbstractService<Like, Like> {

	@Override
	protected Like getId(Like entity) {
		return entity;
	}

	// c content_id - <[account_id, content_id]>
	public List<Like> getLikes(Long c) {
		LikeRepository dao = (LikeRepository) super.rep;
		return c==null ? dao.findAll()
			: c<0 ? dao.findLikesByContentId() : dao.findLikesByContentId(c);
	}

	public int deleteLikes(Long c) throws IllegalArgumentException {
		LikeRepository dao = (LikeRepository) super.rep;
		return c==null || c<0 ? dao.deleteLikesByContentId() : dao.deleteLikesByContentId(c);
	}
}


