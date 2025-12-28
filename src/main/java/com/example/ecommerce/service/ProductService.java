package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 支持分页和分类查询
    public Page<Product> findProducts(int pageNum, int pageSize, String category) {
        // 创建分页请求：页码，每页数量，排序方式(按ID倒序，即新商品排前面)
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());

        if (category != null && !category.isEmpty()) {
            // 如果用户选了分类，就按分类查
            return productRepository.findByCategory(category, pageable);
        } else {
            // 如果没选分类，就分页查询所有
            return productRepository.findAll(pageable);
        }
    }



    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}