// 2024.10.18 - came up with below 2 which match the solution
// basically every worker should choose the job within their ability and with the max profit.
// M1: sort [diff, profit], make profit into maxProfit until index, do binary search for each worker to find the first index with num > worker ability. Return the maxProfit from its left [diff, maxprofit] element. Time O(nlogn + n mlogn) = O((m+n)logn)
// M2: after sort [diff, profit] and maxprofit, if m << n, can also sort worker, then two pointers one for worker, another for [diff, maxprofit]. Time O(nlogn + n + mlogm +m + n) = O(nlogn + mlogm)
// M1
class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int n = profit.length;
        int[][] diffPro = new int[n][2];
        for (int i = 0; i <n; i++) {
            diffPro[i] = new int[]{difficulty[i], profit[i]};
        }
        Arrays.sort(diffPro, (a,b)->(a[0]==b[0]?a[1]-b[1]:a[0]-b[0]));
        int maxProfitSofar = diffPro[0][1];
        for (int i = 1; i <n; i++) {
            diffPro[i][1] = Math.max(maxProfitSofar, diffPro[i][1]);
            maxProfitSofar = diffPro[i][1];
        }
        int profitSum = 0;
        for (int w: worker) {
            profitSum += getProfit(diffPro, w);
        }
        return profitSum;
    }

    private int getProfit(int[][] arr, int w) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = left + (right-left) / 2;
            if (arr[mid][0] > w) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        int target = arr[left][0] > w ? left - 1 : left;
        return target >= 0? arr[target][1] : 0;
    }
}


// M2
class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int n = profit.length;
        int[][] diffPro = new int[n][2];
        for (int i = 0; i <n; i++) {
            diffPro[i] = new int[]{difficulty[i], profit[i]};
        }
        Arrays.sort(diffPro, (a,b)->(a[0]==b[0]?a[1]-b[1]:a[0]-b[0]));
        int maxProfitSofar = diffPro[0][1];
        for (int i = 1; i <n; i++) {
            diffPro[i][1] = Math.max(maxProfitSofar, diffPro[i][1]);
            maxProfitSofar = diffPro[i][1];
        }
        Arrays.sort(worker);
        int diffProIdx = 0;
        int profitSum = 0;
        for (int w: worker) {
            while (diffProIdx < diffPro.length && diffPro[diffProIdx][0] <= w) {
                diffProIdx++;
            }
            if (diffProIdx != 0) {
                profitSum += diffPro[--diffProIdx][1];  
            }
            
        }
        return profitSum;
    }

}