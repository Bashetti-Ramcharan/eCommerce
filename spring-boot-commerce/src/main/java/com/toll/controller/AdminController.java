package com.toll.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.toll.dto.ProductDTO;
import com.toll.model.Category;
import com.toll.model.Product;
import com.toll.service.CategoryService;
import com.toll.service.ProductService;

@Controller
public class AdminController 
{
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	
	private static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/Images";
	
	@GetMapping("/admin")
	public String adminHome()
	{
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCat(Model m)
	{
		m.addAttribute("categories",categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model m)
	{
		m.addAttribute("category",new Category());//pass empty object here
		return "categoriesAdd";
	}
	

	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category cat)
	{
		categoryService.addCategory(cat);
		return "redirect:/admin/categories";
	}
	
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id)
	{
		categoryService.deleteCategory(id);
		return "redirect:/admin/categories";
	}
	
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model m)
	{
		Optional<Category> obj=categoryService.findCategoryById(id);
		if(obj.isPresent())
		{
		m.addAttribute("category", obj.get());
		return "categoriesAdd";
		}
		else
			return "484";
		
	}
	
	@GetMapping("/admin/products")
	public String products(Model m)
	{
		m.addAttribute("products",productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String getProdAdd(Model m)
	{
		m.addAttribute("productDTO",new ProductDTO());
		m.addAttribute("categories",categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String postProdAdd(@ModelAttribute("productDTO") ProductDTO pro,
			@RequestParam("productImage")MultipartFile file,
			@RequestParam("imgName")String imgName
			)throws IOException
	{
		//We need to convert productDTO obj to product becoz we are saving product obj
		Product product=new Product();
		product.setId(1);
		product.setName(pro.getName());
		product.setCategory(categoryService.findCategoryById(pro.getCategoryId()).get());
		product.setPrice(pro.getPrice());
		product.setWeight(pro.getWeight());
		product.setDescription(pro.getDescription());
		String imageUUID;
		if(!file.isEmpty())
		{
			imageUUID=file.getOriginalFilename();
			Path fileNameandPath=Paths.get(uploadDir,imageUUID);
			Files.write(fileNameandPath,file.getBytes());
			
		}
		else
		{
			imageUUID=imgName;
		}
		
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id)
	{
		productService.deleteProduct(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable int id,Model m)
	{
		Product product=productService.findProductById(id).get();
		ProductDTO pdo=new ProductDTO();
		pdo.setId(product.getId());
		pdo.setName(product.getName());
		pdo.setCategoryId(product.getCategory().getId());
		pdo.setPrice(product.getPrice());
		pdo.setWeight(product.getWeight());
		pdo.setDescription(product.getDescription());
		pdo.setImageName(product.getImageName());
		m.addAttribute("categories",categoryService.getAllCategory());
		m.addAttribute("productDTO",pdo);
		return "productsAdd";
		
		
	}
	
}
