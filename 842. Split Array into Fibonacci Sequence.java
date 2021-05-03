class Solution {
    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> curList = new LinkedList<>();
        if(dfs(curList, S, 0)) {return curList;}
        return new LinkedList<Integer>();
    }

    private boolean dfs(List<Integer> curList, String S, int curPos) {
    	if (curPos == S.length()) {
    		if (curList.size() >= 3) {return true;}
    		return false;
    	}

    	for (int i = curPos + 1; i <= S.length() && i <= curPos + 10; i++) {
    		// deal with leading zero
    		if (S.charAt(curPos) == '0' && i > curPos + 1) {break;}
    		long next = Long.valueOf(S.substring(curPos, i));
    		if(isValid(next, curList)) {
    			curList.add((int) next);
    			if(dfs(curList, S, i)) {return true;}
    			curList.remove(curList.size() - 1);
    		}
    	}
        return false;
    }

    private boolean isValid(long n, List<Integer> F) {
    	int len = F.size();
    	boolean inRange = n >= 0 && n <= Math.pow(2, 31) - 1;
    	if (len >= 2) {
    		return inRange && F.get(len - 2) + F.get(len - 1) == n;
    	}
    	return inRange;
    }
}

//https://leetcode.com/problems/split-array-into-fibonacci-sequence/discuss/133936/short-and-fast-backtracking-solution
/*
Two improvement: 
1) use num * 10 + char - '0' to get next long number instead of using substring
2) if next already > F(-1) + F(-2), break the loop to make it faster.
*/

// own code improved with two above
class Solution {
    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> curList = new LinkedList<>();
        if(dfs(curList, S, 0)) {return curList;}
        return new LinkedList<Integer>();
    }

    private boolean dfs(List<Integer> curList, String S, int curPos) {
    	if (curPos == S.length()) {
    		if (curList.size() >= 3) {return true;}
    		return false;
    	}
    	long next = 0;
    	for (int i = curPos; i < S.length() && i <= curPos + 9; i++) {
    		// deal with leading zero
    		if (S.charAt(curPos) == '0' && i > curPos) {break;}
    		next = 10 * next + （S.charAt(i) - '0'）;
    		// no need to continue the loop if next already > F[-1] + F[-2]
    		if (curList.size() >= 2 && curList.get(curList.size() - 1) + curList.get(curList.size() - 2) < next) {break;}
    		if(isValid(next, curList)) {
    			curList.add((int) next);
    			if(dfs(curList, S, i + 1)) {return true;}
    			curList.remove(curList.size() - 1);
    		}
    	}
        return false;
    }

    private boolean isValid(long n, List<Integer> F) {
    	int len = F.size();
    	boolean inRange = n >= 0 && n <= Math.pow(2, 31) - 1;
    	if (len >= 2) {
    		return inRange && F.get(len - 2) + F.get(len - 1) == n;
    	}
    	return inRange;
    }
}


// another O(N) way, use the property of value <= 2^31 -1, i.e. length <= 10
// find all possible combination of f(1) and f(2), for each comb, iterate the string once, move forward only when the third number of f(1) + f(2) can be find in the string
// https://leetcode.com/problems/split-array-into-fibonacci-sequence/discuss/134535/O(N)-solution-in-python 
// Java version also in solution













