/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import tss.dto.Contract;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@RequestScoped
@Named
public class DashBoard {

    @EJB
    ContractRemote cr;

    public List<Contract> getContracts() {
        List<Contract> contracts;
        contracts = cr.getAllContract();
        return contracts;
    }

    public boolean isContractAwailable() {
        return !getContracts().isEmpty();
    }

    public List<Contract> getContractWithEmployee() {

        List<Contract> contracts;
        String[] roles = {UserRoles.EMPLOYEE};
        contracts = cr.getContractsWithRole(roles);
        return contracts;
    }

    public List<Contract> getContractWithManager() {
        List<Contract> contracts;
        String[] roles = {UserRoles.SUPERVISOR, UserRoles.SECRETARY, UserRoles.ASSISTANT};
        contracts = cr.getContractsWithRole(roles);
        return contracts;
    }
    public List<Contract> getArchievedContract(){
        return cr.getAllArchievedContract();
    }
}
