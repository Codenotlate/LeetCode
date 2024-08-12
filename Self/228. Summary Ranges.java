class Solution {
    public List<String> summaryRanges(int[] nums) {
        List<String> res = new LinkedList<>();
        int left = 0;
        while (left < nums.length) {
            int right = left;
            while(right < nums.length-1 && nums[right+1] == nums[right]+1) {
                right++;
            }
            if (right == left) {
                res.add(""+nums[left]);
            } else {
                res.add(""+nums[left]+"->"+nums[right]);
            }
            left= right+1;
        }
        return res;
    }
}