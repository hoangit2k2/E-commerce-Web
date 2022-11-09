package com.lovepink.service.lmpl;

import java.util.ArrayList;

import com.lovepink.model.request.createContenRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lovepink.Dao.contentDao;
import com.lovepink.entity.Content;
import com.lovepink.service.ContentService;


import com.lovepink.exception.NotFoundException;

@Service
public class ContentServicelmpl implements ContentService {
	@Autowired 
	contentDao dao;
	@Override
	public List<Content> findAll() {
		return dao.findAll();
	}
	@Override
	public Content findById(Integer id) {
		List<Content> contens = dao.findAll();
		for(Content conten : contens) {
			if(conten.getId() == id) {
				return dao.findById(id).get();
			}
		}
		throw new NotFoundException("Id Content không tồn tại");
	}
	@Override
	public List<Content> searchContent(String keyword) {
		List<Content> result = new ArrayList<Content>();
		List<Content> contens = dao.findAll();
		for(Content conten : contens) {
			if(conten.getSubject().contains(keyword)){
				result.add(conten);
			}
		}
		return result;
	}
	@Override
	public String deleteUser(Integer id) {
		List<Content> contens = dao.findAll();
		for(Content conten : contens) {
			//check id content
			if(conten.getId() == id) {
				dao.deleteById(id);
				return "Delete Content Successfully";
			}
		}
		throw new NotFoundException("Id Content không tồn tại");
	}
	@Override
	public Content createUser(createContenRequest req) {
		Content content = Content.toContent(req);
		dao.save(content);
		return content;
	}
	@Override
	public Content updatecontent(createContenRequest req , int id) {
		List<Content> contents = dao.findAll();
		for(Content content : contents) {
			if(content.getId() == id) {
				content.setId(id);
				content.setUsernameid(req.getUsernameid());
				content.setCategoryid(req.getCategoryid());
				content.setNamecontent(req.getNamecontent());
//				content.setContent_images(req.getContent_images());
				content.setSubject(req.getSubject());
				content.setPrice(req.getPrice());
				content.setEmail(req.getEmail());
				content.setPhone(req.getPhone());
				content.setAddress(req.getAddress());
				content.setStatus(req.isStatus());
				dao.save(content);
			}
		}
		
		throw new NotFoundException("ID Content Không tồn tại");
	}
	@Override
	public List<Content> findContentId(String username){
		return  dao.findByUsernameid(username);
	}

	@Override
	public List<Content> findContentByCategory(int categoryid){
		return  dao.findByCategoryid(categoryid);
	}

}
