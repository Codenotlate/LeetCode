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


//20240809
class Solution {
    public boolean isIsomorphic(String s, String t) {
        Map<Character,Character> smap = new HashMap<>();
        Map<Character, Character> tmap = new HashMap<>();
        for (int i = 0; i < s.length();i++) {
            char s_c = s.charAt(i);
            char t_c = t.charAt(i);
            if(smap.containsKey(s_c) && smap.get(s_c) != t_c || (tmap.containsKey(t_c) && tmap.get(t_c) != s_c)) {return false;}
            smap.put(s_c, t_c);
            tmap.put(t_c, s_c);

        }
        return true;
    }
}
