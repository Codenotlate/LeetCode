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


// 2024/3/18
/*
Very similar idea as above, use binary search first to find the closest pos, then two pointers to add to res, and remeber to use linkedlist to make add to the front O(1) time.
Time: O(logn + k) Space O(1)
*/

class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int closest = binarySearchClosest(arr, x);
        int left = closest-1;
        int right = closest+1;
        List<Integer> res = new LinkedList<>();
        res.add(arr[closest]);
        while(res.size() != k) {
            if (left >= 0 && right < arr.length) {
                if (Math.abs(arr[left]-x) <= Math.abs(arr[right]-x)) {
                    res.add(0,arr[left]);
                    left--;
                } else {
                    res.add(arr[right]);
                    right++;                   
                }
            } else if (left < 0) {
                res.add(arr[right]);
                right++;             
            } else {
                res.add(0,arr[left]);
                left--;
            }
        }
        return res;
    }


    private int binarySearchClosest(int[] arr, int x) {
        int start = 0;
        int end = arr.length -1;
        while (start < end - 1) {
            int mid = start + (end-start)/2;
            if (arr[mid]==x) {
                return mid;
            }
            if (arr[mid] > x) {
                end = mid;
            } else {
                start = mid;
            }
        }
        return Math.abs(arr[start]-x) <= Math.abs(arr[end]-x)? start:end;
    }
}


/* another interesting way - find the leftmost element in the array. Assume that's pos i, since the result array will be at length k, we should not expect pos [i+k] to be included in the result. Thus for binary search, the removing condition can be comparing arr[i] with arr[i+k]. If arr[i] is closer than a[i+k], we know end = i; else start = i+1.
Also the binary search end pointer can start with len-k.
Time O(log(n-k) + k) space O(1)
*/

class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int start = 0;
        int end = arr.length - k;
        while (start < end) {
            int mid = start + (end-start)/2;
            if (x-arr[mid] <= arr[mid+k] - x) {
                end = mid;
            } else {
                start = mid+1;
            }
        }

        List<Integer> res = new LinkedList<>();
        for (int i =start; i < start + k; i++) {
            res.add(arr[i]);
        }
        return res;
    }
}



// 2024.10.9
// Naive way: using a PQ and define the specific order, but it's not optimized cause it didn't use the sorted characteristic of the array. time O(nlogn)
// binary search to find the number >= x. O(logn) (note it's possible no value is returned)
// have left = pos-1, right = pos, expand the window to the two sides. Comparing left number with right number regarding how close they are to x. Add the closer one to result and move its pointer to expand the window. O(k)
// time O(logn + k)
// another interesting way as above
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int pos = findLE(arr, x);
        int left = pos - 1;
        int right = pos;
        List<Integer> res = new ArrayList<>();
        while (right-left-1 < k) {
            if (left < 0 || (right < arr.length && compare(arr[right], arr[left], x) < 0)) {
                right++;
            } else {
                left--;
            }
        }
        for (int i = left+1; i < right; i++) {res.add(arr[i]);}
        return res;
    }

    // if no value largerthanorequalto x, return arr.len
    private int findLE(int[] arr, int x) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = left + (right-left)/2;
            if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return arr[left] >= x ? left : arr.length;
    }

    private int compare(int a, int b, int x) {
        int diff = Math.abs(a-x) - Math.abs(b-x);
        if (diff < 0 || (diff==0 && a < b)) {
            return -1;
        }
        return 0;
    }
}
















