// 2024.10.21
// the answer ranges from [1, max/(m-1)]. For each answer we test if it's doable. If mid is, then try left = mid, else right=mid-1;
// the check function requires all m balls can be assigned with a distance >= mid. This takes O(n)time. And need to sort the position array first.
// total time: O(nlogn + n * log(max/(m-1))) = O(n*log(n*max/m))

class Solution {
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int left = 1;
        int right = position[position.length-1] / (m-1);
        int res = 0;
        while (left <= right) {
            int mid = left + (right-left)/2;
            if (isValid(mid, position, m)) {
                res = mid; // this is a good way to handle <= case.
                left = mid + 1;
            } else {
                right = mid-1;
            }
        }
        return res;
    }

    private boolean isValid(int mid, int[] pos, int m) {
        int count = 1;
        int lastPos = pos[0];
        for (int p: pos) {
            if (p-lastPos >= mid) {
                count++;
                lastPos = p;
            }
        }
        return count >= m;
    }
}