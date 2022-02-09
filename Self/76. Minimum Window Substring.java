// This problem description is not clear
// it should be return the minimum window in s which will contain all the characters with same frequency in t

// phase3 solution M1
// Time O(T+ 2*S)= O(T+ S）
// space O(T) since the countmap for window can only count the chars in t.
class Solution {
    public String minWindow(String s, String t) {
        // build the freq map for t
        Map<Character, Integer> tMap = new HashMap<>();
        for (char c: t.toCharArray()) {
        	tMap.putIfAbsent(c, 0);
        	tMap.put(c, tMap.get(c) + 1);
        }
        int uniqueChar = tMap.keySet().size();

        // use two pointers for sliding window over s
        int left = 0;
        int right = 0;
        int satisfiedNum = 0; // used to record how many chars in current window satisfy the freq in tMap, when satisfiedNum == uniqueChar, this window is valid.
        // window Map to track the freq of chars in current window and compare with tMap to see if the window is valid
        Map<Character, Integer> windowMap = new HashMap<>();

        // use int[] to store the result [leftidx, rightidx, minLen]
        int[] res = new int[3];
        res[2] = s.length() + 1;

        while (right < s.length()) {
        	char c = s.charAt(right);
        	if (tMap.keySet().contains(c)) {
        		windowMap.putIfAbsent(c, 0);
        		windowMap.put(c, windowMap.get(c) + 1);
        		// !!! for comparison between objects(Integer), convert it to .intValue() first, never use == to compare !!!
        		// if (windowMap.get(c) == tMap.get(c)) {
        		if (windowMap.get(c).intValue() == tMap.get(c).intValue()) {
        			satisfiedNum++;
        		}
        	}

        	// find the valid window, then move left pointer to see if there's a smaller one valid
        	// don't forget about left <= right
        	while (left <= right && satisfiedNum == uniqueChar) {
        		// update the res
        		if (right - left + 1 < res[2]) {
        			res[0] = left;
        			res[1] = right;
        			res[2] = right - left + 1;
        		}

        		// move the left, and adjust the impact the move brings
        		char l = s.charAt(left);
        		if (windowMap.keySet().contains(l)) {
        			windowMap.put(l, windowMap.get(l) - 1);

        			if (windowMap.get(l).intValue() < tMap.get(l).intValue()) {
        				satisfiedNum--;
        			}
        		}
        		left++;
        	}

        	// when current window no longer valid, expand right one step further
        	right++;
        	
        }

        // return the String

        return res[2] == s.length() + 1 ? "" : s.substring(res[0], res[1] + 1);
    }
}



/* DON‘T USE == FOR OBJECT COMPARISON!!!
The Integer.intValue() is used to get the primitive int value of Integer. The other test cases pass for you because the int values are low and the auto-unboxing feature of java does the value comparison for you. But for this one test case the int values are pretty large and hence it leads to object comparison instead of the value comparison which is not what we want.
You can get more details from here:
https://stackoverflow.com/questions/3130311/weird-integer-boxing-in-java
https://stackoverflow.com/questions/3131136/integers-caching-in-java?noredirect=1&lq=1`

*/


// Phase 3 solution M2: filter S first
// time O(S + T + 2 * filterS len) when S >>> filterS len, M2 will be much faster than M1
// Space O(T)
class Solution {
    public String minWindow(String s, String t) {
        // build the tMap similarly
        Map<Character, Integer> tMap = new HashMap<>();
        for (char c: t.toCharArray()) {
        	tMap.putIfAbsent(c, 0);
        	tMap.put(c, tMap.get(c) + 1);
        }
        int uniqueChar = tMap.keySet().size();

        // convert S to filter S
        List<Pair<Character, Integer>> filterS = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
        	if (tMap.containsKey(s.charAt(i))) {
        		filterS.add(new Pair(s.charAt(i), i));
        	}
        }

        int left = 0;
        int right = 0;
        Map<Character, Integer> windowMap = new HashMap<>();
        int[] res = new int[3];
        res[2] = s.length() + 1;
        // this time use uniqueChar-- instead 
        while (right < filterS.size()) {
        	char rChar = filterS.get(right).getKey();
        	int rIdx = filterS.get(right).getValue();

        	windowMap.putIfAbsent(rChar, 0);
        	windowMap.put(rChar, windowMap.get(rChar) + 1);

        	if (windowMap.get(rChar).intValue() == tMap.get(rChar).intValue()) {
        		uniqueChar--;

        		while (uniqueChar == 0) {
        			// uniqueChar == 0 here means window is valid
        			char lChar = filterS.get(left).getKey();
        			int lIdx = filterS.get(left).getValue();
        			if (rIdx - lIdx + 1 < res[2]) {
        				res[2] = rIdx - lIdx + 1;
        				res[0] = lIdx;
        				res[1] = rIdx;
        			}

        			// move left pointer one step forward
        			windowMap.put(lChar, windowMap.get(lChar) - 1);
        			if (windowMap.get(lChar).intValue() < tMap.get(lChar).intValue()) {
        				uniqueChar++;
        			}
        			left++;
        		}
        	}
        	right++;
        }

        return res[2] == s.length() + 1 ? "" : s.substring(res[0], res[1] + 1);
    }
}


// a post with template for this kind of problems
// https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems

// also since it's just english letter, using 128 or 256 array would be quicker than using a map, but time complexity wise, they are the same.




// Review Self
// similar to above M1
class Solution {
    /* thought from solution
    if s len smaller than t, return immediately
    build the count map for t. Then have the window starts as both start and end pointers point to zero index. Expand the end pointer till the window contains count map for t. For this check, we need to keep a countmap for the current s window and we can use a integer to track the number of chars in the current window that has count >= the count of the char in t. If the number of chars equal to the total number of unique chars in t, then the current window contains t. 
    Then we shrink the current window by moving start pointer until the number in current window won't match t, and we track the min substring along the way by tracking the start,end position of it.
    When current window no longer satisfy the requirement of t, we repeat to expand the window by moving end pointer again. We repeat this process until end pointer reaches the end of s string.
    time O(2 * s.len + t.len) space O(t.len)
    */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {return "";}
        // build the countMap for t
        Map<Character, Integer> tMap = new HashMap<>();
        for (char c: t.toCharArray()) {
            tMap.putIfAbsent(c, 0);
            tMap.put(c, tMap.get(c) + 1);
        }
        
        // start the sliding window
        int start = 0;
        int end = 0;
        Map<Character, Integer> sWindowMap = new HashMap<>();
        int[] minRes = new int[]{0, s.length() + 1}; // used to record the smallest valid window start and end position
        int matchCharNum = 0;
        while (end < s.length()) {
            // expand the end pointer to find the first valid window
            while(end< s.length()) {
                char c = s.charAt(end);
                if (tMap.containsKey(c)) {
                    sWindowMap.putIfAbsent(c, 0);
                    sWindowMap.put(c, sWindowMap.get(c) + 1);
                    if (sWindowMap.get(c).intValue() == tMap.get(c).intValue()) {matchCharNum++;}
                    if (matchCharNum == tMap.keySet().size()) {break;}
                }
                end++;                
            }
            
            if (end >= s.length()) {break;}
            // otherwise, we have found the first valid window, now we shrink it by moving start pointer
            while (start <= end) {
                if (end - start < minRes[1] - minRes[0]) {minRes[0] = start; minRes[1] = end;}
                char c = s.charAt(start);
                if (tMap.containsKey(c)) {
                    sWindowMap.put(c, sWindowMap.get(c) - 1);
                    if (sWindowMap.get(c).intValue() < tMap.get(c).intValue()) {matchCharNum--; start++; break;}
                }
                start++;
            }
            // don't forget this line
            end++;
        }
        
        return minRes[1] == s.length() + 1? "" : s.substring(minRes[0], minRes[1]+1);
        
    }
}

// Try M2 optimized way next time




















