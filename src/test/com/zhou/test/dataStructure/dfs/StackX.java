package com.zhou.test.dataStructure.dfs;

/**
 * 简单的栈
 * @author eli
 * @date 2017/12/18 14:24
 */
class StackX {
    private final int SIZE = 20;
    private int[] st;
    private int top;

    public StackX() {
        st = new int[SIZE];
        top = -1;
    }

    public void push(int j) {
        st[++top] = j;
    }

    /**
     * 取栈顶元素,并弹栈
     * @return
     */
    public int pop() {
        return st[top--];
    }

    /**
     * 返回栈顶元素的值
     * @return
     */
    public int peek() {
        return st[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }
}
