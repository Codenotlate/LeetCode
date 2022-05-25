/*Thought
M1:first separate by dot to get string arrays for each version. then two pointers to compare, use integer.valueOf() to convert to int and compare. If not equal, return result immediately, otherwise, pointers++. If one pointer has reached the end, set the num to 0 and compare with all other nums in another version.
time O(l1 + l2) space O(l1 + l2)

M2:can also do it inplace, have two pointers for each version as left and right. Until right pointer reach dot or end of the string, do curnum = curnum * 10 + curchar. for each version at each round, compare two curnums. If equal, continue. If one pointer reach the end, val as 0 similarly as above.
time O(l1 + l2)  space O(1)
*/
// M1
class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int i = 0;
        int j = 0;
        while ( i < v1.length || j < v2.length) {
            int num1 = i < v1.length? Integer.valueOf(v1[i]):0;
            int num2 = j < v2.length? Integer.valueOf(v2[j]) : 0;
            if (num1 != num2) {return num1 < num2? -1 : 1;}
            i++;
            j++;
        }
        return 0;
    }
}
// M2
class Solution {
    public int compareVersion(String version1, String version2) {
        int left1 = 0;
        int left2 = 0;
        while (left1 < version1.length() || left2 < version2.length()) {
            // get num1
            int right1 = left1;
            int curnum1 = 0;
            while (right1 < version1.length() && version1.charAt(right1) != '.') {
                curnum1 = curnum1 * 10 + version1.charAt(right1)-'0';
                right1++;
            }
            left1 = right1 + 1;
            // get num2
            int right2 = left2;
            int curnum2 = 0;
            while (right2 < version2.length() && version2.charAt(right2) != '.') {
                curnum2 = curnum2 * 10 + version2.charAt(right2)-'0';
                right2++;
            }
            left2 = right2+1;
            if (curnum1 != curnum2) {return curnum1 < curnum2? -1:1;}
        }
        return 0;
    }
}