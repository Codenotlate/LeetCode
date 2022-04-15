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



// Phase3: similar to above M2, this one is better than M1 (easier to understand)
class Solution {
    // initial idea is to build the graph, and do BFS starting source node. since we want the least number of bus, at each level we need to add all nodes sharing buses with current node. Thus the graph is actually a stopToBus map + the initial routes array.
    // possible special case to consider: source or target not in the graph, or source and target are the same
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {return 0;}
        // build the stopToBus map
        Map<Integer, List<Integer>> stopToBus = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int stop: routes[i]) {
                stopToBus.putIfAbsent(stop, new LinkedList<>());
                stopToBus.get(stop).add(i);
            }
        }
        // special case: source / target not in the routes
        if (!stopToBus.keySet().contains(source) || !stopToBus.keySet().contains(target)) {return -1;}
        
        // start BFS, each level add all nodes sharing buses with cur node
        int level = 0;
        Queue<Integer>queue = new LinkedList<>();
        // visited can record bus instead of stop number
        Set<Integer> visited = new HashSet<>();
        
        for (int bus: stopToBus.get(source)) {
            if(visited.contains(bus)) {continue;}
            visited.add(bus);
            for (int stop: routes[bus]) {
                queue.add(stop);
            }
        }
        
        while(!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int curStop = queue.poll();
                if (curStop == target) {return level+1;}
                for (int bus: stopToBus.get(curStop)) {
                    if(visited.contains(bus)) {continue;}
                    visited.add(bus);
                    for (int stop: routes[bus]) {
                        queue.add(stop);
                    }
                }
            }
            level++;
        }
        return -1;
    }
}






// Review
/* Thought
Basic idea is to build a graph and do BFS. The tricky part is here we want the num of buses but one bus has multi stops.
One way to build the graph is each node represents a bus. And is they share same stop, they have edge in between. When we at cur bus polled from queue, we check all other buses has any of the curstops, thus we need to have a stopToBus map. We added those unvisited connected bus to BFS queue and do BFS.

Another way is to build the graph with node representing stop. Similarly we will need a stopToBus map. Initially, we add all stops in the source stop related bus to the queue. Each time when we at curstop polled from queue,  we check all other unvisited buses has this curstop, and add all stops of those buses to the queue. This way we might have duplicate stops in the queue. So I think M1 is better.


Note pay attention to two special cases: when target or source not in routes; when target is same as source.

self think time is O(b^2 * s)
*/

class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // special case
        if (target == source) {return 0;}
        // build stopToBus map
        Map<Integer, Set<Integer>> stopToBus = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int stop: routes[i]) {
                stopToBus.putIfAbsent(stop, new HashSet<>());
                stopToBus.get(stop).add(i);
            }
        }
        // do BFS
        Queue<Integer> queue = new LinkedList<>();
        int level = 0;
        Set<Integer> visitedBus = new HashSet<>();
        if (!stopToBus.containsKey(source) || !stopToBus.containsKey(target)) {return -1;}
        for (int bus: stopToBus.get(source)) {queue.add(bus); visitedBus.add(bus);}
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int curbus = queue.poll();
                if (stopToBus.get(target).contains(curbus)) {return level + 1;}
                for (int stop: routes[curbus]) {
                    for (int nextbus: stopToBus.get(stop)) {
                        if (!visitedBus.contains(nextbus)) {
                            queue.add(nextbus);
                            visitedBus.add(nextbus);
                        }
                    }
                }
            }
            level++;
        }
        
        return -1;
    }
}

