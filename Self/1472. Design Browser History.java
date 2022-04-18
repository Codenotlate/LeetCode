/*Initial thought
Using an arraylist, track the current index and the last index.
for visit, if cur == list.size()-1, add one string into the list, cur++ and last++. If cur < last, set list[cur+1] to url, cur++, last = cur.
for back, cur = max(0,cur - steps), return list[cur]
for forward, cur = min(last, cur+steps), return list[cur]

time O(1) for visit, back and forward.

*/
class BrowserHistory {
    ArrayList<String> list;
    int curIdx;
    int lastIdx;

    public BrowserHistory(String homepage) {
        list = new ArrayList<>();
        list.add(homepage);
        curIdx = 0;
        lastIdx = 0;
    }
    
    public void visit(String url) {
        if (curIdx == list.size()-1) {
            list.add(url);
            curIdx++;
            lastIdx++;
        } else {
            list.set(curIdx+1, url);
            curIdx++;
            lastIdx = curIdx;
        }
    }
    
    public String back(int steps) {
        curIdx = Math.max(0, curIdx - steps);
        return list.get(curIdx);
    }
    
    public String forward(int steps) {
        curIdx = Math.min(curIdx+steps, lastIdx);
        return list.get(curIdx);
    }
}

// based on discussion, this is already the best way!







// Review
class BrowserHistory {
    List<String> urllist;
    int curPos;
    int lastPos;

    public BrowserHistory(String homepage) {
        urllist = new ArrayList<>();
        urllist.add(homepage);
        curPos = 0;
        lastPos = 0;
    }
    
    public void visit(String url) {
        if (curPos == urllist.size() - 1) {urllist.add(url);}
        else {urllist.set(curPos+1, url);}      
        curPos++;
        lastPos = curPos;
    }
    
    public String back(int steps) {
        curPos = Math.max(curPos - steps, 0);
        return urllist.get(curPos);
    }
    
    public String forward(int steps) {
        curPos = Math.min(curPos + steps, lastPos);
        return urllist.get(curPos);
    }
}