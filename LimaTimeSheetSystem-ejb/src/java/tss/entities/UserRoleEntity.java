/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.ws.rs.ext.ParamConverter;

/**
 *
 * @author savaliya
 */

@NamedQueries(
        {@NamedQuery(
                name = "getRoleByname",
                query = "SELECT r FROM UserRoleEntity r WHERE r.roleName = :rolename"
        ),
        
         
        })
@Entity
public class UserRoleEntity  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(unique = true)
    private String roleName;
    
    
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER,mappedBy = "userRoles")
    private Set<PersonEntity> persons = new HashSet<>();

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(Set<PersonEntity> persons) {
        this.persons = persons;
    }

    

    
    
    
    
    
    
}
