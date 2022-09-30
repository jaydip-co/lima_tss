/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dto;

import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import tss.UserRoles;
import tss.enums.ContractStatus;
import tss.enums.TimeSheetFrequency;

/**
 *
 * @author savaliya
 */
@XmlRootElement
public class Contract extends BaseDTO {

    private static final long serialVersionUID = -4848962003160569842L;

    private String name;

    private ContractStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private TimeSheetFrequency frequency;

    private LocalDate terminationDate;

    private double hoursPerWeek;

    private double vacationHour;

    private double hourDue;

    private int workingDaysPerWeek;

    private int vacationDaysPerYear;

    private Person employee;

    private Person supervisor;

    private List<Person> secretaries;

    private List<Person> assistants;

    private String currentUserRole;

    public Contract() {
    }

    public Contract(String name, ContractStatus status,
            LocalDate startDate, LocalDate endDate,
            TimeSheetFrequency frequency, LocalDate terminationDate,
            double hoursPerWeek, double vacationHour, double hourDue,
            int workingDaysPerWeek, int vacationDaysPerYear, String uuid,
            int jpaVersion,
            Person employee,
            Person supervisor,
            List<Person> secretaries,
            List<Person> assistants,
            String currentUserRole) {
        super(uuid, jpaVersion);
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
        this.terminationDate = terminationDate;
        this.hoursPerWeek = hoursPerWeek;
        this.vacationHour = vacationHour;
        this.hourDue = hourDue;
        this.workingDaysPerWeek = workingDaysPerWeek;
        this.vacationDaysPerYear = vacationDaysPerYear;
        this.employee = employee;
        this.supervisor = supervisor;
        this.secretaries = secretaries;
        this.assistants = assistants;

        this.currentUserRole = currentUserRole;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public void setCurrentUserRole(String currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public Person getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Person supervisor) {
        this.supervisor = supervisor;
    }

    public List<Person> getSecretaries() {
        return secretaries;
    }

    public void setSecretaries(List<Person> secretaries) {
        this.secretaries = secretaries;
    }

    public List<Person> getAssistants() {
        return assistants;
    }

    public void setAssistants(List<Person> assistants) {
        this.assistants = assistants;
    }

    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person employee) {
        this.employee = employee;
    }

    public Contract(String name, String uuid) {
        super(uuid, 1);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isManager() {
        List<String> roles = List.of(UserRoles.SECRETARY, UserRoles.SUPERVISOR, UserRoles.ASSISTANT);
        return roleContain(roles);
    }
    
    public boolean isRoleSecretary(){
        List<String> roles = List.of(UserRoles.SECRETARY);
        return roleContain(roles);
    }
    
    public boolean isRoleSupervisor(){
        List<String> roles = List.of(UserRoles.SUPERVISOR);
        return roleContain(roles);
    }
    
    public boolean isRoleAssistant(){
        List<String> roles = List.of(UserRoles.ASSISTANT);
        return roleContain(roles);
    }

    private boolean roleContain(List<String> roles) {
        return roles.contains(currentUserRole);
    }

    public boolean inStarted() {
        return status == ContractStatus.STARTED;
    }

    public boolean inPrepared() {
        return status == ContractStatus.PREPARED;
    }

    public String getStatusColor() {
        String color = "";
        switch (this.status) {
            case PREPARED:
                color = "#bcc30b";
                
                break;
            case STARTED:
                color = "#17c634";
                break;
            case TERMINATED:
                color = "#ed2f12";
                break;
            case ARCHIVED:
                color = "gray";
                break;
        }

        return color;
    }
}
