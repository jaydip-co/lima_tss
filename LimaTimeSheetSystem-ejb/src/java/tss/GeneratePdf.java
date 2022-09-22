/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss;



import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.ws.Response;

/**
 *
 * @author savaliya
 */
@Path("files")
@Stateless
public class GeneratePdf {
    
    @GET
    public String getFile(){
        return "jaydip";
    }
    
}
