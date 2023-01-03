package agent;

import tranformer.mytran;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void premain(String agrs, Instrumentation inst){
        System.out.println("Welcome to Mysql Injection Rasp");
        inst.addTransformer(new mytran(),true);
    }
}
