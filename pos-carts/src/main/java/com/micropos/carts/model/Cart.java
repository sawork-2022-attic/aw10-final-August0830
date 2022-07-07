package com.micropos.carts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.micropos.dto.CartItemDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.*;

@Entity
@Table(name = "carts")
@Accessors(fluent = true, chain=true)
@Data
public class Cart implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5555559482394141750L;

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="cartItems",
    joinColumns = @JoinColumn(name="cart_id"))
    @Getter
    @Setter
    private List<CartItemDto> cartItems = new ArrayList<>();

    public Cart(){
        id = -1;
    }

    public Cart(Integer id, List<CartItemDto> cartItems){
        this.id = id;
        if(cartItems!=null && !cartItems.isEmpty())
            this.cartItems.addAll(cartItems);
    }

    public boolean addItem(CartItemDto item){
        return cartItems.add(item);
    }

    public boolean removeItem(CartItemDto item){
        return cartItems.remove(item);
    }

}