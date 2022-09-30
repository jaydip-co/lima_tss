/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author savaliya
 */
@XmlRootElement
public class ContractStatistic {
    private Contract contract;
    
    private double totalDueHour;
    
    private double vacationHours;
    
    private double seekLeaveHours;
    
    private double workedHours;
    
    private double remainingHours;
    
    private double totalVacationHours;

    public ContractStatistic(Contract contract, double totalDueHour, double vacationHours, double seekLeaveHours, double workedHours, double totalVacationHours) {
        this.contract = contract;
        this.totalDueHour = totalDueHour;
        this.vacationHours = vacationHours;
        this.seekLeaveHours = seekLeaveHours;
        this.workedHours = workedHours;
        this.totalVacationHours = totalVacationHours;
        
        remainingHours = Math.max(0, totalDueHour - (vacationHours + seekLeaveHours + workedHours));
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public double getTotalDueHour() {
        return totalDueHour;
    }

    public void setTotalDueHour(double totalDueHour) {
        this.totalDueHour = totalDueHour;
    }

    public double getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(double vacationHours) {
        this.vacationHours = vacationHours;
    }

    public double getSeekLeaveHours() {
        return seekLeaveHours;
    }

    public void setSeekLeaveHours(double seekLeaveHours) {
        this.seekLeaveHours = seekLeaveHours;
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        this.workedHours = workedHours;
    }

    public double getRemainingHours() {
        return remainingHours;
    }

    public void setRemainingHours(double remainingHours) {
        this.remainingHours = remainingHours;
    }

    public double getTotalVacationHours() {
        return totalVacationHours;
    }

    public void setTotalVacationHours(double totalVacationHours) {
        this.totalVacationHours = totalVacationHours;
    }
    
    
    
    
}
