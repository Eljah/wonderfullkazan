package androidquickstart.test;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

/**
 * Created by Ilya Evlampiev on 22.03.15.
 */
public class LandmarkInfoWindow  extends MarkerInfoWindow {
    //POI mSelectedPoi;
    String description;
    public LandmarkInfoWindow(MapView mapView) {
        super(R.layout.bonuspack_bubble, mapView);
        Button btn = (Button)(mView.findViewById(R.id.bubble_moreinfo));
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (description != null){

                    //Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedPoi.mUrl));
                    //view.getContext().startActivity(myIntent);
                } else {
                    Toast.makeText(view.getContext(), "Button clicked", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override public void onOpen(Object item){
        super.onOpen(item);
        mView.findViewById(R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
        Marker marker = (Marker)item;
        //mSelectedPoi = (POI)marker.getRelatedObject();

        //8. put thumbnail image in bubble, fetching the thumbnail in background:
        //if (mSelectedPoi.mThumbnailPath != null){
        //    ImageView imageView = (ImageView)mView.findViewById(R.id.bubble_image);
        //    mSelectedPoi.fetchThumbnailOnThread(imageView);
        //}
    }
}
