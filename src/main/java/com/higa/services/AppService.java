package com.higa.services;

import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.QueueStorageException;
import com.higa.models.ReqBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

@Service
public class AppService {

    @Value("${azure.endpoint.protocol}")
    private String endpointProtocol;

    @Value("${azure.endpoint.suffix}")
    private String endpointSuffix;

    @Value("${azure.storage.account.name}")
    private String storageAccountName;

    @Value("${azure.storage.account.key}")
    private String storageAccountKey;

    @Value("${azure.queue.name}")
    private String queueName;

    public ResponseEntity<String> insertImgIntoQueue(ReqBody reqBody) throws QueueStorageException {
        if(isValidMsgSent(reqBody)){
            getAzQueueClient(queueName).sendMessage(reqBody.getCert_nasc_base64());
            return ResponseEntity.ok("Imagem inserida com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Request body enviado invalido.");
        }
    }

    private boolean isValidMsgSent(ReqBody reqBody){
        if(Objects.isNull(reqBody.getCert_nasc_base64())){
            return false;
        }
        try{
            Base64.getDecoder().decode(reqBody.getCert_nasc_base64());
            return true;
        } catch (IllegalArgumentException exception){
            return false;
        }
    }

    private QueueClient getAzQueueClient(String queueName){
        return new QueueClientBuilder()
                .connectionString(getAzConnStr())
                .queueName(queueName)
                .buildClient();
    }

    private String getAzConnStr(){
        return new StringBuilder()
                .append("DefaultEndpointsProtocol=").append(endpointProtocol).append(";")
                .append("AccountName=").append(storageAccountName).append(";")
                .append("AccountKey=").append(storageAccountKey).append(";")
                .append("EndpointSuffix=").append(endpointSuffix)
                .toString();
    }
}
