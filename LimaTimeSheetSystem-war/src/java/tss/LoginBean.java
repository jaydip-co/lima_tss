/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import tss.dto.Person;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@SessionScoped
@Named
public class LoginBean implements Serializable{
    private static final long serialVersionUID = 7755587737462676166L;
    
    @EJB
    private ContractRemote cr;
    
    Person currentUser;
    
    public String getUser(){
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        if(p == null){
            return "";
        }
         List userRoles = new ArrayList(UserRoles.roles.length);
        
            userRoles.clear();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            for(String s : UserRoles.roles){
                if(ec.isUserInRole(s)){
                    userRoles.add(s);
                }
            }
        currentUser = cr.getUserDataByUserName(p.getName(),
                    userRoles,ec.isUserInRole("STAFFMEMBER"));
          if(currentUser == null){
            return "";
        }
        return currentUser.getFirstName();
    }
    
     public String getUsetRole(){
        String[] roles = {
            "ADMINISTRATOR",
            "STUDENT",
            "STAFFMEMBER",
            "SECRETARY",
            "ASSISTANT",
            "SUPERVISOR",
            "EMPLOYEE",
            "GEUST"};
        String  name = "";
        List userRoles = new ArrayList(roles.length);
        try{
            userRoles.clear();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            for(String s : roles){
                if(ec.isUserInRole(s)){
                    userRoles.add(s);
                }
            }
        }
        catch(Exception e){
            name = e.getMessage();
        }
       return userRoles.toString();
    }
     
    public boolean isLoggedIn(){
        return currentUser != null;
    }
     
     public boolean isSecretary(){
       return  hasRole(UserRoles.SECRETARY);
     }
     public boolean isSupervisor(){
       return  hasRole(UserRoles.SUPERVISOR);
     }
     public boolean isEmployee(){
       return  hasRole(UserRoles.EMPLOYEE);
     }
     
     public boolean isStaff(){
        return currentUser.isIsStaff();
     }
     
     public boolean isAssistant(){
       return  hasRole(UserRoles.ASSISTANT);
     }
     
      public void invalidateSession() {
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        currentUser = null;
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
    }
     public void logout(){
         invalidateSession();
        FacesContext.getCurrentInstance()
                .responseComplete();
    }
     
     private boolean hasRole(String role){
         ExternalContext p = FacesContext.getCurrentInstance()
                 .getExternalContext();
                 
                
         return p.isUserInRole(role);
     }
     
    
}
