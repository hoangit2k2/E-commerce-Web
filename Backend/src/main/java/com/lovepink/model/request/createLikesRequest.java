package com.lovepink.model.request;

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
    private  String contentid;
}
