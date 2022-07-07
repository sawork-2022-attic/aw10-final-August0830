package com.micropos.carts.service;

import java.util.List;
import java.util.Optional;

import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.model.CartItem;
import com.micropos.carts.repository.CartRepository;
import com.micropos.dto.CartItemDto;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CartServiceImp implements CartService {

    private CartRepository cartRepository;

    @LoadBalanced
    private RestTemplate restTemplate;
    /**
     * @param cartRepository the cartRepository to set
     */
    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Autowired
    private CartMapper mapper;

    @Override
    public Cart addItemToCart(Cart cart, CartItem item) {
        // TODO Auto-generated method stub
        if(cart.addItem(mapper.toCartItemDto(item)))
            return cartRepository.save(cart);
        return null;
    }

    @Override
    public double checkout(Integer cartId) {
        // TODO Auto-generated method stub
        Optional<Cart> cart = this.cartRepository.findById(cartId);
        if(cart.isEmpty())
            return -1.0;
        Cart realCart = cart.get();
        double sum = 0;
        StringBuilder orderBuiler = new StringBuilder();
        for(CartItemDto item: realCart.cartItems()){
            sum += item.getProduct().getPrice() * item.getQuantity();
            orderBuiler.append(item.getProduct().getName()+"|");
        }
        orderBuiler.append("addr:CityA");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(orderBuiler.toString());
        restTemplate.postForObject("http://localhost:8085/order", request, Boolean.class);
        return sum;
    }

    @Override
    public List<Cart> getAllCarts() {
        // TODO Auto-generated method stub
        return Streamable.of(cartRepository.findAll()).toList();
    }

    @Override
    public Cart addEmptyCart(Cart cart) {
        // TODO Auto-generated method stub
        // log.info(String.format("add empty cart id %s", cart.id()));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Integer id) {
        // TODO Auto-generated method stub
        Optional<Cart> cart = this.cartRepository.findById(id);
        log.info(String.format("service cart id %s", cart.get().id()));
        if(cart.isEmpty())
            return null;
        else
            return cart.get();
    }

}