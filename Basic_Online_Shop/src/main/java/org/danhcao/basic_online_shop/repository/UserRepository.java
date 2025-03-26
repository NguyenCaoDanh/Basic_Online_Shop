package org.danhcao.basic_online_shop.repository;


import org.danhcao.basic_online_shop.entity.User;
import org.danhcao.basic_online_shop.generic.IRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends IRepository<User,Integer> {
    User findByAccount_IdAccount(int idAccount);
}
