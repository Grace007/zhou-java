package com.zhou.test.dataStructure.linkList;


import org.apache.commons.lang.StringUtils;

/**
 * 链表实现(单列数据链表,如果是类似map的双列链表,则节点多加一个数据)
 * @author eli
 * @date 2017/12/14 16:28
 */
public class LinkList<T> {
    private Node node;
    private int length;
    public LinkList(){
        clear();
    }

    /**
     * 清除链表
     */
    private  void clear(){
        node=null;
        length=0;
    }

    /**
     * 得到链表长度
     * @return
     */
    private int getLength(){
        return length;
    }

    private boolean isEmpty(){
        if (length==0) return true;
        return false;
    }

    private boolean delete(T data){
        try {
            if (StringUtils.equals(data.toString(),node.data.toString())){
                node=node.next;
                length--;
                return true;
            }
            Node tempNode = node.next;
            if(StringUtils.equals(data.toString(),tempNode.data.toString())){
                node.next=tempNode.next;
                length--;
                return true;
            }
            while (!StringUtils.equals(data.toString(),tempNode.next.data.toString())) {
                tempNode=tempNode.next;
            }
            tempNode.next=tempNode.next.next;
            length--;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 头插法
     * @param data
     */
    private void firstInsert(T data){
        Node frontNode =new Node(data);
        if (node == null){
            node=frontNode;
            length++;
        }else {
            frontNode.next=node;
            node=frontNode;
            length++;
        }
    }

    /**
     * 尾插法:必须维持node是头结点
     * @param data
     */
    private void lastInsert(T data){
        Node tailNode =new Node(data);
        if (node == null){
            node=tailNode;
            length++;
        }else{
            if (node.next == null){
                node.next = tailNode;
                length++;
            }else {
                Node tempNode = node.next;
                while (tempNode.next != null) {
                    tempNode = tempNode.next;
                }
                tempNode.next = tailNode;
                length++;
            }
        }
    }

    public void  display(){
        if (node ==null){
            System.out.println("链表为空");
        }else{
            Node tempNode=node.next;
            System.out.println(node.data);
            while (tempNode!=null){
                System.out.println(tempNode.data);
                tempNode=tempNode.next;
            }
        }

    }
    /**
     * 构建node内部类
     */
    private class Node {
        private T data;
        private Node next;

        private Node(T data){
            this.data = data;
        }
        private Node(T data,Node next){
            this.data = data;
            this.next = next;
        }
        public void displayLink()      // display ourself
        {
            System.out.print("{" + data + ", " + next + "} ");
        }
    }

    public static void main(String[] args) {
        LinkList<String> linkList = new LinkList<String>();
        /*linkList.firstInsert("node1");
        linkList.firstInsert("node2");
        linkList.firstInsert("node3");*/
        linkList.lastInsert("node1");
        linkList.lastInsert("node2");
        linkList.lastInsert("node3");
        linkList.lastInsert("node4");
        linkList.lastInsert("node5");
        linkList.lastInsert("node6");

        linkList.display();
        System.out.println("##############" );
        linkList.delete("node2");
        linkList.display();



    }

}