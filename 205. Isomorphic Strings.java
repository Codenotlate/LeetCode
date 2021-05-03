public class Solution {
    public boolean isIsomorphic(String s, String t) {
        if(s == null || s.length() <= 1) return true;
        HashMap<Character, Character> map = new HashMap<Character, Character>();
        for(int i = 0 ; i< s.length(); i++){
            char a = s.charAt(i);
            char b = t.charAt(i);
            if(map.containsKey(a)){
                if(!map.get(a).equals(b)) {return false;}
            }else{
                if(map.containsValue(b)) {return false;}
                map.put(a,b);                
            }
        }
        return true;
        
    }
}


public class Solution {
	// use two int[256] array to make sure char at t and char at s are mapped to same number
    public boolean isIsomorphic(String s, String t) {
        if(s == null || s.length() <= 1) return true;
        int[] m1 = new int[256];
        int[] m2 = new int[256];
        Arrays.fill(m1, -1);
        Arrays.fill(m2, -1);
        for(int i = 0 ; i< s.length(); i++){
            int a = s.charAt(i);
            int b = t.charAt(i);
            if (m1[a] != m2[b]) {return false;}
            m1[a] = i;
            m2[b] = i;
        }
        return true;
        
    }
}
