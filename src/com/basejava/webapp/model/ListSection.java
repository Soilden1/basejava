package com.basejava.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private final List<String> list;

    public ListSection(List<String> list) {
        Objects.requireNonNull(list, "companies must not be null");
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String string : list) {
            sb.append(string).append("\n");
        }
        return sb.toString();
    }
}
