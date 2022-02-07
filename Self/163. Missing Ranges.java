class Solution {
    /* Initial thought:
    have a prev label the end of last range, init with lower. Then add [prev+1, cur-1] to the result. Should deal with no number or only one number in between cases.
    Also upper will be processed as the last number.
    time O(n)
    space O(1) if exclude result space
    // pay attention to special case when nums is empty!
    */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> res = new LinkedList<>();
        if (nums.length == 0) {
            if (lower == upper) {res.add(String.valueOf(lower));}
            else {res.add(lower + "->" + upper);}
            return res;
        }
        int prev = lower-1; // this line is essential cause lower could be not in
        for (int n: nums) {
            if (n - prev  == 2) {
                res.add(String.valueOf(n-1));
            } else if (n - prev > 2) {
                int first = prev+1;
                int second = n-1;
                res.add(first + "->"+second);
            }
            prev = n;
        }
        
        if (upper - prev == 1) {res.add(String.valueOf(upper));}
        else if(upper - prev > 1) {
            int first = prev+1;
            res.add(first + "->" + upper);
        }
        
        return res;
    }
}

// same idea but a more concise way in solution