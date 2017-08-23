package com.javarush.task.task36.task3601;

import java.util.List;

/**
 * Created by ua053202 on 23.08.2017.
 */
public class View {

    private Controller controller = new Controller();

    public void fireEventShowData() {
        System.out.println(controller.onDataListShow());
    }
}
