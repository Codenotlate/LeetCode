// Phase3, solution, segment tree - bottom up
// time O(nlogM)
// see goodnote and solution page for detail
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        // set the offset, size of the total possible numbers
        int offset = 10000;
        int size = 2 * 10000 + 1; // represents numbers from [-10000, 10000]
        int[] segTree = new int[2 * size];
        // The root will be stored starting from 1, the leaf node in the last layer will be stored from [n, 2*n - 1]
        List<Integer> res = new ArrayList<>();

        for (int i = nums.length - 1; i >= 0; i--) {
        	res.add(query(segTree, 0, nums[i] + offset, size));
        	update(segTree, nums[i] + offset, 1, size);
        }
        
        
        Collections.reverse(res);
        return res;
    }


    private int query(int[] segTree, int left, int right, int indexAdj) {
    	// return the number of element within the range of [left, right)
    	int leftIdx = left + indexAdj;
    	int rightIdx = right + indexAdj;
    	int res = 0;
    	while (leftIdx < rightIdx) {
    		if (leftIdx % 2 == 1) {
    			res += segTree[leftIdx];
    			leftIdx++;
    		}
    		leftIdx /= 2;
    		if (rightIdx % 2 == 1) {
    			res += segTree[--rightIdx];
    		}
    		rightIdx /= 2;
    	} 
    	return res;
    }

    private void update(int[] segTree, int nodeToUpdate, int updateVal, int size) {
    	int idxToUpdate = nodeToUpdate + size; // start with leaf and move all the way up to root
    	segTree[idxToUpdate] += updateVal;
    	while (idxToUpdate > 1) {
    		idxToUpdate /= 2;
    		segTree[idxToUpdate] = segTree[2 * idxToUpdate] + segTree[2 *idxToUpdate + 1];
    	}
    }
}


// phase3 solution2 segment-tree M2
// reference: https://leetcode.com/problems/count-of-smaller-numbers-after-self/discuss/76674/3-Ways-(Segment-Tree-Binary-Indexed-Tree-Merge-Sort)-clean-Java-code
class Solution {
	private class SegTreeNode {
		int count; // represents the count within range [min, max]
		int min, max;
		SegTreeNode left, right;

		public SegTreeNode(int min, int max) {
			this.min = min;
			this.max = max;
			count = 0;
		}

		public int mid() {
			return min + (max - min) / 2;
		}
	}


    public List<Integer> countSmaller(int[] nums) {
        // first go through the nums to get value range of num in nums
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int n: nums) {
        	min = Math.min(min, n);
        	max = Math.max(max, n);
        }

        SegTreeNode root = new SegTreeNode(min, max);
        List<Integer> resList = new LinkedList<>();

        for (int i = nums.length - 1; i >= 0; i--) {
        	resList.add(0, findCount(root, nums[i] - 1)); // find the count for range [min, nums[i]) -> [min, nums[i] - 1]
        	update(root, nums[i]);
        }

        return resList;
    }


    private int findCount(SegTreeNode root, int target) {
    	if (root == null) {return 0;}
    	if (target >= root.max) {
    		return root.count;
    	}

    	int mid = root.mid();
    	if (target <= mid) {
    		return findCount(root.left, target);
    	} else {
    		return findCount(root.left, target) + findCount(root.right, target);
    	}
    }


    private void update(SegTreeNode root, int target) {
    	if (target < root.min || target > root.max) {
    		return;
    	}

    	root.count++;
    	if (root.min == root.max) {
    		return;
    	}

    	int mid = root.mid();
    	if (target <= mid) {
    		if (root.left == null) {
    			root.left = new SegTreeNode(root.min, mid);
    		}
    		update(root.left, target);
    	} else {
    		if (root.right == null) {
    			root.right = new SegTreeNode(mid + 1, root.max);
    		}
    		update(root.right, target);
    	}
    }
}



// Phase 3 solution M3: Merge sort
/*
The key is in the merge sort, when we merge two sorted part, for a[i] in first half, the position of j pointer in the second half before a[i] is put into the overall sorted array represents the numbers that is smaller than a[i] but position-wise in the right side of the i. which is exactly what we want in the problem.
Thus we keep an array "smaller" to keep track and count the number being moved from a[i]'s right side to left side. And we need to sum up that count in each level of merge sort.

Time : O(nlogn)
space: O(n)

//https://leetcode.com/problems/count-of-smaller-numbers-after-self/discuss/76584/Mergesort-solution
*/


class Solution {
	// Note for each number, we need to know the original index position of each number after they are sorted. Thus we use Pair to pair the value of the elements with its original position in the nums.


    public List<Integer> countSmaller(int[] nums) {
        Integer[] smaller = new Integer[nums.length];
        Arrays.fill(smaller, 0);
        // can also use int[][] instead
        Pair<Integer, Integer>[] numsAndIdx = new Pair[nums.length];
        // will get TLE if put copyArr inside mergeSort
        Pair<Integer, Integer>[] copyArr = new Pair[nums.length];

        for (int i = 0; i < nums.length; i++) {
        	numsAndIdx[i] = new Pair(nums[i], i);
        }

        mergeSort(numsAndIdx, copyArr,  0, nums.length - 1, smaller);

        return Arrays.asList(smaller);
    }


    private void mergeSort(Pair<Integer, Integer>[] arr, Pair<Integer, Integer>[] copyArr, int start, int end, Integer[] smaller) {
    	if (start >= end) {return;}

    	int mid = start + (end - start) / 2;
    	mergeSort(arr, copyArr, start, mid, smaller);
    	mergeSort(arr, copyArr, mid + 1, end, smaller);

    	// since we make inplace sort in the arr itself, we need to copy it to another copyArr[] first, then put new sorted result of both half into the arr [start, end] range again
    	
    	for (int i = start; i <= end; i++) {
    		copyArr[i] = arr[i];
    	}

    	// i, j start at the head of each half array
    	int i = start;
    	int j = mid + 1;

    	while (i <= mid || j <= end) {
    		// two cases: 1) both half still has element and [i] <= [j]; 2) second half is exausted, then for the rest i, the number of jumped elements is always the length of the second half.
    		if (j > end || (i <= mid && copyArr[i].getKey().intValue() <= copyArr[j].getKey().intValue())){
    			// the j position now tells how many elements in second half arr was put into final arr before firstHalf[i]
                // System.out.print(copyArr[i].getValue().intValue());
                // System.out.print(smaller.length);
    			smaller[copyArr[i].getValue().intValue()] += j - (mid + 1);
    			// put the value into final sorted array
    			arr[i + j - (mid + 1)] = copyArr[i];
    			i++;
    		} else { // also two cases: 1) normal case, [i] >[j], put [j] in final arr; 2) i is exausted, just put [j] in final arr and no action to "smaller" array.
    			arr[i + j - (mid + 1)] = copyArr[j];
    			j++;
    		}
    	}
    }
}












// M2 binary index tree skip for now
// explanation video
// https://www.youtube.com/watch?v=uSFzHCZ4E-8


































// Still has bug in this implementation!!!!!
// phase 3 self segment tree: try top-down for query and update
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        // set the offset, size of the total possible numbers
        int offset = 10000;
        int size = 2 * 10000 + 1; // represents numbers from [-10000, 10000]
        int[] segTree = new int[4 * size];
        // The root will be stored starting from 1, the leaf node in the last layer will be stored from [n, 2*n - 1]
        List<Integer> res = new ArrayList<>();

        for (int i = nums.length - 1; i >= 0; i--) {
        	// query return count including count of right, so need to subtract those equal to right.
        	int countSmaller = query(segTree, 1, 0, size, 0, nums[i] + offset - 1);
        	res.add(countSmaller);
        	update(segTree, 1, 0, size, nums[i] + offset, 1);
        }
        
        
        Collections.reverse(res);
        return res;
    }


    private int query(int[] segTree, int index, int rangeLeft, int rangeRight, int left, int right) {
    	// return the number of element within the range of [left, right]
    	if (left <= rangeLeft && right >= rangeRight) {
    		return segTree[index];
    	}

    	if (left > rangeRight || right < rangeLeft) {
    		return 0;
    	}

    	int mid = left + (right - left) / 2;
    	if (left > mid) {
    		return query(segTree, 2 * index + 1, mid + 1, rangeRight, left, right);
    	} else if (right <= mid) {
    		return  query(segTree, 2 * index, rangeLeft, mid, left, right);
    	
    	}

    	int leftQuery = query(segTree, 2 * index, rangeLeft, mid, left, mid);
    	int rightQuery = query(segTree, 2 * index + 1, mid + 1, rangeRight, mid + 1, right);
    	return leftQuery + rightQuery;

    }

    private void update(int[] segTree, int index, int rangeLeft, int rangeRight, int nodeToUpdate, int updateVal) {
    	// update the value from root to nodeToUpdate leaf node
    	if (rangeLeft == rangeRight) {
    		//has reached the leaf, update the value directly
    		segTree[index] += updateVal;
    		return;
    	}

    	int mid = rangeLeft + (rangeRight - rangeLeft) / 2;
    	if (nodeToUpdate > mid) {
    		update(segTree, 2 * index + 1, mid + 1, rangeRight, nodeToUpdate, updateVal);
    	} else {
    		update(segTree, 2 * index, rangeLeft, mid, nodeToUpdate, updateVal);
    	}
    	segTree[index] = segTree[2 * index] + segTree[2 * index + 1];
    }
}
































