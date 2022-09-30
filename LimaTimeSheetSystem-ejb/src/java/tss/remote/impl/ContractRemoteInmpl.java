/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.remote.impl;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import tss.enums.UserRoles;
import tss.dao.ContractAccess;
import tss.dao.ContractUserRoleAccess;
import tss.dao.PersonAccess;
import tss.dao.TimeSheetAccess;
import tss.dao.UserRoleAccess;
import tss.dto.Contract;
import tss.dto.ContractStatistic;
import tss.dto.Convertor;
import tss.dto.Person;
import tss.dto.TimeSheet;
import tss.dto.TimeSheetEntry;
import tss.entities.ContractEntity;
import tss.entities.ContractUserRole;
import tss.entities.PersonEntity;
import tss.entities.TimeSheetEntity;
import tss.entities.TimeSheetEntryEntity;
import tss.entities.UserRoleEntity;
import tss.enums.ContractStatus;
import tss.enums.ReportType;
import tss.enums.TimeSheetStatus;
import tss.logic.CalculationLogic;
import tss.logic.TsTimePeriod;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@Stateless
public class ContractRemoteInmpl implements ContractRemote {

    public static final String STUDENT = "STUDENT";

    public static final String STAFF_MEMBER = "STAFFMEMBER";

    @Resource
    EJBContext ejbContext;

    @EJB
    private ContractAccess ca;

    private static final Logger LOG = Logger.getLogger(ContractRemoteInmpl.class.getName());

    @EJB
    private PersonAccess pa;

    @EJB
    private UserRoleAccess ua;
    @EJB
    private ContractUserRoleAccess cura;

    @EJB

    private TimeSheetAccess tsa;

    private PersonEntity currentUser;

    @AroundInvoke
    private Object getCaller(InvocationContext ctx) throws Exception {
        Principal p = ejbContext.getCallerPrincipal();
        if (p != null) {
            currentUser = pa.getUserDataFromUsername(p.getName());
        }
        return ctx.proceed();
    }

    @Override
    public Person getUserData() {
        /// TODO : - implement get user data
        return new Person();
    }

    public EJBContext getEjbContext() {
        return ejbContext;
    }

    public void setEjbContext(EJBContext ejbContext) {
        this.ejbContext = ejbContext;
    }

    public ContractAccess getCa() {
        return ca;
    }

    public void setCa(ContractAccess ca) {
        this.ca = ca;
    }

    public PersonAccess getPa() {
        return pa;
    }

    public void setPa(PersonAccess pa) {
        this.pa = pa;
    }

    public PersonEntity getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(PersonEntity currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public Person storeUser(Person p, boolean isStaff) {
        return Convertor.toPerson(pa.storeUser(p.getUsername(), p.getFirstName(),
                 isStaff));
    }

    ///// user data///////
    @Override
    public Set<Person> getUsersWithRole(String role) {
        UserRoleEntity u = ua.getUserRoleByName(role);
        return u.getPersons().stream().map(e -> Convertor.toPerson(e)).collect(Collectors.toSet());
    }

    @Override
    public void storeUserData(Person p) {
        PersonEntity pe = pa.getUserDataFromUsername(p.getUsername());
        pe.setFirstName(p.getFirstName());
        pe.setLastName(p.getLastName());
        pe.setDob(p.getDob());
        pe.setConsent(p.isConsent());

    }

    @Override
    public Person getUserDataByUserName(String username,
            boolean isStaff) {
        PersonEntity user = pa.getUserDataFromUsername(username);
        if (user == null) {
            user = pa.storeUser(username, null, isStaff);
        }
        return Convertor.toPerson(user);
    }

    @Override
    public Person getUserDataByUserName(String username) {
        PersonEntity user = pa.getUserDataFromUsername(username);
        return Convertor.toPerson(user);
    }

    @Override
    public List<Person> getAllUser(boolean isStaff) {
        return pa.getAllUser(isStaff).stream().map(e -> Convertor.toPerson(e))
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getAllUser() {
        return pa.getAllUser().stream().map(e -> Convertor.toPerson(e))
                .collect(Collectors.toList());
    }

    ///////////// contract logic /////////////
    @RolesAllowed({STAFF_MEMBER, STUDENT})
    @Override
    public Contract getContractWithUuid(String uuid) {
        ContractEntity e = ca.getContractEntityFromUUID(uuid);
        if (e == null) {
            return null;
        }

        Contract c = Convertor.toContract(e, currentUser);
        return c;
    }

    @Override
     @RolesAllowed({STAFF_MEMBER})
    public void deleteContract(String contractUUid) {
        ca.deleteContract(contractUUid);
    }

    @RolesAllowed({STAFF_MEMBER, STUDENT})
    @Override
    public double getRemainingVacation(String contractUuid) {
        ContractEntity c = ca.getContractEntityFromUUID(contractUuid);
        double v = c.getVacationHour();
        double vh = 0;
        Set<TimeSheetEntryEntity> entires = new HashSet<>();
        for (TimeSheetEntity e : c.getTimeSheets()) {
            vh = vh + e.getTimeSheetEntry().stream().filter(i -> i.getReportType() == ReportType.VACATION)
                    .mapToDouble(TimeSheetEntryEntity::getHours)
                    .reduce(0, (a, b) -> a + b);
        }
        return v - vh;
    }

    @Override
     @RolesAllowed({STAFF_MEMBER})
    public String storeContract(Contract c, Person employee,
            Set<Person> secretaries,
            Set<Person> assistants,
            Person supervisor) {
        if (c.isNew()) {
            PersonEntity pe = pa.getUserDataFromUuid(employee.getUuid());

            Set<PersonEntity> secretariesEntity = new HashSet<>();

            secretaries.forEach(e -> {
                secretariesEntity.add(pa.getUserDataFromUuid(e.getUuid()));
            });

            Set<PersonEntity> assistantEntity = new HashSet<>();
            assistants.forEach(e -> {
                assistantEntity.add(pa.getUserDataFromUuid(e.getUuid()));
            });
            Set<PersonEntity> supervisorEntity = new HashSet<>();

            supervisorEntity.add(pa.getUserDataFromUuid(supervisor.getUuid()));

            return ca.createNewContract(c, pe, secretariesEntity,
                    assistantEntity, supervisorEntity);
        }
        ContractEntity e = ca.getContractEntityFromUUID(c.getUuid());
        e.setContractName(c.getName());
        e.setJpaVersion(c.getJpaVersion());
        e.setStartDate(c.getStartDate());
        e.setEndDate(c.getEndDate());
        e.setFrequency(c.getFrequency());
        e.setVacationDaysPerYear(c.getVacationDaysPerYear());
        e.setWorkingDaysPerWeek(c.getWorkingDaysPerWeek());
        e.setHoursPerWeek(c.getHoursPerWeek());
        e.calculateVacationHours();

        PersonEntity pe = pa.getUserDataFromUuid(employee.getUuid());
        PersonEntity su = pa.getUserDataFromUuid(supervisor.getUuid());
        Set<ContractUserRole> users = e.getContractUserRoles();

        Set<ContractUserRole> remo = new HashSet<>();
        for (ContractUserRole r : users) {
            switch (r.getRole()) {
                case UserRoles.EMPLOYEE:
                    r.getPerson().getContractUserRoles().remove(r);
                    r.setPerson(pe);
                    pe.getContractUserRoles().add(r);
                    break;
                case UserRoles.SUPERVISOR:
                    r.getPerson().getContractUserRoles().remove(r);
                    r.setPerson(su);
                    su.getContractUserRoles().add(r);
                    break;
                case UserRoles.SECRETARY:
                    remo.add(r);
                    break;
                case UserRoles.ASSISTANT:
                    remo.add(r);
                    break;
                default:

                    break;
            }
        }

        remo.forEach(et -> cura.remove(et.getUuid()));
        secretaries.forEach(s -> {
            PersonEntity p = pa.getUserDataFromUuid(s.getUuid());

            ContractUserRole cr = cura.createNewContractUserRole(e, p, UserRoles.SECRETARY);

        });
        assistants.forEach(as -> {
            PersonEntity p = pa.getUserDataFromUuid(as.getUuid());

            cura.createNewContractUserRole(e, p, UserRoles.ASSISTANT);
        });

        return e.getUuid();

    }

    @Override
     @RolesAllowed({STAFF_MEMBER})
    public void startContract(String contractUuid) {
        ContractEntity contract = ca.getContractEntityFromUUID(contractUuid);
        contract.setStatus(ContractStatus.STARTED);
        List<TsTimePeriod> weeks = CalculationLogic
                .getWeeksBetween(contract.getStartDate(),
                        contract.getEndDate(), contract.getFrequency());
        weeks.stream().map((e) -> tsa.StoreTimeSheet(e.startDate, e.endDate)).map((te) -> {
            te.setParent(contract);
            te.calculateDueHours();
            return te;
        }).forEachOrdered((te) -> {
            contract.getTimeSheets().add(te);
        });
        contract.calcuteHoursDue();
    }

    @Override
    @RolesAllowed({STAFF_MEMBER})
    public void terminateContract(String contractUuid) {
        ContractEntity contract = ca.getContractEntityFromUUID(contractUuid);
        contract.setStatus(ContractStatus.TERMINATED);
        tsa.deleteSheeetWith(contract, TimeSheetStatus.IN_PROGRESS);
        checkForArchieveContract(contractUuid);
    }

    @Override
    public List<Contract> getAllContract() {
        return ca.getAllContract().stream().map(e -> Convertor.toContract(e, currentUser))
                .collect(Collectors.toList());
    }
    
     @RolesAllowed({STAFF_MEMBER,STUDENT})
    @Override
    public List<Contract> getAllContractWithRole() {
        return cura.getAllContractUserRoleWith(currentUser)
                .stream().map(e -> Convertor.toContract(e.getContract(), currentUser))
                .collect(Collectors.toList());
    }
     @RolesAllowed({STAFF_MEMBER,STUDENT})
    @Override
    public Contract getContractWithTimeSheet(String timeSheetUuid) {
        TimeSheetEntity time = tsa.getSheetEntityFor(timeSheetUuid);
        return Convertor.toContract(time.getParent(), currentUser);
    }
    
    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public ContractStatistic getContractStatisctic(String contractUuid) {
        ContractEntity c = ca.getContractEntityFromUUID(contractUuid);
        double totalDue = c.getHourDue();

        double vacationHours = 0;
        double workedHours = 0;
        double seekLeaveHours = 0;
        for (TimeSheetEntity t : c.getTimeSheets()) {
            for (TimeSheetEntryEntity tse : t.getTimeSheetEntry()) {
                switch (tse.getReportType()) {
                    case SICK_LEAVE:
                        seekLeaveHours += tse.getHours();
                        break;
                    case VACATION:
                        vacationHours += tse.getHours();
                        break;
                    case WORK:
                        workedHours += tse.getHours();
                        break;
                    default:
                        break;
                }
            }
        }
        return new ContractStatistic(
                Convertor.toContract(c, currentUser), totalDue, vacationHours, seekLeaveHours,
                workedHours, c.getVacationHour());
    }

    @Override
    public List<Contract> getContractsWithRole(String[] roles) {
        Set<Contract> contracts = new HashSet<>();
        for (String r : roles) {
            contracts.addAll(
                    cura.getContractWithRole(currentUser, r)
                            .stream()
                            .filter(e -> e.getContract().getStatus() != ContractStatus.ARCHIVED)
                            .map(e -> Convertor.toContract(e.getContract(), currentUser))
                            .collect(Collectors.toList()));
        }
        return contracts.stream().sorted(Comparator.comparing(Contract::getStartDate)).collect(Collectors.toList());
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public List<Contract> getAllArchievedContract() {
        return cura.getAllContractUserRoleWith(currentUser)
                .stream().filter(e -> e.getContract().getStatus() == ContractStatus.ARCHIVED)
                .map(e -> Convertor.toContract(e.getContract(), currentUser))
                .collect(Collectors.toSet())
                .stream().collect(Collectors.toList());

    }

    ///// time sheet ///////
    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public List<TimeSheet> getAlltTimeSheetFor(String contractUuid) {
        ContractEntity ce = ca.getContractEntityFromUUID(contractUuid);
        if (ce == null) {
            return null;
        }
        List<TimeSheet> sheets = ce.getTimeSheets().stream()
                .map(e -> Convertor.toTimeSheet(e))
                .sorted((i1, i2) -> i2.getStartDate().isAfter(i1.getStartDate()) ? -1 : 1)
                .collect(Collectors.toList());
        return sheets;
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public void deleteTimeSheetEntry(String entryUuid) {

        tsa.deleteEntryWith(entryUuid);

    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public TimeSheet getCurrentTimeSheet(String contractUuid) {
        ContractEntity ce = ca.getContractEntityFromUUID(contractUuid);
        if (ce == null) {
            return null;
        }
        LocalDate date = LocalDate.now();
        for (TimeSheetEntity e : ce.getTimeSheets()) {
            if ((e.getStartDate().compareTo(date) * date.compareTo(e.getEndDate())) >= 0) {
                return Convertor.toTimeSheet(e);
            }
        }
        return null;
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public TimeSheet getTimeSheetWith(String timeSheetUuid) {
        return Convertor.toTimeSheet(tsa.getSheetEntityFor(timeSheetUuid));
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public String storeTimeEntry(TimeSheetEntry tse, String parentUuid) {
        TimeSheetEntity ts = tsa.getSheetEntityFor(parentUuid);
        TimeSheetEntryEntity tsee;
        if (tse.isNew()) {
            tsee = tsa.storeTimeEntry(tse.getReportType(),
                    tse.getStartTime(), tse.getEndTime(),
                    tse.getDescription(), tse.getEntryDate());

            tsee.setParent(ts);
            ts.getTimeSheetEntry().add(tsee);
            tsee.calculateHours();
        } else {
            tsee = tsa.getTimeEntryWith(tse.getUuid());
            tsee.setDescription(tse.getDescription());
            tsee.setStartTime(tse.getStartTime());
            tsee.setEndTime(tse.getEndTime());
            tsee.setReportType(tse.getReportType());
            tsee.calculateHours();
        }

        return tsee.getUuid();
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public boolean signSheet(String sheetUuid) {
        TimeSheetEntity sheet = tsa.getSheetEntityFor(sheetUuid);
        Contract c = Convertor.toContract(sheet.getParent(), currentUser);
        if (c.getCurrentUserRole().equals(UserRoles.EMPLOYEE)) {
            sheet.setStatus(TimeSheetStatus.SIGNED_BY_EMPLOYEE);
            sheet.setSignedByEmployee(LocalDate.now());
        } else if (c.getCurrentUserRole().equals(UserRoles.SUPERVISOR)) {
            sheet.setStatus(TimeSheetStatus.SIGNED_BY_SUPERVISOR);
            sheet.setSignedBySupervisor(LocalDate.now());

        }

        return true;
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public boolean revoveSignature(String sheetUuid) {
        TimeSheetEntity sheetEntity = tsa.getSheetEntityFor(sheetUuid);
        sheetEntity.setSignedByEmployee(null);
        sheetEntity.setStatus(TimeSheetStatus.IN_PROGRESS);
        return true;
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public boolean archieveSheet(String sheetUuid) {
        TimeSheetEntity sheetEntity = tsa.getSheetEntityFor(sheetUuid);
        sheetEntity.setStatus(TimeSheetStatus.ARCHIVED);
        checkForArchieveContract(sheetEntity.getParent().getUuid());
        return true;
    }

    private void checkForArchieveContract(String contractUuid) {
        ContractEntity contractEntity = ca.getContractEntityFromUUID(contractUuid);
        Set<TimeSheetEntity> sheets = contractEntity.getTimeSheets();
        if (sheets.stream().anyMatch((e) -> (e.getStatus() != TimeSheetStatus.ARCHIVED))) {
            return;
        }
        contractEntity.setStatus(ContractStatus.ARCHIVED);
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public List<TimeSheetEntry> getAllEntryFor(String timeSheetUuid) {
        TimeSheetEntity tse = tsa.getSheetEntityFor(timeSheetUuid);
        return tsa.getAllEntryFor(tse).stream()
                .map(e -> Convertor.toTimeEntry(e)).collect(Collectors.toList());
    }

    @Override
    @RolesAllowed({STAFF_MEMBER,STUDENT})
    public TimeSheetEntry getTimeSheetEntry(String uuid) {
        return Convertor.toTimeEntry(tsa.getTimeEntryWith(uuid));
    }

}
