class Solution {
	// time O(n) solution
    public int longestConsecutive(int[] nums) {
        // build a set first
        Set<Integer> set = new HashSet<>();
        for (int n: nums) {
        	set.add(n);
        }
        // iterate the set
        int maxLen = 0;
        for (int curNum: set) {
        	// reduce repetitive work 
        	// curNum - 1 not in set assures starting 
        	// with the smallest num in a potential consecutve sequence
        	if (!set.contains(curNum - 1)) {
        		int curLen = 1;
        		while (set.contains(curNum + 1)) {
        			curLen += 1;
        			curNum += 1;
        		}
        		maxLen = Math.max(maxLen, curLen);
        	}
        }
        return maxLen;
    }
}
/*
time complexity explain:
Time complexity : O(n).

Although the time complexity appears to be quadratic due to the while loop nested within the for loop, closer inspection reveals it to be linear. Because the while loop is reached only when currentNum marks the beginning of a sequence (i.e. currentNum-1 is not present in nums), the while loop can only run for nn iterations throughout the entire runtime of the algorithm. This means that despite looking like O(n⋅n) complexity, the nested loops actually run in O(n + n) = O(n) time. All other computations occur in constant time, so the overall runtime is linear.
*/



// sort solution takes O(nlogn), later



// phase3: M1 replicate
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> numsSet = new HashSet<>();
        for (int n: nums) {
            numsSet.add(n);
        }
        
        // only start with the smallest number in a consecutive sequence
        int curLen =0, maxLen = 0;
        for (int n: numsSet) {
            if (numsSet.contains(n - 1)) {continue;}
            while (numsSet.contains(n)) {
                curLen += 1;
                n += 1;
            }
            maxLen = Math.max(curLen, maxLen);
            curLen = 0;
        }
        return maxLen;
    }
}



// phase3 self using UnionFind
class Solution {
    public int longestConsecutive(int[] nums) {
        // don't forget about the special case
        if (nums.length <= 1) {return nums.length;}

        UnionFind uf = new UnionFind(nums.length);
        // store value and position
        Map<Integer, Integer> visited = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (visited.containsKey(nums[i])) {continue;}
            if (visited.containsKey(nums[i] + 1)) {
                uf.connect(i, visited.get(nums[i] + 1));
            }
            if (visited.containsKey(nums[i] - 1)) {
                uf.connect(i, visited.get(nums[i] - 1));
            }
            visited.put(nums[i], i);
        }
        return uf.maxSize();
    }


    private class UnionFind {
        int[] parent, size;
        int maxSize;

        public UnionFind (int n) {
            parent = new int[n];
            size = new int[n];
            Arrays.fill(size, 1);
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            maxSize = 1;
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }

        public void connect (int p, int q) {
            int rootp = find(p);
            int rootq = find(q);
            if (rootp == rootq) {return;}
            if (size[rootp] < size[rootq]) {
                parent[rootp] = rootq;
                size[rootq] += size[rootp];
                maxSize = Math.max(size[rootq], maxSize);
            } else {
                parent[rootq] = rootp;
                size[rootp] += size[rootq];
                maxSize = Math.max(size[rootp], maxSize);
            }
        }

        public int maxSize() {
            return maxSize;
        }
    }
}




// Review
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int maxLen = 0;
        for (int num: nums) {set.add(num);}
        for (int num: set) {
            if (set.contains(num - 1)) {continue;}
            int curLen = 0;
            while(set.contains(num)) {
                curLen++;
                num++;
            }
            maxLen = Math.max(maxLen, curLen);
        }
        return maxLen;
    }
}


















