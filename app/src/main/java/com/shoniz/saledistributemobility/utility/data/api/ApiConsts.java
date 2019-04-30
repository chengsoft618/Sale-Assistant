package com.shoniz.saledistributemobility.utility.data.api;

/**
 * Created by ferdos.s on 5/11/2017.
 */

public final class ApiConsts {
    public static final String CENTRAL_DEVELOP_URL_BASE_SITE = "http://192.168.30.80:21918/api/AuthV1/";
    public static final String CENTRAL_LOCAL_URL_BASE_SITE = "http://192.168.0.20:6999/api/AuthV1/";
    public static final String CENTRAL_EXTERNAL_URL_BASE_SITE = "http://2.186.229.209:6999/api/AuthV1/";
    public static final String API_AUTH_ROOT = "api/AuthV1";
    public static final String CHANGE_LOGS = "CHANGE_LOGS";
    //public static final String OFFICE_URL_BASE_SITE = "http://46.245.36.249:8230/api/ServiceV1/";
//    public static final String CENTRAL_URL_BASE_SITE = "http://192.168.0.196:21918/api/AuthV1/";
    public static final String OFFICE_URL_BASE_SITE = "http://192.168.0.196:21918/api/ServiceV1/";
    public static final String API_PRE_ROUTE = "api/ServiceV1.8.0.0/";
    public static final String IsOnline = "IsOnline";
    public static final String ReportLogin = "/Account/Login/?username=";


    ////192.168.0.196:8010/
    public final class Api {
        public static final String GET_SALE_DATA = "GetSaleDatabase";
        public static final String GET_BASE_DATA = "GetBaseData";
        public static final String GET_BRANCH = "GetBranchList";
        public static final String GET_EMPLOYEE = "GetEmployeeInfo";
        public static final String GET_Roles = "GetRoles";
        public static final String GET_Users = "GetUsers";
        public static final String GET_VISITOR_PATHS = "GetPathFromIds";
        public static final String GET_EMPLOYEE_PATHS_DB = "GetEmployeePathsDb";
        public static final String GET_EMPLOYEE_CUSTOMER_BASE_INFO_DB = "GetEmployeeCustomerBaseInfoDb";
        public static final String GET_EMPLOYEE_CUSTOMER_BASE_INFO_BY_PERSON_IDS = "GetEmployeeCustomerBaseInfoByPersonIds";
        public static final String GET_EMPLOYEE_CUSTOMER_BASE_INFO_BY_PATH = "GetEmployeeCustomerBaseInfoByPath";
        public static final String GET_EMPLOYEE_CUSTOMER_CREDIT_DB = "GetEmployeeCustomerCreditDb";
        public static final String GET_EMPLOYEE_CUSTOMER_CREDIT_BY_PERSON_IDS = "GetEmployeeCustomerCreditByPersonIds";
        public static final String GET_EMPLOYEE_CUSTOMER_CREDIT_BY_PATH = "GetEmployeeCustomerCreditByPath";
        public static final String GET_CUSTOMER_BUY_DB = "GetCustomerBuyDb";
        public static final String GET_EMPLOYEE_CUSTOMER_SUM_BUY_AND_BUY_RETURNED_BY_PERSON_IDS = "GetEmployeeCustomerSumBuyAndBuyReturnedByPersonIds";
        public static final String GET_EMPLOYEE_CUSTOMER_SUM_BUY_AND_BUY_RETURNED_BY_PATH = "GetEmployeeCustomerSumBuyAndBuyReturnedByPath";
        public static final String GET_EMPLOYEE_CUSTOMER_CHEQUE_DB = "GetEmployeeCustomerChequeListDb";
        public static final String GET_EMPLOYEE_CUSTOMER_CHEQUE_BY_PERSON_IDS = "GetEmployeeCustomerChequeListByPersonIds";
        public static final String GET_EMPLOYEE_CUSTOMER_CHEQUE_BY_PATH = "GetEmployeeCustomerChequeListByPath";
        public static final String GET_ORDER_DB = "GetOrderDb";
        public static final String GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_DETAIL_BY_PERSON_IDS = "GetOrderDetailByPersonIds";
        public static final String GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_BY_PERSON_IDS = "GetOdrderByPersonIDs";
        public static final String GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_BY_PATH = "GetEmployeeCustomerBusinessDocByPath";
        public static final String GET_EMPLOYEE_CUSTOMER_BUSINESS_DOC_DETAIL_BY_PATH = "GetEmployeeCustomerBusinessDocDetailByPath";
        public static final String SEND_CUSTOMER_LOCATION = "SetCustomerPoint";

        public static final String SEND_ORDER = "SendOrder";
        public static final String SET_REASON = "sendReasonAll";
        public static final String SET_REASON_ALL = "sendReasonAll";
        public static final String REGISTER_CLOUD_MESSAGE = "RegisterCloudMessage";

        public static final String GET_SHORTCUT_CHANGES = "GetShortcutChangesNew";
        public static final String GET_SHORTCUT_IMAGE = "fillShortcutImage";
        public static final String GET_RESOURCE_IMAGE = "getResourceChanges";
        public static final String GET_RESOURCE_FILE = "fillResourceModelByFile";
        public static final String GET_PROFILE_CATEGORY = "GetProfileCategory";
        public static final String GET_PROFILE_CATEGORY_ALL = "GetProfileCategoryAll";
        public static final String GET_CATEGORY_ALL = "getCategoryAll";
        public static final String GET_SUB_CATEGORY_ALL = "getSubCategoryAll";
        public static final String GET_SUB_CATEGORY_DETAIL_ALL = "getSubCategoryDetailAll";


        public static final String GET_NEW_MESSAGE = "CatchMessage";
        public static final String SEND_SAVED_MESSAGES_IDS = "SetMessageDelivered";


    }

}
