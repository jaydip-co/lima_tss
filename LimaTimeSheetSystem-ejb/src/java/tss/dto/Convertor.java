/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import tss.UserRoles;
import tss.entities.ContractEntity;
import tss.entities.ContractUserRole;
import tss.entities.PersonEntity;
import tss.entities.TimeSheetEntity;
import tss.entities.TimeSheetEntryEntity;

/**
 *
 * @author savaliya
 */
public class Convertor {

    public static Contract toContract(ContractEntity e,PersonEntity curentUser) {
        Set<ContractUserRole> role = e.getContractUserRoles();
        PersonEntity employee = null;
        PersonEntity supervisor = null;
        List<Person> secretaries = new ArrayList<>();
        List<Person> assistants = new ArrayList<>();
        String currentUserRole = null;
        for (ContractUserRole cur : role) {
            if(cur.getPerson() == curentUser){
                currentUserRole = cur.getRole();
            }
            switch (cur.getRole()) {
                case UserRoles.EMPLOYEE:
                    employee = cur.getPerson();
                    break;
                case UserRoles.SUPERVISOR:
                    supervisor = cur.getPerson();
                    break;
                case UserRoles.SECRETARY:
                    secretaries.add(Convertor.toPerson(cur.getPerson()));
                    break;
                case UserRoles.ASSISTANT:
                    assistants.add(Convertor.toPerson(cur.getPerson()));
                    break;
                default:
                    break;

            }
            
        }
        if (employee == null) {
            employee = new PersonEntity();
            employee.setFirstName("No Value");
        }

        Contract contract = new Contract(
                e.getContractName(),
                e.getStatus(),
                e.getStartDate(),
                e.getEndDate(),
                e.getFrequency(),
                e.getTerminationDate(),
                e.getHoursPerWeek(),
                e.getVacationHour(),
                e.getHourDue(),
                e.getWorkingDaysPerWeek(),
                e.getVacationDaysPerYear(),
                e.getUuid(),
                e.getJpaVersion(),
                toPerson(employee),
                toPerson(supervisor),
                secretaries,
                assistants,
                currentUserRole
        );
        return contract;
    }

    public static Person toPerson(PersonEntity e) {
        if(e == null){
            return null;
            
        }
        Person p = new Person(
                e.getFirstName(),
                e.getLastName(),
                e.getDob(),
                e.getUserName(),
                e.isIsStaffMember(),
                e.getUuid(),
                e.getJpaVersion());
        return p;
    }

    public static TimeSheet toTimeSheet(TimeSheetEntity te) {
        TimeSheet ts = new TimeSheet(te.getStatus(),
                te.getStartDate(),
                te.getEndDate(),
                te.getHoursDue(),
                te.getSignedByEmployee(),
                te.getSignedBySupervisor(),
                te.getUuid(),
                te.getJpaVersion());
        return ts;
    }

    public static TimeSheetEntry toTimeEntry(TimeSheetEntryEntity t) {
        TimeSheetEntry te = new TimeSheetEntry(
                t.getReportType(),
                t.getDescription(),
                t.getHours(),
                t.getStartTime(),
                t.getEndTime(),
                t.getEntryDate(),
                t.getUuid(),
                t.getJpaVersion());
        return te;
    }
}
