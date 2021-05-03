// Phase2 BFS self
class Solution {
    public int minMutation(String start, String end, String[] bank) {
        Queue<String> queue = new LinkedList<>();
        Set<String> bankSet = new HashSet(Arrays.asList(bank));
        Set<String> visited = new HashSet<>();
        int level = 0;

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
        	int size = queue.size();
        	while (size-- > 0) {
        		String cur = queue.poll();
        		if (cur.equals(end)) {return level;}
        		for (String next: getValidNeighbors(cur, visited, bankSet)) {
        			queue.add(next);
        			visited.add(next);
        		}
        	}
            level++;
        }
        return -1;
    }

    private Set<String> getValidNeighbors(String cur, Set<String> visited, Set<String> bankSet) {
    	Set<String> res = new HashSet<>();
    	char[] genes = new char[]{'A', 'T', 'C', 'G'};
    	char[] curArr = cur.toCharArray();

    	for (int i = 0; i < curArr.length; i++) {
    		char orichar = curArr[i];
    		for (char c: genes) {
    			if (orichar == c) {continue;}
    			curArr[i] = c;
    			String next = new String(curArr);
    			if (!visited.contains(next) && bankSet.contains(next)) {
    				res.add(next);
    			}
    		}
    		curArr[i] = orichar;
    	}
    	return res;
    }
}


//Phase 2 self : similar to above, improve by removing string from bankSet instead of stored in visited
class Solution {
    public int minMutation(String start, String end, String[] bank) {
        Queue<String> queue = new LinkedList<>();
        Set<String> bankSet = new HashSet(Arrays.asList(bank));
        int level = 0;
        char[] genes = new char[]{'A', 'T', 'C', 'G'};

        queue.add(start);
        bankSet.remove(start);

        while (!queue.isEmpty()) {
        	int size = queue.size();
        	while (size-- > 0) {
        		String cur = queue.poll();
        		if (cur.equals(end)) {return level;}

        		char[] curArr = cur.toCharArray();
        		for (int i = 0; i < curArr.length; i++) {
		    		char orichar = curArr[i];
		    		for (char c: genes) {
		    			if (orichar == c) {continue;}
		    			curArr[i] = c;
		    			String next = new String(curArr);
		    			if (bankSet.contains(next)) {
		    				queue.add(next);
        					bankSet.remove(next);
		    			}
		    		}
		    		curArr[i] = orichar;
		    	}
        	}
            level++;
        }
        return -1;
    }
}













