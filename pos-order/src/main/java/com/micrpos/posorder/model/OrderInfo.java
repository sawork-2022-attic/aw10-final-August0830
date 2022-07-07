package com.micrpos.posorder.model;

import java.util.List;

import com.micropos.dto.CartItemDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo{
    private String orderId;
    private List<CartItemDto> items;
}