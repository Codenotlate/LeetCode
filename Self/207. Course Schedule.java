// topological sort using BFS queue

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // first build the graph
        ArrayList<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
        	graph[i] = new ArrayList<>();
        }
        int[] inDegree = new int[numCourses];
        // and calculate inbound degree for each node along the process
        for (int i = 0; i < prerequisites.length; i++) {
        	int pre = prerequisites[i][1];
        	int cur = prerequisites[i][0];
        	graph[pre].add(cur);
        	inDegree[cur]++;
        }
        //go through each node in the graph and put those with indegree==0 in to queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
        	if (inDegree[i] == 0) {
        		queue.add(i);
        	}
        }

        // start the while loop with queue
        int count = 0; // count tracks the length of the final sorted result
        while (!queue.isEmpty()) {
        	int cur = queue.poll();
        	count++;
        	// for every neighbor of cur node
        	for (Integer next: graph[cur]) {
        		inDegree[next]--;
        		if (inDegree[next] == 0) {
        			queue.add(next);
        		}
        	}
        }

        // if the topologial sort has same size as numCourses, then succeed
        // otherwise, topological sort failed, meaning there's a cycle inside the graph
        return count == numCourses;
    }
}


// DFS way: detect cycles

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // build graph similar to BFS
        ArrayList<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
        	graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < prerequisites.length; i++) {
        	int pre = prerequisites[i][1];
        	int cur = prerequisites[i][0];
        	graph[pre].add(cur);
        }

        // two boolean list
        // passed to track nodes has no cycle in subgraph
        // marked to track nodes have been visited in current path
        boolean[] passed = new boolean[numCourses];
        boolean[] marked = new boolean[numCourses];

        // since graph maybe disconnected, thus need to iterate every node
        for (int i = 0; i < numCourses; i++) {
        	if (hasCycle(graph, passed, marked, i)) {
        		return false;
        	}
        }
        return true;
    }


    private boolean hasCycle(ArrayList<Integer>[] graph, boolean[] passed,
    	boolean[] marked, int i) {
    	// if i already in passed, meaning we have checked i, and no cycle in subgraph of i
    	if (passed[i]) {return false;}
    	// if i already in marked, meaning it's visited in cur path, thus having cycle
    	if (marked[i]) {return true;}

    	// label as visited
    	marked[i] = true;
    	// dfs its neighbors

    	for(Integer next: graph[i]) {
    		if (hasCycle(graph, passed, marked, next)) {
    			return true;
    		}
    	}
    	// meaning subgraph with node i has no cycle
    	passed[i] = true;
    	return false;
    }
}

//https://leetcode.com/problems/course-schedule/discuss/58524/Java-DFS-and-BFS-solution
//knowledge2thepeople's answer in comment


// phase2 M1 self: BFS
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //build the graph with adjacent list
        List<Integer>[] graph = new ArrayList[numCourses];
        // don'y use Arrays.fill here, only applies to int val
        //Arrays.fill(graph, new ArrayList<Integer>());
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        int[] indegree = new int[numCourses];
        for (int[] edge: prerequisites) {
            graph[edge[1]].add(edge[0]);
            indegree[edge[0]]++;
        }
        // put nodes with indegree == 0 into queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {queue.add(i);}
        }
        // start BFS
        int count = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            count++;
            for (int next: graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue.add(next);
                }
            }
        }
        
        return count == numCourses;
    }
}

// Phase2 self dfs way
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge: prerequisites) {
            graph[edge[1]].add(edge[0]);
        }
        
        int[] valid = new int[numCourses];
        int[] curPathVisited = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            if (hasCycle(graph, valid, curPathVisited, i)) {
                return false;
            }
        }
        return true;
    }
    
    
    private boolean hasCycle(List<Integer>[] graph, int[] valid, int[] curPathVisited, int i) {
        if (valid[i] == 1) {return false;}
        if (curPathVisited[i] == 1) {return true;}
        curPathVisited[i] = 1;
        for (int next: graph[i]) {
            if (hasCycle(graph, valid, curPathVisited, next)) {
                return true;
            }
        }
        valid[i] = 1;
        return false;
    }
}


// phase3 BFS way: M1
class Solution {
    // time O(E + V) space O(E + v)
    // bfs way, start with nodes indegree = 0
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        // build the graph and count the indegree for each node
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] e: prerequisites) {
            graph.putIfAbsent(e[1], new LinkedList<Integer>());
            graph.get(e[1]).add(e[0]);
            indegree[e[0]]++;
        }
        
        // bfs, put indegree = 0 nodes into the queue
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {queue.add(i);}
        }
        int pollCount = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            pollCount++;
            if (!graph.keySet().contains(cur)) {continue;}
            for (int next: graph.get(cur)) {
                indegree[next]--;
                if (indegree[next] == 0) {queue.add(next);}               
            }
        }
        return pollCount == numCourses;      
    }
}

// Phase3 M2
class Solution {
    // time O(E + V) space O(E + v)
    // dfs way: detect circles
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // build the graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] e: prerequisites) {
            graph.putIfAbsent(e[1], new LinkedList<Integer>());
            graph.get(e[1]).add(e[0]);
        }
        
        // dfs circle detect for each node
        int[] memo = new int[numCourses]; // memo[i] = 1 means subgraph starting with node i has no cycle
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < numCourses; i++){
            if (hasCircle(graph, i, memo, visited)) {return false;}
        }
        return true;
    }
    
    private boolean hasCircle(Map<Integer, List<Integer>> graph, int i, int[] memo, Set<Integer> visited) {
        if (memo[i] == 1) {return false;}
        if (visited.contains(i)) {return true;}
        visited.add(i);
        if (graph.keySet().contains(i)){
            for (int next: graph.get(i)) {
                if (hasCircle(graph, next, memo, visited)) {return true;}
            }
        }      
        visited.remove(i);
        memo[i] = 1;
        return false;
    }
}





