class Solution {
	// use 2 pointers, Time O(n), extra space O(1)
    private final static HashSet<Character> vowels = new HashSet<>(
        Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

    public String reverseVowels(String s) {
        int start = 0;
        int end = s.length() - 1;
        char[] ori = s.toCharArray();
        char[] res = new char[s.length()];

        while (start <= end) {
        	// we can also do if instead of while
        	while ((start < end) && (!vowels.contains(ori[start]))) {
        		res[start] = ori[start];
        		start += 1;
        	}
        	while ((start < end) && (!vowels.contains(ori[end]))) {
        		res[end] = ori[end];
        		end -= 1;
        	}
        	
    		res[start] = ori[end];
        	res[end] = ori[start];
        	start += 1;
        	end -= 1;
             	
        }

        return new String(res);
    }
}