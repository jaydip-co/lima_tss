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
    
    private double totalHours;
    
    private double workedHours;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public double getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        this.workedHours = workedHours;
    }
    
    

    public ContractStatistic(Contract contract, double totalHours, double workedHours) {
        this.contract = contract;
        this.totalHours = totalHours;
        this.workedHours = workedHours;
    }
    
    
}
