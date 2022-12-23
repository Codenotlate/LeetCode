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


// Phase 3

// M1: map + minHeap
// time O(nlogk) space O(n + k)
class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
            map.put(n, map.getOrDefault(n,0) + 1);
        }
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));
        for (int n: map.keySet()) {
            pq.add(n);
            if (pq.size() > k) {pq.poll();}
        }
        
        int[] res = new int[k];
        int i = 0;
        while (!pq.isEmpty()) {
            res[i++] = pq.poll();
        }
        return res;
    }
}



// M2: map + map key partition, order on count in map
// time aveO(n)/worst O(n^2) space O(n)

class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
            map.put(n, map.getOrDefault(n,0) + 1);
        }
        
        // convert map keySet to an array: https://www.techiedelight.com/convert-set-to-array-java/
        Integer[] keys = map.keySet().toArray(new Integer[0]);
        
        int low = 0;
        int high = keys.length - 1;
        int pIdx = 0;
        while (low <= high) {
            pIdx = partitionHway(keys, low, high, map);
            if (pIdx == keys.length - k) {
                break;
            } else if (pIdx < keys.length - k) {
                low = pIdx + 1;
            } else {
                high = pIdx - 1;
            }
        }
        
        int targetCount = map.get(keys[pIdx]);
        
        int[] res = new int[k];
        int i = 0;
        for (int num: map.keySet()) {
            if (i >= k) {break;}
            if (map.get(num) >= targetCount) {
                res[i++] = num;
            }          
        }
        return res;
    }
    
    
    private int partitionHway(Integer[] nums, int low, int high, Map<Integer, Integer> map) {
        int pivot = low;
        int i = low;
        int j = high;
        
        while (true) {
            while(i < nums.length && map.get(nums[i]) <= map.get(nums[pivot])) {i++;}
            while(j >= 0 && map.get(nums[j]) > map.get(nums[pivot])) {j--;}
            if (i >= j) {break;}
            swap(nums, i, j);
        }
        swap(nums, j, pivot);
        return j;
    }
    
    private void swap(Integer[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}



// M3: map + bucket sort array
// time O(n) space O(n)

class Solution {
    public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
            map.put(n, map.getOrDefault(n,0) + 1);
        }
        
        // bukcets[i] includes the list for all numbers with count = i
        List<Integer>[] buckets = new List[nums.length + 1];
        
        // loop the map key and insert to buckets
        for (int n: map.keySet()) {
            if (buckets[map.get(n)] == null) {buckets[map.get(n)] = new ArrayList<>();}
            buckets[map.get(n)].add(n);
        }
        
        
        int[] res = new int[k];
        int j = 0;
        for (int i = buckets.length - 1; i >= 0; i--) {
            if (j >= k) {break;}
            if (buckets[i] == null) {continue;}
            for (int n: buckets[i]) {
                res[j++] = n;
            }
        }
        return res;
    }
    
    
   
}


// Review
/*initial thought
O(nlogn) way is: count each element, sort each element based on count and return the first k. space O(n)
O(n) way: count each element,  since count is in range[1, n]. Setup an array of integer list with length n+1.Loop the countMap, if num has count, arr[count].add(num).
Iterate arr backwards, insert into the result till k. space O(n)
*/
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int n:nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        // note need to define arr of arraylist this way
        ArrayList<Integer>[] arr = new ArrayList[nums.length + 1];
        for (int num: map.keySet()) {
            int count = map.get(num);
            if (arr[count] == null) {arr[count] = new ArrayList<>();}
            arr[count].add(num);
        }
        
        int[] res = new int[k];
        int i = 0;
        for (int j = arr.length - 1; j >= 0; j--) {
            if (arr[j] == null) {continue;}
            for (int x: arr[j]) {
                res[i++] = x;
            }
            if (i >= k) {break;}
        }   
        return res;
        
    }
}



// Review - bucket sort O(n) way
/*Thoughts
O(n) time to get the freq of each num in nums.
Then naive way is to sort nums based on their freq, which will take O(nlogn) time for regular sort algo. But notice here the freq will be in range [1,n], thus we can consider using counting sort, which will take O(n) time as well.
time & space O(n)
 */
// Note although both O(n) way, using list of list is much slower than using above array of List. Just pay attention to how to declare it: e.g. List<Integer>[] buckets = new List[nums.length]; And also remember to skip null in buckets in the end.
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        int[] res = new int[k];
        List<List<Integer>> buckets = new LinkedList<>();
        for (int i = 0; i < nums.length; i++){buckets.add(new ArrayList<>());}
        Map<Integer, Integer> counts = new HashMap<>();

        for (int n: nums) {
            counts.putIfAbsent(n, 0);
            counts.put(n, counts.get(n) + 1);
        }

        for (int n: counts.keySet()) {
            int count = counts.get(n);
            buckets.get(count - 1).add(n);
        }

        int curIdx = 0;
        for (int i = nums.length-1; i >= 0; i--) {
            for (int n: buckets.get(i)) {
                res[curIdx] = n;
                curIdx++;
            }
            if (curIdx == k) {break;}
        }
        return res;

    }
}












