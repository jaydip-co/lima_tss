/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.web;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/**
 *
 * @author savaliya
 */
@SessionScoped
@Named(value = "lc")
public class LocaleBean  implements Serializable {
      private static final long serialVersionUID = 5167565954217609L;

    private Locale userLocale;

    public Locale getUserLocale() {
        if (userLocale == null) {
            userLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }
        if (userLocale == null) {
            userLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
        }
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

    public void selectEnglish() {
        userLocale = Locale.ENGLISH;
    }

    public void selectGerman() {
        userLocale = Locale.GERMAN;
    }

    public Date getCurrentDate() {
        return new Date();
    }
}
