// Phase 3 based on solution
// time O(nlogn) space O(nlogn)
// similar to 315 mergeSort. But in this problem we do two steps for sorted first half and second half. The first step is to update count by moving j++ only when condition satisfied. The second step is to actually sort the array to be used by its parent level.
class Solution {
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private int mergeSort(int[] nums, int start, int end) {
        if (start >= end) {return 0;}
        int mid = start + (end - start) / 2;
        int count = mergeSort(nums, start, mid) + mergeSort(nums, mid + 1, end);
        int i = start; int j = mid + 1;
        while (i <= mid) {
            if (j <= end && nums[j] < nums[i] / 2.0) {j++;}
            else {
                i++;
                count += j - (mid + 1);
            }
        }
        Arrays.sort(nums, start, end + 1);
        return count;
    }
}



// Reveiw
class Solution {
    /* initial thought
    use merge sort, each time there's one element moved from second half to the first half, we check whether it is a reverse pair and add count.
    the merge sort can be done top-down (requiring extra space O(nlogn)) or bottom-up with O(1) space, but nums will be changed in this case. 
    Time O(nlogn) for both ways.   
    
    // addition to initial thought: the key here is to separate the counting process and the sort process. Cause here the relationship is 2times, and considering negative number, even if first[i] < second[j] we could still have first[i] > 2 * second[j]. Thus move the element along the way will influence the count on following elements in first half.
    */
    // try top-down way, mergesort(start, mid) and mergesort(mid+1, end)
    
    
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private int mergeSort(int[] nums, int start, int end) {
        if (start >= end) {return 0;}
        int mid = start + (end - start) / 2;
        int leftCount = mergeSort(nums, start, mid);
        int rightCount = mergeSort(nums, mid+1, end);
        // count first       
        int l = start;
        int r = mid+1;
        int count = 0;
        while(l <= mid) {
            // use / instead of * to avoid overflow
            while (r <= end && nums[l] / 2.0 > nums[r]) {r++;}
            count += r - (mid+1);
            l++;
        } 
        
        // then sort
        int[] temp = new int[end-start+1];
        int t = 0;
        l = start;
        r = mid+1;
        while(l <= mid || r <= end) {
            if (l > mid || (r <= end && nums[l] > nums[r])) {temp[t++] = nums[r++];}
            else {temp[t++] = nums[l++];}
        }
        for(t = 0; t < temp.length; t++) {nums[t+start] = temp[t];}
        return count+leftCount+rightCount;
        
    }
}


// another  BST way in solution
// https://leetcode.com/problems/reverse-pairs/discuss/97280/Very-Short-and-Clear-MergeSort-and-BST-Java-Solutions




// Summary - VERY GOOOOOOOOOOOD!!!
// https://leetcode.com/problems/reverse-pairs/discuss/97268/General-principles-behind-problems-similar-to-%22Reverse-Pairs%22



// Review from solution (same as above M1, omit here)


