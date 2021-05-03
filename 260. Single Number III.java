class Solution {
	// method1: time O(n) space O(n)
    public int[] singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] res = new int[2];
        for (int n: nums) {
        	map.put(n, map.getOrDefault(n, 0) + 1);
        }
        int idx = 0;
        for (int n: map.keySet()) {
        	if (map.get(n) == 1) {
        		res[idx++] = n;
        	}
        }
        return res;
    }
}


class Solution {
	// method2: time O(n) space O(1)
	// using XOR bit manipulation
    public int[] singleNumber(int[] nums) {
        int diff = 0;
        for (int n: nums) {
        	diff ^= n;
        }
        diff &= -diff;
        int[] res = new int[2];
        for (int n: nums) {
        	if ((diff & n) == 0) {
        		res[0] ^= n;
        	} else {
        		res[1] ^= n;
        	}
        }
        return res;

    }
}