/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import tss.entities.ContractEntity;
import tss.entities.TimeSheetEntity;
import tss.entities.TimeSheetEntryEntity;
import tss.enums.ReportType;

/**
 *
 * @author savaliya
 */
@LocalBean
@Stateless
public class TimeSheetAccess extends BaseAccess {

    public TimeSheetEntity StoreTimeSheet(LocalDate startDate, LocalDate endDate) {
        TimeSheetEntity timeSheet = new TimeSheetEntity(true);
        timeSheet.setStartDate(startDate);
        timeSheet.setEndDate(endDate);
        em.persist(timeSheet);
        return timeSheet;
    }

    public List<TimeSheetEntity> getTimeShhetEntryWith(ContractEntity ce) {
        return em.createNamedQuery("getTimeSheetForContract", TimeSheetEntity.class)
                .setParameter("contract", ce).getResultList();
    }

    public TimeSheetEntity getSheetEntityFor(String sheetUuid) {
        return em.createNamedQuery("getTimeSheetWithUuid",
                TimeSheetEntity.class)
                .setParameter("uuid", sheetUuid)
                .getSingleResult();
    }
    
    
    public TimeSheetEntryEntity storeEntry(LocalTime startTime,LocalTime endTime){
        TimeSheetEntryEntity tsee = new TimeSheetEntryEntity(true);
        tsee.setStartTime(startTime);
        tsee.setEndTime(endTime);
        em.persist(tsee);
        return tsee;
    }
    public TimeSheetEntryEntity storeTimeEntry(
            ReportType reportType,
            LocalTime startTime,
            LocalTime endTime,
            String desciption,
            LocalDate entryDate
            ){
        TimeSheetEntryEntity tsee = new TimeSheetEntryEntity(true);
        tsee.setReportType(reportType);
        tsee.setStartTime(startTime);
        tsee.setEndTime(endTime);
        tsee.setDescription(desciption);
        tsee.setEntryDate(entryDate);
        em.persist(tsee);
        return tsee;
    }
   
    
    public List<TimeSheetEntryEntity> getAllEntryFor(TimeSheetEntity tsee){
        return em.createNamedQuery("getTimeEntryForParent",TimeSheetEntryEntity.class)
                .setParameter("parent", tsee).getResultList();
    }
    
    public TimeSheetEntryEntity getTimeEntryWith(String uuid){
        return em.createNamedQuery("getTimeEntryWithUuid", TimeSheetEntryEntity.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
    }
    
}
