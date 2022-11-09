package com.lovepink.service;
import java.util.List;
import com.lovepink.entity.Content;
import com.lovepink.model.request.createContenRequest;

public interface ContentService {
	List<Content>findAll();
	Content findById(Integer id);
	List<Content> searchContent(String keyword);
	String deleteUser(Integer id);
	Content createUser(createContenRequest req);
	Content updatecontent(createContenRequest req, int id);

	List<Content> findContentId(String username);

	List<Content> findContentByCategory(int categoryid);
}
