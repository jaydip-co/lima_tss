/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dao;

import java.util.List;
import java.util.Set;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import tss.entities.PersonEntity;
import tss.entities.UserRoleEntity;

/**
 *
 * @author savaliya
 */
@LocalBean
@Stateless
public class PersonAccess  extends BaseAccess{
    
    
    public PersonEntity getUserDataFromUsername(String name){
        try{
            return  em.createNamedQuery("getUserByUserName",PersonEntity.class)
               .setParameter("username", name).getSingleResult();
        }
        catch(NoResultException ex){
        return null;
        }
    }
    public PersonEntity getUserDataFromUuid(String uuid){
        try{
            return  em.createNamedQuery("getUserByUuid",PersonEntity.class)
               .setParameter("uuid", uuid).getSingleResult();
        }
        catch(NoResultException ex){
        return null;
        }
    }
    public PersonEntity storeUser(String username,
            String firsname,boolean isStaff){
        PersonEntity entity = new PersonEntity(true);
        entity.setFirstName(firsname);
        entity.setUsername(username);
       
        entity.setIsStaffMember(isStaff);
        em.persist(entity);
        return entity;
    }
    
    public PersonEntity storeUserwithName(String name,Set<UserRoleEntity> entities){
        PersonEntity entity = new PersonEntity(true);
        entity.setFirstName(name);
        
        entity.setUserRoles(entities);
        entities.forEach((a)->{
        a.getPersons().add(entity);});
        
        em.persist(entity);
        return entity;
    }
    
    public  List<PersonEntity> getPersonsByRole(String role){
       List<PersonEntity> ps = em.createNamedQuery("getPersonsByRole",PersonEntity.class)
               .setParameter("role", role).getResultList();
       return ps;
    }
    
    public List<PersonEntity> getAllUser(boolean isStaff){
        return em.createNamedQuery("getAllUser", PersonEntity.class)
                .setParameter("staffMember", isStaff)
                .getResultList();
               
    }
    public List<PersonEntity> getAllUser(){
        return em.createNamedQuery("getAll", PersonEntity.class)
                .getResultList();
               
    }
    
    
    
}
