class Solution {
	// method1: naive, just changing the List to Set from 46, 
	// not efficient, since we do repetitive work with duplicated values and abandon in the end
    public List<List<Integer>> permuteUnique(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        List<Integer> curList = new LinkedList<>();
        int[] visited = new int[nums.length];
        dfs(nums, visited, res, curList);
        return new ArrayList<>(res);
    }

    private void dfs(int[] nums, int[] visited, Set<List<Integer>> res, List<Integer> curList) {
    	if (curList.size() == nums.length) {
    		res.add(new ArrayList(curList));
    		return;
    	}

    	for (int i = 0; i < nums.length; i++) {
    		if (visited[i] != 1) {
    			visited[i] = 1;
    			curList.add(nums[i]);
    			dfs(nums, visited, res, curList);
    			visited[i] = 0;
    			curList.remove(curList.size() - 1);
    		}
    	}
    }
}

// another way based on method1 structure is to sort the array first 
// and for duplicates only use number if his previous one is used
// https://leetcode.com/problems/permutations-ii/discuss/18594/Really-easy-Java-solution-much-easier-than-the-solutions-with-very-high-vote
class Solution {
	// method1 based improvement with sort
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        int[] visited = new int[nums.length];
        Arrays.sort(nums);
        dfs(nums, visited, res, curList);
        return res;
    }

    private void dfs(int[] nums, int[] visited, List<List<Integer>> res, List<Integer> curList) {
    	if (curList.size() == nums.length) {
    		res.add(new ArrayList(curList));
    		return;
    	}

    	for (int i = 0; i < nums.length; i++) {
    		// deal with duplicates, skip it if the previous haven't been used
    		if (i >= 1 && nums[i] == nums[i - 1] && visited[i - 1] != 1) {
    			continue;
    		}
    		if (visited[i] != 1) {
    			visited[i] = 1;
    			curList.add(nums[i]);
    			dfs(nums, visited, res, curList);
    			visited[i] = 0;
    			curList.remove(curList.size() - 1);
    		}
    	}
    }
}

// above method phase2 self
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        int[] visited = new int[nums.length];
        Arrays.sort(nums);
        dfs(nums, visited, res, curList);
        return res;
    }

    private void dfs(int[] nums, int[] visited, List<List<Integer>> res, List<Integer> curList) {
        if (curList.size() == nums.length) {
            res.add(new ArrayList<>(curList));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if(visited[i] == 1 || (i > 0 && nums[i] == nums[i - 1] && visited[i -1] != 1)) {continue;}
            visited[i] = 1;
            curList.add(nums[i]);
            dfs(nums, visited, res, curList);
            visited[i] = 0;
            curList.remove(curList.size() - 1);
        }
    }
}



// both methods: Time O(N!) space O(N) if not take into account result space, otherwise O(N * N!)

class Solution {
	// method2: use hashmap <value, count> to avoid dfs duplicated values
	// count can be used as visted array in above method
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> curList = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
        	map.put(n, map.getOrDefault(n, 0) + 1);
        }
        //no need for visited, use count instead;
        //int[] visited = new int[nums.length];
        dfs(map, nums.length, res, curList);
        return res;
    }

    private void dfs(Map<Integer, Integer> map, int N, List<List<Integer>> res, List<Integer> curList) {
    	// again do deep copy
    	// since now it's a map, we need to store the original length of nums in N
    	if (curList.size() == N) {
    		res.add(new ArrayList<>(curList));
    		return;
    	}

    	for (int n: map.keySet()) {
    		int count = map.get(n);
    		if (count > 0) { // meaning not visited
    			curList.add(n);
    			map.put(n, count - 1);
    			dfs(map, N, res, curList);
    			map.put(n, count);
    			curList.remove(curList.size() - 1);
    		}    		
    	}
    }
}





// phase3 self
// above M1 (same as phase2 self implementation)
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        Arrays.sort(nums);
        dfs(nums, new int[nums.length], new LinkedList<Integer>(), res);
        return res;
    }
    
    
    private void dfs(int[] nums, int[] visited, List<Integer> curList, List<List<Integer>> res) {
        if (curList.size() == nums.length) {
            res.add(new LinkedList(curList));
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] == 1 || (i > 0 && nums[i - 1] == nums[i] && visited[i - 1] == 0)) {
                continue;
            }
            visited[i] = 1;
            curList.add(nums[i]);
            dfs(nums, visited, curList, res);
            visited[i] = 0;
            curList.remove(curList.size() - 1);
        }
    }
}

//above M2 self implementation
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        // use a map to store each element and count, in each dfs level loop all elements with count > 0 to avoid using dup
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int n: nums) {
            countMap.put(n, countMap.getOrDefault(n, 0) + 1);
        }
        dfs(countMap, nums.length, new LinkedList<Integer>(), res);
        return res;
    }
    
    
    private void dfs(Map<Integer, Integer> countMap, int n, List<Integer> curList, List<List<Integer>> res) {
        if (curList.size() == n) {
            res.add(new LinkedList(curList));
            return;
        }
        
        for (int i: countMap.keySet()) {
            if (countMap.get(i) <= 0) {continue;}
            curList.add(i);
            countMap.put(i, countMap.get(i) - 1);
            dfs(countMap, n, curList, res);
            countMap.put(i, countMap.get(i) + 1);
            curList.remove(curList.size() - 1);
        }
    }
}  




// Review M2
class Solution {
    // similar as 46, just use map count num in nums, and count minus 1 for visited. And use map.keySet() for each position to avoid dup
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        dfs(map, nums.length, new LinkedList<Integer>(), res);
        return res;
        
    }

    private void dfs(Map<Integer, Integer> map, int len, List<Integer> curList, List<List<Integer>> res) {
        if (curList.size() == len) {res.add(new LinkedList(curList)); return;}
        for (int n: map.keySet()) {
            if (map.get(n) == 0) {continue;}
            curList.add(n);
            map.put(n, map.get(n) - 1);
            dfs(map, len, curList, res);
            curList.remove(curList.size() - 1);
            map.put(n, map.get(n) + 1);
        }
    } 
}





// Review 23/1/10
/*Thoughts - self way, not exactly the same as above ways, but similar dfs + backtracking way. - Used sort to handle dup cases, but code is not as elegant as above.
Note it contains dup. To avoid dup, we sort the nums first.
Then for each position in the result list, we try each non-dup num from nums, putting into that pos. Then recur on the rest nums.

*/
// M0 - self less concise way
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        return permuRecur(nums, 0, new int[nums.length]);
    }

    private List<List<Integer>> permuRecur(int[] nums,int i, int[] visited) {
        List<List<Integer>> res = new LinkedList<>();
        if ( i >= nums.length) {return res;}
        int k = 0;
        while (k < nums.length) {
            if (visited[k] != 0) {k++; continue;}
            visited[k] = 1;
            List<List<Integer>> subRes = permuRecur(nums, i+1, visited);
            if (subRes.size() == 0) {
                List<Integer> list = new LinkedList<>();
                list.add(nums[k]);
                res.add(list);
            }
            for (List<Integer> list: subRes) {
                list.add(0, nums[k]);
                res.add(list);
            }
            visited[k] = 0; // don't forget about the backtrack
            while(k < nums.length - 1 && nums[k+1] == nums[k]) {k++;}
            k++;
        }
        return res;

    }
}

// Above M1: typical dfs + backtracking way using visited array
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        dfs(nums, new int[nums.length], new LinkedList<>(), res);
        return res;
    }

    private void dfs(int[] nums, int[] visited, List<Integer> curList, List<List<Integer>> res) {
        if (curList.size() == nums.length) {
            res.add(new LinkedList(curList));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i] != 0 || (i > 0 && nums[i-1] == nums[i] && visited[i-1]== 0)) {continue;}
            visited[i] = 1;
            curList.add(nums[i]);
            dfs(nums, visited, curList, res);
            curList.remove(curList.size() - 1);
            visited[i] = 0;
        }
    }
}

// Above M2: typical dfs + backtracking way using count map
class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new LinkedList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int n: nums) {map.put(n, map.getOrDefault(n, 0) + 1);}
        dfs(nums.length, map, new LinkedList<>(), res);
        return res;
    }

    private void dfs(int N, Map<Integer, Integer> map, List<Integer> curList, List<List<Integer>> res) {
        if (curList.size() == N) {
            res.add(new LinkedList(curList));
            return;
        }
        for (int num: map.keySet()) {
            if (map.get(num) == 0) {continue;}
            map.put(num, map.get(num) - 1);
            curList.add(num);
            dfs(N, map, curList, res);
            curList.remove(curList.size() - 1);
            map.put(num, map.get(num) + 1);
        }
    }
}



















