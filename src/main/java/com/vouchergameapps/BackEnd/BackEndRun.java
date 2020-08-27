package com.vouchergameapps.BackEnd;

import com.vouchergameapps.BackEnd.broker.BERecieve;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BackEndRun {
    public static void main(String args[]){
        BERecieve beRecieve = new BERecieve();

        try {
            beRecieve.receiveInputFromRest();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
