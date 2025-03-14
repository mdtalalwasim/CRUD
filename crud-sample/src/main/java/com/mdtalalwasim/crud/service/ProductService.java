package com.mdtalalwasim.crud.service;

import java.util.List;
import java.util.Optional;

import com.mdtalalwasim.crud.entity.Product;

public interface ProductService {

	Product saveProduct(Product product);

	List<Product> getAllProduct();

	Optional<Product> getProductById(Long id);

	Product updateProduct(Long id, Product product);

}
