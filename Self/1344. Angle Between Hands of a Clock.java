/*Initial thought
separately calculate the angle for hour and minute bars towards 12:00 position.
for hour: angle = hour * 30 + minute/60 * 30
for minute: angle = minute / 5 * 30 
result = abs(hour angle - minute angle), note if result >= 180. return 360 - result

*/
class Solution {
    public double angleClock(int hour, int minutes) {
        double hourAngle = hour * 30 + minutes * 0.5;
        double minAngle = minutes * 1.0 * 6;
        double result = Math.abs(hourAngle - minAngle);
        return result > 180 ? 360 -result : result;
    }
}