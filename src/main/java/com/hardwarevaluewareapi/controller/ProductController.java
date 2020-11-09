package com.hardwarevaluewareapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hardwarevaluewareapi.bean.Cart;
import com.hardwarevaluewareapi.bean.Product;
import com.hardwarevaluewareapi.exception.ResourceNotFoundException;
import com.hardwarevaluewareapi.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired 
	private ProductService productService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable String id) throws Exception{
		Product product = productService.getProduct(id);
		if(product == null) {
			throw new ResourceNotFoundException("Not found");
		}
		else
		  return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
		
	@PostMapping("/")
	  public ResponseEntity<?> saveProduct(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,@RequestParam("shopKeeperId") String shopKeeperId,@RequestParam("categoryId") String categoryId, @RequestParam("price") double price,@RequestParam("discount") double discount,@RequestParam("brand") String brand,@RequestParam("qtyInStock") int qtyInStock,@RequestParam("description") String description) throws Exception {
		    if(file.isEmpty())
			  throw new Exception();
		    
		    Product product = new Product();
	        product.setBrand(brand);
		    product.setName(name);
		    product.setDescription(description);
		    product.setDiscount(discount);
		    product.setPrice(price);
		    product.setQtyInStock(qtyInStock);
		    product.setShopKeeperId(shopKeeperId);
		    product.setCategoryId(categoryId);
		    Product u = productService.saveProduct(file, product);
		    return new ResponseEntity<Product>(u,HttpStatus.OK);
	}
	
	@PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestParam("file") MultipartFile file,
    		@RequestParam("productId") String productId,
    		@RequestParam("name") String name,
    		@RequestParam("price") double price,
    		@RequestParam("discount") double discount,
    		@RequestParam("brand") String brand,
    		@RequestParam("categoryId") String categoryId,
    		@RequestParam("description") String description,
    		@RequestParam("qtyInStock") int qtyInStock) throws Exception{
	   	 if(file.isEmpty())
	  		  throw new Exception();
	   	 Product product = new Product();
	   	 product.setBrand(brand);
	   	 product.setName(name);
	   	 product.setPrice(price);
	   	 product.setDescription(description);
	   	 product.setProductId(productId);
	   	 product.setQtyInStock(qtyInStock);
	   	 product.setCategoryId(categoryId);
	   	 Product p=productService.updateProduct(file, product);
	   	 return new ResponseEntity<Product>(p,HttpStatus.OK);
    }

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String id) throws InterruptedException, ExecutionException, ResourceNotFoundException {
		Product product =  productService.deleteProduct(id);
		if(product == null) {
			throw new ResourceNotFoundException("Not found "+id);
		}
		else
		  return new ResponseEntity<Product>(product,HttpStatus.OK);
		  
	}
	@GetMapping("/discount")
	public ResponseEntity<List<Product>> getProductByDiscount() throws InterruptedException, ExecutionException, ResourceNotFoundException {
		ArrayList<Product> productList = productService.getDiscountedProduct();
		if(productList!=null)
		{
			return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);	
		}
		else
		{
			throw new ResourceNotFoundException("Product Not Found");
		}	
	}
	@GetMapping("/recent-product")
	public ResponseEntity<List<Product>> getRecent() throws Exception{
		List<Product> product = productService.getRecentProduct();
		if(product!=null)
		{
			return new ResponseEntity<List<Product>>(product, HttpStatus.OK);	
		}
		else
		{
			throw new ResourceNotFoundException("Product Not Found");
		}
	}
	@GetMapping("/c/{categoryId}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String categoryId) throws ResourceNotFoundException, InterruptedException, ExecutionException {
		List<Product> productList = productService.getProductByCategory(categoryId);
		if(productList!=null)
		{
			return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);	
		}
		else
		{
			throw new ResourceNotFoundException("Product Not Found");
		}	
	}
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Product>> searchProductByName(@PathVariable("name") String name)
			throws InterruptedException, ExecutionException, ResourceNotFoundException {
		ArrayList<Product> productList = productService.searchProductByName(name);
		if (productList != null) {
			return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Product Not Found");
		}
	}
	
}