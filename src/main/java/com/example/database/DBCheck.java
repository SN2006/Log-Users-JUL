package com.example.database;

import com.example.utils.Constants;

import java.nio.file.Files;
import java.nio.file.Path;

public class DBCheck {

    public static boolean isDBExists(){
        return !Files.exists(Path.of(Constants.DB_BASE_URL + Constants.DB_NAME));
    }

}
