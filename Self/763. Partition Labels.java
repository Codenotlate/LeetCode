class Solution {
	// method1: using iterval expanding
	// time O(n) space O(1) since only lowercase letters
    public List<Integer> partitionLabels(String S) {
        // build the [start, end] interval for each char in S
        // store in a list of 26 buckets
        int[][] intervals = new int[26][2];
        for (int i = 0; i < intervals.length; i++) {
        	intervals[i][0] = -1;
        	intervals[i][1] = -1;
        }
        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	// set the start of this char first time have this char
        	if (intervals[idx][0] == -1 ) {
        		intervals[idx][0] = i;
        	}
        	// update the end each time
        	intervals[idx][1] = i;
        }

        // go through s again and track the start and end of the current window
        int start = 0;
        int end = 0;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	if (intervals[idx][0] <= end) { // meaning overlap
        		end = Math.max(end, intervals[idx][1]);
        	} else { // nonoverlap
        		res.add(end - start + 1);
        		start = intervals[idx][0];
        		end = intervals[idx][1];
        	}
        }
        res.add(end - start + 1);
        return res;
    }
}



// method2: optimize method1, actually no need to keep track of start of the interval
// only need to expand end of the window each time, 
// and start will remain same until encounter a non-overlapping interval
// which is detected by index of s reached current end.
class Solution {
    public List<Integer> partitionLabels(String S) {
        // build a map to store the end index of each char in S
        int[] endPositions = new int[26];

        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	endPositions[idx] = i;
        }

        // second pass of S
        int start = 0;
        int end = 0;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < S.length(); i++) {
        	int idx = S.charAt(i) - 'a';
        	end = Math.max(endPositions[idx], end);

        	// when nonoverlapping
        	if (i == end) {
        		res.add(end - start + 1);
        		start = i + 1;
        	}
        }
        return res;
    }
}

// can also do it in sliding window way
//https://leetcode.com/problems/partition-labels/discuss/113264/Easy-O(n)-Java-solution-using-sliding-window-(two-pointers)-comments-and-explanation-given




//Phase3 self
// similar to above M2

class Solution {
    public List<Integer> partitionLabels(String s) {
        // first pass find the right most for each char
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }
        // second pass update curend and res
        List<Integer> res = new ArrayList<>();
        int curEnd = 0;
        int preLen = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i <= curEnd) {
                curEnd = Math.max(curEnd, map.get(s.charAt(i)));
            } else {
                res.add(curEnd + 1 - preLen);
                preLen = curEnd + 1;
                curEnd = map.get(s.charAt(i));
            }
        }
        res.add(curEnd + 1 - preLen);
        return res;
    }
}


// phase3 try sliding window
class Solution {
    public List<Integer> partitionLabels(String s) {
        // first pass: store count of char
        Map<Character, Integer> map = new HashMap<>();
        for (char c: s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        
        int cur = 0;
        int prev = 0;
        Set<Character> curWin = new HashSet<>();
        List<Integer> res = new ArrayList<>();
        while (cur < s.length()) {
            char c = s.charAt(cur);
            if (!curWin.contains(c)) {curWin.add(c);}
            
            map.put(c, map.get(c) - 1);
            if (map.get(c) == 0) {
                curWin.remove(c);
            }
            if (curWin.isEmpty()) {
                res.add(cur - prev + 1);
                prev = cur + 1;
            }          
            cur++;
        }
        return res;
    }
}



// review self: similar as above M2
// time O(n), space O(1)
class Solution {
    public List<Integer> partitionLabels(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }
        
        List<Integer> res = new LinkedList<>();
        int cur = 0;
        int sumRes = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > cur) {
                res.add(i-sumRes);
                sumRes = i;
                cur = map.get(s.charAt(i));
            } else {
                cur = Math.max(cur, map.get(s.charAt(i)));
            }
        }
        res.add(s.length()-sumRes);
        return res;
    }
}
class Solution {
    // try sliding window
    public List<Integer> partitionLabels(String s) {
        // first get the count of each char in s
        Map<Character, Integer> map = new HashMap<>();
        for (char c: s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        // loop the s, use set to represent chars in current window, whenever the window is empty, meaning can start the next substring window
        Set<Character> window = new HashSet<>();
        int prevPos = 0;
        List<Integer> res = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            window.add(s.charAt(i));
            map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
            if (map.get(s.charAt(i)) == 0) {
                window.remove(s.charAt(i));
            }
            if (window.isEmpty()) {
                res.add(i - prevPos + 1);
                prevPos = i + 1;
            }
        }
        return res;
    }
}






// Review
/*Initial thought
Get the end pos for each char. Then traverse the second time, update the end pos of the window based on current char until current pos = updated end pos. Add current pos - the sum pos added to res list to the res list.reset end = 0 and continue with next window.
time O(n) space O(26)
*/
class Solution {
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new LinkedList<>();
        int lastAdded = -1;
        // first round to get end pos for each char
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }
        // second round to partition
        int curEnd = 0;
        for (int i = 0; i < s.length(); i++) {
            curEnd = Math.max(curEnd, map.get(s.charAt(i)));
            if (i == curEnd) {
                res.add(i - lastAdded);
                lastAdded = i;
                curEnd = 0;
            }
        }
        return res;
    }
}






