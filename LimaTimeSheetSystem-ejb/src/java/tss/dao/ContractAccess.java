/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import tss.UserRoles;
import tss.dto.Contract;
import tss.entities.ContractEntity;
import tss.entities.ContractUserRole;
import tss.entities.PersonEntity;

/**
 *
 * @author savaliya
 */
@LocalBean
@Stateless
public class ContractAccess extends BaseAccess{
    
    
    
    public String save(ContractEntity c){
        em.merge(c);
        return c.getUuid();
    }
    
    public String createNewContract(Contract c,PersonEntity employee, 
            Set<PersonEntity> secretary,
            Set<PersonEntity> assitant,
            Set<PersonEntity> supervisor){
        ContractEntity ce = new ContractEntity(true);
        ce.setContractName(c.getName());
        ce.setStartDate(c.getStartDate());
        ce.setEndDate(c.getEndDate());
        ce.setFrequency(c.getFrequency());
        ce.setVacationDaysPerYear(c.getVacationDaysPerYear());
        ce.setWorkingDaysPerWeek(c.getWorkingDaysPerWeek());
        ce.setHoursPerWeek(c.getHoursPerWeek());
        ce.calculateVacationHours();
        
        
        Set<ContractUserRole> roles = new HashSet<>();
        
        ContractUserRole employeRole = new ContractUserRole(true);
        
        employeRole.setContract(ce);
        employeRole.setPerson(employee);
        employeRole.setRole(UserRoles.EMPLOYEE);
        employee.getContractUserRoles().add(employeRole);
        roles.add(employeRole);
        
        
        /////// secretary ///////
        secretary.forEach(e -> {
            ContractUserRole userRole = new ContractUserRole(true);
            userRole.setContract(ce);
            userRole.setPerson(e);
            userRole.setRole(UserRoles.SECRETARY);
            e.getContractUserRoles().add(userRole);
            roles.add(userRole);
        });
        
        /// assistant ///
        assitant.forEach(e -> {
            ContractUserRole userRole = new ContractUserRole(true);
            userRole.setContract(ce);
            userRole.setPerson(e);
            userRole.setRole(UserRoles.ASSISTANT);
            e.getContractUserRoles().add(userRole);
            roles.add(userRole);
        });
        supervisor.forEach(e -> {
            ContractUserRole userRole = new ContractUserRole(true);
            userRole.setContract(ce);
            userRole.setPerson(e);
            userRole.setRole(UserRoles.SUPERVISOR);
            e.getContractUserRoles().add(userRole);
            roles.add(userRole);
        });
        
        ce.setContractUserRoles(roles);
       
        
        
        
        em.persist(ce);
        return ce.getUuid();
    }
    
    public List<ContractEntity> getAllContract(){
        return em.createNamedQuery("getAllContract").getResultList();
        
    }
    
    public ContractEntity getContractEntityFromUUID(String uuid){
        try{
        ContractEntity ce = em.createNamedQuery("getContractByUuid",
                    ContractEntity.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        return ce;
        }
        catch(NoResultException ex){
        return null;
        }
    }
    
    public boolean deleteContract(String uuid){
        ContractEntity ce = getContractEntityFromUUID(uuid);
        if(ce == null){
        return false;
        }
        em.remove(ce);
        return true;
    }
}
