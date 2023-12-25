package com.ptit.springhateoas.controller;

import com.ptit.springhateoas.entity.Account;
import com.ptit.springhateoas.service.AccountModelAssembler;
import com.ptit.springhateoas.service.AccountService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {
  private final AccountService service;
  private final AccountModelAssembler modelAssembler;

  public AccountController(AccountService service, AccountModelAssembler modelAssembler) {
    this.service = service;
    this.modelAssembler = modelAssembler;
  }

  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<Account>> getOne(@PathVariable("id") Integer id) {
    try {
      Account account = service.get(id);
      EntityModel<Account> model = modelAssembler.toModel(account);

      return new ResponseEntity<>(model, HttpStatus.OK);
    } catch (NoSuchElementException ex) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public CollectionModel<EntityModel<Account>> listAll() {
    List<EntityModel<Account>> listEntityModel = service.listAll()
          .stream().map(modelAssembler::toModel).toList();

    CollectionModel<EntityModel<Account>> collectionModel = CollectionModel.of(listEntityModel);

    collectionModel.add(linkTo(methodOn(AccountController.class).listAll()).withSelfRel());

    return collectionModel;
  }

  @PostMapping
  public HttpEntity<Account> add(@RequestBody Account account) {
    Account savedAccount = service.save(account);

    account.add(linkTo(methodOn(AccountController.class)
          .getOne(savedAccount.getId())).withSelfRel());

    account.add(linkTo(methodOn(AccountController.class)
          .listAll()).withRel(IanaLinkRelations.COLLECTION));

    return ResponseEntity.created(linkTo(methodOn(AccountController.class)
          .getOne(savedAccount.getId())).toUri()).body(savedAccount);
  }

  @PutMapping
  public HttpEntity<Account> replace(@RequestBody Account account) {
    Account updatedAccount = service.save(account);

    updatedAccount.add(linkTo(methodOn(AccountController.class)
          .getOne(updatedAccount.getId())).withSelfRel());

    updatedAccount.add(linkTo(methodOn(AccountController.class)
          .listAll()).withRel(IanaLinkRelations.COLLECTION));

    return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
  }
}
