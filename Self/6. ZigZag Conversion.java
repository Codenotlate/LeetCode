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




// self review
class Solution {
    public String convert(String s, int numRows) {
        // pay special attention to this special case!!
        if (numRows == 1) {return s;}
        int gap = 2 * (numRows - 1);
        StringBuilder res = new StringBuilder();
        
        for (int i = numRows - 1; i>= 0; i--) {
            int r = numRows - 1;
            while (r - i < s.length()) {
                res.append(s.charAt(r - i));
                if (r + i < s.length() && i != 0 && i != numRows - 1) {
                    res.append(s.charAt(r + i));
                }
                r += gap;
            }
        }
        
        return res.toString();
    }
}

// Phase3 self
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) {return s;}
        int startIdx = 0;
        int gap = 2 * numRows - 2;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            startIdx = 0;
            while (startIdx < s.length()) {
                if (startIdx + i < s.length()) {
                    res.append(s.charAt(startIdx + i));
                }
                if (i > 0 && i < numRows - 1 && startIdx + gap -i < s.length()) {
                    res.append(s.charAt(startIdx + gap - i));
                }

                startIdx += gap;
            }
        }
        return res.toString();
    }
}




// Review
/*Initial thought
PAYPALISHIRING num = 3
01210121012101

PAYPALISHIRING num = 4
01232101232101
Need to find the pattern for the new rownum for each original index in s.
choose the pivot, we choose 0 over num-1 because the end part may not contain num-1. Each pivot responsible for the followed range with length =  2* (num - 1), For each pivot 0, index1 will be appear at p0 + 1 and p0 + length - 1, index2 will appear at p0 +2 and p0 + length - 2,..., index(n-2) will appear at p0 + n - 2 and p0 + length - (n-2). Then index (n-1) will appear at p0 + n - 1.

time O(n) space O(n)
*/
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) {return s;}
        StringBuilder res = new StringBuilder();
        Map<Integer, List<Integer>> map = new HashMap<>();
        int p0 = 0;
        int size = 2 *(numRows - 1);
        while (p0 < s.length()) {
            map.putIfAbsent(0, new LinkedList<>());
            map.get(0).add(p0);
            for (int i = 1; i <= numRows - 1; i++) {
                map.putIfAbsent(i, new LinkedList<>());
                if (p0 + i < s.length()) {map.get(i).add(p0 + i);}
                if (i !=  numRows - 1 && p0 + size - i < s.length()) {map.get(i).add(p0 + size - i);}
            }
            p0 += size;
        }
        for (int i = 0; i < numRows; i++) {
            for (int pos: map.get(i)) {
                res.append(s.charAt(pos));
            }            
        }
        return res.toString();
    }
}

// Actually is we reverse the loop order, i.e. with numRows as outer loop and j + size as inner loop, we don't need the map, can directly append the result to stringbuilder. As below
class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) {return s;}
        StringBuilder res = new StringBuilder();
        int size = 2 *(numRows - 1);
        for (int i = 0; i < numRows; i++) {
            int p0 = 0;
            while(p0 + i < s.length()) {
                res.append(s.charAt(p0 + i));
                if (i != 0 && i !=  numRows - 1 && p0 + size - i < s.length()) {res.append(s.charAt(p0 + size - i));}
                p0 += size;
            }
            
        }
        
        return res.toString();
    }
}














