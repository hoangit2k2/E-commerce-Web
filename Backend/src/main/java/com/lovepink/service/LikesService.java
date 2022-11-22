package com.lovepink.service;

import com.lovepink.entity.Likes;
import  com.lovepink.model.request.createLikesRequest;

public interface LikesService {
    Likes createLikes(createLikesRequest req);
}
