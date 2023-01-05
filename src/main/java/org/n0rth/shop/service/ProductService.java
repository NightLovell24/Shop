package org.n0rth.shop.service;

import org.n0rth.shop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
}
