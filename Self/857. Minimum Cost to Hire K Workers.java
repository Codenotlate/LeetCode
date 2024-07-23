// 2024.7.16
// Get quantity(quality) and price, first sort by price, then get the window with size k, the max price on the right side will be used for calculating. As we move to right, we update teh max price, and also get rid of the largest quantity in the previous window.
// time O(nlogn + nlogk) = O(nlogn)
class Solution {
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        double[][] qAndp = new double[quality.length][2];
        for (int i = 0; i < quality.length; i++) {
            // note need to do (double) for both
            qAndp[i] = new double[]{(double)quality[i], (double)wage[i]/quality[i]};
        }
        // note for double type need to use Double.compare(), can't go with plain version
        Arrays.sort(qAndp, (a,b)->Double.compare(a[1],b[1]));
        PriorityQueue<Double> pq = new PriorityQueue<>((a,b)->Double.compare(b,a));
        int idx = 0;
        double currentQ = 0;
        while (idx < k) {
            pq.add(qAndp[idx][0]);
            currentQ += qAndp[idx][0];
            idx++;
        }
        double res = qAndp[idx-1][1] * currentQ;
        while (idx < qAndp.length) {
            double maxP = qAndp[idx][1];
            double removeQ = pq.poll();
            currentQ = currentQ - removeQ + qAndp[idx][0];
            res = Math.min(res, maxP * currentQ);
            pq.add(qAndp[idx][0]);
            idx++;
        }
        return res;
    }
}