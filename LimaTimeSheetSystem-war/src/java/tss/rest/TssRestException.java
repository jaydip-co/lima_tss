/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.rest;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author savaliya
 */
@ApplicationException
public class TssRestException extends WebApplicationException{
      private static final long serialVersionUID = 6390905608085584603L;

    public TssRestException(String message, Response.Status status) {
        super(message, status);
    }
    
}
