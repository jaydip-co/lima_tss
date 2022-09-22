/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dto;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.xml.bind.annotation.XmlRootElement;
import tss.enums.ReportType;

/**
 *
 * @author savaliya
 */
@XmlRootElement
public class TimeSheetEntry  extends BaseDTO{
    private ReportType reportType;
    
    private String description;
    
    private double hours;
    
    private LocalTime startTime;
    
    private LocalTime endTime;
    
    private LocalDate entryDate;
    
    public TimeSheetEntry(){}

    public TimeSheetEntry(ReportType reportType,
            String description,
            double hours,
            LocalTime startTime,
            LocalTime endTime,
            LocalDate entryDate,
            String uuid, int jpaVersion) {
        super(uuid, jpaVersion);
        this.reportType = reportType;
        this.description = description;
        this.hours = hours;
        this.startTime = startTime;
        this.endTime = endTime;
        this.entryDate = entryDate;
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
        DecimalFormat formate = new DecimalFormat("0.00");
        return Double.parseDouble(formate.format(this.hours));
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
    
    
}
