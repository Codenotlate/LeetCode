class Solution {
	// similar partition idea as quicksort
	// but with 3 pointers, separating into 4 ranges
	// [0, l)  -- < pivot
	// [l, m) -- == pivot
	// [m, h] -- unsearched area
	// (h, len - 1] -- > pivot
	// m represents the start of the unsearched area also viewed as the current search position
	// Time O(n) one pass, Space O(1)
    public void sortColors(int[] nums) {
        int l = 0;
        int m = 0;
        int h = nums.length - 1;

        int pivot = 1;
        while (m <= h) {
        	if (nums[m] < pivot) {
        		swap(nums, m, l);
        		l++;
        		m++; 
        		// since the value swap from l is always <= pivot,
        		// it won't break the order, thus we can move m to next.
        	} else if (nums[m] > pivot) {
        		swap(nums, m, h);
        		h--;
        		// we can't do m++ here, since the value swap from h could be < pivot
        		// thus we need to check it before move m to next
        	} else {
        		m++;
        	}
        }
    }

    private void swap(int[] nums, int i, int j) {
    	int t = nums[i];
    	nums[i] = nums[j];
    	nums[j] = t;
    }
}


// ideas from discussion
/** similar idea, n0, n1 and n2 always point to the last number of sub-array 
"000...", "111..." and "222...", respectively. So you need to 
move n2 and n1 if you want to insert a '0', 
but you don't need to move n0 and n1 when inserting a '2' since n2 is at the end of the array.
*/
class Solution {
// one pass in place solution
	void sortColors(int A[], int n) {
	    int n0 = -1, n1 = -1, n2 = -1;
	    for (int i = 0; i < n; ++i) {
	        if (A[i] == 0) 
	        { 
	            A[++n2] = 2; A[++n1] = 1; A[++n0] = 0; // The order here matters
	        }
	        else if (A[i] == 1) 
	        {
	            A[++n2] = 2; A[++n1] = 1;
	        }
	        else if (A[i] == 2) 
	        {
	            A[++n2] = 2;
	        }
	    }
	}
}


// This problem also in oneNote laiOffer


// method3: two pass counting sort
// time O(n) space O(1)
class Solution {
	public void sortColors(int[] nums) {
	    // 2-pass
	    int count0 = 0, count1 = 0, count2 = 0;
	    for (int i = 0; i < nums.length; i++) {
	        if (nums[i] == 0) {count0++;}
	        if (nums[i] == 1) {count1++;}
	        if (nums[i] == 2) {count2++;}
	    }
	    for(int i = 0; i < nums.length; i++) {
	        if (i < count0) {nums[i] = 0;}
	        else if (i < count0 + count1) {nums[i] = 1;}
	        else {nums[i] = 2;}
	    }
	
	}
}



// Phase3 not-self
class Solution {
    public void sortColors(int[] nums) {
        // pivot sort related, but two pointers to separate into 3 sorted range and one unsearched range
        int p0 = 0, p1 = 0, p2 = nums.length - 1;
        int pivot = 1;
        while (p1 <= p2) {
            if (nums[p1] == pivot) {p1++;}
            else if (nums[p1] > pivot) {
                swap(nums, p1, p2);
                p2--;
            } else {
                swap(nums, p0, p1);
                p0++;
                p1++;
            }
        }
    }
    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}

// Phase3 not-self
class Solution {
    public void sortColors(int[] nums) {
        int p0 = 0, p1 = 0, p2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 2) {
                nums[p2++] = 2;
            } else if (nums[i] == 1) {
                nums[p2++] = 2;
                nums[p1++] = 1;
            } else {
                nums[p2++] = 2;
                nums[p1++] = 1;
                nums[p0++] = 0;
            }
        }
    }
}


// review M1
class Solution {
    public void sortColors(int[] nums) {
        int i = 0;
        int sml = 0;
        int lg = nums.length - 1;
        int p = 1; // as pivot here
        while (i <= lg) {
            if (nums[i] > p) {
                swap(nums, i, lg);
                lg--;
            } else if (nums[i] < p) {
                swap(nums, i, sml);
                sml++;
                i++;
            } else {
                i++;
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}



// Review
/*Initial thought
Use the partition tech, basically need 3 pointers to label the end of each part. We may have different ways to set the init position of the pointers. One way is to have all three initialized at index 0. And each time we process an element, if it's in part2, then simply move part2 pointer. If it's in part1, then we need to move both part1 and part2 pointers. And if it's in part 0, then we need to move all three pointers together.
Time O(n) with onew pass, and space O(1)
Another way to init pointers is to have part0, part1 pointers starting at index 0 and part2 pointers starting at last position and move backwards. If current element(use part1 pointer) is in part2, swap the value and move part2 pointer--. If current element is in part 0, swap value with part0 pointer and move part0&part1 pointer++. If current element in part 1, just move part1 pointer++. Do this till part1 pointer > part2 pointer.
*/

class Solution {
    public void sortColors(int[] nums) {
        int p0 = 0, p1 = 0, p2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                nums[p2++] = 2; 
                nums[p1++] = 1;
                nums[p0++] = 0;
                              
            } else if (nums[i] == 1) {
                nums[p2++] = 2;
                nums[p1++] = 1;
            } else {
                nums[p2++] = 2;
            }
        }
    }
}

class Solution {
    public void sortColors(int[] nums) {
        int p0 = 0;
        int p2 = nums.length - 1;
        int i = 0;
        while ( i <= p2) {
            if (nums[i] > 1) {
                swap(nums, i, p2);
                p2--;
            } else if (nums[i] < 1) {
                swap(nums, i, p0);
                p0++;
                i++;
            } else {
                i++;
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}



