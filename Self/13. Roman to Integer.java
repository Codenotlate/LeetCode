// Phase3 self
// time O(1) since limited roman numbers; space O(1)

class Solution {
	static Map<Character, Integer> romanMap = new HashMap<>();
	static {
		romanMap.put('I', 1);
		romanMap.put('V', 5);
		romanMap.put('X', 10);
		romanMap.put('L', 50);
		romanMap.put('C', 100);
		romanMap.put('D', 500);
		romanMap.put('M', 1000);
	}


    public int romanToInt(String s) {
    	int res = 0;
        for (int i = 0; i < s.length() - 1; i++) {
        	if(romanMap.get(s.charAt(i)) < romanMap.get(s.charAt(i + 1))) {
        		res -= romanMap.get(s.charAt(i));
        	} else {
        		res += romanMap.get(s.charAt(i));
        	}
        }
        return res + romanMap.get(s.charAt(s.length() - 1));
    }
}