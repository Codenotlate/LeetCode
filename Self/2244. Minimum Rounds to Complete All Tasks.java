// 23/1/31 - self done
/*Thoughts - same as the best solution
count each diff first. Then for each count, we need to figure outhow many min rounds is needed. count = 3k/3k+1/3k+2. for (1)and(3), rounds = k and k+1. For (2), if k >= 1, then 3k+1 = 3(k-1) + 4, rounds = k+1; otherwise, rounds = -1.
time O(n) space O(n)

*/
class Solution {
    public int minimumRounds(int[] tasks) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int t: tasks) {
            map.put(t, map.getOrDefault(t,0)+1);
        }
        int res = 0;
        for (int count: map.values()) {
            int k = count / 3;
            int rem = count % 3;
            if (rem == 0) {res += k;}
            else if (rem == 2) {res += k+1;}
            else {
                if (k == 0) {return -1;}
                res += k+1;
            }
        }
        return res;
    }
}