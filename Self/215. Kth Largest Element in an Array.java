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

