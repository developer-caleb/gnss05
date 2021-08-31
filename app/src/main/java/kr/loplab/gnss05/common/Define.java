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


    public static final String RECYCLERVIEW_LIST_MODE = "recyclerview_mode";
    public static final String EQUIPMENT_MAKER = "equipment_maker";
    public static final String CONNECT_MODE = "connect_mode";


    //SharedPreference Code REFERENCE_COUNTRY
    public static final String REFERENCE_COUNTRY_START_MODE = "reference_country_start_mode";
    public static final String REFERENCE_COUNTRY_DEPLACEMENT_MODE = "reference_country_deplacement_mode";
    public static final String REFERENCE_COUNTRY_COLLECTION_INTERVAL = "reference_country_collection_interval";
    public static final String REFERENCE_COUNTRY_DATA_CONNECTION_TYPE = "reference_country_data_connection_type";
    public static final String REFERENCE_COUNTRY_AUTO_PLAY = "reference_country_reference_country_auto_play";
    public static final String REFERENCE_COUNTRY_RAW_DATA_SAVE = "reference_country_row_data_save";
    public static final String REFERENCE_COUNTRY_NETWORK_AUTO_CONNECT = "reference_country_network_auto_connect";
    public static final String REFERENCE_COUNTRY_NETWORK_MODE = "reference_country_network_mode";
    public static final String REFERENCE_COUNTRY_NETWORK_SYSTEM = "reference_country_network_system";
    public static final String REFERENCE_COUNTRY_INNER_RADIO_CHANNEL = "reference_country_inner_radio_channel";
    public static final String REFERENCE_COUNTRY_INNER_RADIO_PROTOCOL = "reference_country_inner_radio_protocol";
    public static final String REFERENCE_COUNTRY_INNER_RADIO_INTERVAL = "reference_country_inner_radio_interval";
    public static final String REFERENCE_COUNTRY_INNER_RADIO_FEC = "reference_country_inner_radio_fec";
    public static final String REFERENCE_COUNTRY_INNER_RADIO_POWER = "reference_country_inner_radio_power";
    public static final String REFERENCE_COUNTRY_OUTERRADIOCOMMUNICATION_SPEED = "reference_country_outer_radio_communication_speed";
    public static final String DUALCOMMUNICATION_SPEED = "reference_country_dual_communication_speed";
    public static final String REFERENCE_COUNTRY_AUTO_APN = "reference_country_auto_apn";
    public static final String REFERENCE_COUNTRY_APN_INDEX_NUM = "reference_country_apn_index_num";
    public static final String REFERENCE_COUNTRY_CORS_INDEX_NUM = "reference_country_cors_index_num";



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

    //SharedPreference Code MOBILE STATION
    public static final String MOBILE_STATION_CUT_ANGLE= "mobile_station_cut_angle";
    public static final String MOBILE_STATION_COLLECTION_INTERVAL = "mobile_station_collection_interval";
    public static final String MOBILE_STATION_RAW_DATA_SAVE = "mobile_station_row_data_save";
    public static final String MOBILE_STATION_DATA_CONNECTION_TYPE = "mobile_station_data_connection_type";
    public static final String MOBILE_STATION_INNER_RADIO_CHANNEL = "mobile_station_inner_radio_channel";
    public static final String MOBILE_STATION_RADIO_MODE_CHANNEL = "mobile_station_radio_mode_channel";

    public static final String MOBILE_STATION_INNER_RADIO_PROTOCOL = "mobile_station_inner_radio_protocol";
    public static final String MOBILE_STATION_RADIO_MODE_PROTOCOL = "mobile_station_radio_mode_protocol";

    public static final String MOBILE_STATION_INNER_RADIO_INTERVAL = "mobile_station_inner_radio_interval";
    public static final String MOBILE_STATION_INNER_RADIO_FEC = "mobile_station_inner_radio_fec";
    public static final String MOBILE_STATION_INNER_RADIO_POWER = "mobile_station_inner_radio_power";
    public static final String MOBILE_STATION_RADIO_MODE_POWER = "mobile_station_radio_mode_power";

    public static final String MOBILE_STATION_OUTERRADIOCOMMUNICATION_SPEED = "mobile_station_outer_radio_communication_speed";
    public static final String MOBILE_STATION_GGA_UPLOAD_INTERVAL = "mobile_station_gga_upload_interval";
    public static final String MOBILE_STATION_NETWORK_AUTO_CONNECT = "mobile_station_network_auto_connect";
    public static final String MOBILE_STATION_NETWORK_SYSTEM = "mobile_station_network_system";
    public static final String MOBILE_STATION_NETWORK_TRANSFER = "mobile_station_network_transfer";
    public static final String MOBILE_STATION_AUTO_APN = "mobile_station_auto_apn";
    public static final String MOBILE_STATION_MOUNT_POINT = "mobile_station_mount_point";
    public static final String MOBILE_STATION_MOUNT_SORT = "mobile_station_mount_sort";



    //SharedPreference Code MOBILE STATION_SATELLITE
    public static final String MOBILE_STATION_SATELLITE_GPS= "mobile_station_satellite_gps";
    public static final String MOBILE_STATION_SATELLITE_GLONASS= "mobile_station_satellite_glonass";
    public static final String MOBILE_STATION_SATELLITE_BEIDOU= "mobile_station_satellite_beidou";
    public static final String MOBILE_STATION_SATELLITE_GALILEO= "mobile_station_satellite_galileo";
    public static final String MOBILE_STATION_SATELLITE_SBAS= "mobile_station_satellite_sbas";
    public static final String MOBILE_STATION_SATELLITE_QZSS= "mobile_station_satellite_qzss";

    //SharedPreference Code STOP SURVEY
    public static final String STOP_SURVEY_PDOP_LIMIT= "stop_survey_pdop_limit";
    public static final String STOP_SURVEY_CUT_ANGLE= "stop_survey_cut_angle";
    public static final String STOP_SURVEY_COLLECTION_INTERVAL = "stop_survey_collection_interval";
    public static final String STOP_SURVEY_AUTO_SAVE = "stop_survey_auto_save";
    public static final String STOP_SURVEY_POLE_HEIGHT = "stop_survey_pole_height";
    public static final String STOP_SURVEY_HEIGHT_CALC_METHOD = "stop_survey_height_calc_method";




    //SharedPreference Code STOP SURVEY_SATELLITE
    public static final String STOP_SURVEY_SATELLITE_GPS =       "stop_survey_satellite_gps";
    public static final String STOP_SURVEY_SATELLITE_GLONASS =   "stop_survey_satellite_glonass";
    public static final String STOP_SURVEY_SATELLITE_BEIDOU =    "stop_survey_satellite_beidou";
    public static final String STOP_SURVEY_SATELLITE_GALILEO =   "stop_survey_satellite_galileo";
    public static final String STOP_SURVEY_SATELLITE_SBAS =      "stop_survey_satellite_sbas";
    public static final String STOP_SURVEY_SATELLITE_QZSS =      "stop_survey_satellite_qzss";

    //SharedPreference Code Coordinate
    public static final String COORDINATE_ELLIPSOIDNAME = "coordinate_ellipsoid_name";
    public static final String COORDINATE_ITRFCONVERSION = "coordinate_itrf_conversion";
    public static final String COORDINATE_CONVERSION_TYPE = "coordinate_itrf_conversion_type";
    public static final String COORDINATE_COORDINATE_NAME = "coordinate_coordinate_name";
    public static final String COORDINATE_NEW_TARGET = "coordinate_new_target";
    public static final String COORDINATE_INPUT_SPEED = "coordinate_input_speed";
    public static final String COORDINATE_VX = "coordinate_coordinate_VX";
    public static final String COORDINATE_VY = "coordinate_coordinate_VY";
    public static final String COORDINATE_VZ = "coordinate_coordinate_VZ";
    public static final String COORDINATE_SEVEN_PARAMETER_USING = "coordinate_seven_parameter_using";
    public static final String COORDINATE_SEVEN_PARAMETER_MODE = "coordinate_seven_parameter_mode";
    public static final String COORDINATE_SEVEN_PARAMETER_DELTA_X = "coordinate_seven_parameter_delta_x";
    public static final String COORDINATE_SEVEN_PARAMETER_DELTA_Y = "coordinate_seven_parameter_delta_y";
    public static final String COORDINATE_SEVEN_PARAMETER_DELTA_Z = "coordinate_seven_parameter_delta_z";
    public static final String COORDINATE_SEVEN_PARAMETER_DELTA_ALPHA = "coordinate_seven_parameter_delta_alpha";
    public static final String COORDINATE_SEVEN_PARAMETER_DELTA_BETA = "coordinate_seven_parameter_delta_beta";
    public static final String COORDINATE_SEVEN_PARAMETER_DELTA_GAMMA = "coordinate_seven_parameter_delta_gamma";
    public static final String COORDINATE_SEVEN_PARAMETER_DELTA_SCALE = "coordinate_seven_parameter_delta_scale";
    public static final String COORDINATE_FOUR_PARAMETER_USING = "coordinate_four_parameter_using";
    public static final String COORDINATE_FOUR_PARAMETER_NORTH_DIRECTION_MOVE = "coordinate_four_parameter_north_direction_move";
    public static final String COORDINATE_FOUR_PARAMETER_EAST_DIRECTION_MOVE = "coordinate_four_parameter_east_direction_move";
    public static final String COORDINATE_FOUR_PARAMETER_SCALE = "coordinate_four_parameter_scale";
    public static final String COORDINATE_FOUR_PARAMETER_FAR_NORTH_DIRECTION_MOVE = "coordinate_four_parameter_far_north_direction_move";
    public static final String COORDINATE_FOUR_PARAMETER_FAR_EAST_DIRECTION_MOVE = "coordinate_four_parameter_far_east_direction_move";





    //ROOM
    public static final String WORKERS_DB = "workers-db";
    public static final String SERVERS_DB = "servers-db";
}
