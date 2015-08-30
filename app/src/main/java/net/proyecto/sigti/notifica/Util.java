package net.proyecto.sigti.notifica;

/**
 * Created by choqu_000 on 17/08/2015.
 * Clase ddonde esta la configuracion de nuestro server
 */
public class Util {

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String EMAIL = "email";
    public static final String USER_NAME = "user_name";

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public final static String SENDER_ID = "448055700844";
    //192.168.1.124  http://sigti.co/directv/
    //public static String base_url = "http://sigti.co/directv/";
    public static String base_url = "http://192.168.1.124/directv/";
    public final static String  register_url=base_url+"register.php";
    public final static String  send_chat_url=base_url+"sendChatmessage.php";


}
