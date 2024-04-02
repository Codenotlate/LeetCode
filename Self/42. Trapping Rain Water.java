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
Using this rule, we guarantee, if the node is visited by moving the l++, then all the value of its left side will be smaller then h[r], meaning its leftMost must be smaller than its rightMost. And the leftMost will be determined by prev leftMost and h[l].
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
    // M3: one pass time O(n)  space O(1), each time we move the smaller pointer in left and right pointers. This moving rule guarantees when we reach a position i, if it is reached from left, then its leftmost < rightmost, s += leftmost - height[i]. And if reched from right, meaning its rightmost < leftmost, thus s += rightmost - height[i].
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





// Review
// same idea as above M1 2pass way.
/*Thought
one time from left side. And then from right side to left. Each time we want to find the next bar higher than or equal to current bar, then the area will = curheight * (index diff) - barheight in between.  We can use two pointers, top and cur to mimic the stack similar behavior, we also need to keep check of the height sum in between until we have height[cur] >= height[peek], then res += height[peek] * (cur - peek - 1) - heightsum; heightsum reset to 0; peek = cur.
time O(n)
space O(1)

[0,1,0,2,1,0,1,3,2,1,2,1]
left to right: 
resleft += 0*0 + 1*1-0 + 2*3-2 = 5
right to left:
resright += 1*0-0+ 2*1-1+2*0-0 = 1
0,1,0


*/
class Solution {
    public int trap(int[] height) {
        int peek = 0;
        int res = 0;
        int cumheight = 0;
        // from left to right
        for (int cur = 1; cur < height.length; cur++) {
            if (height[cur] >= height[peek]) {
                res += height[peek] * (cur - peek - 1) - cumheight;
                cumheight = 0;
                peek = cur;
            } else {
                cumheight += height[cur];
            }
        }
        
        // from right to left
        peek = height.length - 1;
        cumheight = 0;
        for (int cur = height.length - 2; cur >= 0; cur--) {
            // need to be > to avoid equal condition being calculated twice
            if (height[cur] > height[peek]) {
                res += height[peek] * (peek - cur - 1) - cumheight;
                cumheight = 0;
                peek = cur;
            } else {
                cumheight += height[cur];
            }
        }
        return res;
    }
}
// try one pass way
/*Thought
Another view of the area, is for each bar find its highest left bars and highest right bars (including itself), then res += lefthighest - curheight + righthighest - curheight
O(n) space way is to keep track of lefthighest and righthighest of each position and add in the end which in total takes 3 passes.
O(1) space way is to have two pointers one from start and one from end and we move the smaller one, update lefthigh or righthigh accordingly, and res += the move side highest - curheight.
*/
class Solution {
    public int trap(int[] height) {
        int start = 0;
        int end = height.length-1;
        int res = 0;
        int leftHigh = 0;
        int rightHigh = 0;
        while (start <= end) {
            if(height[start] <= height[end]) {
                leftHigh = Math.max(leftHigh, height[start]);
                res += leftHigh - height[start];
                start++;
            } else {
                rightHigh = Math.max(rightHigh, height[end]);
                res += rightHigh - height[end];
                end--;
            }
        }
        return res;
    }
}


// Review 23/8/16 - self with M1, hint for M2, solution for M3
/**
M0: naive way O(n^2) O(1)- Key idea: for each position, we want to know its leftMax and rightMax, then the water from this position = min(leftMax, rightMax) - height[cur].
M1: two traverse O(n) O(n)- from left to right, record down the leftMax, then from right to left, get the rightMax and compare to leftMax.
M2: Use a stack O(n) O(n) - M1 way is to add the vertical height of each pos. M2 way is to add the horizontal rectangle of each pos. The stack will store the index.
M3: two pointers O(n) O(1) space - similar as M1, but do it in one iteration with start and end pointers. The key is to determine which pointer to move each time. And we move the side with smaller max number, and add the height to res. Because that part is at least guaranteed.
*/
// M2
class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] <= height[i]) {
                int cur = stack.pop();
                // don't forget this case
                if (!stack.isEmpty()) {
                    int leftMax = height[stack.peek()];
                    res += (Math.min(leftMax, height[i]) - height[cur]) * (i - stack.peek() - 1);
                }               
            }
            stack.push(i);
        }
        return res;
    }
}
// M3
class Solution {
    public int trap(int[] height) {
        int res = 0;
        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;

        while (left <= right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if(leftMax < rightMax) {
                res += leftMax - height[left];
                left++;
            } else {
                res += rightMax - height[right];
                right--;
            }
        }
        return res;
    }
}





/** 2024/4/2
M1: View the graph vertically, for each position, the area = min(maxLeft,maxRight)-curHeight. Thus we do two pass, one from left to right to get maxLeft, another from right to left to get maxRight. Can only use one array, since in the second pass we can directly calculate results. Time O(2n) space O(n)
M2 (used monotonic template): View the graph horizontally, for each position, the area = (min(prevGreater,nextGreater)-curHeight) * index diff between prevGreater and next Greater. Since this involves prevGreater and nextGreater, it can use monotonic stack with 1 pass. Time O(n) Space O(n)
M3 (learn from solution): Two pointer way. Again view the graph vertically like M1. We track leftMax and rightMax from the two ends of the array, update them based on current left and right pointer. And move the left pointer when leftMax < rightMax. The reason being, future rightMax is only going to be larger than or equal to current rightMax, if current rightMax already larger than leftMax, then for this position, the min(maxLeft, rightMax) will always equal to current maxLeft. Thus we can directly add maxLeft-curHeight to the result for this position and move left++. Time O(n) Space O(1)

 */
 // M1
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        int[] maxLeft = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {maxLeft[i] = height[i]; continue;}
            maxLeft[i] = Math.max(maxLeft[i-1], height[i]);
        }
        int res = 0;
        int maxRight = 0;
        for (int i = n-1; i >= 0; i--) {
            maxRight = Math.max(maxRight, height[i]);
            res += Math.min(maxLeft[i], maxRight)-height[i];
        }
        return res;

    }
}
// M2
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while(!stack.isEmpty() && height[stack.peek()] <= height[i]) {
                int top = stack.pop();
                if (!stack.isEmpty()) {
                    res += (Math.min(height[i], height[stack.peek()])-height[top]) * (i-stack.peek()-1);
                }
            }
            stack.push(i);
        }
        return res;

    }
}
// M3
class Solution {
    public int trap(int[] height) {
        int res = 0;
        int left = 0;
        int right = height.length-1;
        int leftMax = 0;
        int rightMax = 0;

        while (left <= right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax < rightMax) {
                res += leftMax - height[left];
                left++;
            } else {
                res += rightMax - height[right];
                right--;
            }
        }
        return res;
        

    }
}











