class Solution {
	// just use two pointers method same as 167
	// since we have square, the search range is from 0 to (int) sqrt(c)
    public boolean judgeSquareSum(int c) {
        int end = (int) Math.sqrt(c);
        int start = 0;
        // since a could be equal to b
        while (start <= end) {
        	int sum = start * start + end * end;
        	if (sum > c) {
        		end -= 1;
        	} else if (sum < c) {
        		start += 1;
        	} else {
        		return true;
        	}
        }
        return false;
    }
}
// above way is no longer valid for test case c = 2147483600, overflow issue for sum.


// 2024.10.16
// O(sqrt(C)) - TLE way for large C
class Solution {
    public boolean judgeSquareSum(int c) {
        int n = 0;
        Set<Integer> squares = new HashSet<>();
        while (n*n <= c) {
            squares.add(n*n);
            n+=1;
        }
        for (int square: squares) {
            if (square > c / 2) {break;}
            if (squares.contains(c-square)) {return true;}
        }
        return false;
    }
}
// Need to handle overflow issue with long
// time O(sqrt(c))
class Solution {
    public boolean judgeSquareSum(int c) {
        int left = 0;
        int right = (int)Math.sqrt(c);
        while (left <= right) {
            long sum = (long)left * left + right * right;
            if (sum == c) {return true;}
            if (sum < c) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }
}