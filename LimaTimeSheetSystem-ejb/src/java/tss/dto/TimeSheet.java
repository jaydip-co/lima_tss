/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dto;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlRootElement;
import tss.enums.TimeSheetStatus;

/**
 *
 * @author savaliya
 */
@XmlRootElement
public class TimeSheet extends BaseDTO{
    
     private TimeSheetStatus status;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    private double hoursDue;
    
    private LocalDate signedByEmployee;
    
    private LocalDate signedBySupervisor;

    public TimeSheet(TimeSheetStatus status, 
            LocalDate startDate,
            LocalDate endDate,
            double hoursDue,
            LocalDate signedByEmployee, 
            LocalDate signedBySupervisor,
            String uuid, int jpaVersion) {
        super(uuid, jpaVersion);
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hoursDue = hoursDue;
        this.signedByEmployee = signedByEmployee;
        this.signedBySupervisor = signedBySupervisor;
    }

    public TimeSheetStatus getStatus() {
        return status;
    }

    public void setStatus(TimeSheetStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(double hoursDue) {
        this.hoursDue = hoursDue;
    }

    public LocalDate getSignedByEmployee() {
        return signedByEmployee;
    }

    public void setSignedByEmployee(LocalDate signedByEmployee) {
        this.signedByEmployee = signedByEmployee;
    }

    public LocalDate getSignedBySupervisor() {
        return signedBySupervisor;
    }

    public void setSignedBySupervisor(LocalDate signedBySupervisor) {
        this.signedBySupervisor = signedBySupervisor;
    }

    
    
    
    
}
