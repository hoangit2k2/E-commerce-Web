package com.lovepink.service.lmpl;

import java.io.IOException;
import java.util.ArrayList;
import com.lovepink.model.request.createUserRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

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
		// TODO Auto-generated method stub
		return dao.findById(id).get();
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
	public Content createUser(createUserRequest req) {
		Content content = Content.toContent(req);
		dao.save(content);
		return content;
	}
	@Override
	public Content updatecontent(createUserRequest req , int id) {
		List<Content> contents = dao.findAll();
		for(Content content : contents) {
			if(content.getId() == id) {
				content.setId(id);
				content.setCustomerid(req.getCustomerid());
				content.setCategoryid(req.getCustomerid());
				content.setNamecontent(req.getNamecontent());
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

}
