
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import tss.logic.CalculationLogic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author savaliya
 */
public class LogicTest {
    public static void main(String[] args) {
        CalculationLogic lg = new CalculationLogic();
        List<LocalDate> dates = lg.holidaysBetween(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31),true);
        dates.forEach(e -> System.err.println(e.getDayOfWeek().toString() + e.toString()));
        
        LocalDate start = LocalDate.parse("2022-09-01", DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse("2022-09-30", DateTimeFormatter.ISO_LOCAL_DATE);
        double hours = CalculationLogic.getHoursDue(start,end,40,5);
        System.err.println("Days :- "+ hours);
        int a = lg.holidaysBetween(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31),5);
        System.err.println("asdsad" + a);
    }
}
