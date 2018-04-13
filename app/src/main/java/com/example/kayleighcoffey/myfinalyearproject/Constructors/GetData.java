package com.example.kayleighcoffey.myfinalyearproject.Constructors;


public class GetData {

    private String myDay;
    private String myDate;
    private String mySteps;
    private String mySleep;
    private String myDrink;
    private String myMood;


    public GetData(){
        // needed for firebase
    }

    public GetData( String date, String day, String Steps, String Sleep, String Drink, String Mood )
    {

        myDate = date;
        myDay = day;
        mySteps = Steps;
        mySleep = Sleep;
        myDrink = Drink;
        myMood = Mood;

    }

    ///// DATE
    public void setDate(String date) {
        myDate = date;
    }
    public String getDate() {
        return myDate;
    }
    ///// DAY
    public String getmDay() {
        return myDay;
    }
    public void setmDay(String mDay) {
        this.myDay = mDay;
    }

    ////// STEPS
    public String getMySteps() {
        return mySteps;
    }

    public void setMySteps(String mySteps) {
        this.mySteps = mySteps;
    }

    ////// SLEEP
    public String getMySleep() {
        return mySleep;
    }

    public void setMySleep(String mySleep) {
        this.mySleep = mySleep;
    }

    /////// DRINK
    public String getMyDrink() {
        return myDrink;
    }

    public void setMyDrink(String myDrink) {
        this.myDrink = myDrink;
    }

    ///// MOOD
    public String getMyMood() {
        return myMood;
    }

    public void setMyMood(String myMood) {
        this.myMood = myMood;
    }
}
