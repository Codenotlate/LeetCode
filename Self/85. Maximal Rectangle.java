class Solution {
    public int maximalRectangle(char[][] matrix) {
        // If we count consecutive 1s from left to right for each row, or from top to bottom for each column, we can convert this problem to the same problem as 84. In this case, we need to get the global max area of histogram composed by each row(if we count 1 vertically), or by each column (if we count 1 horizontally). 
        // Thus we can use the naive way or the stack way in 84 as two methods. 
        // M1: use O(n^2) 84 way. total time O(n^2 * m) space can be optimized to O(n)
        // here choose to count 1 vertically and each row is a histogram
        int m = matrix.length;
        if (m == 0) {return 0;}
        int n = matrix[0].length;
        int[] hist = new int[n];
        int maxArea = 0;
        int rowMax = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    hist[j] = 0;
                } else {
                    hist[j] += 1;
                }
            }
            rowMax = histMaxarea(hist);
            maxArea = Math.max(maxArea, rowMax);
        }
        return maxArea;
    }
    
    // use O(n^2) 84 way
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
    
}


class Solution {
    public int maximalRectangle(char[][] matrix) {
        // If we count consecutive 1s from left to right for each row, or from top to bottom for each column, we can convert this problem to the same problem as 84. In this case, we need to get the global max area of histogram composed by each row(if we count 1 vertically), or by each column (if we count 1 horizontally). 
        // Thus we can use the naive way or the stack way in 84 as two methods. 
        // M2: use O(n) 84 stack way. total time O(n * m) space O(n)
        // here choose to count 1 vertically and each row is a histogram
        int m = matrix.length;
        if (m == 0) {return 0;}
        int n = matrix[0].length;
        int[] hist = new int[n];
        int maxArea = 0;
        int rowMax = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    hist[j] = 0;
                } else {
                    hist[j] += 1;
                }
            }
            rowMax = histMaxarea(hist);
            maxArea = Math.max(maxArea, rowMax);
        }
        return maxArea;
    }
    
    // use O(n) 84 stack way
    private int histMaxarea(int[] hist) {
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        stack.push(0);
        for (int i = 1; i < hist.length; i++) {
            while (stack.peek() != -1 && hist[i] < hist[stack.peek()]) {
                int height = hist[stack.pop()];
                maxArea = Math.max(maxArea, (i - stack.peek() - 1) * height);
            }
            stack.push(i);           
        }
        while (stack.peek() != -1) {
            int height = hist[stack.pop()];
            maxArea = Math.max(maxArea, (hist.length - stack.peek() - 1) * height);
        }
        return maxArea;
    }
    
    
}



// also has dp way time O(mn) for later learning
// https://leetcode.com/problems/maximal-rectangle/solution/


















