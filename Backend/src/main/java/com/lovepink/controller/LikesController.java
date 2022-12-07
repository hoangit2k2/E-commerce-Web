package com.lovepink.controller;

import com.lovepink.entity.Likes;
import com.lovepink.exception.NotFoundException;
import com.lovepink.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lovepink.model.request.createLikesRequest;

import java.util.List;

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
    @GetMapping("rest/likes/{username}")
    public  ResponseEntity<?> getContentByUsername(@PathVariable String username){
        try {
            System.out.println("hello");
            List<Likes> result = likesService.getContentByUsername(username);
            return  ResponseEntity.ok(result);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    @DeleteMapping("rest/likes/{id}")
    public ResponseEntity<?> deletelikebyid(@PathVariable Integer id){
        try {
            System.out.println("hi");
            String result = likesService.deletelike(id);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            System.out.println(e);
            throw  new NotFoundException("Xóa Thất Bại");
        }
    }
}
