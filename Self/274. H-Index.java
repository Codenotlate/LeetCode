
// Self phase3: regular sort way
// Time O (nlogn) space O(1)
class Solution {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        for (int i = 0; i < citations.length; i++) {
        	if (citations[i] >= citations.length - i) {
        		return citations.length - i;
        	}
        }
        return 0;
    }
}


// M2: counting sort
// since the answer will be in range 0 to n, then we can use counting sort to separate them into (n + 1) buckets
// time O(n), space O(n)
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] count = new int[n + 1];
        for (int c: citations) {
        	// if number is large than n, then we put it in the bucket n
        	c = Math.min(c, n);
        	count[c]++;
        }

        int num = 0;
        for (int i = n; i >= 0; i--) {
        	num += count[i];
        	if (num >= i) {
        		return i;
        	}
        }
        return 0;
    }
}

/*
Counting sort: each bucket only contains the number of elements in this bucket
Bucket sort: separate elements into each bucket first, then within each bucket, do quick sort
Radix sort: Do bucket sort for each digit.

*/


// Phase3 self
class Solution {
    /* initial thought
    since the h value will be in the range of 0 to n, we can use a count array with length = n + 1, and count[i] represent number of papers with citations == i. For citations > n, we count it into count[n].
    After the loop of citations array, we loop count array from right to left, and return the first i where cumSum from right >= i.
    */
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] count = new int[n+1];
        for(int c: citations) {
            if (c >= n) {count[n]++;}
            else {count[c]++;}
        }
        
        int cumSum = 0;
        for (int i = n; i >= 0; i--) {
            cumSum += count[i];
            if (cumSum >= i) {return i;}
        }
        return 0;
    }
}



// Review
/*Initial thought
M1 way: sort the array first, then move from backwards. find the first i having len - i > nums[i], return len-i-1. time O(nlogn) space O(sort space)
[0,1,4,5,6] [0,0,4,4]
M2 way: try to improve to O(n) time. Use counting sort idea, init a n+1 size array and get the count for each h = index. We init the count array size here as n instead of maxh is because the maximum answer here is n, and for any h value > n, we can simply combine the count into the position with h value = n.
Again move backwards of the count array, find the first i having 
accumSum(i) >= i.
This way time O(n) space O(n)
[1,1,0,1,0,2]
*/
// M1 way: sort
class Solution {
    public int hIndex(int[] citations) {
        Arrays.sort(citations);
        for (int i = citations.length - 1; i >= 0; i--) {
            if(citations.length - i > citations[i]) {return citations.length - i-1;}
        }
        return citations.length;
    }
}

// M2 way: counting sort
class Solution {
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] count = new int[n+1];
        for (int num: citations){
            int idx = Math.min(num, n);
            count[idx]++;
        }
        int accumsum = 0;
        for (int i = n; i >= 0; i--) {
            accumsum += count[i];
            if (accumsum >= i) {return i;}
        }
        return -1; // should not reach this line
    }
}


