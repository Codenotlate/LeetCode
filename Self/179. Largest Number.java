// phase 3 solution
// the key is to have a custom comparator. And need to prove A > B (i.e. AB > BA) and B > C (BC > CB) can transit to A > C (i.e. AC > CA). Proof see goodnotes.
// math proof: https://leetcode.com/problems/largest-number/discuss/53195/Mathematical-proof-of-correctness-of-sorting-method

// String.compareTo: The comparison is based on the Unicode value of each character in the strings.
// then sort the array using that comparator (built-in sort or self implemented)
// for O(nlogn) sort, the time is O(knlogn), k is the length of str in nums

// M1: use built-in sort and implement custom comparator as a new class
class Solution {
	// define custom comparator as a new class
	private class NewStrSort implements Comparator<String> {
		// override the compare method
		@Override
		public int compare(String a, String b){
			String order1 = a + b;
			String order2 = b + a;
			// the larger one in front in the sorted result
			return order2.compareTo(order1);
		}
	}



    public String largestNumber(int[] nums) {
        // convert int to String (use String.valueOf or Integer.toString)
        String[] numsStr = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
        	numsStr[i] = Integer.toString(nums[i]);
        }

        Arrays.sort(numsStr, new NewStrSort());
        // if largest one is 0, then return 0 immediately
        if (numsStr[0].equals("0")) {return "0";}

        StringBuilder res = new StringBuilder();
        for (String s: numsStr) {
        	res.append(s);
        }
        return res.toString();
    }
}


// M2: built-in sort and new comparator object in the solution
// https://leetcode.com/problems/largest-number/discuss/53158/My-Java-Solution-to-share
class Solution {
    public String largestNumber(int[] nums) {
        // convert int to String (use String.valueOf or Integer.toString)
        String[] numsStr = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
        	numsStr[i] = String.valueOf(nums[i]);
        }

        // have a new Comparator object in sort function and override its compare method
        Arrays.sort(numsStr, new Comparator<String>() {
        	@Override // override compare method, not compareTo!!!
        	public int compare(String s1, String s2) {
        		String o1 = s1 + s2;
        		String o2 = s2 + s1;
        		return o2.compareTo(o1);
        	}
        });

        if (numsStr[0].equals("0")) {return "0";}

        StringBuilder res = new StringBuilder();
        for (String s: numsStr) {
        	res.append(s);
        }
        return res.toString();

    }
}


// M3: self implement sort algo
// https://leetcode.com/problems/largest-number/discuss/53298/Python-different-solutions-(bubble-insertion-selection-merge-quick-sorts).
class Solution {
	private int compare(int n1, int n2) {
		String o1 = String.valueOf(n1) + String.valueOf(n2);
		String o2 = String.valueOf(n2) + String.valueOf(n1);
		return o2.compareTo(o1);
	}

    public String largestNumber(int[] nums) {
        // all kinds of sort implemented
        // bubbleSort(nums);
        // selectionSort(nums);
        // insertionSort(nums);
        // mergeSort(nums, 0, nums.length - 1);
        // quickSortLway(nums, 0, nums.length - 1);
        quickSortHway(nums, 0, nums.length - 1);


        if (nums[0] == 0) {return "0";}

        StringBuilder res = new StringBuilder();
        for (int n: nums) {
        	res.append(String.valueOf(n));
        }
        return res.toString();
    }

    // implements all kinds of sort below
    // bubble sort: time O(n^2)
    private void bubbleSort(int[] nums) {
    	for (int i = nums.length - 1; i >= 0; i--) {
    		for (int j = 0; j < i; j++) {
    			if (compare(nums[j], nums[j + 1]) > 0) {
    				int temp = nums[j];
    				nums[j] = nums[j + 1];
    				nums[j + 1] = temp;
    			}
    		}
    	}
    }

    // selection sort: time O(n^2) space O(1)
    private void selectionSort(int[] nums) {
    	for (int i = nums.length - 1; i >= 0; i--) {
    		int maxIdx = 0;
    		for (int j = 0; j <= i; j++) {   			
    			if (compare(nums[j], nums[maxIdx]) > 0) {
    				maxIdx = j;
    			}
    		}
    		// swap the largest to the end of the unsorted array
    		int temp = nums[i];
    		nums[i] = nums[maxIdx];
    		nums[maxIdx] = temp;
    	}
    }

    // insertion sort: move each cur to the correct position in the leftside sorted part
    // time O(n^2) space O(1)
    private void insertionSort(int[] nums) {
    	for (int i = 1; i < nums.length; i++) {
    		for (int j = i; j >= 1; j--) {
    			if (compare(nums[j], nums[j - 1]) < 0) {
    				int temp = nums[j];
    				nums[j] = nums[j - 1];
    				nums[j - 1] = temp;
    			} else {
    				break;
    			}
    		}
    	}
    }


    // mergeSort: time O(nlogn) space O(n)
    private void mergeSort(int[] nums, int left, int right) {
    	// don't forget this line
    	if (left >= right) {return;}
    	int mid = left + (right - left) / 2;
    	mergeSort(nums, left, mid);
    	mergeSort(nums, mid + 1, right);
    	int[] sorted = new int[right - left + 1];
    	int i = 0;
    	int l = left;
    	int r = mid + 1;
    	while (l <= mid || r <= right) {
    		if (r > right || l <= mid && compare(nums[l], nums[r]) <= 0) {
    			sorted[i++] = nums[l];
    			l++;
    		} else if (l > mid || r <= right && compare(nums[l], nums[r]) > 0) {
    			sorted[i++] = nums[r];
    			r++;
    		}
    	}

    	for (int k = left; k <= right; k++) {
    		nums[k] = sorted[k - left];
    	}
    }


    // quickSort time average O(nlogn) if use random pivot, O(n^2) worst case; space O(1)
    /* two kinds of quick sort: two partition way
    Lomuto way(pivot at most right position, scan from left to right and put smaller then pivot elements to the left)
    Hoare way (pivot at most left position), scan from left and right together and i++ and j-- until find inversion and swap i and j, do this until i > j.
	*/
	// Lway quick sort
    private void quickSortLway(int[] nums, int left, int right) {
    	if (left >= right) {return;}
    	int p = partitionRandomLway(nums, left, right);
    	quickSortLway(nums, left, p - 1);
    	quickSortLway(nums, p + 1, right);
    }

    private int partitionRandomLway(int[] nums, int left, int right) {
    	Random rd = new Random();
    	int randomPivot = rd.nextInt(right - left + 1) + left;
    	// swap the random pivot to the most right position
    	swap(nums, randomPivot, right);

    	int pivot = nums[right];
    	int smallerIdx = left;
    	for (int cur = left; cur < right; cur++) {
    		if (compare(nums[cur], pivot) < 0) {
    			swap(nums, smallerIdx, cur);
    			smallerIdx++;
    		}
    	}
    	swap(nums, smallerIdx, right);
    	return smallerIdx;
    }

    private void swap(int[] nums, int i, int j) {
    	int temp = nums[i];
    	nums[i] = nums[j];
    	nums[j] = temp;
    }


    // H way quick sort
    private void quickSortHway(int[] nums, int left, int right) {
    	if (left >= right) {return;}

    	int p = partitionRandomHway(nums, left, right);
    	quickSortHway(nums, left, p);
    	quickSortHway(nums, p + 1, right);
    }

    private int partitionRandomHway(int[] nums, int left, int right) {
    	// generate random pivot and swap to the left
    	Random rd = new Random();
    	int randomPivot = rd.nextInt(right - left + 1) + left;
    	swap(nums, left, randomPivot);

    	int small = left - 1;
    	int large = right + 1;
    	int pivot = nums[left];

    	while (true) {
    		do {small++;} while(compare(nums[small], pivot) < 0);
    		do {large--;} while(compare(nums[large], pivot) > 0);
	    
	    	if(small >= large) {
	    		return large;
	    	}
	    	swap(nums, small, large);
    	}
    	

    }
}



// Phase3 self, use PQ with custom order
// worse than above method in space, cause need extra space for PQ
class Solution {
    // basically sort the num: A>B if AB > BA
    // use a priorityQueue and define the order rule
    // convert number to string
    public String largestNumber(int[] nums) {
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>(){
            public int compare(String s1, String s2) {                
                return (s2+s1).compareTo(s1+s2); // use this directly instead of compare the number value cause the number value may exceed integer range and difference returned could also larger than integer range
            }
        });
        
        
        for (int n: nums) {
            pq.add(Integer.toString(n));
        }
        
        StringBuilder res = new StringBuilder();
        while (!pq.isEmpty()) {res.append(pq.poll());}
        // don't forget to deal with special case when starting with 0
        if (res.charAt(0) == '0') {return "0";}
        return res.toString();
    }
}








// Review


/*Initial thought
brutal force way is to have all combinations and find the max. O(n!)
improved way: sort the num first. The rule for comparin two string is: for s1,s2. if s1+s2 > s2 + s1, then s1 is better than s2.
time O(nklogn) where n is nums.len, and k is the average digit length of num in nums.
space O(n)
*/
class Solution {
    public String largestNumber(int[] nums) {
        String[] numstrs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {numstrs[i]= String.valueOf(nums[i]);}
        Arrays.sort(numstrs, (s1,s2)->((s2+s1).compareTo(s1+s2)));
        // special case with starting 0
        if(numstrs[0].equals("0")) {return "0";}
        StringBuilder res = new StringBuilder();
        for (String s: numstrs) {res.append(s);}
        
        return res.toString();
    }
}



// Review 23/1/25 - skip the implementation, one error record. Should convert to string array first as above codes.
/* Thought
M1: basically it's about the sort of the num in nums. The order of n1, n2 is actually determined by int(str(n1) + str(n2)) v.s. int(str(n2) + str(n1)). Thus we can directly sort based on this rule and append get the result.
Time O(nlogn) space O(n)

Note: reason why we can't just define a integer comparator for int[] array sort
https://stackoverflow.com/questions/29583035/no-suitable-method-found-for-sortint-anonymous-comparatorinteger
*/












