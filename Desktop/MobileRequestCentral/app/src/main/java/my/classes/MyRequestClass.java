package my.classes;

import java.io.Serializable;

public class MyRequestClass implements Serializable {
    public static final String REQUESTCLASSEXTRA = "com.example.mobilerequestcentarl.REQUESTCLASSEXTRA";
    private static final long serialVersionUID = 1L;

	/*
     * String status, requestid, description, requesttype, location, dueon,
	 * organization, id;
	 */
    String location;
    String organization;
    String service;
    String description;
    String imagename;
    String requesttype;
    String id;
    String duedate;
    String WOStatus;
    String WORequestID;
    String WorkOrderID;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static String getRequestclassextra() {
        return REQUESTCLASSEXTRA;
    }

    public String getWorkOrderID() {
        return WorkOrderID;
    }

    public void setWorkOrderID(String workOrderID) {
        WorkOrderID = workOrderID;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getWOStatus() {
        return WOStatus;
    }

    public void setWOStatus(String wOStatus) {
        WOStatus = wOStatus;
    }

    public String getWORequestID() {
        return WORequestID;
    }

    public void setWORequestID(String wORequestID) {
        WORequestID = wORequestID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

}
