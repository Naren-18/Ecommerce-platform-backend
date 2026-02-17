package com.backend.ProductService.Client;

import com.backend.ProductService.Client.Dto.ImageKeyValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "MEDIASERVICE",path = "/media")
public interface MediaClient {

    @GetMapping("/imageKey/validate")
    ImageKeyValidationResponse validateImageKey(@RequestParam String imageKey);

    @DeleteMapping("/imageKey/delete")
    String deleteImageKey(@RequestParam String imageKey);
}
