package com.mdtalalwasim.crud.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mdtalalwasim.crud.entity.Product;
import com.mdtalalwasim.crud.others.ApiResponse;
import com.mdtalalwasim.crud.service.ProductService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api")
public class ProductRestController {
	
	
	@Autowired
	ProductService productService;
	
	//1st way
	@PostMapping("/create-product-1st-way")
	public Product createProductFirstWay(@RequestBody Product product) {
		Product productCreated = productService.saveProduct(product);
		return productCreated;
	}
	
	
	/*
	 * Pros: ✔️ Allows setting HTTP status codes (201 CREATED, 500 INTERNAL SERVER
	 * ERROR, etc.). ✔️ More flexible than directly returning the Product. ✔️ Works
	 * well with exception handling.
	 * 
	 * Cons: ❌ If an exception is thrown, only a status code is returned (no error
	 * message).
	 */
	//2nd way
	@PostMapping("/create-product-second-way")
	public ResponseEntity<Product> createProductSecondWay(@RequestBody Product product) {
		try {
			Product saveProduct = productService.saveProduct(product);
			
			return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
			
		} catch (Exception e) {
			
			return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
			//return new ResponseEntity<>("Failed to create product", HttpStatus.BAD_REQUEST);
			
		}
	}
	
	
	/*
	 * Pros: ✔️ Simple and concise. ✔️ Works well for basic APIs. ✔️ Automatically
	 * serializes the Product object to JSON.
	 * 
	 * Cons: ❌ Always returns 200 OK, even when an error occurs. ❌ No flexibility in
	 * handling different responses.
	 */
	//third-way
	@PostMapping("/create-product-third-way")
	public Product createProductsThirdWay(@RequestBody Product product) {
	    return productService.saveProduct(product);
	}
	
	
	/*
	 * Pros: ✔️ More structured response with a message field. ✔️ Easier to maintain
	 * and extend (e.g., add error codes, metadata). ✔️ Standardizes API responses
	 * across your project.
	 */
	//4th-way
	@PostMapping("/create-product-fourth-way")
	public ResponseEntity<ApiResponse> createProductForthWay(@RequestBody Product product){
		try {
			Product saveProduct = productService.saveProduct(product);
			ApiResponse apiResponse = new ApiResponse("Product Created Successfully.",saveProduct);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.CREATED);
		} catch (Exception e) {
			ApiResponse apiResponse = new ApiResponse("Failed to create product.", null);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	
	/*
	 * Pros: ✔️ No need to create a separate DTO class. ✔️ More informative than
	 * returning just the Product. ✔️ Easy to modify (e.g., add status field).
	 */
	//5th way
	//Returning a Response with Success & Error Messages
	@PostMapping("/create-product-fifth-way")
	public ResponseEntity<Map<String, Object>> createProductWithResponseSuccessAndError(@RequestBody Product product){
		Map<String, Object> response = new HashMap<>();
		try {
			Product saveProduct = productService.saveProduct(product);
			response.put("message", "Product Save Successfully");
			response.put("data", saveProduct);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			response.put("message", "Failed to create product");
			response.put("data", null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	/*
	 * Pros: ✔️ Clean and simple. ✔️ Avoids wrapping responses in ResponseEntity<>.
	 * 
	 * Cons: ❌ Less flexible (no error handling). ❌ Cannot return custom messages on
	 * failure.
	 */
	
	//6th way
	//Using @ResponseStatus (Simple & Clean)
	//Instead of ResponseEntity, We can use @ResponseStatus:
	
	@PostMapping("/create-product-sixth-way")
	@ResponseStatus(HttpStatus.CREATED)
	public Product createProductWithResponseStatus(@RequestBody Product product) {
	    return productService.saveProduct(product);
	}
	
	
	
	//fetch the product List
	@GetMapping("/all-product")
	public ResponseEntity<List<Product>> productList(){
		List<Product> productList = productService.getAllProduct();
		
		return new ResponseEntity<List<Product>>(productList,HttpStatus.OK);
	} 
	
	//fetch product by id
	@GetMapping("/edit-product/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long id ){
	 	Optional<Product> product =  productService.getProductById(id);
	 	return product.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	//update product
	@PutMapping("/update-product/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long id, @RequestBody Product product) {
		try {
			Product updateProduct = productService.updateProduct(id,product);
			ApiResponse response = new ApiResponse("Product Update Successfully", updateProduct);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			ApiResponse apiResponse = new ApiResponse("Failed to update product.", null);
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	



}
