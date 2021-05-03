class Solution {
	// time O(n^2), actually if length of word m is larger,
	// then I think time should be O(max(n^2, m*n))
	// space O(n)
    public int maxProduct(String[] words) {
    	// first pass of words, build the binary representation for each word
    	// store the in a map: word[i]:[bin, size]
    	Map<String, Integer[]> map = new HashMap<>();
    	for (String word: words) {
    		int bin = 0;
    		for (char c: word.toCharArray()) {
    			bin |= 1 << (c - 'a');
    		}
    		map.put(word, new Integer[]{bin, word.length()});
    	}

    	// two loops
    	int maxLen = 0;
        for (int i = 0; i < words.length; i++) {
        	for (int j = i + 1; j < words.length; j++) {
        		if ((map.get(words[i])[0] & map.get(words[j])[0]) == 0) {
        			maxLen = Math.max(maxLen, map.get(words[i])[1] * map.get(words[j])[1]);
        		}
        	}
        }
        return maxLen;
    }
}


// use int[] instead of map, could be quicker
class Solution {
	// time O(n^2), actually if length of word m is larger,
	// then I think time should be O(max(n^2, m*n))
	// space O(n)
    public int maxProduct(String[] words) {
    	// first pass of words, build the binary representation for each word
    	// store the in a map: word[i]:[bin, size]
    	int[] bins = new int[words.length];
    	for (int i = 0; i < words.length; i++) {
    		for (char c: words[i].toCharArray()) {
    			bins[i] |= 1 << (c - 'a');
    		}
    	}

    	// two loops
    	int maxLen = 0;
        for (int i = 0; i < words.length; i++) {
        	for (int j = i + 1; j < words.length; j++) {
        		if ((bins[i] & bins[j]) == 0) {
        			maxLen = Math.max(maxLen, words[i].length() * words[j].length());
        		}
        	}
        }
        return maxLen;
    }
}