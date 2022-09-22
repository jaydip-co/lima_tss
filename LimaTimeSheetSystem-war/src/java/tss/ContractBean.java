/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import tss.dto.Contract;
import tss.dto.Person;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@RequestScoped
@Named
public class ContractBean {
    @EJB
    ContractRemote cr;
    private static final Logger log = Logger.getLogger(ContractBean.class.getName());
    
   
    public void setContract(){
//        log.log(Level.SEVERE,"thay 6");
//        LocalDate date = LocalDate.now();
//        cr.storeContract(new Contract()); 
    }
    
    public String getUserName(){
        String  name;
        try{
        name = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName(); 
        }
        catch(Exception e){
            name = e.getMessage();
        }
       return name;
    }
    public String getUsetRole(){
        String[] roles = {"ADMINISTRATOR","SECRETARY","ASSISTANT","SUPERVISOR","EMPLOYEE","GEUST"};
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
    
    public List<Contract> getAllContract(){
    return cr.getAllContract();
    }
    
    public void logout(){
         invalidateSession();
        FacesContext.getCurrentInstance()
                .responseComplete();
    }
      public void invalidateSession() {
//        LOG.log(Level.INFO, "invalidateSession()");
        Principal p = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getUserPrincipal();
        if (p != null) {
//            LOG.log(Level.INFO, "Contacts: LOGOUT user {0}", p.getName());
        }
//        currentUser = null;
//        oldPrincipal = null;
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
    }
      
      
    public void addDemoData(){
        
        
        Person person = new Person();
        
        person.setFirstName("student1");
        person.setUsername("student1@tss.com");
        cr.storeUser(person, false);
        
        person.setFirstName("student2");
        person.setUsername("student2@tss.com");
        cr.storeUser(person, false);
        
        person.setFirstName("staff_member1");
        person.setUsername("staff_member1@tss.com");
        cr.storeUser(person, true);
        
        person.setFirstName("staff_member2");
        person.setUsername("staff_member2@tss.com");
        cr.storeUser(person, true);
        
        person.setFirstName("staff_member3");
        person.setUsername("staff_member3@tss.com");
        cr.storeUser(person, true);
        
    
//        String[] employeeRoles = {"employee"};
//        person.setFirstName("employee1");
//        person.setUsername("employee1@tss.com");
//        cr.storeUser(person,employeeRoles);
//        person.setFirstName("employee2");
//        person.setUsername("employee2@tss.com");
//        cr.storeUser(person,employeeRoles);
//        
//        String[] supervisorRole = {"supervisor"};
//        person.setFirstName("supervisor1");
//        person.setUsername("supervisor1@tss.com");
//        cr.storeUser(person,supervisorRole);
//        person.setFirstName("supervisor2");
//        person.setUsername("supervisor2@tss.com");
//        cr.storeUser(person,supervisorRole);
//        
//        String[] roles = {"secretary"};
//        person.setFirstName("secretary1");
//        person.setUsername("secretary1@tss.com");
//        cr.storeUser(person,roles);
//        person.setFirstName("secretary2");
//        person.setUsername("secretary2@tss.com");
//        cr.storeUser(person,roles);
//        
//        String[] assistantRole = {"assistant"};
//        person.setFirstName("assistant1");
//        person.setUsername("assistant1@tss.com");
//        cr.storeUser(person,assistantRole);
//        person.setFirstName("assistant2");
//        person.setUsername("assistant2@tss.com");
//        cr.storeUser(person,assistantRole);
//        
//        
    }
    
    public String getPersonWithUserName(){
        try{
           Set<Person> ps = cr.getUsersWithRole("supervisor");
            StringBuilder sb = new StringBuilder();
            for(Person e:ps){
            sb.append(e.getFirstName());
            sb.append("///");
            sb.append("///");
            sb.append(e.getUuid());
            sb.append(":=--");
            }
            sb.append(ps.size());
    return sb.toString();
        }
        catch(Exception e){
        return e.getMessage();
        }
        
        
    }
    
    
}
