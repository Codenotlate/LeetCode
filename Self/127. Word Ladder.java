class Solution {
	// method1: regular BFS using queue
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        // special case
        if (!wordSet.contains(endWord)) {
        	return 0;
        }
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        int level = 0;
        queue.add(beginWord);
        visited.add(beginWord);

        while (!queue.isEmpty()) {
        	int size = queue.size();
        	while (size > 0) {
        		String cur = queue.poll();
        		if (cur.equals(endWord)) {
        			return level + 1;
        		}
        		// adding neighbors into the queue
        		// neighbors need to be in wordList and only one char diff from cur
        		for (int i = 0; i < cur.length(); i++) {
        			// note: need to change [i] back to original one to start with the next i.
        			char[] curArray = cur.toCharArray();
        			for (char c = 'a'; c <= 'z'; c++) {
        				curArray[i] = c;
        				String next = String.valueOf(curArray);
        				if (wordSet.contains(next) && !visited.contains(next)) {
        					queue.add(next);
        					visited.add(next);
        				}
        			}
        		}
        		size--;
        	}
        	level++;
        }
        return 0;
    }
}


// method 2: bidirectional BFS, using 2 set instead of queue for startWord and end Word.
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
		Set<String> wordSet = new HashSet<>(wordList);
		// special case
		if (!wordSet.contains(endWord)) {
			return 0;
		}
		Set<String> startSet = new HashSet<>();
		Set<String> endSet = new HashSet<>();
		// visited can use the same one, cause when there's overlap, the search ends
		Set<String> visited = new HashSet<>();


		int level = 1;
		startSet.add(beginWord);
		visited.add(beginWord);
		endSet.add(endWord);
		visited.add(endWord);

		while (!startSet.isEmpty() && !endSet.isEmpty()) {
			// always search the smaller set for next search range
			// adjust startSet and endSet if startSet.size() > endSet.size()
			// if we swap start and end without comparing their size, the runtime went to from 14mx to 40ms, but is still faster than 70ms for regular BFS
			if (startSet.size() > endSet.size()) {
				Set<String> swapTemp = endSet;
				endSet = startSet;
				startSet = swapTemp;
			}

			Set<String> nextSet = new HashSet<>();
			for (String cur: startSet) { // same as queue.poll() in regular BFS
        		// adding neighbors into the queue
        		// neighbors need to be in wordList and only one char diff from cur
        		for (int i = 0; i < cur.length(); i++) {
        			// note: need to change [i] back to original one to start with the next i.
        			char[] curArray = cur.toCharArray();
        			for (char c = 'a'; c <= 'z'; c++) {
        				curArray[i] = c;
        				String next = String.valueOf(curArray);
        				if (endSet.contains(next)) {
		        			return level + 1;
		        		}
        				if (wordSet.contains(next) && !visited.contains(next)) {
        					nextSet.add(next);
        					visited.add(next);
        				}
        			}
        		}
			}
			startSet = nextSet;
			level++;
		}
		return 0;

    }
}




// phase 2:
// M1: regular BFS
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        int[] visited = new int[wordList.size()];
        int level = 0;
        queue.add(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            level += 1;
            while (size-- > 0) {
                String cur = queue.poll();
                if (cur.equals(endWord)) {return level;}
                HashSet<String> adjSet = adjWords(cur);
                for (int i = 0; i < wordList.size(); i++) {
                    if (visited[i] != 1 && adjSet.contains(wordList.get(i))) {
                        visited[i] = 1;
                        queue.add(wordList.get(i));
                    }
                }
            }
        }
        return 0;
    }
    
    private HashSet adjWords(String cur) {
        HashSet<String> res = new HashSet<>();
        char[] s = cur.toCharArray();
        for (int i = 0; i < s.length; i++) {
            char ori = s[i];
            for (char c = 'a'; c <= 'z'; c++) {
                s[i] = c;
                res.add(new String(s));
            }
            s[i] = ori;
        }
        return res;
    }
}



// Phase3 
// M1 regular BFS
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int level = 0;
        Queue<String> queue = new LinkedList<>();
        Set<String> wordSet = new HashSet(wordList);
        // special case
        if (!wordSet.contains(endWord)) {return level;}
        
        queue.add(beginWord);
        while (!queue.isEmpty()) {
            int size = queue.size();
            level++;
            while (size-- > 0) {
                String cur = queue.poll();
                if (cur.equals(endWord)) {return level;}

                Set<String> validNeigh = getValidNeighbors(cur, wordSet);
                for (String s: validNeigh) {
                    queue.add(s);
                }
            }
            
        }
        return 0;
    }
    
    private Set<String> getValidNeighbors(String cur, Set<String> wordSet) {
        Set<String> res = new HashSet<>();
        for (int i = 0; i < cur.length(); i++) {
            char[] chars = cur.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String neighbor = String.valueOf(chars);
                //String neighbor = chars.toString(); -- can't use this one, it returns an array of string
                // https://www.tutorialspoint.com/java/util/arrays_tostring_char.htm
                if (wordSet.contains(neighbor)) {
                    res.add(neighbor);
                    wordSet.remove(neighbor);
                }
            }
        }
        return res;
    }
}




// phase 3 self M2
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet(wordList);
        int level = 0;
        if (!wordSet.contains(endWord)) {return level;}
        Set<String> startSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        
        startSet.add(beginWord);
        level++;
        endSet.add(endWord);
        wordSet.remove(endWord); // label as visited
        
        while (!startSet.isEmpty() && !endSet.isEmpty()) {
            // swap if startSet size > endSet size
            if (startSet.size() > endSet.size()) {
                Set<String> temp = startSet;
                startSet = endSet;
                endSet = temp;
            }
            
            Set<String> nextSet = new HashSet<>();  
            level++;
            for (String cur: startSet) {
                for (int i = 0; i < cur.length(); i++) {
                    char[] chars = cur.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[i] = c;
                        String neighbor = String.valueOf(chars);
                        if (endSet.contains(neighbor)) {return level;}
                        if (wordSet.contains(neighbor)) {
                            nextSet.add(neighbor);
                            wordSet.remove(neighbor);
                        }
                    }
                }
            }
            startSet = nextSet;
        }
        return 0;
        
    }
}












