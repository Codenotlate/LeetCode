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



// (Fail)Review - used start index of the range for each element. Fail for a very larget dataset. Doubt the test is wrong and my idea should be correct.
// failure testcase:
// ["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex",...]
// [[[3,14,1,7]],[],[],...]
/*Thought
Basically this w = [1,3] can convert to [0,1,1,1] And we can do usual random pick on the converted array. i.e. we can build an array where arr[i] is the the start idx in the converted array for i. The total size is sum(w). Then we can generate random based on total size. And find the corresponding element that has this index in its range. Can use binary search in arr to find the first number larger than or equal to that index. And return the final result.
Solution(int[] w) will be O(n) time
pickIdx() will be O(logn) time
*/
// class Solution {
//     int[] array;
//     int size;
//     Random rand;
    
//     public Solution(int[] w) {
//         size = 0;
//         array = new int[w.length];
//         for (int i = 0; i < w.length; i++) {
//             array[i] = size;
//             size += w[i];
//         }
//         rand = new Random();
//     }
    
//     public int pickIndex() {
//         // for(int i: array) {System.out.print(i+" ");}
//         // System.out.print(size);
//         int idx = rand.nextInt(size);
//         int start = 0;
//         int end = array.length - 1;
//         while(start < end) {
//             int mid = start + (end - start) / 2;
//             if (array[mid] >= idx) {end = mid;}
//             else {start = mid + 1;}
//         }
//         return array[start] == idx? start : start - 1;
//     }
// }



// Review 23/3/23 - after a long debug
/*Thought
M0:naive way is to store w[i] amount of w[i] in an array. Then we only need to randomly pick one position in that array with equal prob. The space req will be large.

M1:one array for w[i], another array for accumulated sum of w[:i]. When pick random index, we need to do binary search onthe accumulated sum to find the corresponding i in w[i] array.
time O(logn) space O(n)

 */

class Solution {
    int[] weights;
    Random rn = new Random();

    public Solution(int[] w) {
        weights = new int[w.length];
        weights[0] = w[0];
        for (int i = 1; i < w.length; i++) {
            weights[i] = weights[i-1] + w[i];
        }
    }
    
    public int pickIndex() {
        int randomIdx = rn.nextInt(weights[weights.length-1])+1;
        return binarySearch(randomIdx, weights);
    }

    // need to find the first number >= target
    private int binarySearch(int target, int[] weights) {
        int start = 0;
        int end = weights.length-1;
        while(start < end) {
            int mid = start + (end- start) / 2;
            if (weights[mid]>= target) {
                end = mid;
            } else {
                start = mid+1;
            }
        }
        return start;
    }
}
