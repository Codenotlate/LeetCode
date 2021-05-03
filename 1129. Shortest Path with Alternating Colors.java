// phase2 self M1
class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        // first step: build the graph, each node has two list of adjacent nodes for red and blue edges
        List<Integer>[][] graph = new List[n][2];
        for (List[] l: graph) {
        	l[0] = new LinkedList();
        	l[1] = new LinkedList();
        }
        // 0 represents red, 1 for blue
        addColorToGraph(red_edges, 0, graph);
        addColorToGraph(blue_edges, 1, graph);

        // second step: start doing BFS, add all 0's neighbor into the queue
        // add int[node, color] to the queue
        Queue<int[]> queue = new LinkedList<>();
        int[][] visited = new int[n][2];
        // initialize res
        int[] res = new int[n];
        Arrays.fill(res, -1);
        res[0] = 0;
        // add all node 0's neighbor with color of edge
        for (int i = 0; i <= 1; i++) {
        	for (int node: graph[0][i]) {
	        	queue.add(new int[]{node, i});
	        	visited[node][i] = 1;
	        }
        }

        int level = 1;
        while (!queue.isEmpty()) {
        	int size = queue.size();
        	while (size-- > 0) {
        		int[] cur = queue.poll();
        		if (res[cur[0]] == -1) {
        			res[cur[0]] = level;
        		} else {
        			res[cur[0]] = Math.min(res[cur[0]], level);
        		}
        		// find neighbors
        		// cur[1] = 0, we find next blue(1), cur[0]=1, we find next red(0); thus we use 1- cur[1]
        		for (int next: graph[cur[0]][1 - cur[1]]) {
        			if (visited[next][1 - cur[1]] == 0) {
        				queue.add(new int[]{next, 1 - cur[1]});
        				visited[next][1 - cur[1]] = 1;
        			}
        		}
        	}
        	level++;
        }

        return res;
    }


    private void addColorToGraph(int[][] edges, int color, List[][] graph) {
    	for(int[] edge: edges) {
    		graph[edge[0]][color].add(edge[1]);
    	}
    }
}

/*
Another way using similar idea, but use res[color][node] to record the level and at the same time whether visited
res[color][node] is initialize to 2 * n
finally choose the min between res[red][i] and res[blue][i] to get the answer.

*/
// https://leetcode.com/problems/shortest-path-with-alternating-colors/discuss/339964/JavaPython-BFS





















