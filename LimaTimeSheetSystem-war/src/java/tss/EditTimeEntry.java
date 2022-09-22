/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import tss.dto.TimeSheetEntry;
import tss.enums.ReportType;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@ViewScoped
@Named
public class EditTimeEntry implements Serializable {

    @EJB
    protected ContractRemote cr;

    private String timeEntryUuid;
    private String parrentUuid;

    private Date startTime;

    private ReportType rt = ReportType.WORK;

    private String description;

    private Date endTime;

    private TimeSheetEntry entry;

    private Date entryDate;
    
    private String errorString = "";

    public ReportType[] getReportTypes() {
        return ReportType.values();
    }

    public ReportType getRt() {
        return rt;
    }

    public void setRt(ReportType rt) {
        this.rt = rt;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date date) {
        this.startTime = date;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeEntryUuid() {
        return timeEntryUuid;
    }

    public void setTimeEntryUuid(String timeEntryUuid) {
        this.timeEntryUuid = timeEntryUuid;
    }

    public String getParrentUuid() {
        return parrentUuid;
    }

    public void setParrentUuid(String parrentUuid) {
        this.parrentUuid = parrentUuid;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getErrorString() {
        return errorString;
    }
    
    public boolean isValidationError(){
        return !this.errorString.equals("");
    }
    
    

    public TimeSheetEntry getEntry() {
        if (this.entry != null) {
            return this.entry;
        }
        if (!"new".equals(timeEntryUuid)) {
            this.entry = cr.getTimeSheetEntry(timeEntryUuid);
            entryDate = Date.from(entry.getEntryDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            startTime = dateFromTime(entry.getStartTime(), entry.getEntryDate());
            endTime = dateFromTime(entry.getEndTime(), entry.getEntryDate());

        } else {
            this.entry = new TimeSheetEntry();
        }
        return entry;
    }

    public void storeEntry() {
        errorString ="";
        if(startTime.compareTo(endTime) >= 0){
            errorString = "start time can not be after end time";
            return;
        }

        entry.setStartTime(LocalTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault()));
        entry.setEndTime(LocalTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault()));
        entry.setEntryDate(LocalDate.ofInstant(entryDate.toInstant(), ZoneId.systemDefault()));
        this.timeEntryUuid = cr.storeTimeEntry(entry, parrentUuid);

        if (timeEntryUuid != null) {
            entry = null;
        }

    }

    private Date dateFromTime(LocalTime time, LocalDate date) {
        return Date.from(time.atDate(date).atZone(ZoneId.systemDefault()).toInstant());
    }
}
