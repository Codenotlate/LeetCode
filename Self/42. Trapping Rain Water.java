// Phase 3 self idea: time O(2n) = O(n)

class Solution {
    public int trap(int[] height) {
        int left = 0;
        int right = left + 1;
        int res = 0;
        int midSum = 0;
        int n = height.length;

        while (right < n) {
        	if (height[right] < height[left]) {
        		midSum += height[right];
        		right++;
        	} else {
        		res += (right - left - 1) * height[left] - midSum;
        		midSum = 0;
        		left = right;
        		right = left + 1;
        	}
        }

       	// do the same thing in reverse order
       	right = n - 1;
       	left = right - 1;
        midSum = 0;
       	while (left >= 0) {
            // pay attention, here should not be <, otherwise the two sides equal cases will be duplicated in two directions
        	if (height[left] <= height[right]) {
        		midSum += height[left];
        		left--;
        	} else {
        		res += (right - left - 1) * height[right] - midSum;
        		midSum = 0;
        		right = left;
        		left = right - 1;
        	}
        }

        return res;
    }
}


// another three-pass way having similar idea as M1, is instead calculate the area, for each bar, the size of water it can hold is the diff between it and min(its leftMost, its rightMost) note the leftMost and rightMost also include itself
// Thus we go one pass to get the leftMost for each bar and another pass to get rightMost for each Bar.
// Then the third pass to add the size of water held by each bar

// omit M2



// M3: One pass way - key idea is the same as M2
/*
we use two pointers, one from start and the other one from end. Then we move the one with smaller value one step to the middle.
Using this rule, we guarantee, if the node is visited by moving the l++, then all the value of its left side will be smaller then h[r], meaning its leftMost mush be smaller than its rightMost. And the leftMost will be determined by prev leftMost and h[l].
*/



class Solution {
    public int trap(int[] height) {
    	// need to deal with n = 0 first
    	if (height.length == 0) {return 0;}
        int left = 0;
        int leftMost = height[left];
        int right = height.length  - 1;
        int rightMost = height[right];
        int resArea = 0;

        while (left < right) {
        	if (height[left] < height[right]) {
        		left++;
        		leftMost = Math.max(leftMost, height[left]);
        		resArea += leftMost - height[left];
        	} else {
        		right--;
        		rightMost = Math.max(rightMost, height[right]);
        		resArea += rightMost - height[right];
        	}	  	
        }
        return resArea;
    }
}





















