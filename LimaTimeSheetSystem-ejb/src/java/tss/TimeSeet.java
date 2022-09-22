/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.time.LocalDate;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tss.dao.ContractAccess;
import tss.entities.ContractEntity;
import tss.enums.ContractStatus;

/**
 *
 * @author savaliya
 */
@Stateless
@LocalBean
public class TimeSeet {
    
    @PersistenceContext(unitName = "LimaTimeSheetSystem-ejbPU")
    private EntityManager em;
    
    @EJB
    private ContractAccess ca;
    
    public void setContract(){
//       ca.createNewContract();
    } 

   
}
