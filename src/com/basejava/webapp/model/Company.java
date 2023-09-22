package com.basejava.webapp.model;

import com.basejava.webapp.util.DateUtil;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static com.basejava.webapp.util.DateUtil.NOW;

public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Link homePage;
    private List<Period> periods = new ArrayList<>();

    public Company(String name, String url, Period... periods) {
        this(new Link(name, url), Arrays.asList(periods));
    }

    public Company(Link homePage, List<Period> period) {
        this.homePage = homePage;
        this.periods = period;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(homePage);
        for (Period period : periods) {
            sb.append(period);
        }
        return sb.toString();
    }

    public static class Period implements Serializable {

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Period(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!startDate.equals(period.startDate)) return false;
            if (!endDate.equals(period.endDate)) return false;
            if (!title.equals(period.title)) return false;
            return Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return startDate + " - " + endDate + "\n" + title + "\n" + description;
        }
    }
}
