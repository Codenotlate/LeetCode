class Solution {
	// method 1: use minHeap time O(nlogk) space O(k + n)

	private class Node {
		private int value;
		private int freq;

		Node(int value, int count) {
			this.value = value;
			this.freq = count;
		}
	}
	// also can avoid build a private class, by directly using map in PQ's comparable lambda func
	/**
	Queue<Integer> heap = new PriorityQueue<>(
            (n1, n2) -> map.get(n1) - map.get(n2));

    also use Integer.compare is better them use '-' directly,
    cause the latter may cause overflow, when n1=MIN_VALUE and n2 < 0
	*/


    public int[] topKFrequent(int[] nums, int k) {
        // build a map with value and freq pair with one pass
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
        	map.put(n, map.getOrDefault(n, 0) + 1);
        }

        // build a k size minHeap and put all pairs as node into minHeap
        PriorityQueue<Node> minHeap = new PriorityQueue<>(
        	(a, b) -> Integer.compare(a.freq, b.freq));
        for (Map.Entry<Integer, Integer> e: map.entrySet()) {
        	minHeap.add(new Node(e.getKey(), e.getValue()));
        	if (minHeap.size() > k) {
        		minHeap.poll();
        	}
        }

        // poll all K nodes from minHeap to result array
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
        	res[i] = minHeap.poll().value;
        }
        return res;
    }
}


// method 2: use quick selection(partition) like 215
// but the comparison is not based on value but based on frequency
// since the frequence exists duplicates, it't more efficient to use H way partition
// instead of L way partition
// after finding the Kth frequent elements, iterate the freq map again and 
// return all values with frequency larger than kth element's frequency.
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // build the frequency map first
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
        	map.put(n, map.getOrDefault(n, 0) + 1);
        }

        // build an array using keys in map, i.e. the unique values in nums
        int[] unique = new int[map.size()];
        int i = 0;
        for (int n: map.keySet()) {
        	unique[i] = n;
        	i++;
        }
        // use partition to find the kth freq number
        int l = 0;
        int r = unique.length - 1;
        int kPos = unique.length - k;
        // actually no need, since order of unique is not the original order in nums
        shuffle(unique); 
        while (l < r) {
        	int pivotIndex = partitionH(unique, l, r, map);
        	if (pivotIndex == kPos) {
        		break;
        	} else if (pivotIndex > kPos) {
        		r = pivotIndex - 1;
        	} else {
        		l = pivotIndex + 1;
        	}
        }
        int freqForK = map.get(unique[kPos]);

        // based on the freqForK, iterate map again, return all values with freq >= k
        int[] res = new int[k];
        i = 0;
        for(Map.Entry<Integer, Integer> e: map.entrySet()) {
        	if (e.getValue() >= freqForK) {
        		res[i] = e.getKey();
        		i++;
        	}
        }
        return res;
    }

    private int partitionH(int[] nums, int left, int right, Map map) {
    	int pivot = nums[left];
    	int start = left;
    	int end = right;
    	while (true) {
    		while (left <= end && 
    			((int) map.get(nums[left]) <= (int) map.get(pivot))) {left++;}
    		while (right >= start && 
    			((int) map.get(nums[right]) > (int) map.get(pivot))) {right--;}
    		if (left > right) {
    			break;
    		}
    		swap(nums, left, right);
    	}
    	swap(nums, start, right);
    	return right;
    }

    private void swap(int[] nums, int i, int j) {
    	int temp = nums[i];
    	nums[i] = nums[j];
    	nums[j] = temp;
    }

    private void shuffle(int[] nums) {
    	Random random = new Random();
    	for (int i = 0; i < nums.length; i++) {
    		int j = random.nextInt(nums.length - 1);
    		swap(nums, i, j);
    	}
    }

}





// method 3 using bucket sort idea
// since the frequency is at most nums.length, we could use an array with this length
// the index of the position represents the frequency, 
// and the value of that position is an arraylist storing values with freq == index
// after this array is built, iterate backwards to find the top K elements
// Time O(n) space O(n)
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
    	// build the frequency map first
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
        	map.put(n, map.getOrDefault(n, 0) + 1);
        }


        ArrayList<Integer>[] bucket = new ArrayList[nums.length + 1];
        // iterate nums, to build bucket array
        for (Map.Entry<Integer, Integer> e: map.entrySet()) {
        	int freq = e.getValue();
        	int value = e.getKey();

        	if (bucket[freq] == null) {
        		bucket[freq] = new ArrayList<>();
        	}
        	bucket[freq].add(value);
        }

        // iterate the bucket array backwards and put the value into res
        int[] res = new int[k];
        int i = 0;
        for (int j = bucket.length - 1; j >= 0; j--) {
        	if (bucket[j] != null) {
        		for (int v: bucket[j]) {
	        		res[i] = v;
	        		i++;
	        		if (i >= k) {
	        			return res;
	        		}
	        	}
	        }

        }
        // should not reach this line
        return res;
    }
}

























