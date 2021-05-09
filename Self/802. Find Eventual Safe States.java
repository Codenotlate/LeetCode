// Phase 2 self: M1 DFS hasCycle
// similar to 207 210
// time O(E + N) space O(N)
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<Integer> res = new ArrayList<>();
        int[] visited = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            if (!hasCycle(graph, i, visited)) {
                res.add(i);
            }
        }
        return res;
    }
    
    private boolean hasCycle(int[][] graph, int i, int[] visited) {
        if (visited[i] == 2) {return false;}
        if (visited[i] == 1) {return true;}
        visited[i] = 1;
        for (int next: graph[i]) {
            if (hasCycle(graph, next, visited)) {
                return true;
            }
        }
        visited[i] = 2;
        return false;
    }
}


// M2: Topological sort: BFS, but need to reverse the graph first
// start with nodes having outdegree == 0, then move backwards
// thus need to build a reverse rgraph which records all prev nodes of cur node
class Solution {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        List<Integer>[] rgraph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            rgraph[i] = new ArrayList();
        }
        int[] outdegree = new int[n];
        for (int i = 0; i < n; i++) {
            for (int next: graph[i]) {
                rgraph[next].add(i);
                outdegree[i]++;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (outdegree[i] == 0) {
                queue.add(i);
            }
        }
        
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res.add(cur);
            for (int prev: rgraph[cur]) {
                outdegree[prev]--;
                if (outdegree[prev] == 0) {
                    queue.add(prev);
                }
            }
        }
        
        Collections.sort(res);
        return res;
    }
}