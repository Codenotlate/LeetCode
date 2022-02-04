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

// Review add M2
class Solution {
    // M2: three(two) pass time O(n)  space O(n)
    public int trap(int[] height) {
        int n = height.length;
        if (n==0) {return 0;}
        int[] leftmost = new int[n];
        int[] rightmost = new int[n];
        int s = 0;
        leftmost[0] = height[0];
        rightmost[n-1] = height[n-1];
        for (int i = 1; i < n; i++) {
            leftmost[i] = Math.max(leftmost[i-1], height[i]);
            rightmost[n-1-i] = Math.max(rightmost[n-i], height[n-1-i]);
        }
        
        for (int i = 0; i < n; i++) {
            s += Math.min(leftmost[i], rightmost[i]) - height[i];
        }
        return s;
    }
}



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



// Review M3:
class Solution {    
    // M3: one pass time O(n)  space O(n), each time we move the smaller pointer in left and right pointers. This moving rule guarantees when we reach a position i, if it is reached from left, then its leftmost < rightmost, s += leftmost - height[i]. And if reched from right, meaning its rightmost < leftmost, thus s += rightmost - height[i].
    public int trap(int[] height) {
        int n = height.length;
        if (n==0) {return 0;}
        int left = 0;
        int right = n-1;
        int s = 0;
        int leftmost = height[left];
        int rightmost = height[right];
        while (left < right) {
            if (height[left] < height[right]) {
                left++;
                leftmost = Math.max(leftmost, height[left]);
                s += leftmost - height[left];
            } else {
                right--;
                rightmost = Math.max(rightmost, height[right]);
                s += rightmost - height[right];
            }
        }
        
        return s;
    }
}




















