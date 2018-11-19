package com.example.demo.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 下午9:07 2018/10/23 .
 */
public class PersonListTest {

    public static void main(String[] args){

        List<Person> peopleList = Arrays.asList(
                new Person("Ted", "Neward", 42),
                new Person("Charlotte", "Neward", 39),
                new Person("Michael", "Neward", 19),
                new Person("Matthew", "Neward", 13),
                new Person("Neal", "Ford", 45),
                new Person("Candy", "Ford", 39),
                new Person("Jeff", "Brown", 43),
                new Person("Betsy", "Brown", 39)
        );

       //排序
       Collections.sort(peopleList,Person::compareLastAndAge);
       //遍历
        peopleList.forEach(it -> System.out.println(it));
        //过滤
        List<Person> collect = peopleList.stream().filter(it -> it.getAge() > 21).collect(Collectors.toList());
        System.out.println("===============过滤=======================");
        collect.forEach(it -> System.out.println(it));
        System.out.println("===============特殊过滤=======================");
        peopleList.stream().filter(it -> it.getAge() > 21)
                .filter(it -> it.getAge() < 43)
                .filter(it -> it.getLastName().length() > 4)
                .forEach(it -> System.out.println(it));

        //转换成map
        System.out.println("===============list转换成map=======================");
        Map<String,Person> map = peopleList.stream().collect(Collectors.toMap(Person::key, a -> a));
        System.out.println(map.toString());


    }
}
