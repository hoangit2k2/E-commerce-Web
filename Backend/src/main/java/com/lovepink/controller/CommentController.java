package com.lovepink.controller;

import com.lovepink.entity.Comment;
import com.lovepink.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import  com.lovepink.model.request.createCommentRequest;
import javax.servlet.ServletContext;
import com.lovepink.service.CommentService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/comment")
public class CommentController implements ServletContextAware {
    private ServletContext servletContext;
    @Autowired
    CommentService commentService;

//    @GetMapping

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody createCommentRequest req ){
        try {
            Comment comment = commentService.createcomment(req);
            return ResponseEntity.ok(comment);
        }catch (Exception e){
            System.out.println(e);
            throw new NotFoundException("Tạo Comment thất bại");
        }
    }
    @GetMapping("{id}")
    public  ResponseEntity<?> getCommentByContentid(@PathVariable Integer id){
        try {
            List<Comment> result = commentService.getCommentByContentId(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            System.out.println(e);
            throw new NotFoundException("Get comment faild");
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext){
        this.servletContext = servletContext;
    }
}
