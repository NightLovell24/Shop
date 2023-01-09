package org.n0rth.shop.service;

import org.n0rth.shop.domain.Bucket;
import org.n0rth.shop.domain.User;
import org.n0rth.shop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);

    BucketDTO getBucketByUser(String name);
}
