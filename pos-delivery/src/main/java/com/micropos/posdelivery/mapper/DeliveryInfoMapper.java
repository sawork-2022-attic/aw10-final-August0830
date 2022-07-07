package com.micropos.posdelivery.mapper;

import org.mapstruct.Mapper;

import com.micropos.dto.DeliveryInfoDto;
import com.micropos.posdelivery.model.DeliveryInfo;

@Mapper
public interface DeliveryInfoMapper {
    DeliveryInfo toDeliveryInfo(DeliveryInfoDto deliveryInfoDto);
    DeliveryInfoDto toDeliveryInfoDto(DeliveryInfo deliveryInfo);
}
