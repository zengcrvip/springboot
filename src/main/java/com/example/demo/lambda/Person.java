package com.example.demo.lambda;

import lombok.Data;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 下午9:00 2018/10/23 .
 */
@Data
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    public Person(String fn, String ln, int a) {
        this.firstName = fn;
        this.lastName = ln; this.age = a;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }

    public String key(){
        return new StringBuilder().append(firstName).append(".")
                .append(lastName).toString();

    }

    public static  int compareLastAndAge(Person lhs, Person rhs){
        if(lhs.lastName.equals(rhs.lastName)){
            return lhs.age - rhs.age;
        }else{
            return lhs.lastName.compareTo(rhs.lastName);
        }
    }



}
