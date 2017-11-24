package purdue.bowlingapp;

/**
 * Created by Aaron on 11/19/2017.
 * code adapted from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/DepthFirstPaths.java.html
 */

public class DepthFirstSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int count;

    public DepthFirstSearch(Graph g,int s) {
        edgeTo = new int[g.V()];
        marked = new boolean[g.V()];
        dfs(g,s);
    }

    private void dfs(Graph g, int s) {
        marked[s] = true;
        for(int w : g.getAdj(s)) {
            if(!marked[w]) {
                edgeTo[w]=s;
                dfs(g,w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }
}
