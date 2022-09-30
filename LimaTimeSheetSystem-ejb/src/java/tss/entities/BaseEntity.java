/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.entities;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 *
 * @author savaliya
 */
@MappedSuperclass
public class BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true,length = 36)
    private String uuid;
    
    @Version
    private int jpaVersion;

    public BaseEntity() {
    }
    
    public BaseEntity(Boolean newEntity){
        if(newEntity){
        this.uuid = UUID.randomUUID().toString();
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       if(object instanceof BaseEntity) {
           BaseEntity e = (BaseEntity) object;
           if(this.uuid != null && this.uuid.equals(e.uuid)){
           return true;
           }
       }
       return false;
    }

    @Override
    public String toString() {
        return "tss.entities.BaseEntity[ id=" + id + " uuid " + uuid +"]";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public int getJpaVersion() {
        return jpaVersion;
    }

    public void setJpaVersion(int jpaVersion) {
        this.jpaVersion = jpaVersion;
    }
    
    
    
}
