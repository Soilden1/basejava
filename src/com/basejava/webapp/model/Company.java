package com.basejava.webapp.model;

import java.util.List;

public class Company {

    String name;
    String website;
    List<Period> period;

    public Company(String name, String website, List<Period> period) {
        this.name = name;
        this.website = website;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(name).append("\n").append(website);
        for (Period period : period) {
            sb.append(period);
        }
        return sb.toString();
    }
}
