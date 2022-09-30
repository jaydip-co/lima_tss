/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.web;

import java.io.Serializable;
import java.util.List;
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
    
    
    private String contractUuid;
    
    private Contract c;
    private TimeSheet currentTs;

    public String getContractUuid() {
        return contractUuid;
    }

    public void setContractUuid(String uuid) {
        this.contractUuid = uuid;
    }
    
    public Contract getConract(){
        if(c == null){
             c = cr.getContractWithUuid(contractUuid);
        }
        return c;
    }
    
    
    public List<TimeSheet> getAllTimeSheet(){
        
       return cr.getAlltTimeSheetFor(contractUuid);
    }
    
    public TimeSheet getCurrentTimeSheet(){
        if(currentTs == null){
            currentTs = cr.getCurrentTimeSheet(contractUuid);
        }
        return currentTs;
    }
    
    public boolean isActiveSheet(){
       currentTs = getCurrentTimeSheet();
        return currentTs != null;
    }
    
    
    public void addTimeSheets(){
        
//        cr.storeTimeSheetFor(contractUuid);
    }
    
    public boolean isTimesheetAwailable(){
        return !getAllTimeSheet().isEmpty();
    }
}
