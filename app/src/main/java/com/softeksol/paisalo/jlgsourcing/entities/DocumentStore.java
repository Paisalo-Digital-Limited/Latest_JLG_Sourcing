package com.softeksol.paisalo.jlgsourcing.entities;

import android.content.Context;
import android.database.Cursor;
import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonStreamerEntity;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.DbIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.WebOperations;
import com.softeksol.paisalo.jlgsourcing.entities.dto.DocumentStoreDTO;
import com.softeksol.paisalo.jlgsourcing.enums.EnumApiPath;
import com.softeksol.paisalo.jlgsourcing.enums.EnumFieldName;
import com.softeksol.paisalo.jlgsourcing.enums.EnumImageTags;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.Executors;

import static com.softeksol.paisalo.jlgsourcing.Utilities.DateUtils.formatdateTimeStrings;

/**
 * Created by sachindra on 2016-10-04.
 */

@ModelContainer
@Table(database = DbIGL.class)
public class DocumentStore extends BaseModel {

    @Column
    @Expose
    public String Creator;

    @Column
    @Expose
    public long ficode;

    @Column
    @Expose
    public String fitag;

    //Tag of Image
    @Column
    @Expose
    public String imageTag; // //CUSTIMG, GUARPIC, DOCPIC

    @Column(defaultValue = "0")
    @Expose
    public int GuarantorSerial;

    @Column
    @Expose
    public String remarks;

    @Column(defaultValue = "0")
    @Expose
    public long checklistid;

    @Column
    @Expose
    public String userid;

    //Field name of the stream
    @Column
    @Expose
    public String fieldname; //borrowerpic,guarantorpic,documentpic

    @Column
    @Expose
    public Date creationDate;

    @Column(defaultValue = "0")
    public float latitude;

    @Column(defaultValue = "0")
    public float longitude;

    @Column
    @PrimaryKey(autoincrement = true)
    public long DocId;

    @Column
    public long FiID;
    @Column
    @Expose
    public String apiRelativePath;  // api/posfi/SaveFiPic, api/posfi/SaveGuarPic,  api/posfi/SaveFiDocs
    @Column
    public boolean updateStatus = false;
    @Column
    public String imagePath;

//    @Column
//    public String imageshow;

    public static String getDocumentName(long checklistId) {

        Cursor cursor = SQLite.select(RangeCategory_Table.DescriptionEn).from(RangeCategory.class).as("DocName")
                .where(RangeCategory_Table.cat_key.eq("docList")).and(RangeCategory_Table.RangeCode.eq(String.valueOf(checklistId))).query();
        String documentName = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            documentName = cursor.getString(0);
        }
        cursor.close();
        return documentName;
    }

    public DocumentStore() {
    }

    public DocumentStore(DocumentStoreDTO dto) {
        this.Creator = dto.Creator;
        this.ficode = dto.FICode;
        this.checklistid = dto.ChecklistID;
        this.GuarantorSerial = dto.GrNo;
        this.remarks = dto.DocRemark;
        /*this.updateStatus=true;*/
    }

    public DocumentStoreDTO getDocumentDTO() {
        DocumentStoreDTO dto = new DocumentStoreDTO();
        dto.Creator = this.Creator;
        dto.FICode = this.ficode;
        dto.ChecklistID = this.checklistid;
        dto.DocRemark = this.remarks;
        dto.Tag = this.fitag;
        dto.GrNo = this.GuarantorSerial;
        dto.ImageTag = this.imageTag;
        dto.latitude = this.latitude;
        dto.longitude = this.longitude;
        dto.UserID = this.userid;
        dto.timestamp = DateUtils.getFormatedDate(creationDate, formatdateTimeStrings[0]);
        File file = new File(this.imagePath);
        if (file.exists()) {
            byte[] bytes = Utils.getBytesFromFile(file);
            dto.Document = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return dto;
    }

    public JsonStreamerEntity getDocumentJsonStreamEntity(ResponseHandlerInterface responseHandlerInterface) {
        JsonStreamerEntity jsonStreamerEntity = new JsonStreamerEntity(responseHandlerInterface, true, null);
        jsonStreamerEntity.addPart("Creator", Creator);
        jsonStreamerEntity.addPart("FICode", ficode);
        jsonStreamerEntity.addPart("ChecklistID", checklistid);
        jsonStreamerEntity.addPart("DocRemark", remarks);
        jsonStreamerEntity.addPart("Tag", fitag);
        jsonStreamerEntity.addPart("GrNo", GuarantorSerial);
        jsonStreamerEntity.addPart("ImageTag", imageTag);
        jsonStreamerEntity.addPart("latitude", latitude);
        jsonStreamerEntity.addPart("longitude", longitude);
        jsonStreamerEntity.addPart("UserID", userid);
        jsonStreamerEntity.addPart("timestamp", DateUtils.getFormatedDate(creationDate, formatdateTimeStrings[0]));
        File file = new File(this.imagePath);
        if (file.exists()) {
            byte[] bytes = Utils.getBytesFromFile(file);
            jsonStreamerEntity.addPart("Document", Base64.encodeToString(bytes, Base64.DEFAULT));
        }
        return jsonStreamerEntity;
    }

    public RequestParams configureClient(Context context, AsyncHttpClient client) {
        WebOperations.setHttpHeaders(context, client, true);
        client.setThreadPool(Executors.newSingleThreadExecutor());
        client.addHeader("Creator", Creator);
        client.addHeader("ficode", String.valueOf(ficode));
        if (fitag != null) client.addHeader("fitag", fitag);
        client.addHeader("fiimgtag", imageTag);
        client.addHeader("grsrno", String.valueOf(GuarantorSerial));
        if (remarks != null) client.addHeader("DocRemark", remarks);
        if (checklistid != 0) client.addHeader("ChecklistID", String.valueOf(checklistid));
        if (userid != null) client.addHeader("UserID", userid);
        if (creationDate != null)
            client.addHeader("timestamp", DateUtils.getFormatedDate(creationDate, formatdateTimeStrings[0]));
        if (latitude > 0) client.addHeader("latitude", String.valueOf(latitude));
        if (longitude > 0) client.addHeader("longitude", String.valueOf(longitude));
        RequestParams params = new RequestParams();
        File docFile = new File(imagePath);
        //Log.d("Image",docFile.getPath()+ "   Size "+ docFile.length());
        try {
            params.put(fieldname, docFile, "application/octet-stream", imageTag + ".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return params;
    }

    public DocumentStore getDocumentStore(Borrower borrower, int checkListId, String remarks) {
        DocumentStore documentStore;
        Where<DocumentStore> where = SQLite.select().from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(borrower.FiID))
                .and(DocumentStore_Table.checklistid.eq(checkListId))
                .and(DocumentStore_Table.GuarantorSerial.eq(0))
                .and(DocumentStore_Table.remarks.eq(remarks));
        documentStore = where.querySingle();
        if (documentStore == null) {
            documentStore = new DocumentStore();
            documentStore.FiID = borrower.FiID;
            documentStore.fitag = borrower.Tag;
            documentStore.ficode = borrower.Code;
            documentStore.Creator = borrower.Creator;
            documentStore.userid = borrower.UserID;
            documentStore.checklistid = checkListId;
            documentStore.remarks = remarks;
            if (checkListId == 0) {
                documentStore.imageTag = EnumImageTags.Borrower.getImageTag();
                documentStore.fieldname = EnumFieldName.Borrower.getFieldName();
                documentStore.apiRelativePath = EnumApiPath.BorrowerApiJson.getApiPath();

            } else {
                documentStore.imageTag = EnumImageTags.Documents.getImageTag();
                documentStore.fieldname = EnumFieldName.Documents.getFieldName();
                documentStore.apiRelativePath = EnumApiPath.DocumentApi.getApiPath();
            }
            documentStore.GuarantorSerial = 0;
        } else if (documentStore.ficode == 0) {
            documentStore.ficode = borrower.Code;
        }
        return documentStore;
    }

    public DocumentStore getDocumentStore(Guarantor guarantor, int checkListId, String remarks) {
        DocumentStore documentStore;
        documentStore = SQLite.select().from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(guarantor.getFiID()))
                .and(DocumentStore_Table.checklistid.eq(checkListId))
                .and(DocumentStore_Table.GuarantorSerial.eq(guarantor.getGrNo()))
                .and(DocumentStore_Table.remarks.eq(remarks))
                .querySingle();
        if (documentStore == null) {
            documentStore = new DocumentStore();
            documentStore.FiID = guarantor.getFiID();
            documentStore.ficode = guarantor.getFi_Code();
            documentStore.Creator = guarantor.getCreator();
            documentStore.fitag = guarantor.getFiTag();
            documentStore.userid = guarantor.getUserID();
            documentStore.checklistid = checkListId;
            documentStore.remarks = remarks;
            if (checkListId == 0) {
                documentStore.imageTag = EnumImageTags.Guarantor.getImageTag();
                documentStore.fieldname = EnumFieldName.Guarantor.getFieldName();
                documentStore.apiRelativePath = EnumApiPath.GuarantorApiJson.getApiPath();
            } else {
                documentStore.imageTag = EnumImageTags.Documents.getImageTag();
                documentStore.fieldname = EnumFieldName.Documents.getFieldName();
                documentStore.apiRelativePath = EnumApiPath.DocumentApi.getApiPath();
            }
            documentStore.GuarantorSerial = guarantor.getGrNo();
        } else if (documentStore.ficode == 0) {
            documentStore.ficode = guarantor.getFi_Code();
        }
        return documentStore;
    }

    public static Comparator<DocumentStore> sortByUploadStatus = new Comparator<DocumentStore>() {
        public int compare(DocumentStore dueData1, DocumentStore dueData2) {
            String updated1 = dueData1.updateStatus ? "Y" : "N";
            String updated2 = dueData2.updateStatus ? "Y" : "N";
            return updated1.compareTo(updated2);
        }
    };

    @Override
    public String toString() {
        return "DocumentStore{" +
                "Creator='" + Creator + '\'' +
                ", ficode=" + ficode +
                ", fitag='" + fitag + '\'' +
                ", imageTag='" + imageTag + '\'' +
                ", GuarantorSerial=" + GuarantorSerial +
                ", remarks='" + remarks + '\'' +
                ", checklistid=" + checklistid +
                ", userid='" + userid + '\'' +
                ", fieldname='" + fieldname + '\'' +
                ", creationDate=" + creationDate +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", DocId=" + DocId +
                ", FiID=" + FiID +
                ", apiRelativePath='" + apiRelativePath + '\'' +
                ", updateStatus=" + updateStatus +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
