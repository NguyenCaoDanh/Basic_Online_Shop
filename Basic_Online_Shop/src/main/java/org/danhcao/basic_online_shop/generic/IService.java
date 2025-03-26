package org.danhcao.basic_online_shop.generic;

import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public interface IService<T, ID> {
    void save(T t);

    void delete(ID id);

    Iterator<T> findAll();

    T findOne(ID id);

}
