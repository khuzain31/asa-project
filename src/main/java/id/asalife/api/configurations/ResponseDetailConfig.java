package id.asalife.api.configurations;

import lombok.Data;

@Data
public class ResponseDetailConfig {

    String code = "status", message = "message";
    public String tokenPrefix = "Bearer ";
    public String codeSuccess = "200";
    public String codeServer = "500";
    public String codeNotFound = "404";
    public String codeRequired = "400";
    public String codeNull = "1";

    public String messageSuccess = "Successful";
    public String messageAlreadyExist = " already exist";
    public String messageNotFound = " not found.";

    public String isRequired = " is required";
    public String statusActive = "active";
    public String statusInActive = "inactive";
    public String statusPublish = "publish";
    public String statusNotPublish = "not published";
    public String statusDraft = "draft";

}


