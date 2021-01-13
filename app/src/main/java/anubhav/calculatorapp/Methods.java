package anubhav.calculatorapp;

import java.util.Calendar;

public class Methods {
    public static String getCurrentDate(){
        Calendar cc = Calendar.getInstance();
        int year = cc.get(Calendar.YEAR);
        int month = cc.get(Calendar.MONTH)+1;
        int mDay = cc.get(Calendar.DAY_OF_MONTH);
        String date = year+"/"+month+"/"+mDay;
        return date;
    }
}
