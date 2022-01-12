package fxs.fourthten.springtutorial.config.utility;

public class ConstantUtil {
    /** Role */
    public static final String SUPERADMIN = "SUPERADMIN";
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    /** Response */
    public static final String DATA_NOT_FOUND = "Data Not Found";
    public static final String SUCCESS = "Successfully loaded";
    public static final String EXISTING_DATA = "Data already exist";
    public static final String MODIFY_DATA_FAILED = "Failed to modify data";

    /** JWT */
    public static final String API_SECRET_KEY = "fourthsecretkey";
    public static final Long TIME_ACCESS_VALIDITY = System.currentTimeMillis() * 30 * 60 * 1000;
    public static final Long TIME_REFRESH_VALIDITY = System.currentTimeMillis() * 60 * 60 * 1000;
}
