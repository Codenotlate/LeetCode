

// Not solved yet!!
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> startSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> visited = new HashSet<>();
        Set<String> wordSet = new HashSet<>(wordList);
        Map<String, Set<String>> startParent = new HashMap<>();
        Map<String, Set<String>> endParent = new HashMap<>();
        List<List<String>> res = new LinkedList<>();
        // special case
        if (!wordSet.contains(endWord)) {return res;}
        boolean isStart = true; // used to label whether startSet/startParen is real start one, cause we do swap along the way

        startSet.add(beginWord);
        startParent.put(beginWord, null);
        visited.add(beginWord);
        endSet.add(endWord);
        endParent.put(endWord, null);
        visited.add(endWord);

        boolean hasFound = false; // used to flag when we already find the shortest length chain, then no need to go deeper level.

        while (!startSet.isEmpty() && !endSet.isEmpty()) {
        	if (startSet.size() > endSet.size()) {
        		isStart = !isStart;
        		Set<String> tempSet = startSet;
        		startSet = endSet;
        		endSet = tempSet;

        		Map<String, Set<String>> tempMap = startParent;
        		startParent = endParent;
        		endParent = tempMap;
        	}

        	Set<String> nextSet = new HashSet<>();
        	for (String cur: startSet) {
        		for (int i = 0; i < cur.length(); i++) {
        			char[] s = cur.toCharArray();
        			for (char c = 'a'; c <= 'z'; c++) {
        				s[i] = c;
        				String word = new String(s);
        				if (endSet.contains(word)) {
        					genChain(isStart, startParent, cur, endParent, word, res);
        					hasFound = true;
        				}
        				// won't go to add string into nextSet when has already find the shortest length, other possible list only exists in current level
        				if (!hasFound && !visited.contains(word) && wordSet.contains(word)) {
        					visited.add(word);
        					nextSet.add(word);
        					// add parent
        					Set<String> newP = startParent.getOrDefault(word, new HashSet<String>());
        					newP.add(cur);
        					startParent.push(word, newP);
        				}
        			}
        		}
        	}
        	if(hasFound) {break;}
        	startSet = nextSet;
        }
        return res;
    }


    private void genChain(boolean isStart, Map<String, Set<String>> startParent, String cur, Map<String, Set<String>> endParent, String word, List<List<String>> res) {
    	List<List<String>> firstHalf = new LinkedList<>();
    	List<List<String>> secondHalf = new LinkedList<>();
    	genHalf(startParent, cur, firstHalf, new LinkedList<String> curList, isStart);
    	genHalf(endParent, word, secondHalf, new LinkedList<String> curList, !isStart);
    	for (List<String> first: firstHalf) {
    		for (List<String> second: secondHarf) {
    			if (isStart) {
    				first.addAll(second);
    				res.add(first);
    			} else {
    				second.addAll(first);
    				res.add(second);
    			}
    			
    		}
    	}
    }

    private void genHalf(Map<String, Set<String>> parent, String cur, List<List<String>> res, List<String> curList) {
    	if (cur == null) {
    		if (isStart) {curList.reverse();}
    		res.add(new LinkedList<>(curList));
    		return;
    	}
    	curList.add(cur);
    	for (String next: parent.get(cur)) {
    		genHalf(parent, next, res, curList);
    	}
    	curList.remove(curList.size() - 1);
    }
}


/*

magic,
manic,
mania,
maria,
pearl,
pears,peary,
peaks,perry,
maris,marta,
paris,marks,marty,
parks,marry,party,

manic,
mania,
maria,
maris,marta,
pears,peary,
peaks,perry,
parry,perks,merry,
paris,marks,marty,
parks,marry,party,



[["magic","manic","mania","maria","maris","paris","parks","perks","peaks","pears","pearl"],
["magic","manic","mania","maria","marta","marty","marry","parry","perry","peary","pearl"],
["magic","manic","mania","maria","marta","marty","marry","merry","perry","peary","pearl"],
["magic","manic","mania","maria","marta","marty","party","parry","perry","peary","pearl"]]

[["magic","manic","mania","maria","marta","marty","party","parry","perry","peary","pearl"],
["magic","manic","mania","maria","maris","paris","parks","perks","peaks","pears","pearl"],
["magic","manic","mania","maria","marta","marty","marry","merry","perry","peary","pearl"],
["magic","manic","mania","maria","marta","marty","marry","parry","perry","peary","pearl"],



["magic","manic","mania","maria","maris","marks","parks","perks","peaks","pears","pearl"]]

[["magic","manic","mania","maria","maris","paris","parks","perks","peaks","pears","pearl"],
["magic","manic","mania","maria","maris","marks","manic","magic","perks","peaks","pears","pearl"],
["magic","manic","mania","maria","marta","marty","marry","parry","perry","peary","pearl"],
["magic","manic","mania","maria","marta","marty","marry","merry","perry","peary","pearl"],
["magic","manic","mania","maria","marta","marty","party","parry","perry","peary","pearl"]]


paris,marks,--->parks
maris,--->paris
maria,--->maris
mania,--->maria
manic,--->mania
magic,--->manic
maris,--->marks
maria,--->maris
mania,--->maria
manic,--->mania
magic,--->manic
peaks,--->perks
pears,--->peaks
pearl,--->pears
marty,--->marry
marta,--->marty
maria,--->marta
mania,--->maria
manic,--->mania
magic,--->manic
perry,--->parry
peary,--->perry
pearl,--->peary
marty,--->marry
marta,--->marty
maria,--->marta
mania,--->maria
manic,--->mania
magic,--->manic
perry,--->merry
peary,--->perry
pearl,--->peary
marty,--->party
marta,--->marty
maria,--->marta
mania,--->maria
manic,--->mania
magic,--->manic
perry,--->parry
peary,--->perry
pearl,--->peary

*/





















