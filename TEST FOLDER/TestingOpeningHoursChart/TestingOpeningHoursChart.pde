final int SCREEN_X=1200;
final int SCREEN_Y=600;
String[] hours = {"1:30-5:0", "0:00-2:16", "10:45-22:30", "1:0-23:34", "0:0-23:59", "None", "6:36-7:49"};
BusinessHoursChart chart;
int x, y;
void settings() {
  size(SCREEN_X, SCREEN_Y);
}

void setup() {
  x=50;
  y=50;
  chart = new BusinessHoursChart(hours, x, y);
}

void draw() {
  if (chart.hasBusinessHours()) {  // some businesses have all business hours set to "None" so no point drawing this
    chart.draw();
  } else {
    text("No business hours available", 50, 50);
  }
}