/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.lcl;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author savaliya
 */
public class Message {
      private static final String MESSAGE_BUNDLE = "tss.lcl.message";

    public static String getMessage(String key, Object... arguments) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle.getBundle(MESSAGE_BUNDLE, fc.getViewRoot().getLocale());
        try {
            String message = bundle.getString(key);
            if (message == null) {
                return "???" + key + "???";
            }
            return MessageFormat.format(message, arguments);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }
}
