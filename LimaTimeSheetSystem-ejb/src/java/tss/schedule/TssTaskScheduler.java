/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.schedule;

import com.sun.istack.logging.Logger;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tss.UserRoles;

import tss.dao.TimeSheetAccess;
import tss.entities.PersonEntity;
import tss.entities.TimeSheetEntity;
import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@Singleton
public class TssTaskScheduler {

    @EJB
    ContractRemote cr;

    @EJB
    TimeSheetAccess tsa;

    @Resource(lookup = "mail/contacts-mail")
    private Session mailSession;

    @PostConstruct
    public void initialize() {
        Logger.getLogger("tss", TssTaskScheduler.class)
                .log(Level.SEVERE, "Started...");
    }

    @Schedule(minute = "15", hour = "6", second = "0", persistent = false)
    public void addData() throws InterruptedException {
        List<TimeSheetEntity> pendingEntries = tsa.getAllPendingEntry();

        Map<PersonEntity, Set<TimeSheetEntity>> m = new HashMap<>();

        pendingEntries.forEach(e -> {
            PersonEntity pe = e.getParent().getContractUserRoles().stream().filter(et -> et.getRole().equals(UserRoles.EMPLOYEE))
                    .findFirst().get().getPerson();
            if (m.get(pe) == null) {
                Set<TimeSheetEntity> times = new HashSet<>();
                times.add(e);
                m.put(pe, times);
            }
            else{
                Set<TimeSheetEntity> times = m.get(pe);
                times.add(e);
                m.put(pe, times);
            }
        });

       
            m.keySet().forEach(e -> {
                try {
                    Message msg = new MimeMessage(mailSession);
                    msg.setSubject("Reminders for Time Sheet");
                    msg.setSentDate(new Date());
                    msg.setFrom(InternetAddress.parse(
                            "nobody@tss.com",
                            false)[0]);
                    msg.setRecipients(
                            Message.RecipientType.TO,
                            InternetAddress.parse(
                                    e.getUserName(),
                                    false)
                    );
                    StringBuilder sb = new StringBuilder();
                    sb.append("You have Pending sign in Folowing contract :-");
                    sb.append("\n");
                    m.get(e).forEach(et -> {
                        sb.append(et.getParent().getContractName());
                        sb.append(":- ");
                        sb.append(et.getStartDate());
                        sb.append("-");
                        sb.append(et.getEndDate());
                        sb.append("\n");
                    });
                    msg.setText(sb.toString());
                    Transport.send(msg);
                } catch (MessagingException ex) {
                    Logger.getLogger(TssTaskScheduler.class.getEnclosingClass())
                            .log(Level.SEVERE, null, ex);
                }
            });
            
          Logger.getLogger("tss", TssTaskScheduler.class)
                .log(Level.SEVERE, "Remainder sent successfully");
    }
}
