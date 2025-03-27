package org.danhcao.basic_online_shop.repository;

import org.danhcao.basic_online_shop.entity.Banner;
import org.danhcao.basic_online_shop.generic.IRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends IRepository<Banner, Integer> {
    Page<Banner> findAll(Pageable pageable);
}
