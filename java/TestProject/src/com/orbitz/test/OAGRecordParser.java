package com.orbitz.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 *  Represents a record in the OAG schedule data file
 *
 * <p>(c) 2000-02 Orbitz, LLC. All Rights Reserved.
 * @version $Id$
 */

public class OAGRecordParser {
    // ** STATIC/FINAL DATA ***************************************************
    public static final String CLASSID   =
        "$Id$";
    public static final String CLASSNAME = "[OagScheduleCacheImpl]";

    // ** PRIVATE DATA ********************************************************
    private Calendar  _start;
    private Calendar  _end;
    private Set       _daysOfOperation;
    private String    _carrier;
    private String    _flightNum;
    private String    _itinVariation;
    private String    _legSequence;
    private String    _origAirport;
    private String    _origTerminal;
    private String    _origTime;
    private String    _destAirport;
    private String    _destTerminal;
    private String    _destTime;
    private String    _aircraft;

    // ** CONSTRUCTORS ********************************************************

    /**
     * Construct a OAG Schedule Record from a line of SIM data.
     * @param str A Flight Leg record from the SIM data file.
     * @throws IOException 
     */
    
    public static void main(String s[]) throws IOException{
    	OAGRecordParser oag = new OAGRecordParser();
    	
    	 FileReader fileReader = new FileReader("/Users/mkchakravarti/Documents/project/oag/Msimfile_temp");
         
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         
         List<String> lines = new ArrayList<String>();
         String line = null;
         while ((line = bufferedReader.readLine()) != null) {
             lines.add(line);
             oag.parseData(line);
         }
    }
    public void parseData(String str) {
    	//str = "3 AA    12501J25AUG1225AUG12     6  JFK09000900-04008 LAX12101210-07004 762FAJRDIYBHKMVWNSQLGO";
        _carrier = str.substring(2,5).trim();
        _flightNum = str.substring(5,9).trim();

        _itinVariation = str.substring(9,11);
        _legSequence = str.substring(11,13);

        _origAirport = str.substring(36,39).trim();
        _origTime = str.substring(43,47);
        _origTerminal = str.substring(52,54).trim();

        _destAirport = str.substring(54,57).trim();
        _destTime = str.substring(57,61);
        _destTerminal = str.substring(70,72).trim();

        _aircraft = str.substring(72,75).trim();

        calculatePeriodOfOperation(str);
        calculateDaysOfOperation(str);
        if(!this.toString().isEmpty())
        System.out.println(this.toString());
    }

    // ** PUBLIC METHODS ******************************************************

    /**
     * Default String-ifier for this class.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        if(_start !=null){
        buf.append("Start Date: " + _start.getTime() + "|");
        if ( _end==null ) {
            buf.append( "End Data: n/a\n" );
        } else {
            buf.append("End Date: " + _end.getTime() + "|");
        }
        buf.append("Days Of Operation: ");
        Iterator iter = _daysOfOperation.iterator();
        while (iter.hasNext()) {
            buf.append(iter.next() + " ");
        }
        buf.append("|");
        buf.append("Origin Airport: " + _origAirport + "|");
        buf.append("Origin Terminal: " + _origTerminal + "|");
        buf.append("Origin Time: " + _origTime + "|");
        buf.append("Destination Airport: " + _destAirport + "|");
        buf.append("Destination Terminal: " + _destTerminal + "|");
        buf.append("Destination Time: " + _destTime + "|");
        buf.append("Aircraft: " + _aircraft + "|");
        buf.append("Itinerary Variation: " + _itinVariation + "|");
        buf.append("Leg Sequence: " + _legSequence + "|");
        }
        return buf.toString();
    }

    /**
     * Get the start date of operation for this record.
     */
    public Calendar getStartDate() {
        return _start;
    }

    /**
     * Get the end date of operation for this record.
     */
    public Calendar getEndDate() {
        return _end;
    }

    /**
     * Get the days that this record is valid for.
     * @return A set of Calendar.MONDAY..Calendar.SUNDAY records.
     */
    public Set getDaysOfOperation() {
        return _daysOfOperation;
    }

    /**
     * Get the Airline (Carrier) name of this record.
     */
    public String getCarrier() {
        return _carrier;
    }

    /**
     * Get the flight number of this record.
     */
    public String getFlightNum() {
        return _flightNum;
    }

    /**
     * Get the Origin (departure) time of this record.
     */
    public String getOriginTime() {
        return _origTime;
    }

    /**
     * Get the Destination (arrival) time of this record.
     */
    public String getDestinationTime() {
        return _destTime;
    }

    /**
     * Get a String representation of the type of aircraft of this record.
     */
    public String getAircraft() {
        return _aircraft;
    }

    /**
     * Returns the itinerary variation number.
     */
    public String getVariation() {
        return _itinVariation;
    }

    /**
     * Returns the leg sequence with the current variation.
     */
    public String getLeg(){
        return _legSequence;
    }

    /**
     * Get the Origin (departure) airport code.
     */
    public String getOriginAirportCode() {
        return _origAirport;
    }

    /**
     * Get the Destination (arrival) airport code.
     */
    public String getDestinationAirportCode() {
        return _destAirport;
    }

    /**
     * Get a FlifoLegDataImpl object built with the OagScheduleRecord data.
     */
    

    /**
     * Determines whether the flight operates on the departure date.
     * @param departureDate The date of departure.
     * @return true if the flight is flown on the departure date,
     * false otherwise.
     */
    public boolean isInOperationOnThisDay(Calendar departureDate) {

        Calendar tmp = Calendar.getInstance();
        tmp.set(Calendar.DAY_OF_MONTH,
                departureDate.get(Calendar.DAY_OF_MONTH));
        tmp.set(Calendar.MONTH, departureDate.get(Calendar.MONTH));
        tmp.set(Calendar.YEAR, departureDate.get(Calendar.YEAR));
        tmp.clear(Calendar.HOUR);
        tmp.clear(Calendar.HOUR_OF_DAY);
        tmp.clear(Calendar.MINUTE);
        tmp.clear(Calendar.SECOND);
        tmp.clear(Calendar.MILLISECOND);

        // If OAG does not provide a start or end date, we have to assume that
        // there is no beginning or ending bound.
        if ((_start == null || _start.before(tmp) || _start.equals(tmp)) &&
            (_end == null || _end.after(tmp) || _end.equals(tmp))) {

            return true;
        } else {
            return false;
        }
    }

    // ** PRIVATE METHODS *****************************************************

    private void calculateDaysOfOperation(String str) {
        _daysOfOperation = new HashSet();

        str = str.substring(28,35);

        if (str.indexOf("1") != -1) {
            _daysOfOperation.add(new Integer(Calendar.MONDAY));
        }
        if (str.indexOf("2") != -1) {
            _daysOfOperation.add(new Integer(Calendar.TUESDAY));
        }
        if (str.indexOf("3") != -1) {
            _daysOfOperation.add(new Integer(Calendar.WEDNESDAY));
        }
        if (str.indexOf("4") != -1) {
            _daysOfOperation.add(new Integer(Calendar.THURSDAY));
        }
        if (str.indexOf("5") != -1) {
            _daysOfOperation.add(new Integer(Calendar.FRIDAY));
        }
        if (str.indexOf("6") != -1) {
            _daysOfOperation.add(new Integer(Calendar.SATURDAY));
        }
        if (str.indexOf("7") != -1) {
            _daysOfOperation.add(new Integer(Calendar.SUNDAY));
        }
    }

    private void calculatePeriodOfOperation(String str) {
        _start = parseTime(str.substring(14,21));
        _end = parseTime(str.substring(21,28));
    }

    private Calendar parseTime(String timeStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
        Calendar cal = Calendar.getInstance();

        try {
            Date dTmp = sdf.parse(timeStr);
            cal.setTime(dTmp);

        } catch (ParseException pe) {
            return null;
        }

        return cal;
    }
}
