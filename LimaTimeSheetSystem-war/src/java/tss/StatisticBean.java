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
    
    
    
    public String getTotalHoursChartData(){
        StringBuilder sb = new StringBuilder();
        
        sb.append("[");
        sb.append("'");
        sb.append("Worked Hours");
        sb.append("'");
        sb.append(",");
        sb.append(50);
        sb.append("]");
        sb.append(",");
        sb.append("[");
        sb.append("'");
        sb.append("nothing");
        sb.append("'");
        sb.append(",");
        sb.append(10);
        sb.append("]");
        sb.append(",");
        sb.append("[");
        sb.append("'");
        sb.append("something");
        sb.append("'");
        sb.append(",");
        sb.append(30);
        sb.append("]");
        
        
        
        return sb.toString();
    }
    
}
