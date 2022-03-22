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


// phase3 self
class Solution {
    // M1: BFS way
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new List[numCourses];
        int[] indegree = new int[numCourses];
        // build the graph
        for (int[] e:prerequisites) {
            if(graph[e[1]] == null) {graph[e[1]] = new LinkedList<>();}
            graph[e[1]].add(e[0]);
            indegree[e[0]]++;
        }
        
        // start BFS, put all indegree = 0 nodes int the queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {queue.add(i);}
        }
        int pollNum = 0;
        int[] res = new int[numCourses];
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res[pollNum] = cur;
            pollNum++;
            if (graph[cur] == null) {continue;}
            for (int next: graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) {queue.add(next);}
            }
        }
        
        return pollNum == numCourses? res: new int[0];       
    }
}

class Solution {
    // M2: DFS way, note the trick is to add the number to final result in a reverse postorder way (do it in hasCircle recursion)
    // also use one arr to label two status of a node: 1 for isValid, 2 for isVisited
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new List[numCourses];
        // build the graph
        for (int[] e:prerequisites) {
            if(graph[e[1]] == null) {graph[e[1]] = new LinkedList<>();}
            graph[e[1]].add(e[0]);
        }
        
        int[] status = new int[numCourses];
        int[] res = new int[numCourses];
        int[] respos = new int[]{numCourses - 1}; // used to reverse the postorder,use array instead of integer, because the value needs to be passed along recursion
        for (int i = 0; i < numCourses; i++) {
            if (hasCircle(graph, i, status, respos, res)) {return new int[0];}
        }
        
        return res; // respos == -1 is guaranteed to this line       
    }
    
    private boolean hasCircle(List<Integer>[] graph, int i, int[] status, int[] respos, int[] res) {
        if (status[i] == 1) {return false;}
        if (status[i] == 2) {return true;}
        status[i] = 2;
        if (graph[i] != null) {
            for (int next: graph[i]) {
                if (hasCircle(graph, next, status, respos, res)) {return true;}
            }
        }
        // postorder add to res reversely
        res[respos[0]--] = i;
        status[i] = 1; // label as valid
        return false;
    }
}






// Review
/*Initial thought
Build the graph. pair[a,b] means there is an directed edge from b to a. 
M1: Do topological search. use a queue, put all nodes with inCount = 0. pop out along the way and adjust the inCount for its neighbors. Until queue is empty.If the search ends with less then numCourses, then meaning there's impossible to do it and return empty array. Otherwise the order of the nodes popped out of the queue is the order in the final result.
time O(V + E) space O(V + E)
M2: DFS + 3 status to detect cycle and add result in reverse order. Try next time.
*/
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inCounts = new int[numCourses];
        // build the graph
        for (int[] edge: prerequisites) {
            graph.putIfAbsent(edge[1], new LinkedList<>());
            graph.get(edge[1]).add(edge[0]);
            inCounts[edge[0]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {if(inCounts[i] == 0) {queue.add(i);}}
        int[] res = new int[numCourses];
        int resIdx = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res[resIdx++] = cur;
            for (int next: graph.getOrDefault(cur, new LinkedList<>())) {
                inCounts[next]--;
                if (inCounts[next] == 0) {queue.add(next);} 
            }
        }
        
        return resIdx == numCourses ? res : new int[0];
    }
}
























