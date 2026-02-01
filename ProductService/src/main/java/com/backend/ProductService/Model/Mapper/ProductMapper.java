package com.backend.ProductService.Model.Mapper;

import com.backend.ProductService.Model.Dto.UpdateProductRequest;
import com.backend.ProductService.Model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

     void updateProductRequestToProduct(UpdateProductRequest updateProductRequest, @MappingTarget Product product);
}
