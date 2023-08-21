// 23/8/21 - self M1 way, other ways from solution
/*
M1: basic sliding window way. Time O(l1*l2) space O(1)
 */
class Solution {
    public int strStr(String haystack, String needle) {
        int s1 = 0;
        int s2 = 0;
        while(s1 < haystack.length() && s2 < needle.length()) {
            int cur = s1;
            while(cur < haystack.length() && s2 < needle.length() && haystack.charAt(cur) == needle.charAt(s2)) {
                cur++;
                s2++;
            }
            if (s2 >= needle.length()) {return s1;}
            s1++;
            s2 = 0;
        }
        return -1;

    }
}
/*
M2: Rabin-Karp Algorithm (Single Hash)
Let's define our hash function as sum of mapped values of all the characters in the string. The mapped value of a is 1, the mapped value of b is 2, and so on.
Using this abb will be mapped to 1 + 2 + 2 = 5. If two strings are equal, then their hash values will be equal. However, using this hashing approach, the reverse will not necessarily be True. If two hash values are equal, we can't say that the strings are also equal.

[-Naive Idea-]What we can do is check character-by-character only if the hash value of the m-substring of haystack is equal to the Hash Value of needle. 
[--Limitations--]: 
[1] As illustrated in the above example, we have checked character-by-character only 5 times. Out of which 4 times, we had a spurious hit. This is better than checking character-by-character for every m-substring (which are 9 in this example). Still, spurious hits are one of the limitations.
[2]Another commonly encountered limitation is that if we calculate the hash value of every m-substring of haystack, then it is equivalent to iterating each character of the m-substring, producing the same time complexity as the brute force approach.

[-Improved Idea: rolling hash-]
[1]To overcome spurious hits, we can assign position weight to each index of the string. For example, the last character of the string is assigned a weight of 111, the second last character of the string is assigned a weight of 10, the third last character of the string is assigned a weight of 10^2 and so on.
However, the approach is still not fool-proof. aal will also be mapped to 100+10+12=122. Thus, we still can have spurious hits. The reason being our chosen position weight 101010 is not enough to overcome spurious hits.
Mathematically, it turns out that to have a unique hash value for every m-substring, positional weight should be greater than or equal to the number of characters in the set, which is 262626 in our case. Any number (preferably, a prime number) no less than 262626 is a workable base. To have a lower hash value, we can map a to 000, b to 111, c to 222, and so on.
Thus we can use 26 instead of 10 as the weight base for each position. The formula works fine, but the hash value may easily overflow. To prevent overflow, we can use modular arithmetic. There may be spurious hits after MOD, but this can be minimized by using a sufficiently large prime number.
[2] It turns out that we never explicitly need to compute the hash value of every m-substring. We only need to compute the hash value of the first m-substring of haystack and needle.


[-Time Complexity-]
Time complexity: O(nm)

In the worst case, hashNeedle might match with the hash value of all haystack substrings. Hence, we then have to iterate character by character in each window. There are n−m+1 such windows of length mmm. Hence, the time complexity is O(nm)

But in the best case, if no hash value of the haystack substring matches with hashNeedle, then we don't have to iterate character by character in each window. In that case, it will be O(n+m). Computing the hash value of haystack and needle will be O(m) and for traversing all windows, we will have O(n−m) time complexity. And during traversal, we are doing constant number of operations, hence, in that case, it will be O(n−m+2m)=O(n+m)
*/
// M2 from solution
class Solution {
    public int hashValue(String string, int RADIX, int MOD, int m) {
        int ans = 0;
        long factor = 1;
        for (int i = m - 1; i >= 0; i--) {
            ans += ((int) (string.charAt(i) - 'a') * factor) % MOD;
            factor = (factor * RADIX) % MOD;
        }
        return ans % MOD;
    }

    public int strStr(String haystack, String needle) {
        int m = needle.length();
        int n = haystack.length();
        if (n < m)
            return -1;

        // CONSTANTS
        int RADIX = 26;
        int MOD = 1000000033;
        long MAX_WEIGHT = 1;

        for (int i = 0; i < m; i++)
            MAX_WEIGHT = (MAX_WEIGHT * RADIX) % MOD;

        // Compute hash of needle
        long hashNeedle = hashValue(needle, RADIX, MOD, m), hashHay = 0;

        // Check for each m-substring of haystack, starting at index windowStart
        for (int windowStart = 0; windowStart <= n - m; windowStart++) {
            if (windowStart == 0) {
                // Compute hash of the First Substring
                hashHay = hashValue(haystack, RADIX, MOD, m);
            } else {
                // Update Hash using Previous Hash Value in O(1)
                hashHay = ((hashHay * RADIX) % MOD - ((int) (haystack.charAt(windowStart - 1) - 'a') * MAX_WEIGHT) % MOD
                        + (int) (haystack.charAt(windowStart + m - 1) - 'a') + MOD) % MOD;
            }
            // If the hash matches, Check Character by Character. 
            // Because of Mod, spurious hits can be there.
            if (hashNeedle == hashHay) {
                for (int i = 0; i < m; i++) {
                    if (needle.charAt(i) != haystack.charAt(i + windowStart)) {
                        break;
                    }
                    if (i == m - 1) {
                        return windowStart;
                    }
                }
            }
        }

        return -1;
    }
}

/*
M3: Rabin-Karp algorithm (Double Hash) - further minimize the CHANCE of spurious hits
Using a different approach for computing hash value, we may avoid spurious hits. But which approach to use? It turns out that instead of a single hash value, if we compute two (or more) hash values, we can significantly reduce the CHANCE of spurious hits. 
We can produce different hash values by changing
MOD value
RADIX value
MAPPING value
WEIGHTAGE associated with characters of the string.
Mathematically, we can prove that by using different MOD and RADIX, we can reduce the CHANCE (i. e. probability) of spurious hits to 10^−10.
Thus M3 idea is: 
* Compute the hash value pair of the current window, if it's the first window, then compute using the hash_pair() function, otherwise use the O(1) formula. Make sure to use the property of modular arithmetic to prevent overflow.
* If the hash value pair of the current window matches with the hash value pair of needle, then return window_start. The probability of a spurious hit tends to zero.

[-Time Complexity-]
Time complexity: O(n).

For computing hash pairs of needle, we have to do O(m) work.

For checking for a match, we have to iterate over n−m+1n-m+1n−m+1 times. Out of these n−m+1n-m+1n−m+1 operations, we have to do O(1)O(1)O(1) work for n−mn-mn−m times, and O(m)O(m)O(m) work for 111 time.

Hence, total time complexity is O(m+(n−m)⋅1+(1)⋅m), which is O(n+m)

Moreover, we are proceeding only when n≥m, thus final time complexity is O(n) only. In this case, O(m+n) has an upper bound of O(2⋅n), that's why we can ignore the m term. When n<m we are simply returning -1. Thus, only n is dominating in Time Complexity, and not m.
*/











