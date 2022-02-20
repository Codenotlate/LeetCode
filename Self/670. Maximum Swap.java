class Solution {
    /*Initial thought
    Go from start to end, find the first element that is smaller than the largest num in the range of its right to end. Swap it with the rightest largest num.
    Thus we need to first loop the original num to get an array(0,9), each position records the rightest position of that number.
    Check the number from left to right, for each position check from 9 to that number + 1,if we can find a num with position at the right of the current number, we can swap them and return.
    time O(n) space O(n)
    */
    public int maximumSwap(int num) {
        int[] pos = new int[10];
        char[] chars = Integer.toString(num).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            pos[chars[i]-'0'] = i;
        }
        
        for (int i = 0; i < chars.length; i++) {
            for (int k = 9; k > chars[i] - '0'; k--) {
                if (pos[k] > i) {
                    // swap
                    char temp = chars[pos[k]];
                    chars[pos[k]] = chars[i];
                    chars[i] = temp;
                    return Integer.valueOf(new String(chars));
                }
            }
        }
        return num;
    }
}


class Solution {
    /* improvement can actually optimized to one pass. We go from right to left. We maintain the position of the largest number in the right of each cur position. If current position number is smaller than its right largest, then it is a potential swap position. We update this potential position as we move right to left.
    !!NOTE: we need to track potential position pair instead of just the first one, otherwise, we fail examples like 99901
    After the whole pass, we swap the final potential positions(cause it's the most left pair) with the largest position.
    time O(n) space O(n)
    */
    public int maximumSwap(int num) {
        char[] chars = Integer.toString(num).toCharArray();
        int potentialPos1 = -1;
        int potentialPos2 = -1;
        int rightMaxPos = chars.length - 1;
        for (int i = chars.length - 2; i >= 0; i--) {
            if (chars[i] - '0' < chars[rightMaxPos] - '0') {
                potentialPos1 = i;
                potentialPos2 = rightMaxPos;
            } else if(chars[i] - '0' > chars[rightMaxPos] - '0') {
                rightMaxPos = i;
            }
        }
        
        if (potentialPos1 != -1) {
            char temp = chars[potentialPos1];
            chars[potentialPos1] = chars[potentialPos2];
            chars[potentialPos2] = temp;
        }
        return Integer.valueOf(new String(chars));
    }
}