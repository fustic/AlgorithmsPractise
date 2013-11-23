package prev;

import java.util.Date;

public class Helpers {
    public static void getDiffTime(Date startDate, String message){
        Date stoped=new Date();
        long diff=stoped.getTime() - startDate.getTime();
        System.out.println(message + " " + diff + " milisec");
    }
}
