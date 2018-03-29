package cn.itcast.domain.constants;

/**
 * 常量工具类
 */
public class Constants {


    public static final String BOS_MANAGEMENT_HOST = "http://localhost:8080";
    public static final String CRM_MANAGEMENT_HOST = "http://localhost:9002";
    public static final String BOS_FORE_HOST = "http://localhost:9003";
    public static final String BOS_SMS_HOST = "http://localhost:9004";
    public static final String BOS_MAIL_HOST = "http://localhost:9005";

    private static final String BOS_MANAGEMENT_CONTEXT = "/bos_management";
    private static final String CRM_MANAGEMENT_CONTEXT = "/crm_management";
    private static final String BOS_FORE_CONTEXT = "/bos_fore";
    private static final String BOS_SMS_CONTEXT = "/bos_sms";
    private static final String BOS_MAIL_CONTEXT = "/bos_mail";

    public static final String BOS_MANAGEMENT_URL = BOS_MANAGEMENT_HOST    // bos_management（后台系统）
            + BOS_MANAGEMENT_CONTEXT;
    public static final String CRM_MANAGEMENT_URL = CRM_MANAGEMENT_HOST    // crm_management（CRM系统）
            + CRM_MANAGEMENT_CONTEXT;
    public static final String BOS_FORE_URL = BOS_FORE_HOST + BOS_FORE_CONTEXT;  // bos_fore（前端系统）
    public static final String BOS_SMS_URL = BOS_SMS_HOST + BOS_SMS_CONTEXT;   // bos_sms（短信平台）
    public static final String BOS_MAIL_URL = BOS_MAIL_HOST + BOS_MAIL_CONTEXT;  // bos_main（邮件平台）
}