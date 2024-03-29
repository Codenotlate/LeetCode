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


// Review
class Solution {
    // Review M1: n sorted arrays to find the kth element. Using a minHeap
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((p1, p2) -> matrix[p1[0]][p1[1]] - matrix[p2[0]][p2[1]]);
        for (int j = 0; j < matrix[0].length; j++) {
            pq.add(new int[]{0, j});
        }
        
        int popSize = 0;
        int popNum = 0;
        while (!pq.isEmpty() && popSize < k) {
            int[] popPair = pq.poll();
            int r = popPair[0];
            int c = popPair[1];
            if (r + 1 < matrix.length) {
                pq.add(new int[]{r + 1, c});
            }
            popNum = matrix[r][c];
            popSize++;
        }
        
        if (popSize != k) {return -1;}
        return popNum;
    }
}

class Solution {
    // use binary search on range, find the one num in matrix that has k elements in matrix that <= it.
    // since O(log(max- min) time to do Binary search and with each search O(n) time to find count, total time is O(n * log(max - min))
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0];
        int high = matrix[n-1][n-1];
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            int count = countMatrix(matrix, mid);
            if (count < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;        
    }
    
    private int countMatrix(int[][] matrix, int target) {
        int n = matrix.length;
        int r = 0;
        int c = n - 1;
        int res = 0;
        while (r < n && c >= 0) {
            if (target >= matrix[r][c]) {
                res += c + 1;
                r += 1;
            } else {
                c -= 1;
            }
        }
        return res;
    }   
    
}



// Review
/* Initial thought
    * naive way: put all n^2 values into an array and sort that array and return the number with rank k. time O(n^2log(n^2)) space O(n^2)
    * another naive way: using a maxheap with size k, add every element into the heap. poll out the peek of maxheap if size is larger than k to maintain a k size. time O(n^2log(k)) space O(k)
    * improved way1: consider the attribute of the matrix: rows and cols are sorted in ascending order. View this as merge n sorted array. put the first row into the queue, poll out the smallest, and add (cur[0]+1, cur[1]) to the queue. repeat the process till poll out k elements. time O(klog(n)) space O(n)
    * improved way2: consider binary search. Our goal is to find a number in range [min, max] of matrix that has k elements <= it. binary search takes O(log(max-min)) round. Within each round, find how many elements <= target takes O(n). Thus total time O(nlog(max-min)) space O(1)
    */
// improved way 1
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a,b)->(matrix[a[0]][a[1]] - matrix[b[0]][b[1]]));
        int n = matrix.length;
        for (int j = 0; j < n; j++) {heap.add(new int[]{0,j});}
        int[] polled = new int[2];
        while(k-- > 0) {
            polled = heap.poll();
            if(polled[0] < n-1) {heap.add(new int[]{polled[0]+1, polled[1]});}         
        }
        return matrix[polled[0]][polled[1]];
    }
}
// improved way 2
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int low = matrix[0][0];
        int high = matrix[n-1][n-1];
        
        while(low < high) {
            int mid = low + (high-low) / 2;
            int count = countNum(matrix, mid);
            // if (count == k) {return mid;} you can't have this line, because mid might not be the number in the matrix
            if(count < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        
        return low;
    }
    
    private int countNum(int[][] matrix, int target) {
        int n = matrix.length;
        int r = 0;
        int c = n-1;
        int res = 0;
        while (r < n && c >= 0) {
            if (target >= matrix[r][c]) {
                res += c + 1;
                r++;
            } else {
                c--;
            }
        }
        return res;
    }
}



 



/*Thought
M1: use PQ for each top value at each column. Then time O(klogn) space O(min(k, n))
M2: use BS to have space O(1). the answer will be in range (min, max). And we want to find a number that has count(<=) == k. We reduce the range each time based on the comparison between count(<=target) and k. Also given the sorted attributes, we can do count() in O(n) times: start with [0,n-1], each time reduce a row or a col.
Thus M2 time O(nlog(max-min))  space O(1)
*/
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int max = matrix[n-1][n-1];
        int min = matrix[0][0];
        while (min < max) {
            int mid = min + (max - min) / 2;
            int count = count(matrix, mid);
            if (count < k) {min = mid + 1;}
            else {max = mid;}
        }
        return min; // should not reach this line
    }
    
    private int count(int[][] mat, int mid) {
        int r = 0;
        int c = mat.length - 1;
        int count = 0;
        while (r < mat.length && c >= 0) {
            if (mat[r][c] <= mid) {
                count+= c+1;
                r++;
            } else {
                c--;
            }
        }
        return count;
    }
}




// Review - still need hint to some extent
/* Thoughts
M1: naive way - simply sort the whole matrix using a max PQ with size k, time O(n^2logk) space O(k)

M2: optimized based on M1. Again use a min PQ, but this time, we view each column as a sorted array, we put all the (r,c) in first row to the pq first. The number popped out each time must be the min in all non-popped numbers. Thus every time we pop, we add 1 to the count (or do k--). And we add (r+1,c) to the PQ. Until the pop count == k, that is the result.
time O(klogn) space O(n)
(Note if we constrain the pq size to be k in the first place, the time can be O(klog(min(n,k))) and space O(min(n, k))

M3: using 240, search a num in such a mat take O(n) time. The search can be converted to count how many numbers are smaller than target by adding c+1, when doing r++, for target >= curNum case. Thus we can do binary search based on [min, max] and find out the number with count == k.  Given the number may not exist in the mat, we can continue the BS when count == k to set high = mid. Until we have low >= high.
time O(nlog(max-min)) space O(1)


*/
// M2
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((int[] p1, int[] p2) -> (matrix[p1[0]][p1[1]] - matrix[p2[0]][p2[1]]));
        for (int j = 0; j < n; j++) {pq.add(new int[]{0,j});}
        int[] pop = new int[2];
        while (k--> 0) {
            pop = pq.poll();
            if (pop[0]+1 < n) {pq.add(new int[]{pop[0]+1, pop[1]});}
        }
        return matrix[pop[0]][pop[1]];
    }
}
// M3
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n-1][n-1];
        while (left < right) {
            int mid= left +(right - left) / 2;
            int countMid = count(matrix, mid);
            if (countMid < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int count(int[][] matrix, int target) {
        int n =  matrix.length;
        int r = 0;
        int c = n-1;
        int res = 0;
        while(r < n && c >= 0) {
            if (matrix[r][c] > target) {
                c--;
            } else {
                r++;
                res += c+1;
            }
        }
        return res;
    }
}
/* How to prove "left" here will always be the number inside the matrix
This one messed up my head as well. So many good explanations already. Just to share my thought:

If there are less than k elements in the range of [matrix[0][0], mid], we increment mid by 1 and assign it to low. Then if there are more or equal to k elements in the range of [matrix[0][0], mid+1]

mid+1 is our answer because it's mid+1 that causes count to increase from less than k to greater or equal to k, and mid+1 has to be in the matrix otherwise this would never happen.

count < k will always be false to trigger high=mid; namely low should stay still at mid+1, waiting for high to meet it, since there are always more than k elements in the range of [matrix[0][0], t]where high >= t >= mid+1 , and t is the only value we would get from all the following operations of low + (high-low)/2 given now low is mid+1.

Also, since we started low from matrix[0][0] and would not reassign value greater than mid+1 to it. We never miss our answer


*/








