/*Thought
naive way: BFS, for each number, two possible neighbors. time and space are O(2^res) -- too slow
Better way from solution: move backwards is divide by 2 and add 1. Notice if x is odd, we can only choose to +1. If x is even, then x/2 + 1 is always better(less operations) than (x + 1 + 1) / 2, thus always choose /2 for even numbers.

*/
class Solution {
    public int brokenCalc(int startValue, int target) {
        int count = 0;
        while (startValue < target) {
            if (target % 2 == 0) {
                target /= 2;
            } else {
                target += 1;
            }
            count++;
        }
        return count + startValue - target;
    }
}



/* why moving backwards:
If with subtraction and multiplication, the effect of earlier subtraction will be amplified by later multiplications, hence, greedily doing multiplication might not reach optimal solution as an additional early subtraction can have a huge effect.
Whereas with addition and division, earlier addition will be diminished by later divisions. It is thus always better to do division wherever possible.

detailed proof of greedy algo
https://leetcode.com/problems/broken-calculator/discuss/236565/Detailed-Proof-Of-Correctness-Greedy-Algorithm






*/