package dev.oleksa.product.controller;

import dev.oleksa.product.constants.ProductConstants;
import dev.oleksa.product.dto.request.ProductDetailsRequest;
import dev.oleksa.product.dto.response.ProductDetailsResponse;
import dev.oleksa.product.dto.response.ResponseDto;
import dev.oleksa.product.entity.Product;
import dev.oleksa.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin/create")
    public ResponseEntity<ResponseDto> createProduct(
            @Valid @RequestBody ProductDetailsRequest productDto
    ) {
        Long id = productService.createProduct(productDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProductConstants.STATUS_201, "Product was created. Id: " + id));
    }

    @GetMapping("/fetch/{product-id}")
    public ResponseEntity<ProductDetailsResponse> fetchProduct(
            @PathVariable(name = "product-id") Long productId
    ) {

        ProductDetailsResponse product = productService.fetchProduct(productId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<ResponseDto> updateProduct(
            @Valid @RequestBody ProductDetailsRequest productDto
    ) {

        System.out.println("ID " + productDto.getId());

        boolean isUpdated = productService.updateProduct(productDto);

        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_UPDATE));
        }
    }

    @PatchMapping("/admin/deactivate/{product-id}")
    public ResponseEntity<ResponseDto> deactivateProduct(
            @PathVariable(name = "product-id") Long productId
    ) {
        boolean isDeleted = productService.deactivateProduct(productId);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProductConstants.STATUS_200, ProductConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProductConstants.STATUS_417, ProductConstants.MESSAGE_417_DELETE));
        }
    }

    @GetMapping("/search-products")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam(name = "search-text") String searchText,
            Pageable pageable
    ) {

        Page<Product> productsList = productService.getPaginableListByNameContaining(searchText, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productsList);
    }

    @GetMapping("/fetch-products")
    public ResponseEntity<Page<Product>> fetchProductPage(
            Pageable pageable
    ) {
        Page<Product> productsList = productService.getPaginableList(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productsList);
    }

    @GetMapping("/fetch-products-with-filters")
    public ResponseEntity<Page<Product>> fetchProductsWithFilters(
            @RequestParam double minPrice,
            @RequestParam double maxPrice,
            @RequestParam List<Long> categoryIds,
            @RequestParam(name = "sorting-order") String sortingOrder,
            @RequestParam(name = "sorting-method") String sortingMethod,
            Pageable pageable
    ) {

        Page<Product> productsList = productService.getProductsByCategoriesAndPriceRange(minPrice, maxPrice, categoryIds, sortingOrder, sortingMethod, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productsList);
    }

}
