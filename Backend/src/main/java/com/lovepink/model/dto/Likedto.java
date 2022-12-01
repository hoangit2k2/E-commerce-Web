//package com.lovepink.entity;
//
//import lombok.*;
//import org.springframework.stereotype.Component;
//
//import javax.persistence.*;
//
//import com.lovepink.model.request.createLikesRequest;
//
//
//import java.io.Serializable;
//
//@Data
//@Table
//@NoArgsConstructor
//@AllArgsConstructor
//@Component
//@Entity(name = "likes")
//public class Likedto implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String usernameid;
//    @ManyToOne
//    @JoinColumn(name = "contentid")
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private Content content;
//}
