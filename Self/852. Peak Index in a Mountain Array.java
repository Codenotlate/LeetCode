/*Thoughts
Given array is two parts sorted and requires O(logn) time, thus consider binary search
For mid we want to check mid-1 and mid+1. 
if in ascending order, meaning the valid range is [mid+1:], else if in descending order, meaning the valid range is [:mid-1]. Else mid is the peak. Since arr is guaranteed to be a mountain arr, there won't be case like [mid] < [mid-1] && [mid]<[mid+1].
consider about special cases, when start = end - 1.
 */

class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        while(start < end - 1) {
            int mid = start + (end - start) / 2;
            if (arr[mid] > arr[mid+1] && arr[mid-1] > arr[mid]) {
                end = mid - 1;
            } else if (arr[mid] < arr[mid+1] && arr[mid-1] < arr[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return arr[start] > arr[end]? start : end;
        
    }
}

// can also only compare arr[mid] with arr[mid+1] to move the BS range.