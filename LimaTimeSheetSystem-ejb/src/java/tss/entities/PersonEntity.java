/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author savaliya
 */
@NamedQueries({
    @NamedQuery(
            name = "getUserByUserName",
            query = "SELECT u FROM person u WHERE u.username = :username"
    ),
    @NamedQuery(
            name = "getUserByUuid",
            query = "SELECT u FROM person u WHERE u.uuid = :uuid"
    ),
    @NamedQuery(
            name = "getAll",
            query = "SELECT u FROM person u"
    ),
    @NamedQuery(
            name = "getAllUser",
            query = "SELECT u FROM person u WHERE "
            + "u.isStaffMember =:staffMember"
    ),
    @NamedQuery(
            name = "getPersonsByRole",
            query = "SELECT c FROM person c"
    )
})
@Entity(name = "person")
public class PersonEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    @Column
    private boolean isStaffMember;

    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "person",
            fetch = FetchType.EAGER)
    private Set<ContractUserRole> contractUserRoles;

    @Column
    private String emailID;
    
    private boolean consent;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "person_role",
            joinColumns = {
                @JoinColumn(name = "person_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "role_id")}
    )
    private Set<UserRoleEntity> userRoles = new HashSet<>();

    public PersonEntity() {
    }

    public PersonEntity(Boolean isNewEntity) {
        super(isNewEntity);
    }

    public String getFirstName() {
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

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }
    
    

    public Set<ContractUserRole> getContractUserRoles() {
        return contractUserRoles;
    }

    public void setContractUserRoles(Set<ContractUserRole> contractUserRoles) {
        this.contractUserRoles = contractUserRoles;
    }

    public Set<UserRoleEntity> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoleEntity> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isIsStaffMember() {
        return isStaffMember;
    }

    public void setIsStaffMember(boolean isStaffMember) {
        this.isStaffMember = isStaffMember;
    }

}
