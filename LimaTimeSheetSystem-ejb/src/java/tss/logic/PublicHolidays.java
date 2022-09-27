/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tss.logic;

/**
 *
 * @author savaliya
 */
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter; 
import java.time.Period;

public class PublicHolidays {
    public static int[] EasterSunday(int year, int month, int day)
    {
        int g = year % 19;
        int c = year / 100;
        int h =  (c - (int)(c / 4) - (int)((8 * c + 13) / 25) 
                                        + 19 * g + 15) % 30;
        int i = h - (int)(h / 28) * (1 - (int)(h / 28) * 
                    (int)(29 / (h + 1)) * (int)((21 - g) / 11));
        int[] ans = new int[2];
        
        day   = i - ((year + (int)(year / 4) + 
                  i + 2 - c + (int)(c / 4)) % 7) + 28;
        month = 3;

        if (day > 31)
        {
            month++;
            day -= 31;
        }
        ans[0] = month;
        ans[1] = day;
        return ans;
    }
    
    public static LocalDate EasterSunday(int year)
    {
        int month = 0;
        int day = 0;
        int[] ans = new int[2];
        ans = EasterSunday(year, month, day);
        Integer m = ans[0];
        Integer d = ans[1];
        Integer y = year;
        String Eastermonth = new String();
        String Easterday = new String();
        if(ans[0]<10)
        {
            Eastermonth = "0"+m.toString();
        }
        else
        {
            Eastermonth = m.toString();
        }
        if(ans[1]<10)
        {
            Easterday = "0"+d.toString();
        }
        else
        {
            Easterday = d.toString();
        }
        String Easter = y.toString()+Eastermonth+Easterday;
        LocalDate date = LocalDate.parse(Easter, DateTimeFormatter.BASIC_ISO_DATE);
        return date;
    }
    
    public static void main(String args[]) {
      int year = 2022;
      LocalDate Ostern = EasterSunday(year);
      LocalDate NeuJahr = LocalDate.of(year,01,01);
      Period p1 = Period.ofDays(2);
      Period p2 = Period.ofDays(1);
      Period p3 = Period.ofDays(39);
      Period p4 = Period.ofDays(50);
      Period p5 = Period.ofDays(10);
      LocalDate KarFreitag = Ostern.minus(p1);
      LocalDate OsterMontag = Ostern.plus(p2);
      LocalDate TagDerArbeit = LocalDate.of(year,05,01);
      LocalDate ChristiHimmelfahrt = Ostern.plus(p3);
      LocalDate PfingstMontag = Ostern.plus(p4);
      LocalDate Fronleichnam = PfingstMontag.plus(p5);
      LocalDate TagDerDeutschenEinheit = LocalDate.of(year,10,03);
      LocalDate AllerHeiligen = LocalDate.of(year,11,01);
      LocalDate Weihnachten = LocalDate.of(year,12,25);
      LocalDate ZweiterWeihnachtsFeiertag = LocalDate.of(year,12,26);
      LocalDate[] GesetzlicheFeiertage = new LocalDate[12];
      int i = 0;
      GesetzlicheFeiertage[i++] = NeuJahr;
      GesetzlicheFeiertage[i++] = KarFreitag;
      GesetzlicheFeiertage[i++] = Ostern;
      GesetzlicheFeiertage[i++] = OsterMontag;
      GesetzlicheFeiertage[i++] = TagDerArbeit;
      GesetzlicheFeiertage[i++] = ChristiHimmelfahrt;
      GesetzlicheFeiertage[i++] = PfingstMontag;
      GesetzlicheFeiertage[i++] = Fronleichnam;
      GesetzlicheFeiertage[i++] = TagDerDeutschenEinheit;
      GesetzlicheFeiertage[i++] = AllerHeiligen;
      GesetzlicheFeiertage[i++] = Weihnachten;
      GesetzlicheFeiertage[i++] = ZweiterWeihnachtsFeiertag;
      for(int j=0;j<GesetzlicheFeiertage.length;j++)
      {
          System.out.println(GesetzlicheFeiertage[j]);
      }
    }
}
