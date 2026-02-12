package com.backend.ProductService.Model.Mapper;


import com.backend.ProductService.Model.Dto.AddProductRequest;
import com.backend.ProductService.Model.Dto.PatchUpdateProductRequest;
import com.backend.ProductService.Model.Dto.ProductResponse;
import com.backend.ProductService.Model.Dto.UpdateProductRequest;
import com.backend.ProductService.Model.Product;
import com.backend.ProductService.Model.ProductStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring",imports = {ProductStatus.class})
public interface ProductMapper {

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void patchupdateProductRequestToProduct(PatchUpdateProductRequest patchUpdateProductRequest, @MappingTarget Product product);

     @Mapping(target = "productId",ignore = true)
     @Mapping(source = "status",target = "status",defaultExpression = "java(ProductStatus.DRAFT)")
     Product addProductRequestToProduct(AddProductRequest addProductRequest);

     ProductResponse productToProductResponse(Product product);

     @Mapping(source = "status",target = "status",defaultExpression = "java(ProductStatus.DRAFT)")
     void updateProductRequestToProduct(
             UpdateProductRequest updateProductRequest,@MappingTarget Product product);


}
