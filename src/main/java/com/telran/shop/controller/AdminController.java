package com.telran.shop.controller;

import com.telran.shop.controller.dto.*;
import com.telran.shop.data.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminRepository repository;

    @PostMapping("category")
    public AddUnitResponseDto addCategory(@RequestBody CategoryDto dto) {
        String id = repository.addCategory(dto.getName());
        return AddUnitResponseDto.builder().id(id).build();
    }

    @PostMapping("product")
    public AddUnitResponseDto addProduct(@RequestBody ProductDto dto) {
        String id = repository.addProduct(dto.getName(), dto.getPrice(), dto.getCategory().getId());
        return AddUnitResponseDto.builder().id(id).build();
    }

    @DeleteMapping("product/{id}")
    public SuccessResponseDto delProduct(@PathVariable("id") String productId) {
        if (repository.removeProduct(productId)) {
            return new SuccessResponseDto(String.format("Product with id: %s was deleted!", productId));
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Product with id: %s wasn't deleted!", productId));
    }

    @DeleteMapping("category/{id}")
    public SuccessResponseDto delCategory(@PathVariable("id") String categoryId) {
        if (repository.removeCategory(categoryId)) {
            return new SuccessResponseDto(String.format("Category with id: %s was deleted!", categoryId));
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Category with id: %s wasn't deleted!", categoryId));
    }

    @PutMapping("category")
    public SuccessResponseDto updCategory(@RequestBody UpdCategoryDto categoryDto) {
        if (repository.updateCategory(categoryDto.getCatId(), categoryDto.getCatName())) {
            return new SuccessResponseDto(String.format("Category with id %s was updated", categoryDto.getCatId()));
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Category with id %s wasn't updated", categoryDto.getCatId()));
    }

    @PutMapping("product")
    public SuccessResponseDto updProductPrice(@RequestBody UpdProductDto productDto) {
        if (repository.changeProductPrice(productDto.getProdId(), productDto.getPrice())) {
            return new SuccessResponseDto(String.format("Price of product with id %s was updated to %f", productDto.getProdId(), productDto.getPrice()));
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Price of product with %s wasn't updated", productDto.getProdId()));
    }

    @PutMapping("user")
    public SuccessResponseDto updUserBalance(@RequestBody UpdUserBalanceDto userBalance) {
        if (repository.addBalance(userBalance.getUserId(), userBalance.getBalance())) {
            return new SuccessResponseDto(String.format("Balance of user %s was updated to %f", userBalance.getUserId(), userBalance.getBalance()));
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Balance of user %s wasn't updated", userBalance.getUserId()));
    }
}
