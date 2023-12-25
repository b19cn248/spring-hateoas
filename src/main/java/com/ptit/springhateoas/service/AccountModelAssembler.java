package com.ptit.springhateoas.service;

import com.ptit.springhateoas.controller.AccountController;
import com.ptit.springhateoas.entity.Account;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {
  @Override
  public EntityModel<Account> toModel(Account entity) {
    EntityModel<Account> accountModel = EntityModel.of(entity);

    accountModel.add(linkTo(methodOn(AccountController.class).getOne(entity.getId())).withSelfRel());
    accountModel.add(linkTo(methodOn(AccountController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));

    return accountModel;
  }
}
