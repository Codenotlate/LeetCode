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