package com.chtrembl.petstoreapp.service;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.telemetry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelemetryLogger {
    private static final Logger logger = LoggerFactory.getLogger(TelemetryLogger.class);

    private static TelemetryClient telemetryClient = new TelemetryClient();

    static {
        telemetryClient.getContext().setInstrumentationKey(System.getenv("e526f19c-54fc-4a5d-ae6f-0036ff702711"));
    }

    private static Map<String,SeverityLevel>  logLevelToSeverityLevelMap = new HashMap<>(){{
        put("debug", SeverityLevel.Verbose);
        put("info", SeverityLevel.Information);
        put("warn", SeverityLevel.Warning);
        put("error", SeverityLevel.Error);

    }};

    public static void debug(String s){
        trackTrace(s, logLevelToSeverityLevelMap.get("debug"));
        logger.debug(s);
    }

    public static void info(String s){
        trackTrace(s, logLevelToSeverityLevelMap.get("info"));
        logger.info(s);
    }
    public static void warn(String s){
        trackTrace(s, logLevelToSeverityLevelMap.get("warn"));
        logger.warn(s);
    }
    public static void error(String s){
        trackTrace(s, logLevelToSeverityLevelMap.get("error"));
        logger.error(s);
    }

    public static void logMetric(String metricName, double value){
        telemetryClient.trackMetric(new MetricTelemetry(metricName, value));
    }

    public static void logEvent(String eventName){
        telemetryClient.trackEvent(new EventTelemetry(eventName));
    }

    public static void logException(Exception ex){
        ExceptionTelemetry exceptionTelemetry = new ExceptionTelemetry(ex, 2);
        telemetryClient.trackException(exceptionTelemetry);
    }

    private static void trackTrace(String s, SeverityLevel logLevelToSeverityLevelMap) {
        TraceTelemetry traceTelemetry = new TraceTelemetry(s);
        traceTelemetry.setSeverityLevel(logLevelToSeverityLevelMap);
        telemetryClient.trackTrace(traceTelemetry);
    }

}