package UI.model;


import domain.Doctor;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;

public class ModelSchedule {
    private ModelSchedule(){
        for(int i=0; i<36; i++){
            schdSlots[i] =-1;
        }

    }

    private final static ModelSchedule instance = new ModelSchedule();

    public static ModelSchedule getInstance() {
       return instance;
    }

    // for adding/editing events
    public int schdDay;
    public int schdMonth ;
    public int schdYear ;
    public Doctor schdDoctor;
    public int schdStartTimeIndex;
    public int schdEndTimeIndex;
    public boolean schdAdded = false;
    public int[] schdSlots = new int[36];

    // for the current calendar being worked on
    public int calendar_start = Calendar.getInstance().get(Calendar.YEAR);
    public int calendar_end = calendar_start+1;
    public String calendar_start_date;
    public String calendar_name;

    // All months in String
    public String[] monthsArr ={"January", "February","March","April", "May", "June", "July", "August",
            "September","October","November", "December"};

    // All time slots
    public String[] allTimeSlots = {"9:00AM", "9:15AM", "9:30AM", "9:45AM", "10:00AM", "10:15AM", "10:30AM","10:45AM",
            "11:00AM", "11:15AM", "11:30AM", "11:45AM", "12:00PM", "12:15PM","12:30PM",  "12:45PM",
            "1:00PM", "1:15PM","1:30PM",  "1:45PM", "2:00PM", "2:15PM","2:30PM",  "2:45PM",
            "3:00PM", "3:15PM","3:30PM",  "3:45PM", "4:00PM", "4:15PM","4:30PM",  "4:45PM",
            "5:00PM", "5:15PM","5:30PM",  "5:45PM"};

    public int getSlotInteger(String startTime){
        switch (startTime){
            case "9:00AM": return 1;
            case "9:15AM" :return 2;
            case "9:30AM": return 3;
            case "9:45AM": return 4;
            case "10:00AM": return 5;
            case "10:15AM": return 6;
             case "10:30AM": return 7;
             case "10:45AM": return 8;
             case "11:00AM": return 9;
             case "11:15AM": return 10;
             case "11:30AM": return 11;
             case "11:45AM": return 12;
             case "12:00PM": return 13;
             case "12:15PM": return 14;
             case "12:30PM": return 15;
            case "12:45PM": return 16;
            case "1:00PM": return 17;
            case "1:15PM": return 18;
            case "1:30PM": return 19;
            case "1:45PM": return 20;
            case "2:00PM": return 21;
            case "2:15PM": return 22;
            case "2:30PM": return 23;
            case "2:45PM": return 24;
            case "3:00PM": return 25;
            case "3:15PM": return 26;
            case "3:30PM": return 27;
            case "3:45PM": return 28;
            case "4:00PM": return 29;
            case "4:15PM": return 30;
            case "4:30PM": return 31;
            case "4:45PM": return 32;
            case "5:00PM": return 33;
            case "5:15PM": return 34;
            case "5:30PM": return 35;
            case "5:45PM": return 36;
            default: return -1;

        }
    }



}

