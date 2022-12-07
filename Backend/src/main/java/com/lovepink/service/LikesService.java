package com.lovepink.service;

import com.lovepink.entity.Likes;
import  com.lovepink.model.request.createLikesRequest;

import java.util.List;

public interface LikesService {
    Likes createLikes(createLikesRequest req);
    List<Likes> getContentByUsername(String usernameid);
    String deletelike(Integer id);
}
