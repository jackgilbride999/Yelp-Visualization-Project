import org.gicentre.geomap.*;

public static final int SCREEN_X = 800;
public static final int SCREEN_Y = 800;
public final color RED = color(255,0,0);
public final color BLUE = color(0,0,255);
public final color GREEN = color(0,255,0);

GeoMap geoMap;

void settings() {
  size(SCREEN_X, SCREEN_Y);
}
void setup()
{
  //geoMap = new GeoMap(SCREEN_X/2, 0, SCREEN_X/2, SCREEN_Y/2, this); // (x, y, width, height, this)
  geoMap = new GeoMap(this);
  geoMap.readFile("ne_10m_admin_0_countries");  // Read shapefile
}

void draw() {
  background(BLUE);  // Ocean Colour
  fill(GREEN);      // Land colour
  stroke(RED);      // Boundary colour
  geoMap.draw();

  // Find the country at mouse position and draw in different colour.
  int id = geoMap.getID(mouseX, mouseY);
  if (id != -1)
  {
    fill(RED);      // Highlighted land colour.
    geoMap.draw(id);
  }
}