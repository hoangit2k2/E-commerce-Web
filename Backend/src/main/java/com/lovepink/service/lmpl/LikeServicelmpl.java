package com.lovepink.service.lmpl;

import com.lovepink.entity.Likes;
import com.lovepink.model.request.createLikesRequest;
import com.lovepink.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lovepink.Dao.likesDao;
@Service
public class LikeServicelmpl implements LikesService {

    @Autowired
    likesDao dao;
    @Override
    public Likes createLikes(createLikesRequest req){
        Likes likes = Likes.toLikes(req);
        dao.save(likes);
        return likes;
    }
}
