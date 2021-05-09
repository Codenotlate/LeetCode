class Solution {
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        // decide if it's illigal
        if (nums.length == 0 || r * c != nums.length * nums[0].length) {
        	return nums;
        }

        int[][] res = new int[r][c];
        int c0 = nums[0].length;
        for (int i = 0; i < r; i++) {
        	for (int j = 0; j < c; j++) {
        		int i0 = (i * c + j) / c0;
        		int j0 = (i * c + j) % c0;
        		res[i][j] = nums[i0][j0];
        	}
        }
        return res;
    }
}

// similar as approach1, just simplify i*c+j part to count
public class Solution {
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int[][] res = new int[r][c];
        if (nums.length == 0 || r * c != nums.length * nums[0].length)
            return nums;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                res[count / c][count % c] = nums[i][j];
                count++;
            }
        }
        return res;
    }
}

// using a queue, this takes extra space
class Solution {
    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int[][] res = new int[r][c];
        if (nums.length == 0 || r * c != nums.length * nums[0].length)
            return nums;
        Queue<Integer> queue = new LinkedList();
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                queue.add(nums[i][j]);
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[i][j] = queue.remove();
            }
        }
        return res;
    }
}