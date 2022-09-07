package com.toll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toll.model.Category;
import com.toll.repository.CategoryRepository;

@Service
public class CategoryService 
{
	@Autowired
	CategoryRepository cr;
	public void addCategory(Category cat)
	{
		cr.save(cat);
	}
	
	public List<Category> getAllCategory()
	{
		return cr.findAll();
	}

	public void deleteCategory(int id) {
		cr.deleteById(id);
		
	}


	public Optional<Category> findCategoryById(int id) 
	{
		
		return cr.findById(id);
	}
	
}
