// Phase 3 self
// M1: sort every str in strs, and use that sorted string as the key of each group
// time O(nklogk) where n is the # of str in strs, k is the max len of str in strs
// space O(nk)

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s: strs) {
        	char[] temp = s.toCharArray();
        	Arrays.sort(temp);
        	String sortedCur = new String(temp);
        	map.putIfAbsent(sortedCur, new ArrayList<>());
        	map.get(sortedCur).add(s);
        }

        // List<List<String>> res = new LinkedList<>();
        // for (List<String> l: map.valueSet()) {
        // 	res.add(l);
        // }

        // return res;

        // can be directly as below
        return new ArrayList(map.values());
    }
}



// Phase3 solution
// M2: a faster way, is to build a string representing the count of the letters in each str, in contrast to sort, this takes O(k) time.
// time O(nk) space O(nk)
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s: strs) {
        	String countCode = getCountCode(s);
        	map.putIfAbsent(countCode, new ArrayList<>());
        	map.get(countCode).add(s);
        }
        return new ArrayList(map.values());
    }

    private String getCountCode(String s) {
    	int[] count = new int[26];
    	for (char c: s.toCharArray()) {
    		count[c - 'a']++;
    	}

    	StringBuilder sb = new StringBuilder();
    	for (int n: count) {
    		sb.append('#');
    		sb.append(n);
    	}


    	return sb.toString();
    }
}



// phase3 self review
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str: strs) {
            String countRes = countStr(str);
            map.putIfAbsent(countRes, new ArrayList<String>());
            map.get(countRes).add(str);           
        }
        
        return new ArrayList(map.values());
    }
    
    // scan each str in the array to get its string count representation
    private String countStr(String s) {
        StringBuilder res = new StringBuilder();
        int[] count = new int[26];
        for (char c: s.toCharArray()) {
            count[c - 'a']++;
        }
        
        for (int i = 0; i < 26; i++) {
            if (count[i] != 0) {
                res.append(count[i]).append('a' + i);
            }
        }
        
        return res.toString();
    }
}



























