package my.classes.reservation;

/**
 * Created by amitk on 9/29/2015.
 */
public class MyEquipmentClass {
    String name;
    String status;
    String reservation_number;
    String system_record_id;
    String building_name;

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReservation_number() {
        return reservation_number;
    }

    public void setReservation_number(String reservation_number) {
        this.reservation_number = reservation_number;
    }

    public String getSystem_record_id() {
        return system_record_id;
    }

    public void setSystem_record_id(String system_record_id) {
        this.system_record_id = system_record_id;
    }
}
