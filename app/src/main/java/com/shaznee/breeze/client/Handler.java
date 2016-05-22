package com.shaznee.breeze.client;

import com.shaznee.breeze.models.Forecast;

/**
 * Created by Shaznee on 22-May-16.
 */
public interface Handler {

    void successful(Forecast forecast);

    void failure();
}
