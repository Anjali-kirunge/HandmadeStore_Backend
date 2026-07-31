package com.handmadecrafts.backend.service;

import com.handmadecrafts.backend.dto.CategoryDto;
import com.handmadecrafts.backend.dto.ProductDto;
import com.handmadecrafts.backend.dto.ProductImageDto;
import com.handmadecrafts.backend.entity.Category;
import com.handmadecrafts.backend.entity.Product;
import com.handmadecrafts.backend.entity.ProductImage;
import com.handmadecrafts.backend.exception.ResourceNotFoundException;
import com.handmadecrafts.backend.repository.ProductImageRepository;
import com.handmadecrafts.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return convertToDto(product);
    }

    public List<ProductDto> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductImageDto> getProductImages(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with ID: " + productId);
        }
        return productImageRepository.findByProductProductId(productId).stream()
                .map(this::convertToImageDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        CategoryDto categoryDto = null;
        if (product.getCategory() != null) {
            Category category = product.getCategory();
            categoryDto = CategoryDto.builder()
                    .categoryId(category.getCategoryId())
                    .categoryName(category.getCategoryName())
                    .build();
        }

        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(categoryDto)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    private ProductImageDto convertToImageDto(ProductImage image) {
        return ProductImageDto.builder()
                .imageId(image.getImageId())
                .productId(image.getProduct() != null ? image.getProduct().getProductId() : null)
                .imageUrl(image.getImageUrl())
                .build();
    }
}
