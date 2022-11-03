package com.lovepink.service;
import java.io.IOException;
import java.util.List;
import com.lovepink.entity.Content;
import com.lovepink.model.request.createUserRequest;

public interface ContentService {
	List<Content>findAll();
	Content findById(Integer id);
	List<Content> searchContent(String keyword);
	String deleteUser(Integer id);
	Content createUser(createUserRequest req);
	Content updatecontent(createUserRequest req, int id);
}
