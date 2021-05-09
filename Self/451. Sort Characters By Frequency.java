// method1: similar bucket idea as 347
// time O(n) space O(n)

class Solution {
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c: s.toCharArray()) {
        	map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // put into bucket
        List<Character>[] bucket = new ArrayList[s.length() + 1];
        for (Map.Entry<Character, Integer> e: map.entrySet()) {
        	if (bucket[e.getValue()] == null) {
        		bucket[e.getValue()] = new ArrayList<>();
        	}
        	bucket[e.getValue()].add(e.getKey());
        }

        // iterate bucket backwards, append char with index times into stringbuilder
        StringBuilder res = new StringBuilder();
        for (int j = bucket.length - 1; j >= 0; j--) {
        	if (bucket[j] != null) {
        		for (char c: bucket[j]) {
        			int time = j;
        			while (time > 0) {
        				res.append(c);
        				time--;
        			}
        		}
        	}
        }

        return res.toString();
    }
}




// method 2: using minHeap similar to 347 as well
// time O(n + mlogm) = O(n), space O(m)
class Solution {
    public String frequencySort(String s) {
    	// again build the map first
    	Map<Character, Integer> map = new HashMap<>();
        for (char c: s.toCharArray()) {
        	map.put(c, map.getOrDefault(c, 0) + 1);
        }

    	// define comparator for minHeap, the char with highest freq should be at the top of heap
        PriorityQueue<Character> minHeap = new PriorityQueue<>(
        	(a, b) -> -Integer.compare(map.get(a), map.get(b)));

        minHeap.addAll(map.keySet());

        // append char with map.get(c) times into stringbuilder
        StringBuilder res = new StringBuilder();
        while (!minHeap.isEmpty()) {
        	char c = minHeap.poll();
        	int time = map.get(c);
        	while (time > 0) {
        		res.append(c);
        		time--;
        	}
        }

        return res.toString();


    }
}



















