package androidquickstart.test;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import androidquickstart.test.R;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.bonuspack.overlays.InfoWindow;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.slf4j.Logger;
import org.slf4j.impl.AndroidLogger;
import org.structr.android.restclient.*;

import java.util.*;


/**
 * public class HelloAndroidActivity extends Activity {
 * <p/>
 * Called when the activity is first created.
 * //@param savedInstanceState If the activity is being re-initialized after
 * previously being shut down then this Bundle contains the data it most
 * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
 */
    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(androidquickstart.test.R.menu.main, menu);
	return true;
    }     */

public class MainActivity extends Activity implements LocationListener, NetworkStateReceiver.NetworkStateReceiverListener {
    private NetworkStateReceiver networkStateReceiver;

    private LocationManager locationManager;
    public GeoPoint currentLocation;
    OverlayItem myCurrentLocationOverlayItem;
    public ArrayList<OverlayItem> overlayItemArrayLocation = new ArrayList<OverlayItem>();
    MapView map;
    Marker startMarker;
    Logger log;
    public List<Landmark> landmarks;
    ArrayList<GeoPoint> waypoints;
    Road road;
    RadiusMarkerClusterer landmarkMarkers;

    RoadManager roadManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //force localization needed for Tatar
        String languageToLoad = "tt";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        //force localization ended
        setContentView(R.layout.activity_main);
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.CYCLEMAP);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        IMapController mapController = map.getController();
        mapController.setZoom(13);
        GeoPoint startPoint = new GeoPoint(55.79337, 49.10914, 290);
        mapController.setCenter(startPoint);

        //InfoWindow iw = new I
        //Marker startMarker = new Marker(55.79337, 49.10914);
        //map.getOverlays().add(startMarker);
        //startMarker.setPosition(startPoint);
        //startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        //map.getOverlays().add(startMarker);

        // Create an ArrayList with overlays to display objects on map
        ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();

        /*
        // Create som init objects
        OverlayItem linkopingItem = new OverlayItem("Kazan", "Tatarstan",
                new GeoPoint(55.79337, 49.10914));
        OverlayItem stockholmItem = new OverlayItem("Stockholm", "Sweden",
                new GeoPoint(59.3073348, 18.0747967));

        // Add the init objects to the ArrayList overlayItemArray
        overlayItemArray.add(linkopingItem);
        overlayItemArray.add(stockholmItem);
        // Add the Array to the IconOverlay
        */

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("wonderfulkazan", "Before objects load");
        landmarks = new ArrayList<Landmark>() {
        };

        Landmark tuqay = new Landmark("55.785366", "49.120409", "Tuqay square");
        tuqay.description = "Tuqay square is the most central square in Kazan, informally named the Ring";
        landmarks.add(tuqay);
        Landmark sadhermitage = new Landmark("55.790196", "49.127316", "Hermitage Garden");
        landmarks.add(sadhermitage);
        Landmark calil = new Landmark("55.793768", "49.132458", "Musa Cälil museum");
        landmarks.add(calil);
        Landmark derjavin = new Landmark("55.793568", "49.134359", "Derjavin monument");
        landmarks.add(derjavin);
        Landmark baqiurmance = new Landmark("55.791734", "49.133158", "Baqi Urmançe's Museum");
        landmarks.add(baqiurmance);
        Landmark yershov = new Landmark("55.790819", "49.1475", "Yershov Garden");
        landmarks.add(yershov);
        Landmark gorkyPark = new Landmark("55.796659", "49.152074", "Gorky park");
        landmarks.add(gorkyPark);
        Landmark podluj = new Landmark("55.801376", "49.146806", "Podlujnaya Quarter");
        landmarks.add(podluj);
        Landmark fuchs = new Landmark("55.800508", "49.129997", "Karl Fuchs Garden");
        landmarks.add(fuchs);
        Landmark nkc = new Landmark("55.803161", "49.12611", "National Cultural Center");
        landmarks.add(nkc);
        Landmark saray = new Landmark("55.800712", "49.111577", "Ministry of Agriculture");
        landmarks.add(saray);
        Landmark blacklake = new Landmark("55.794959", "49.114571", "The Black Lake");
        landmarks.add(blacklake);
        Landmark leningarden = new Landmark("55.793736", "49.121422", "Lenin Garden");
        landmarks.add(leningarden);
        Landmark kfu = new Landmark("55.792397", "49.120162", "Kazan State University");
        landmarks.add(kfu);
        Landmark kremlin = new Landmark("55.796076", "49.109587", "The Kremlin");
        landmarks.add(kremlin);
        Landmark bolaq = new Landmark("55.791571", "49.108714", "The Bolaq channel");
        landmarks.add(bolaq);
        Landmark fuchshouse = new Landmark("55.785766", "49.111292", "The Fuchs House");
        landmarks.add(fuchshouse);
        Landmark soltan = new Landmark("55.784006", "49.109072", "Soltan Mosque");
        landmarks.add(soltan);
        Landmark gali = new Landmark("55.781207", "49.111328", "Galiev Mosque");
        landmarks.add(gali);
        Landmark pecan = new Landmark("55.783105", "49.114396", "The Hay (Nurulla) Mosque");
        landmarks.add(pecan);
        Landmark zakaban = new Landmark("55.777456", "49.128026", "Zakabannaya Mosque");
        landmarks.add(zakaban);
        Landmark qaban = new Landmark("55.76877", "49.129509", "The Qaban Lake");
        landmarks.add(qaban);
        Landmark apanay = new Landmark("55.777928", "49.119338", "Apanay Mosque");
        landmarks.add(apanay);
        Landmark marcani = new Landmark("55.779809", "49.117608", "Märcani Mosque");
        landmarks.add(marcani);
        Landmark kamal = new Landmark("55.782211", "49.115949", "Kamal Theatre");
        landmarks.add(kamal);

        roadManager = new OSRMRoadManager();
        waypoints = new ArrayList<GeoPoint>();
        //waypoints.add(startPoint);
        //GeoPoint endPoint = new GeoPoint(48.4, -1.9);
        //waypoints.add(endPoint);

        landmarkMarkers = new RadiusMarkerClusterer(this);
        landmarkMarkers.setRadius(100);

        for (Landmark lm : landmarks) {
            //OverlayItem item = new OverlayItem(lm.name, lm.id,
            //      new GeoPoint(Double.parseDouble(lm.latitude), Double.parseDouble(lm.longitude)));

            // Add the init objects to the ArrayList overlayItemArray
            //overlayItemArray.add(item);
            Marker marker = new Marker(map);

            marker.setPosition(new GeoPoint(Double.parseDouble(lm.latitude), Double.parseDouble(lm.longitude)));
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            //
            marker.setIcon(getResources().getDrawable(R.drawable.zilant));
            marker.setTitle(lm.name);
            marker.setSnippet(lm.description);
            marker.setInfoWindow(new LandmarkInfoWindow(map));
            landmarkMarkers.add(marker);
            //map.getOverlays().add(marker);
            waypoints.add(new GeoPoint(Double.parseDouble(lm.latitude), Double.parseDouble(lm.longitude)));

        }
        waypoints.add(new GeoPoint(Double.parseDouble(landmarks.get(0).latitude), Double.parseDouble(landmarks.get(0).longitude)));
        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable) clusterIconD).getBitmap();
        landmarkMarkers.setIcon(clusterIcon);
        map.getOverlays().add(landmarkMarkers);

        //roadManager = new MapQuestRoadManager("Fmjtd%7Cluu82l02nu%2C8g%3Do5-94znu4");
        //roadManager.addRequestOption("routeType=bicycle");
        //ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArray, null);

        // Add the overlay to the MapView
        //map.getOverlays().add(itemizedIconOverlay);
        //map.refreshDrawableState();
        road = roadManager.getRoad(waypoints);

        //other thread is started for the network call
        drawRouteOnline();
        map.invalidate();


        //StructrConnector.initialize(this,1,"password");
        new IdEntityLoader(new EntityHandler() {

            public void handleProgress(Progress... progress) {
                // handle progress / exception
                Log.d("wonderfulkazan", "handling progress");

            }

            //@Override
            public void handleResult(StructrObject structrObject) {
                //log.debug("Object " + structrObject.getId());
                if (structrObject != null) {
                    Log.d("wonderfulkazan", "Object " + structrObject.toString());
                } else {
                    Log.d("wonderfulkazan", "structrObject is null");
                    //then creating landmarks manually as a mock for now:
                }
            }

        }).execute(Landmark.class, "id");

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                3000,   // 3 sec
                10, this);//
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //locationListener = new M();
        if (location != null) {
            currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
        }
        startMarker = new Marker(map);

    }


    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new GeoPoint(location);
        displayMyCurrentLocationOverlay();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void displayMyCurrentLocationOverlay() {
        if (currentLocation != null) {
            /*
            if( overlayItemArrayLocation == null ) {
                overlayItemArrayLocation = new ArrayList<OverlayItem>();
                myCurrentLocationOverlayItem = new OverlayItem("My Location", "My Location!",currentLocation);
                overlayItemArrayLocation.add(myCurrentLocationOverlayItem);
                ItemizedIconOverlay<OverlayItem> itemizedIconOverlayLocation = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArrayLocation, null);
                itemizedIconOverlayLocation.setEnabled(true);
                itemizedIconOverlayLocation.setFocus(myCurrentLocationOverlayItem);
                map.getOverlays().add(itemizedIconOverlayLocation);

            } else {
                //myCurrentLocationOverlayItem.setPoint(currentLocation);
                //overlayItemArrayLocation.requestRedraw();  \
                //overlayItemArrayLocation = new ArrayList<OverlayItem>();
                //myCurrentLocationOverlayItem = new OverlayItem("My Location", "My Location!",currentLocation);
                //overlayItemArrayLocation.add(myCurrentLocationOverlayItem);
                //ItemizedIconOverlay<OverlayItem> itemizedIconOverlayLocation = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArrayLocation, null);
                //itemizedIconOverlayLocation.setEnabled(true);
                //itemizedIconOverlayLocation.setFocus(myCurrentLocationOverlayItem);
                //myCurrentLocationOverlayItem.getMarker(1).getCurrent();
                //map.getOverlays().add(itemizedIconOverlayLocation);
                //map.refreshDrawableState();
              */

            //osmodroidbonuspack

            startMarker.setPosition(currentLocation);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            //
            startMarker.setIcon(getResources().getDrawable(R.drawable.marker_node));
            startMarker.setTitle(getString(R.string.hello_world));
            map.getOverlays().add(startMarker);
            map.invalidate();
            //}
            map.getController().setCenter(currentLocation);
        }
    }

    @Override
    public void networkAvailable() {
        drawRouteOnline();
        map.invalidate();
    }

    @Override
    public void networkUnavailable() {

    }

    public void drawRouteOnline() {
        new Thread(new Runnable() {
            public void run() {
                //roadManager = new OSRMRoadManager();
                try {
                    roadManager = new MapQuestRoadManager("Fmjtd%7Cluu82l02nu%2C8g%3Do5-94znu4");
                    //roadManager.addRequestOption("routeType=bicycle");
                    //pedestrian
                    roadManager.addRequestOption("routeType=pedestrian");
                    road = roadManager.getRoad(waypoints);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        if (road != null) {
                            if (road.mStatus != Road.STATUS_OK) {
                                Log.d("wonderfullkazan", "Error reaching road manager vie network " + road.mStatus);
                            }
                            Polyline roadOverlay = MapQuestRoadManager.buildRoadOverlay(road, Color.GREEN, 10, MainActivity.this);
                            map.getOverlays().remove(map.getOverlays().size() - 1);
                            map.getOverlays().add(roadOverlay);
                            map.getOverlays().add(landmarkMarkers);

                            //Polyline roadOverlay = RoadManager.buildRoadOverlay(road, Color.RED, 8, MainActivity.this);
                            //map.getOverlays().add(roadOverlay);
                        }
                    }
                });
            }
        }).start();

    }

}


//}

