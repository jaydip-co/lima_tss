/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import tss.entities.UserRoleEntity;

/**
 *
 * @author savaliya
 */
@LocalBean
@Stateless
public class UserRoleAccess extends BaseAccess{
    
    
    public UserRoleEntity getUserRoleByName(String role){
        try{
            return em.createNamedQuery("getRoleByname",UserRoleEntity.class)
                    .setParameter("rolename", role).getSingleResult();
        
        }
        catch(NoResultException e){
            return null;
        }
    }
    
    public UserRoleEntity storeRole(String role){
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRoleName(role);
        em.persist(userRole);
        return userRole;
        
    }
  

   
    
}
