package com.lovepink.service;

import com.lovepink.entity.Comment;
import com.lovepink.model.request.createCommentRequest;

import java.util.List;

public interface CommentService {
     Comment createcomment(createCommentRequest req);
     List<Comment> getCommentByContentId(Integer content);
//    Comment createcontent(createCommentRequest);
}
