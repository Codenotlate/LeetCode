// Phase 3 self
/*
problem itself is simple, follow up implementing a Trie is interesting
*/
class Solution {
    public String longestCommonPrefix(String[] strs) {
    	StringBuilder res = new StringBuilder();
        int i = 0;
        while (i < strs[0].length()) {
        	char cur = strs[0].charAt(i);
        	for (String s: strs) {
        		if (i == s.length() || s.charAt(i) != cur) {
        			return res.toString();
        		}
        	}
        	res.append(cur);
        	i++;
        }

        return res.toString();
    }
}



/* follow up: using Trie to make each search O(len of q), the build of the Trie take O(m) time, where m is # of all chars in S.
Given a set of keys S = [S1, S2, S3, ..., Sn], find the longest common prefix among a string q and S. This LCP query will be called frequently.

*/


class Solution {

	class Trie {
		private class TrieNode {
			TrieNode[] links;
			int final linkSize = 26;
			int childSize;
			boolean isEnd;

			public TrieNode() {
				links = new TrieNode[linkSize];
				isEnd = false;
				childSize = 0;
			}
			
		}

		private TrieNode root;

		public Trie() {
			root = new TrieNode();
		}


		public void insert(String word) {
			TrieNode cur = root;

			for (char c: word.toCharArray()) {
				int idx = c - 'a';
				if (cur.links[idx] == null) {
					cur.links[idx] = new TrieNode();
					cur.childSize++;
				}
				cur = cur.links[idx];
			}
			cur.isEnd = true;
		}

		public String searchLCP(String q) {
			TrieNode cur = root;
			StringBuilder res = new StringBuilder();

			for (char c: q.toCharArray()) {
				int idx = c - 'a';
				if (cur.links[idx] == null || cur.childSize != 1 || cur.isEnd) {break;}
				res.append(c);
				cur = cur.links[idx];
			}
			return res.toString();
		}
	}

	// above is the Trie implementation

    public String longestCommonPrefix(String[] strs, String q) {
        if (strs == null || strs.length == 0) {return "";}
        Trie trie = new Trie();

        for (String s: strs) {
        	trie.insert(s);
        }

        return trie.searchLCP(q);
    }
}






// 23/8/20 - Basic way is easy, Trie way needs review
/*
Can compare vertically O(n*min(m)) or horizontally O(n*average(m)).

*/
// M1: horizontal way
class Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res = new StringBuilder(strs[0]);
        for (int i = 1; i < strs.length; i++) {
            int k = 0;
            while (k < res.length() && k < strs[i].length()) {
                if (res.charAt(k) != strs[i].charAt(k)) {break;}
                k++;
            }
            res.delete(k, res.length());
        }
        return res.toString();
    }
}
// M2: vertical way
class Solution {
    public String longestCommonPrefix(String[] strs) {
        StringBuilder res = new StringBuilder();
        int k = 0;
        boolean isDone = false;
        while (true) {
            if (isDone || k >= strs[0].length()) {break;}
            char stdChar = strs[0].charAt(k);
            for (int i = 1; i < strs.length; i++) {
                if (k >= strs[i].length() || stdChar != strs[i].charAt(k)) {
                    isDone = true; 
                    break;
                }                
            }
            if (!isDone) {res.append(stdChar);}
            k++;
        }
        
        return res.toString();
    }
}



















