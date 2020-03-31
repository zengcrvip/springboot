package com.example.demo.leetcode;

/**
 * @Author changrong.zeng
 * @Description 约瑟夫问题
 *  编号1，2，3，...n个人围坐成一圈，编号为K的人开始报数（1<=k<=n）,数到m的人出列，他的下一位继续从1开始数，数到m的人出列，依次类推，
 *  直到所有人出列，产生一个出队的编号序列
 * @Date 21:18 2020-03-28
 **/
public class Josephu {

    public static void main(String[] args) {
        CycleLink cycleLink = new CycleLink(new Boy(-1));

        //方法一：一次性添加10个人
        System.out.println("============== 一次性添加10个人 ==============");
        cycleLink.add(10);
        cycleLink.list();

        //方法一：一个一个添加，一共添加10个人
        System.out.println("============== 一个一个添加，一共添加10个人 ==============");
        for(int i=1;i<=10;i++){
            cycleLink.addOneByOne(i);
        }
        cycleLink.list();


        cycleLink.countBoy(1,2,5);


    }

}

class CycleLink{
    private Boy head;

    public CycleLink(Boy head) {
        this.head = head;
    }

    /**
     * 添加nums个人
     * @param nums
     */
    public void add(int nums){
        if(nums <1){
            System.out.println("添加人数不能少于1个");
            return;
        }
        Boy cur = null;
        for(int i=1;i<=nums;i++){
            Boy boy = new Boy(i);
            if(i==1){
                head = boy;
                head.setNext(boy);
                cur = head;
            }else{
                boy.setNext(head);
                cur.setNext(boy);
                cur = cur.getNext();
            }
        }
    }

    /**
     * 一个一个添加
     * @param no
     */
    public void addOneByOne(int no){
        Boy boy = new Boy(no);
        if(no == 1){
            head = boy;
            head.setNext(head);
            return;
        }
        Boy cur = head;
        while(cur.getNext() != head){
            cur = cur.getNext();
        }
        cur.setNext(boy);
        boy.setNext(head);
    }


    /**
     * 模拟约瑟夫问题出列过程
     * @param startNo 开始编号，从第几个人开始数
     * @param endNo 结束编号，第几个人出列
     * @param count  一共多少人
     */
    public void countBoy(int startNo, int endNo, int count){
        if(startNo < 1 ||  startNo > count){
            return;
        }
        add(count); //生成初始队列
        Boy helper  = head;   //建一个辅助节点，指向head的前一个节点
        while(helper.getNext() != head){
            helper = helper.getNext();
        }

        //节点移动到endNo
        for(int i = 0;i< startNo -1;i++){
            head = head.getNext();
            helper = helper.getNext();
        }

        while(helper != head){
            // 找到需要出队列的endNo
            for(int j=0;j<endNo-1;j++){
                head = head.getNext();
                helper = helper.getNext();
            }
            System.out.println("当前节点出队列 no=" + head.getNo());
            head = head.getNext();
            helper.setNext(head);
        }
        System.out.println("当前节点出队列 no=" + head.getNo());
    }



    public void list(){
        Boy temp = head;
        while(temp.getNext() != head){
            System.out.println(temp.toString());
            temp = temp.getNext();
        }
    }


}

class Boy{
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "no=" + no +
                '}';
    }
}
