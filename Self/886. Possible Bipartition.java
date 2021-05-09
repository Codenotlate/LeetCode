// same idea as 785
// use BFS here
// can also do dfs after graph is built

class Solution {
    public boolean possibleBipartition(int N, int[][] dislikes) {
        // first step : build the graph using dislikes
        // need to pay attention, here number is from 1 to n, not 0 to n-1
        List<Integer>[] graph = new ArrayList[N + 1];
        // don't forget this part
        for (int i = 0; i < N + 1; i++) {
        	graph[i] = new ArrayList();
        }
        for (int[] e: dislikes) {
        	graph[e[0]].add(e[1]);
        	graph[e[1]].add(e[0]);
        }

        // do bfs to search and try bipartition
        int[] visited = new int[N + 1];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < N + 1; i++) {
        	if (visited[i] == 0) {
        		queue.add(i);
        		visited[i] = 1;
        		while(!queue.isEmpty()) {
        			int cur = queue.poll();
        			for (int next: graph[cur]) {
        				if (visited[next] != 0) {
        					if (visited[next] != -visited[cur]) {
        						return false;
        					}
        				} else {
        					visited[next] = -visited[cur];
        					queue.add(next);
        				}
        			}
        		}
        	}
        }
        return true;
    }
}