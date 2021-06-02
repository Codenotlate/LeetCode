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