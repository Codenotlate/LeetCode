// 2024.9.25
// time O(nlogn+klogn) (maintain a minheap, it's like n sorted arrays)
// space O(n)
class Solution {
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        PriorityQueue<double[]> pq = new PriorityQueue<>((a,b) -> Double.compare(a[0], b[0])); // store arr[i]/arr[j], i, j
        int n = arr.length;
        for (int i = 0; i < n-1; i++) {
            pq.add(new double[]{arr[i]*1.0/arr[n-1], i, n-1});
        }
        while(!pq.isEmpty()) {
            double[] popout = pq.poll();
            int first = (int)popout[1];
            int second = (int)popout[2];
            k--;
            if (k== 0) {
                return new int[]{arr[first], arr[second]};
            }
            // add the next one with arr[i]
            pq.add(new double[]{arr[first]*1.0/arr[second-1], first, second-1});
        }
        return new int[]{0,0}; // should not hit this row
    }
}


// There's also binary search way, try BS way next time