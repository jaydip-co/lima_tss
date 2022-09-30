/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import tss.dto.Contract;
import tss.dto.ContractStatistic;
import tss.remote.ContractRemote;



/**
 *
 * @author savaliya
 */
@ViewScoped
@Named
public class StatisticBean implements Serializable{
    
    @EJB
    ContractRemote cr;
    
    private String contractUuid;
    
    public String getContractUuid() {
        return contractUuid;
    }

    public void setContractUuid(String contractUuid) {
        this.contractUuid = contractUuid;
    }
    
    private ContractStatistic statistic;

    public ContractStatistic getStatistic() {
        if(statistic == null){
            statistic = cr.getContractStatisctic(contractUuid);
        }
        return statistic;
    }

    public void setStatistic(ContractStatistic statistic) {
        this.statistic = statistic;
    }
    
    public Contract getContract(){
        return cr.getContractWithUuid(contractUuid);
    }
    
    
    
    public String getTotalHoursChartData(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("[");
        sb.append("'");
        sb.append("Worked Hours");
        sb.append("'");
        sb.append(",");
        sb.append(getStatistic().getWorkedHours());
        sb.append("]");
        sb.append(",");
        sb.append("[");
        sb.append("'");
        sb.append("Vacation Hours");
        sb.append("'");
        sb.append(",");
        sb.append(getStatistic().getVacationHours());
        sb.append("]");
        sb.append(",");
        sb.append("[");
        sb.append("'");
        sb.append("Seek Leave");
        sb.append("'");
        sb.append(",");
        sb.append(getStatistic().getSeekLeaveHours());
        
        sb.append("]");
        sb.append(",");
        sb.append("[");
        sb.append("'");
        sb.append("Remaining Hours");
        sb.append("'");
        sb.append(",");
        sb.append(getStatistic().getRemainingHours());
        
        sb.append("]");
        
        
        
        return sb.toString();
    }
    
    public String getVacationHoursData(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("[");
        sb.append("'");
        sb.append("Vacation Hours");
        sb.append("'");
        sb.append(",");
        sb.append(getStatistic().getVacationHours());
        sb.append("]");
        sb.append(",");
         sb.append("[");
        sb.append("'");
        sb.append("Remainng");
        sb.append("'");
        sb.append(",");
        sb.append(getStatistic().getTotalVacationHours());
        sb.append("]");
        
        return sb.toString();
    }
}
