// DFS way
class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];

        for (int i = 0; i < n; i++) { //This graph might be a disconnected graph. So check each unvisited node.
        	if (colors[i] == 0) {
        		if (!isValidColor(graph, colors, 1, i)) {return false;}
        	}
        }
        return true;
    }
    // dfs helper
    private boolean isValidColor(int[][] graph, int[] colors, int color, int i) {
    	// already visited
    	if (colors[i] != 0) {
    		return colors[i] == color;
    	}
    	// haven't visited
    	colors[i] = color;
    	// do same thing to its adj nodes
    	for (int j: graph[i]) {
    		if (!isValidColor(graph, colors, -color, j)) {
    			return false;
    		}
    	}
    	return true;

    }





class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];
        // since graph might be disconnected, need to start with each node
        // and only visit those haven't been visited
        for (int i = 0; i < n; i++) {
        	if (colors[i] != 0) {continue;} // skip those have been visited
        	Queue<Integer> q = new LinkedList<>();

	        q.offer(i);
	        colors[i] = 1;
	        while (!q.isEmpty()) {
	        	int cur = q.poll();
	        	for (int next: graph[cur]) {
	        		if (colors[next] == 0) {
	        			colors[next] = -colors[cur];
	        			q.offer(next); // if not visited, add to queue, labeled as visited
	        		} else {
	        			if (colors[next] != -colors[cur]) {return false;}
	        		}
	        	}
        	}
        }      
        return true;
    }
}




// phase 2 self : DFS
class Solution {
    public boolean isBipartite(int[][] graph) {
        int[] visited = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] == 0) {
                if (dfs(graph, i, visited, 1) == false) { 
                    return false;
                }   
            }
        }
        return true;
    }
    
    private boolean dfs(int[][] graph, int cur, int[] visited, int label) {
        if (visited[cur] != 0) {return visited[cur] == label;}
        visited[cur] = label;
        label = -label;
        for (int next: graph[cur]) {
            if (dfs(graph, next, visited, label) == false) {
                return false;
            }
        }
        return true;
    }
}


// Phase2 self: bfs
class Solution {
    public boolean isBipartite(int[][] graph) {
        int[] visited = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (visited[i] == 0) {
                if (bfs(graph, i, visited, 1) == false) {
                    return false;
                }   
            }
        }
        return true;
    }
    
    private boolean bfs(int[][] graph, int i, int[] visited, int label) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(i);
        visited[i] = label;
        while (!queue.isEmpty()) {
            int size = queue.size();
            label = -label;
            while (size-- > 0) {
                int cur = queue.poll();
                for (int next: graph[cur]) {
                    if (visited[next] != 0) {
                        if (visited[next] != label) {return false;}
                    } else {
                        visited[next] = label;
                        queue.add(next);
                    }                 
                }
            }
        }
        return true;
    }
}



// Phase3 self 
// The traverse could be bfs or dfs
// M1: bfs
class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] status = new int[n];
        for (int i = 0; i < n; i++) {
            if (status[i] != 0) {continue;}
            if (!traverse(graph, i, status, 1)) {return false;}
        }
        return true;
    }
    
    // M1: bfs way
    private boolean traverse(int[][] graph, int i, int[] status, int label) {
        Queue<Integer> queue = new LinkedList<>();
        status[i] = label;
        queue.add(i);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                for (int next: graph[cur]) {
                    if (status[next] == 0) {
                        status[next] = -label;
                        queue.add(next);
                    } else if (status[next] == label) {return false;}
                } 
            }
            label *= -1;
        }
        return true;
    }


    // M2: dfs way
    private boolean traverse(int[][] graph, int i, int[] status, int label) {
        if (status[i] != 0) {return status[i] == label;}
        status[i] = label;
        for (int next: graph[i]) {
            if (!traverse(graph, next, status, -label)) {return false;}
        }
        return true;
    }


}



