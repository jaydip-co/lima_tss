/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author savaliya
 */
@NamedQueries({
    @NamedQuery(
            name = "getContractWithRole",
            query = "SELECT c FROM ContractUserRole c "
            + "WHERE c.person = :person"
    ),
    @NamedQuery(
            name = "getContractWithSpecificRole",
            query = "SELECT c FROM ContractUserRole c "
            +"WHERE c.person =:person AND c.userrole =:role"
    ),
    @NamedQuery(
            name = "getContractRoleWithUuid",
            query = "SELECT c FROM ContractUserRole c "
            + "WHERE c.uuid =:uuid"
    )
})

@Entity
public class  ContractUserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private PersonEntity person;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private ContractEntity contract;
    
    private String userrole;

    public ContractUserRole(Boolean newEntity) {
        super(newEntity);
    }

    public ContractUserRole() {
    }
    
    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

    public String getRole() {
        return userrole;
    }

    public void setRole(String role) {
        this.userrole = role;
    }
    

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    
    
   
    
}
