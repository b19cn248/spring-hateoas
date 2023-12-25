package com.ptit.springhateoas.repository;

import com.ptit.springhateoas.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Integer> {

  @Query("UPDATE Account a SET a.balance = a.balance + ?1 WHERE a.id = ?2")
  @Modifying
  @Transactional
  void deposit(float amount, Integer id);

  @Query("UPDATE Account a SET a.balance = a.balance - ?1 WHERE a.id = ?2")
  @Modifying
  @Transactional
  void withdraw(float amount, Integer id);
}
