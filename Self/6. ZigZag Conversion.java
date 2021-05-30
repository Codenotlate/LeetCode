// Phase3 self
// the level goes from 0 to numRows - 1 then back to 0 again, using this pattern, go through s, and put char into corresponding list group (representing each row in the zigzag).

class Solution {
    public String convert(String s, int numRows) {
    	// remember to deal with numRows == 1 special case
    	if (numRows == 1) {return s;}


        List<Character>[] charsRow = new List[numRows];
        for (int i = 0; i < numRows; i++) {
        	charsRow[i] = new ArrayList<>();
        }
        int level = 0;
        boolean isIncrease = true;

        for (char c: s.toCharArray()) {
        	charsRow[level].add(c);
        	if (isIncrease) {level++;}
        	else {level--;}

        	if (level >= numRows) {
        		level -= 2;
        		isIncrease = false;
        	} else if (level < 0) {
        		level += 2;
        		isIncrease = true;
        	}
        }

        StringBuilder res = new StringBuilder();
        for (List<Character> l: charsRow) {
        	for (char c: l) {
        		res.append(c);
        	}
        }
        return res.toString();
    }
}


// phase3 solution M2: visit in zigzag order
// time O(n) space O(1) if string and stringBuilder is not counted as extra space
/*
r0 chars index are k * (2 * (numRow - 1))
ri chars index are r0_index +/- i
	when i = lastRow (i.e. rowNum - 1), index are r0_index + i only (no "-")
*/

class Solution {
    public String convert(String s, int numRows) {
    	if (numRows == 1) {return s;}

    	int n = s.length();
    	int cycleLen = 2 * numRows - 2;
    	StringBuilder res = new StringBuilder();

        for (int i = 0; i < numRows; i++) {
        	for (int j = 0; j + i < n; j += cycleLen) {
        		res.append(s.charAt(i + j));
        		if (i != 0 && i != numRows - 1 && j + cycleLen - i < n) {
        			res.append(s.charAt(j + cycleLen - i));
        		}
        	}
        }

        return res.toString();
    }
}



















