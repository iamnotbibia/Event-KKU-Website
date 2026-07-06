package com.example.demo.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.demo.model.Cart;
import com.example.demo.model.Event;
import com.example.demo.model.UserDtls;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;


@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Cart saveCart(Integer productId, Integer userId) {

		UserDtls userDtls = userRepository.findById(userId).get();
		Event product = productRepository.findById(productId).get();

		Cart cartStatus = cartRepository.findByProductIdAndUserId(productId, userId);

		Cart cart = null;

		if (ObjectUtils.isEmpty(cartStatus)) {
			cart = new Cart();
			cart.setProduct(product);
			cart.setUser(userDtls);
			cart.setQuantity(1);
		} else {
			cart = cartStatus;
			cart.setQuantity(cart.getQuantity() + 1);
		}
		Cart saveCart = cartRepository.save(cart);

		return saveCart;
	}

	@Override
	public List<Cart> getCartsByUser(Integer userId) {
	    List<Cart> carts = cartRepository.findByUserId(userId);
	    System.out.println("Carts for user " + userId + ": " + carts); // ดีบักข้อมูล
	    return carts;
	}


	@Override
	public Integer getCountCart(Integer userId) {
		Integer countByUserId = cartRepository.countByUserId(userId);
		return countByUserId;
	}
	
	@Override
	public Boolean deleteCart(int id) {
		Cart cart= cartRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(cart)) {
			cartRepository.delete(cart);
			return true;
		}
		return false;
	}

	public boolean isProductInCart(Integer productId, Integer userId) {
	    // ตรวจสอบว่าผลิตภัณฑ์อยู่ในรถเข็นของผู้ใช้หรือไม่
	    return cartRepository.existsByProductIdAndUserId(productId, userId);
	}



	@Override
	public void updateQuantity(String sy, Integer cid) {

		Cart cart = cartRepository.findById(cid).get();
		int updateQuantity;

		if (sy.equalsIgnoreCase("de")) {
			updateQuantity = cart.getQuantity() - 1;

			if (updateQuantity <= 0) {
				cartRepository.delete(cart);
			} else {
				cart.setQuantity(updateQuantity);
				cartRepository.save(cart);
			}

		} else {
			updateQuantity = cart.getQuantity() + 1;
			cart.setQuantity(updateQuantity);
			cartRepository.save(cart);
		}

	}

	@Override
	public void deleteCartItemById(Integer cId) {
		// TODO Auto-generated method stub
		
	}

}
