package ru.omstu.monographreview.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.omstu.monographreview.dto.RequestFile;
import ru.omstu.monographreview.models.enumeration.RequestFileType;

import java.io.IOException;

@Service
public class FileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations gridFsOperations;

    public String uploadFile(MultipartFile file, RequestFileType type) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("fileType", "document");
        metaData.put("docType", type);

        String fileName = file.getResource().getFilename();
        ObjectId id = gridFsTemplate.store(file.getInputStream(), fileName, file.getContentType(), metaData);
        return id.toString();
    }

    public RequestFile getFile(String id) throws IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        RequestFile requestFile = new RequestFile();
        requestFile.setType(RequestFileType.valueOf(file.getMetadata().get("docType").toString()));
        requestFile.setFileName(gridFsOperations.getResource(file).getFilename());
        requestFile.setData(gridFsOperations.getResource(file).getContentAsByteArray());
        return requestFile;
    }
}
