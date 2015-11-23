package my.classes.reservation;

import java.io.Serializable;

/**
 * Created by amitk on 8/12/2015.
 */
public class myReservationClass implements Serializable, Comparable<myReservationClass> {

    private static final long serialVersionUID = 46543445;
    String numberofattendees;
    String plannedduration;
    String foodservice;
    String equipment;
    String storage;
    String specialneed;
    String network_connection;
    String ada_available;
    String in_room_projector;
    String whiteboard;
    String city;
    String video_conference;
    String roomtype;
    String reservation_type;
    String teleconference_phone;
    String room_phone;
    String reservation_manager_type;
    String capacity;
    String catering_available;
    String control_number;
    String buildingname;
    String floorname;
    String spacename;
    String description;
    String reservationId;
    String date;
    String starttime;
    String endtime;
    String timestamp;
    String requestedlayout;
    String checkedin;

    public String getBuildingname() {
        return buildingname;
    }

    public void setBuildingname(String buildingname) {
        this.buildingname = buildingname;
    }

    public String getFloorname() {
        return floorname;
    }

    public void setFloorname(String floorname) {
        this.floorname = floorname;
    }

    public String getSpacename() {
        return spacename;
    }

    public void setSpacename(String spacename) {
        this.spacename = spacename;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestedlayout() {
        return requestedlayout;
    }

    public void setRequestedlayout(String requestedlayout) {
        this.requestedlayout = requestedlayout;
    }

    public String getNumberofattendees() {
        return numberofattendees;
    }

    public void setNumberofattendees(String numberofattendees) {
        this.numberofattendees = numberofattendees;
    }

    public String getPlannedduration() {
        return plannedduration;
    }

    public void setPlannedduration(String plannedduration) {
        this.plannedduration = plannedduration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodservice() {
        return foodservice;
    }

    public void setFoodservice(String foodservice) {
        this.foodservice = foodservice;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getSpecialneed() {
        return specialneed;
    }

    public void setSpecialneed(String specialneed) {
        this.specialneed = specialneed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getCheckedin() {
        return checkedin;
    }

    public void setCheckedin(String checkedin) {
        this.checkedin = checkedin;
    }

    public String getReservation_manager_type() {
        return reservation_manager_type;
    }

    public void setReservation_manager_type(String reservation_manager_type) {
        this.reservation_manager_type = reservation_manager_type;
    }

    public String getNetwork_connection() {
        return network_connection;
    }

    public void setNetwork_connection(String network_connection) {
        this.network_connection = network_connection;
    }

    public String getAda_available() {
        return ada_available;
    }

    public void setAda_available(String ada_available) {
        this.ada_available = ada_available;
    }

    public String getIn_room_projector() {
        return in_room_projector;
    }

    public void setIn_room_projector(String in_room_projector) {
        this.in_room_projector = in_room_projector;
    }

    public String getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(String whiteboard) {
        this.whiteboard = whiteboard;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVideo_conference() {
        return video_conference;
    }

    public void setVideo_conference(String video_conference) {
        this.video_conference = video_conference;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getReservation_type() {
        return reservation_type;
    }

    public void setReservation_type(String reservation_type) {
        this.reservation_type = reservation_type;
    }

    public String getTeleconference_phone() {
        return teleconference_phone;
    }

    public void setTeleconference_phone(String teleconference_phone) {
        this.teleconference_phone = teleconference_phone;
    }

    public String getRoom_phone() {
        return room_phone;
    }

    public void setRoom_phone(String room_phone) {
        this.room_phone = room_phone;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCatering_available() {
        return catering_available;
    }

    public void setCatering_available(String catering_available) {
        this.catering_available = catering_available;
    }

    public String getControl_number() {
        return control_number;
    }

    public void setControl_number(String control_number) {
        this.control_number = control_number;
    }


    @Override
    public int compareTo(myReservationClass another) {
        return this.getDate().compareTo(another.getDate());
    }
}
