package com.lovepink.model.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class createCommentRequest {
//    private Integer id;
    private String usernameid;
    private Integer contentid;
    private String subject;
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//    @Column(name = "datetime")
//    private LocalDateTime datetime;
}
