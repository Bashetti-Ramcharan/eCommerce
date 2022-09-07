package com.toll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toll.dto.ProductDTO;
import com.toll.model.Category;
import com.toll.model.Product;
import com.toll.repository.ProductRepository;

@Service
public class ProductService 
{
	@Autowired
	ProductRepository pr;
	public List<Product> getAllProduct()
	{
		return pr.findAll();
	}
	public void addProduct(Product pro)
	{
		pr.save(pro);
		
	}
	
	public void deleteProduct(long id) 
	{
		
		pr.deleteById(id);
	}
	public Optional<Product> findProductById(long id) 
	{
		
		return pr.findById(id);
	}
	public List<Product> getAllProductsByCategoryById(int id) 
	{
		
		return pr.findAllByCategory_Id(id);
	}
	

}
