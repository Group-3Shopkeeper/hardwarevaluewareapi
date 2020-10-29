package com.hardwarevaluewareapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hardwarevaluewareapi.bean.Category;
import com.hardwarevaluewareapi.exception.ResourceNotFoundException;
import com.hardwarevaluewareapi.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	@GetMapping("/list")
	public ResponseEntity<List<Category>> getCategoryList() throws ExecutionException, InterruptedException {
		ArrayList<Category> categoryList = categoryService.getCategoryList();

		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	@PostMapping("/")
	public ResponseEntity<?> saveCategory(@RequestParam("file") MultipartFile file,@RequestParam ("categoryName") String categoryName ) throws Exception {
		if(file.isEmpty())
	   		  throw new Exception();
		Category c= new Category();
		c.setCategoryName(categoryName);
		Category category=categoryService.saveCategory(file,c);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
	@GetMapping("/{categoryId}")
	  public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") String categoryId) throws InterruptedException, ExecutionException, ResourceNotFoundException{
	     Category c = categoryService.getCategoryById(categoryId);	  
	     if(c == null)
	    	 throw new ResourceNotFoundException("Category not found");
	     else
	    	 return new ResponseEntity<Category>(c,HttpStatus.OK);
	  }
	
}
