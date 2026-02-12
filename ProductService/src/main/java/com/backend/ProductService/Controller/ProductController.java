package com.backend.ProductService.Controller;

import com.backend.ProductService.Model.Dto.*;
import com.backend.ProductService.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(productService.addProduct(addProductRequest), HttpStatus.CREATED);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable UUID productId, @RequestBody @Valid UpdateProductRequest updateProductRequest){
        return ResponseEntity.ok(productService.updateProduct(productId,updateProductRequest));
    }

    //This is the partial product update controller
    @PatchMapping("/product/{productId}")
    public ResponseEntity<String> updateProductPartially(@PathVariable UUID productId, @RequestBody PatchUpdateProductRequest patchUpdateProductRequest){
        return ResponseEntity.ok(productService.updateProductPartially(productId,patchUpdateProductRequest));
    }

    @PatchMapping("/product/{productId}/status")
    public ResponseEntity<String> updateProductStatus(@PathVariable UUID productId, @RequestBody UpdateProductStatusRequest updateProductStatusRequest){
        return ResponseEntity.ok(productService.updateProductStatus(productId,updateProductStatusRequest));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/product/{productId}/price")
    public ResponseEntity<ProductPriceResponse> getProductPriceById(@PathVariable UUID productId){
        return ResponseEntity.ok(productService.getProductPriceById(productId));
    }

    //Soft Delete
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID productId){
        return ResponseEntity.ok(productService.deleteProductById(productId));
    }
}
