package com.example.demo.leetcode.array;

/**
 * @Author changrong.zeng
 * @Description 迷宫问题
 * @Date 15:05 2020-04-03
 **/
public class MazeDemo {



    public static void main(String[] args) {
        Maze maze = new Maze();
        maze.init();
        maze.print();

        maze.setWay(1,1);
        System.out.println("============走完后=======");
        maze.print();

    }



}

class Maze{
    public int[][] table = new int[8][8];

    /**
     * 初始化
     */
    public void init(){
        //初始化
        for(int i=0;i< 8;i++){
            table[0][i] = 1;
            table[7][i] = 1;
        }
        for(int i=0;i<8;i++){
            table[i][0]= 1;
            table[i][7] = 1;
        }
        table[3][1]= 1;
        table[3][2]= 1;

    }

    /**
     * 打印
     */
    public void print(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                System.out.printf(table[i][j]+" ");
            }
            System.out.println();
        }
    }

    /**
     * 按照从下-右-上-左的方式走，如果当前节点没有试过为0，为墙是1，如果试过是可通行的为2，不可通为3
     * @param i
     * @param j
     * @return
     */
    public boolean setWay(int i,int j){
        if(table[6][6] == 2){
            return true;
        }else{
            //当前节点没有走过
            if(table[i][j] == 0){
                table[i][j] = 2; //假设当前节点是可通行的
                if(setWay(i+1,j)){ //下
                    return true;
                }else if(setWay(i,j+1)){ //右
                    return true;
                }else if(setWay(i-1,j)){ //上
                    return true;
                }else if(setWay(i,j-1)) { //左
                    return true;
                }else{
                    table[i][j] = 3;
                    return false;
                }
            }else{
                return false;
            }
        }
    }


}
