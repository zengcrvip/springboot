package com.example.demo.leetcode.stack;

/**
 * @Author changrong.zeng
 * @Description
 * @Date 16:31 2020-03-31
 **/
public class ArrayStack {
    private int[] array;
    private int maxSize;
    private int top;


    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        array = new int[maxSize];
        top = -1;
    }

    public void push(int x){
        if(isFull()){
            throw new RuntimeException("stack is full");
        }
        top++;
        array[top] = x;
    }

    public int pop(){
        if(isEmpty()){
            throw new RuntimeException("stack is empty");
        }
        int value = array[top];
        top --;
        return value;

    }

    public int peek(){
        if(isEmpty()){
            throw new RuntimeException("stack is empty");
        }
        return array[top];
    }

    public boolean isEmpty(){
        return top == -1;

    }

    public boolean isFull(){
        return top == maxSize-1;
    }

    public void list(){
        if(isEmpty()){
            return;
        }
        while(top > -1){
            System.out.println(array[top]);
            top --;
        }
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(3);

        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.list();

    }
}
