package dev.oleksa.product.service.impl;

import dev.oleksa.product.constants.SortingOrder;
import dev.oleksa.product.dto.request.ProductDetailsRequest;
import dev.oleksa.product.dto.response.ProductDetailsResponse;
import dev.oleksa.product.entity.Product;
import dev.oleksa.product.exception.ResourceNotFoundException;
import dev.oleksa.product.repository.ProductRepository;
import dev.oleksa.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Long createProduct(ProductDetailsRequest productDto) {
        Product product = Product.builder()
                .sku(productDto.getSku())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .unitsInStock(productDto.getUnitsInStock())
                .imageUrl(productDto.getImageUrl())
                .active(productDto.getActive())
                .categoryIds(productDto.getCategoryIds()).build();

        product = repository.save(product);

        return product.getId();
    }

    @Override
    public ProductDetailsResponse fetchProduct(Long productId) {
        Product product = repository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "productId", productId)
        );

        return ProductDetailsResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .price(product.getPrice())
                .unitsInStock(product.getUnitsInStock())
                .imageUrl(product.getImageUrl())
                .active(product.isActive())
                .categoryIds(product.getCategoryIds())
                .build();
    }

    @Override
    public boolean updateProduct(ProductDetailsRequest productDto) {
        Product product = repository.findById(productDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product", "productId", productDto.getId())
        );

        product.setSku(productDto.getSku());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setUnitsInStock(productDto.getUnitsInStock());
        product.setImageUrl(productDto.getImageUrl());
        product.setActive(productDto.getActive());
        product.setCategoryIds(productDto.getCategoryIds());

        repository.save(product);

        return true;
    }

    @Override
    public boolean deactivateProduct(Long productId) {
        Product product = repository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "productId", productId)
        );
        product.setActive(false);
        repository.save(product);
        return true;
    }

    @Override
    public Page<Product> getPaginableList(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> getPaginableListByNameContaining(String text, Pageable pageable) {
        return repository.findByNameContaining(text, pageable);
    }

    @Override
    public Page<Product> getProductsByCategoriesAndPriceRange(double minPrice, double maxPrice, List<Long> categoryIds, String sortingOrder, String sortingField, Pageable pageable) {
        var order = Sort.Direction.ASC;
        if (sortingOrder.trim().equals(SortingOrder.DESC.label)) {
            order = Sort.Direction.DESC;
        }

        List<Long> idList = new ArrayList<>();
        for (Long i : categoryIds) {
            if (i != 0) {
                idList.add(i);
            }
        }

        if (idList.isEmpty()) {
            return repository.findProductsByPriceRange(
                    minPrice,
                    maxPrice,
                    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(order, sortingField)));
        }
        return repository.findProductsByCategoriesAndPriceRange(
                minPrice,
                maxPrice,
                idList,
                idList.size(),
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(order, "p." + sortingField))
        );
    }
}
