package com.example.utils;

public final class Constants {

    // DB
    public final static String DB_DRIVER = "jdbc:sqlite:";
    public final static String DB_BASE_URL = "src/main/resources/db/";
    public final static String DB_NAME = "users_db.db";

    // UI messages
    public final static String WRONG_EMAIL_MSG = "Wrong email input.";
    public final static String WRONG_ID_MSG = "Wrong id input.";
    public final static String INCORRECT_INPUT_MSG = "Check inputs.";
    public final static String DB_ABSENT_MSG = ">> No database!";
    public final static String DATA_ABSENT_MSG = ">> No data!";
    public final static String DATA_INSERT_MSG = ">> Created.";
    public final static String DATA_UPDATE_MSG = ">> Updated.";
    public final static String DATA_DELETE_MSG = ">> Deleted.";
    public final static String APP_CLOSE_MSG = ">> App closed.";
    public final static String INPUT_REQ_MSG = "Input required.";
    public final static String INCORRECT_OPTION_MSG = ">> Incorrect option! Try again.";

    // Log messages
    public final static String LOG_DB_ABSENT_MSG = "Database is absent!";
    public final static String LOG_DATA_INPUTS_WRONG_MSG = "Data inputs incorrect.";
    public final static String LOG_DATA_ABSENT_MSG = "Data are absent!";
    public final static String LOG_DB_ERROR_MSG = "DB manipulations error.";
    public final static String LOG_DB_INSERT_MSG = "Data insert successful.";
    public final static String LOG_DB_READ_MSG = "Data read successful.";
    public final static String LOG_DB_UPDATE_MSG = "User data update successful.";
    public final static String LOG_DB_DELETE_MSG = "User data delete successful.";
}
