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






// Phase3 self: merge sort solution
class Solution {
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[][] numIdx = new int[n][2];
        // put corresponding index and num pair into nums
        for (int i = 0; i < n; i++) {
            numIdx[i][0] = nums[i];
            numIdx[i][1] = i;
        }
        // use copy to copy numIdx on each level
        int[][] copy = new int[n][2];
        // use list count to store the final result
        List<Integer> count = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            count.add(0);
        }
        int start = 0;
        int end = n - 1;
        mergesort(numIdx, copy, start, end, count);
        return count;
    }
    
    
    private void mergesort(int[][] numIdx, int[][] copy, int start, int end, List<Integer> count) {
        if (start >= end) {return;}
        int mid = start + (end - start) / 2;
        mergesort(numIdx, copy, start, mid, count);
        mergesort(numIdx, copy, mid+1, end, count);
        // since numIdx is needed to store sorted array from merging 2 halves, we need to copy the original numIdx to copy[][]
        for (int i = start; i <= end; i++) {
            copy[i] = numIdx[i];
        }
        
        // start merge two halves
        int i = start;
        int j = mid + 1;
        while ( i <= mid || j <= end) {
            if (j > end || (i <= mid && copy[i][0] <= copy[j][0])) {
                numIdx[i + j - (mid + 1)] = copy[i];
                count.set(copy[i][1], count.get(copy[i][1]) + j - (mid + 1));
                i++;
            } else {
                numIdx[i + j - (mid + 1)] = copy[j];
                j++;
            }
        }
    }
}


class Solution {
    // phase 3 self
    // use segment tree:  time O(nlog(M)) space O(M)
    /*
    1.since the number is in range [-10^4, 10^4], we can set a segment tree with leaf nodes representing the count of number from -10^4 to 10^4 (assume M = 2 * 10^4 + 1)in nums. 
    2.We use an array of size 2 * M to represent the segment tree and the root node starts at index = 1. (Because segment tree is complete tree, if it has n leaf nodes, it will have n-1 other notes, and in total 2n - 1 nodes)
    3. since array index starts at 0, whereas the min can be -10^4. Thus we use on offset= 10^4 to make the number range starts 0 consistent with the array index. All the leaf nodes(numbers + offset range from 0 to M) indexes range from M to 2*M-1  Thus, assume num in nums, the leaf node index of num in the segment tree array is num + offset + 2*M + 1
    4. In this problem, the node stores the count of number in nums that is in that range represented by that node. Thus the initial value of each node is zero. Since we store it in an array and automatically initialized as 0, we don't need to build the segment tree in this problem.
    5. Since the problem is count the number smaller in the right, we need to traverse nums starting from right side. For each num in nums, we query the node value representing range [-10^4, num-1] or [-10^4, num), put it in the corresponding position in result list. Then update the values of nodes in the path to leaf node "num".
    6. The segment tree artical uses topdown way for build/query/update. In this question's solution, it uses bottom-up and thus requires less space.
    
    */
    public List<Integer> countSmaller(int[] nums) {
        int offset = 10000;
        int size = 2 * 10000 + 1;
        // array for segment tree
        int[] tree = new int[2 * size];
        List<Integer> res = new LinkedList<>();
        // traverse nums in reverse order
        for (int i = nums.length - 1; i >= 0; i--) {
            res.add(0, query(tree, 0, nums[i] + offset - 1, size)); // tree, low, high, size for leaf index calculation
            update(tree, nums[i] + offset, 1, size); // tree, the node to update with adding 1, size for leaf index calculation
        }
        return res;
    }
    
    // bottom-up way: want to find the count in range [low, high]
    private int query(int[] tree, int low, int high, int size) {
        // get leaf indexes
        low += size;
        high += size;
        int res = 0;
        while (low < high) {
            if(low % 2 == 1) { // low is the right child
                res += tree[low];
                low++;      
            }
            low /= 2;
            if (high % 2 == 0) { // right is the left child   
                res += tree[high];
                high--;
            }
            high /= 2;
        }
        if (low == high) {res += tree[low];}
        return res;
    }
    
    
    private void update(int[] tree, int index, int val, int size){
        index += size; // go to the leaf node
        while (index >= 1) {
            tree[index] += val;
            index /= 2;
        }
    }
}























