/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import tss.entities.ContractEntity;
import tss.entities.ContractUserRole;
import tss.entities.PersonEntity;

/**
 *
 * @author savaliya
 */
@Stateless
@LocalBean
public class ContractUserRoleAccess extends BaseAccess {

    public List<ContractUserRole> getAllContractUserRoleWith(PersonEntity p) {
        return em.createNamedQuery("getContractWithRole", ContractUserRole.class)
                .setParameter("person", p).getResultList();
    }

    public List<ContractUserRole> getContractWithRole(PersonEntity p, String role) {
        return em.createNamedQuery("getContractWithSpecificRole", ContractUserRole.class)
                .setParameter("person", p)
                .setParameter("role", role)
                .getResultList();
    }

    public ContractUserRole getEntityWithUuid(String uuid) {
        return em.createNamedQuery("getContractRoleWithUuid", ContractUserRole.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }
    
    public ContractUserRole createNewContractUserRole(
            ContractEntity contract,
            PersonEntity person,
            String role
            ){
        ContractUserRole c = new ContractUserRole(Boolean.TRUE);
        c.setPerson(person);
        person.getContractUserRoles().add(c);
        c.setContract(contract);
        contract.getContractUserRoles().add(c);
        c.setRole(role);
        em.persist(c);
        return c;
    
    }

    public void remove(String er) {
        ContractUserRole userRole = getEntityWithUuid(er);
        userRole.getContract().getContractUserRoles().remove(userRole);
        userRole.getPerson().getContractUserRoles().remove(userRole);
        userRole.setContract(null);
        userRole.setContract(null);
        em.remove(userRole);
    }

    public void save(ContractUserRole er) {
        em.persist(er);
    }

}
