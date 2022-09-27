/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.logic;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public static double getHoursDue(LocalDate startDate, LocalDate endDate,
            double hoursPerWeek,
            int workingDaysPerWeek
    ) {
        Period p = Period.between(startDate, endDate);
        List<LocalDate> dates = new ArrayList<>();
        
        LocalDate temp = startDate;
        
        while(temp.isBefore(endDate.plusDays(1))){
            if(temp.getDayOfWeek().getValue() <= workingDaysPerWeek){
                dates.add(temp);
            }
            temp = temp.plusDays(1);
        }
        
        return (double) (dates.size() - holidaysBetween(startDate, endDate, workingDaysPerWeek)) * hoursPerWeek / (double) workingDaysPerWeek;
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

    //// Method to get number of Holidays Beetween two dates ////
    public static int holidaysBetween(LocalDate start, LocalDate end,int workingDaysinWeek) {
        List<LocalDate> dates = publicHolidays(start.getYear());
        
        return dates.stream().filter(e ->
                e.getDayOfWeek().getValue() <= workingDaysinWeek 
                && (e.equals(start) || e.equals(end) || (e.isAfter(start) 
                        && e.isBefore(end))))
                .collect(Collectors.toList()).size();
    }
     public List<LocalDate> holidaysBetween(LocalDate start, LocalDate end,boolean v) {
        List<LocalDate> dates = publicHolidays(start.getYear());
        return dates;
    }


    ////// Method to get easter day of given year ///////
    public static LocalDate easterHoliday(int year) {
        int g = year % 19;
        int c = year / 100;
        int h = (c - (int) (c / 4) - (int) ((8 * c + 13) / 25)
                + 19 * g + 15) % 30;
        int i = h - (int) (h / 28) * (1 - (int) (h / 28)
                * (int) (29 / (h + 1)) * (int) ((21 - g) / 11));

        int day = i - ((year + (int) (year / 4)
                + i + 2 - c + (int) (c / 4)) % 7) + 28;
        int month = 3;

        if (day > 31) {
            month++;
            day -= 31;
        }

        return LocalDate.of(year, month, day);
    }

    /// Method to get Holidays of given year //// 
    public static List<LocalDate> publicHolidays(int year) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate Ostern = easterHoliday(year);
        LocalDate NeuJahr = LocalDate.of(year, 01, 01);
        Period p1 = Period.ofDays(2);
        Period p2 = Period.ofDays(1);
        Period p3 = Period.ofDays(39);
        Period p4 = Period.ofDays(50);
        Period p5 = Period.ofDays(10);
        LocalDate KarFreitag = Ostern.minus(p1);
        LocalDate OsterMontag = Ostern.plus(p2);
        LocalDate TagDerArbeit = LocalDate.of(year, 05, 01);
        LocalDate ChristiHimmelfahrt = Ostern.plus(p3);
        LocalDate PfingstMontag = Ostern.plus(p4);
        LocalDate Fronleichnam = PfingstMontag.plus(p5);
        LocalDate TagDerDeutschenEinheit = LocalDate.of(year, 10, 03);
        LocalDate AllerHeiligen = LocalDate.of(year, 11, 01);
        LocalDate Weihnachten = LocalDate.of(year, 12, 25);
        LocalDate ZweiterWeihnachtsFeiertag = LocalDate.of(year, 12, 26);
        dates.add(NeuJahr);
        dates.add(KarFreitag);
        dates.add(Ostern);
        dates.add(OsterMontag);
        dates.add(TagDerArbeit);
        dates.add(ChristiHimmelfahrt);
        dates.add(PfingstMontag);
        dates.add(Fronleichnam);
        dates.add(TagDerDeutschenEinheit);
        dates.add(AllerHeiligen);
        dates.add(Weihnachten);
        dates.add(ZweiterWeihnachtsFeiertag);

        return dates;

    }

}
