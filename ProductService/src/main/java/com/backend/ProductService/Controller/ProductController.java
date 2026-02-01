package com.backend.ProductService.Controller;

import com.backend.ProductService.Model.Dto.AddProductRequest;
import com.backend.ProductService.Model.Dto.ProductResponse;
import com.backend.ProductService.Model.Dto.UpdateProductRequest;
import com.backend.ProductService.Model.Dto.UpdateProductStatusRequest;
import com.backend.ProductService.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    //Add the product
    @PostMapping("/product")
    public ResponseEntity<String> addProduct(@RequestBody @Valid AddProductRequest addProductRequest){

        return productService.addProduct(addProductRequest);

    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable UUID productId, @RequestBody @Valid UpdateProductRequest updateProductRequest){
        return productService.updateProduct(productId,updateProductRequest);
    }

    //This is the partial product update controller
    @PatchMapping("/product/{productId}")
    public ResponseEntity<String> updateProductPartially(@PathVariable UUID productId, @RequestBody @Valid UpdateProductRequest updateProductRequest){
        return productService.updateProductPartially(productId,updateProductRequest);
    }

    @PatchMapping("/product/{productId}/status")
    public ResponseEntity<String> updateProductStatus(@PathVariable UUID productId, @RequestBody UpdateProductStatusRequest updateProductStatusRequest){
        return productService.updateProductStatus(productId,updateProductStatusRequest);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return productService.getProducts();
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId){
        return productService.getProductById(productId);
    }

    //Soft Delete
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID productId){
        return productService.deleteProductById(productId);
    }
}
