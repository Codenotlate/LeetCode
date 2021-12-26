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









