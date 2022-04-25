/*Initial thought
notice if n % i != 0 then n % (k * i) != 0
naive way is to test factor from 1 to n, count those n % i == 0, return i when count == k. This way time O(n) space O(1)

from hint: improvement
notice that factors are appearing in pairs, i.e. if n % i == 0, then (i, n/i) are both factors of n. Thus we can only check the first 1 to sqrt(n) numbers, and store the factors and count the number in range( 1, sqrt(n)).
if count == k in the process, return that factor i.
if count < k when we processed all number in range [1, sqrt(n)], then the kth factor is actually the pair factor of factors[count -(k - count)]. simply return n / that factor.
time O(sqrt(n))
space O(min(sqrt(n), k))
*/ 
class Solution {
    public int kthFactor(int n, int k) {
        int sqrtN = (int) Math.sqrt(n);
        List<Integer> factors = new ArrayList<>();
        int count = 0;
        for (int i = 1; i < sqrtN + 1; i++) {
            if(n % i == 0) {
                factors.add(i);
                count++;
            }
            if(count == k) {return i;}
        }
        // note if n is a perfect square, we need to skip one
        int adjust = sqrtN * sqrtN == n ? 1 : 0;
        int pairIdx = count - 1 - (k-count-1) - adjust;
        return pairIdx < 0 ? -1 : n / factors.get(pairIdx);
    }
}





// Review
/*Thought
Appear as pair, we can traverse sqrt(n) pair.  Pay attention to perfect square numbers.
time O(sqrt(n)) space O(min(sqrt(n), k))
*/
class Solution {
    public int kthFactor(int n, int k) {
        List<Integer> factors = new LinkedList<>();
        int i = 1;
        int count = 0;
        while (i * i <= n) {
            if (n % i == 0) {
                factors.add(i); 
                count++;
                if (count == k) {return i;}
            }
            i++;
        }
        i--;
        int len = n == i * i? (2 * count - 1) : (2 * count);
        if (k > len) {return -1;}
        return n / factors.get(len-k);
    }
}