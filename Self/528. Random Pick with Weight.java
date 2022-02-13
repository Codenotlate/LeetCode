class Solution {
    /*Initial thought
    to generate index with possiblity as w[i] / sum(w), each index should have a range, and each number in the range has even possibility.
    e.g. [1,2,3] corresponding to 0: [0,1); 1:[1:3); 3:[3:6)
    then generate random number x in [0, 5] evenly, if x in the range of that index, return that index.
    above dict structure can be converted to an array [0,2,5], when we got x, we need to find the first element that is larger than or equal to x. Then that is the index we return.
    space O(n) time O(n) for constructor, time O(logn) space O(1) for pickIndex
    */
    Random rand = new Random();
    int[] range;
    
    public Solution(int[] w) {
        range = new int[w.length];
        int cumSum = 0;
        for (int i = 0; i < w.length; i++) {
            cumSum += w[i];
            range[i] = cumSum - 1;
        }
    }
    
    public int pickIndex() {
        int randNum = rand.nextInt(range[range.length-1] + 1);
        int start = 0;
        int end = range.length - 1;
        while (start < end) {
            int mid = start + (end - start) /2;
            if (range[mid] >= randNum) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}

// similar to the sample solution