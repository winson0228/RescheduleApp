/**
 * Created by winson on 2017-05-19.
 */
public class Appointment implements Comparable<Appointment> {

    private String dayweek;
    private int timeStart;
    private int timeEnd;
    private String dsp;
    private boolean isCompleted;
    private boolean isStacked = false;
    private int priority;
    private enum Occasions {
        Work, Family, Exercise, Eating
    }
    private Occasions occasion;
    private String location;

    public Appointment(int timeStart, int timeEnd, String dsp, String location, int priority, Occasions occasions) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dayweek = dayweek;
        this.dsp = dsp;
        this.location = location;
        this.isCompleted = false;
        this.priority = priority;
        this.occasion = occasions;
    }

    //getters

    public int getTimeStart() {
        return this.timeStart;
    }

    public int getTimeEnd() {
        return this.timeEnd;
    }

    public String getDayweek() { return this.dayweek; }

    public String getDsp() { return this.dsp; }

    public String getLocation() { return this.location; }

    public boolean getCompletionStatus() {
        return this.isCompleted;
    }

    public boolean getStackedStatus() {
        return this.isStacked;
    }

    public int getPriority() {
        return this.priority;
    }

    public Occasions getOccasion() {
        return this.occasion;
    }

    //setters

    public void setTimeStart(int time) {
        this.timeStart = time;
    }

    public void setTimeEnd(int time) {
        this.timeEnd = time;
    }

    public void setDsp(String str) {
        this.dsp = str;
    }

    public void setLocation(String loc) {
        this.location = loc;
    }

    public void setCompleted() {
        this.isCompleted = !this.isCompleted;
    }

    public void setStacked() {
        this.isStacked = !this.isStacked;
    }

    public void setPriority(int num) {
        this.priority = num;
    }

    public void setOccasion(Occasions occasion) {
        this.occasion = occasion;
    }

    @Override
    public int compareTo(Appointment apt) {
        return this.getTimeStart() - apt.getTimeStart();

    }
}
