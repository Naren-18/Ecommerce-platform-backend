package com.backend.ProductService.Service;

import com.backend.ProductService.Exception.ProductNotFoundException;
import com.backend.ProductService.Model.Dto.*;
import com.backend.ProductService.Model.Mapper.ProductMapper;
import com.backend.ProductService.Model.Product;
import com.backend.ProductService.Model.ProductStatus;
import com.backend.ProductService.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
//add product method
    public String addProduct(AddProductRequest addProductRequest) {

//        Product product = new Product();
//        product.setName(addProductRequest.getName());
//        product.setDescription(addProductRequest.getDescription());
//        product.setImageUrl(addProductRequest.getImageUrl());
//        product.setPrice(addProductRequest.getPrice());
//        product.setCurrency(addProductRequest.getCurrency());
//        product.setStatus(addProductRequest.getStatus()==null ? ProductStatus.DRAFT:addProductRequest.getStatus());

        // ================= Using Builder ======================
//        Product.builder()
//                .name(addProductRequest.getName())
//                .description(addProductRequest.getDescription())
//                .imageUrl(addProductRequest.getImageUrl())
//                .price(addProductRequest.getPrice())
//                .currency(addProductRequest.getCurrency())
//                .status(addProductRequest.getStatus()==null ? ProductStatus.DRAFT : addProductRequest.getStatus())
//                .build();

// ================= Using Mapper ======================
        productRepo.save(productMapper.addProductRequestToProduct(addProductRequest));
        return "Product added successfully";
    }



//As of now it is ok next I have to use Streams and Builder
    public List<ProductResponse> getProducts() {
        List<Product> product = productRepo.findAll();

        List<ProductResponse> productResponses = new ArrayList<>();


        for(Product p:product)
        {
//            ProductResponse productResponse = new ProductResponse();
//
//              productResponse.setProductId(p.getProductId());
//              productResponse.setName(p.getName());
//              productResponse.setDescription(p.getDescription());
//              productResponse.setImageUrl(p.getImageUrl());
//              productResponse.setPrice(p.getPrice());
//              productResponse.setCurrency(p.getCurrency());
//              productResponse.setStatus(p.getStatus());
//              productResponse.setCreatedAt(p.getCreatedAt());
//              productResponse.setUpdatedAt(p.getUpdatedAt());

            //=========== Using Builder ===============
//              productResponses.add(
//                      ProductResponse.builder()
//                      .productId(p.getProductId())
//                      .name(p.getName())
//                      .description(p.getDescription())
//                      .imageUrl(p.getImageUrl())
//                      .price(p.getPrice())
//                      .currency(p.getCurrency())
//                      .status(p.getStatus())
//                      .createdAt(p.getCreatedAt())
//                      .updatedAt(p.getUpdatedAt())
//                      .build()
//              );

              productResponses.add(productMapper.productToProductResponse(p));
        }

        return productResponses;
    }

    public ProductResponse getProductById(UUID productId) {

        Optional<Product> existingProduct = productRepo.findById(productId);

        if(existingProduct.isEmpty())
            throw new ProductNotFoundException("Product not found");
        else
        {
            Product product = existingProduct.get();

//            ProductResponse productResponse = ProductResponse.builder()
//                    .productId(product.getProductId())
//                    .name(product.getName())
//                    .description(product.getDescription())
//                    .imageUrl(product.getImageUrl())
//                    .price(product.getPrice())
//                    .currency(product.getCurrency())
//                    .status(product.getStatus())
//                    .createdAt(product.getCreatedAt())
//                    .updatedAt(product.getUpdatedAt())
//                    .build();
            return  productMapper.productToProductResponse(product);
        }
    }

//Updates the complete product
    public String updateProduct(UUID productId, UpdateProductRequest updateProductRequest) {
        Optional<Product> existingProduct = productRepo.findById(productId);

        if(existingProduct.isPresent())
        {
            Product product = existingProduct.get();
//            product.setName(updateProductRequest.getName());
//            product.setDescription(updateProductRequest.getDescription());
//            product.setImageUrl(updateProductRequest.getImageUrl());
//            product.setPrice(updateProductRequest.getPrice());
//            product.setCurrency(updateProductRequest.getCurrency());
//            product.setStatus(updateProductRequest.getStatus()==null ? ProductStatus.DRAFT : updateProductRequest.getStatus());
            productMapper.updateProductRequestToProduct(updateProductRequest,product);
            productRepo.save(product);

            return "Product updated successfully";
        }
        else
            throw new ProductNotFoundException("Product not found") ;
    }

    //Updates just status of the product
    public String updateProductStatus(UUID productId, UpdateProductStatusRequest updateProductStatusRequest) {
       Optional<Product> existingProduct = productRepo.findById(productId);

       if (existingProduct.isPresent())
       {
           Product product = existingProduct.get();
           product.setStatus(updateProductStatusRequest.getStatus()==null ? ProductStatus.DRAFT : updateProductStatusRequest.getStatus());
           productRepo.save(product);
           return "Status updated successfully";
       }
       else
           throw new ProductNotFoundException("Product not found") ;
    }

    @Autowired
    private ProductMapper productMapper;
    //Updates the product partially
    public String updateProductPartially(UUID productId, PatchUpdateProductRequest patchUpdateProductRequest) {
        Optional<Product> existingProduct = productRepo.findById(productId);

        if(existingProduct.isPresent())
        {
            Product product = existingProduct.get();
            productMapper.patchupdateProductRequestToProduct(patchUpdateProductRequest,product);
            productRepo.save(product);

            return "Product updated successfully";
        }
        else
            throw new ProductNotFoundException("Product not found");
    }


    public String deleteProductById(UUID productId) {
        return productRepo.findById(productId).
                map(product -> {
                    productRepo.delete(product);
                    return "Deleted successfully";
                }).orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    public ProductPriceResponse getProductPriceById(UUID productId) {
       Optional<Product> existingProduct = productRepo.findById(productId);
       if(existingProduct.isPresent())
       {
           Product product = existingProduct.get();
           ProductPriceResponse productPriceResponse = new ProductPriceResponse();
           productPriceResponse.setPrice(product.getPrice());
           productPriceResponse.setCurrency(product.getCurrency());
           return productPriceResponse;
       }
       else
           throw new ProductNotFoundException("Product not found");
    }
}
