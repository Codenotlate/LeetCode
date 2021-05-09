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















