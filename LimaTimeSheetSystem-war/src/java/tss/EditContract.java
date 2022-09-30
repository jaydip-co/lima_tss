/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import tss.dto.Contract;
import tss.dto.Person;
import tss.enums.ContractStatus;
import tss.enums.TimeSheetFrequency;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@ViewScoped
@Named
public class EditContract implements Serializable {

    private static final long serialVersionUID = -8516655326049733719L;

    @EJB
    ContractRemote cr;

    private String uuid;

    private Contract contract;

    private String name;

    private String frq = "1";

    private Person employee;

    private Date startDate;

    private Date endDate;

    private String wdpw = "5";

    private String vdpy = "20";

    private String hpw;

    private String errorMessage = "";

    private Person secretary;
    private Person supervisor;
    private Person assistant;

    private String secr;

    private String assis;

    private List<Person> per = new ArrayList<>();

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Person> getPer() {
        return per;
    }

    public void setPer(List<Person> per) {
        this.per = per;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSecr() {
        return secr;
    }

    public void setSecr(String secr) {
        this.secr = secr;
    }

    public String getAssis() {
        return assis;
    }

    public void setAssis(String assis) {
        this.assis = assis;
    }

    public String getWdpw() {
        return wdpw;
    }

    public void setWdpw(String wdpw) {
        this.wdpw = wdpw;
    }

    public String getVdpy() {
        return vdpy;
    }

    public void setVdpy(String vdpw) {
        this.vdpy = vdpw;
    }

    public String getHpw() {
        return hpw;
    }

    public void setHpw(String hpw) {
        this.hpw = hpw;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Person getSecretary() {
        return secretary;
    }

    public void setSecretary(Person secretary) {
        this.secretary = secretary;
    }

    public Person getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Person supervisor) {
        this.supervisor = supervisor;
    }

    public Person getAssistant() {
        return assistant;
    }

    public void setAssistant(Person assistant) {
        this.assistant = assistant;
    }

    public Date getTodayDate() {
        return new Date();
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Person getEmployee() {
        return employee;
    }

    public void setEmployee(Person c) {
        this.employee = c;
    }

    public ContractRemote getCr() {
        return cr;
    }

    public void setCr(ContractRemote cr) {
        this.cr = cr;
    }

    public String getFrq() {
        return frq;
    }

    public void setFrq(String frq) {
        this.frq = frq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isValidationError() {
        return !errorMessage.equals("");
    }

    public void addPer() {
        per.add(null);
    }

    public Contract getContract() {
        if (contract == null) {
            if ("new".equals(uuid)) {
                contract = new Contract();
            } else {
                contract = cr.getContractWithUuid(uuid);
                frq = contract.getFrequency() == TimeSheetFrequency.WEEKLY ? "1" : "2";
                startDate = convertToDate(contract.getStartDate());
                endDate = convertToDate(contract.getEndDate());
                hpw = converToString(contract.getHoursPerWeek());
                wdpw = converToString(contract.getWorkingDaysPerWeek());
                vdpy = converToString(contract.getVacationDaysPerYear());

                employee = contract.getEmployee();
                supervisor = contract.getSupervisor();

                assistant = contract.getAssistants().size() > 0 ? contract.getAssistants().get(0) : null;
                assis = getStringFrom(contract.getAssistants());
                secretary = contract.getSecretaries().size() > 0 ? contract.getSecretaries().get(0) : null;
                secr = getStringFrom(contract.getSecretaries());
            }
        }
        return contract;
    }

    private String getStringFrom(List<Person> p) {
        StringBuilder sb = new StringBuilder();
        for (Person s : p) {
            sb.append(s.getUuid());
            if (!(p.indexOf(s) == p.size() - 1)) {
                sb.append(",");
            }

        }
        return sb.toString();
    }

    private List<Person> getPersonFrom(String s) {

        List<Person> p = new ArrayList<>();
        if (s == null) {
            return p;
        }
        String ss[] = s.split(",");
        List<Person> all = getStaffMembers();
        for (String st : ss) {
            all.forEach(e -> {
                if (e.getUuid().equals(st)) {
                    p.add(e);
                }
            });
        }
        return p;
    }

    public void storeContract() {

        LocalDate sD = convert(startDate);
        LocalDate eD = convert(endDate);
        errorMessage = "";
        if (sD.isBefore(LocalDate.now())) {
            errorMessage = "Start can not be in past";
            return;
        }
        if (sD.getDayOfMonth() != 1 || eD.getDayOfMonth() != eD.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth()) {
            errorMessage = "Please Select Start Date as first Day of month and last Date as last Day of Month";
            return;
        }

        contract.setStartDate(convert(startDate));
        contract.setEndDate(convert(endDate));
        contract.setFrequency(frq.equals("1") ? TimeSheetFrequency.WEEKLY : TimeSheetFrequency.MONTHLY);
        contract.setVacationDaysPerYear(convertToInt(vdpy));
        contract.setWorkingDaysPerWeek(convertToInt(wdpw));
        contract.setHoursPerWeek(convertToDouble(hpw));
        Set<Person> secretaryList = new HashSet<>();

        secretaryList.addAll(getPersonFrom(secr));

//            secretaryList.add(this.secretary);
        Set<Person> assistantList = new HashSet<>();
        assistantList.addAll(getPersonFrom(assis));
//            assistantList.add(this.assistant);

        uuid = cr.storeContract(contract, employee,
                secretaryList, assistantList, supervisor);

        if (uuid != null) {
            contract = null;
        } else {
            uuid = "new";
        }

    }

    public void startContract() {
        cr.startContract(uuid);
        contract = null;
    }

    public void deleteContract() {

        cr.deleteContract(uuid);
        contract = null;
    }

    public void terminate() {
        cr.terminateContract(uuid);
        contract = null;
    }

    public void printContract() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            String absoluteWebPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            context.getExternalContext().redirect(absoluteWebPath + "/rest/contracts/list?id=9b72ae70-d94a-4808-a3cf-95682b719ff2");
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    public boolean isInPrepared() {
        return contract.getStatus() == ContractStatus.PREPARED;
    }

    public boolean isInStarted() {
        return contract.getStatus() == ContractStatus.STARTED;
    }

    public List<Person> getStaffMembers() {
        return cr.getAllUser(true);
    }

    public List<Person> getAllUsers() {
        return cr.getAllUser();
    }

    public Set<Person> getAllEmployees() {
        return cr.getUsersWithRole(UserRoles.EMPLOYEE);
    }

    public Set<Person> getAllSecretary() {
        return cr.getUsersWithRole(UserRoles.SECRETARY);
    }

    public Set<Person> getAllSupervisor() {
        return cr.getUsersWithRole(UserRoles.SUPERVISOR);
    }

    public Set<Person> getAllAssistant() {
        return cr.getUsersWithRole(UserRoles.ASSISTANT);
    }

    private LocalDate convert(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date convertToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private int convertToInt(String s) {
        return Integer.parseInt(s);
    }

    private Double convertToDouble(String s) {
        return Double.parseDouble(s);
    }

    private String converToString(Object o) {
        return String.valueOf(o);
    }
}
