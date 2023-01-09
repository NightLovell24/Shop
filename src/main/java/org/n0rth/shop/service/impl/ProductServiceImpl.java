package org.n0rth.shop.service.impl;

import org.n0rth.shop.dao.ProductRepository;
import org.n0rth.shop.domain.Bucket;
import org.n0rth.shop.domain.User;
import org.n0rth.shop.dto.ProductDTO;
import org.n0rth.shop.mapper.ProductMapper;
import org.n0rth.shop.service.BucketService;
import org.n0rth.shop.service.ProductService;
import org.n0rth.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository repository;
    private final UserService userService;
    private final BucketService bucketService;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, UserService userService, BucketService bucketService) {
        this.repository = repository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(repository.findAll());
    }

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found - " + username);
        }
        Bucket bucket = user.getBucket();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }

}
