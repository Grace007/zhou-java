package com.zhou.test.dataStructure.dfs;

/**
 * 图中每个节点的表示
 * @author eli
 * @date 2017/12/18 14:25
 */
class Vertex {
    public char label;
    public boolean wasVisited;

    public Vertex(char lab) {
        label = lab;
        wasVisited = false;
    }
}