package com.ats.manoharadmin.common;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
//import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

public class Constant {

	
	public static final String url="http://localhost:8095/";
	
	//public static final String url="http://107.180.91.43:8080/madhviwebapi/";

	
	
	public static final String SPCAKE_IMAGE_URL = "http://107.180.91.43:8080/uploads/MSPCAKE/";
	public static final String FR_IMAGE_URL = "http://107.180.91.43:8080/uploads/FR/";
	public static final String MESSAGE_IMAGE_URL = "http://107.180.91.43:8080/uploads/MSG/";
	public static final String ITEM_IMAGE_URL = "http://107.180.91.43:8080/uploads/ITEM/";
	public static final String RAW_MAT_IMG_URL ="http://107.180.91.43:8080/uploads/RAWMAT/";
	public static final String GATE_ENTRY_IMG_URL = "http://107.180.91.43:8080/uploads/GATEENTRY/";
	public static final String GVN_IMAGE_URL ="http://107.180.91.43:8080/uploads/GVN/";
	public static  String FACTORYNAME = "Madhvi Dairy";
	public static  String FACTORYADDRESS = "C-2, A/8, Plot No. 183/1, Phase - 1 ,GIDC Naroda, Ahmedabad - 382330";
	public static String FACTORYGSTIN = "-";
	public static final String CITY = "Palanpur";
	public static final String STATE = "GUJARAT";
	public static final String CODE = "MF";

	public static int mainAct=0;
	public static int subAct=0;

	public static int FR_IMAGE_TYPE=1;
	public static int ITEM_IMAGE_TYPE=2;
	public static int MESSAGE_IMAGE_TYPE=3;
	public static int SPCAKE_IMAGE_TYPE=4;
	public static int CUST_CHIOICE_IMAGE_TYPE=5;
	public static int RAW_MAT_IMAGE_TYPE=6;
    public static int GATE_ENTRY_IMAGE_TYPE=7;

    //public static final String ReportURL ="http://localhost:9090/adminpanel/";
	public static final String ReportURL ="http://107.180.91.43:8080/admin/";//change
	public static final String SETTING_KEY = "PB";
	public static final int DIS_BY_ACC = 7;
	public static final int AP_BY_ACC = 6;
	public static final int DIS_BY_STORE = 5;
	public static final int AP_BY_STORE = 4;
	public static final int AP_BY_GATE = 2;
	public static final int DIS_BY_GATE = 3;
	public static final String REPORT_SAVE = "/opt/apache-tomcat-8.5.49/webapps/uploads/AdminReport.pdf";
	public static final String BILL_REPORT_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/Bill.pdf";
	public static final String CRN_REPORT_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/crn.pdf";
    public static final String FINISHEDGOOD_REPORT_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/fsr.pdf";
    public static final String PO_PDF_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/po.pdf";
    public static final String SALES_REPORT_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/report.pdf";
    public static final String INWARD_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/Inward.pdf";
    public static final String LOG_PDF_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/logdf.pdf";
    public static final String DISPATCH_PATH="/opt/apache-tomcat-8.5.49/webapps/uploads/dispatch.pdf";
		
	public static final String SP_CAKE_FOLDER = "http://107.180.91.43:8080/uploads/SPCAKE/";
	public static final String CUST_CHOICE_PHOTO_CAKE_FOLDER = "http://107.180.91.43:8080/uploads/CUSTCHOICEPHOTOCAKE/";
	public static final int LOGIS_BILL_PDF_TYPE = 8;
	public static final String LOGIS_BILL_URL = "http://107.180.91.43:8080/uploads/MSPCAKE/";
	public static final int MENU_IMAGE_TYPE = 0;
	public static final String MENU_IMAGE_URL = null;
	public static final String SPHSN = "19059010";
	
	public static final String UPLOAD_URL = "/home/maddy/ats-12/";
	public static final String showDocSaveUrl = "http://localhost:8081/madhvickadmin/home/maddy/ats-12/";

	
	
	public static String[] imageAndDocFileExtension = { "txt", "doc", "docx", "pdf", "xls", "xlsx","jpg", "jpeg", "gif", "png" }; 
	public static String[] imageFileExtensions = {"jpg", "jpeg", "gif", "png" };
	
	public static RestTemplate rest = new RestTemplate();
	 public static RestTemplate getRestTemplate() {
			rest=new RestTemplate();
			rest.getInterceptors().add(new BasicAuthorizationInterceptor("aaryatech", "Aaryatech@1cr"));
			return rest;

			} 
}
