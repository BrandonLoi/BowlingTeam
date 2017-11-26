package purdue.bowlingapp;

import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by Aaron on 11/17/2017.
 */

public class Graph {
    //private ArrayList<ArrayList<Integer>> adj;
    private ArrayList<CheckBox>[] checkAdj;
    private ArrayList<Integer>[] adj;
    private int E;
    private final int V;

    Graph(int V) {
        adj = (ArrayList<Integer>[]) new ArrayList[V];
        this.V = V;
        this.E = 0;
        for(int i = 0 ; i < V; i++)
            adj[i]= new ArrayList<>();
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    public ArrayList<Integer> getAdj(int v) {
        return adj[v];
    }

    public int degree(int v) {
        return adj[v].size();
    }
}
