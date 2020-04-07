package com.example.demo.leetcode.array;

/**
 * @Author changrong.zeng
 * @Description 八皇后问题
 * @Date 17:19 2020-04-03
 **/
public class queen8 {
     int max = 8;
     int[] tab = new int[max]; //定一个一个一维数组存放每个皇后的位置，下标代表行，值代表列
    static int cnt = 0;

    public static void main(String[] args) {
        queen8 queen = new queen8();
        queen.check(0);
        System.out.println("共有摆放方式：" + cnt);






    }

    /**
     * 判断第n个跟前面n-1个皇后是否冲突
     * @param n
     * @return
     */
    public  boolean judge(int n){
        for(int i=0;i<n;i++){
            // tab[i] == tab[n] 判断第n个皇后和第i个皇后是不是在同一列
            // Math.abs(tab[n] - tab[i]) 判断第n个皇后和第i个皇后是不是在统一斜线上
            if(tab[i] == tab[n] || (Math.abs(n-i) == Math.abs(tab[n] - tab[i]))){
                return false;
            }
        }
        return true;
    }

    //放置第n个皇后
    public void check(int n){
        //放完第8个退出
        if(n == max){
            print();
            return;
        }

        for(int i=0;i<max;i++){
            tab[n] = i;    //循环判断，将第n个皇后放在当前位置
            if(judge(n)){  //判断是否冲突
                check(n+1); //不冲突继续下一个皇后
            }
            //如果冲突，i++，第n个皇后继续下一个位置检验
        }


    }

    public void print(){
        cnt++;
        for(int i=0;i<max;i++){
            System.out.printf(tab[i]+" ");
        }
        System.out.println();
    }




}
