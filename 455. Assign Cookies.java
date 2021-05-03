// greedy idea
// prove local optimal is also global optimal
// local optimal: satisfy child with small g[i] first
// and only satisfy them with min s[j]

class Solution {
    public int findContentChildren(int[] g, int[] s) {
        // sort first
        Arrays.sort(g);
        Arrays.sort(s);

        int si = 0;
        int gi = 0;

        while (si < s.length && gi < g.length) {
        	if (s[si] >= g[gi]) {gi++;}
        	si++;
        }
        return gi;
    }
}