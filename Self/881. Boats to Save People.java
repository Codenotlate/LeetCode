/* from hint
sort the array first. Then do two pointers from start and end of the array. if (arr[end] + arr[start] > limit): res++; end--; otherwise, since at most two people at the same time, res++; start++; end--.

time O(nlogn) space O(space for sort)
*/
class Solution {
    public int numRescueBoats(int[] people, int limit) {
        int res = 0;
        Arrays.sort(people);
        int start = 0;
        int end = people.length-1;
        while(start <= end) {
            int sum = people[start] + people[end];
            if (sum <= limit) {start++;}
            end--;
            res++;
        }
        return res;
    }
}



// discussion about an O(limit) way
// https://leetcode.com/problems/boats-to-save-people/discuss/197063/easy-thought-process-to-improve-from-O(nlogn)-to-O(n)

// follow up discussion on what if  each boat can carry at most k people instead of only 2 people at the same time
// https://leetcode.com/problems/boats-to-save-people/discuss/157289/What-if-each-boat-carries-at-most-K-people-(instead-of-2-people)-at-the-same-time










// Review
// time O(nlogn) space O(logn)
class Solution {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int start = 0;
        int end = people.length - 1;
        int count = 0;
        while (start < end) {
            if (people[start] + people[end] <= limit) {start++;}
            end--;
            count++;
        }
        return start == end ? count + 1 : count;
    }
}