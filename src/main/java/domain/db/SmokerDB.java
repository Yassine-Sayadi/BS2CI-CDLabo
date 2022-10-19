package domain.db;

import domain.model.Smoker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SmokerDB {

    private int sequence = 0;
    private List<Smoker> smokers = new ArrayList<>();

    public SmokerDB(){
        // populating database with placeholder values
        // localdate can be defined using .of() method

        try {
            this.add(new Smoker("Pobo", "Schaapmens", LocalDate.now().minusDays(200)));
            this.add(new Smoker("Bassine", "Banani", LocalDate.of(2022, 2, 24)));
            this.add(new Smoker("Peter", "Kingsman", LocalDate.of(2022, 1, 4)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void add(Smoker smoker){
        this.sequence++;
        smoker.setId(this.sequence);
        this.smokers.add(smoker);
    }

    public void delete(int id){
        if (find(id) != null){
            this.smokers.remove(find(id));
        }
    }

    public Smoker find(int id) {
        for (Smoker s : this.smokers) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public ArrayList<Smoker> find(String name) {
        ArrayList<Smoker> result = new ArrayList<>();

        for (Smoker s : this.smokers) {
            if (name != null
                    && !name.trim().isEmpty()
                    /* this last line is the "search engine"
                    *  it concats the first and last name of every database entry
                    *  then it turns all characters to lowercase to eliminate case sensitivity
                    *  index of returns either the index of where the argument appears in the string it's being called on
                    *  or -1 if it doesn't appear in that string. So checking for a value greater than -1 (0, 1, ...)
                    *  is enough to know whether or not a match was found.
                    * */
                    && s.getfName().toLowerCase().concat(s.getlName().toLowerCase()).indexOf(name.toLowerCase()) >-1) {
                result.add(s);
            }
        }
        return (result.size() == 0)? null: result;
    }

    public float calculateAverageDaysElapsed(){
        float avg = 0.00f;

        if (this.smokers.size() != 0) {
            for (Smoker s : this.getSmokers()) {
                avg += s.calculateDaysElapsed();
            }

            return avg / this.getSmokers().size();
        } else {
            return 0;
        }

    }

    // Getters and setters for the javabean convention (fields/properties are DEFINED by their getter and setter)

    public int getSequence() { return this.sequence; }
    public void setSequence(int sequence) { this.sequence = sequence; }

    public List<Smoker> getSmokers(){
        return this.smokers;
    }
    public void setSmokers(ArrayList<Smoker> smokers) { this.smokers = smokers; }

}
