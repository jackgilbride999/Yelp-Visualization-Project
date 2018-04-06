//http://unfoldingmaps.org/tutorials/basic-how-to-use-unfolding
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.utils.*;  
import de.fhpotsdam.unfolding.providers.*;
final int SCREEN_X = 800;
final int SCREEN_Y = 600;
UnfoldingMap map;

void settings(){ 
 size(SCREEN_X, SCREEN_Y, P2D);  // need this renderer for the markers, not sure if this will mess up anyone else's code yet
}

void setup() {
  background(0);
  // just showing that this map can be drawn on a subsection of the screen, just delete the four ints for it to draw on the full screen
  map = new UnfoldingMap(this,  2*SCREEN_X/3, 0, SCREEN_X/3, SCREEN_Y/3, new OpenStreetMap.OpenStreetMapProvider()); // put the map provider in this constructor
  MapUtils.createDefaultEventDispatcher(this, map);   // allows us to drag, zoom etc
  Location tcdLocation = new Location(53.3438, -6.2546); // tcd location as found through a Google search
  Location ucdLocation = new Location(53.3083, -6.2236);
  Location dcuLocation = new Location(53.3851, -6.2568);
  Location spireLocation = new Location(53.3498, -6.2603);
  map.zoomAndPanTo(spireLocation, 10); // about 15 for street level, 13 for city level etc
  float maxPanningDistance = 10;
  map.setPanningRestriction(spireLocation, maxPanningDistance); // comment this out if you don't want the panning restricted
  ImageMarker tcdMarker = new ImageMarker(tcdLocation, loadImage("ui/marker.png"));    // just showing the different markers that can be used
  ImageMarker ucdMarker = new ImageMarker(ucdLocation, loadImage("ui/marker_red.png"));
  ImageMarker dcuMarker = new ImageMarker(dcuLocation, loadImage("ui/marker_gray.png"));
  map.addMarkers(tcdMarker, ucdMarker, dcuMarker);
}

void draw() {
  map.draw();
  text("Rest of screen", SCREEN_X/2, SCREEN_Y/2);
}