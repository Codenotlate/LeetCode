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



























