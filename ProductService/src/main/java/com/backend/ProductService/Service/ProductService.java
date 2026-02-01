package com.backend.ProductService.Service;

import com.backend.ProductService.Model.Dto.AddProductRequest;
import com.backend.ProductService.Model.Dto.ProductResponse;
import com.backend.ProductService.Model.Dto.UpdateProductRequest;
import com.backend.ProductService.Model.Dto.UpdateProductStatusRequest;
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
    public ResponseEntity<String> addProduct(AddProductRequest addProductRequest) {

//        Product product = new Product();
//        product.setName(addProductRequest.getName());
//        product.setDescription(addProductRequest.getDescription());
//        product.setImageUrl(addProductRequest.getImageUrl());
//        product.setPrice(addProductRequest.getPrice());
//        product.setCurrency(addProductRequest.getCurrency());
//        product.setStatus(addProductRequest.getStatus()==null ? ProductStatus.DRAFT:addProductRequest.getStatus());

        //Using Builder
        productRepo.save(
                Product.builder()
                .name(addProductRequest.getName())
                .description(addProductRequest.getDescription())
                .imageUrl(addProductRequest.getImageUrl())
                .price(addProductRequest.getPrice())
                .currency(addProductRequest.getCurrency())
                .status(addProductRequest.getStatus()==null ? ProductStatus.DRAFT : addProductRequest.getStatus())
                .build()
        );

        return new ResponseEntity<String>("Product added successfully", HttpStatus.CREATED);
    }



//As of now it is ok next I have to use Streams and Builder
    public ResponseEntity<List<ProductResponse>> getProducts() {
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

            //Using Builder
              productResponses.add(
                      ProductResponse.builder()
                      .productId(p.getProductId())
                      .name(p.getName())
                      .description(p.getDescription())
                      .imageUrl(p.getImageUrl())
                      .price(p.getPrice())
                      .currency(p.getCurrency())
                      .status(p.getStatus())
                      .createdAt(p.getCreatedAt())
                      .updatedAt(p.getUpdatedAt())
                      .build()
              );
        }

        return ResponseEntity.ok(productResponses);
    }

    public ResponseEntity<ProductResponse> getProductById(UUID productId) {

        Optional<Product> existingProduct = productRepo.findById(productId);

        if(existingProduct.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            Product product = existingProduct.get();
            ProductResponse productResponse = ProductResponse.builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .imageUrl(product.getImageUrl())
                    .price(product.getPrice())
                    .currency(product.getCurrency())
                    .status(product.getStatus())
                    .createdAt(product.getCreatedAt())
                    .updatedAt(product.getUpdatedAt())
                    .build();
            return ResponseEntity.ok(productResponse);
        }
    }

//Updates the complete product
    public ResponseEntity<String> updateProduct(UUID productId, UpdateProductRequest updateProductRequest) {
        Optional<Product> existingProduct = productRepo.findById(productId);

        if(existingProduct.isPresent())
        {
            Product product = existingProduct.get();
            product.setName(updateProductRequest.getName());
            product.setDescription(updateProductRequest.getDescription());
            product.setImageUrl(updateProductRequest.getImageUrl());
            product.setPrice(updateProductRequest.getPrice());
            product.setCurrency(updateProductRequest.getCurrency());
            product.setStatus(updateProductRequest.getStatus()==null ? ProductStatus.DRAFT : updateProductRequest.getStatus());

            productRepo.save(product);

            return ResponseEntity.ok("Product updated successfully");
        }
        else
            return new ResponseEntity<String>("Product not found",HttpStatus.NOT_FOUND);
    }

    //Updates just status of the product
    public ResponseEntity<String> updateProductStatus(UUID productId, UpdateProductStatusRequest updateProductStatusRequest) {
       Optional<Product> existingProduct = productRepo.findById(productId);

       if (existingProduct.isPresent())
       {
           Product product = existingProduct.get();
           product.setStatus(updateProductStatusRequest.getStatus()==null ? ProductStatus.DRAFT : updateProductStatusRequest.getStatus());
           productRepo.save(product);
           return ResponseEntity.ok("Status updated successfully");
       }
       else
           return new ResponseEntity<String>("Product not found",HttpStatus.NOT_FOUND);
    }

    @Autowired
    private ProductMapper productMapper;
    //Updates the product partially
    public ResponseEntity<String> updateProductPartially(UUID productId, UpdateProductRequest updateProductRequest) {
        Optional<Product> existingProduct = productRepo.findById(productId);

        if(existingProduct.isPresent())
        {
            Product product = existingProduct.get();
            productMapper.updateProductRequestToProduct(updateProductRequest,product);
            productRepo.save(product);

            return ResponseEntity.ok("Product updated successfully");
        }
        else
            return new ResponseEntity<String>("Product not found",HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<String> deleteProductById(UUID productId) {
        return productRepo.findById(productId).
                map(product -> {
                    productRepo.delete(product);
                    return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
                }).orElse(new ResponseEntity<String>("Product not found",HttpStatus.NOT_FOUND));
    }
}
