/*Thought
nextn = 10 * n + 1, assume rem = n % k;
then nextn % k = (10 * n + 1) % k = (10 * xk + 10 * rem + 1) % k = (10 * rem + 1) % k. Thus we can use rem for n to avoid overflow issue. And since rem in range [0, k-1], thus if  [1, k-1] is visited twice before we have rem = 0, then meaning there's a loop and we return -1. time O(k) space O(k)
improve space to O(1). We do it at most k times, if still can't reach rem = 0, meaning some num in [1,k-1] has been visited twice, thus return -1. time O(k) space O(1)
*/
class Solution {
    public int smallestRepunitDivByK(int k) {
        int len = 1;
        int n = 1;
        for (int i = 0; i < k; i++) {
            if (n % k == 0) {return len;}
            n = 10 * (n % k) + 1;
            len++;
        }
        return -1;
    }
}