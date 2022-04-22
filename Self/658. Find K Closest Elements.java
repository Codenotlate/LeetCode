/* Initial thought
use binary search to first find the first position of number >= x in arr.
if return -1, meaning x > all num in arr, simply return arr[len-k, len-1].
if return 0, meaning x <= all num in arr, simply return arr[0, k-1]
if return pos in between, have two pointers(i,j) starting at pos-1 and pos.
if (j-i+1) > k, check if arr[i] closer to arr[j], j--; else i++. break the loop and add arr[i:j] to result list.
otherwise, check if arr[i] closer to arr[j],i == 0 ? j++ : i--; else j == arr.length-1? i--: j++.

time O(logn + k)
space O(1) exclude result space
*/
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> res = new LinkedList<>();
        int pos = search(arr, x);
        if (pos == -1 || k == arr.length) {
            for(int i = arr.length - k; i < arr.length; i++) {res.add(arr[i]);}
            return res;
        } else if (pos == 0) {
            for (int i = 0; i < k; i++) {res.add(arr[i]);}
            return res;
        }
        int i = pos-1;
        int j = pos;
        while(i >= 0 && j < arr.length) {
            if (j - i + 1 > k) {
                if(isCloser(arr, i, j, x)) {j--;}
                else {i++;}
                break;
            }
            if(isCloser(arr, i, j, x)) {
                if(i == 0) {j++;}
                else {i--;}
            } else {
                if(j == arr.length - 1) {i--;}
                else {j++;}
            }
        }
        // need to add this line, for the case when k == arr.length. Or we can treat it as a special case in the beginning with pos == -1 case.
        // i = Math.max(i,0);
        // j = Math.min(j, arr.length-1);
        
        for (int w = i; w <= j; w++) {
            res.add(arr[w]);
        }
        return res;
    }
    
    private boolean isCloser(int[] arr, int i, int j, int x) {
        return (Math.abs(arr[i] - x) < Math.abs(arr[j] - x)) || (Math.abs(arr[i] - x) == Math.abs(arr[j] - x) && arr[i] < arr[j]);
    }
    
    // this search for the first element in arr that is >= target, return -1 if no such element.
    private int search(int[] arr, int target) {
        int n = arr.length;
        if (arr[n-1] < target) {return -1;}
        int start = 0;
        int end = n - 1;
        while(start < end) {
            int mid = start + (end - start) / 2;
            if(arr[mid] >= target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return start;
    }
}

// above self solution is the same as solution M2. There's another method M3, where we use binary search to find the leftmost position of the final answer. And simply add k elements starting that position. time O(log(n-k) + k) space O(1)
// https://leetcode.com/problems/find-k-closest-elements/discuss/106426/JavaC%2B%2BPython-Binary-Search-O(log(N-K)-%2B-K)







// Review - similar as above way

// time O(logn + k) space O(1)
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int firstpos = binaryfind(arr, x);
        List<Integer> res =new LinkedList<>();
        if (firstpos == 0) {
            for (int i = 0; i < k; i++) {res.add(arr[i]);}
        } else if (firstpos == -1) {
            for (int i = arr.length - k; i < arr.length; i++) {res.add(arr[i]);}
        } else {
            //two pointers
            int p1 = firstpos - 1;
            int p2 = firstpos;
            int count = 0;
            while (p1 >= 0 || p2 < arr.length) {
                if (count == k) {break;}
                int p1num = p1 >= 0? Math.abs(arr[p1]-x): Integer.MAX_VALUE;
                int p2num = p2 < arr.length? Math.abs(arr[p2] - x) : Integer.MAX_VALUE;
                if(p1num <= p2num) {
                    p1--;
                } else {
                    p2++;
                }
                count++;
                
            }
            for (int w = p1+1; w <p2; w++) {res.add(arr[w]);}
        
        }
        
        
        return res;
    }
    
    
    
    private int binaryfind(int[] arr, int x) {
        int n = arr.length;
        if (arr[0] >= x) {return 0;}
        if (arr[n-1] < x) {return -1;}
        int start = 0;
        int end= n - 1;
        while(start < end) {
            int mid = start + (end - start) / 2;
            if (arr[mid] < x) {start  = mid + 1;}
            else {end = mid;}
        }
        return start;
        
    }
}




























