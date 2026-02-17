package com.backend.ProductService.Service;

import com.backend.ProductService.Client.Dto.ImageKeyValidationResponse;
import com.backend.ProductService.Client.MediaClient;
import com.backend.ProductService.Exception.ProductImageValidationException;
import com.backend.ProductService.Exception.ProductNotFoundException;
import com.backend.ProductService.Model.Dto.*;
import com.backend.ProductService.Model.Mapper.ProductMapper;
import com.backend.ProductService.Model.Product;
import com.backend.ProductService.Model.ProductStatus;
import com.backend.ProductService.Repo.ProductRepo;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private MediaClient mediaClient;

    private void validateImageKeyIfPresent(String imageKey)
    {
        if(imageKey == null || imageKey.isBlank()) return;

        ImageKeyValidationResponse validation = mediaClient.validateImageKey(imageKey);
        if (!validation.isValid())
            throw new ProductImageValidationException
                    ("Invalid Key. Status: "+validation.getStatus());

    }

    private void cleanupOldImageKeyIfChanged(String oldKey,String newKey)
    {
        if(oldKey == null || oldKey.isBlank()) return;
        if (newKey == null || newKey.isBlank()) return;
        if (oldKey.equals(newKey)) return;
        try {
            mediaClient.deleteImageKey(oldKey);
        }catch (FeignException fe)
        {
            log.error("Media cleanup failed oldImageKey={}. Manual cleanup needed.", oldKey, fe);
        }

    }

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

       validateImageKeyIfPresent(addProductRequest.getImageKey());
// ================= Using Mapper ======================
        productRepo.save(productMapper.addProductRequestToProduct(addProductRequest));
        return "Product added successfully";
    }




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

            String oldKey = product.getImageKey();
            String newKey = updateProductRequest.getImageKey();

            validateImageKeyIfPresent(newKey);

            productMapper.updateProductRequestToProduct(updateProductRequest,product);
            productRepo.save(product);

            cleanupOldImageKeyIfChanged(oldKey,newKey);
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

            String oldKey = product.getImageKey();
            String newKey = patchUpdateProductRequest.getImageKey();

            validateImageKeyIfPresent(newKey);
            productMapper.patchupdateProductRequestToProduct(patchUpdateProductRequest,product);
            productRepo.save(product);

            cleanupOldImageKeyIfChanged(oldKey,newKey);

            return "Product updated successfully";
        }
        else
            throw new ProductNotFoundException("Product not found");
    }


    public String deleteProductById(UUID productId) {
       Product product = productRepo.findById(productId)
               .orElseThrow(()-> new ProductNotFoundException("Product not found"));

       String imageKey = product.getImageKey();
       productRepo.delete(product);

       if (imageKey!=null && !imageKey.isBlank())
       {
           try
           {
               mediaClient.deleteImageKey(imageKey);
           }catch (FeignException fe)
           {
               log.error("Media cleanup failed ImageKey:{}.Manual cleanup is needed.", imageKey, fe);
           }
       }



       return "Deleted successfully";
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

    public ProductResponse updateProductImage(UUID productId, UpdateProductImageRequest updateProductImageRequest) {
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

        String oldKey = existingProduct.getImageKey();
        String newKey = updateProductImageRequest.getImageKey();

        validateImageKeyIfPresent(newKey);
        existingProduct.setImageKey(newKey);
        Product updatedProduct = productRepo.save(existingProduct);

        cleanupOldImageKeyIfChanged(oldKey,newKey);

        return productMapper.productToProductResponse(updatedProduct);
    }
}
