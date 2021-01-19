package com.mercury.platform.shared.config;


import org.apache.commons.lang3.SystemUtils;

public class MercuryConfigurationSource extends ConfigurationSource {
    public MercuryConfigurationSource() {
        super(SystemUtils.IS_OS_WINDOWS ? System.getenv("USERPROFILE") + "\\AppData\\Local\\MercuryTrade" : "AppData/Local/MercuryTrade",
              SystemUtils.IS_OS_WINDOWS ? System.getenv("USERPROFILE") + "\\AppData\\Local\\MercuryTrade\\configuration.json" : "AppData/Local/MercuryTrade/configuration.json");
    }
}
