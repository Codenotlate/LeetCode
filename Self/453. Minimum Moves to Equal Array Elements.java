/* From solution:  time O(n) space O(1)
https://leetcode.com/problems/minimum-moves-to-equal-array-elements/discuss/93817/It-is-a-math-question
math way: prove idea: original sum + m * (n - 1) = x * n. And since each time we add 1 to the n-1 elements other than the curmax, the original min value of the array will always get add 1 till it reaches final equal number x. Thus we also have x = min + m. Combine these two equation, we can solve it in math way:  m = sum - min * n.
*/
class Solution {
    public int minMoves(int[] nums) {
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int n: nums) {
            sum += n;
            min = Math.min(n, min);
        }
        return sum - min * nums.length;
    }
}


// There's also other ways in solution.











// Naive way: TLE
/*Intial thought
each move increment (n-1) elements and we want min move numbers, thus at each step the best way is to leave the biggest one unchanged and increments the rest n-1 elements. Because if we leave another element unchanged the largest element will get even larger and we need extra step later to make the unchanged element equal to the largest.
Each round loop all num in nums,init nextmax = curmax; if num == curmax, maxCount++ and if maxCount== 1, num unchange; other num++, nextmax = Math.max(updated num, nextmax); also tracking round number.
when maxCount == nums.length for that round, return round number.
*/
// class Solution {
//     public int minMoves(int[] nums) {
//         int roundNum = 0;
//         int curmax = Integer.MIN_VALUE;
//         for (int num: nums) {curmax = Math.max(curmax, num);}
//         int maxCount = 0;
//         while (maxCount != nums.length) {
//             int nextmax = curmax;
//             maxCount = 0;
//             for (int i = 0; i < nums.length; i++) {
//                 if (nums[i] == curmax) {
//                     maxCount++;
//                     if (maxCount == nums.length) {return roundNum;}
//                     if (maxCount == 1) {continue;}
//                 } 
//                 nums[i]++;
//                 nextmax = Math.max(nextmax, nums[i]);
//             }
//             curmax = nextmax;
//             roundNum++;
//         }
//         return roundNum;
//     }
// }








// Review - from above M1
/*Thought

sum + mov * (len-1) =  x * len......(1)

[5,6,6,6] -> [6,7,7,6] -> [7,8,7,7] -> [8,8,8,8]
min won't change. We want min == max in the array in the end. Thus we also have:
x = min + mov......(2)

combine (1) and (2), we have
=> sum + mov *(len-1) = min*len + mov * len
=> mov = sum - min * len
*/
class Solution {
    public int minMoves(int[] nums) {
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for (int n: nums) {
            sum += n;
            min = Math.min(min, n);
        }
        return sum - min * nums.length;
    }
}


/*improved from M2 from solution
view each step as add min to current max, it takes diff = curmax- curmin moves.
Detail in notions(copied below)
### **Approach #3 Using Sorting [Accepted]**

**Algorithm**

The problem gets simplified if we sort the given array in order to obtain a sorted array a*a*. Now, similar to Approach 2,we use the difference diff=max-min*diff*=*max*−*min* to update the elements of the array, but we need not traverse the whole array to find the maximum and minimum element every time, since if the array is sorted, we can make use of this property to find the maximum and minimum element after updation in O(1)*O*(1) time. Further, we need not actually update all the elements of the array. To understand how this works, we'll go in a stepwise manner.

Firstly, assume that we are updating the elements of the sorted array after every step of calculating the difference diff*diff*. We'll see how to find the maximum and minimum element without traversing the array. In the first step, the last element is the largest element. Therefore, diff=a[n-1]-a[0]*diff*=*a*[*n*−1]−*a*[0]. We add diff*diff* to all the elements except the last one i.e. a[n-1]*a*[*n*−1]. Now, the updated element at index 0 ,a'[0]*a*′[0] will be a[0]+diff=a[n-1]*a*[0]+*diff*=*a*[*n*−1]. Thus, the smallest element a'[0]*a*′[0] is now equal to the previous largest element a[n-1]*a*[*n*−1]. Since, the elements of the array are sorted, the elements upto index i-2*i*−2 satisfy the property a[j]>=a[j-1]*a*[*j*]>=*a*[*j*−1]. Thus, after updation, the element a'[n-2]*a*′[*n*−2] will become the largest element, which is obvious due to the sorted array property. Also, a[0] is still the smallest element.

Thus, for the second updation, we consider the difference diff*diff* as diff=a[n-2]-a[0]*diff*=*a*[*n*−2]−*a*[0]. After updation, a''[0]*a*′′[0] will become equal to a'[n-2]*a*′[*n*−2] similar to the first iteration. Further, since a'[0]*a*′[0] and a'[n-1]*a*′[*n*−1] were equal. After the second updation, we get a''[0]=a''[n-1]=a'[n-2]*a*′′[0]=*a*′′[*n*−1]=*a*′[*n*−2]. Thus, now the largest element will be a[n-3]*a*[*n*−3]. Thus, we can continue in this fashion, and keep on incrementing the number of moves with the difference found at every step.

Now, let's come to step 2. In the first step, we assumed that we are updating the elements of the array a*a* at every step, but we need not do this. This is because, even after updating the elements the difference which we consider to add to the number of moves required remains the same because both the elements max*max* and min*min* required to find the diff*diff* get updated by the same amount everytime.

Thus, we can simply sort the given array once and use moves=\sum_{i=1}^{n-1} (a[i]-a[0])*moves*=∑*i*=1*n*−1(*a*[*i*]−*a*[0]).

**Complexity Analysis**

- Time complexity : O\big(nlog(n)\big)*O*(*nlog*(*n*)). Sorting will take O\big(nlog(n)\big)*O*(*nlog*(*n*)) time.
- Space complexity : O(1)*O*(1). No extra space required.



*/




/*
The idea is the same as M2, but instead sort first, we can simply find the min and add all diff together. Which takes O(n) time instead of O(nlogn). 

This is also actually the same calculation method as the Math way.


*/
class Solution {
    public int minMoves(int[] nums) {
        int count = 0;
        int min = Integer.MAX_VALUE;
        int minIdx = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
                minIdx = i;
            }
        }
        for (int n: nums) {count += n - min;}
        return count;
        
    }
}