// BFS way using a queue and add result to an res array
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] inDegree = new int[numCourses];
        Queue<Integer> q = new LinkedList<>();
        // special case - can delete later, can be handled
        //if (prerequisites.length == 0 && numCourses == 1) {return res;}

        // initial lize the graph
        for (int i = 0; i < numCourses; i++) {
        	graph[i] = new ArrayList<Integer>();
        }
        // build a adjacent list graph
        for (int[] pair: prerequisites) {
        	graph[pair[1]].add(pair[0]);
        	inDegree[pair[0]]++;
        }
        // add those with inDegree == 0 into Queue
        for (int i = 0; i < numCourses; i++) {
        	if (inDegree[i] == 0) {
        		q.add(i);
        	}
        }
        // start of while loop
        int resPos = 0;
        while (!q.isEmpty()) {
        	int cur = q.poll();
        	res[resPos++] = cur;
        	for (int next: graph[cur]) {
        		inDegree[next]--;
        		if (inDegree[next] == 0) {
        			q.add(next);
        		}
        	}
        }

        // decide whether there exists topological sort
        if (resPos == numCourses) {
        	return res;
        } else {
        	return new int[0];
        }
    }
}



// dfs postorder reverse way
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // build the graph similarly as BFS
        int[] res = new int[numCourses];
        // used to add value to res backwards,
        // need to be an array to pass the change
        int[] curPos = new int[]{numCourses - 1}; 
        List<Integer>[] graph = new ArrayList[numCourses];
        // special case - can delete later, can be handled
        //if (prerequisites.length == 0 && numCourses == 1) {return res;}

        // initial lize the graph
        for (int i = 0; i < numCourses; i++) {
        	graph[i] = new ArrayList<Integer>();
        }
        // build a adjacent list graph
        for (int[] pair: prerequisites) {
        	graph[pair[1]].add(pair[0]);
        }

        // use an int array to track visited node, 
        // 2 means visited
        // 1 means visiting -> detect cycle
        // 0 means unvisited
        int[] visited = new int[numCourses];
        // use dfs helper function for each node
        for (int i = 0; i < numCourses; i++) {
        	if (hasCycle(graph, i, visited, res, curPos)) {
        		return new int[0];
        	}
        }

        // after the whole dfs, compare curPos[0] with -1 to determine whether sorted successfully
		return (curPos[0] == -1) ? res : new int[0];
    }

    private boolean hasCycle(List<Integer>[] graph, int cur, int[] visited,
    	int[] res, int[] curPos) {
    	// already visited and no cycle for sure
    	if (visited[cur] == 2) {return false;}
    	// in visiting status, and already in current path and visited again
    	// meaning there's cycle
    	if (visited[cur] == 1) {return true;}
    	visited[cur] = 1;
    	for (int next: graph[cur]) {
    		if (hasCycle(graph, next, visited, res, curPos)) {
    			return true;
    		}
    	}
    	// adding result in dfs postorder, and also in reverse order
    	res[curPos[0]--] = cur;
    	// change the node status from visiting(1) to visited(2)
    	visited[cur] = 2;
    	return false;
    }
}



//https://leetcode.com/problems/course-schedule-ii/discuss/59317/Two-AC-solution-in-Java-using-BFS-and-DFS-with-explanation
//Hellokitty_2015



// Phase2 self
// M1: BFS with indegree == 0
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // build the graph
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        int[] inDegree = new int[numCourses];

        for (int[] edge: prerequisites) {
            graph[edge[1]].add(edge[0]);
            inDegree[edge[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        int[] res = new int[numCourses];
        int resPos = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res[resPos++] = cur;
            for (int next: graph[cur]) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.add(next);
                }
            }
        }

        if (resPos == numCourses) {
            return res;
        } else {
            return new int[0];
        }
    }
}



//Phase2 self: DFS postorder reverse way
// try replace the 2 arrays in 207 with 1 array having diff values
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // build the graph
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge: prerequisites) {
            graph[edge[1]].add(edge[0]);
        }

        int[] res = new int[numCourses];
        int[] resPos = new int[]{numCourses - 1};
        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(graph, visited, i, res, resPos)) {
                return new int[0];
            }
        }
        return res;
    }

    private boolean hasCycle(List<Integer>[] graph, int[] visited, int i, int[] res, int[] resPos) {
        if (visited[i] == 2) {return false;} // same as valid[i] == true in 207
        if (visited[i] == 1) {return true;} // same as curPathVisited[i] == true in 207

        visited[i] = 1;
        for (int next: graph[i]) {
            if (hasCycle(graph, visited, next, res, resPos)) {
                return true;
            }
        }
        visited[i] = 2;
        res[resPos[0]--] = i;
        return false;
    }
}
































