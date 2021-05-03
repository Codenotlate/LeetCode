class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int start = 0;
        int end = numbers.length - 1;
        while (start < end) {
        	if (numbers[start] + numbers[end] > target) {
        		end -= 1;
        	} else if (numbers[start] + numbers[end] < target) {
        		start += 1;
        	} else {
        		return new int[]{start + 1, end + 1};
        	}
        }
        return new int[2];
    }
}