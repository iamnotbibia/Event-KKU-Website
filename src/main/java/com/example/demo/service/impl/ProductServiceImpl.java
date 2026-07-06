package com.example.demo.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Event;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Event saveProduct(Event product) {
		return productRepository.save(product);
	}

	@Override
	public List<Event> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Page<Event> getAllProductsPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return productRepository.findAll(pageable);
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		Event product = productRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(product)) {
			productRepository.delete(product);
			return true;
		}
		return false;
	}

	@Override
	public Event getProductById(Integer id) {
		Event product = productRepository.findById(id).orElse(null);
		return product;
	}

	@Override
	public Event updateProduct(Event product, MultipartFile image) {
	    // ค้นหาผลิตภัณฑ์ในฐานข้อมูล
	    Event dbProduct = getProductById(product.getId());

	    // เช็คว่าภาพที่อัปโหลดใหม่หรือไม่
	    String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();

	    // อัปเดตข้อมูลผลิตภัณฑ์
	    dbProduct.setTitle(product.getTitle());
	    dbProduct.setDescription(product.getDescription());
	    dbProduct.setCategory(product.getCategory());
	    dbProduct.setLocation(product.getLocation());
	    dbProduct.setStartdate(product.getStartdate());
	    dbProduct.setEnddate(product.getEnddate());
	    dbProduct.setImage(imageName);
	    dbProduct.setIsActive(product.getIsActive());

	    // บันทึกการเปลี่ยนแปลงในฐานข้อมูล
	    Event updatedProduct = productRepository.save(dbProduct);

	    // ตรวจสอบว่าการอัปเดตสำเร็จหรือไม่
	    if (!ObjectUtils.isEmpty(updatedProduct)) {
	        // ถ้ามีการอัปโหลดภาพใหม่
	        if (!image.isEmpty()) {
	            try {
	                // กำหนดตำแหน่งในการบันทึกภาพ
	                File saveFile = new ClassPathResource("static/img").getFile();
	                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator + image.getOriginalFilename());
	                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return product; // ส่งคืนผลิตภัณฑ์ที่อัปเดต
	    }
	    return null; // ถ้าอัปเดตไม่สำเร็จ
	}


	@Override
	public List<Event> getAllActiveProducts(String category) {
		List<Event> products = null;
		if (ObjectUtils.isEmpty(category)) {
			products = productRepository.findByIsActiveTrue();
		} else {
			products = productRepository.findByCategory(category);
		}

		return products;
	}

	@Override
	public List<Event> searchProduct(String ch) {
		return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
	}

	@Override
	public Page<Event> searchProductPagination(Integer pageNo, Integer pageSize, String ch) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch, pageable);
	}
	
    @Override
    public Page<Event> searchProductByDatePagination(Integer pageNo, Integer pageSize, String ch, String startDateTime, String endDateTime) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        LocalDateTime start = parseDateTime(startDateTime);
        LocalDateTime end = parseDateTime(endDateTime);
        return productRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCaseAndDateRange(ch, ch, start, end, pageable);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            // Handle other date formats if necessary
            return null;
        }
    }

	@Override
	public Page<Event> getAllActiveProductPagination(Integer pageNo, Integer pageSize, String category) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Event> pageProduct = null;

		if (ObjectUtils.isEmpty(category)) {
			pageProduct = productRepository.findByIsActiveTrue(pageable);
		} else {
			pageProduct = productRepository.findByCategory(pageable, category);
		}
		return pageProduct;
	}

	@Override
	public Page<Event> searchActiveProductPagination(Integer pageNo, Integer pageSize, String category, String ch) {

		Page<Event> pageProduct = null;
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		pageProduct = productRepository.findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch,
				ch, pageable);

//		if (ObjectUtils.isEmpty(category)) {
//			pageProduct = productRepository.findByIsActiveTrue(pageable);
//		} else {
//			pageProduct = productRepository.findByCategory(pageable, category);
//		}
		return pageProduct;
	}
}
