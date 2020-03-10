package com.example.demo.model;

import sun.management.counter.Counter;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 23:30 2020-03-09
 **/
public class Test {

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "bca";

        if (checkPermutation(s1,s2)){
            System.out.println("true");

        }else{
            System.out.println("false");
        }
    }

    public static boolean checkPermutation(String s1, String s2) {
        if (s1.length()!=s2.length()) return false;
        int[] temp=new int[256];
        for (int i = 0; i <s1.length() ; i++) {
            char c = s1.charAt(i);
            temp[c]++;
        }
        for (int i = 0; i <s2.length() ; i++) {
            char c=s2.charAt(i);
            if (temp[c]==0) return false;
            else temp[c]--;
        }
        return true;
    }


}
