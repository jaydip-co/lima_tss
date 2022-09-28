/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.remote;

import java.util.List;
import java.util.Set;
import javax.ejb.Remote;
import tss.dto.Contract;
import tss.dto.ContractStatistic;
import tss.dto.Person;
import tss.dto.TimeSheet;
import tss.dto.TimeSheetEntry;
import tss.entities.PersonEntity;
import tss.entities.TimeSheetEntity;
import tss.entities.UserRoleEntity;

/**
 *
 * @author savaliya
 */
@Remote
public interface ContractRemote {

    public void setContract();

    public List<Contract> getAllContract();

    public String storeContract(Contract c, Person employee,
            Set<Person> secretaries,
            Set<Person> assistant,
            Person supervisor
    );

    public Person getUserData();

    public Person storeUser(Person p, String[] roles);

    public Person storeUser(Person p, boolean isStaff);

//    public Set<PersonEntity> getUsersWithRole(String role);
    public Set<Person> getUsersWithRole(String role);

    public Contract getContractWithUuid(String uuid);

    public void storeTimeSheetFor(String contractUuid);

    public void storeEntryFor(String timeSheetUuid);

    //////////////// cointract ////////
    public List<Contract> getAllContractWithRole();

    public List<Contract> getContractsWithRole(String[] roles);
    
    public List<Contract> getAllArchievedContract();

    public void startContract(String contractUuid);
    
    public void deleteContract(String contractUUid);

    public void terminateContract(String contractUuid);
    
    public Contract getContractWithTimeSheet(String timeSheetUuid);
    
    public ContractStatistic getContractStatisctic(String contractUuid);

    //////////// user Data /////////
    public Person getUserDataByUserName(String username, List<String> roles, boolean isStaff);
    
    public Person getUserDataByUserName(String username);
    
    public void storeUserData(Person p);

    ///// time sheets ///
    public String storeTimeEntry(TimeSheetEntry tse, String parentUuid);
    
    public void deleteTimeSheetEntry(String entryUuid);

    public List<TimeSheet> getAlltTimeSheetFor(String contractUuid);

    public TimeSheet getCurrentTimeSheet(String contractUuid);
    
    public TimeSheet getTimeSheetWith(String timeSheetUuid);

    public TimeSheetEntry getTimeSheetEntry(String uuid);

    public List<TimeSheetEntry> getAllEntryFor(String timeSheetUuid);

    public List<Person> getAllUser(boolean istaff);
    
    public List<Person> getAllUser();
    
    public boolean signSheet(String sheetUuid);
    
    public boolean revoveSignature(String signature);
    
    public boolean archieveSheet(String sheetUuid);
    
    public double getRemainingVacation(String contractUuid);

}
