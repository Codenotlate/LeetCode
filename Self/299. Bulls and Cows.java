// Phase3 self
// basic idea is to count the null# along the first loop and also build the hashmap for each string
// then take the second loop to see how many number in common the two strings have

class Solution {
    public String getHint(String secret, String guess) {
        int aNum = 0;
        int totalNum = 0;
        Map<Character, Integer> sMap = new HashMap<>();
        Map<Character, Integer> gMap = new HashMap<>();

        for (int i = 0; i < secret.length(); i++) {
        	char s = secret.charAt(i);
        	char g = guess.charAt(i);
        	if (s == g) {
        		aNum++;
        	}
        	sMap.put(s, sMap.getOrDefault(s, 0) + 1);
        	gMap.put(g, gMap.getOrDefault(g, 0) + 1);
        }

        for (char c: sMap.keySet()) {
        	totalNum += Math.min(gMap.getOrDefault(c, 0), sMap.get(c));
        }

        int bNum = totalNum - aNum;
        return aNum + "A" + bNum + "B";
    }
}


/* Improvement: only one pass
1. since it's only digit 0 to 9, the hashmap can be converted as a int[10] array
2. We can use 1 array for both strings, str1++, str2--
3. we can count the cow # along the first pass: for str1, if num in array < 0, meaning str2 has that, then cow++
	same idea for str2 when array > 0
*/
class Solution {
    public String getHint(String secret, String guess) {
        // use count to record the count of each digits in two strings, replace the hashmap in M1
        int[] count = new int[10];
        int aNum = 0;
        int bNum = 0;

        for (int i = 0; i < secret.length(); i++) {
        	int s = secret.charAt(i) - '0';
        	int g = guess.charAt(i) - '0';
        	if (s == g) {
        		aNum++;
        	} else {
                // Pay attention to this part, can't use equal to 0 as condition, that won't work for 1122 & 2211
        		if (count[s] < 0) {bNum++;}
        		if (count[g] > 0) {bNum++;}
        		count[s]++;
        		count[g]--;
        	}
        }

        return aNum + "A" + bNum + "B";

    }
}



















