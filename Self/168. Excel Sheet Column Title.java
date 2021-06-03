class Solution {
    public String convertToTitle(int columnNumber) {
        StringBuilder res = new StringBuilder();

        while (columnNumber > 0) {
        	columnNumber--;
        	int curDigit = columnNumber % 26;
        	char c = (char) ('A' + curDigit);
        	res.append(c);
        	columnNumber /= 26;
        }

        res.reverse();
        return res.toString();
    }
}