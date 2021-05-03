// Phase2 self: M1
// multiple BFS at the same time using one queue
// graph node is the bus, represented by its index in routes

class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // don't forget about the special case where source == target
        if (source == target) {return 0;}

        // build the map of stops to bus index and at the same time build the graph with the node being bus index
        Map<Integer, ArrayList<Integer>> stopToBus = new HashMap<>();
        List<Integer>[] graph = new List[routes.length];
        for (int i = 0; i < graph.length; i++) {
        	graph[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < routes.length; i++) {
        	for (int stop: routes[i]) {
        		if (!stopToBus.keySet().contains(stop)) {
        			stopToBus.put(stop, new ArrayList());
        		} else {
        			for (int bus: stopToBus.get(stop)) {
        				graph[i].add(bus);
        				graph[bus].add(i);
        			}
        		}
        		stopToBus.get(stop).add(i);
        	}
        }
        // another special case: if target or source not in stopToBus
        if (!stopToBus.keySet().contains(source) || !stopToBus.keySet().contains(target)) {
        	return - 1;
        }


        // do multiple BFS at the same time, then each node should record its own level
        // node in queue is an array. array[0] stores bus index, array[1] stores the level of current node
        Queue<int[]> queue = new LinkedList<>();
        // use visited to label bus index visited
        int[] visited = new int[routes.length];
        // put node related to source into the queue
        for (int bus: stopToBus.get(source)) {
        	queue.add(new int[]{bus, 0});
        	visited[bus] = 1;
        }
        // put buses related to target into a set
        Set<Integer> targetSet = new HashSet<>(stopToBus.get(target));

        while (!queue.isEmpty()) {
        	int[] cur = queue.poll();
        	int curBus = cur[0];
        	int num = cur[1];
        	if (targetSet.contains(curBus)) {return num + 1;}
        	for (int next: graph[curBus]) {
        		if (visited[next] == 0) {
        			queue.add(new int[]{next, num + 1});
        			visited[next] = 1;
        		}
        	}
        }
        return -1;

    }
}


// phase 2 self: M2, add stops into the queue, 
// but at each level, add all stops that connects to cur stops, using routes and stopToBus map
class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // don't forget about the special case where source == target
        if (source == target) {return 0;}

        // build the map of stops to bus index and at the same time build the graph with the node being bus index
        Map<Integer, ArrayList<Integer>> stopToBus = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
        	for (int stop: routes[i]) {
        		stopToBus.putIfAbsent(stop, new ArrayList());
        		stopToBus.get(stop).add(i);
        	}
        }
        // another special case: if target or source not in stopToBus
        // below can be omitted, but adding it would be faster for source/target not included conditions
        if (!stopToBus.keySet().contains(source) || !stopToBus.keySet().contains(target)) {
        	return - 1;
        }

        

        // do BFS with each stop as a node
        // still use visited to label bus visited (this will be more efficient than label visited stops)
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        // put stops related to source into the queue
        for (int bus: stopToBus.get(source)) {
        	if (visited.contains(bus)) {continue;}
        	for (int stop: routes[bus]) {
        		queue.add(stop);
        	}
        	visited.add(bus);
        }

        // only one BFS now, need to record level and size of each level
        int level = 0;

        while (!queue.isEmpty()) {
        	int size = queue.size();
        	level++;
        	while (size-- > 0) {
        		int cur = queue.poll();
	        	if (cur == target) {return level;}
	        	for (int bus: stopToBus.get(cur)) {
		        	if (visited.contains(bus)) {continue;}
		        	for (int stop: routes[bus]) {
		        		queue.add(stop);
		        	}
		        	visited.add(bus);
		        }
        	}       	
        }
        return -1;

    }
}




