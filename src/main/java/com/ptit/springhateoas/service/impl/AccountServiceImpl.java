package com.ptit.springhateoas.service.impl;

import com.ptit.springhateoas.entity.Account;
import com.ptit.springhateoas.repository.AccountRepository;
import com.ptit.springhateoas.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

  private final AccountRepository repository;

  public AccountServiceImpl(AccountRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Account> listAll() {
    return repository.findAll();
  }

  @Override
  public Account get(Integer id) {
    return repository.findById(id).orElseThrow();
  }

  @Override
  public Account save(Account account) {
    return repository.save(account);
  }

  @Override
  public Account deposit(float amount, Integer id) {
    repository.deposit(amount, id);
    return this.get(id);
  }

  @Override
  public Account withdraw(float amount, Integer id) {
    repository.withdraw(amount, id);
    return this.get(id);
  }

  @Override
  public void delete(Integer id) {
    repository.deleteById(id);
  }

  @Bean
  public CommandLineRunner initDatabase() {
    return args -> {
      Account account1 = new Account("1982080185", 1021.99f);
      Account account2 = new Account("1982032177", 231.50f);
      Account account3 = new Account("1982094128", 6211.00f);

      repository.saveAll(List.of(account1, account2, account3));

      log.info("Sample database initialized.");
    };
  }
}
