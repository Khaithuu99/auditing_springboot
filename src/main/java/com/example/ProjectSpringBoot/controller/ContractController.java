package com.example.ProjectSpringBoot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.ProjectSpringBoot.DTO.AccountContractReqDTO;
import com.example.ProjectSpringBoot.DTO.AccountContractResDTO;
import com.example.ProjectSpringBoot.DTO.AuditContractReqDTO;
import com.example.ProjectSpringBoot.DTO.AuditContractResDTO;
import com.example.ProjectSpringBoot.DTO.ConsultancyContractReqDTO;
import com.example.ProjectSpringBoot.DTO.ConsultancyContractResDTO;
import com.example.ProjectSpringBoot.DTO.ContractResponseDTO;
import com.example.ProjectSpringBoot.model.Accounting;
import com.example.ProjectSpringBoot.model.Audit;
import com.example.ProjectSpringBoot.model.Consultancy;
import com.example.ProjectSpringBoot.model.Contract;
import com.example.ProjectSpringBoot.repository.AccountingRepository;
import com.example.ProjectSpringBoot.repository.AuditRepository;
import com.example.ProjectSpringBoot.repository.ConsultantRepository;
import com.example.ProjectSpringBoot.repository.ContractRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ContractController {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private AccountingRepository accountingRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/accounting/contract")
    public Contract accountContract(@RequestBody AccountContractReqDTO accountContractReqDTO) {
        // Find the Accounting object based on the provided accountId
        Optional<Accounting> accountingOptional = accountingRepository.findById(accountContractReqDTO.getAccountId());

        if (accountingOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Accounting not found with ID: " + accountContractReqDTO.getAccountId());
        }

        Accounting accounting = accountingOptional.get();

        // Update the contract status of the Accounting
        accounting.setContractStatus("Generated");

        Contract contract = modelMapper.map(accountContractReqDTO, Contract.class);

        contract.setAccounting(accounting);
        contract.setContractType("Accounting");

        return contractRepository.save(contract);

    }

    @PostMapping("/audit/contract")
    public Contract auditContract(@RequestBody AuditContractReqDTO auditContractReqDTO) {
        // Find the Accounting object based on the provided accountId
        Optional<Audit> auditOptional = auditRepository.findById(auditContractReqDTO.getAuditId());

        if (auditOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Audit not found with ID: " + auditContractReqDTO.getAuditId());
        }

        Audit audit = auditOptional.get();

        // Update the contract status of the Accounting
        audit.setContractStatus("Generated");

        Contract contract = modelMapper.map(auditContractReqDTO, Contract.class);

        contract.setAudit(audit);
        contract.setContractType("Audit");

        return contractRepository.save(contract);

    }

    @PostMapping("/consultancy/contract")
    public Contract consultancyContract(@RequestBody ConsultancyContractReqDTO consultancyContractReqDTO) {
        // Find the Accounting object based on the provided accountId
        Optional<Consultancy> consultancyOptional = consultantRepository
                .findById(consultancyContractReqDTO.getConsultancyId());

        if (consultancyOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Consultancy not found with ID: " + consultancyContractReqDTO.getConsultancyId());
        }

        Consultancy consultancy = consultancyOptional.get();

        // Update the contract status of the Accounting
        consultancy.setContractStatus("Generated");

        Contract contract = modelMapper.map(consultancyContractReqDTO, Contract.class);

        contract.setConsultancy(consultancy);
        contract.setContractType("Consultancy");

        return contractRepository.save(contract);

    }

    @GetMapping("/contract")
    public List<Contract> getAllContractsss() {
        return contractRepository.findAll();
    }



    @GetMapping("/contract/all")
    public List<ContractResponseDTO> getAllContracts() {
        List<ContractResponseDTO> contractsList = new ArrayList<>();
        for (Contract contract : contractRepository.findAll()) {

            if (contract.getAudit() != null) {
                AuditContractResDTO auditContractResDTO = modelMapper.map(contract, AuditContractResDTO.class);

                auditContractResDTO.setAreaAudit(contract.getAudit().getAreaAudit());
                auditContractResDTO.setFinancialYear(contract.getAudit().getFinancialYear());
                auditContractResDTO.setAuditingType(contract.getAudit().getAuditingType());
                auditContractResDTO.setAuditingStandard(contract.getAudit().getAuditingStandard());
                auditContractResDTO.setFinancialStatement(contract.getAudit().getFinancialStatement());
                auditContractResDTO.setPreviousAudit(contract.getAudit().getPreviousAudit());

                auditContractResDTO.setApprovalStatus(contract.getAudit().getApprovalStatus());
                auditContractResDTO.setContractStatus(contract.getAudit().getContractStatus());
                auditContractResDTO.setEngagementDate(contract.getAudit().getEngagementDate());

                auditContractResDTO.setFirmName(contract.getAudit().getClient().getFirmName());
                auditContractResDTO.setFirmType(contract.getAudit().getClient().getFirmType());
                auditContractResDTO.setFirmEmail(contract.getAudit().getClient().getFirmEmail());
                auditContractResDTO.setFirmAddress(contract.getAudit().getClient().getFirmAddress());
                auditContractResDTO.setFirmPhone(contract.getAudit().getClient().getFirmPhone());

                contractsList.add(auditContractResDTO);
            }

            if (contract.getConsultancy() != null) {
                ConsultancyContractResDTO consultancyContractResDTO = modelMapper.map(contract,
                        ConsultancyContractResDTO.class);

                consultancyContractResDTO.setConsultancyId(contract.getConsultancy().getConsultancyId());
                consultancyContractResDTO.setFinancialYear(contract.getConsultancy().getFinancialYear());
                consultancyContractResDTO.setConsultancyType(contract.getConsultancy().getConsultancyType());
                consultancyContractResDTO.setConsultancyScope(contract.getConsultancy().getConsultancyScope());
                consultancyContractResDTO.setConsultancyObjective(contract.getConsultancy().getConsultancyObjective());
                consultancyContractResDTO.setExpert(contract.getConsultancy().getExpert());
                consultancyContractResDTO.setBudget(contract.getConsultancy().getBudget());
                consultancyContractResDTO.setFeeStructure(contract.getConsultancy().getFeeStructure());

                consultancyContractResDTO.setApprovalStatus(contract.getConsultancy().getApprovalStatus());
                consultancyContractResDTO.setContractStatus(contract.getConsultancy().getContractStatus());
                consultancyContractResDTO.setEngagementDate(contract.getConsultancy().getEngagementDate());

                consultancyContractResDTO.setFirmName(contract.getConsultancy().getClient().getFirmName());
                consultancyContractResDTO.setFirmType(contract.getConsultancy().getClient().getFirmType());
                consultancyContractResDTO.setFirmEmail(contract.getConsultancy().getClient().getFirmEmail());
                consultancyContractResDTO.setFirmAddress(contract.getConsultancy().getClient().getFirmAddress());
                consultancyContractResDTO.setFirmPhone(contract.getConsultancy().getClient().getFirmPhone());

                contractsList.add(consultancyContractResDTO);
            }

            if (contract.getAccounting() != null) {
                AccountContractResDTO accountContractResDTO = modelMapper.map(contract, AccountContractResDTO.class);

                accountContractResDTO.setAccountId(contract.getAccounting().getAccountId());
                accountContractResDTO.setStartingDate(contract.getAccounting().getStartingDate());
                accountContractResDTO.setEndingDate(contract.getAccounting().getEndingDate());
                accountContractResDTO.setAccountingPeriod(contract.getAccounting().getAccountingPeriod());
                accountContractResDTO.setFinancialStatements(contract.getAccounting().getFinancialStatements());
                accountContractResDTO.setNeedBalanceSheet(contract.getAccounting().getNeedBalanceSheet());
                accountContractResDTO.setNeedIncomeStatement(contract.getAccounting().getNeedIncomeStatement());
                accountContractResDTO.setNeedcashFlow(contract.getAccounting().getNeedcashFlow());
                accountContractResDTO.setNeedChangeInEquity(contract.getAccounting().getNeedChangeInEquity());
                accountContractResDTO.setAccountingStandard(contract.getAccounting().getAccountingStandard());
                accountContractResDTO.setBusinessNature(contract.getAccounting().getBusinessNature());
                accountContractResDTO
                        .setPreviousFinancialStatement(contract.getAccounting().getPreviousFinancialStatement());

                accountContractResDTO.setApprovalStatus(contract.getAccounting().getApprovalStatus());
                accountContractResDTO.setContractStatus(contract.getAccounting().getContractStatus());
                accountContractResDTO.setEngagementDate(contract.getAccounting().getEngagementDate());

                accountContractResDTO.setFirmName(contract.getAccounting().getClient().getFirmName());
                accountContractResDTO.setFirmType(contract.getAccounting().getClient().getFirmType());
                accountContractResDTO.setFirmEmail(contract.getAccounting().getClient().getFirmEmail());
                accountContractResDTO.setFirmAddress(contract.getAccounting().getClient().getFirmAddress());
                accountContractResDTO.setFirmPhone(contract.getAccounting().getClient().getFirmPhone());

                contractsList.add(accountContractResDTO);
            }
        }
        return contractsList;
    }



    @GetMapping("/contract/client/{id}")
    public List<ContractResponseDTO> getContractByClientId(@PathVariable long id) {
        List<ContractResponseDTO> contractsList = new ArrayList<>();
        for (Contract contract : contractRepository.findAllByClientId(id)) {

            if (contract.getAudit() != null) {
                AuditContractResDTO auditContractResDTO = modelMapper.map(contract, AuditContractResDTO.class);

                auditContractResDTO.setAreaAudit(contract.getAudit().getAreaAudit());
                auditContractResDTO.setFinancialYear(contract.getAudit().getFinancialYear());
                auditContractResDTO.setAuditingType(contract.getAudit().getAuditingType());
                auditContractResDTO.setAuditingStandard(contract.getAudit().getAuditingStandard());
                auditContractResDTO.setFinancialStatement(contract.getAudit().getFinancialStatement());
                auditContractResDTO.setPreviousAudit(contract.getAudit().getPreviousAudit());

                auditContractResDTO.setApprovalStatus(contract.getAudit().getApprovalStatus());
                auditContractResDTO.setContractStatus(contract.getAudit().getContractStatus());
                auditContractResDTO.setEngagementDate(contract.getAudit().getEngagementDate());

                auditContractResDTO.setFirmName(contract.getAudit().getClient().getFirmName());
                auditContractResDTO.setFirmType(contract.getAudit().getClient().getFirmType());
                auditContractResDTO.setFirmEmail(contract.getAudit().getClient().getFirmEmail());
                auditContractResDTO.setFirmAddress(contract.getAudit().getClient().getFirmAddress());
                auditContractResDTO.setFirmPhone(contract.getAudit().getClient().getFirmPhone());

                contractsList.add(auditContractResDTO);
            }

            if (contract.getConsultancy() != null) {
                ConsultancyContractResDTO consultancyContractResDTO = modelMapper.map(contract,
                        ConsultancyContractResDTO.class);

                consultancyContractResDTO.setConsultancyId(contract.getConsultancy().getConsultancyId());
                consultancyContractResDTO.setFinancialYear(contract.getConsultancy().getFinancialYear());
                consultancyContractResDTO.setConsultancyType(contract.getConsultancy().getConsultancyType());
                consultancyContractResDTO.setConsultancyScope(contract.getConsultancy().getConsultancyScope());
                consultancyContractResDTO.setConsultancyObjective(contract.getConsultancy().getConsultancyObjective());
                consultancyContractResDTO.setExpert(contract.getConsultancy().getExpert());
                consultancyContractResDTO.setBudget(contract.getConsultancy().getBudget());
                consultancyContractResDTO.setFeeStructure(contract.getConsultancy().getFeeStructure());

                consultancyContractResDTO.setApprovalStatus(contract.getConsultancy().getApprovalStatus());
                consultancyContractResDTO.setContractStatus(contract.getConsultancy().getContractStatus());
                consultancyContractResDTO.setEngagementDate(contract.getConsultancy().getEngagementDate());

                consultancyContractResDTO.setFirmName(contract.getConsultancy().getClient().getFirmName());
                consultancyContractResDTO.setFirmType(contract.getConsultancy().getClient().getFirmType());
                consultancyContractResDTO.setFirmEmail(contract.getConsultancy().getClient().getFirmEmail());
                consultancyContractResDTO.setFirmAddress(contract.getConsultancy().getClient().getFirmAddress());
                consultancyContractResDTO.setFirmPhone(contract.getConsultancy().getClient().getFirmPhone());

                contractsList.add(consultancyContractResDTO);
            }

            if (contract.getAccounting() != null) {
                AccountContractResDTO accountContractResDTO = modelMapper.map(contract, AccountContractResDTO.class);

                accountContractResDTO.setAccountId(contract.getAccounting().getAccountId());
                accountContractResDTO.setStartingDate(contract.getAccounting().getStartingDate());
                accountContractResDTO.setEndingDate(contract.getAccounting().getEndingDate());
                accountContractResDTO.setAccountingPeriod(contract.getAccounting().getAccountingPeriod());
                accountContractResDTO.setFinancialStatements(contract.getAccounting().getFinancialStatements());
                accountContractResDTO.setNeedBalanceSheet(contract.getAccounting().getNeedBalanceSheet());
                accountContractResDTO.setNeedIncomeStatement(contract.getAccounting().getNeedIncomeStatement());
                accountContractResDTO.setNeedcashFlow(contract.getAccounting().getNeedcashFlow());
                accountContractResDTO.setNeedChangeInEquity(contract.getAccounting().getNeedChangeInEquity());
                accountContractResDTO.setAccountingStandard(contract.getAccounting().getAccountingStandard());
                accountContractResDTO.setBusinessNature(contract.getAccounting().getBusinessNature());
                accountContractResDTO
                        .setPreviousFinancialStatement(contract.getAccounting().getPreviousFinancialStatement());

                accountContractResDTO.setApprovalStatus(contract.getAccounting().getApprovalStatus());
                accountContractResDTO.setContractStatus(contract.getAccounting().getContractStatus());
                accountContractResDTO.setEngagementDate(contract.getAccounting().getEngagementDate());

                accountContractResDTO.setFirmName(contract.getAccounting().getClient().getFirmName());
                accountContractResDTO.setFirmType(contract.getAccounting().getClient().getFirmType());
                accountContractResDTO.setFirmEmail(contract.getAccounting().getClient().getFirmEmail());
                accountContractResDTO.setFirmAddress(contract.getAccounting().getClient().getFirmAddress());
                accountContractResDTO.setFirmPhone(contract.getAccounting().getClient().getFirmPhone());

                contractsList.add(accountContractResDTO);
            }
        }
        return contractsList;
    }


    // get by either audit, or account, or cinsultancy Id
    @GetMapping("/contract/client/either/{id}")
    public ContractResponseDTO getContractByEitherId(@PathVariable long id) {
        Contract contract = contractRepository.findAllByEitherId(id);
        if (contract != null) {
            if (contract.getAudit() != null) {
                AuditContractResDTO auditContractResDTO = modelMapper.map(contract, AuditContractResDTO.class);
                auditContractResDTO.setAreaAudit(contract.getAudit().getAreaAudit());
                auditContractResDTO.setFinancialYear(contract.getAudit().getFinancialYear());
                auditContractResDTO.setAuditingType(contract.getAudit().getAuditingType());
                auditContractResDTO.setAuditingStandard(contract.getAudit().getAuditingStandard());
                auditContractResDTO.setFinancialStatement(contract.getAudit().getFinancialStatement());
                auditContractResDTO.setPreviousAudit(contract.getAudit().getPreviousAudit());
                auditContractResDTO.setApprovalStatus(contract.getAudit().getApprovalStatus());
                auditContractResDTO.setContractStatus(contract.getAudit().getContractStatus());
                auditContractResDTO.setEngagementDate(contract.getAudit().getEngagementDate());
                auditContractResDTO.setFirmName(contract.getAudit().getClient().getFirmName());
                auditContractResDTO.setFirmType(contract.getAudit().getClient().getFirmType());
                auditContractResDTO.setFirmEmail(contract.getAudit().getClient().getFirmEmail());
                auditContractResDTO.setFirmAddress(contract.getAudit().getClient().getFirmAddress());
                auditContractResDTO.setFirmPhone(contract.getAudit().getClient().getFirmPhone());
                return auditContractResDTO;
            } else if (contract.getConsultancy() != null) {
                ConsultancyContractResDTO consultancyContractResDTO = modelMapper.map(contract, ConsultancyContractResDTO.class);
                consultancyContractResDTO.setConsultancyId(contract.getConsultancy().getConsultancyId());
                consultancyContractResDTO.setFinancialYear(contract.getConsultancy().getFinancialYear());
                consultancyContractResDTO.setConsultancyType(contract.getConsultancy().getConsultancyType());
                consultancyContractResDTO.setConsultancyScope(contract.getConsultancy().getConsultancyScope());
                consultancyContractResDTO.setConsultancyObjective(contract.getConsultancy().getConsultancyObjective());
                consultancyContractResDTO.setExpert(contract.getConsultancy().getExpert());
                consultancyContractResDTO.setBudget(contract.getConsultancy().getBudget());
                consultancyContractResDTO.setFeeStructure(contract.getConsultancy().getFeeStructure());
                consultancyContractResDTO.setApprovalStatus(contract.getConsultancy().getApprovalStatus());
                consultancyContractResDTO.setContractStatus(contract.getConsultancy().getContractStatus());
                consultancyContractResDTO.setEngagementDate(contract.getConsultancy().getEngagementDate());
                consultancyContractResDTO.setFirmName(contract.getConsultancy().getClient().getFirmName());
                consultancyContractResDTO.setFirmType(contract.getConsultancy().getClient().getFirmType());
                consultancyContractResDTO.setFirmEmail(contract.getConsultancy().getClient().getFirmEmail());
                consultancyContractResDTO.setFirmAddress(contract.getConsultancy().getClient().getFirmAddress());
                consultancyContractResDTO.setFirmPhone(contract.getConsultancy().getClient().getFirmPhone());
                return consultancyContractResDTO;
            } else if (contract.getAccounting() != null) {
                AccountContractResDTO accountContractResDTO = modelMapper.map(contract, AccountContractResDTO.class);
                accountContractResDTO.setAccountId(contract.getAccounting().getAccountId());
                accountContractResDTO.setStartingDate(contract.getAccounting().getStartingDate());
                accountContractResDTO.setEndingDate(contract.getAccounting().getEndingDate());
                accountContractResDTO.setAccountingPeriod(contract.getAccounting().getAccountingPeriod());
                accountContractResDTO.setFinancialStatements(contract.getAccounting().getFinancialStatements());
                accountContractResDTO.setNeedBalanceSheet(contract.getAccounting().getNeedBalanceSheet());
                accountContractResDTO.setNeedIncomeStatement(contract.getAccounting().getNeedIncomeStatement());
                accountContractResDTO.setNeedcashFlow(contract.getAccounting().getNeedcashFlow());
                accountContractResDTO.setNeedChangeInEquity(contract.getAccounting().getNeedChangeInEquity());
                accountContractResDTO.setAccountingStandard(contract.getAccounting().getAccountingStandard());
                accountContractResDTO.setBusinessNature(contract.getAccounting().getBusinessNature());
                accountContractResDTO.setPreviousFinancialStatement(contract.getAccounting().getPreviousFinancialStatement());
                accountContractResDTO.setApprovalStatus(contract.getAccounting().getApprovalStatus());
                accountContractResDTO.setContractStatus(contract.getAccounting().getContractStatus());
                accountContractResDTO.setEngagementDate(contract.getAccounting().getEngagementDate());
                accountContractResDTO.setFirmName(contract.getAccounting().getClient().getFirmName());
                accountContractResDTO.setFirmType(contract.getAccounting().getClient().getFirmType());
                accountContractResDTO.setFirmEmail(contract.getAccounting().getClient().getFirmEmail());
                accountContractResDTO.setFirmAddress(contract.getAccounting().getClient().getFirmAddress());
                accountContractResDTO.setFirmPhone(contract.getAccounting().getClient().getFirmPhone());
                return accountContractResDTO;
            }
            // If the contract doesn't match any type, you may throw an exception or handle it accordingly.
        }
        // Return null or throw an exception if the contract is not found.
        return null;
    }

    




    @GetMapping("/contract/audit/{id}")
    public List<ContractResponseDTO> getAllAuditContracts(@PathVariable long id) {
        List<ContractResponseDTO> contractsList = new ArrayList<>();
        for (Contract contract : contractRepository.findAllByClientId(id)) {

            if (contract.getAudit() != null) {
                AuditContractResDTO auditContractResDTO = modelMapper.map(contract, AuditContractResDTO.class);

                auditContractResDTO.setAreaAudit(contract.getAudit().getAreaAudit());
                auditContractResDTO.setFinancialYear(contract.getAudit().getFinancialYear());
                auditContractResDTO.setAuditingType(contract.getAudit().getAuditingType());
                auditContractResDTO.setAuditingStandard(contract.getAudit().getAuditingStandard());
                auditContractResDTO.setFinancialStatement(contract.getAudit().getFinancialStatement());
                auditContractResDTO.setPreviousAudit(contract.getAudit().getPreviousAudit());

                auditContractResDTO.setApprovalStatus(contract.getAudit().getApprovalStatus());
                auditContractResDTO.setContractStatus(contract.getAudit().getContractStatus());
                auditContractResDTO.setEngagementDate(contract.getAudit().getEngagementDate());

                auditContractResDTO.setFirmName(contract.getAudit().getClient().getFirmName());
                auditContractResDTO.setFirmType(contract.getAudit().getClient().getFirmType());
                auditContractResDTO.setFirmEmail(contract.getAudit().getClient().getFirmEmail());
                auditContractResDTO.setFirmAddress(contract.getAudit().getClient().getFirmAddress());
                auditContractResDTO.setFirmPhone(contract.getAudit().getClient().getFirmPhone());

                contractsList.add(auditContractResDTO);
            }

            
        }
        return contractsList;
    }


    @GetMapping("/contract/accounting/{id}")
    public List<ContractResponseDTO> getAllAccountingContracts(@PathVariable long id) {
        List<ContractResponseDTO> contractsList = new ArrayList<>();
        for (Contract contract : contractRepository.findAllByClientId(id)) {

            
            if (contract.getAccounting() != null) {
                AccountContractResDTO accountContractResDTO = modelMapper.map(contract, AccountContractResDTO.class);

                accountContractResDTO.setAccountId(contract.getAccounting().getAccountId());
                accountContractResDTO.setStartingDate(contract.getAccounting().getStartingDate());
                accountContractResDTO.setEndingDate(contract.getAccounting().getEndingDate());
                accountContractResDTO.setAccountingPeriod(contract.getAccounting().getAccountingPeriod());
                accountContractResDTO.setFinancialStatements(contract.getAccounting().getFinancialStatements());
                accountContractResDTO.setNeedBalanceSheet(contract.getAccounting().getNeedBalanceSheet());
                accountContractResDTO.setNeedIncomeStatement(contract.getAccounting().getNeedIncomeStatement());
                accountContractResDTO.setNeedcashFlow(contract.getAccounting().getNeedcashFlow());
                accountContractResDTO.setNeedChangeInEquity(contract.getAccounting().getNeedChangeInEquity());
                accountContractResDTO.setAccountingStandard(contract.getAccounting().getAccountingStandard());
                accountContractResDTO.setBusinessNature(contract.getAccounting().getBusinessNature());
                accountContractResDTO
                        .setPreviousFinancialStatement(contract.getAccounting().getPreviousFinancialStatement());

                accountContractResDTO.setApprovalStatus(contract.getAccounting().getApprovalStatus());
                accountContractResDTO.setContractStatus(contract.getAccounting().getContractStatus());
                accountContractResDTO.setEngagementDate(contract.getAccounting().getEngagementDate());

                accountContractResDTO.setFirmName(contract.getAccounting().getClient().getFirmName());
                accountContractResDTO.setFirmType(contract.getAccounting().getClient().getFirmType());
                accountContractResDTO.setFirmEmail(contract.getAccounting().getClient().getFirmEmail());
                accountContractResDTO.setFirmAddress(contract.getAccounting().getClient().getFirmAddress());
                accountContractResDTO.setFirmPhone(contract.getAccounting().getClient().getFirmPhone());

                contractsList.add(accountContractResDTO);
            }
        }
        return contractsList;
    }


    @GetMapping("/contract/consultancy/{id}")
    public List<ContractResponseDTO> getAllConsultancyContracts(@PathVariable long id) {
        List<ContractResponseDTO> contractsList = new ArrayList<>();
        for (Contract contract : contractRepository.findAllByClientId(id)) {

            
            if (contract.getConsultancy() != null) {
                ConsultancyContractResDTO consultancyContractResDTO = modelMapper.map(contract,
                        ConsultancyContractResDTO.class);

                consultancyContractResDTO.setConsultancyId(contract.getConsultancy().getConsultancyId());
                consultancyContractResDTO.setFinancialYear(contract.getConsultancy().getFinancialYear());
                consultancyContractResDTO.setConsultancyType(contract.getConsultancy().getConsultancyType());
                consultancyContractResDTO.setConsultancyScope(contract.getConsultancy().getConsultancyScope());
                consultancyContractResDTO.setConsultancyObjective(contract.getConsultancy().getConsultancyObjective());
                consultancyContractResDTO.setExpert(contract.getConsultancy().getExpert());
                consultancyContractResDTO.setBudget(contract.getConsultancy().getBudget());
                consultancyContractResDTO.setFeeStructure(contract.getConsultancy().getFeeStructure());

                consultancyContractResDTO.setApprovalStatus(contract.getConsultancy().getApprovalStatus());
                consultancyContractResDTO.setContractStatus(contract.getConsultancy().getContractStatus());
                consultancyContractResDTO.setEngagementDate(contract.getConsultancy().getEngagementDate());

                consultancyContractResDTO.setFirmName(contract.getConsultancy().getClient().getFirmName());
                consultancyContractResDTO.setFirmType(contract.getConsultancy().getClient().getFirmType());
                consultancyContractResDTO.setFirmEmail(contract.getConsultancy().getClient().getFirmEmail());
                consultancyContractResDTO.setFirmAddress(contract.getConsultancy().getClient().getFirmAddress());
                consultancyContractResDTO.setFirmPhone(contract.getConsultancy().getClient().getFirmPhone());

                contractsList.add(consultancyContractResDTO);
            }

            
        }
        return contractsList;
    }






        

}
