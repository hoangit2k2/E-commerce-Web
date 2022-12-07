package com.lovepink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import com.lovepink.model.request.createCommentRequest;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "COMMENT")
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String usernameid;
    private Integer contentid;
    private String subject;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "datetime")
    private LocalDateTime datetime;

    public static Comment toComment(createCommentRequest req)  {
        Comment comment = new Comment();
//        comment.setId(req);
          comment.setUsernameid(req.getUsernameid());
          comment.setContentid(req.getContentid());
          comment.setSubject(req.getSubject());
          comment.setDatetime(java.time.LocalDateTime.now());

        return comment;
    }
}
