class Solution {
    /* Initial thought
    based on 3sum. This is adding another outside layer, fix one number, and it becomes a 3sum problem. 
    time O(n^3) space O(sort space)   
    */
    /* Additional from solution, we generalize it to ksum. And use recursion.
    Three basic conditions to return immediately
    1) if target / k > largest in range
    2) if target / k < min in range
    3) if len - i < k, meanning we don't have enough number to fill in the k positions left
    
    Also need to avoid dup by skip i if nums[i] = nums[i-1].

    time O(n^(k-1)) space O(k) for the recustion stack
    */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, 0, 4, target);
    }
    
    private List<List<Integer>> kSum(int[] nums, int i, int k, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // basic case return immediately
        int n = nums.length;
        if (n - i < k || target / k > nums[n-1] || target / k < nums[i]) {return res;}
        if (k == 2) {return twoSum(nums, i, target);}
        for (int j = i; j < n; j++) {
            if (j > i && nums[j] == nums[j-1]) {continue;}
            List<List<Integer>> subres = kSum(nums, j+1, k-1, target - nums[j]);
            for (List<Integer> curlist: subres) {
                // need to convert to arraylist, otherwise can't use get(idx) below
                res.add(new ArrayList(Arrays.asList(nums[j])));
                res.get(res.size() - 1).addAll(curlist);                  
            }
        }
        return res;        
    }
    
    
    private List<List<Integer>> twoSum(int[] nums, int i, int target) {
        int left = i;
        int right = nums.length - 1;
        List<List<Integer>> res = new LinkedList<>();
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                res.add(Arrays.asList(nums[left++], nums[right--]));
                // avoid dup
                while(left < right && nums[left] == nums[left-1]) {left++;}
            } else if (sum > target) {
                right--;
            } else {
                left++;
            }
        }
        return res;
    }
}


// check the solution, also include hashset method, which also requires sort, but change the twosum part implementation.
// https://leetcode.com/problems/4sum/solution/
// twoSum using hashSet
private List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> res = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = start; i < nums.length; i++) {
            if (set.contains(target - nums[i])) {
                List<Integer> temp = Arrays.asList(target - nums[i], nums[i]);
                // note don't compare list == list
                if (res.isEmpty() || res.get(res.size()-1).get(1) != nums[i]) {res.add(temp);}
            }
            set.add(nums[i]);
        }
        return res;
    }







// Review - almost same as above
/*Thought
based on 3sum. And this can be generalized to ksum. The base case will be 2sum.
*/
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        int k = 4;
        Arrays.sort(nums);
        return sumHelper(nums, 0, k, target);
    }
    
    private List<List<Integer>> sumHelper(int[] nums, int start, int k, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // can add early stop conditions
        if (nums.length - start < k || target / k < nums[start] || target / k > nums[nums.length-1]) {return res;}
        if (k == 2) {return twoSum(nums, start, target);}
        
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i-1]) {continue;}
            List<List<Integer>> sublist = sumHelper(nums, i+1, k-1, target - nums[i]);
            for (List<Integer> sub: sublist) {
                // pay attention to this line, can't use Arrays.asList() directly.
                res.add(new ArrayList(Arrays.asList(nums[i])));
                res.get(res.size()-1).addAll(sub);
            }           
        }
        return res;
    }
    
    
    private List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> res = new LinkedList<>();
        int left = start;
        int right = nums.length - 1;
        while (left < right) {
            // deal with dup
            if (left > start && nums[left] == nums[left-1]) {left++; continue;}
            int pairSum = nums[left] + nums[right];
            if (pairSum == target) {
                res.add(Arrays.asList(nums[left], nums[right]));
                left++; 
                right--;
            } else if (pairSum < target) {left++;}
            else {right--;}
        }
        return res;
    }
}