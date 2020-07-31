package com.example.demo.leetcode.dfs_bfs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @Author changrong.zeng
 * @Description 深度搜索优先算法
 *
 * @Date 14:16 2020-04-09
 **/
public class DFSDemo {

    public static void main(String[] args) {
        Tree node = init();

        System.out.println("=======先序遍历,stack=============");
        pre_sort_stack(node);
        System.out.println();

        System.out.println("=======先序遍历,递归=============");
        pre_sort_digui(node);
        System.out.println();

        System.out.println("=======中序遍历,stack=============");
        middle_sort_stack(node);
        System.out.println();

        System.out.println("=======中序遍历,递归=============");
        middle_sort_digui(node);
        System.out.println();

        System.out.println("=======后序遍历,递归=============");
        post_sort_digui(node);
        System.out.println();

        System.out.println("=======后序遍历,stack=============");
        post_sort_stack(node);
        System.out.println();

        System.out.println("=======后序遍历,stack2=============");
        post_sort_stack2(node);
        System.out.println();

    }



    /**
     * 先序遍历
     * DFS 通过递归的方式实现
     * 先序遍历：先访问根，再访问左子树，最后访问右子树，总结就是“根左右”；
     */
    public static void pre_sort_digui(Tree root){
        if(root == null){
            System.out.println("tree is empty");
        }
        System.out.printf(root.data + " ");

        if(root.left !=null){
            pre_sort_digui(root.left);
        }
        if(root.right != null){
            pre_sort_digui(root.right);
        }
    }

    /**
     * DFS 中序遍历
     * 通过递归实现
     * 中序遍历：先访问左子树，再访问根，最后访问右子树，总结就是“左根右”；
     * @param root
     */
    public static void middle_sort_digui(Tree root){
        if(root == null){
            System.out.println("tree is empty");
        }
        if(root.left != null){
            middle_sort_digui(root.left);
        }
        System.out.printf(root.data + " ");

        if(root.right != null){
            middle_sort_digui(root.right);
        }
    }

    /**
     * DFS 后序遍历
     * 通过递归实现
     * 中序遍历：先访问左子树，再访访问右子树，最后访问根，总结就是“左右根”；
     * @param root
     */
    public static void post_sort_digui(Tree root){
        if(root == null){
            System.out.println("tree is empty");
        }
        if(root.left != null){
            post_sort_digui(root.left);
        }

        if(root.right != null){
            post_sort_digui(root.right);
        }

        System.out.printf(root.data + " ");
    }


    /**
     * DFS 先序遍历
     * 通过stack实现
     * 先序遍历：先访问根，在访问左子树，最后访问右子树，总结就是“根左右”；
     */
    public static void pre_sort_stack(Tree tree){
        Deque<Tree> stack = new ArrayDeque<>();
        stack.push(tree);

        while (!stack.isEmpty()){
            Tree node = stack.poll();
            System.out.printf(node.data+" ");

            //前序遍历，stack是后进先出的结构，所以先插入右节点
            if(node.right != null){
                stack.push(node.right);
            }

            if(node.left != null){
                stack.push(node.left);
            }
        }

    }

    /**
     * DFS 中序遍历
     * 通过stack实现
     * 中序遍历：先访问左子树，再访问根，最后访问右子树，总结就是“左根右”；
     * @param root
     */
    public static void middle_sort_stack(Tree root){
        if(root == null){
            System.out.println("tree is empty");
        }

        Deque<Tree> stack = new ArrayDeque<>();
        Tree cur  = root;

        while(cur != null || !stack.isEmpty()){

            //将当前节点及左子节点压入stack
            while (cur != null){
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            System.out.printf(cur.data + " ");
            cur = cur.right;
        }
    }


    /**
     * DFS 后序遍历
     * 通过stack实现
     * 中序遍历：先访问左子树，再访问右子树，最后访问根，总结就是“左右根”；
     * @param root
     */
    public static void post_sort_stack(Tree root){
        if(root == null){
            System.out.println("tree is empty");
        }

        Deque<Tree> stack = new ArrayDeque<>();
        Tree cur  = root;
        Tree lastVisit = root;



        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.peek();
            if(cur.right == null || cur.right == lastVisit){
                System.out.printf(cur.data + " ");
                stack.pop();
                lastVisit = cur;
                cur = null;
            }else{
                cur = cur.right;
            }
        }

    }

    /**
     * DFS 后序遍历方法2
     * 我们已知后序遍历的节点访问顺序为：左 → 右 → 中；
     * 我们将这个次序颠倒过来：中 → 右 → 左；有没有想到前序遍历的节点访问顺序呢？
     * 前序遍历是，中 → 左 → 右；因此，我们可以将前序遍历代码中的压栈顺序进行调整，并将结果逆序输出就可以啦！
     *
     *
     * @param root
     */
    public static void post_sort_stack2(Tree root){
        if(root == null){
            System.out.println("tree is empty");
        }

        Deque<Tree> stack = new ArrayDeque<>();
        LinkedList<Integer> list = new LinkedList<Integer>();

        stack.push(root);

        while(!stack.isEmpty()){
            Tree node = stack.pop();
            list.addFirst(node.data);
            if(node.left != null){
                stack.push(node.left);
            }
            if(node.right != null){
                stack.push(node.right);
            }
        }

        while(!list.isEmpty()){
            System.out.print(list.pop() + " ");
        }

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
