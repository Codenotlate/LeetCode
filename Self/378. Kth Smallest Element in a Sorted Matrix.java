class Solution {
	// method1: build a heap of self defined class element
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Element> minHeap = new PriorityQueue<>();
        for(int j = 0; j < matrix.length; j++) {
        	minHeap.offer(new Element(0, j, matrix[0][j]));
        }
        int count = 0;
        while (count < k - 1) {
        	Element e = minHeap.poll();
        	count++;
        	if (e.x >= matrix.length - 1) {continue;}
        	minHeap.add(new Element(e.x + 1, e.y, matrix[e.x + 1][e.y]));
        	
        }
        return minHeap.poll().val;
    }


    private class Element implements Comparable<Element> {
    	private int x, y, val;

    	Element(int x, int y, int val) {
    		this.x = x;
    		this.y = y;
    		this.val = val;
    	}

    	@Override
    	public int compareTo(Element other) {
    		return this.val - other.val;
    	}
    	// or we can do it without override compareTo, just use below when new a PQ
    	// PriorityQueue<Element> minHeap = new PriorityQueue<Element>((n1, n2) -> n1.val - n2.val);
    }
}



// method2: use binary search
// sorted array is the count(m), reduce the size of count() each time
// finally want to find count(x) == k
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int row = matrix.length - 1;
        int col = matrix[0].length - 1;
        int low = matrix[0][0];
        int high = matrix[row][col];
        while (low < high) {
        	int mid = (high - low) / 2 + low;
        	if (count(matrix, mid) < k) {
        		low = mid + 1;
        	} else {
        		high = mid;
        	}
        }
        return low;
    }

    private int count(int[][] m, int target) {
    	int r = 0;
    	int c = m[0].length - 1;
    	int res = 0;
    	while (r < m.length && c > 0) {
    		if (m[r][c] <= target) {
    			r++;
    			res += c + 1;
    		} else {
    			c--;
    		}
    	}
    	return res;
    }
}



//https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/301357/Java-0ms-(added-Python-and-C%2B%2B)%3A-Easy-to-understand-solutions-using-Heap-and-Binary-Search
//https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/discuss/85173/Share-my-thoughts-and-Clean-Java-Code

// Phase3 review (still need help from solution)
class Solution {
    // M1 use minheap, start with first row, then pop out one smallest element(r,c) a time, adding (r+1, c) into the minheap, stop once k element has been popped out
    // time O(min(n,k) + klog(min(n, k)))  space minO(n, k) for the minheap
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> minheap = new PriorityQueue<>((e1,e2)->matrix[e1[0]][e1[1]] - matrix[e2[0]][e2[1]]);
        // adding first row
        for (int j = 0; j < matrix.length; j++) {
            minheap.add(new int[]{0,j});
        }
        
        int popCount = 0;
        int[] popNum = new int[]{0,0};
        while (popCount < k) {
            popNum = minheap.poll();
            popCount++;
            int r = popNum[0];
            int c = popNum[1];
            if (r + 1 < matrix.length) {
                minheap.add(new int[]{r + 1, c});
            }
        }
        return matrix[popNum[0]][popNum[1]];
    }
}




class Solution {
    // M2: binary search on range
    //find min and then max, move start and end based on count(mid), which is the number of elements in matrix that is <= mid. Narrow the binary search until start and end meet. It is guaranteed that the final start will be a number in the matrix
    // time O(n * log(max - min)) space O(1)
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int start = matrix[0][0];
        int end = matrix[n-1][n-1];
        while(start < end) {
            int mid = start + (end - start) / 2;
            int count = count(matrix, mid);
            if (count < k) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }
    
    private int count(int[][] matrix, int t) {
        int r = 0;
        int c = matrix[0].length - 1;
        int res = 0;
        while (r < matrix.length && c >= 0) {
            if (matrix[r][c] <= t) {
                res += c + 1;
                r++;
            } else {
                c--;
            }
        }
        return res;
    }
}
















