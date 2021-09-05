// self naive way: very slow
class Solution {
    public int numTrees(int n) {
        return getNum(1, n);
    }
    
    private int getNum(int min, int max) {
        if (min >= max) {return 1;}
        int res = 0;
        for (int i = min; i <= max; i++) {
            int left = getNum(min, i - 1);
            int right = getNum(i + 1, max);
            res += left * right;
        }
        return res;
    }
}


// self M2: improve the M1 by storing the result in a map to avoid repetitive work
// time O(n^2), space O(n ^ 2)
class Solution {
    public int numTrees(int n) {
        Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
        return getNum(1, n, map);
    }
    
    private int getNum(int min, int max, Map<Pair<Integer, Integer>, Integer> map) {
        if (min >= max) {return 1;}
        if (map.keySet().contains(new Pair(min, max))) {return map.get(new Pair(min, max));}
        int res = 0;
        for (int i = min; i <= max; i++) {
            int left = getNum(min, i - 1, map);
            int right = getNum(i + 1, max, map);
            res += left * right;
        }
        map.put(new Pair(min, max), res);
        return res;
    }

}

// self M3: improve from M1 by storing result in map for only the difference between max and min instead of (min, max) pair. because we notice getNum(2,3) is actually 	the same as getNum(1,2), thus we only need to store getNum(1).
// time O(n^2), space O(n)
class Solution {
    public int numTrees(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        return getNum(1, n, map);
    }
    
    private int getNum(int min, int max, Map<Integer, Integer> map) {
        if (min >= max) {return 1;}
        if (map.keySet().contains(max - min)) {return map.get(max - min);}
        int res = 0;
        for (int i = min; i <= max; i++) {
            int left = getNum(min, i - 1, map);
            int right = getNum(i + 1, max, map);
            res += left * right;
        }
        map.put(max - min, res);
        return res;
    }

}


// same as M3, we can also do the DP way
// time O(n^2), space O(n)
class Solution {
    public int numTrees(int n) {
        int[] dp = new int [n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[i - j] * dp[j - 1];
            }
        }
        
        return dp[n];
    }
}


// all Java solution summary
//https://leetcode.com/problems/unique-binary-search-trees/discuss/409987/Summary-of-All-Solutions-in-Java-with-Explanations
















