package com.micrpos.posorder.mapper;


import org.mapstruct.Mapper;

import com.micropos.dto.OrderInfoDto;
import com.micrpos.posorder.model.OrderInfo;

@Mapper
public interface OrderInfoMapper {
    OrderInfo toOrderInfo(OrderInfoDto orderInfoDto);
    OrderInfoDto toOrderInfoDto(OrderInfo orderInfo);
}
