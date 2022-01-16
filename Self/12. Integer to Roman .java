class Solution {
	static List<Pair<Integer, String>> romanList = new ArrayList<>();
	static {
		romanList.add(new Pair(1000, "M"));
		romanList.add(new Pair(900, "CM"));
		romanList.add(new Pair(500, "D"));
		romanList.add(new Pair(400, "CD"));
		romanList.add(new Pair(100, "C"));
		romanList.add(new Pair(90, "XC"));
		romanList.add(new Pair(50, "L"));
		romanList.add(new Pair(40, "XL"));
		romanList.add(new Pair(10, "X"));
		romanList.add(new Pair(9, "IX"));
		romanList.add(new Pair(5, "V"));
		romanList.add(new Pair(4, "IV"));
		romanList.add(new Pair(1, "I"));
		
	}


    public String intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        while (num != 0) {
        	while (i < romanList.size() && num / romanList.get(i).getKey() == 0) {i++;}
        	int count = num / romanList.get(i).getKey();
        	while (count-- > 0) {res.append(romanList.get(i).getValue());}
        	num %= romanList.get(i).getKey();
        }
        return res.toString();
    }
}




// self review
class Solution {
    static List<Pair<Integer, String>> romanList = new ArrayList<>();
	static {
		romanList.add(new Pair(1000, "M"));
		romanList.add(new Pair(900, "CM"));
		romanList.add(new Pair(500, "D"));
		romanList.add(new Pair(400, "CD"));
		romanList.add(new Pair(100, "C"));
		romanList.add(new Pair(90, "XC"));
		romanList.add(new Pair(50, "L"));
		romanList.add(new Pair(40, "XL"));
		romanList.add(new Pair(10, "X"));
		romanList.add(new Pair(9, "IX"));
		romanList.add(new Pair(5, "V"));
		romanList.add(new Pair(4, "IV"));
		romanList.add(new Pair(1, "I"));
		
	}
    
    
    public String intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        
        for (Pair<Integer, String> p: romanList) {
            if (num == 0) {break;}
            int val = p.getKey();
            String romanStr = p.getValue();
            
            int c = num / val;
            if (c == 0) {continue;}
            
            while (c-- > 0) {
                res.append(romanStr);
            }
            num %= val;
            
        }
        
        return res.toString();
    }
}


// Phase3 self
class Solution {
    private static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};    
    private static final String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

    public String intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i< values.length; i++) {
            int count = num / values[i];
            while (count-- > 0) {res.append(symbols[i]);}
            num %= values[i];
        }
        
        return res.toString();      
    }
}


