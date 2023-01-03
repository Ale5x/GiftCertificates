package com.epam.esm.controller;

import org.springframework.stereotype.Component;

/**
 * This class specifies page paths for specific application operations.
 *
 * @author Alexander Pishchala
 */
@Component
public class PathPageConstant {

    public static final String FLASH = "/";
    public static final String APP_STORE = "store";
    public static final String SECTION_USER = APP_STORE + FLASH + "user";
    public static final String SECTION_CERTIFICATE = APP_STORE + FLASH + "certificate";
    public static final String SECTION_ORDER = APP_STORE + FLASH + "order";
    public static final String SECTION_TAG = APP_STORE + FLASH + "tag";

    public static final String LOGIN_CUSTOM_AUTH = "api/login";
    public static final String LOGIN = "login";


    public static final String USER_GET_ALL = SECTION_USER + FLASH + "getAllUsers";
    public static final String USER_CREATE = SECTION_USER + FLASH + "create";
    public static final String USER_GET = SECTION_USER + FLASH + "get";
    public static final String USER_GET_USERNAME = SECTION_USER + FLASH + "username";
    public static final String USER_GET_COUNT = SECTION_USER + FLASH + "getCountUser";
    public static final String USER_CHANGE_ROLE = SECTION_USER + FLASH + "changeRole";
    public static final String USER_CHANGE_STATUS = SECTION_USER + FLASH + "changeStatus";
    public static final String USER_EXIST = SECTION_USER + FLASH + "existUser";


    public static final String CERTIFICATE_CREATE = SECTION_CERTIFICATE + FLASH + "create";
    public static final String CERTIFICATE_UPDATE = SECTION_CERTIFICATE + FLASH + "update";
    public static final String CERTIFICATE_UPDATE_PRICE = SECTION_CERTIFICATE + FLASH + "updatePrice";
    public static final String CERTIFICATE_UPDATE_DURATION = SECTION_CERTIFICATE + FLASH + "updateDuration";
    public static final String CERTIFICATE_DELETE = SECTION_CERTIFICATE + FLASH + "delete";
    public static final String CERTIFICATE_GET = SECTION_CERTIFICATE + FLASH + "get";
    public static final String CERTIFICATE_GET_ALL = SECTION_CERTIFICATE + FLASH + "getAllCertificates";
    public static final String CERTIFICATE_GET_BY_PART_NAME = SECTION_CERTIFICATE + FLASH + "getCertificatesByPartName";
    public static final String CERTIFICATE_GET_BY_TAG_NAME = SECTION_CERTIFICATE + FLASH + "getCertificatesByTagName";
    public static final String CERTIFICATE_GET_ALL_SORT_NAME = SECTION_CERTIFICATE + FLASH + "allSortName";
    public static final String CERTIFICATE_GET_ALL_SORT_REVERSE_NAME = SECTION_CERTIFICATE + FLASH + "allSortReverseName";
    public static final String CERTIFICATE_GET_ALL_SORT_DATE = SECTION_CERTIFICATE + FLASH + "allSortDate";
    public static final String CERTIFICATE_GET_ALL_SORT_REVERSE_DATE = SECTION_CERTIFICATE + FLASH + "allSortReverseDate";
    public static final String CERTIFICATE_GET_BY_ORDER = SECTION_CERTIFICATE + FLASH + "getByOrder";
    public static final String CERTIFICATE_GET_COUNT = SECTION_CERTIFICATE + FLASH + "getCountCertificates";

    public static final String ORDER_CREATE = SECTION_ORDER + FLASH + "create";
    public static final String ORDER_DELETE = SECTION_ORDER + FLASH + "delete";
    public static final String ORDER_GET = SECTION_ORDER + FLASH + "get";
    public static final String ORDER_USER = SECTION_ORDER + FLASH + "user";
    public static final String ORDER_GET_ALL = SECTION_ORDER + FLASH + "getAllOrders";
    public static final String ORDER_GET_COUNT = SECTION_ORDER + FLASH + "getCountOrders";


    public static final String TAG_CREATE = SECTION_TAG + FLASH + "create";
    public static final String TAG_DELETE = SECTION_TAG + FLASH + "delete";
    public static final String TAG_GET = SECTION_TAG + FLASH + "get";
    public static final String TAG_GET_ALL = SECTION_TAG + FLASH + "getAllTags";
    public static final String TAG_GET_BY_PART_NAME = SECTION_TAG + FLASH + "getByPartName";
    public static final String TAG_GET_NAME = SECTION_TAG + FLASH + "getName";
    public static final String TAG_GET_POPULAR_TAG = SECTION_TAG + FLASH + "getPopularTag";
    public static final String TAG_GET_COUNT = SECTION_TAG + FLASH + "getCountTag";


    public static final String TOKEN_REFRESH = SECTION_USER + FLASH + "token/refresh";

    private PathPageConstant() {
    }
}
