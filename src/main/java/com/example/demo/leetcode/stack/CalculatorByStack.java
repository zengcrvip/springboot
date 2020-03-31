package com.example.demo.leetcode.stack;

/**
 * @Author changrong.zeng
 * @Description 通过stack模拟计算过程
 * 初始化两个stack，一个push数字，一个push操作符合；
 * 如果当前操作符比stack里的操作符号优先级高，直接压入；
 * 如果当前操作符比stack里操作符优先级低，先取出数字stack两个数字，操作符stack一个进行运算，再将结果push数字栈，再把当前操作符push stack
 * @Date 16:29 2020-03-31
 **/
public class CalculatorByStack {


    public static void main(String[] args) {
        CalculatorByStack calculator = new CalculatorByStack();
         ArrayStack num_stack = new ArrayStack(20);
         ArrayStack operator_stack = new ArrayStack(20);

        String str = "300+2*6-3-2";
        int num1 = 0;
        int num2 = 0;
        char c = ' ';

        for(int i=0;i<str.length();i++){
             c = str.charAt(i);
            //如果是运算符
            if(calculator.isOperator(c)){
                //operator_stack 为空
                if(operator_stack.isEmpty()){
                    operator_stack.push(c);
                }else{
                    //如果当前运算符的优先级 <= 栈顶的符号优先级
                    if(calculator.operatorPriority(c) <= calculator.operatorPriority(operator_stack.peek())){
                        num1 = num_stack.pop();
                        num2 = num_stack.pop();
                        int val = calculator.calculate(num1, num2, operator_stack.pop());
                        num_stack.push(val);
                        operator_stack.push(c);
                    }else{
                        operator_stack.push(c);
                    }
                }
            }else{
                //如果是数字
                int j = i+1;
                int start = i;
                while (j < str.length()){
                    char temp = str.charAt(j);
                    if(calculator.isOperator(temp)){
                        break;
                    }
                    j++;
                    i++;
                }
                num_stack.push(Integer.parseInt(str.substring(start,j)));
            }
        }

        //表达式扫描完毕，顺序从栈中pop出元素计算，数字stack最后一个就是结果
        while (!operator_stack.isEmpty()){
             num1 = num_stack.pop();
             num2 = num_stack.pop();
             num_stack.push(calculator.calculate(num1,num2,operator_stack.pop()));
        }
        System.out.println(num_stack.pop());

    }


    /**
     * 计算两个数字的计算结果
     * @param num1
     * @param num2
     * @param oper
     * @return
     */
    public int calculate(int num1, int num2, int oper){
        int res = 0;
        switch (oper){
            case '*' :
                res = num1 * num2;
                break;
            case '/' :
                res = num2 / num1;
                break;
            case '+' :
                res = num1 + num2;
                break;
            case '-' :
                res = num2 - num1;
                break;
            default:
                res = 0;
        }
        return res;
    }

    /**
     * 返回运算符的优先级
     * @param oper
     * @return
     */
    public int operatorPriority(int oper){
        if('*' == oper || '/' == oper){
            return 1;
        }else if('+' == oper || '-' == oper){
            return 0;
        }else{
            return -1;
        }
    }

    /**
     *  判断是否是运算符
     * @param oper
     * @return
     */
    public boolean isOperator(int oper){
        if('*' == oper || '/' == oper || '+' == oper || '-' == oper){
            return true;
        }
        return false;
    }



}



