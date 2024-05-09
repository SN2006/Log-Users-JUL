package com.example.utils;

import com.example.controller.AppController;
import com.example.service.AppService;
import com.example.view.AppView;

public class AppStarter {

    public static void startApp(){
        AppView appView = new AppView();
        AppService service = new AppService();
        AppController controller = new AppController(appView, service);
        controller.runApp();
    }

}
