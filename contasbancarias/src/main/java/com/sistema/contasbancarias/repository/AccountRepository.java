package com.sistema.contasbancarias.repository;

import com.sistema.contasbancarias.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account,Long>{

}
