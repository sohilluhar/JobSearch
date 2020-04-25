package com.example.job.Model;

import java.util.List;

public class EventGoing {

    private List<String> goingppl;

    public EventGoing(List<String> goingppl) {
        this.goingppl = goingppl;
    }

    public EventGoing() {
    }

    public List<String> getGoingppl() {
        return goingppl;
    }

    public void setGoingppl(List<String> goingppl) {
        this.goingppl = goingppl;
    }

}
