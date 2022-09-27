/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import tss.dto.Person;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@ViewScoped
@Named
public class UserDataBean implements Serializable{
    @EJB
    private ContractRemote cr;
    
    private Person currentUser;
    
    private Date dob;
    
    private String year,month,day;
    
    String errorString = "";
    
    public boolean isError(){
        return !"".equals(errorString);
    }
    public ContractRemote getCr() {
        return cr;
    }

    public void setCr(ContractRemote cr) {
        this.cr = cr;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getYear() {
        
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
    
    
    
    
    
    

    public Person getCurrentUser() {
        if(currentUser == null){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            currentUser = cr.getUserDataByUserName(
                    context.getUserPrincipal().getName());
            day = currentUser.getDob().getDayOfMonth()+"";
            month = currentUser.getDob().getMonthValue()+"";
            year = currentUser.getDob().getYear()+"";
            
        }
        return currentUser;
    }

    public void setCurrentUser(Person currentUser) {
        this.currentUser = currentUser;
    }
    
    public void storeData(){
        LocalDate dob;
        try{
            errorString = "";
         int y = Integer.parseInt(year);
            int m = Integer.parseInt(month);
            int d = Integer.parseInt(day);
         dob = LocalDate.of(y, m, d);
        }
        catch(Exception e){
           
            
             errorString = "Please Enter Valid Date";
               return;
        }
        if(dob == null || dob.equals(LocalDate.now()) || dob.isAfter(LocalDate.now())){
            errorString  = "Invalid Dob";
        } 
        currentUser.setDob(dob);
       cr.storeUserData(currentUser);
       currentUser = null;
    }
    
    
    
}
