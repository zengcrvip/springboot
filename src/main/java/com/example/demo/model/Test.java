package com.example.demo.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 23:30 2020-03-09
 **/
public  class Test {


    public static void main(String[] args) {
        int[] nums = {5,2,3,1};
        System.out.println(sortArray(nums));
    }

    public static int[] sortArray(int[] nums) {

        for(int i=0;i<nums.length;i++){
            int cur = nums[i];
            for(int j= i+1; j<nums.length;j++){
                if(nums[j] < cur){
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                }
            }
        }
        return nums;
    }
}
