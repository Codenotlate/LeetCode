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

// Review self
class Solution {
    /* initial thought
    since the array is sorted, can use two pointers and shrink the window according to the curSum and target. If target < curSum, right--. else left++. until sum == target, return int[left+1, right+1]
    time O(n) space O(1)
    */
    public int[] twoSum(int[] numbers, int target) {
        int start = 0;
        int end =numbers.length - 1;
        while (start < end) {
            if (numbers[start] + numbers[end] == target) {return new int[]{start+1, end+1};}
            else if (numbers[start] + numbers[end] < target) {start++;}
            else {end--;}
        }
        return new int[0]; // since answer is guaranteed, thus should not reach this line.
    }
}



// 20240805
// two pointer O(n) space O(1), dictionary O(n) space O(n), binary search O(nlogn) space O(1)
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        int[] res = new int[2];
        while (left < right) {
            if (numbers[left] + numbers[right] == target) {
                res[0] = left+1;
                res[1] = right+1;
                return res;
            }
            if (numbers[left] + numbers[right] > target) {
                right--;
            } else {
                left++;
            }
        }
        return res;
    }
}