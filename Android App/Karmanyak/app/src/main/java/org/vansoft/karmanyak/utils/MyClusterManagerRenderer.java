package org.vansoft.karmanyak.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import org.vansoft.karmanyak.R;
import org.vansoft.karmanyak.model.ClusterMarker;

public class MyClusterManagerRenderer extends DefaultClusterRenderer<ClusterMarker> {

    private  IconGenerator iconGenerator;
    private  ImageView imageView;
    private  int markerWidth;
    private  int markerHeight;


    public MyClusterManagerRenderer(Context context, GoogleMap map, ClusterManager<ClusterMarker> clusterManager) {
        super(context, map, clusterManager);

        iconGenerator = new IconGenerator(context.getApplicationContext());
        imageView = new ImageView(context.getApplicationContext());
        markerWidth = 50;
//        markerWidth = (int) context.getResources().getDimension(R.dimen.marker_image_weidth);
        markerHeight =50;
        imageView.setLayoutParams(new ViewGroup.LayoutParams(markerWidth,markerHeight));
//        int padding = (int) context.getResources().getDimension(R.dimen.marker_img_padding);
        int padding = 4;
        imageView.setPadding(padding,padding,padding,padding);
        iconGenerator.setContentView(imageView);

    }

    @Override
    protected void onBeforeClusterItemRendered(ClusterMarker item, MarkerOptions markerOptions) {
        imageView.setImageResource(item.getIconPicture());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());

    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<ClusterMarker> cluster) {
        return false;
    }
}
