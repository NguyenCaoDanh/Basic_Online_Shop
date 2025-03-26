package org.danhcao.basic_online_shop.repository;

import org.danhcao.basic_online_shop.entity.Review;
import org.danhcao.basic_online_shop.generic.IRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends IRepository<Review,Integer> {
}
