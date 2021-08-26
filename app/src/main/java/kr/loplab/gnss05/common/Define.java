package kr.loplab.gnss05.common;

import java.util.ArrayList;

public class Define {
    //Common String
    public static final String REQUEST_CODE_STRING = "requestCode";
    public static final String APNS_SELECTED_INDEX = "apns_selected_index";
    public static final String CORS_SELECTED_INDEX = "cors_selected_index";
    //Request Code
    public static final int REQUEST_SETTING = 1001;
    public static final int REQUEST_WORKER_MANAGE_ADD = 1002;
    public static final int REQUEST_WORKER_MANAGE_EDIT = 1003;
    public static final int REQUEST_SERVER_MANAGE_ADD = 1004;
    public static final int REQUEST_SERVER_MANAGE_EDIT = 1005;
    public static final int REQUEST_WORKMANAGER = 1006;
    public static final int REQUEST_CORS_SERVER_MANAGER = 1007;

    //SharedPreference Code
    public static final String RECYCLERVIEW_LIST_MODE = "recyclerview_mode";
    public static final String EQUIPMENT_MAKER = "equipment_maker";
    public static final String CONNECT_MODE = "connect_mode";
    public static final String START_MODE = "start_mode";
    public static final String DEPLACEMENT_MODE = "deplacement_mode";
    public static final String COLLECTION_INTERVAL = "collection_interval";
    public static final String DATA_CONNECTION_TYPE = "data_connection_type";
    public static final String REFERENCE_COUNTRY_AUTO_PLAY = "reference_country_auto_play";
    public static final String RAW_DATA_SAVE = "row_data_save";
    public static final String NETWORK_AUTO_CONNECT = "network_auto_connect";
    public static final String NETWORK_MODE = "network_mode";
    public static final String NETWORK_SYSTEM = "network_system";
    public static final String INNER_RADIO_CHANNEL = "inner_radio_channel";
    public static final String INNER_RADIO_PROTOCOL = "inner_radio_protocol";
    public static final String INNER_RADIO_INTERVAL = "inner_radio_interval";
    public static final String INNER_RADIO_FEC = "inner_radio_fec";
    public static final String INNER_RADIO_POWER = "inner_radio_power";
    public static final String COMMUNICATION_SPEED = "communication_speed";
    public static final String AUTO_APN = "auto_apn";
    public static final String APN_INDEX_NUM = "apn_index_num";
    public static final String CORS_INDEX_NUM = "cors_index_num";

    //SharedPreference Code REFERENCE_COUNTRY_COORDINATE_SETTING
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_NAME = "reference_country_coordinate_setting_name";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_CODE= "reference_country_coordinate_setting_code";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_COORDINATE_TYPE= "reference_country_coordinate_setting_coordinate_type";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_X= "reference_country_coordinate_setting_X";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_Y= "reference_country_coordinate_setting_Y";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_LEVEL= "reference_country_coordinate_setting_LEVEL";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_POLE_HEIGHT= "reference_country_coordinate_setting_pole_height";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_HEIGHT_CALCULATION= "reference_country_coordinate_setting_height_calculation";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_CALCULATED_HEIGHT= "reference_country_coordinate_setting_calculated_height";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_ANTENNA_TYPE= "reference_country_coordinate_setting_antenna_type";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_R= "reference_country_coordinate_setting_r";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_H= "reference_country_coordinate_setting_h";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_HL1= "reference_country_coordinate_setting_hl1";
    public static final String REFERENCE_COUNTRY_COORDINATE_SETTING_HL2= "reference_country_coordinate_setting_hl2";

    //SharedPreference Code REFERENCE_COUNTRY_SATELLITE
    public static final String REFERENCE_COUNTRY_SATELLITE_CUT_ANGLE= "reference_country_satellite_cut_angle";
    public static final String REFERENCE_COUNTRY_SATELLITE_PDOP_LIMIT= "reference_country_satellite_pdop_limit";
    public static final String REFERENCE_COUNTRY_SATELLITE_DELAY= "reference_country_satellite_delay";
    public static final String REFERENCE_COUNTRY_SATELLITE_GPS= "reference_country_satellite_gps";
    public static final String REFERENCE_COUNTRY_SATELLITE_GLONASS= "reference_country_satellite_glonass";
    public static final String REFERENCE_COUNTRY_SATELLITE_BEIDOU= "reference_country_satellite_beidou";
    public static final String REFERENCE_COUNTRY_SATELLITE_GALILEO= "reference_country_satellite_galileo";
    public static final String REFERENCE_COUNTRY_SATELLITE_SBAS= "reference_country_satellite_sbas";
    public static final String REFERENCE_COUNTRY_SATELLITE_QZSS= "reference_country_satellite_qzss";
    public static final String REFERENCE_COUNTRY_SATELLITE_LBAND= "reference_country_satellite_lband";


    //ROOM
    public static final String WORKERS_DB = "workers-db";
    public static final String SERVERS_DB = "servers-db";
}
