package com.lovepink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import com.lovepink.model.request.createLikesRequest;
import com.lovepink.entity.Content;
import com.lovepink.entity.Content;


import java.io.Serializable;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "likes")
public class Likes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String usernameid;
    private  String contentid;
//	@ManyToOne
//	@JoinColumn(name = "username")
//	Users user;
//	@ManyToOne
//	@JoinColumn(name = "id")
//	Content content;
//	
	
    public static Likes toLikes(createLikesRequest req){
        Likes likes = new Likes();
        likes.setId(req.getId());
        likes.setUsernameid(req.getUsernameid());
        likes.setContentid(req.getContentid());
        return likes;
    }
}
