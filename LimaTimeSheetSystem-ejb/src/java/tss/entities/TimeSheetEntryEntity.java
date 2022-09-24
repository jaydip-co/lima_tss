/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoField;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import tss.enums.ReportType;

/**
 *
 * @author savaliya
 */

@NamedQueries({
    @NamedQuery(
            name = "getTimeEntryForParent",
            query = "SELECT c FROM time_sheet_entry c "
            +"WHERE c.parent =:parent"
    ),
    @NamedQuery(
            name ="getTimeEntryWithUuid",
            query = "SELECT c FROM time_sheet_entry c "
            + "WHERE c.uuid = :uuid"
    ),
   
})
@Entity(name = "time_sheet_entry")
public class TimeSheetEntryEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    
    private String description;
    
    private double hours;
    
    private LocalTime startTime;
    
    private LocalTime endTime;
    
    private LocalDate entryDate;
    
    @ManyToOne
    private TimeSheetEntity parent;

    public TimeSheetEntryEntity() {
    }
    public TimeSheetEntryEntity(Boolean isNewEntry) {
        super(isNewEntry);
    }
    

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

   

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public TimeSheetEntity getParent() {
        return parent;
    }

    public void setParent(TimeSheetEntity parent) {
        this.parent = parent;
    }
    
    public void calculateHours(){
        double hour = (double)endTime.getHour() - (double)startTime.getHour();
        int min = endTime.get(ChronoField.SECOND_OF_DAY) - startTime.get(ChronoField.SECOND_OF_DAY);
        this.hours = ((double) min / (double)3600);
    }
}
