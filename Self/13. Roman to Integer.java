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




// Review 23/8/16 - need hint for map init and wrongly used string instead of Character
class Solution {
    Map<Character, Integer> map =  new HashMap<>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};
    public int romanToInt(String s) {
        int res = 0;
        int prevAdded = 0;
        for (char c: s.toCharArray()) {
            int cur = map.get(c);
            if (prevAdded != 0 && prevAdded < cur) {
                res -= 2 * prevAdded;
            }
            res += cur;
            prevAdded = cur;
        }
        return res;
    }
}