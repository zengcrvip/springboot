package com.example.demo.collection;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 18:31 2020-04-07
 **/
public class Test {
    public static void main(String[] args) {
        Test test = new Test();

        System.out.println(test.swip(5));

    }

    public int movingCount(int m, int n, int k) {
        int result = 0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(swip(i) + swip(j) <= k){
                    result ++;
                }
            }
        }
        return result;
    }

    public int swip(int x){
        int result = x%10;
        int num = x/10;
        while(num != 0){
            result = result + num%10;
            num = num/10;
        }
        return result;
    }
}
