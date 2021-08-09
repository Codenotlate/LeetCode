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



// phase 3 self
// M1: buckets sort
class Solution {
    public String frequencySort(String s) {
        // main idea: bucket sort, each bucket represents the freq of the occurance of that char
        Set<Character>[] buckets = new HashSet[s.length() + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new HashSet<>();
        }
        Map<Character, Integer> count = new HashMap<>();
        for (char c: s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
            int cnt = count.get(c);
            buckets[cnt].add(c);
            buckets[cnt - 1].remove(c);
        }
        StringBuilder res = new StringBuilder();
        for (int i = buckets.length - 1; i >= 0; i--) {
            if (buckets[i].size() != 0) {
                for (char c: buckets[i]) {
                    for (int k = 0; k < i; k++) {
                        res.append(c);
                    }
                }
            }            
        }
        return res.toString();
    }
}



class Solution {
    public String frequencySort(String s) {
        //main idea: same count map, but using heap instead of buckets to store chars in freq order
        Map<Character, Integer> count = new HashMap<>();
        for (char c: s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        
        PriorityQueue<Character> heap = new PriorityQueue<>((a,b) -> -Integer.compare(count.get(a), count.get(b)));
        
        heap.addAll(count.keySet());
        
        StringBuilder res = new StringBuilder();
        while (!heap.isEmpty()) {
            char c = heap.poll();
            for (int k = 0; k < count.get(c); k++) {
                res.append(c);
            }
        }
        return res.toString();
        
    }
}













