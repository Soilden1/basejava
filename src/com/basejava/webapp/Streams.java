package com.basejava.webapp;

import java.util.*;

import static java.util.stream.Collectors.*;

public class Streams {

    public static void main(String[] args) {
        int[] ints = new int[] {1, 2, 3, 3, 2, 3};
        System.out.println(minValue(ints));

        List<Integer> integers = new ArrayList<>(List.of(1, 1, 1, 2, 2, 1, 3));
        System.out.println(oddOrEven(integers));
    }

    public static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (x, y) -> x * 10 + y);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> oddAndEven = integers.stream().collect(partitioningBy(x -> x % 2 == 0, toList()));
        return oddAndEven.get(oddAndEven.get(false).size() % 2 != 0);
    }
}
