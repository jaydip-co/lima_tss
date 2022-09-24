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
import tss.dto.Contract;
import tss.dto.TimeSheet;
import tss.dto.TimeSheetEntry;
import tss.enums.TimeSheetStatus;
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
    
    private Contract contract;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ContractRemote getCr() {
        return cr;
    }

    public Contract getContract() {
        if(contract == null){
            contract = cr.getContractWithTimeSheet(uuid);
        }
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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

    public TimeSheet getTimeSheet() {
        return cr.getTimeSheetWith(uuid);

    }

    public List<TimeSheetEntry> getAllTimeShhetEntry() {
        if (uuid == null) {
            uuid = cr.getCurrentTimeSheet(parentUuid).getUuid();
            return cr.getAllEntryFor(uuid);
        }
        return cr.getAllEntryFor(uuid);
    }

    public void setEntry() {
        cr.storeEntryFor(uuid);
    }

    public void signSheet() {
        cr.signSheet(uuid);

    }
    
    public void archieveSheet(){
        cr.archieveSheet(uuid);
    }

    public void revokeSignature() {
        cr.revoveSignature(uuid);
    }
    
    public boolean isStartStatus(){
      return   getContract().inStarted();
    }
    
    public boolean isEmployee(){
        return UserRoles.EMPLOYEE.equals(getContract().getCurrentUserRole());
    }
    
    public boolean isSupervisor(){
        return UserRoles.SUPERVISOR.equals(getContract().getCurrentUserRole());
    }
    public boolean isManager(){
        return getContract().isManager();
    }
    
    
    public boolean isCurrentSheetinSignedByEmployee(){
        return getTimeSheet().getStatus() == TimeSheetStatus.SIGNED_BY_EMPLOYEE;
    }
    public boolean isCurrentSheetinSignedBySupervisor(){
        return getTimeSheet().getStatus() == TimeSheetStatus.SIGNED_BY_SUPERVISOR;
    }
    public boolean canArchieve(){
        return getTimeSheet().getStatus() == TimeSheetStatus.SIGNED_BY_SUPERVISOR;
    }

}
