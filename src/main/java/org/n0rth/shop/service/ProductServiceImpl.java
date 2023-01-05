package org.n0rth.shop.service;

import org.n0rth.shop.dao.ProductRepository;
import org.n0rth.shop.dto.ProductDTO;
import org.n0rth.shop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(repository.findAll());
    }
}
