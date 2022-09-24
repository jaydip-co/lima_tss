/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import tss.dto.Contract;
import tss.enums.ContractStatus;
import tss.enums.TimeSheetFrequency;
import tss.logic.CalculationLogic;

/**
 *
 * @author savaliya
 */
@NamedQueries({
    @NamedQuery(
            name = "getAllContract",
            query = "SELECT c FROM contract c"
    ),
    @NamedQuery(
            name = "getContractByUuid",
            query = "SELECT c FROM contract c"
            + " WHERE c.uuid = :uuid"
    )
})

@Entity(name = "contract")
public class ContractEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contractName;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'PREPARED'")
    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.PREPARED;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'WEEKLY'")
    @Enumerated(EnumType.STRING)
    private TimeSheetFrequency frequency;

    private LocalDate terminationDate;

    private double hoursPerWeek;

    private double vacationHour;

    private double hourDue;

    private int workingDaysPerWeek;

    private int vacationDaysPerYear;
    
    @OneToMany(mappedBy = "contract", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<ContractUserRole> contractUserRoles;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<TimeSheetEntity> timeSheets;

    public ContractEntity() {
    }

    public ContractEntity(Boolean isNewEntry) {
        super(isNewEntry);
        if (isNewEntry) {
            timeSheets = new HashSet<>();
        }
    }

    public Set<ContractUserRole> getContractUserRoles() {
        return contractUserRoles;
    }

    public void setContractUserRoles(Set<ContractUserRole> contractUserRoles) {
        this.contractUserRoles = contractUserRoles;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
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

    public TimeSheetFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(TimeSheetFrequency frequency) {
        this.frequency = frequency;
    }

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
        this.terminationDate = terminationDate;
    }

    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public double getVacationHour() {
        return vacationHour;
    }

    public void setVacationHour(double vacationHour) {
        this.vacationHour = vacationHour;
    }

    public double getHourDue() {
        return hourDue;
    }

    public void setHourDue(double hourDue) {
        this.hourDue = hourDue;
    }

    public int getWorkingDaysPerWeek() {
        return workingDaysPerWeek;
    }

    public void setWorkingDaysPerWeek(int workingDaysPerWeek) {
        this.workingDaysPerWeek = workingDaysPerWeek;
    }

    public int getVacationDaysPerYear() {
        return vacationDaysPerYear;
    }

    public void setVacationDaysPerYear(int vacationDaysPerYear) {
        this.vacationDaysPerYear = vacationDaysPerYear;
    }

    public Set<TimeSheetEntity> getTimeSheets() {
        return timeSheets;
    }

    public void setTimeSheets(Set<TimeSheetEntity> timeSheets) {
        this.timeSheets = timeSheets;
    }

    public void calculateVacationHours() {
        vacationHour = CalculationLogic.calculateVacationHour(startDate,
                endDate, hoursPerWeek, workingDaysPerWeek, vacationDaysPerYear);
    }

    public void calcuteHoursDue() {
        double hours = 0.0;
        hours = timeSheets.stream().map((e) -> e.getHoursDue()).reduce(hours, (accumulator, _item) -> accumulator + _item);
        this.hourDue = hours;
    }
}
