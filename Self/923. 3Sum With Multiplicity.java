/*Thought
M1: with best space O(1)
ole way: sort first. Can't do the traditional one by one two pointer. Because if pair sum equals to target, we don't know which pointer to move and may miss some pairs due to pointer moving.
Conversion: when we have pair sum equal to target, we count all equal num at once.
if (num1 != num2) move i and j towards center until not equal to num1/num2. res += count1 * count2.
if (num1 == num2) res += C(j-i+1,2) And stop the pointer loop.
Time O(n^2 + nlogn) = O(n^2)   space O(1)

M2: from solution: time O(101(for sort & count unique nums) + k^2) where k is number of unique nums in arr. worst case when k == n -> O(n^2)
space O(k) -> O(n)
do two pointers on unique nums, sort unique nums(since max = 100, can do counting sort to have sort and counting the same time), and calculate count based on diff equal cases between x,y,z.
if (x != y!=z) {c = c1 * c2 * c3;}
else if (x == y != z) {c = C(c1,2) * c3;}
else if (x != y == z) {c = c1 * C(c2,2);}
else if (x == z != y) {c = C(c1,2) * c2;} // note this won't happen
else {c = C(c1,3)}


M3: self: use count map and no need to do sort
build the count map.
For each key(x) in the map, check all key y(including itself) in the map. If target-(x+y) in the map && x <= y <= z, then add count based on relationship between x, y and z similar to above M2.
time O(n + k^2) -> O(n^2)
space O(k) -> O(n)

*/
// M1 two pointers on arr
class Solution {
    public int threeSumMulti(int[] arr, int target) {
        Arrays.sort(arr);
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res = (res + getCount(arr, i+1, target-arr[i])) % 1000000007;
        }
        return res;
    }
    
    private int getCount(int[] arr, int left, int target) {
        int res = 0;
        int right = arr.length - 1;
        while (left < right) {
            int pairsum = arr[left] + arr[right];
            if (pairsum < target) {left++;}
            else if (pairsum > target) {right--;}
            else if (arr[left] != arr[right]) {
                int oldleft = left;
                int oldright = right;
                while(left < right&& arr[left] == arr[oldleft]) {left++;}
                // note the condition is right v.s.left-1
                while (left-1 < right && arr[right] == arr[oldright]) {right--;}
                res = (res + (left-oldleft) * (oldright-right)) % 1000000007;
            } else {
                res = (res + (right - left + 1) * (right - left)/2) % 1000000007;
                break;
            }
        }
        return res;
    }
}






// M2 counting sort unique nums
class Solution {
    public int threeSumMulti(int[] arr, int target) {
        long[] count = new long[101];
        int unique = 0;
        // build count array
        for (int n: arr) {
            count[n]++;
            if (count[n] == 1) {unique++;}
        }
        // build unique num array
        int[] keys = new int[unique];
        int keysIdx = 0;
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {keys[keysIdx++] = i;}
        }
        
        // two pointers on unique and sorted keys array
        int res = 0;
        for (int i = 0; i < keys.length; i++) {
            int newT = target - keys[i];
            int left = i; // since each num has multiple counts, we can't start on i+1
            int right = keys.length - 1;
            while (left <= right) { // since each num has multiple count, we should have equal sign here
                int pairsum = keys[left] + keys[right];
                if (pairsum < newT) {left++;}
                else if (pairsum > newT) {right--;}
                else {
                    res = (res + (int) getCount(keys[i], keys[left], keys[right], count)) % 1000000007;
                    left++;
                    right--;
                }
            }
        }
        return res;
    }
    
    
    // note use long here to deal with data range
    private long getCount(int x, int y, int z, long[] count) {
        long c = 0;
        long c1 = count[x];
        long c2 = count[y];
        long c3 = count[z];
        if (x != y && y != z) {c = c1 * c2 * c3;}
        else if ( x == y && y != z) {c = c1 * (c1-1)/2 * c3;}
        else if (x != y && y == z) {c = c1 * c2 * (c2-1) / 2;}
        else {c = c1 * (c1 -1 ) * (c1-2) / 6;}
        return c % 1000000007;
    }
    
    
}




// M3:using counting map without sort directly
class Solution {
    public int threeSumMulti(int[] arr, int target) {
        Map<Integer, Long> map = new HashMap<>();
        for (int n: arr) {
            map.put(n, map.getOrDefault(n,new Long(0)) + 1); // need to convert 0 to long type for the map, otherwise will have data overflow to wrong answer
        }
        int res = 0;
        for (int x: map.keySet()) {
            for (int y: map.keySet()) {
                int z = target - x- y;
                if (map.containsKey(z) && x <= y && y <= z) {
                    res = (res + (int) getCount(x, y, z, map));
                }
            }
        }
        return res;
    }
    
    private long getCount(int x, int y, int z, Map<Integer, Long> map) {
        long c = 0;
        long c1 = map.get(x);
        long c2 = map.get(y);
        long c3 = map.get(z);
        if (x != y && y != z) {c = c1 * c2 * c3;}
        else if ( x == y && y != z) {c = c1 * (c1-1)/2 * c3;}
        else if (x != y && y == z) {c = c1 * c2 * (c2-1) / 2;}
        else {c = c1 * (c1 -1 ) * (c1-2) / 6;}
        return c % 1000000007; 
    }
}












