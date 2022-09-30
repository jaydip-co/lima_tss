/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.rest;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tss.dto.Contract;

import tss.remote.ContractRemote;

/**
 *
 * @author savaliya
 */
@Stateless
@LocalBean
@Path("contracts")
public class TssRestEndpoint {

    @EJB
    ContractRemote cr;

    @Path("list")
    @GET
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response getConract(@QueryParam("id") String id) {

        try {

            Contract c = cr.getContractWithUuid(id);

            File f = File.createTempFile("pref", ".pdf");
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdir();
            }
            if (!f.exists()) {

                f.mkdir();
            }
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(f));

            makeFile(document, c);
            
            f.deleteOnExit();
            return Response.ok(f, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + c.getName() + ".pdf" + "\"").build();

        } catch (Exception e) {
//            return "file not found " + e.getMessage();
            
        }
        return null;
    }

    private void makeFile(Document d, Contract c) throws DocumentException {
                    d.addTitle("Contract Page for "+c.getName());
            d.addSubject("Cover Page");
            d.addCreator("TSS");
        Font title = new Font();
        title.setSize(20);
          Font appName = new Font();
        appName.setSize(24);
        
        Font title2 = new Font();
        title2.setSize(18);
        
        title2.setStyle("noraml");

        Font title3 = new Font();
        title3.setSize(14);

        title3.setStyle("tiny");
        d.open();
        
        Paragraph appTitle = new Paragraph("Time Sheet System");
        appTitle.setFont(appName);
        d.add(appTitle);
        d.add(new Paragraph("\n"));
        Paragraph para = new Paragraph();
        para.setFont(title);
        para.add(new Paragraph("Contract Name :- " + c.getName()));
        para.add(new Paragraph("\n"));
        d.add(para);
        Paragraph para2 = new Paragraph();
        para2.setFont(title2);
        para2.add(new Paragraph("Details :-"));
        d.add(para2);
        d.add(new Paragraph("\n"));
        Paragraph details = new Paragraph();
        details.setFont(title3);

        details.add(new Paragraph(
                "Start Date :- " + c.getStartDate()
        ));
        details.add(new Paragraph(
                "End Date :- " + c.getEndDate()
        ));
        details.add(new Paragraph(
                "Status :- " + c.getStatus()
        ));
        
        details.add(new Paragraph(
                "Employee :- " + c.getEmployee().getFirstName()
        ));
        details.add(new Paragraph(
                "Supervisor :- " + c.getEmployee().getFirstName()
        ));
        String sec = c.getSecretaries().stream().map(e -> " " + e.getFirstName() + ",").reduce("", (a, b) -> a + b)+"";
        

        details.add(new Paragraph(
                "Secretary :- " + sec.substring(0, sec.length() - 1)
        ));
        String ass = c.getAssistants().stream().map(e -> " " + e.getFirstName() + ",").reduce("", (a, b) -> a + b)+"";

        details.add(new Paragraph(
                "Assisatants :- " + ass.substring(0, ass.length() - 1)
        ));
        details.add(new Paragraph(
                "Due Hours :- " + c.getHourDue()
        ));
        details.add(new Paragraph(
                "Vacation Hours :- " + (int) c.getVacationHour()
                
        ));
        details.add(new Paragraph(
                "Frequency :- " + c.getFrequency()
        ));
        details.add(new Paragraph(
                "Hours Per Week :- " + c.getHoursPerWeek()
        ));
       

        d.add(details);
        d.close();
    }
}
