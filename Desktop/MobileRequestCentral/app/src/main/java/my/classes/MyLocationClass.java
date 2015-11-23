package my.classes;

import java.io.Serializable;

public class MyLocationClass implements Serializable {
    public static final String EXTRA = "com.example.locationfilterization.LOCATION_EXTRA";
    private static final long serialVersionUID = 1L;
    String path;
    String locationName;
    String floor;
    String floorpath;
    String space;
    String defaultLocation;

    /*public String getLocationId() {
        return locationId;
    }*/
    /*public void setLocationId(String locationId) {
		this.locationId = locationId;
	}*/
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloorpath() {
        return floorpath;
    }

    public void setFloorpath(String floorpath) {
        this.floorpath = floorpath;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

}
