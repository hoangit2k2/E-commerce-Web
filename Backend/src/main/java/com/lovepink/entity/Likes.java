package com.lovepink.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import com.lovepink.model.request.createLikesRequest;
import com.lovepink.entity.Content;
import com.lovepink.entity.Content;


import java.io.Serializable;
import java.util.Set;

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
//    private  String contentid;

    @ManyToOne
    @JoinColumn(name = "contentid")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Content content;

    public static Likes toLikes(createLikesRequest req){
        Likes likes = new Likes();
        likes.setId(req.getId());
        likes.setUsernameid(req.getUsernameid());
        likes.setContent(req.getContent());
        return likes;
    }
}
