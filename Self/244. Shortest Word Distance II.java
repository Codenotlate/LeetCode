class WordDistance {
	// store that in a map, with words as key and list of index in ascending order as the value

	Map<String, List<Integer>> map;
	Map<Pair<String, String>, Integer> storedDistance;


    public WordDistance(String[] wordsDict) {
        map = new HashMap<>();
        for (int i = 0; i < wordsDict.length; i++) {
        	map.putIfAbsent(wordsDict[i], new ArrayList<>());
        	map.get(wordsDict[i]).add(i);
        }
        storedDistance = new HashMap<>();
    }
    
    public int shortest(String word1, String word2) {
    	// direcly return if its dist is already in storedDistance
    	if (storedDistance.containsKey(new Pair(word1, word2))) {
    		return storedDistance.get(new Pair(word1, word2));
    	}


        List l1 = map.getOrDefault(word1, null);
        List l2 = map.getOrDefault(word2, null);
        if (l1 == null || l2 == null) {return -1;}

        int i = 0, j = 0;
        int minDiff = Integer.MAX_VALUE;
        while (i < l1.size() && j < l2.size()) {
        	// note need to add (int) otherwise it would be bad operand to "-" between objects(Integer)
        	int diff = (int) l1.get(i) - (int) l2.get(j); 
        	minDiff = Math.min(minDiff, Math.abs(diff));
        	if (minDiff == 1) {return minDiff;} // return early if mindiff == 1
        	if (diff < 0) {
        		i++;
        	} else {
        		j++;
        	}
        }
        // store the result
        storedDistance.put(new Pair(word1, word2), minDiff);
        storedDistance.put(new Pair(word2, word1), minDiff);

        return minDiff;
    }
}

/**
 * Your WordDistance object will be instantiated and called as such:
 * WordDistance obj = new WordDistance(wordsDict);
 * int param_1 = obj.shortest(word1,word2);
 */

// can also do BS way
// https://leetcode.com/problems/shortest-word-distance-ii/discuss/150133/Java-Beats-95-BinarySearch-%2B-HashMap-2-Different-Ways

// Phase3 self
// 2 pointers way: time O(n)
class WordDistance {
    Map<String, List<Integer>> map;
    Map<Pair<String, String>, Integer> memo;

    public WordDistance(String[] wordsDict) {
        map = new HashMap<>();
        memo = new HashMap<>();
        for (int i = 0; i < wordsDict.length; i++){
            map.putIfAbsent(wordsDict[i], new LinkedList<>());
            map.get(wordsDict[i]).add(i);
        }
    }
    
    public int shortest(String word1, String word2) {
        Pair<String, String> pair = new Pair(word1, word2);
        if (memo.keySet().contains(pair)) {return memo.get(pair);}
        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);
        int p1 = 0;
        int p2 = 0;
        int minDist = Integer.MAX_VALUE;
        while (p1 < l1.size() && p2 < l2.size()) {
            int diff = Math.abs(l1.get(p1) - l2.get(p2));
            minDist = Math.min(minDist, diff);
            if (l1.get(p1) - l2.get(p2) < 0) {p1++;}
            else {p2++;}
        }
        memo.put(pair, minDist);
        return minDist;
    }
}


// Binary search way. time O(nlogn)
class WordDistance {
    Map<String, List<Integer>> map;
    Map<Pair<String, String>, Integer> memo;

    public WordDistance(String[] wordsDict) {
        map = new HashMap<>();
        memo = new HashMap<>();
        for (int i = 0; i < wordsDict.length; i++){
            map.putIfAbsent(wordsDict[i], new LinkedList<>());
            map.get(wordsDict[i]).add(i);
        }
    }
    
    public int shortest(String word1, String word2) {
        Pair<String, String> pair = new Pair(word1, word2);
        if (memo.keySet().contains(pair)) {return memo.get(pair);}
        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);
        //do bs in l2 for each element in l1
        int minDist = Integer.MAX_VALUE;
        for (int target: l1) {
            int posSmall = bsHelper(l2, target);
            if (posSmall >= 0) {minDist = Math.min(minDist, target - l2.get(posSmall));}
            if (posSmall <= l2.size() - 2) {minDist = Math.min(minDist, l2.get(posSmall + 1) - target);}
        }
        
        
        memo.put(pair, minDist);
        return minDist;
    }
    
    private int bsHelper(List<Integer> list, int target) {
        int low = 0;
        int high = list.size() - 1;
        if (target < list.get(low)) {return -1;}
        if (target > list.get(high)) {return high;}
        
        while (low < high - 1) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) >= target) {high = mid - 1;}
            else {low = mid;}
        }
        return low;
        
    }
}
























