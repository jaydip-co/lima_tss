/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.logic;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import tss.enums.TimeSheetFrequency;

/**
 *
 * @author savaliya
 */
public class CalculationLogic {

    public static double calculateVacationHour(
            LocalDate startDate,
            LocalDate endDate,
            double hoursPerWeek,
            int workingDaysPerWeek,
            int vacationDaysPerYear
    ) {

        double durationOfContract = Period.between(startDate, endDate).getMonths() + 1;
        return (durationOfContract / (double) 12) * (hoursPerWeek / (double) workingDaysPerWeek)
                * vacationDaysPerYear;

    }
    
    public static double getHoursDue(LocalDate startDate,LocalDate endDate,
            double hoursPerWeek,
            int workingDaysPerWeek
           ) {
        Period p = Period.between(startDate, endDate);
        
     return (double) (p.getDays() + 1) * hoursPerWeek/ (double)workingDaysPerWeek;
    }

    public static List<TsTimePeriod> getWeeksBetween(LocalDate start,
            LocalDate end,
            TimeSheetFrequency tsf) {

        if (tsf == TimeSheetFrequency.WEEKLY) {
            List<TsTimePeriod> weeks = new ArrayList<>();
            int startingDate = start.getDayOfWeek().getValue();
            LocalDate d = start;
            LocalDate d1 = d.plusDays(7 - startingDate);
            weeks.add(new TsTimePeriod(d, d1));
            d = d1.plusDays(1);
            while (d.isBefore(end) || d.equals(end)) {
                if (d.plusDays(6).isBefore(end)) {
                    weeks.add(new TsTimePeriod(d, d.plusDays(6)));
                    d = d.plusDays(7);
                } else if (d.plusDays(6).equals(d)) {
                    weeks.add(new TsTimePeriod(d, end));
                } else {
                    weeks.add(new TsTimePeriod(d, end));
                    d = d.plusDays(7);
                }
            }
            return weeks;
        } else {

            List<TsTimePeriod> months = new ArrayList<>();

            LocalDate startingDate = start;
            while (startingDate.isBefore(end)) {
                if (startingDate.plusMonths(1).isBefore(end)) {
                    LocalDate en = LocalDate.of(start.getYear(), start.getMonthValue(), start.getMonth().length(start.isLeapYear()));
                    months.add(new TsTimePeriod(startingDate, en));
                } else {
                    months.add(new TsTimePeriod(startingDate, end));
                }
                startingDate = startingDate.plusMonths(1);
            }

            return months;
        }
    }

}
