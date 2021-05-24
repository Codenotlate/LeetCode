// Phase3, solution, segment tree - bottom up
// time O(nlogM)
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
































