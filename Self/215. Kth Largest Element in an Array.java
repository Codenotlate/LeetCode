// method 1: sort
// time O(nlogn) space O(1)
class Solution {
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}



// method 2: use minheap with fixed size k
// time O(nlogk) space O(k)
class Solution {
    public int findKthLargest(int[] nums, int k) {
    	PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    	for (int n: nums) {
    		minHeap.offer(n);
    		if (minHeap.size() > k) {
    			minHeap.poll();
    		}
    	}
    	return minHeap.poll();
    }
}


// method 3 (most efficient way): quick selection:use partition idea similar as in quick sort
// time O(n) space O(1)
class Solution {
    public int findKthLargest(int[] nums, int k) {
    	// the kth largest element index should be nums.length - k;
    	k = nums.length - k;
    	// shuffle the array before partition to lower the possibility of worst case O(n^2)
    	shuffle(nums);
    	// partition method put the pivot value in the right position and
    	// also return that position index
    	// we shrink the search range every time until low and high meet
    	int low = 0;
    	int high = nums.length - 1;
    	while (low <= high) {
    		int pivotIndex = partition2(nums, low, high);
    		if (pivotIndex == k) {
    			return nums[k];
    		} else if (pivotIndex > k) {
    			high = pivotIndex - 1;
    		} else {
    			low = pivotIndex + 1;
    		}
    	}
    	// should not reach this row
    	return nums[low];
    	
    	
    }

    // two ways to do partition
    // first way: L-way, use right as pivot, 
    // and a smallEnd to point the end + 1 position of elements <= pivot
    private int partition1(int[] nums, int left, int right) {
    	int pivot = nums[right];
    	int smallEnd = left;
    	// by doing the following, we make sure we have scanned each element in nums
    	// and all the element <= pivot is put into range left to smallEnd - 1.
    	for (int cur = left; cur < right; cur++) {
    		if (nums[cur] <= pivot) {
    			// add that to smallEnd(by swapping) and move smallEnd
    			swap(nums, cur, smallEnd);
    			smallEnd++;
    		}
    	}
    	swap(nums, smallEnd, right);
    	return smallEnd;
    	
    }

    // second way: H-way, use left as pivot, can also use middle 
    // and start and end pointers move towards each other until start > end.
    // in the end, all the elements <= pivot is put into range 0 to start - 1
    private int partition2(int[] nums, int left, int right) {
    	int pivot = nums[left];
    	int start = left;
    	int end = right;
    	while (true) {
    		// move start to the first element from the left > pivot
    		while (start <= right && nums[start] <= pivot) {start++;}
    		// similarly move end to the first element from right that <= pivot
    		while (end >= left && nums[end] > pivot) {end--;}
    		if (start > end) {break;}
    		swap(nums, start, end);
    	}
    	// swap pivot to the right position, which is (start - 1) / end
    	swap(nums, left, start - 1);
    	return start - 1;
    }


    private void swap(int[] n, int i, int j) {
    	int temp = n[i];
    	n[i] = n[j];
    	n[j] = temp;
    }

    private void shuffle(int[] nums) {
    	Random random = new Random();
    	for (int i = 0; i < nums.length; i++) {
    		int x = random.nextInt(nums.length);
    		swap(nums, i, x);
    	}
    }
}




class Solution {
    // phase 3 from solution
    // use partition, the one at index = length - k  is the kth largest element
    // worst case time O(n^2), average O(n), but with shuffle in advance, we can lower than probability of worse case
    public int findKthLargest(int[] nums, int k) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high)  {
            int partitionIdx = partitionLway(nums, low, high);
            if (partitionIdx == nums.length - k) {
                return nums[partitionIdx];
            } else if (partitionIdx < nums.length - k) {
                low = partitionIdx + 1;
            } else {
                high = partitionIdx - 1;
            }
        }
        // should not reach this row
        return -1;      
    }
    
    // use H-way: pivot from left, both i and j move towards center
    private int partitionHway(int[] nums, int low, int high) {
        int pivot = low;
        int i = low;
        int j = high;
        while (true) {
            while (i <= high && nums[i] <= nums[pivot]) {i++;}
            while (j >= low && nums[j] > nums[pivot]) {j--;}
            if (i >= j) {break;}
            swap(nums, i, j);
        }
        swap(nums, j, pivot);
        return j;        
    }
    
    // Lway partition, use high as pivot, single i move from left to right
    private int partitionLway(int[] nums, int low, int high) {
        int pivot = high;
        // left points to the (end position+1) position of sorted list that are <= pivot value 
        int left = low;
        
        for (int i = low; i < high;i++) {
            if (nums[i] <= nums[pivot]) {
                swap(nums, i, left);
                left++;
            }
        }
        swap(nums, left, pivot);
        return left;
        
    }
    
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void shuffle(int[] nums) {
        Random rand = new Random();
        for (int i = 0; i < nums.length; i++) {
            swap(nums, i, rand.nextInt(nums.length));
        }
    }
}



// Review
/* Thinking process
naive way: sort and loop to the kth element. time O(nlogn) space O(1) based on sort
improved way 1: keep a size k minheap. Add all number into it and return minheap.peek() in the end. time O(nlogk) space O(k)
improved way 2: bucket sort. index as position and value as count. time O(max-min) space O(max-min) [Not mentioned in solution, this really depends on the min and max of the array]
improved way 3 (hint from solution): quickSort. time average O(n) worst case O(n^2). 
shuffle first to limit the chance of worst case scenario. After each quicksort, the array will be replaces into two part, all values <= pivot will be in the left of pivot, and all > pivot on the right. So if it's normal case(not worst one), we can eliminate search range for half each time. Add up to O(n)[a tree with n leaf nodes has in total 2n nodes].
*/
class Solution {                       
    public int findKthLargest(int[] nums, int k) {
        k = nums.length - k;
        shuffle(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int pivotIdx = pivot(nums, left, right);
            if (pivotIdx == k) {return nums[k];}
            else if (pivotIdx < k) {
                left = pivotIdx + 1;
            } else {
                right = pivotIdx - 1;
            }
        }
        return nums[left]; // should not reach this line if an answer is guaranteed
    }
    
    
    private int pivot(int[] nums, int left, int right) {
        int pivot = nums[left];
        int start = left + 1;
        int end = right;
        while (start <= end) {
            while (start <= end && nums[start] <= pivot) {start++;}
            while (end >= start && nums[end] > pivot) {end--;}
            if (start > end) {break;}
            swap(nums, start, end);
        }
        swap(nums, start - 1, left);
        return start - 1;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void shuffle(int[] nums) {
        Random rand = new Random();
        for (int i = 0; i < nums.length; i++) {
            int x = rand.nextInt(nums.length);
            swap(nums, i, x);
        }
    }
    
    
    
    
    
}
