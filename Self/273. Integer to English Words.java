// Phase3 self

// Note: pay attention to all the corner cases: tens, numbers less than 20, also where to put the space and trim() at the end

class Solution {
	private String[] lessThenTwenty = new String[]{"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
	private String[] tens = new String[]{"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
	private String[] thousands = new String[] {"", "Thousand", "Million", "Billion", "Trillion"};

    public String numberToWords(int num) {
        if (num == 0) {return "Zero";}

        int i = 0;
        Stack<String> stack = new Stack<>();
        while (num != 0) {
        	if (num % 1000 != 0) {
        		stack.push(readNum(num % 1000) + thousands[i] + " ");
        	}      	
        	i++;
        	num /= 1000;
        }

        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
        	res.append(stack.pop());
        }
        return res.toString().trim();
    }

    private String readNum(int n) {
    	if (n == 0) {return "";}
    	else if (n < 20) {return lessThenTwenty[n] + " ";}
    	else if (n < 100) {return tens[n / 10] + " " + readNum(n % 10);}
    	else return lessThenTwenty[n / 100] + " Hundred " + readNum(n % 100);
    }
}

// Phase3 another faster way, using StringBuilder to avoid String + String
class Solution {
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    
    
    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        return helper(num);
    }

    private String helper(int n) {
    	StringBuilder sb = new StringBuilder();
    	if (n < 20) {
    		sb.append(LESS_THAN_20[n]);
    	} else if (n < 100) {
    		sb.append(TENS[n / 10]).append(" ").append(helper(n % 10));
    	} else if (n < 1000) {
    		sb.append(LESS_THAN_20[n / 100]).append(" Hundred ").append(helper(n % 100));
    	} else if (n < 1000000) {
    		sb.append(helper(n / 1000)).append(" Thousand ").append(helper(n % 1000));
    	} else if (n < 1000000000) {
    		sb.append(helper(n / 1000000)).append(" Million ").append(helper(n % 1000000));
    	} else {
    		sb.append(helper(n / 1000000000)).append(" Billion ").append(helper(n % 1000000000));
    	}

    	return sb.toString();

    }

}

































