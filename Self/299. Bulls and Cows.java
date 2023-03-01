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



//Review self: idea close to above M1 with slight difference
class Solution {
    /* initial thought
    first pass: go through two strings to find bull number, and also count other char count in secret string using a count[10] array. Use a visited[len] array to label position counted as bull.
    second pass: go through guess string, skip visited bull positions. Check if the number is in the count[] arr and count > 0. If yes increase the cows number.
    time O(n) space O(1)
    
    */
    public String getHint(String secret, String guess) {
        int[] count = new int[10];
        int[] visited = new int[secret.length()];
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {bulls++; visited[i] = 1;}
            else {count[secret.charAt(i) - '0']++;}
        }
        
        for (int i = 0; i < guess.length(); i++) {
            if (visited[i] != 0) {continue;}
            if (count[guess.charAt(i)-'0'] > 0) {
                count[guess.charAt(i) - '0']--;
                cows++;
            }
        }
        
        StringBuilder res = new StringBuilder();
        res.append(bulls).append("A").append(cows).append("B");
        return res.toString();
    }
}

// similar to above M2
class Solution {
    // Try one pass way, only use one count[10] array, secret and guess do ++ or -- in the same step. if s[i] = g[i], bulls++; Otherwise: if count[s[i]] < 0, then count[s[i]]++, cows++; if count[g[i]] > 0, then count[g[i]]--; cows++
    // time O(n) space O(1)
    public String getHint(String secret, String guess) {
        int[] count = new int[10];
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < secret.length(); i++) {
            char s = secret.charAt(i);
            char g = guess.charAt(i);
            if (s == g) {bulls++;}
            else {
                count[s-'0']++;
                if (count[s-'0'] <= 0) {cows++;}
                count[g-'0']--;
                if (count[g-'0'] >= 0) {cows++;}
            }
        }
        return bulls + "A" + cows + "B";
    }
}




// Review
/*Initial thought
M1 three pass way:
Since only consists of digits, we can use an array with size 10 as the counting array for secret string to count each digit. [Wrong!!!:Then we traverse guess String, if current digit is the same as the corresponding digit in secret and count[digit] > 0, then bull++, and count[digit]--.Otherwise if count[digit] > 0, then cows++, count[digit]--.!!! Wrong]
bug example: "1122"& "1222" -> 3A0B
Since bulls have higher priority than cows, we need two passes. First pass count for bulls and adjust count array number, second pass for cows, only add cows when we have cur digit count > 0 and cur digit is not a bull.

M2 one pass way:
One pass way:
traverse secret and guess at the same time. Again using a size 10 count array, if corresponding pos char the same, then bulls++. we do this first to guarantee bulls have higher priority than cows. Otherwise, for secret we do count++, and for guess we do count--. Thus for char from secret, if count[digit] <=0 after count++, meaning there's this digit exists in guess, we do cows++. And vice versa.

time O(len) space O(1)
*/
// M1 way
class Solution {
    public String getHint(String secret, String guess) {
        int[] count = new int[10];
        for (char c: secret.toCharArray()) {
            count[c-'0']++;
        }
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length();i++) {
            if(guess.charAt(i) == secret.charAt(i)) {
                bulls++;
                count[guess.charAt(i)-'0']--;
            }            
        }
        
        for (int i = 0; i < guess.length();i++) {
            char c = guess.charAt(i);
            if (count[c-'0'] > 0 && c != secret.charAt(i)) {cows++; count[c-'0']--;}
        }
        return bulls + "A" + cows + "B";
    }
}
// M2 way
class Solution {
    public String getHint(String secret, String guess) {
        int[] count = new int[10];
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < guess.length();i++) {
            int g = guess.charAt(i) - '0';
            int s = secret.charAt(i) -'0';
            if(g == s) {
                bulls++;
            } else {
                count[s]++;
                if(count[s] <= 0) {cows++;}
                count[g]--;
                if (count[g] >= 0) {cows++;}
            }           
        }

        return bulls + "A" + cows + "B";
    }
}





// Review 23/3/1 - first time with bug, second time two pass way. Didn't think about there's one pass way. Hope next time can come up with one-pass way self.
/* Thoughts
Go through secret string, get the count number for each digit in it. Then go through guess string, if current digit is the same as the current position digit in secret, A++, and reduce the number in the count array (>0). Else if current digit is contained in the count array, B++, reduce the number in the count array.
!! Bug in above example("1122" and "1222"): if we do it in one pass, numB will consume the potential numA later. Two pass of guess can solve this bug. Or move the calculation for numA into the pass of secret.
time O(l1+l2) space O(10) = O(1)
 */
class Solution {
    public String getHint(String secret, String guess) {
        int[] count = new int[10];
        int numA = 0;
        int numB = 0;
        for (int i = 0; i < guess.length(); i++) {
            if(guess.charAt(i) == secret.charAt(i)) {
                numA++;
            } else {
                count[secret.charAt(i)-'0']++;
            }           
        }
        for (int i = 0; i < guess.length(); i++) {
            if(guess.charAt(i) == secret.charAt(i)) {continue;}
            if (count[guess.charAt(i)-'0']>0) {
                numB++;
                count[guess.charAt(i)-'0']--;
            }
        }

        StringBuilder res = new StringBuilder();
        res.append(numA).append('A').append(numB).append('B');
        return res.toString();
    }
}








