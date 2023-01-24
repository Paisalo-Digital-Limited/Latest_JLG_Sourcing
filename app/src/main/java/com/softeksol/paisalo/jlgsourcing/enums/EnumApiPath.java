package com.softeksol.paisalo.jlgsourcing.enums;

/**
 * Created by sachindra on 12/14/2016.
 */
public enum EnumApiPath {
    BorrowerApi("api/posfi/SaveFiPic"), GuarantorApi("api/posguarantor/SaveGuarPic"), DocumentApi("api/posfi/SaveFiDocs"),
    BorrowerApiJson("api/uploaddocs/savefipicjson"), GuarantorApiJson("api/uploaddocs/savefipicjson"), DocumentApiJson("api/uploaddocs/savefidocsjson");
    private final String apiPath;

    public String getApiPath() {
        return apiPath;
    }

    EnumApiPath(String apiPath) {
        this.apiPath = apiPath;
    }
}
