package com.lovepink.service.lmpl;

import com.lovepink.entity.Comment;
import com.lovepink.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import com.lovepink.Dao.commentDao;
import com.lovepink.model.request.createCommentRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServicelmpl implements CommentService {
    @Autowired
    commentDao dao;

    @Override
    public Comment createcomment(createCommentRequest req){
        Comment comment = Comment.toComment(req);
        dao.save(comment);
        return comment;
    }
    @Override
    public List<Comment> getCommentByContentId(Integer contentid){
        return dao.findByContentid(contentid);
    }
}
