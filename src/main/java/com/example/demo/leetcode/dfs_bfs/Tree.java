package com.example.demo.leetcode.dfs_bfs;

import lombok.Data;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 14:17 2020-04-09
 **/

public class Tree {
    public  int data;
    public  Tree left;
    public Tree right;

    public Tree(int data) {
        this.data = data;
    }


}
