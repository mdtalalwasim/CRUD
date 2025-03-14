package com.mdtalalwasim.crud.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdtalalwasim.crud.entity.Product;
import com.mdtalalwasim.crud.repository.ProductRepository;
import com.mdtalalwasim.crud.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductRepository productRepository;
	
	@Override
	public Product saveProduct(Product product) {
		// TODO Auto-generated method stub
		product.setCreatedAt(new Date());
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProduct() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public Product updateProduct(Long id, Product product) {
		Optional<Product> existingProduct = productRepository.findById(id);
		if(existingProduct.isPresent()) {
			existingProduct.get().setId(id);
			existingProduct.get().setName(product.getName());
			existingProduct.get().setPrice(product.getPrice());
			existingProduct.get().setQuantity(product.getQuantity());
			
			return productRepository.save(existingProduct.get());
			
		}else {
			return null;
		}
		
	}

}
