class NumArray {
	private int[] originalNums;
	private int[] preSumNums;


    public NumArray(int[] nums) {
        this.originalNums = nums;
        preSumNums = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
        	// pay attention: don't set index 0 outside for loop without checking whether array is empty.
        	if (i == 0) {
        		preSumNums[0] = nums[0];
        	} else {
        		preSumNums[i] = preSumNums[i - 1] + nums[i];
        	}
        	
        }
    }
    
    public int sumRange(int i, int j) {
    	if (i == 0) {return preSumNums[j];}
        return preSumNums[j] - preSumNums[i - 1];
    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(i,j);
 */