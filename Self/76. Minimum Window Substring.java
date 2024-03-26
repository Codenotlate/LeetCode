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




// Review
/* Thought
if t.len < s.len, return "";
count array for t first, record nonZeros as well.using sliding window on s also update the start pos of minLen subString along the way.
Move right pointer, count[right-'a']--.If count[right-'a']==0, nonZeros--. If (nonZeros = 0), start to shrink the window size by moving left pointers.
count[left-'a']++, if count[left-'a'] == 1, nonZeros++. If (nonZeros != 0), compare right-left+1 with minLen. If it's smaller,update minLen to it and update start pos as left. Stop the window shrink. left++, and start a new round(moving right again).

every char processed exactly twice, time O(m+n) space O(1)

*/
class Solution {
    public String minWindow(String s, String t) {
        if(t.length() > s.length()) {return "";}
        int[] count = new int[58];
        int nonZeros = 0;
        for (char c: t.toCharArray()) {
            count[c-'A']++;
            if (count[c-'A'] == 1) {nonZeros++;}
        }
        
        int left = 0;
        int right = 0;
        int minLen = s.length() + 1;
        int startpos = -1;
        
        while (right < s.length()) {
            // move right to expand the window
            while (right < s.length()) {
                int idx = s.charAt(right) -'A';
                count[idx]--;
                if (count[idx] == 0) {
                    nonZeros--;
                    if (nonZeros == 0) {break;}
                }
                right++;
            }
            
            // move left to shrink the window (be careful with the condition between left and right below)
            while (right < s.length() && left <= right - t.length()+1) {
                int idx = s.charAt(left)-'A';
                count[idx]++;
                if (count[idx] == 1) {
                    nonZeros++;
                    if (minLen > right - left + 1) {
                        minLen = right - left + 1;
                        startpos = left;
                    }
                    left++;
                    break;
                }
                left++;
            }
            right++;
        }
        
        return startpos == -1 ?  "" : s.substring(startpos, startpos + minLen);
    }
}





// 2024/3/24
// basic way and below optimized way and template way
// first draft (basic way)
class Solution {
    public String minWindow(String s, String t) {
        if(t.length() > s.length()) {return "";}
        // record the min result window (left idx, and length)
        int minLen = s.length()+1;
        int minLeft = -1;
        Map<Character, Integer> count = new HashMap<>();
        for (char c: t.toCharArray()) {
            count.put(c, count.getOrDefault(c,0)+1);
        }
        int left = 0;
        int right = 0;
        int zeroCount = 0;
        while (right < s.length()) {
            while (right < s.length()) {
                char curChar = s.charAt(right);
                if (count.containsKey(curChar)) {
                    count.put(curChar, count.get(curChar)-1);
                    if (count.get(curChar)==0) {zeroCount++;}
                }
                if (zeroCount == count.size()) {
                    if(minLen > right-left+1){
                        minLen = right-left+1;
                        minLeft = left;
                    }
                    break;
                }
                right++;
            }
            
            while(left <= right && zeroCount == count.size()) {
                char leftChar = s.charAt(left);
                left++;
                if (count.containsKey(leftChar)) {
                    count.put(leftChar, count.get(leftChar)+1);
                    if (count.get(leftChar)==1) {zeroCount--;}
                }
                if (zeroCount == count.size() && minLen > right-left+1) {
                    minLen = right-left+1;
                    minLeft = left;
                }
            }
            right++;
        }
        return minLeft != -1? s.substring(minLeft, minLeft+minLen):"";
    }
}
// optimized way
/**
Basic idea is the same as above prev solutions.
A few optimizations based on this self solution:
=== Opt1: can filter s with t first, since we only care those chars and their positions that t contains. (skip in below, cause it's better to company with count map.) Given the time after opt1 is O(s.len+t.len+2*filteredSlen), comparing to before time O(2*s.len+t.len), will improve a lot when filteredSlen <<< s.len
=== Opt2: can change map to int[58] since we only have letters here and A=65, a=97, thus 26+97-65=58. 
And we don't need to worry about count++,-- for chars not in t would impact our accurate nonZero. Cause their count will always be <= 0, since they need to always be added to the window first, which is doing count-- from 0. And when they are moved out of the window, the highest value they can go is just 0.
Also we just need zeroCount. Add to it when get uniq char in t, and remove it
 */

class Solution {
    public String minWindow(String s, String t) {
        if(t.length() > s.length()) {return "";}
        // record the min result window (left idx, and length)
        int minLen = s.length()+1;
        int minLeft = -1;
        int zeroCount = 0;
        // opt 2
        int[] count = new int[58];
        for (char c: t.toCharArray()) {
            count[c-'A']++;
            if (count[c-'A']==1) {zeroCount++;}
        }
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            while (right < s.length()) {
                char curChar = s.charAt(right);
                count[curChar-'A']--;
                if (count[curChar-'A']==0){zeroCount--;}
                if (zeroCount == 0) {
                    if(minLen > right-left+1){
                        minLen = right-left+1;
                        minLeft = left;
                    }
                    break;
                }
                right++;
            }
            
            while(left <= right && zeroCount == 0) {
                char leftChar = s.charAt(left);
                left++;
                count[leftChar-'A']++;
                if (count[leftChar-'A']==1) {zeroCount++;}
                if (zeroCount == 0 && minLen > right-left+1) {
                    minLen = right-left+1;
                    minLeft = left;
                }
            }
            right++;
        }
        return minLeft != -1? s.substring(minLeft, minLeft+minLen):"";
    }
}

// template way
/**
Also a templated way:
    int findSubstring(string s){
        vector<int> map(128,0);
        int counter; // check whether the substring is valid
        int begin=0, end=0; //two pointers, one point to tail and one  head
        int d; //the length of substring

        for() { // initialize the hash map here  }

        while(end<s.size()){

            if(map[s[end++]]-- ?){  // modify counter here }

            while(// counter condition ){ 
                 
                 // update d here if finding minimum

                //increase begin to make it invalid/valid again
                
                if(map[s[begin++]]++ ?){ //modify counter here }
            }  

            // update d here if finding maximum
        }
        return d;
  }*/

class Solution {
    public String minWindow(String s, String t) {
        if(t.length() > s.length()) {return "";}
        // record the min result window (left idx, and length)
        int minLen = s.length()+1;
        int minLeft = -1;
        int zeroCount = 0;
        // opt 2
        int[] count = new int[58];
        for (char c: t.toCharArray()) {
            count[c-'A']++;
            if (count[c-'A']==1) {zeroCount++;}
        }
        int left = 0;
        int right = 0;
        while(right < s.length()) {
            char curChar = s.charAt(right);
            count[curChar-'A']--;
            if (count[curChar-'A']==0){zeroCount--;}
            right++;
            while(zeroCount == 0) {
                if (minLen > right-left) {
                    minLen = right-left;
                    minLeft = left;
                }
                char leftChar = s.charAt(left);
                count[leftChar-'A']++;
                if (count[leftChar-'A']==1){zeroCount++;}
                left++;
            }
        }
        return minLeft == -1?"":s.substring(minLeft, minLeft+minLen);
    }
}







