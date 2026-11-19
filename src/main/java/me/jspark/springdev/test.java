package me.jspark.springdev;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3,4,5,6,7,8,9,10);
        List<Integer> result = new ArrayList<>();
        for (int i : list){
            if (i % 2 == 0){
                result.add(i*2);
            }
        }
        System.out.println(result);
        List<Integer> result2 = list.stream().filter(i -> i % 2 == 0).map(i -> i * 2).toList();
        System.out.println(result2);
    }
}
