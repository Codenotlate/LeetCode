class Solution {
    /* Initial thought
    each num string covert to a * 10^k. Two for loop for each digit in each num, [then sum up all result to get final result and convert to string].
    [...] inside is wrong, because we are constrained to not use any bigInteger library, but the multiplied result could breach the int value range. Thus we need to use the carry method we use when add two numbers.
    (100 + 20 + 3) * (400 + 50 + 6)
    4 0000, 5 000, 6 00 -> [0,4,5,6,0,0]
    8 000, 10 00, 12 0 -> [0,5,3,6,0,0] -> [0,5,4,6,0,0] -> [0,5,4,7,2,0]
    12 00, 15 0, 18 -> [0,5,5,9,2,0] -> [0,5,6,0,7,0] ->[0,5,6,0,8,8]

    Thus the overall idea is: init an array to store intermediate result(size: l1 + l2).for ith digit in num1, jth digit in num2, store num1[i] * num2[j] in array[i+j+1]. accumulate sum to array along the loop in the corresponding position.
    In the end, it's the same as how to deal with carry in the add two numbers problem. Thus iterate the array from end to start, move carry at each position i to position i-1.
    Convert final array into string, note need to remove 0 ahead.
    
    time O(l1 * l2) space O(l1 + l2)
    */

    // this way is same as solution3
    public String multiply(String num1, String num2) {
        int l1 = num1.length();
        int l2 = num2.length();
        if (num1.equals("0") || num2.equals("0")) {return "0";}
        int[] middleres = new int[l1 + l2];
        for (int i = 0; i < l1; i++) {
            for (int j = 0; j < l2; j++) {
                middleres[i + j + 1] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            }
        }
        
        int carry = 0;
        for (int k = l1 + l2 -1; k >= 0; k--) {
            int num = middleres[k] + carry;
            middleres[k] = num % 10;
            carry = num / 10;
        }
        
        StringBuilder res = new StringBuilder();
        for (int k = 0; k < l1 + l2; k++) {
            if (k == 0 && middleres[k] == 0) {continue;}
            res.append(middleres[k]);
        }
        return res.toString();
    }
}









// Review - same idea above, used list instead of an array
/*Thought
Can be separated to digit multiply. For each digit in num2, multiply it to every digit in num1. res2 (for each digit in num2) = res2 * 10 + num2 digit * num1[i]; Then for the total res, res = res * 10 + res2 of num2[j].
The key is how to deal with large number, maybe use an Arraylist.
time O(l1+l2) space O(# of digits of res)
123
456

4 8 12
  5 10 15
    6  12  18    ->  4,13,28,27,18 -> (backwards)->     5,6,0,8,8 -> convert to String
*/
class Solution {
    public String multiply(String num1, String num2) {
        // don't forget this line
        if (num1.equals("0") || num2.equals("0")) {return "0";}
        List<Integer> reslist = new ArrayList<>();
        for (int j = 0; j < num2.length(); j++) {
            for (int i = 0; i < num1.length(); i++) {
                if (reslist.size()<= j + i) {reslist.add(0);}
                int temp = (num1.charAt(i)-'0') * (num2.charAt(j) - '0');
                reslist.set(i+j, reslist.get(i+j) + temp);
            }
        }
        // move backwards
        int carry = 0;
        for(int k = reslist.size() - 1; k >= 0; k--) {
            int num = (reslist.get(k) + carry) % 10;
            carry = (reslist.get(k) + carry) / 10;
            reslist.set(k, num);
        }
        
        StringBuilder res = new StringBuilder();
        // don't forget this line
        if (carry != 0) {res.append(carry);}
        for (int n: reslist) {res.append(n);}
        return res.toString();
    }
}