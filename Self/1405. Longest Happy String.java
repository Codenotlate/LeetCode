class Solution {
    /* From solution
    solve in a greedy way. For the one with largest count, we will use 2 of it each time, and use the second largest one as the deliminator.(since we can't use 3 in a row, thus 2 is the best choice to have the longest length. Note if lg - 2 < md, meaning next round md will become lg, thus this round we can't attach one md as deliminator since otherwise next round we will have 2md added and makes it 3 in a row)
    we repeat the process by check the count after each round and reassign lg,md,sm
    */
    public String longestDiverseString(int a, int b, int c) {
        return generate(a,b,c, "a","b","c");
    }
    
    private String generate(int lgCount, int mdCount, int smCount, String lgs,String mds, String sms) {
        if (lgCount < mdCount) {return generate(mdCount, lgCount, smCount, mds, lgs, sms);}
        if (mdCount < smCount) {return generate(lgCount, smCount, mdCount, lgs, sms, mds);}
        // base case (lgCount,0,0)
        if (mdCount == 0) {return lgs.repeat(Math.min(lgCount,2));}
        // now we have lg >= md>=sm
        StringBuilder res = new StringBuilder();
        int uselg = Math.min(lgCount,2);
        int usemd = lgCount - uselg >= mdCount? 1 : 0;
        String rest = generate(lgCount-uselg, mdCount-usemd, smCount, lgs,mds,sms);
        while(uselg-->0) {res.append(lgs);}
        while(usemd-- > 0) {res.append(mds);}
        res.append(rest);
        return res.toString();
    }
    
    
    
} 

// solution:
// https://leetcode.com/problems/longest-happy-string/discuss/564277/C%2B%2BJava-a-greater-b-greater-c