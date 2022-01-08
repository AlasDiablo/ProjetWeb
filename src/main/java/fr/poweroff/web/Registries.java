package fr.poweroff.web;

public class Registries {
    /* * * * * * * * Path to a jsp * * * * * * * */
    public static final String JSP_ERROR_401 = "/html/error/401.jsp";

    public static final String JSP_ACCOUNT                      = "/html/page/account/account.jsp";
    public static final String JSP_ACCOUNT_INFO                 = "/html/page/account/personal-information.jsp";
    public static final String JSP_ACCOUNT_INFO_EDIT            = "/html/page/account/personal-information-edit.jsp";
    public static final String JSP_ACCOUNT_FRIENDS              = "/html/page/account/friends.jsp";
    public static final String JSP_ACCOUNT_NOTIFICATION         = "/html/page/account/notification.jsp";
    public static final String JSP_ACCOUNT_NOTIFICATION_DISPLAY = "/html/page/account/notification-display.jsp";

    public static final String JSP_ADD_ACTIVITY = "/html/page/activity/add-activity.jsp";
    public static final String JSP_MY_ACTIVITY  = "/html/page/activity/my-activity.jsp";

    public static final String JSP_INDEX = "/html/index.jsp";

    public static final String JSP_SIGN_IN = "/html/page/login/sign-in.jsp";
    public static final String JSP_SIGN_UP = "/html/page/login/sign-up.jsp";

    public static final String JSP_PEOPLES      = "/html/page/people/peoples.jsp";
    public static final String JSP_PEOPLE_ABOUT = "/html/page/people/people-about.jsp";

    // admin
    public static final String JSP_ADMIN_ACTIVITY = "/html/page/activity/activities.jsp";

    /* * * * * * * * Path to a servlet * * * * * * * */
    public static final String PATH_API_ACTIVITY     = "/api/activity";
    public static final String PATH_API_PLACE        = "/api/place";
    public static final String PATH_API_DATA_HISTORY = "/api/public/data/history";

    public static final String PATH_INDEX = "/";

    public static final String PATH_ACCOUNT                      = "/account";
    public static final String PATH_ACCOUNT_INFO                 = "/account/info";
    public static final String PATH_ACCOUNT_INFO_EDIT            = "/account/info/edit";
    public static final String PATH_ACCOUNT_FRIENDS              = "/account/friends";
    public static final String PATH_ACCOUNT_NOTIFICATION         = "/account/notification";
    public static final String PATH_ACCOUNT_NOTIFICATION_DISPLAY = "/account/notification/display";

    public static final String PATH_ADD_ACTIVITY = "/account/activity/add";
    public static final String PATH_MY_ACTIVITY  = "/account/activity";

    public static final String PATH_SIGN_IN  = "/sign-in";
    public static final String PATH_SIGN_UP  = "/sign-up";
    public static final String PATH_SIGN_OUT = "/sign-out";

    public static final String PATH_PEOPLES      = "/peoples";
    public static final String PATH_PEOPLE_ABOUT = "/people-about";

    public static final String PATH_POSITIF = "/positif";

    // admin
    public static final String PATH_DELETE_PEOPLE  = "/admin/delete-people";
    public static final String PATH_ADMIN_ACTIVITY = "/admin/activity";
}
