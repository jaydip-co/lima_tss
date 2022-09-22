/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author savaliya
 */
public class BaseAccess {
    
    @PersistenceContext(unitName = "LimaTimeSheetSystem-ejbPU")
    EntityManager em;
    
}
