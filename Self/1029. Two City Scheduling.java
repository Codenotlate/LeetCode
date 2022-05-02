class Solution {
    /*Based on solution:
    we notice that if that costA-costB is very small(negative), we tend to choose A over B and vice versa. Thus we sort all elements based on costA-costB. For the first n elements, we would choose cityA and the rest n we choose costB.
    
    time O(nlogn + n) = O(n)
    space O(1) based on sort algo implementation
    */
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, (c1, c2) -> (c1[0] - c1[1] - (c2[0] - c2[1])));
        int sum = 0;
        int n = costs.length;
        for (int i = 0; i < n; i++) {
            sum += i < n / 2 ? costs[i][0] : costs[i][1];
        }
        return sum;
    }
}





// Review

/*Thought
Consider we have two people, we need to decide who goto A and who goto B with min cost. This actually depends on diff of costa and costb for each person. The person with larger (costb-costa) diff prefers A more than B and vise virsa. Thus we need to sort n people based on their ab cost diff. And choose the first half people with bcost and the rest half with acost.
time O(nlogn)  space O(sort)
*/
class Solution {
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, (c1,c2) -> (c1[1] - c1[0] - (c2[1]-c2[0])));
        int res = 0;
        for (int i = 0; i < costs.length; i++) {
            res += i < costs.length / 2? costs[i][1] : costs[i][0];
        }
        return res;
    }
}