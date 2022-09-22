/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.rest;

import com.itextpdf.text.Document;
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
    public Response getContactList(@QueryParam("id") String id) {
       
        try {
            File f = File.createTempFile("pref", ".pdf");
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdir();
            }
            if (!f.exists()) {

                f.mkdir();
            }
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(f));
            document.open();
            document.addTitle("My first PDF");
            document.addSubject("Using iText");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Lars Vogel");
            document.addCreator("Lars Vogel");
            Paragraph para = new Paragraph();
            para.add(new Paragraph("hi i am jaydip  "+id));
            document.add(para);
            
            document.close();

            f.deleteOnExit();
            return Response.ok(f, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition",  "attachment; filename=\""+"test.pdf"+"\"").build();

        } catch (Exception e) {
//            return "file not found " + e.getMessage();

        }
        return null;
    }
}
