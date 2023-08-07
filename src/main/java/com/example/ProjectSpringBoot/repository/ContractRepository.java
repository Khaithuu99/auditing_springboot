package com.example.ProjectSpringBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ProjectSpringBoot.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long>{
    @Query(value = "SELECT c.* FROM contract c LEFT JOIN accounting a ON c.accounting_account_id = a.account_id LEFT JOIN consultancy cn ON c.consultancy_consultancy_id = cn.consultancy_id LEFT JOIN audit ad ON c.audit_audit_id = ad.audit_id WHERE a.client_client_id = ?1 OR cn.client_client_id = ?1 OR ad.client_client_id = ?1", nativeQuery=true)
     List<Contract> findAllByClientId(long clientId);

    @Query(value = "SELECT c.* FROM contract c LEFT JOIN accounting a ON c.accounting_account_id = a.account_id LEFT JOIN consultancy cn ON c.consultancy_consultancy_id = cn.consultancy_id LEFT JOIN audit ad ON c.audit_audit_id = ad.audit_id WHERE a.account_id = ?1 OR cn.consultancy_id = ?1 OR ad.audit_id = ?1", nativeQuery=true)
    Contract findAllByEitherId(long clientId);

}
