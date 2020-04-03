package com.example.demo.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author changrong.zeng
 * @Description 单链表
 * @Date 11:14 2020-03-27
 **/


public class SingleLinkedListDemo {

    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        HeroNode node1 = new HeroNode(1,"宋江","及时雨");
        HeroNode node2 = new HeroNode(2,"卢俊义","玉麒麟");
        HeroNode node3 = new HeroNode(3,"吴用","智多星");
        HeroNode node4 = new HeroNode(4,"林冲","豹子头");

        // 添加节点测试
        System.out.println("==============   添加节点测试 =====================");
        singleLinkedList.add(node1);
        singleLinkedList.add(node2);
        singleLinkedList.add(node3);
        singleLinkedList.add(node4);
        singleLinkedList.list();
//
//        // 删除节点测试
//        System.out.println("==============   删除节点测试 =====================");
//        singleLinkedList.del(1);
//        singleLinkedList.del(3);
//        singleLinkedList.list();

//        //添加节点测试
//        System.out.println("==============   添加节点测试 =====================");
//        singleLinkedList.addByOrder(node2);
//        singleLinkedList.addByOrder(node1);
//        singleLinkedList.addByOrder(node4);
//        singleLinkedList.addByOrder(node3);
//        singleLinkedList.addByOrder(node3);

//        singleLinkedList.list();

        //反转节点测试
//        System.out.println("==============   反转节点测试 =====================");
//        singleLinkedList.reverseList(singleLinkedList.getHead());
//        singleLinkedList.list();


        SingleLinkedList singleLinkedList2 = new SingleLinkedList();
        HeroNode node5 = new HeroNode(5,"张三","zhangsan");
        HeroNode node6 = new HeroNode(6,"李四","lisi");


        // 添加节点测试
        System.out.println("==============  singleLinkedList2 添加节点测试 =====================");
        singleLinkedList2.add(node5);
        singleLinkedList2.add(node6);
        singleLinkedList2.list();

        // 两个单链表合并节点测试
        System.out.println("==============   两个单链表合并节点测试 =====================");
        HeroNode heroNode = SingleLinkedList.unionList(singleLinkedList.getHead(), singleLinkedList2.getHead());
        SingleLinkedList newsingleLinkedList = new SingleLinkedList();
        newsingleLinkedList.setHead(heroNode);
        newsingleLinkedList.list();


    }



}


class SingleLinkedList{
    private HeroNode head = new HeroNode(0,"","");

    public HeroNode getHead() {
        return head;
    }

    public void setHead(HeroNode head) {
        this.head = head;
    }

    /**
     * 直接添加节点
     * @param hNode
     */
    public void add(HeroNode hNode){
        if(head.next == null){
            head.next = hNode;
            return;
        }
        HeroNode temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        temp.next = hNode;
    }

    /**
     * 按次序添加
     * @param hNode
     */
    public void addByOrder(HeroNode hNode){
        if(head.next == null){
            head.next = hNode;
            return;
        }
        HeroNode temp = head;
        boolean flag = false;
        while (null != temp.next && temp.next.no <= hNode.no){
            if(temp.next == null){
                break;
            }
            if(temp.next.no == hNode.no){
                flag = true;
                break;
            }

            temp = temp.next;
        }
        if(flag){
            System.out.printf("该节点已经存在，no:%d",hNode.no);
            System.out.println();
            return;
        }
        hNode.next =  temp.next;
        temp.next = hNode;
    }

    /**
     * 链表反转
     * 新建立一个节点reverseHead，依次遍历单链表，将取出的每个节点插入reverseHead的下一个节点，最后替换reverseHead
     * @param head
     */
    public void reverseList(HeroNode head){
        if(head.next == null  || head.next.next == null){
            return;
        }
        HeroNode reverseHead = new HeroNode(0,"","");
        HeroNode cur = head.next;
        HeroNode next = null;
        while (cur != null){
            next = cur.next;
            cur.next = reverseHead.next;
            reverseHead.next = cur;
            cur = next;

        }
        head.next = reverseHead.next;
    }


    /**
     * 合并两个有序链表，新的链表重新排序
     * @param head1
     * @param head2
     */
    public static HeroNode unionList(HeroNode head1, HeroNode head2){

        if(head1.next == null){
            return head2;
        }
        if(head2.next == null){
            return  head1;
        }

        HeroNode heroNode = new HeroNode(0,"","");
        HeroNode temp1 =  head1.next;
        HeroNode temp2 = head2.next;
        HeroNode temp = heroNode;


        while (temp1 != null && temp2 != null){
            if(temp1.no <= temp2.no){
                temp.next = temp1;
                temp1 = temp1.next;

            }else{
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        while (temp1 != null){
            temp.next = temp1;
            temp1 = temp1.next;
            temp = temp.next;
        }
        while (temp2 != null){
            temp.next = temp2;
            temp2 = temp2.next;
            temp = temp.next;
        }
        return  heroNode;

    }



    /**
     * 删除节点
     * @param hNo
     */
    public void del(int hNo){
        if(head.next == null){
            return;
        }
        HeroNode temp = head;
        boolean flag = false;

        while(temp.next != null){
            if(temp.next.no == hNo){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if(!flag){
            System.out.printf("该节点不存在，no:%d",hNo);
            return ;
        }
       temp.next  = temp.next.next;
    }

    /**
     * 返回链表的长度
     * @param head
     * @return
     */
    public int getLength(HeroNode head){
        if(head.next == null){
            return 0;
        }
        int count = 0;
        HeroNode temp = head;
        while (temp.next != null){
            count ++;
            temp = temp.next;
        }
        return count;

    }



    /**
     * 遍历展示
     * @return
     */
    public void list(){
        if(head.next == null){
            return;
        }
        HeroNode temp = head;
        while (temp.next != null){
            System.out.println(temp.next.toString());
             temp = temp.next;
        }
    }


    public HeroNode removeDuplicateNodes(HeroNode head) {
        if(head == null || head.next == null){
            return head;
        }

        HeroNode temp = head.next;
        HeroNode temp_pre = head;
        Set<Integer> set = new HashSet<Integer>();
        set.add(head.no);
        while(temp != null){
            if(set.contains(temp.no)){
                temp_pre.next =  temp_pre.next.next;
            }else{
                set.add(temp.no);
            }
            temp = temp.next;
            temp_pre = temp_pre.next;
        }
        return head;
    }


}



class HeroNode{
    public  int no;
    public String name;
    public String nickName;
    public HeroNode next;

    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
