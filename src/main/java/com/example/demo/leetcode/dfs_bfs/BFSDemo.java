package com.example.demo.leetcode.dfs_bfs;

import org.apache.poi.ss.formula.functions.T;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author changrong.zeng
 * @Description BFS
 * @Date 16:05 2020-04-09
 **/
public class BFSDemo {

    public static void main(String[] args) {
        Tree tree = init();
        sort(tree);

    }



    public static void sort(Tree root){
        Queue<Tree> queue = new LinkedList<>();
        queue.add(root);


        while(!queue.isEmpty()){
            Tree node = queue.poll();
            System.out.print(node.data+" ");

            if(node.left != null){
                queue.add(node.left);
            }

            if(node.right != null){
                queue.add(node.right);
            }
        }
        System.out.println();
    }

    /**
     * 原始树
     * @return
     */
    public static Tree init(){
        Tree node1 = new Tree(1);
        Tree node2 = new Tree(2);
        Tree node3 = new Tree(3);
        Tree node4 = new Tree(4);
        Tree node5 = new Tree(5);
        Tree node6 = new Tree(6);
        Tree node7 = new Tree(7);
        Tree node8 = new Tree(8);
        Tree node9 = new Tree(9);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        node5.right = node8;
        node7.left = node9;
        return node1;


    }



}
