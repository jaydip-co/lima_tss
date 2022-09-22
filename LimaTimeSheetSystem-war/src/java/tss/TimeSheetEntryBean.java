/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import tss.dto.TimeSheet;
import tss.dto.TimeSheetEntry;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@Named
@ViewScoped
public class TimeSheetEntryBean implements Serializable {
    @EJB
    ContractRemote cr;
    
    private String uuid;
    private String parentUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ContractRemote getCr() {
        return cr;
    }

    public void setCr(ContractRemote cr) {
        this.cr = cr;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }
    
    public TimeSheet getTimeSheet(){
        return cr.getCurrentTimeSheet(parentUuid);
    }
    
    
    
    public List<TimeSheetEntry> getAllTimeShhetEntry(){
        if(uuid == null){
         uuid = cr.getCurrentTimeSheet(parentUuid).getUuid();
         return cr.getAllEntryFor(uuid);
        }
        return cr.getAllEntryFor(uuid);
    }
    
    public void setEntry(){
        cr.storeEntryFor(uuid);
    }
    
}
