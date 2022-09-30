/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import tss.enums.TimeSheetStatus;
import tss.logic.CalculationLogic;

/**
 *
 * @author savaliya
 */
@NamedQueries({
    @NamedQuery(
            name = "getTimeSheetWithUuid",
            query = "SELECT c FROM time_sheet c "
            + "WHERE c.uuid = :uuid"
    ),
    @NamedQuery(
            name = "getTimeSheetForContract",
            query = "SELECT c FROM time_sheet c "
            + "WHERE c.parent = :contract ORDER BY c.id"
    ),
    @NamedQuery(
            name = "getTimeSheetWithStatus",
            query = "SELECT c FROM time_sheet c "
            + "WHERE c.status = :status AND c.parent =:contract"
    ),
    @NamedQuery(
            name = "getPendingSheet",
            query = "SELECT c FROM time_sheet c  "
            + "WHERE c.status = :status AND c.endDate <=:end")
})
@Entity(name = "time_sheet")
public class TimeSheetEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'IN_PROGRESS'")
    @Enumerated(EnumType.STRING)
    private TimeSheetStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private double hoursDue;

    private LocalDate signedByEmployee;

    private LocalDate signedBySupervisor;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<TimeSheetEntryEntity> timeSheetEntry;

    @ManyToOne
    private ContractEntity parent;

    public ContractEntity getParent() {
        return parent;
    }

    public void setParent(ContractEntity parent) {
        this.parent = parent;
    }

    public TimeSheetEntity() {
    }

    public TimeSheetEntity(Boolean isNewEntry) {
        super(isNewEntry);
        if (isNewEntry) {
            timeSheetEntry = new HashSet<>();
            status = TimeSheetStatus.IN_PROGRESS;
        }

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

    public Set<TimeSheetEntryEntity> getTimeSheetEntry() {
        return timeSheetEntry;
    }

    public void setTimeSheetEntry(Set<TimeSheetEntryEntity> timeSheetEntry) {
        this.timeSheetEntry = timeSheetEntry;
    }

    public void calculateDueHours() {
        this.hoursDue = CalculationLogic.getHoursDue(startDate, endDate, parent.getHoursPerWeek(), parent.getWorkingDaysPerWeek());
    }

    public double getWorkHours() {
        double h = 0.0;
        h = getTimeSheetEntry().stream().mapToDouble(TimeSheetEntryEntity::getHours)
                .reduce(h, (a, b) -> a + b);
        return h;

    }

}
