package com.zhou.test.dataStructure.dfs;

/**
 * 图的具体表示
 * @author eli
 * @date 2017/12/18 14:27
 */
public class Graph {
    //图节点限制
    private final int MAX_VERTS = 20;
    //节点数组
    private Vertex vertexList[];
    //邻接矩阵
    private int adjMat[][];
    //当前节点数目
    private int nVerts;
    //栈
    private StackX theStack;

    /**
     * 初始化图
     */
    public Graph(){
        vertexList = new Vertex[MAX_VERTS];
        nVerts=0;
        theStack=new StackX();
        adjMat=new int[MAX_VERTS][MAX_VERTS];
        for (int i=0;i<MAX_VERTS;i++){
            for (int j=0;j<MAX_VERTS;j++){
                adjMat[i][j]=0;
            }
        }
    }

    /**
     * 向图中增加一个节点
     */
    public void addVertex(char ch) {
        vertexList[nVerts++] = new Vertex(ch);
    }
    public void addEdge(int start,int end){
        adjMat[start][end]=1;
        adjMat[end][start]=1;
    }
    public void dfs(){
        vertexList[0].wasVisited = true;
        displayVertex(0);
        //向栈中放入一个起点
        theStack.push(0);
        while( !theStack.isEmpty() )
        {
            //弹栈并返回
            int v = getAdjUnvisitedVertex( theStack.peek() );
            //如果没有未访问节点
            if(v == -1)
                theStack.pop();
            else
            {
                vertexList[v].wasVisited = true;
                displayVertex(v);
                theStack.push(v);
            }
        }

        for(int j=0; j<nVerts; j++){
            vertexList[j].wasVisited = false;
        }
    }

    public int getAdjUnvisitedVertex(int v)
    {
        for(int j=0; j<nVerts; j++)
            if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
                return j;
        return -1;
    }
    public void displayVertex(int v)
    {
        System.out.print(vertexList[v].label);
    }

    public static void main(String[] args)
    {
        Graph theGraph = new Graph();
        theGraph.addVertex('A');
        theGraph.addVertex('B');
        theGraph.addVertex('C');
        theGraph.addVertex('D');
        theGraph.addVertex('E');

        theGraph.addEdge(0, 1);
        theGraph.addEdge(1, 2);
        theGraph.addEdge(0, 3);
        theGraph.addEdge(3, 4);

        System.out.print("Visits: ");
        theGraph.dfs();
        System.out.println();
    }

}
