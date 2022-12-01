package com.lovepink.model.request;

import com.lovepink.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class createLikesRequest {
    private int id;
    private String usernameid;
    private Content content;
}
