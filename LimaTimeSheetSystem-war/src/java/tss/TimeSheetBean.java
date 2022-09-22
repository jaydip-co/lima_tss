/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import tss.dto.Contract;
import tss.dto.TimeSheet;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@Named
@ViewScoped
public class TimeSheetBean implements Serializable{
    
    @EJB
    private ContractRemote cr;
    
    
    private String uuid;
    
    private Contract c;
    private TimeSheet currentTs;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public Contract getConract(){
        if(c == null){
             c = cr.getContractWithUuid(uuid);
        }
        return c;
    }
    
    
    public List<TimeSheet> getAllTimeSheet(){
        
       return cr.getAlltTimeSheetFor(uuid);
    }
    
    public TimeSheet getCurrentTimeSheet(){
        if(currentTs == null){
            currentTs = cr.getCurrentTimeSheet(uuid);
        }
        return currentTs;
    }
    
    public boolean isActiveSheet(){
       currentTs = getCurrentTimeSheet();
        return currentTs != null;
    }
    
    
    public void addTimeSheets(){
        
        cr.storeTimeSheetFor(uuid);
    }
    
}
