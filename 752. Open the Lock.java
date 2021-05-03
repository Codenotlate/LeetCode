// phase2 self: M1: regular BFS
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        // special case:
        if (deadSet.contains("0000")) {return -1;}
        if (target.equals("0000")) {return 0;}
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        int level = 0;
        queue.add("0000");
        visited.add("0000");
        while (!queue.isEmpty()) {
        	int size = queue.size();
        	level += 1;
        	while (size-- > 0) {
        		String cur = queue.poll();
        		for (String next: nextSet(cur)) {
        			if (next.equals(target)) {return level;}
        			if (deadSet.contains(next) || visited.contains(next)) {continue;}
        			queue.add(next);
        			visited.add(next);
        		}
        	}
        }
        return -1;
    }

    private Set<String> nextSet(String cur) {
    	char[] s = cur.toCharArray();
    	Set<String> res = new HashSet<>();
    	for (int i = 0; i < s.length; i++) {
    		int ori = s[i] - '0';
    		s[i] = (char) ((ori + 1) % 10 + '0');
    		res.add(new String(s));
    		s[i] = (char) ((ori - 1 + 10) % 10 + '0');
    		res.add(new String(s));
    		s[i] = (char) (ori +'0');
    	}
    	return res;
    }
}



// Phase 2 self: two end BFS
class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadends));
        // special case:
        if (deadSet.contains("0000")) {return -1;}
        if (target.equals("0000")) {return 0;}
        Set<String> visited = new HashSet<>();
        Set<String> startSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        int level = 0;
        startSet.add("0000");
        visited.add("0000");
        endSet.add(target);
        visited.add(target);
        while (!startSet.isEmpty() && !endSet.isEmpty()) {
        	if (startSet.size() > endSet.size()) {
        		Set<String> temp = startSet;
        		startSet = endSet;
        		endSet = temp;
        	}
        	level += 1;
        	Set<String> tempSet = new HashSet<>();
        	for (String cur: startSet) {
        		for (String next: nextSet(cur)) {
        			if (endSet.contains(next)) {return level;}
        			if (deadSet.contains(next) || visited.contains(next)) {continue;}
        			tempSet.add(next);
        			visited.add(next);
        		}
        	}
        	startSet = tempSet;
        }
        return -1;
    }

    private Set<String> nextSet(String cur) {
    	char[] s = cur.toCharArray();
    	Set<String> res = new HashSet<>();
    	for (int i = 0; i < s.length; i++) {
    		int ori = s[i] - '0';
    		s[i] = (char) ((ori + 1) % 10 + '0');
    		res.add(new String(s));
    		s[i] = (char) ((ori - 1 + 10) % 10 + '0');
    		res.add(new String(s));
    		s[i] = (char) (ori +'0');
    	}
    	return res;
    }
}