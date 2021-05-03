class Solution {
    public boolean isSubsequence(String s, String t) {
        // similar to 524, using 2 pointers
        int si = 0;
        int ti = 0;
        while (si < s.length() && ti < t.length()) {
        	if (s.charAt(si) == t.charAt(ti)) {si++;}
        	ti++;
        }
        return si >= s.length();
    }
}

// for followup
/**
self idea: but this only works when 2^N < KN
generate all the combinations of subsequence of t and store in a set
space would be O(2^t.length())
time would be O(2^t.length() + k) for s1 to sk
*/

/**
better idea using binary search and index list
1) record the index list for each char in t in an index array, each char could have a list of corresponding index
2) for each char in S, if s[i]==t[j] then s[i+1] should match  t[k] where k > j. 
Otherwise, s is not a subsequence of t.
3) to test 2), we use prev to store the index of the last matched char's index. And for the next char in s, we go to the index list of that char(in index array), search that index list to find the first index larger than prev.
If we can find that, meaning we can match this char in s in t with a index(k) larger than prev(j). And we update prev = k. And continue the loop.
If return -1, meaning we can't find a k satisfy 2), then we return false.

Time: O(KMlogN) logN is the worst case for binary search (when t consists of all equal chars.) and M is the average length of s1 to sk.
since most likely, MlogN would be smaller than N, for M < N. Thus this way, we could do better than original solution.

*/
class Solution {
	public boolean isSubsequence(String s, String t) {
	    if (s == null || t == null) return false;
	    
	    Map<Character, List<Integer>> map = new HashMap<>(); //<character, index>
	    
	    //preprocess t
	    for (int i = 0; i < t.length(); i++) {
	        char curr = t.charAt(i);
	        if (!map.containsKey(curr)) {
	            map.put(curr, new ArrayList<Integer>());
	        }
	        map.get(curr).add(i);
	    }
	    
	    int prev = -1;  //index of previous character
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        
	        if (map.get(c) == null)  {
	            return false;
	        } else {
	            List<Integer> list = map.get(c);
	            prev = binarySearch(prev, list, 0, list.size() - 1);
	            if (prev == -1) {
	                return false;
	            }
	        }
	    }
	    
	    return true;
	}

	// binary search return the first element larger than target
	private int binarySearch(int target, List<Integer> list, int start, int end) {
	    while (start < end) {
	        int mid = start + (end - start) / 2;
	        if (list.get(mid) <= target) {
	            start = mid + 1;
	        } else {
	            end = mid;
	        }
	    }
	    int lastValue = list.get(end);
	    return lastValue > target  ? lastValue : -1;
	}
}





//https://leetcode.com/problems/is-subsequence/discuss/87268/Java-code-for-the-follow-up-question
//https://leetcode.com/problems/is-subsequence/discuss/87302/Binary-search-solution-for-follow-up-with-detailed-comments

























