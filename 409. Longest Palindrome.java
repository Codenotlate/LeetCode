class Solution {
    public int longestPalindrome(String s) {
        // build a map to store char -> count pair
        // use int[character size fixed] instead of map could be even faster
        Map<Character, Integer> map = new HashMap<>();
        for (char c: s.toCharArray()) {
        	map.put(c, map.getOrDefault(c, 0) + 1);
        }
        // iterate the map
        int sum = 0;
        boolean containsOdd = false;
        for (char c: map.keySet()) {
        	int count = map.get(c);
        	sum += count / 2 * 2;
        }
        return sum != s.length()? sum + 1 : sum;
    }
}


class Solution {
	// same idea using only hashSet
    public int longestPalindrome(String s) {
        if(s==null || s.length()==0) return 0;
        HashSet<Character> hs = new HashSet<Character>();
        int count = 0;
        for(int i=0; i<s.length(); i++){
            if(hs.contains(s.charAt(i))){
                hs.remove(s.charAt(i));
                count++;
            }else{
                hs.add(s.charAt(i));
            }
        }
        if(!hs.isEmpty()) return count*2+1;
        return count*2;
    }
}














