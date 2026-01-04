package com.cms.customer_management.dto;

import java.util.ArrayList;
import java.util.List;

public class ExcelUploadResponse {

    private int saved;
    private int updated;
    private int skipped;
    private List<String> errors = new ArrayList<>();

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public List<String> getErrors() {
        return errors;
    }
}
