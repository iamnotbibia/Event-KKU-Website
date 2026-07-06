package com.example.demo.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Event;



public interface ProductService {

	public Event saveProduct(Event product);

	public List<Event> getAllProducts();

	public Boolean deleteProduct(Integer id);

	public Event getProductById(Integer id);

	public Event updateProduct(Event product, MultipartFile file);

	public List<Event> getAllActiveProducts(String category);

	public List<Event> searchProduct(String ch);

	public Page<Event> getAllActiveProductPagination(Integer pageNo, Integer pageSize, String category);

	public Page<Event> searchProductPagination(Integer pageNo, Integer pageSize, String ch);

	public Page<Event> getAllProductsPagination(Integer pageNo, Integer pageSize);

	public Page<Event> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch);

	public Page<Event> searchProductByDatePagination(Integer pageNo, Integer pageSize, String ch, String startDateTime,
			String endDateTime);
}
