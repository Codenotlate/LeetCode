class Solution {
    public int largestRectangleArea(int[] heights) {
        //M1: naive O(n^2) way -- time limit exceeded
        //main idea: for each cur bar from 0 to n - 1, find the closest left bar and right bar that is smaller than the current bar. Then the max area of the rect with height = cur bar will equal to (right idx - left index - 1) * cur bar height.
        int maxArea = 0;
        for (int cur = 0; cur < heights.length; cur++) {
            int height = heights[cur];
            int left = findLeft(heights, cur);
            int right = findRight(heights, cur);
            maxArea = Math.max(maxArea, (right - left - 1) * height);
        }
        return maxArea;
    }
    
    private int findLeft(int[] heights, int cur) {
        for (int i = cur; i >= 0; i--) {
            if(heights[i]< heights[cur]) {
                return i;
            }
        }
        return -1;
    }
    
    private int findRight(int[] heights, int cur) {
        for (int i = cur + 1; i < heights.length; i++) {
            if(heights[i] < heights[cur]) {
                return i;
            }
        }
        return heights.length;
    }
}

// using the one wrote in 85, a more concise version of above method
private int histMaxarea(int[] hist) {
    int maxArea = 0;
    for (int i = 0; i < hist.length; i++) {
        int cur = hist[i];
        int left = i - 1;
        int right = i + 1;
        while (left >= 0) {
            if (hist[left] < cur) {break;}
            left--;
        }
        while (right < hist.length) {
            if (hist[right] < cur) {break;}
            right++;
        }
        maxArea = Math.max(maxArea, (right - left - 1) * cur);
    }
    return maxArea;
}



class Solution {
    public int largestRectangleArea(int[] heights) {
        //M2: use stack O(n) way
        //main idea: for each cur bar from 0 to n - 1, find all the bars on the left to cur that has larger height. those are the heights of rect that will use cur as its right end bar. And since we will keep popping out bars have higher height than cur bar, the final bars (actually idx in the bar. refer to its corresponding bar height here) left in the stack will be in ascending order. Thus the left start bar will be the top one in stack after the height popped out.
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        stack.push(0);
        for (int cur = 1; cur < heights.length; cur++) {
            while (stack.peek() != -1 && heights[cur] < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int right = cur;
                int left = stack.peek();
                maxArea = Math.max(maxArea, (right - left - 1) * height);
            }
            stack.push(cur);
        }
        
        while (stack.peek() != -1) {
            int height = heights[stack.pop()];
            maxArea = Math.max(maxArea, (heights.length - stack.peek() - 1) * height);
        }
        return maxArea;
    }
    
}



//https://leetcode.com/problems/largest-rectangle-in-histogram/solution/
// another divide and conquar method for later review


// Phase3 from solution
// Note: by using stack, we keep track of the first left bar has smaller height and first right bar has smaller height than current bar. Instead of searching every time for every bar.
// Thus when each bar is popped out from the stack, we can use the left and right idx info to calculate the maxArea using the current bar's height, and update the maxArea.
class Solution {
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < heights.length; i++) {
            while (stack.peek() != -1 && heights[stack.peek()] > heights[i]) {
                int height = heights[stack.pop()];
                int width = i - stack.peek() - 1;
                maxArea = Math.max(height * width, maxArea);
            }
            stack.push(i);
        }
        
        // continue pop out ascending bars left in the stake, now the right side will be the end of the array
        while (stack.peek() != -1) {
            int height = heights[stack.pop()];
            int width = heights.length - 1 - stack.peek();
            maxArea = Math.max(height * width, maxArea);
        }
        return maxArea;
    }
}



// Review self
/* similar idea as the above optimial stack solution. But not the most optimized way. We can convert the 3 pass into only 1 pass. Cause whenever an item is poped out of the leftStack, we actually alrady know its leftIdx and rightIdx. its leftIdx is stack.peek() after it pops out, and rightIdx is the curIdx, cause cur is the one make it pops out meaning curVal < its value.
Thus in the first pass we can calcualte most of the bars maxArea, except those bars left in the stack which has their rightIdx = n. Thus we can pop them out and update maxArea. And when the stack is empty, the maxArea for all bars have been calculated.
*/
// self 3 pass way

class Solution {
    /* Thinking process
    for each bar, we want to find the first one smaller than it on both left and right side. So that the area = curheight * (firstrightIdx-firstleftIdx-1). then we can update area for each bar to get max area.
    To find the first one smaller on left and right side, we can use monotone stack twice, one starting from left and another one starting from right side.
    firstleftIdx arr = [-1, -1, 1, 2, 1,4]
    leftStack(idx(val)) = [-1(min), 0(2)[pop],1(1),2(5)[pop],3(6)[pop], 4(2), 5(3)
    [1,6,4,4,6,6] = firstrightIdx arr
      0(2),1(1),2(5)[pop],3(6)[pop],4(2)[pop],5(3)[pop],6(min)]= rightStack(idx(val)) 
    maxArea = max([2 *1, 1 * 6, 5 * 2, 6 * 1, 2 * 4, 3 *1]) = 10

    time O(n) space O(n)
    */
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> leftStack = new Stack<>();
        Stack<Integer> rightStack = new Stack<>();
        int n = heights.length;
        int[] leftIdx = new int[n];
        int[] rightIdx = new int[n];
        
        leftStack.push(-1);
        for (int i = 0; i < n; i++) {
            int cur = heights[i];
            while (leftStack.peek() != -1 && heights[leftStack.peek()] >= cur) {leftStack.pop();}
            leftIdx[i] = leftStack.peek();
            leftStack.push(i);
        }
        
        rightStack.push(n);
        for (int i = n-1; i>= 0; i--) {
            int cur = heights[i];
            while(rightStack.peek() != n && heights[rightStack.peek()] >= cur) {
                rightStack.pop();
            }
            rightIdx[i] = rightStack.peek();
            rightStack.push(i);
        }
        
        int maxArea = 0;
        for (int i = 0; i < n ; i++) {
            maxArea = Math.max(maxArea, heights[i] * (rightIdx[i] - leftIdx[i] - 1));
        }
        return maxArea;
    }
}






// Review
/*Thought
The key here is for each height, find the index of the first height smaller than it on both left and right side. Then that area will be the max area of that height, once we traverse all height, we can get the result.
Naive way to find the left and right lower index is O(n) time and thus O(n^2) time in total.
Optimized way is to use a monotone stack. We put the index not the height into the stack, this helps to calculate the width. When we have h[stack.peek()] > h[curIdx], we have find the left and right index for stack.peek(). Thus we pop it out and calculate its area. We keep doing it until h[stack.peek()] > cur no longer satisfies. Then we add curIdx into the stack. Until we traversed all element in heights. All the elements left in the stack will have right side = right outside range of the array. Also note at the beginning we will add - 1 to the stack to represent the left outside range of the array.
time O(n) at most two pass
space O(n)
*/
class Solution {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxArea = 0;
        for (int i = 0; i < heights.length; i++) {
            while(stack.peek() != -1 && heights[stack.peek()] > heights[i]) {
                int idx = stack.pop();
                maxArea = Math.max(maxArea, heights[idx] * (i - stack.peek() -1));
            }
            stack.push(i);
        }
        
        while(stack.peek() != -1) {
            maxArea = Math.max(maxArea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
        }
        return maxArea;
    }
}



----------Use below most concise way-------------

/** 2024/4/9
Very similar to 907.For each element, we again want to find it's previous smaller and next smaller.
O(n) time and space


 */
class Solution {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int res = 0;
        for (int cur = 0; cur <= heights.length; cur++) {
            // note: the order of the two Or conditions can't be reversed
            while (stack.peek()!=-1 && (cur == heights.length||heights[stack.peek()]>= heights[cur])) {
                int height = heights[stack.pop()];
                res = Math.max(res, height * (cur - stack.peek() -1));
            }
            stack.push(cur);
        }
        return res;
    }
}



