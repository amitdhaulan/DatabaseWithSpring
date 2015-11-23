package database.helper;

/*Change getReservationEquipmentList_ViaBuildinhg query....*/

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import my.classes.FacilityClass;
import my.classes.MyLocationClass;
import my.classes.MyRequestClass;
import my.classes.OrgClass;
import my.classes.ServiceClass;
import my.classes.reservation.MyEquipmentClass;
import my.classes.reservation.ReservationBuildingClass;
import my.classes.reservation.ReservationSpaceClass;
import my.classes.reservation.myReservationClass;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSON = 1;
    public static final String DATABASE_NAME = "Location1.db";
    String DB_PATH =null;
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    // *********************************************************************
    public static final String KEY_ID = "id";
    //    *****************************************************************

    public static String TABLE_USER = "User";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_TOKEN = "token";
    //    *******************************************************************

    public static String MY_LOCATION_TABLE = "Location";
    public static final String KEY_LOCATION_HIERARCHYPATH = "HierarchyPath";
    public static final String KEY_LOCATION_NAME = "LocationName";
    public static final String KEY_LOCATION_DEFAULTLOCATION = "defaultLocation";
    public static final String KEY_LOCATION_MYFLOORNAME = "FloorName";
    public static final String KEY_LOCATION_MYFSPACENAME = "SpaceName";
    //    *********************************************************************

    public static String TABLE_REQUESTDATA = "RequestData";
    public static final String KEY_REQUESTDATA_REQUEST_LOCATION = "location";
    public static final String KEY_REQUESTDATA_ORGANIZATION = "organization";
    public static final String KEY_REQUESTDATA_REQUESTTYPE = "requesttype";
    public static final String KEY_REQUESTDATA_REQUEST_DESCRIPTION = "description";
    public static final String KEY_REQUESTDATA_STATUS = "status";
    public static final String KEY_REQUESTDATA_WOREQUESTID = "requestid";
    public static final String KEY_REQUESTDATA_DUEON = "dueon";
    public static final String KEY_REQUESTDATA_WORKORDERID = "workorderid";
    public static final String KEY_REQUESTDATA_SERVICE = "Service";
    //    ***********************************************************************

    public static String MY_SERVICE_TABLE = "MyServiceTable";
    public static final String KEY_SERVICE_SERVICE_TYPE_RECORD_ID = "ServiceTypeRecordId";
    public static final String KEY_SERVICE_SERVICE_NAME = "ServiceName";
    public static final String KEY_SERVICE_HIERARCHY_PATH = "HierarchyPath";
    //    ***********************************************************************
    public static String MY_RESERVATIONBUILDING_TABLE = "MyReservationBuildingTable";
    public static final String KEY_RESERVATIONBUILDING_BUILDING_ID = "BuildingId";
    public static final String KEY_RESERVATIONBUILDING_BUILDING_NAME = "BuildingName";
    //    ************************************************************************
    public static String MY_RESERVATION_SPACE_TABLE = "MyReservationSpaceTable";
    public static final String KEY_RESERVATIONSPACE_NAME = "SpaceName";
    public static final String KEY_RESERVATIONSPACE_STATUS= "Status";
    public static final String KEY_RESERVATIONSPACE_PARENT_BUILDING = "ParentBuilding";
    public static final String KEY_RESERVATIONSPACE_RESERVABLE = "Reservable";
    public static final String KEY_RESERVATIONSPACE_ID = "SpaceId";
    public static final String KEY_RESERVATIONSPACE_HIERARCHYPATH= "HierarchyPath";
    public static final String KEY_RESERVATIONSPACE_RECORDID= "RecordId";
    public static final String KEY_RESERVATIONSPACE_CAPACITY= "Capacity";
    public static final String KEY_RESERVATIONSPACE_RESERVATIONCLASSNAME = "ReservationClassName";
    public static final String KEY_RESERVATIONSPACE_RESERVATIONNAME = "ReservationName";
    public static final String KEY_RESERVATIONSPACE_LAYOUTTYPE= "LayoutType";
    public static final String KEY_RESERVATIONSPACE_ROOMTYPE= "RoomType";
    //    *********************************************************************
    public static String MY_ORG_TABLE = "MyOrgTable";
    public static final String KEY_ORG_DEF_ORG = "DefOrg";
    public static final String KEY_ORG_NAME = "OrgName";
    public static final String KEY_ORG_PATH = "OrgPath";
    public static final String KEY_ORG_BUILDING = "OrgBuilding";
    public static final String KEY_ORG_FLOOR = "OrgFloor";
    public static final String KEY_ORG_SPACE = "OrgSpace";
    public static final String KEY_ORG_LOCATION_LOOKUP = "LocationLookup";
    public static final String KEY_ORG_ORGAINASTION_ID = "OrganisationId";
    public static final String KEY_ORG_PROPERTY = "OrgProperty";
    //    **********************************************************************

    public static String MY_FACILITY_TABLE = "MYFacilityTable";
    public static final String KEY_FACILITY_GUI_ID = "gui_id";
    public static final String KEY_FACILITY_PARENT_TYPE = "parent_type";
    public static final String KEY_FACILITY_REQUEST_CLASS = "request_class";
    public static final String KEY_FACILITY_REQUEST_TYPE = "RequestType";

    //    *********************************Database for demo****************************
    public static String MY_DEMORESERVATION_TABLE = "MyDemoReservationTable";
    public static final String KEY_MY_DEMORESERVATION_TABLE_BUILDINGNAME = "BuildingNameDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_DELETEDTAG = "DeletedTag";
    public static final String KEY_MY_DEMORESERVATION_TABLE_CHECKEDIN = "CheckedIn";
    public static final String KEY_MY_DEMORESERVATION_TABLE_FLOORNAME = "FloorNameDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_SPACENAME = "SpaceNameDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_PLANNEDDURATION = "PlannedDurationDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_REQUESTEDLAYOUT = "RequestedLayoutDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_NUMBERFOFATTENDEES = "NumberOfAttendeesDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_DESCRIPTION = "DescriptionDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_FOODSERVICE = "FoodServiceDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_EQUIPMENT = "EquipmentDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_STORAGE = "StorageDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_SPECIALNEED = "SpecialNeedDemo";
    public static final String KEY_MY_DEMORESERVATION_TABLE_STARTTIME = "StartTime";
    public static final String KEY_MY_DEMORESERVATION_TABLE_ENDTIME = "EndTime";
    public static final String KEY_MY_DEMORESERVATION_TABLE_DATE = "Date";
    public static final String KEY_MY_DEMORESERVATION_TABLE_RECORDID = "RecordId";

    public static final String KEY_MY_DEMORESERVATION_TABLE_NETWORKCONNECTION = "NetworkConnection";
    public static final String KEY_MY_DEMORESERVATION_TABLE_ADAAVAILABLE = "AdaAvailable";
    public static final String KEY_MY_DEMORESERVATION_TABLE_IN_ROOM_PROJECTOR = "InRoomProjector ";
    public static final String KEY_MY_DEMORESERVATION_TABLE_WHITEBOARD = "WhiteBoard";
    public static final String KEY_MY_DEMORESERVATION_TABLE_CITY = "City";
    public static final String KEY_MY_DEMORESERVATION_TABLE_VIDEOCONFERENCE = "VideoConference";
    public static final String KEY_MY_DEMORESERVATION_TABLE_CONFERENCENUMBER = "ConferenceNumber";
    public static final String KEY_MY_DEMORESERVATION_TABLE_ROOMPHONE = "RoomPhone";
    public static final String KEY_MY_DEMORESERVATION_TABLE_CATERINGAVAILABLE = "CateringAvailable";
    public static final String KEY_MY_DEMORESERVATION_TABLE_CAPACITY = "Capacity";
    public static final String KEY_MY_DEMORESERVATION_TABLE_CONTROLNUMBER = "ControlNumber";
    public static final String KEY_MY_DEMORESERVATION_TABLE_RESERVATIONMANAGERTYPE = "ReservationManagerType";
    public static final String KEY_MY_DEMORESERVATION_TABLE_RESERVATIONCLASS = "ReservationClass";


    public static String MY_EQUIPMENT_TABLE = "MyEquipmentTable";
    public static final String KEY_MY_EQUIPMENT_TABLE_NAME = "Name";
    public static final String KEY_MY_EQUIPMENT_TABLE_BUILDING = "Building";
    public static final String KEY_MY_EQUIPMENT_TABLE_STATUS = "Status";
    public static final String KEY_MY_EQUIPMENT_TABLE_RESERVATION_NUMBER = "ReservationNumber";
    public static final String KEY_MY_EQUIPMENT_TABLE_SYSTEM_RECORD_ID = "SystemRecordId";
    //    *********************************************************************************


    //    *****************Unused Tables and Columns*****************************************
    public static String TABLE_Cncn = "Cncn";
    public static String TABLE_REQUEST_CREATED = "RequestCreated";
    public static String TABLE_RESERVED_SPACE = "ReservedSpace";

    public static final String KEY_LOCATION_ID = "LocationId";
    public static final String KEY_PROPERTY = "Property";
    public static final String KEY_BUILDING = "Building";
    public static final String KEY_ORGANISATION_NAME = "OrganisationName";
    public static final String KEY_PEOPLE_NAME = "PeopleName";
    public static final String KEY_PEOPLE_SPECID = "PeopleSpecId";
    public static final String KEY_ORGANISATION_SPECID = "OrganisationSpecId";
    public static final String KEY_TIME = "time";
    public static final String KEY_LOCATION = "Location";
    public static final String KEY_ORGANISATION = "Organisation";
    public static final String KEY_DESCRIPTION = "Description";
    public static final String KEY_WOSTATUS = "wostatus";
    public static final String KEY_WOID = "woid";
    public static final String KEY_RESERVATION_NAME = "Reservation_name";
    public static final String KEY_RESERVATION_DATE = "Reservation_date";
    public static final String KEY_RESERVATION_START_TIME = "StartTime";
    public static final String KEY_RESERVATION_END_TIME = "EndTime";
    public static final String KEY_RESERVATION_EMAIL = "Reservation_email";
    public static final String KEY_RESERVATION_PHONENO = "Reservation_phoneno";
    public static final String KEY_CLASS_ROOM = "ClassRoom";
    public static final String KEY_NUMBER_OF_ATTENDES = "NumberOfAttendes";
    public static final String KEY_FOOD_SERVICE_REQUESTED = "FoodServiceRequested";
    public static final String KEY_SPECIAL_NEED = "SpecialNeed";
    public static final String KEY_STORAGE_NEEDED = "Storage_needed";
    public static final String KEY_COMMENT = "Comment";
    public static final String KEY_EQUIPMENT_REQUIRED = "EquipmentRequired";
    //    ************************************************************************

    public static final String KEY_FLOOR_AVAILABLE = "FloorAvailable";
    public static final String KEY_SPACE_AVAILABLE = "SpaceAvailable";
    public static final String KEY_MYFLOORPATH = "FloorPath";
    public static final String KEY_SPACE_ID = "SpaceId";
    public static final String KEY_IMAGE = "Image";
    //    ****************************************************************


    // =======================================================//

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSON);
        this.myContext = context;

        if(android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

    }
    /*public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
        }else{
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {

                AssetManager am = myContext.getAssets();
                OutputStream os = new FileOutputStream("/data/data/"+"com.example.mobilerequestcentral"+"/databases/Location1.db");
                byte[] b = new byte[100];
                int r;
                InputStream is = am.open("Location1.db");
                while ((r = is.read(b)) != -1) {
                    os.write(b, 0, r);
                }
                is.close();
                os.close();
                *//*throw new Error("Error copying database");*//*

                // COPY IF NOT EXISTS

            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){

            System.out.println("Database does not exist!");
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_PATH+DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();



    }
    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }
    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }*/


    @Override
    public void onCreate(SQLiteDatabase db) {
        // ***********************************************************************
        String CREATE_LOCATION_TABLE = "Create Table " + MY_LOCATION_TABLE
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LOCATION_ID + " TEXT , " + KEY_LOCATION_HIERARCHYPATH + " TEXT, " + KEY_LOCATION_MYFLOORNAME + " TEXT, "
                + KEY_MYFLOORPATH + " TEXT, " + KEY_LOCATION_MYFSPACENAME + " TEXT,  " + KEY_FLOOR_AVAILABLE + " TEXT, " + KEY_SPACE_AVAILABLE + " TEXT, " + KEY_LOCATION_DEFAULTLOCATION + " TEXT, " + KEY_LOCATION_NAME + " TEXT )";
        db.execSQL(CREATE_LOCATION_TABLE);

        String CREATE_Service_TABLE = "Create Table " + MY_SERVICE_TABLE
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_SERVICE_SERVICE_TYPE_RECORD_ID + " TEXT , " + KEY_SERVICE_SERVICE_NAME
                + " TEXT, " + KEY_SERVICE_HIERARCHY_PATH + " TEXT" + ")";
        db.execSQL(CREATE_Service_TABLE);

        String CREATE_ReservationBuilding_TABLE = "Create Table " + MY_RESERVATIONBUILDING_TABLE
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RESERVATIONBUILDING_BUILDING_ID + " TEXT , " + KEY_RESERVATIONBUILDING_BUILDING_NAME
                + " TEXT" + ")";
        db.execSQL(CREATE_ReservationBuilding_TABLE);


        String CREATE_ReservationSpace_TABLE = "Create Table " + MY_RESERVATION_SPACE_TABLE
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RESERVATIONSPACE_ID+ " TEXT , "+ KEY_RESERVATIONSPACE_NAME+ " TEXT , "
                + KEY_RESERVATIONSPACE_HIERARCHYPATH+ " TEXT , "
                + KEY_RESERVATIONSPACE_RESERVABLE + " TEXT , "
                + KEY_RESERVATIONSPACE_PARENT_BUILDING+ " TEXT , "

                + KEY_RESERVATIONSPACE_RECORDID+ " TEXT , "
                + KEY_RESERVATIONSPACE_CAPACITY+ " TEXT , "
                + KEY_RESERVATIONSPACE_RESERVATIONCLASSNAME + " TEXT , "
                + KEY_RESERVATIONSPACE_RESERVATIONNAME + " TEXT , "
                + KEY_RESERVATIONSPACE_LAYOUTTYPE+ " TEXT , "
                + KEY_RESERVATIONSPACE_ROOMTYPE+ " TEXT , "

                + KEY_RESERVATIONSPACE_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_ReservationSpace_TABLE);



       /* String CREATE_Org_TABLE = "Create Table " + MY_ORG_TABLE + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ORG_DEF_ORG + " TEXT, " + KEY_ORG_NAME
                + " TEXT)";
        db.execSQL(CREATE_Org_TABLE);*/


        String CREATE_Org_TABLE = "Create Table " + MY_ORG_TABLE + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ORG_DEF_ORG + " TEXT, "
                + KEY_ORG_LOCATION_LOOKUP + " TEXT, "
                + KEY_ORG_BUILDING + " TEXT, "
                + KEY_ORG_FLOOR + " TEXT, "
                + KEY_ORG_NAME + " TEXT, "
                + KEY_ORG_PATH + " TEXT, "
                + KEY_ORG_PROPERTY + " TEXT, "
                + KEY_ORG_SPACE + " TEXT, "
                + KEY_ORG_ORGAINASTION_ID+ " TEXT)";
        db.execSQL(CREATE_Org_TABLE);


        String CREATE_FACILITY_TABLE = "Create Table " + MY_FACILITY_TABLE
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FACILITY_PARENT_TYPE + " TEXT , " + KEY_FACILITY_REQUEST_CLASS + " TEXT, " + KEY_FACILITY_REQUEST_TYPE + " TEXT, "
                + KEY_FACILITY_GUI_ID + " TEXT )";
        db.execSQL(CREATE_FACILITY_TABLE);


        String CREATE_REQUEST_TABLE = "Create Table " + TABLE_REQUEST_CREATED
                + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FACILITY_REQUEST_TYPE + " TEXT , " + KEY_LOCATION + " TEXT , "
                + KEY_ORGANISATION + " TEXT, " + KEY_REQUESTDATA_SERVICE + " TEXT, "
                + KEY_DESCRIPTION + " TEXT, " + KEY_IMAGE + " TEXT "
                + KEY_WOSTATUS + " TEXT, " + KEY_WOID + " TEXT" + ")";
        db.execSQL(CREATE_REQUEST_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USER_NAME
                + " TEXT," + KEY_USER_TOKEN + " TEXT, " + KEY_USER_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_CNCN = "CREATE TABLE " + TABLE_Cncn + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TIME + " TEXT"
                + ")";
        db.execSQL(CREATE_CNCN);

        String CREATE_REQUESTDATA_TABLE = "CREATE TABLE " + TABLE_REQUESTDATA
                + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_REQUESTDATA_STATUS + " TEXT," + KEY_REQUESTDATA_WOREQUESTID + " TEXT, "
                + KEY_REQUESTDATA_WORKORDERID + " TEXT,"
                + KEY_REQUESTDATA_REQUEST_DESCRIPTION + " TEXT, " + KEY_REQUESTDATA_REQUESTTYPE
                + " TEXT, " + KEY_REQUESTDATA_REQUEST_LOCATION + " TEXT, " + KEY_REQUESTDATA_DUEON
                + " TEXT, " + KEY_REQUESTDATA_ORGANIZATION + " TEXT, " + KEY_IMAGE
                + " TEXT, " + KEY_REQUESTDATA_SERVICE + " TEXT " + ")";

        System.out.println(CREATE_REQUESTDATA_TABLE);
        db.execSQL(CREATE_REQUESTDATA_TABLE);

        String CREATE_RESERVATION_TABLE = "Create Table "
                + TABLE_RESERVED_SPACE + " ( " + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SPACE_ID
                + " TEXT, " + KEY_RESERVATION_NAME + " TEXT, "
                + KEY_RESERVATION_START_TIME + " TEXT, "
                + KEY_RESERVATION_END_TIME + " TEXT, " + KEY_RESERVATION_EMAIL
                + " TEXT, " + KEY_CLASS_ROOM + " TEXT, "
                + KEY_RESERVATION_PHONENO + " TEXT, " + KEY_NUMBER_OF_ATTENDES
                + " TEXT, " + KEY_FOOD_SERVICE_REQUESTED + " TEXT, "
                + KEY_SPECIAL_NEED + " TEXT, " + KEY_STORAGE_NEEDED + " TEXT, "
                + KEY_COMMENT + " TEXT, " + KEY_EQUIPMENT_REQUIRED + " TEXT, "
                + KEY_RESERVATION_DATE + " TEXT " + ")";
        db.execSQL(CREATE_RESERVATION_TABLE);


//        ***************************************************************
        String CREATE_TABLE_MYDEMO_RESERVATION_TABLE = "Create Table "
                + MY_DEMORESERVATION_TABLE + " ( " + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MY_DEMORESERVATION_TABLE_BUILDINGNAME
                + " TEXT, " + KEY_MY_DEMORESERVATION_TABLE_DESCRIPTION+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_EQUIPMENT+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_FLOORNAME+ " TEXT, " + KEY_MY_DEMORESERVATION_TABLE_FOODSERVICE
                + " TEXT, " + KEY_MY_DEMORESERVATION_TABLE_NUMBERFOFATTENDEES+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_PLANNEDDURATION+ " TEXT, " + KEY_MY_DEMORESERVATION_TABLE_REQUESTEDLAYOUT
                + " TEXT, " + KEY_MY_DEMORESERVATION_TABLE_SPACENAME+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_SPECIALNEED+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_STARTTIME+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_ENDTIME+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_DATE+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_RECORDID+ " TEXT, "

                + KEY_MY_DEMORESERVATION_TABLE_NETWORKCONNECTION+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_ADAAVAILABLE+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_IN_ROOM_PROJECTOR+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_WHITEBOARD+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_CITY+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_VIDEOCONFERENCE+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_CONFERENCENUMBER+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_ROOMPHONE+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_CATERINGAVAILABLE+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_CAPACITY+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_CONTROLNUMBER+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_RESERVATIONMANAGERTYPE+ " TEXT, "
                + KEY_MY_DEMORESERVATION_TABLE_STORAGE + " TEXT " + ")";
        db.execSQL(CREATE_TABLE_MYDEMO_RESERVATION_TABLE);






        String CREATE_TABLE_EQUIPMENT_TABLE = "Create Table "
                + MY_EQUIPMENT_TABLE + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MY_EQUIPMENT_TABLE_NAME+ " TEXT, "
                + KEY_MY_EQUIPMENT_TABLE_BUILDING+ " TEXT, "
                + KEY_MY_EQUIPMENT_TABLE_RESERVATION_NUMBER+ " TEXT, "
                + KEY_MY_EQUIPMENT_TABLE_STATUS+ " TEXT, "
                + KEY_MY_EQUIPMENT_TABLE_SYSTEM_RECORD_ID + " TEXT " + ")";
        db.execSQL(CREATE_TABLE_EQUIPMENT_TABLE);

        // ************************************************************************
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

    /*public ArrayList<String> getProperty() {

        propArrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select DISTINCT Property from " + TABLE_LOCATION;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                propArrayList.add(c.getString(c.getColumnIndex(KEY_PROPERTY)));
            } while (c.moveToNext());
            c.close();
        }


        return propArrayList;
    }*/

    // ************************************************************************************************

    public void deleteReservationRecord(myReservationClass objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(MY_DEMORESERVATION_TABLE, KEY_MY_DEMORESERVATION_TABLE_RECORDID+"=?",new String[]{objArrayList.getReservationId()} );


        db.close();
    }

    public void checkinReservationRecord(myReservationClass objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MY_DEMORESERVATION_TABLE_CHECKEDIN, "checkedin");
        db.update(MY_DEMORESERVATION_TABLE, contentValues, KEY_MY_DEMORESERVATION_TABLE_RECORDID + "=?", new String[]{objArrayList.getReservationId()});

        db.close();
    }




    public void saveLocation(ArrayList<MyLocationClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        MyLocationClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new MyLocationClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
//			values.put(KEY_LOCATION_ID, objClass.getLocationId());
            values.put(KEY_LOCATION_HIERARCHYPATH, objClass.getPath());
            values.put(KEY_LOCATION_NAME, objClass.getLocationName());
            values.put(KEY_LOCATION_DEFAULTLOCATION, objClass.getDefaultLocation());
            values.put(KEY_LOCATION_MYFLOORNAME, objClass.getFloor());
            values.put(KEY_LOCATION_MYFSPACENAME, objClass.getSpace());
            db.insert(MY_LOCATION_TABLE, null, values);
        }
        db.close();
    }

    public void saveFacilityData(ArrayList<FacilityClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        FacilityClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new FacilityClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
            values.put(KEY_FACILITY_GUI_ID, objClass.getGUI_ID());
            values.put(KEY_FACILITY_PARENT_TYPE, objClass.getParentType());
            values.put(KEY_FACILITY_REQUEST_CLASS, objClass.getRequestClass());
            String getParentData = objClass.getRequestType()/*.substring(2)*/;
            values.put(KEY_FACILITY_REQUEST_TYPE, getParentData);
            db.insert(MY_FACILITY_TABLE, null, values);
        }
        db.close();
    }

    public void savereservertationBuildingData(ArrayList<ReservationBuildingClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ReservationBuildingClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new ReservationBuildingClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
            values.put(KEY_RESERVATIONBUILDING_BUILDING_ID, objClass.getBuildingId());
            values.put(KEY_RESERVATIONBUILDING_BUILDING_NAME, objClass.getBuildingName());
            db.insert(MY_RESERVATIONBUILDING_TABLE, null, values);
        }
        db.close();
    }


    public void savereservertationSpaceData(ArrayList<ReservationSpaceClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ReservationSpaceClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new ReservationSpaceClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
            values.put(KEY_RESERVATIONSPACE_NAME, objClass.getSpace_name());
            values.put(KEY_RESERVATIONSPACE_STATUS, objClass.getSpace_status());
            values.put(KEY_RESERVATIONSPACE_PARENT_BUILDING, objClass.getSpace_parentBuilding());
            values.put(KEY_RESERVATIONSPACE_RESERVABLE, objClass.getSpace_reservable());
            values.put(KEY_RESERVATIONSPACE_ID, objClass.getSpace_id());
            values.put(KEY_RESERVATIONSPACE_HIERARCHYPATH, objClass.getSpace_hierarchyPath());

            values.put(KEY_RESERVATIONSPACE_RECORDID, objClass.getSpace_record_id());
            values.put(KEY_RESERVATIONSPACE_CAPACITY, objClass.getSpace_capacity());
            values.put(KEY_RESERVATIONSPACE_RESERVATIONCLASSNAME, objClass.getSpace_reservation_class_name());
            values.put(KEY_RESERVATIONSPACE_RESERVATIONNAME, objClass.getSpace_reservation_name());
            values.put(KEY_RESERVATIONSPACE_LAYOUTTYPE, objClass.getSpace_layout_type());
            values.put(KEY_RESERVATIONSPACE_ROOMTYPE, objClass.getSpace_room_type());

            db.insert(MY_RESERVATION_SPACE_TABLE, null, values);
        }
        db.close();
    }

    public void savereservertationEquipmentData(ArrayList<MyEquipmentClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        MyEquipmentClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new MyEquipmentClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
            values.put(KEY_MY_EQUIPMENT_TABLE_NAME, objClass.getName());
            values.put(KEY_MY_EQUIPMENT_TABLE_BUILDING, objClass.getBuilding_name());
            values.put(KEY_MY_EQUIPMENT_TABLE_RESERVATION_NUMBER, objClass.getReservation_number());
            values.put(KEY_MY_EQUIPMENT_TABLE_STATUS, objClass.getStatus());
            values.put(KEY_MY_EQUIPMENT_TABLE_SYSTEM_RECORD_ID, objClass.getSystem_record_id());
            db.insert(MY_EQUIPMENT_TABLE, null, values);
        }
        db.close();
    }
    public ArrayList<String> getReservationBuildingList() {
        Cursor c = null;
        ArrayList<String> buildingNameArrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_RESERVATIONBUILDING_TABLE,
                    new String[]{KEY_RESERVATIONBUILDING_BUILDING_NAME}, null, null, null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        if (c.moveToFirst()) {
            do {
                buildingNameArrayList.add(c.getString(c.getColumnIndex(KEY_RESERVATIONBUILDING_BUILDING_NAME)));
            } while (c.moveToNext());
            c.close();
        }
        return buildingNameArrayList;
    }



    public ArrayList<String> getReservationSpaceList_ViaBuildinhg(String building) {
        Cursor c = null;
        ArrayList<String> spaceNameArrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_RESERVATION_SPACE_TABLE,
                    new String[]{KEY_RESERVATIONSPACE_NAME}, KEY_RESERVATIONSPACE_PARENT_BUILDING +"=?",new String[]{building} , null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        if (c.moveToFirst()) {
            do {
                spaceNameArrayList.add(c.getString(c.getColumnIndex(KEY_RESERVATIONSPACE_NAME)));

            } while (c.moveToNext());
            c.close();
        }
        return spaceNameArrayList;
    }

    public String getReservationRecordId_ViaSpace(String space) {
        Cursor c = null;
        /*HashMap<String, String> stringHashMap = new HashMap<>();*/
        String spaceRecordId = "";

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_RESERVATION_SPACE_TABLE,
                    new String[]{KEY_RESERVATIONSPACE_NAME, KEY_RESERVATIONSPACE_RECORDID}, KEY_RESERVATIONSPACE_NAME +"=?",new String[]{space} , null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        if (c.moveToFirst()) {
            do {
//                stringHashMap.put(c.getString(c.getColumnIndex(KEY_RESERVATIONSPACE_NAME)),c.getString(c.getColumnIndex(KEY_RESERVATIONSPACE_RECORDID)));
                spaceRecordId = c.getString(c.getColumnIndex(KEY_RESERVATIONSPACE_RECORDID));
            } while (c.moveToNext());
            c.close();
        }
        return spaceRecordId;
    }

    public String getReservationRecordId_ViaEquipment(String Equipment) {
        Cursor c = null;
        /*HashMap<String, String> stringHashMap = new HashMap<>();*/
        String equipmentRecordId = "";

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_EQUIPMENT_TABLE,
                    new String[]{KEY_MY_EQUIPMENT_TABLE_NAME, KEY_MY_EQUIPMENT_TABLE_SYSTEM_RECORD_ID}, KEY_MY_EQUIPMENT_TABLE_NAME +"=?",new String[]{Equipment} , null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        if (c.moveToFirst()) {
            do {
//                stringHashMap.put(c.getString(c.getColumnIndex(KEY_RESERVATIONSPACE_NAME)),c.getString(c.getColumnIndex(KEY_RESERVATIONSPACE_RECORDID)));
                equipmentRecordId = c.getString(c.getColumnIndex(KEY_MY_EQUIPMENT_TABLE_SYSTEM_RECORD_ID));
            } while (c.moveToNext());
            c.close();
        }
        return equipmentRecordId;
    }

    public ArrayList<String> getReservationEquipmentList_ViaBuildinhg(String building) {
        /*Change this function.....remove empty building namae from the query*/
        Cursor c = null;
        ArrayList<String> spaceNameArrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_EQUIPMENT_TABLE,
                    new String[]{KEY_MY_EQUIPMENT_TABLE_NAME}, KEY_MY_EQUIPMENT_TABLE_BUILDING +"=? OR "+KEY_MY_EQUIPMENT_TABLE_BUILDING+"=?",new String[]{building,""} , null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        if (c.moveToFirst()) {
            do {
                spaceNameArrayList.add(c.getString(c.getColumnIndex(KEY_MY_EQUIPMENT_TABLE_NAME)));

            } while (c.moveToNext());
            c.close();
        }
        return spaceNameArrayList;
    }


    public String getDefaultLocation() {
        Cursor c = null;
        String loc = "";

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_LOCATION_TABLE,
                    new String[]{KEY_LOCATION_DEFAULTLOCATION}, null, null, null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        int i = 0;
        if (c.moveToFirst()) {
            do {
                loc = c.getString(c
                        .getColumnIndex(KEY_LOCATION_DEFAULTLOCATION));
                i = i + 1;
            } while (c.moveToNext());
            c.close();
        }
        return loc;
    }

    public String getDefaultOrganization() {
        Cursor c = null;
        String org = "";

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_ORG_TABLE,
                    new String[]{KEY_ORG_DEF_ORG}, null, null, null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        int i = 0;
        if (c.moveToFirst()) {
            do {
                org = c.getString(c
                        .getColumnIndex(KEY_ORG_DEF_ORG));
                i = i + 1;
            } while (c.moveToNext());
            c.close();
        }
        return org;
    }

    public ArrayList<String> fillRequestLists() {
        Cursor c = null;
        ArrayList<String> reqtypeArrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        /*String[] reqTypeStrings;*/

        try {
            /*c = db.query(MY_FACILITY_TABLE,
                    new String[]{KEY_FACILITY_REQUEST_TYPE}, null, null, null, null, null);*/

            c = db.query(MY_FACILITY_TABLE,
                    new String[]{KEY_FACILITY_REQUEST_TYPE}, KEY_FACILITY_PARENT_TYPE +"=?", new String[]{"1"}, null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        if (c.moveToFirst()) {
            do {
                /*reqTypeStrings = (c.getString(c.getColumnIndex(KEY_FACILITY_REQUEST_TYPE))).trim().split("\\.");*/
                reqtypeArrayList.add(c.getString(c.getColumnIndex(KEY_FACILITY_REQUEST_TYPE)));
            } while (c.moveToNext());
            c.close();
        }

        Collections.sort(reqtypeArrayList);
        return reqtypeArrayList;
    }

    public ArrayList<String> fillReservationLists() {
        Cursor c = null;
        ArrayList<String> reqtypeArrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        /*String[] reqTypeStrings;*/

        try {
            /*c = db.query(MY_FACILITY_TABLE,
                    new String[]{KEY_FACILITY_REQUEST_TYPE}, null, null, null, null, null);*/

            c = db.query(MY_FACILITY_TABLE,
                    new String[]{KEY_FACILITY_REQUEST_TYPE}, KEY_FACILITY_PARENT_TYPE +"=?", new String[]{"2"}, null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        if (c.moveToFirst()) {
            do {
                /*reqTypeStrings = (c.getString(c.getColumnIndex(KEY_FACILITY_REQUEST_TYPE))).trim().split("\\.");*/
                reqtypeArrayList.add(c.getString(c.getColumnIndex(KEY_FACILITY_REQUEST_TYPE)));
            } while (c.moveToNext());
            c.close();
        }

        if(reqtypeArrayList.size() == 0){
            reqtypeArrayList.add("Equipment Reservation");
            reqtypeArrayList.add("Vehicle Reservation");
            reqtypeArrayList.add("Room Reservation");
        }

       /* reqtypeArrayList.add("Today's reservation");
        reqtypeArrayList.add("Check in");*/
        /*reqtypeArrayList.add("Make a new reservation");
        reqtypeArrayList.add("Request a reservation");*/
        /*reqtypeArrayList.add("Cancel a reservation");
        reqtypeArrayList.add("My reservations");*/

       /* reqtypeArrayList.add("Cancel a reservation");
        reqtypeArrayList.add("Check in");
        reqtypeArrayList.add("Make a new reservation");
        reqtypeArrayList.add("My reservations");
        reqtypeArrayList.add("Request a reservation");
        reqtypeArrayList.add("Today's reservation");*/



        Collections.sort(reqtypeArrayList);
        return reqtypeArrayList;
    }


    public void updateLocationWithFloor(ArrayList<MyLocationClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        MyLocationClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new MyLocationClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
            values.put(KEY_LOCATION_HIERARCHYPATH, objClass.getPath());
            values.put(KEY_LOCATION_NAME, objClass.getLocationName());
            values.put(KEY_LOCATION_MYFLOORNAME, objClass.getFloor());
            values.put(KEY_MYFLOORPATH, objClass.getFloorpath());
            values.put(KEY_LOCATION_DEFAULTLOCATION, objClass.getDefaultLocation());
            values.put(KEY_FLOOR_AVAILABLE, "Y");
            try {
                db.insert(MY_LOCATION_TABLE, null, values);
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
        db.close();
    }

    public void updateLocationWithFloorAvailability(String building) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FLOOR_AVAILABLE, "N");
        db.update(MY_LOCATION_TABLE, values, KEY_LOCATION_NAME + "=?", new String[]{building});
        db.close();
    }


    public void updateLocationWithSpace(ArrayList<MyLocationClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        MyLocationClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new MyLocationClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();

            values.put(KEY_LOCATION_HIERARCHYPATH, objClass.getPath());
            values.put(KEY_LOCATION_NAME, objClass.getLocationName());
            values.put(KEY_LOCATION_MYFLOORNAME, objClass.getFloor());
            values.put(KEY_MYFLOORPATH, objClass.getFloorpath());
            values.put(KEY_LOCATION_MYFSPACENAME, objClass.getSpace());
            values.put(KEY_LOCATION_DEFAULTLOCATION, objClass.getDefaultLocation());
            values.put(KEY_SPACE_AVAILABLE, "Y");
            db.insert(MY_LOCATION_TABLE, null, values);
        }
        db.close();
    }

    public void updateLocationWithSpaceAvailability(String floor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SPACE_AVAILABLE, "N");
        /*db.update(MY_LOCATION_TABLE, values, KEY_FLOOR_NAME + "=?", new String[]{floor});*/
        db.update(MY_LOCATION_TABLE, values, KEY_LOCATION_MYFLOORNAME + "=?", new String[]{floor});
        db.close();
    }


    public void saveServiceType(ArrayList<ServiceClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ServiceClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new ServiceClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
            values.put(KEY_SERVICE_SERVICE_TYPE_RECORD_ID, objClass.getRecordId());
            values.put(KEY_SERVICE_SERVICE_NAME, objClass.getServiceName());
            values.put(KEY_SERVICE_HIERARCHY_PATH, objClass.getHierarchyPath());
            db.insert(MY_SERVICE_TABLE, null, values);
        }
        db.close();
    }

    public void saveOrg(ArrayList<OrgClass> objArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        OrgClass objClass;
        ContentValues values;
        for (int i = 0; i < objArrayList.size(); i++) {
            objClass = new OrgClass();
            objClass = objArrayList.get(i);
            values = new ContentValues();
            values.put(KEY_ORG_DEF_ORG, objClass.getDef_org());
            values.put(KEY_ORG_NAME, objClass.getOrg_organization_name());
            values.put(KEY_ORG_PATH, objClass.getOrg_organization_path());
            values.put(KEY_ORG_BUILDING, objClass.getOrg_building());
            values.put(KEY_ORG_FLOOR, objClass.getOrg_floor());
            values.put(KEY_ORG_SPACE, objClass.getOrg_space());
            values.put(KEY_ORG_LOCATION_LOOKUP, objClass.getOrg_location_lookup());
            values.put(KEY_ORG_ORGAINASTION_ID, objClass.getOrg_id());
            values.put(KEY_ORG_PROPERTY, objClass.getOrg_property());

            db.insert(MY_ORG_TABLE, null, values);
        }
        db.close();
    }


    public ArrayList<String> getBuildingWithId(String building) {

        ArrayList<String> buildingArrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_LOCATION_TABLE, new String[]{KEY_LOCATION_NAME}, KEY_LOCATION_NAME + "=?",
                new String[]{building}, null, null, null);
        if (c.moveToFirst()) {
            do {
                buildingArrayList.add(c.getString(c.getColumnIndex(KEY_LOCATION_NAME)));
            } while (c.moveToNext());
            c.close();
        }

        return buildingArrayList;
    }

    public ArrayList<String> getBuildingNames() {

        ArrayList<String> buildingArrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_LOCATION_TABLE,
                new String[]{KEY_LOCATION_NAME}, null, null, null, null,
                null);
        if (c.moveToFirst()) {
            do {
                buildingArrayList.add(c.getString(c
                        .getColumnIndex(KEY_LOCATION_NAME)));
            } while (c.moveToNext());
            c.close();
        }

        return buildingArrayList;
    }

    public String serviceId(String service) {

        String serviceId = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_SERVICE_TABLE,
                new String[]{KEY_SERVICE_SERVICE_TYPE_RECORD_ID}, KEY_SERVICE_SERVICE_NAME
                        + "=?", new String[]{service}, null, null, null);
        if (c.moveToFirst()) {
            do {
                serviceId = c.getString(c
                        .getColumnIndex(KEY_SERVICE_SERVICE_TYPE_RECORD_ID));
            } while (c.moveToNext());
            c.close();
        }

        return serviceId;
    }

    public JSONObject ServiceIdWithName_Parent(String building)
            throws JSONException {
        Cursor c = null;
        JSONObject jsonObject = new JSONObject();

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            c = db.query(MY_SERVICE_TABLE, null, KEY_SERVICE_SERVICE_NAME + "=?",
                    new String[]{building}, null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }

        if (c.moveToFirst()) {
            do {
                jsonObject.put("parentId", c.getString(c
                        .getColumnIndex(KEY_SERVICE_SERVICE_TYPE_RECORD_ID)));
                jsonObject.put("serviceName",
                        c.getString(c.getColumnIndex(KEY_SERVICE_SERVICE_NAME)));
                jsonObject.put("hierarchyPath",
                        c.getString(c.getColumnIndex(KEY_SERVICE_HIERARCHY_PATH)));
            } while (c.moveToNext());
            c.close();
        }

        return jsonObject;
    }


    public String getHierarchyPath1(String str) {

        String path = "";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(MY_SERVICE_TABLE,
                new String[]{KEY_SERVICE_HIERARCHY_PATH}, KEY_SERVICE_HIERARCHY_PATH
                        + " LIKE ?", new String[]{"%" + str}, null,
                null, null);
        if (c.moveToFirst()) {
            do {
                path = c.getString(c.getColumnIndex(KEY_SERVICE_HIERARCHY_PATH));
                break;
            } while (c.moveToNext());
            c.close();
        }


        return path;
    }

    public JSONObject getServiceId(String str) throws JSONException {
        Cursor c = null;
        JSONObject jsonObject = new JSONObject();

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            c = db.query(MY_SERVICE_TABLE,
                    new String[]{KEY_SERVICE_SERVICE_TYPE_RECORD_ID},
                    KEY_SERVICE_HIERARCHY_PATH + " LIKE ?", new String[]{"%" + str
                            + "%"}, null, null, null);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }
        int i = 0;
        if (c.moveToFirst()) {
            do {
                jsonObject.put("childId" + i, c.getString(c
                        .getColumnIndex(KEY_SERVICE_SERVICE_TYPE_RECORD_ID)));
                i = i + 1;
            } while (c.moveToNext());
            c.close();
        }

        return jsonObject;
    }


    public ArrayList<String> getService1(String serviceParent, String path) {
        ArrayList<String> spaceArrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_SERVICE_TABLE,
                new String[]{KEY_SERVICE_SERVICE_NAME}, KEY_SERVICE_SERVICE_NAME + "!=?"
                        + " AND " + KEY_SERVICE_HIERARCHY_PATH + " LIKE ?",
                new String[]{serviceParent, "%" + path + "%"}, null, null,
                KEY_SERVICE_SERVICE_NAME);

        if (c.moveToFirst()) {
            do {
                spaceArrayList.add((c.getString(c
                        .getColumnIndex(KEY_SERVICE_SERVICE_NAME))));
            } while (c.moveToNext());
            c.close();
        }

        Set<String> hs = new HashSet<String>();
        hs.addAll(spaceArrayList);
        spaceArrayList.clear();
        spaceArrayList.addAll(hs);
        return spaceArrayList;
    }

    public String getRequestClass(String requestType) {
        String reqType = "";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(MY_FACILITY_TABLE, new String[]{KEY_FACILITY_REQUEST_CLASS}, KEY_FACILITY_REQUEST_TYPE + "=?", new String[]{requestType}, null, null, null);

        if (c.moveToFirst()) {
            do {
                reqType = c.getString(c
                        .getColumnIndex(KEY_FACILITY_REQUEST_CLASS));
            } while (c.moveToNext());
            c.close();
        }


        return reqType.trim();
    }


    public String getGUIID(String requestType) {
        String GUI_ID = "";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(MY_FACILITY_TABLE, new String[]{KEY_FACILITY_GUI_ID}, KEY_FACILITY_REQUEST_TYPE + "=?", new String[]{requestType}, null, null, null);

        if (c.moveToFirst()) {
            do {
                GUI_ID = c.getString(c
                        .getColumnIndex(KEY_FACILITY_GUI_ID));
            } while (c.moveToNext());
            c.close();
        }

        return GUI_ID.trim();
    }

    public ArrayList<String> getOrg() {
        ArrayList<String> orgArrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_ORG_TABLE, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                orgArrayList.add((c.getString(c.getColumnIndex(KEY_ORG_NAME))));
            } while (c.moveToNext());
            c.close();
        }


        Set<String> hs = new HashSet<String>();
        hs.addAll(orgArrayList);
        orgArrayList.clear();
        orgArrayList.addAll(hs);
        return orgArrayList;
    }



    public ArrayList<String> getOrg_building(String building) {
        ArrayList<String> orgArrayList = new ArrayList<String>();
        Set<String> hs = new HashSet<String>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.query(MY_ORG_TABLE, null, KEY_ORG_BUILDING +"=?", new String[]{building}, null, null, null);

            if (c.moveToFirst()) {
                do {
                    orgArrayList.add((c.getString(c.getColumnIndex(KEY_ORG_NAME))));
                } while (c.moveToNext());
                c.close();
            }
            hs.addAll(orgArrayList);
            orgArrayList.clear();
        }catch (Exception e){

        }

        orgArrayList.addAll(hs);
        return orgArrayList;
    }


    // ***************************************************************************************

    public ArrayList<String> getBuilding() {

        ArrayList<String> buildingArrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select DISTINCT " + KEY_LOCATION_NAME + " from "
                + MY_LOCATION_TABLE;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                buildingArrayList.add(c.getString(c
                        .getColumnIndex(KEY_LOCATION_NAME)));
            } while (c.moveToNext());
            c.close();
        }
        buildingArrayList.removeAll(Collections.singleton(null));
        buildingArrayList.removeAll(Arrays.asList(null, ""));
        ArrayList<String> sorted_buildingArrayList = new ArrayList<String>();
        Collections.sort(buildingArrayList);
        for (String str : buildingArrayList) {
            sorted_buildingArrayList.add(str);
        }

        return sorted_buildingArrayList;
    }


    public ArrayList<String> getFloor(String building) {
        ArrayList<String> floorArrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(MY_LOCATION_TABLE, new String[]{" DISTINCT " + KEY_LOCATION_MYFLOORNAME}, KEY_LOCATION_NAME + "=?", new String[]{building}, null, null, null);

        if (c.moveToFirst()) {
            do {
                floorArrayList
                        .add(c.getString(c.getColumnIndex(KEY_LOCATION_MYFLOORNAME)));
            } while (c.moveToNext());
            c.close();
        }

        floorArrayList.removeAll(Collections.singleton(null));
        Collections.sort(floorArrayList);
        return floorArrayList;
    }


    public ArrayList<String> getFloorAvailability(String building) {
        ArrayList<String> floorAvailability = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(MY_LOCATION_TABLE, new String[]{" DISTINCT " + KEY_FLOOR_AVAILABLE}, KEY_LOCATION_NAME + "=?", new String[]{building}, null, null, null);

        if (c.moveToFirst()) {
            do {
                floorAvailability.add(c.getString(c.getColumnIndex(KEY_FLOOR_AVAILABLE)));
            } while (c.moveToNext());
            c.close();
        }


        floorAvailability.removeAll(Collections.singleton(null));
        return floorAvailability;
    }


    public String getHierarchyPathForFloor(String building) {
        String floorStrign = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(MY_LOCATION_TABLE, new String[]{KEY_LOCATION_HIERARCHYPATH}, KEY_LOCATION_NAME + "=?", new String[]{building}, null, null, null);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        if (c.moveToFirst()) {
            do {
                floorStrign = c.getString(c.getColumnIndex(KEY_LOCATION_HIERARCHYPATH));
            } while (c.moveToNext());
            c.close();
        }


        return floorStrign;
    }


    public String getHierarchyPathForSpace(String floor) {
        String floorStrign = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(MY_LOCATION_TABLE, new String[]{KEY_MYFLOORPATH}, KEY_LOCATION_MYFLOORNAME + "=?", new String[]{floor}, null, null, null);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        if (c.moveToFirst()) {
            do {
                floorStrign = c.getString(c.getColumnIndex(KEY_MYFLOORPATH));
            } while (c.moveToNext());
            c.close();
        }


        return floorStrign;
    }


    public int getRowCount(String Table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try{
            cursor = db.query(Table, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return cursor.getCount();
    }


    public void saveUserDetail(String _userName, String token, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, _userName);
        values.put(KEY_USER_TOKEN, token);
        values.put(KEY_USER_PASSWORD, password);
        db.insert(TABLE_USER, null, values);
        db.close();

    }

    public String[] getuserDetail() {
        String username = null;
        String password = null;
        String token = null;
        String[] userdeatail = new String[3];
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER;
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                username = c.getString(c
                        .getColumnIndex(DatabaseHandler.KEY_USER_NAME));
                password = c.getString(c
                        .getColumnIndex(DatabaseHandler.KEY_USER_PASSWORD));
                token = c
                        .getString(c.getColumnIndex(DatabaseHandler.KEY_USER_TOKEN));
                userdeatail[0] = username;
                userdeatail[1] = password;
                userdeatail[2] = token;

            } while (c.moveToNext());
            c.close();
        }


        return userdeatail;
    }


    public void emptyTable(String table_name, String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (key.equals("blank") && value.equals("blank")) {
                db.delete(table_name, null, null);
            } else {
                db.delete(table_name, key + "=?", new String[]{value});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }


   /* public void saveRequestRecord(MyRequestClass objClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_REQUESTDATA_REQUESTTYPE, objClass.getRequesttype());
        values.put(KEY_REQUESTDATA_REQUEST_LOCATION, objClass.getLocation());
        values.put(KEY_REQUESTDATA_ORGANIZATION, objClass.getOrganization());
        values.put(KEY_REQUESTDATA_SERVICE, objClass.getService());
        values.put(KEY_REQUESTDATA_REQUEST_DESCRIPTION, objClass.getDescription());
        values.put(KEY_IMAGE, objClass.getImagename());
        db.insert(TABLE_REQUESTDATA, null, values);
        db.close();

    }*/


    // *******************************************************************************

    public ArrayList<MyRequestClass> getFilteredRequestData_Old(/*String status, */String reqType) {
        Cursor c = null;
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MyRequestClass> objArraylist = new ArrayList<MyRequestClass>();
        @SuppressWarnings("unused")
        String orgId = null;
        MyRequestClass obj;
        /*String query = "SELECT *  FROM " + TABLE_REQUESTDATA;*/

       /* if (status.equals("All")) {
            System.out.println("Final Query=====>" + "SELECT * FROM " + TABLE_REQUESTDATA);
            i = i + 1;
            if (i == 0) {
                c.close();
            }
            try{
                c = db.query(TABLE_REQUESTDATA, null, KEY_REQUESTDATA_WORKORDERID+" !=?", new String[]{"N/A"}, null, null, null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }*/
       /* if (reqType.equals("All")) {
            System.out.println("Final Query=====>" + "SELECT * FROM " + TABLE_REQUESTDATA);
            i = i + 1;
            if (i == 0) {
                c.close();
            }
            c = db.query(TABLE_REQUESTDATA, null, KEY_REQUESTDATA_WORKORDERID+" !=?", new String[]{"N/A"}, null, null, null);
        }*/
        /*if ((*//*!status.equals("All") && *//*!reqType.equals("All"))) {*/
            System.out.println("Final Query=====>" + "SELECT * FROM " + TABLE_REQUESTDATA + " WHERE " /*+ KEY_REQUESTDATA_STATUS + "=" + status + " AND "*/ + KEY_REQUESTDATA_REQUESTTYPE + " LIKE " + '%'+reqType+'%');
            i = i + 1;
            if (i == 0) {
                c.close();
            }
            c = db.query(TABLE_REQUESTDATA, null, /*KEY_REQUESTDATA_STATUS + "=?" + " AND " + */KEY_REQUESTDATA_REQUESTTYPE + " LIKE ? "+ " AND "+ KEY_REQUESTDATA_WORKORDERID +" !=?", new String[]{/*status, */+'%'+reqType+"%", "N/A"}, null, null, null, null);
        /*}*/

       /* if (*//*(!status.equals("All") && *//*reqType.equals("All")) {
            *//*System.out.println("Final Query=====>" + "SELECT * FROM " + TABLE_REQUESTDATA + " WHERE " + KEY_REQUESTDATA_STATUS + "=" + status);*//*
            i = i + 1;
            if (i == 0) {
                c.close();
            }
            c = db.query(TABLE_REQUESTDATA, null, *//*KEY_REQUESTDATA_STATUS + "=?"+ " AND "+*//*KEY_REQUESTDATA_WORKORDERID+" !=?", new String[]{*//*status,*//*"N/A"}, null, null, null);
        }*/

        /*if (*//*(status.equals("All") && *//*!reqType.equals("All")) {
            System.out.println("Final Query=====>" + "SELECT * FROM " + TABLE_REQUESTDATA + " WHERE " + KEY_REQUESTDATA_REQUESTTYPE + "=" + reqType);
            i = i + 1;
            if (i == 0) {
                c.close();
            }
            c = db.query(TABLE_REQUESTDATA, null, KEY_REQUESTDATA_REQUESTTYPE + "=?"+" AND "+KEY_REQUESTDATA_WORKORDERID+" !=?", new String[]{reqType, "N/A"}, null, null, null);
        }*/

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    obj = new MyRequestClass();
                    obj.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    obj.setWOStatus(c.getString(c.getColumnIndex(KEY_REQUESTDATA_STATUS)));
                    obj.setWORequestID(c.getString(c.getColumnIndex(KEY_REQUESTDATA_WOREQUESTID)));
                    obj.setWorkOrderID(c.getString(c.getColumnIndex(KEY_REQUESTDATA_WORKORDERID)));
                    obj.setDescription(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUEST_DESCRIPTION)));
                    obj.setRequesttype(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUESTTYPE)));
                    obj.setLocation(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUEST_LOCATION)));
                    obj.setDuedate(c.getString(c.getColumnIndex(KEY_REQUESTDATA_DUEON)));
                    obj.setOrganization(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_ORGANIZATION)));
                    obj.setImagename(c.getString(c.getColumnIndex(KEY_IMAGE)));
                    obj.setService(c.getString(c.getColumnIndex(KEY_REQUESTDATA_SERVICE)));

                    objArraylist.add(obj);
                } while (c.moveToNext());
                c.close();
            }
        }

        return objArraylist;
    }






    public ArrayList<MyRequestClass> getFilteredRequestData(String reqType) {
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MyRequestClass> objArraylist = new ArrayList<MyRequestClass>();
        @SuppressWarnings("unused")
        String orgId = null;
        MyRequestClass obj;


        /*c = db.query(TABLE_REQUESTDATA, null, KEY_REQUESTDATA_REQUESTTYPE + " LIKE? "+ " AND "+KEY_REQUESTDATA_WORKORDERID+" !=?", new String[]{reqType+"%", "N/A"}, null, null, null, null);*/

        String reqtype = "%"+reqType+"%";
        String[] splitReqType= reqType.split(" ");


        if(splitReqType[0].equals("Temperature")) {
            c = db.query(TABLE_REQUESTDATA, null, KEY_REQUESTDATA_WORKORDERID + " !=? " + " AND " + KEY_REQUESTDATA_REQUESTTYPE + " =?", new String[]{"N/A", "Heating & AC"}, null, null, null, null);
            System.out.println("Final Query=====>" + "SELECT * FROM " + TABLE_REQUESTDATA + " WHERE " + KEY_REQUESTDATA_REQUESTTYPE + " = " +"Heating & AC");
        }else {
            c = db.query(TABLE_REQUESTDATA, null, KEY_REQUESTDATA_WORKORDERID + " !=? " + " AND " + KEY_REQUESTDATA_REQUESTTYPE + " LIKE?", new String[]{"N/A", splitReqType[0] + "%"}, null, null, null, null);
            System.out.println("Final Query=====>" + "SELECT * FROM " + TABLE_REQUESTDATA + " WHERE " + KEY_REQUESTDATA_REQUESTTYPE + " LIKE " +splitReqType[0]+"%");
        }


        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    obj = new MyRequestClass();
                    obj.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    obj.setWOStatus(c.getString(c.getColumnIndex(KEY_REQUESTDATA_STATUS)));
                    obj.setWORequestID(c.getString(c.getColumnIndex(KEY_REQUESTDATA_WOREQUESTID)));
                    obj.setWorkOrderID(c.getString(c.getColumnIndex(KEY_REQUESTDATA_WORKORDERID)));
                    obj.setDescription(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUEST_DESCRIPTION)));
                    obj.setRequesttype(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUESTTYPE)));
                    obj.setLocation(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUEST_LOCATION)));
                    obj.setDuedate(c.getString(c.getColumnIndex(KEY_REQUESTDATA_DUEON)));
                    obj.setOrganization(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_ORGANIZATION)));
                    obj.setImagename(c.getString(c.getColumnIndex(KEY_IMAGE)));
                    obj.setService(c.getString(c.getColumnIndex(KEY_REQUESTDATA_SERVICE)));

                    objArraylist.add(obj);
                } while (c.moveToNext());
                c.close();
            }
        }

        return objArraylist;
    }


    public ArrayList<MyRequestClass> getUnFilteredRequestData() {
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<MyRequestClass> objArraylist = new ArrayList<MyRequestClass>();
        @SuppressWarnings("unused")
        String orgId = null;
        MyRequestClass obj;

        System.out.println("Query ===> " + "SELECT * FROM " + TABLE_REQUESTDATA +" ORDER BY "+KEY_REQUESTDATA_REQUESTTYPE);
        c = db.query(TABLE_REQUESTDATA, null, null, null, null, null, KEY_REQUESTDATA_DUEON);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    obj = new MyRequestClass();
                    obj.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    obj.setWOStatus(c.getString(c.getColumnIndex(KEY_REQUESTDATA_STATUS)));
                    obj.setWORequestID(c.getString(c.getColumnIndex(KEY_REQUESTDATA_WOREQUESTID)));
                    obj.setWorkOrderID(c.getString(c.getColumnIndex(KEY_REQUESTDATA_WORKORDERID)));
                    obj.setDescription(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUEST_DESCRIPTION)));
                    obj.setRequesttype(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUESTTYPE)));
                    obj.setLocation(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_REQUEST_LOCATION)));
                    obj.setDuedate(c.getString(c.getColumnIndex(KEY_REQUESTDATA_DUEON)));
                    obj.setOrganization(c.getString(c
                            .getColumnIndex(KEY_REQUESTDATA_ORGANIZATION)));
                    obj.setImagename(c.getString(c.getColumnIndex(KEY_IMAGE)));
                    obj.setService(c.getString(c.getColumnIndex(KEY_REQUESTDATA_SERVICE)));

                    objArraylist.add(obj);
                } while (c.moveToNext());
                c.close();
            }
        }

        return objArraylist;
    }

    // *******************************************************************************

    public ArrayList<String> getSpacenew(String floor, String building) {

        ArrayList<String> spaceArrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(MY_LOCATION_TABLE, new String[]{" DISTINCT " + KEY_LOCATION_MYFSPACENAME}, KEY_LOCATION_MYFLOORNAME + "=?" + " AND " + KEY_LOCATION_NAME + "=?", new String[]{floor, building}, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                spaceArrayList
                        .add(c.getString(c.getColumnIndex(KEY_LOCATION_MYFSPACENAME)));
            } while (c.moveToNext());
        }
        c.close();

        System.out.println("=====>>>>>>>" + spaceArrayList);
        spaceArrayList.removeAll(Collections.singleton(null));
        Collections.sort(spaceArrayList);
        return spaceArrayList;

    }

    public ArrayList<String> getSpaceAvailability(String floor, String building) {
        ArrayList<String> floorAvailability = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(MY_LOCATION_TABLE, new String[]{" DISTINCT " + KEY_SPACE_AVAILABLE}, KEY_LOCATION_MYFLOORNAME + "=?" + " AND " + KEY_LOCATION_NAME + "=?", new String[]{floor, building}, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                floorAvailability.add(c.getString(c.getColumnIndex(KEY_SPACE_AVAILABLE)));
            } while (c.moveToNext());
            c.close();
        }

        floorAvailability.removeAll(Collections.singleton(null));
        return floorAvailability;
    }


    public int getRowCount(String tABLE_RESERVED_SPACE2, String get_spaceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tABLE_RESERVED_SPACE2 + " WHERE "
                + KEY_SPACE_ID + " = " + get_spaceId;
        Cursor c = db.rawQuery(query, null);
        int i = c.getCount();
        c.close();
        return i;
    }


    public void addDownloadedRequest(Map<String, String> map) {
        SQLiteDatabase db = this.getWritableDatabase();
        Set<String> set = map.keySet();
        ContentValues contentValues = new ContentValues();
        Iterator<String> iter = set.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if (key.equals("Location Requested For") && map.get(key).equals("")) {
                contentValues.put(KEY_REQUESTDATA_REQUEST_LOCATION, "N/A");
            }
            if (key.equals("Location Requested For")
                    && !map.get(key).equals("")) {
                contentValues.put(KEY_REQUESTDATA_REQUEST_LOCATION, map.get(key));
            }
            if (key.equals("Organization")) {
                contentValues.put(KEY_REQUESTDATA_ORGANIZATION, map.get(key));
            }
            if (key.equals("Request Type")) {
                contentValues.put(KEY_REQUESTDATA_REQUESTTYPE, map.get(key));
            }
            if (key.equals("Description")) {
                contentValues.put(KEY_REQUESTDATA_REQUEST_DESCRIPTION, map.get(key));
            }
            if (key.equals("Status")) {
                contentValues.put(KEY_REQUESTDATA_STATUS, map.get(key));
            }
            if (key.equals("Request ID")) {
                contentValues.put(KEY_REQUESTDATA_WOREQUESTID, map.get(key));
            }
            if (key.equals("Due On")) {
                contentValues.put(KEY_REQUESTDATA_DUEON, map.get(key));
            }
            if (key.equals("Work Order ID")) {
                contentValues.put(KEY_REQUESTDATA_WORKORDERID, map.get(key));
            }
            if (key.equals("Service Requested")) {
                contentValues.put(KEY_REQUESTDATA_SERVICE, map.get(key));
            }
        }

        try {
            db.insert(TABLE_REQUESTDATA, null, contentValues);
            System.out.println("Inserted");
        } catch (Exception exception) {
            exception.getLocalizedMessage();
            exception.printStackTrace();
            System.out.println("Not Inserted");
        }
    }



    public ArrayList<String> show(String building) {
        ArrayList<String> strings = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;

        try {
            c = db.query(MY_LOCATION_TABLE, new String[]{"DISTINCT " + KEY_LOCATION_MYFLOORNAME}, KEY_LOCATION_NAME + "=?", new String[]{building}, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (c.moveToFirst()) {
            do {
                strings.add(c.getString(c.getColumnIndex(KEY_LOCATION_MYFLOORNAME)));
            } while (c.moveToNext());
            c.close();
        }

        c.close();
        System.out.println("\nFloors Data in the local database: >>>>>>>>" + strings);
        return strings;
    }

    public ArrayList<String> getStatus() {
        ArrayList<String> status = new ArrayList<String>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(TABLE_REQUESTDATA, new String[]{" DISTINCT " + KEY_REQUESTDATA_STATUS}, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                status.add(c.getString(c.getColumnIndex(KEY_REQUESTDATA_STATUS)));
            } while (c.moveToNext());
            c.close();
        }
        return status;
    }

    public ArrayList<String> getReqType() {
        ArrayList<String> reqtype = new ArrayList<String>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(TABLE_REQUESTDATA, new String[]{" DISTINCT " + KEY_REQUESTDATA_REQUESTTYPE}, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                reqtype.add(c.getString(c.getColumnIndex(KEY_REQUESTDATA_REQUESTTYPE)));
            } while (c.moveToNext());
            c.close();
        }
        return reqtype;
    }


    public void saveDemoReservationData(ArrayList<myReservationClass> myReservationClassArrayList){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try{
            for(int i = 0; i<myReservationClassArrayList.size(); i++){
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_BUILDINGNAME, myReservationClassArrayList.get(i).getBuildingname());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_DESCRIPTION, myReservationClassArrayList.get(i).getDescription());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_EQUIPMENT, myReservationClassArrayList.get(i).getEquipment());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_FLOORNAME, myReservationClassArrayList.get(i).getFloorname());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_FOODSERVICE, myReservationClassArrayList.get(i).getFoodservice());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_NUMBERFOFATTENDEES, myReservationClassArrayList.get(i).getNumberofattendees());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_PLANNEDDURATION, myReservationClassArrayList.get(i).getPlannedduration());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_REQUESTEDLAYOUT, myReservationClassArrayList.get(i).getRequestedlayout());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_SPACENAME, myReservationClassArrayList.get(i).getSpacename());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_SPECIALNEED, myReservationClassArrayList.get(i).getSpecialneed());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_STORAGE, myReservationClassArrayList.get(i).getStorage());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_DATE, myReservationClassArrayList.get(i).getDate());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_STARTTIME, myReservationClassArrayList.get(i).getStarttime());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_ENDTIME, myReservationClassArrayList.get(i).getEndtime());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_RECORDID, myReservationClassArrayList.get(i).getReservationId());

                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_NETWORKCONNECTION, myReservationClassArrayList.get(i).getNetwork_connection());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_ADAAVAILABLE, myReservationClassArrayList.get(i).getAda_available());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_IN_ROOM_PROJECTOR, myReservationClassArrayList.get(i).getIn_room_projector());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_WHITEBOARD, myReservationClassArrayList.get(i).getWhiteboard());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_CITY, myReservationClassArrayList.get(i).getCity());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_VIDEOCONFERENCE, myReservationClassArrayList.get(i).getVideo_conference());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_RESERVATIONMANAGERTYPE, myReservationClassArrayList.get(i).getReservation_manager_type());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_ROOMPHONE, myReservationClassArrayList.get(i).getRoom_phone());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_CAPACITY, myReservationClassArrayList.get(i).getCapacity());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_CATERINGAVAILABLE, myReservationClassArrayList.get(i).getCatering_available());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_CONTROLNUMBER, myReservationClassArrayList.get(i).getControl_number());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_CHECKEDIN, myReservationClassArrayList.get(i).getCheckedin());
                /*contentValues.put(KEY_MY_DEMORESERVATION_TABLE_ROOM_TYPE, myReservationClassArrayList.get(i).getReservationId());
                contentValues.put(KEY_MY_DEMORESERVATION_TABLE_TELECONFERENCEPHONE, myReservationClassArrayList.get(i).getReservationId());*/




                db.insert(MY_DEMORESERVATION_TABLE, null, contentValues);
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<myReservationClass> getDemoReservationData() {
        ArrayList<myReservationClass> myReservationClassesArrayList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.query(MY_DEMORESERVATION_TABLE, null, null, null, null, null, null);
        /*Cursor c = database.query(MY_DEMORESERVATION_TABLE, null, KEY_MY_DEMORESERVATION_TABLE_DELETEDTAG+"!=?", new String[]{"deleted"}, null, null, null);*/
        if (c.moveToFirst()) {
            do {
                myReservationClass myReservationClass = new myReservationClass();
                myReservationClass.setDate(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_DATE)));
                myReservationClass.setBuildingname(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_BUILDINGNAME)));
                myReservationClass.setDescription(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_DESCRIPTION)));
                myReservationClass.setEquipment(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_EQUIPMENT)));
                myReservationClass.setFloorname(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_FLOORNAME)));
                myReservationClass.setFoodservice(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_FOODSERVICE)));
                myReservationClass.setNumberofattendees(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_NUMBERFOFATTENDEES)));
                myReservationClass.setPlannedduration(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_PLANNEDDURATION)));
                myReservationClass.setRequestedlayout(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_REQUESTEDLAYOUT)));
                myReservationClass.setSpacename(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_SPACENAME)));
                myReservationClass.setSpecialneed(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_SPECIALNEED)));
                myReservationClass.setStorage(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_STORAGE)));
                myReservationClass.setStarttime(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_STARTTIME)));
                myReservationClass.setEndtime(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ENDTIME)));
                myReservationClass.setReservationId(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_RECORDID)));


                myReservationClass.setNetwork_connection(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_NETWORKCONNECTION)));
                myReservationClass.setAda_available(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ADAAVAILABLE)));
                /*myReservationClass.setIn_room_projector(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_IN_ROOM_PROJECTOR)));*/
                myReservationClass.setWhiteboard(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_WHITEBOARD)));
                myReservationClass.setCity(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CITY)));
                myReservationClass.setVideo_conference(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_VIDEOCONFERENCE)));
                /*myReservationClass.setReservationId(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ROOM_TYPE)));*/
                myReservationClass.setReservation_manager_type(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_RESERVATIONMANAGERTYPE)));
                /*myReservationClass.setReservationId(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_TELECONFERENCEPHONE)));*/
                myReservationClass.setRoom_phone(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ROOMPHONE)));
                myReservationClass.setCapacity(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CAPACITY)));
                myReservationClass.setCatering_available(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CATERINGAVAILABLE)));
                myReservationClass.setControl_number(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CONTROLNUMBER)));
                myReservationClass.setCheckedin(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CHECKEDIN)));

                if(!myReservationClass.getDate().equals(""))
                    myReservationClassesArrayList.add(myReservationClass);

            } while (c.moveToNext());
            c.close();
        }
        return myReservationClassesArrayList;
    }

    public ArrayList<myReservationClass> getDemoReservationDataForParticularDay(String date) {
        ArrayList<myReservationClass> myReservationClassesArrayList = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        /*Cursor c = database.query(MY_DEMORESERVATION_TABLE, null, null, null, null, null, null);*/
        Cursor c = database.query(MY_DEMORESERVATION_TABLE, null, KEY_MY_DEMORESERVATION_TABLE_DATE+"=?", new String[]{date}, null, null, null);
        /*Cursor c = database.query(MY_DEMORESERVATION_TABLE, null, KEY_MY_DEMORESERVATION_TABLE_DELETEDTAG+"!=?", new String[]{"deleted"}, null, null, null);*/
        if (c.moveToFirst()) {
            do {
                myReservationClass myReservationClass = new myReservationClass();
                myReservationClass.setDate(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_DATE)));
                myReservationClass.setBuildingname(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_BUILDINGNAME)));
                myReservationClass.setDescription(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_DESCRIPTION)));
                myReservationClass.setEquipment(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_EQUIPMENT)));
                myReservationClass.setFloorname(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_FLOORNAME)));
                myReservationClass.setFoodservice(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_FOODSERVICE)));
                myReservationClass.setNumberofattendees(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_NUMBERFOFATTENDEES)));
                myReservationClass.setPlannedduration(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_PLANNEDDURATION)));
                myReservationClass.setRequestedlayout(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_REQUESTEDLAYOUT)));
                myReservationClass.setSpacename(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_SPACENAME)));
                myReservationClass.setSpecialneed(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_SPECIALNEED)));
                myReservationClass.setStorage(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_STORAGE)));
                myReservationClass.setStarttime(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_STARTTIME)));
                myReservationClass.setEndtime(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ENDTIME)));
                myReservationClass.setReservationId(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_RECORDID)));
                myReservationClass.setNetwork_connection(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_NETWORKCONNECTION)));
                myReservationClass.setAda_available(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ADAAVAILABLE)));
                /*myReservationClass.setIn_room_projector(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_IN_ROOM_PROJECTOR)));*/
                myReservationClass.setWhiteboard(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_WHITEBOARD)));
                myReservationClass.setCity(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CITY)));
                myReservationClass.setVideo_conference(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_VIDEOCONFERENCE)));
                /*myReservationClass.setReservationId(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ROOM_TYPE)));*/
                myReservationClass.setReservation_manager_type(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_RESERVATIONMANAGERTYPE)));
                /*myReservationClass.setReservationId(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_TELECONFERENCEPHONE)));*/
                myReservationClass.setRoom_phone(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_ROOMPHONE)));
                myReservationClass.setCapacity(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CAPACITY)));
                myReservationClass.setCatering_available(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CATERINGAVAILABLE)));
                myReservationClass.setControl_number(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CONTROLNUMBER)));
                myReservationClass.setCheckedin(c.getString(c.getColumnIndex(KEY_MY_DEMORESERVATION_TABLE_CHECKEDIN)));

                if(!myReservationClass.getDate().equals("")){
                    myReservationClassesArrayList.add(myReservationClass);
                }


            } while (c.moveToNext());
            c.close();
        }
        return myReservationClassesArrayList;
    }
}


            /*
            *****************Code to send/upload image to server in JSON format*********************
            */

					/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
					byte[] imageBytes = baos.toByteArray();
					String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/
