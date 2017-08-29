package com.javarush.task.task37.task3711;

/**
 * Created by ua053202 on 29.08.2017.
 */
public class Computer {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;

    public Computer() {
        cpu = new CPU();
        memory = new Memory();
        hardDrive = new HardDrive();
    }

    public void run()
    {
        cpu.calculate();
        memory.allocate();
        hardDrive.storeData();
    }
}
