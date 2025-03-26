package org.danhcao.basic_online_shop.service.impl;


import org.danhcao.basic_online_shop.entity.Account;
import org.danhcao.basic_online_shop.entity.Role;
import org.danhcao.basic_online_shop.exception.ErrorHandler;
import org.danhcao.basic_online_shop.generic.GenericService;
import org.danhcao.basic_online_shop.generic.IRepository;
import org.danhcao.basic_online_shop.repository.AccountRepository;
import org.danhcao.basic_online_shop.repository.RoleRepository;
import org.danhcao.basic_online_shop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GenericService generalService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(Account account) {
        try {
            // Kiểm tra username đã tồn tại
            if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
                throw new ErrorHandler(HttpStatus.BAD_REQUEST, "Username already exists.");
            }

            // Validate mật khẩu
            generalService.validatePassword(account.getPassword());

            // Gán vai trò mặc định
            Role role = roleRepository.findById(1)
                    .orElseThrow(() -> new ErrorHandler(HttpStatus.BAD_REQUEST, "Role with ID 1 not found"));

            account.setRole(role);
            account.setPassword(passwordEncoder.encode(account.getPassword()));


            // Lưu tài khoản (cascade sẽ tự lưu User)
            accountRepository.save(account);
        } catch (Exception e) {
            throw new ErrorHandler(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Override
    public void delete(Integer integer) {

    }


    @Override
    public Iterator<Account> findAll() {
        return null;
    }

    @Override
    public Account findOne(Integer integer) {
        return null;
    }


    @Override
    public IRepository<Account, Integer> getRepository() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.orElseThrow(() -> new ErrorHandler(HttpStatus.UNAUTHORIZED, "Account not exist"));
    }
}
