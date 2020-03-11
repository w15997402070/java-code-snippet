package com.demo.graph;

import java.io.InputStream;

/**
 * 无向图API
 *
 * @author wang
 */
public class Graph {
    /**
     * 顶点
     */
    private final int V;
    /**
     * 边数
     */
    private int E;
    /**
     * 领接表
     */
    private Bag<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        //创建领接表
        adj = new Bag[V];
        //将所有领接表初始化为空
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }
    }

    public Graph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }


    /**
     * 顶点数
     *
     * @return
     */
    public int V() {
        return V;
    }

    /**
     * 边数
     *
     * @return
     */
    public int E() {
        return E;
    }

    /**
     * 向图中添加一条边
     * v - w
     *
     * @param v
     * @param w
     */
    public void addEdge(int v, int w) {
        //将w添加到v链表中
        ((Bag<Integer>) adj(v)).add(w);
        //将v添加到w链表中
        ((Bag<Integer>) adj(w)).add(v);
        E++;
    }

    /**
     * 和v相邻的所有顶点
     *
     * @param v
     * @return
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    @Override
    public String toString() {
        String s = V + " vertices. " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + " : ";
            for (int w : this.adj(v)) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

    /**
     * 计算v的度数
     *
     * @param G
     * @param v
     * @return
     */
    public static int degree(Graph G, int v) {
        int degree = 0;
        for (int w : G.adj(v)) {
            degree++;
        }
        return degree;
    }

    /**
     * 计算所有顶点的最大度数
     *
     * @param G
     * @return
     */
    public static int maxDegree(Graph G) {
        int max = 0;
        for (int v = 0; v < G.V(); v++) {
            if (degree(G, v) > max) {
                max = degree(G, v);
            }
        }
        return max;
    }

    /**
     * 计算所有顶点的平均度数
     *
     * @param G
     * @return
     */
    public static double avgDegree(Graph G) {
        return 2 * G.E() / G.V();
    }

    /**
     * 计算自环的个数
     *
     * @param G
     * @return
     */
    public static int numberOfSelfLoops(Graph G) {
        int count = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    count++;
                }
            }
        }
        return count / 2;
    }


}
