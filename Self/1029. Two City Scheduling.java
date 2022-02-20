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