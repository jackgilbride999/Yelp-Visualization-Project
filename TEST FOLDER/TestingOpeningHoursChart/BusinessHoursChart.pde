class BusinessHoursChart {
  private boolean[][] openingHours;
  private static final int DAYS_PER_WEEK = 7;
  private static final int HALF_HOURS_PER_DAY = 48;
  private static final int BOX_WIDTH = 20;
  private static final int BOX_HEIGHT = 20;
  private int graphX, graphY;

  public BusinessHoursChart(String[] hoursArray, int x, int y) {
    this.graphX=x;
    this.graphY=y;
    openingHours = new boolean[DAYS_PER_WEEK][HALF_HOURS_PER_DAY];
    if (hoursArray!=null) {
      for (int i=0; i<hoursArray.length; i++) {
        try {
          String hoursOpen = hoursArray[i];
          if (!hoursOpen.equals("None")) {
            String[] hours = hoursOpen.split("-"); // opening time will be at index  0,  closing time at index  1
            String openingTime = hours[0];
            int indexToOpenAt = 2*Integer.parseInt(openingTime.split(":")[0]); //eg if the business opens at 7:00, will start indexing at index 14 as we are working with half hours
            if (Integer.parseInt(openingTime.split(":")[1])>=30) { // but if it opens at 7:30 we will start indexing at index 15
              indexToOpenAt++;
            }
            String closingTime = hours[1];
            int indexToCloseAt = 2*Integer.parseInt(closingTime.split(":")[0]);
            if (Integer.parseInt(closingTime.split(":")[1])<30) {
              indexToCloseAt--;
            }
            else if(Integer.parseInt(closingTime.split(":")[1])>45){
              indexToCloseAt++;
            }
            for (int count = indexToOpenAt; count<=indexToCloseAt; count++) {
              //   println("indexToOpenAt="+indexToOpenAt+" indexToCloseAt="+indexToCloseAt+" i="+i+" count="+count); // for debugging
              openingHours[i][count] = true;
            }
          }
        }
        catch(Exception e) {
        }
      }
    }
  }
  void draw() {
    fill(0);
    drawAxes();
    drawXLabels();
    drawYLabels();
    for (int dayCount = 0; dayCount<openingHours.length; dayCount++) {
      for (int hourCount = 0; hourCount<openingHours[dayCount].length; hourCount++) {
        boolean isOpen = openingHours[dayCount][hourCount];
        if (isOpen) { // draw full boxes if the business is open during the specific half-hour
          fill(65, 244, 169);
        } else {
          fill(255, 0, 0);
        }
        stroke(color(0));
        rect(graphX+hourCount*BOX_WIDTH, graphY+dayCount*BOX_HEIGHT+BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
      }
    }
  }

  void drawAxes() {
    line(graphX, graphY+BOX_HEIGHT, graphX+HALF_HOURS_PER_DAY*BOX_WIDTH, graphY+BOX_HEIGHT);
    line(graphX, graphY+BOX_HEIGHT, graphX, graphY + DAYS_PER_WEEK*BOX_HEIGHT+BOX_HEIGHT);
    String title = "Opening Hours:";
    text(title, graphX+(HALF_HOURS_PER_DAY*BOX_WIDTH)/2-textWidth(title)/2, graphY);
  }

  void drawYLabels() {
    text("Mon", graphX-textWidth("Mon")-2, graphY+2*BOX_HEIGHT);
    text("Tue", graphX-textWidth("Tue")-2, graphY+3*BOX_HEIGHT);
    text("Wed", graphX-textWidth("Wed")-2, graphY+4*BOX_HEIGHT);
    text("Thu", graphX-textWidth("Thu")-2, graphY+5*BOX_HEIGHT);
    text("Fri", graphX-textWidth("Fri")-2, graphY+6*BOX_HEIGHT);
    text("Sat", graphX-textWidth("Sat")-2, graphY+7*BOX_HEIGHT);
    text("Sun", graphX-textWidth("Sun")-2, graphY+8*BOX_HEIGHT);
  }

  void drawXLabels() {
    for (int hour = 0; hour<24; hour++) {
      String time = "" + hour + ":00";
      text(time, graphX+BOX_WIDTH+2*BOX_WIDTH*hour-textWidth(time)/2, graphY+BOX_HEIGHT-2);
    }
  }

  boolean hasBusinessHours() {
    for (int day=0; day<openingHours.length; day++)
      for (int halfHour=0; halfHour<openingHours[day].length; halfHour++)
        if (openingHours[day][halfHour]==true)  // This means that in at least one day there are business hours for this business
          return true;
    return false;
  }
}