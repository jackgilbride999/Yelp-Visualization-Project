//http://unfoldingmaps.org/tutorials/basic-how-to-use-unfolding

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.utils.*;  
import de.fhpotsdam.unfolding.providers.*;

UnfoldingMap map;
SimplePointMarker tcdMarker;

void setup() {
  size(800, 600);
  map = new UnfoldingMap(this, new OpenStreetMap.OpenStreetMapProvider()); // put the map provider in this constructor
  MapUtils.createDefaultEventDispatcher(this, map);             // allows us to drag, zoom etc
  Location tcdLocation = new Location(53.3438, -6.2546); // tcd location as found through a Google search
  map.zoomAndPanTo(tcdLocation, 15); // about 15 for street level
  float maxPanningDistance = 1;
  map.setPanningRestriction(tcdLocation, maxPanningDistance); // comment this out if you don't want the panning restricted
  tcdMarker = new SimplePointMarker(tcdLocation); // markers do not seem to work for Processing 3, may need to use different API for maps,
  map.addMarkers(tcdMarker);                      // but aside from the markers this API is good
}

void draw() {
  map.draw();

}