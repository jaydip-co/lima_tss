/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dto;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author savaliya
 */
@XmlRootElement
public class Person extends BaseDTO{
     private String firstName;
    
    private String lastName;
    
    private LocalDate dob;
    
    private String username;
    
    private boolean isStaff;

    public Person() {
        super();
    }

    
    public Person(String firstName,
            String lastName,
            LocalDate dob,
            String username,
            boolean isStaff,
            String uuid,
            int jpaVersion) {
        super(uuid, jpaVersion);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.username = username;
        this.isStaff = isStaff;
    }

    public boolean isIsStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }
    
    
    
    

    public String getFirstName() {
        if(firstName == null){
            return username;
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
