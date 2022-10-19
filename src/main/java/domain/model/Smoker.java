package domain.model;

import domain.exceptions.DomainException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Smoker implements java.io.Serializable{

    private String fName, lName;
    private LocalDate startDate;

    private int id;

    public Smoker () {} // Default constructor

    public Smoker (String fName, String lName, LocalDate startDate){
        setfName(fName);
        setlName(lName);
        setStartDate(startDate);
    }

    public String toString(){
        return this.fName + " " + this.lName + " on " + this.startDate.toString();
    }

    public int calculateDaysElapsed() {
        return ((int) (LocalDate.now().toEpochDay() - this.startDate.toEpochDay()));
    }

    public void setfName(String fName) {
        if (fName == null || fName.trim().isEmpty()) {
            throw new DomainException("Please enter your first name.");
        }

        /*
        * Java Strings have a built-in .matches method which does not do what it says it does
        * The Pattern class compiles regex expressions and allows one to check a string for the pattern
        * \S    : simply matches any non-whitespace character
        * *     : checks for zero or more occurrences of the previous "pattern" or "token"
        * [0-9] : checks whether the string contains any digit
        * +     : checks for one or more occurrences of the previous ... */

        if (Pattern.matches("\\S*[0-9]+\\S*", fName)){
            throw new DomainException("Please enter a first name containing no numeric values.");
        }
        this.fName = fName;
    }

    public String getfName() { return this.fName; }

    public void setlName(String lName) {
        if (lName == null || lName.trim().isEmpty()) { //isBlank gave an error i couldn't figure out
            throw new DomainException("Please enter your last name.");
        }
        if (Pattern.matches("\\S*[0-9]+\\S*", lName)){
            throw new DomainException("Please enter a last name containing no numeric values.");
        }
        this.lName = lName;
    }

    public String getlName() { return this.lName; }

    public void setStartDate(LocalDate startDate) {
        if (startDate.isAfter(LocalDate.now())){
            throw new DomainException("Start date cannot be after the current date.");
        }
        this.startDate = startDate;
    }

    public LocalDate getStartDate() { return this.startDate; }

    public void setId(int id) { this.id = id; }

    public int getId() { return id; }
}
