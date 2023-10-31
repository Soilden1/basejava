package com.basejava.webapp;

import java.util.*;

import static java.util.stream.Collectors.*;

public class Streams {

    public static void main(String[] args) {
        // minValue
        int[] ints = new int[] {1, 2, 3, 3, 2, 3};
        int minValue = Arrays.stream(ints).distinct().sorted().reduce((x, y) -> x * 10 + y).getAsInt();
        System.out.println(minValue);

        // oddOrEven
        List<Integer> integers = new ArrayList<>(List.of(1, 1, 1, 2, 2, 1, 3));
        Map<Boolean, List<Integer>> oddAndEven = integers.stream().collect(partitioningBy(x -> x % 2 == 0, toList()));
        System.out.println(oddAndEven.get(oddAndEven.get(false).size() % 2 != 0));
    }
}
