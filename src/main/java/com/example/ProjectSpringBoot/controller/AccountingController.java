package com.example.ProjectSpringBoot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProjectSpringBoot.DTO.AccountRequestDTO;
import com.example.ProjectSpringBoot.DTO.AccountResponseDTO;
import com.example.ProjectSpringBoot.exception.ResourceNotFoundException;
import com.example.ProjectSpringBoot.model.Accounting;
import com.example.ProjectSpringBoot.model.Client;
import com.example.ProjectSpringBoot.model.Consultancy;
import com.example.ProjectSpringBoot.repository.AccountingRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class AccountingController {

    @Autowired
    private AccountingRepository accountingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/accounting")
    public List<AccountResponseDTO> getAllAccount() {
        List<AccountResponseDTO> list = new ArrayList<>();
        for (Accounting accounting : accountingRepository.findAll()) {
            AccountResponseDTO accountResponseDTO = modelMapper.map(accounting, AccountResponseDTO.class);
            accountResponseDTO.setFirmEmail(accounting.getClient().getFirmEmail());
            accountResponseDTO.setFirmAddress(accounting.getClient().getFirmAddress());
            accountResponseDTO.setFirmPhone(accounting.getClient().getFirmPhone());
            accountResponseDTO.setFirmName(accounting.getClient().getFirmName());
            accountResponseDTO.setFirmType(accounting.getClient().getFirmType());
            accountResponseDTO.setStartingDate(accounting.getStartingDate());
            accountResponseDTO.setEndingDate(accounting.getEndingDate());
            accountResponseDTO.setAccountingPeriod(accounting.getAccountingPeriod());
            accountResponseDTO.setAccountingStandard(accounting.getAccountingStandard());
            accountResponseDTO.setPreviousFinancialStatement(accounting.getPreviousFinancialStatement());
            accountResponseDTO.setBusinessNature(accounting.getBusinessNature());
            accountResponseDTO.setAccountId(accounting.getAccountId());

            list.add(accountResponseDTO);
        }
        return list;
    }


    // get accounting by client id
    @GetMapping("/accounting/client/{id}")
    public List<AccountResponseDTO> getAllAccountByClientId(@PathVariable long id) {
        List<AccountResponseDTO> list = new ArrayList<>();
        for (Accounting accounting : accountingRepository.findByClientId(id)) {
            AccountResponseDTO accountResponseDTO = modelMapper.map(accounting, AccountResponseDTO.class);
            accountResponseDTO.setFirmEmail(accounting.getClient().getFirmEmail());
            accountResponseDTO.setFirmAddress(accounting.getClient().getFirmAddress());
            accountResponseDTO.setFirmPhone(accounting.getClient().getFirmPhone());
            accountResponseDTO.setFirmName(accounting.getClient().getFirmName());
            accountResponseDTO.setFirmType(accounting.getClient().getFirmType());
            accountResponseDTO.setStartingDate(accounting.getStartingDate());
            accountResponseDTO.setEndingDate(accounting.getEndingDate());
            accountResponseDTO.setAccountingPeriod(accounting.getAccountingPeriod());
            accountResponseDTO.setAccountingStandard(accounting.getAccountingStandard());
            accountResponseDTO.setPreviousFinancialStatement(accounting.getPreviousFinancialStatement());
            accountResponseDTO.setBusinessNature(accounting.getBusinessNature());
            accountResponseDTO.setAccountId(accounting.getAccountId());

            list.add(accountResponseDTO);
        }
        return list;
    }

    @PostMapping("/accounting")
    public Accounting addAccounting(@RequestBody AccountRequestDTO accountRequestDTO) {
        Client client = new Client();

        client.setClientId(accountRequestDTO.getClientId());

        Accounting accounting = modelMapper.map(accountRequestDTO, Accounting.class);
        accounting.setClient(client);
        accounting.setApprovalStatus("Not Approved");
        accounting.setContractStatus("Not Generated");
        accounting.setEngagementDate("Not Set");

        return accountingRepository.save(accounting);
    }

    @PutMapping("/accounting/{id}")
    public ResponseEntity<?> updateAccounting(@PathVariable("id") Long id, @RequestBody Accounting accounting) {
        Accounting updateAccount = accountingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        updateAccount.setStartingDate(accounting.getStartingDate());
        updateAccount.setEndingDate(accounting.getEndingDate());
        updateAccount.setAccountingPeriod(accounting.getAccountingPeriod());
        updateAccount.setFinancialStatements(accounting.getFinancialStatements());
        updateAccount.setAccountingStandard(accounting.getAccountingStandard());
        updateAccount.setPreviousFinancialStatement(accounting.getPreviousFinancialStatement());
        updateAccount.setBusinessNature(accounting.getBusinessNature());

        Accounting accounting2 = accountingRepository.save(updateAccount);

        return ResponseEntity.ok(accounting2);
    }

    // Approve request

    @PutMapping("/accounting/approvalStatus/{id}")
    public ResponseEntity<?> updateApprovalStatus(@PathVariable("id") Long id) {
        Accounting updateAccount = accountingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        updateAccount.setApprovalStatus("Approved");

        Accounting updatedAccount = accountingRepository.save(updateAccount);

        return ResponseEntity.ok(updatedAccount);
    }

    // Assign engagement date

    @PutMapping("/accounting/engagementdate/{id}")
    public ResponseEntity<?> updateEngagementDate(@PathVariable("id") Long id, @RequestBody String engagementDate) {
        Accounting updateAccount = accountingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("account not found"));

        updateAccount.setEngagementDate(engagementDate);

        Accounting updatedAccount = accountingRepository.save(updateAccount);

        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/accounting/{id}")
    public ResponseEntity<Accounting> getAccountById(@PathVariable Long id) {
        Accounting accounting = accountingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
        return ResponseEntity.ok(accounting);
    }

    @DeleteMapping("/accounting/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAccount(@PathVariable("id") Long id) {
        Accounting findAccounting = accountingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found"));
        accountingRepository.delete(findAccounting);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }

    @PutMapping("/accounting/contractStatus/{id}")
public ResponseEntity<?> updateContractStatus(@PathVariable("id") Long id) {
    Accounting updateAccounting = accountingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("account not found"));

    updateAccounting.setContractStatus("Agreed and Submitted");

    Accounting updatedAccounting = accountingRepository.save(updateAccounting);

    return ResponseEntity.ok(updatedAccounting);
}

}
