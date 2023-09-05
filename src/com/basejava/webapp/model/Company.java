package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company {

    private final String name;
    private final String url;
    private final List<Period> period;

    public Company(String name, String url, List<Period> period) {
        Objects.requireNonNull(name);
        this.name = name;
        this.url = url;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Period> getPeriod() {
        return period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!name.equals(company.name)) return false;
        if (!url.equals(company.url)) return false;
        return period.equals(company.period);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + period.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(name).append("\n").append(url);
        for (Period period : period) {
            sb.append(period);
        }
        return sb.toString();
    }
}
