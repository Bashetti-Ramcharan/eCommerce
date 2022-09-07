package com.toll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.toll.dto.ProductDTO;
import com.toll.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

	

	List<Product> findAllByCategory_Id(int id);

	
}
