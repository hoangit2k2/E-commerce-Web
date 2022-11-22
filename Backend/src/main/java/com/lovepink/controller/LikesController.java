package com.lovepink.controller;

import com.lovepink.entity.Likes;
import com.lovepink.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.lovepink.model.request.createLikesRequest;
@CrossOrigin("*")
@RestController
public class LikesController {
    @Autowired
    LikesService likesService;

    @PostMapping("rest/likes")
    public ResponseEntity<?> createLikes(@RequestBody createLikesRequest req){
        try {
            System.out.println(req);
            Likes result = likesService.createLikes(req);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            System.out.println(e);
            return  null;
        }
    }
}
