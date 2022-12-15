/*Thought
M1: define the comparator of sorting explicitly or implicitly. Though one thing to worry is whether the array.sort() is stable, i.e. whether it can maintain the relative order for digit type. Turns out it can be stable.
One optimization is we should use split(" ",2) to separate only to two parts at first space pos: identifier + content.
time & space O(nklogn)

M2: since digit types can maintain the relative order, we can first separate into two arrays, one for digit and one for letter type, represent by its index in logs. Then sort the letter array, and use the index to compose the sorted array, then append the digit types logs.
time & space O(n + n'klogn' + n) = O(nklogn) 
on average, should be faster than M1.

 */
// M1
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, (l1, l2) -> {
            String[] l1split = l1.split(" ",2);
            String[] l2split = l2.split(" ", 2);
            boolean l1IsDigit = Character.isDigit(l1split[1].charAt(0));
            boolean l2IsDigit = Character.isDigit(l2split[1].charAt(0));

            if (l1IsDigit && l2IsDigit) {
                return 0;
            } else if (!l1IsDigit && !l2IsDigit) {
                int compareRes = l1split[1].compareTo(l2split[1]);
                return compareRes == 0? l1split[0].compareTo(l2split[0]) : compareRes;
            } else {
                return l1IsDigit? 1 : -1;
            }
        });
        return logs;
    }
}


// M2
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        ArrayList<Integer> numList = new ArrayList<>();
        ArrayList<Integer> letList = new ArrayList<>();

        for (int i = 0; i < logs.length; i++) {
            String[] split = logs[i].split(" ", 2);
            if (Character.isDigit(split[1].charAt(0))) {
                numList.add(i);
            } else {
                letList.add(i);
            }
        }

        // sort letList
        letList.sort((l1, l2) -> {
            String[] l1split = logs[l1].split(" ",2);
            String[] l2split = logs[l2].split(" ", 2);
            int compareRes = l1split[1].compareTo(l2split[1]);
            return compareRes == 0? l1split[0].compareTo(l2split[0]) : compareRes;
        });
        String[] res = new String[logs.length];
        for (int i = 0; i < logs.length; i++) {
            if ( i < letList.size()) {
                res[i] = logs[letList.get(i)];
            } else {
                res[i] = logs[numList.get(i - letList.size())];
            }
        }
        return res;
    }
}






















