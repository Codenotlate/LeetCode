/* Thoughts - 23/1/23
using char[] as a stack, go from left to right, track the index of unhandled positions.
If current pos is L: if no R in the left, replace all . between unhandled position and current position with L; if unhandled position is R, replace accordingly (middle of the window be .)
If current pos is R: if has R in the left, replace all between positions to R. Update lastIdx to this R.
time O(n)
space O(n)

not the ideal one, but solutions also similar,check the solution next time


 */
class Solution {
    public String pushDominoes(String dominoes) {
        char[] res = dominoes.toCharArray();
        int lastIdx = 0;
        for (int i = 0; i < dominoes.length(); i++) {
            char c = dominoes.charAt(i);
            if (c == 'R') {
                if (dominoes.charAt(lastIdx) == 'R') {
                    for (int k = lastIdx; k < i; k++) {res[k] = 'R';}
                }
                lastIdx = i;
            } else if (c == 'L') {
                if (res[lastIdx] == 'R') {
                    int mid = lastIdx + (i-lastIdx) / 2;
                    for (int k = lastIdx + 1; k <= mid; k++) {res[k] = 'R';}
                    for (int k = mid+1; k < i; k++) {res[k] = 'L';}
                    if ((i - lastIdx)%2 == 0) {res[mid] = '.';}
                } else {
                    for (int k = lastIdx; k < i; k++) {res[k] = 'L';}
                }
                lastIdx = i + 1;
            }
        }
        if (lastIdx < dominoes.length() && dominoes.charAt(lastIdx) == 'R') {
            for (int k = lastIdx + 1; k < dominoes.length(); k++) {res[k] = 'R';}
        }
        return new String(res);
    }
}