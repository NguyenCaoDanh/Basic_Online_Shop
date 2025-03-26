package org.danhcao.basic_online_shop.repository;


import org.danhcao.basic_online_shop.entity.Account;
import org.danhcao.basic_online_shop.generic.IRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends IRepository<Account,Integer> {
    Optional<Account> findByUsername(String username);
}
