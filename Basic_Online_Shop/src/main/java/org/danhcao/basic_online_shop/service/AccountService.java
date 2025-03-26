package org.danhcao.basic_online_shop.service;


import org.danhcao.basic_online_shop.entity.Account;
import org.danhcao.basic_online_shop.generic.IRepository;
import org.danhcao.basic_online_shop.generic.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends IService<Account,Integer>, UserDetailsService {
    IRepository<Account, Integer> getRepository();
}
