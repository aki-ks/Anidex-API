package de.kaysubs.tracker.anidex.model;

import org.apache.http.entity.mime.MultipartEntityBuilder;

public interface SetSettingRequest {

    String getFormName();

    void buildForm(MultipartEntityBuilder builder);

}
