package androidquickstart.test;

import com.google.gson.annotations.Expose;
import org.structr.android.restclient.StructrObject;

import java.util.Date;

/**
 * Created by Ilya Evlampiev on 21.03.15.
 */
public class Landmark extends StructrObject {
    Landmark(String latitude, String longitude, String name)
    {
        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
    }

    @Expose
    public String id;
    @Expose public String name;
    @Expose private Date timestamp;
    @Expose public String latitude;
    @Expose public String longitude;
    @Expose public String description;

}
