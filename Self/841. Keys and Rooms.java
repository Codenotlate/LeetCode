// Phase2 self DFS way
class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Set<Integer> visited = new HashSet<>();
        return dfs(rooms, 0, visited);
    }

    private boolean dfs(List<List<Integer>> rooms, int curRoom, Set<Integer> visited) {
    	if (visited.contains(curRoom)) {return false;}
    	visited.add(curRoom);
    	if (visited.size() == rooms.size()) {return true;}
    	for (int nextRoom: rooms.get(curRoom)) {
    		if (dfs(rooms, nextRoom, visited)) {return true;}
    	}
    	return false;
    }
}


// M2: using iteratiev BFS/DFS (Queue/Stack)
class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(0);
        visited.add(0);

        while (!queue.isEmpty()) {
        	int cur = queue.poll();
        	for (int next: rooms.get(cur)) {
        		if (visited.contains(next)) {continue;}
        		queue.add(next);
        		visited.add(next);
        		if (visited.size() == rooms.size()) {return true;}
        	}
        }
        // deal with [[]]
        return visited.size() == rooms.size();
    }
}
// or deal with [[]] this way
class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(0);
        visited.add(0);
        if (visited.size() == rooms.size()) {return true;}

        while (!queue.isEmpty()) {
        	int cur = queue.poll();
        	for (int next: rooms.get(cur)) {
        		if (visited.contains(next)) {continue;}
        		queue.add(next);
        		visited.add(next);
        		if (visited.size() == rooms.size()) {return true;}
        	}
        }
        
        return false;
    }
}