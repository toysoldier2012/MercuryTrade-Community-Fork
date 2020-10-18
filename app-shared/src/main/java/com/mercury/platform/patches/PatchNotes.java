package com.mercury.platform.patches;

import java.util.List;

public class PatchNotes {
    private String version;
    private List<Change> fix;
    private List<Change> features;
    private List<Change> minorChanges;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Change> getFix() {
        return fix;
    }

    public void setFix(List<Change> fix) {
        this.fix = fix;
    }

    public List<Change> getFeatures() {
        return features;
    }

    public void setFeatures(List<Change> features) {
        this.features = features;
    }

    public List<Change> getMinorChanges() {
        return minorChanges;
    }

    public void setMinorChanges(List<Change> minorChanges) {
        this.minorChanges = minorChanges;
    }
}
