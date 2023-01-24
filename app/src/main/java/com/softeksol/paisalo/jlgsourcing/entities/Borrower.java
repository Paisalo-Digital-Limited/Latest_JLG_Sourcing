package com.softeksol.paisalo.jlgsourcing.entities;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.softeksol.paisalo.jlgsourcing.BuildConfig;
import com.softeksol.paisalo.jlgsourcing.DbIGL;
import com.softeksol.paisalo.jlgsourcing.Utilities.Utils;
import com.softeksol.paisalo.jlgsourcing.Utilities.Verhoeff;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerFamilyLoanDTO;
import com.softeksol.paisalo.jlgsourcing.entities.dto.BorrowerFamilyMemberDTO;
import com.softeksol.paisalo.jlgsourcing.enums.EnumApiPath;
import com.softeksol.paisalo.jlgsourcing.enums.EnumFieldName;
import com.softeksol.paisalo.jlgsourcing.enums.EnumImageTags;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sachindra on 2015-10-01.
 */
@ModelContainer
@Table(database = DbIGL.class)
public class Borrower extends BaseModel implements Serializable {

    @Expose
    @Column
    public long Code;

    @Expose
    @Column
    public String Tag;

    @Expose
    @Column
    public String FI_NO;

    @Expose
    @Column
    public String DelCode;

    @Expose
    @Column
    public String A_Code;

    @Expose
    @Column
    public String Case_City;

    @Expose
    @Column
    public Date DT;

    @Expose
    @Column
    public String Approved;

    @Expose
    @Column
    public String Saved;

    @Expose
    @Column
    public int Loan_Amt;

    @Expose
    @Column
    public String Loan_Duration;

    @Expose
    @Column
    public String Loan_Reason;

    @Expose
    @Column
    public String ini;

    @Expose
    @Column
    public String Fname;

    @Expose
    @Column
    public String Mname = "";

    @Expose
    @Column
    public String Lname = "";

    @Expose
    @Column
    public String F_ini;

    @Expose
    @Column
    public String F_fname;

    @Expose
    @Column
    public String F_mname = "";

    @Expose
    @Column
    public String F_lname = "";

    @Expose
    @Column
    public String Business_Detail = "";

    @Expose
    @Column
    public int Income;

    @Expose
    @Column
    public Date DOB;

    @Expose
    @Column
    public String Cast = "";

    @Expose
    @Column
    public String P_Add1 = "";

    @Expose
    @Column
    public String P_add2 = "";

    @Expose
    @Column
    public String P_add3 = "";

    @Expose
    @Column
    public String P_city = "";

    @Expose
    @Column
    public String P_ph1;

    @Expose
    @Column
    public String P_ph2;

    @Expose
    @Column
    public String P_ph3;

    @Expose
    @Column
    public String T_Add1;

    @Expose
    @Column
    public String T_add2;

    @Expose
    @Column
    public String T_add3;

    @Expose
    @Column
    public String T_city;

    @Expose
    @Column
    public String T_ph1;

    @Expose
    @Column
    public String T_ph2;

    @Expose
    @Column
    public String T_ph3;

    @Expose
    @Column
    public String O_Add1;

    @Expose
    @Column
    public String O_Add2;

    @Expose
    @Column
    public String O_Add3;

    @Expose
    @Column
    public String O_City;

    @Expose
    @Column
    public String O_Ph1;

    @Expose
    @Column
    public String O_Ph2;

    @Expose
    @Column
    public String O_Ph3;

    @Expose
    @Column
    public String Identity;

    @Expose
    @Column
    public String Identity_no;

    @Expose
    @Column
    public String bank_ac_no;

    @Expose
    @Column
    public String Bank_Address;

    @Expose
    @Column
    public String Res_type;

    @Expose
    @Column
    public String House_Owner;

    @Expose
    @Column
    public int Rent_of_House;

    @Expose
    @Column
    public String Property_Det;

    @Expose
    @Column
    public int FAmily_member;

    @Expose
    @Column
    public String Enc_Property;

    @Expose
    @Column
    public String Main_Title_Deed;

    @Expose
    @Column
    public String Lit_Det;

    @Expose
    @Column
    public String Sec_Details;

    @Expose
    @Column
    public String FI_REPORT;

    @Expose
    @Column
    public String Flat_Typ;

    @Expose
    @Column
    public String Near_House;

    @Expose
    @Column
    public String House_Loan;

    @Expose
    @Column
    public int Loan_EMi;

    @Expose
    @Column
    public String House_Identity;

    @Expose
    @Column
    public String Live_IN_City;

    @Expose
    @Column
    public String Live_In_Present_Place;

    @Expose
    @Column
    public int Area_Of_House;

    @Expose
    @Column
    public String House_Locality;

    @Expose
    @Column
    public String House_Interior;

    @Expose
    @Column
    public String Two_Wh_From;

    @Expose
    @Column
    public String Two_Wh_Modal;

    @Expose
    @Column
    public String Two_Wh_Make;

    @Expose
    @Column
    public String Two_Wh_Regdno;

    @Expose
    @Column
    public String Four_Wh_From;

    @Expose
    @Column
    public String four_Wh_Modal;

    @Expose
    @Column
    public String four_Wh_Make;

    @Expose
    @Column
    public String four_Wh_Regdno;

    @Expose
    @Column
    public String Vehicle_USe_By;

    @Expose
    @Column
    public String Oth_Prop_Det;

    @Expose
    @Column
    public String person_Contact_place;

    @Expose
    @Column
    public String Oth_Contact_Place;

    @Expose
    @Column
    public String Verified_phone;

    @Expose
    @Column
    public String SEL;

    @Expose
    @Column
    public String CASE_NO;

    @Expose
    @Column
    public String Creator;

    @Expose
    @Column
    public String UserID;

    @Expose
    @Column
    public String Auth_UserID;

    @Expose
    @Column
    public Date Auth_Date;

    @Expose
    @Column
    public Date Creation_Date;

    @Expose
    @Column
    public String Mod_Type;

    @Expose
    @Column
    public String Last_Mod_UserID;

    @Expose
    @Column
    public Date Last_Mod_Date;

    @Expose
    @Column
    public String GroupCode;

    @Expose
    @Column
    public String CityCode;

    @Expose
    @Column
    public String Gender;

    @Expose
    @Column
    public String Religion;

    @Expose
    @Column
    public int LandHolding;

    @Expose
    @Column
    public String ExServiceMan;

    @Expose
    @Column
    public int Age;

    @Expose
    @Column
    public int p_pin;

    @Expose
    @Column
    public int t_pin;

    @Expose
    @Column
    public int o_pin;

    @Expose
    @Column
    public String p_state;

    @Expose
    @Column
    public String o_state;

    @Expose
    @Column
    public String t_state;

    @Expose
    @Column
    public String PanNO;

    @Expose
    @Column
    public String aadharid;

    @Expose
    @Column
    public String voterid;

    @Expose
    @Column
    public String drivinglic;

    @Expose
    @Column
    public String isCustvisited;

    @Expose
    @Column
    public String SmCode;

    @Expose
    @Column
    public String OldCaseCode;

    @Expose
    @Column
    public String isAadharVerified;

    @Expose
    @Column
    public String isMarried;

    @Expose
    @Column
    public String BankName;

    @Expose
    @Column
    public String BankAcType;

    @Expose
    @Column
    public Date BankAcOpenDt;

    @Expose
    @Column
    public float Latitude;

    @Expose
    @Column
    public float Longitude;

    @Expose
    @Column
    public Date GeoDateTime;

    @Expose
    @Column
    public String RelationWBorrower;

    @Expose
    @Column
    public String AMApproval;

    @Expose
    @Column
    public String AMRemarks;

    @Expose
    @Column
    public String KYCUUID;

    @Expose
    public BorrowerExtra fiExtra;

    @Expose
    public BorrowerExtraBank fiExtraBank;

    @Column
    @PrimaryKey(autoincrement = true)
    public long FiID;

    List<Guarantor> fiGuarantors;

    @Expose
    public List<BorrowerFamilyMember> fiFamMems;

    @Expose
    public List<BorrowerFamilyLoan> fiFamLoans;

    @Expose
    public BorrowerFamilyExpenses fiFamExpenses;

    @Column
    public String isCurrentAddressDifferent = "N";


    public Borrower(String creator, String tag, String groupCode, String cityCode, String userId) {
        this.Creator = creator;
        this.Tag = tag;
        this.GroupCode = groupCode;
        this.CityCode = cityCode;
        this.UserID = userId;
        this.fiExtra = getBorrowerExtra();
        this.fiExtraBank = getBorrowerExtraBank();
        this.fiFamExpenses = getFiFamilyExpenses();
    }

    public Borrower() {
    }

    public BorrowerExtra getBorrowerExtra() {
        fiExtra = SQLite.select()
                .from(BorrowerExtra.class)
                .where(BorrowerExtra_Table.FiID.eq(FiID))
                .querySingle();
        return fiExtra;
    }

    public void associateExtra(BorrowerExtra borrowerExtra) {
        borrowerExtra.Code = this.Code;
        borrowerExtra.Creator = this.Creator;
        borrowerExtra.Tag = this.Tag;
        borrowerExtra.FiID = this.FiID;
        this.fiExtra = borrowerExtra; // convenience conversion
    }

    public BorrowerExtraBank getBorrowerExtraBank() {
        fiExtraBank = SQLite.select()
                .from(BorrowerExtraBank.class)
                .where(BorrowerExtraBank_Table.FiID.eq(FiID))
                .querySingle();
        return fiExtraBank;
    }

    public void
    associateExtraBank(BorrowerExtraBank borrowerExtraBank) {
        borrowerExtraBank.Code = this.Code;
        borrowerExtraBank.Creator = this.Creator;
        borrowerExtraBank.Tag = this.Tag;
        borrowerExtraBank.FiID = this.FiID;
        this.fiExtraBank = borrowerExtraBank; // convenience conversion
    }

    public static Borrower getBorrower(long borrowerID) {

        Borrower borrower = SQLite.select()
                .from(Borrower.class)
                .where(Borrower_Table.FiID.eq(borrowerID))
                .querySingle();
        borrower.fiExtra = SQLite.select()
                .from(BorrowerExtra.class)
                .where(BorrowerExtra_Table.FiID.eq(borrowerID))
                .querySingle();
        borrower.fiExtraBank = SQLite.select()
                .from(BorrowerExtraBank.class)
                .where(BorrowerExtraBank_Table.FiID.eq(borrowerID))
                .querySingle();
        borrower.fiFamExpenses = SQLite.select()
                .from(BorrowerFamilyExpenses.class)
                .where(BorrowerFamilyExpenses_Table.FiID.eq(borrowerID))
                .querySingle();
        return borrower;
    }

    public static Borrower getBorrower(long fiCode, String creator) {

        Borrower borrower = SQLite.select()
                .from(Borrower.class)
                .where(Borrower_Table.Code.eq(fiCode))
                .and(Borrower_Table.Creator.eq(creator))
                .querySingle();
        if (borrower != null) {
            borrower = getBorrower(borrower.FiID);
        }
        return borrower;
    }

    public static Borrower getBorrower(String aadharid) {

        Borrower borrower = SQLite.select()
                .from(Borrower.class)
                .where(Borrower_Table.aadharid.eq(aadharid))
                .querySingle();
        if (borrower != null) {
            borrower = getBorrower(borrower.FiID);
        }
        return borrower;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "fiFamMems")
    public List<BorrowerFamilyMember> getFiFamilyMembers() {
        //if ((fiGuarantors == null || fiGuarantors.isEmpty())) {
        fiFamMems = SQLite.select()
                .from(BorrowerFamilyMember.class)
                .where(BorrowerFamilyMember_Table.borrowerForeignKeyContainer_FiID.eq(FiID))
                .queryList();
        //}
        return fiFamMems;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "fiGuarantors")
    public List<Guarantor> getFiGuarantors() {
        //if ((fiGuarantors == null || fiGuarantors.isEmpty())) {
        fiGuarantors = SQLite.select()
                .from(Guarantor.class)
                .where(Guarantor_Table.borrowerForeignKeyContainer_FiID.eq(FiID))
                .orderBy(Guarantor_Table.GrNo, true)
                .queryList();
        //}
        return fiGuarantors;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "fiFamLoans")
    public List<BorrowerFamilyLoan> getFiFamilyLoans() {
        fiFamLoans = SQLite.select()
                .from(BorrowerFamilyLoan.class)
                .where(BorrowerFamilyLoan_Table.borrowerForeignKeyContainer_FiID.eq(FiID))
                .queryList();
        //}
        return fiFamLoans;
    }

    public BorrowerFamilyExpenses getFiFamilyExpenses() {
        BorrowerFamilyExpenses expenses = SQLite.select()
                .from(BorrowerFamilyExpenses.class)
                .where(BorrowerFamilyExpenses_Table.FiID.eq(FiID))
                .querySingle();
        return Utils.NullIf(expenses, new BorrowerFamilyExpenses());
    }

    public void setNames(String BorrowerName) {
        String[] bNames = BorrowerName.replace("  ", " ").split(" ");
        Lname = "";
        Mname = "";
        if (bNames.length > 0) {
            Fname = bNames[0];
        }
        if (bNames.length > 1) {
            Lname = bNames[bNames.length - 1];
        }
        if (bNames.length > 2) {
            Mname = "";
            for (int i = 1; i < bNames.length - 1; i++) {
                Mname += (i > 1 ? " " : "") + bNames[i];
            }
        }
    }

    public void setGuardianNames(String GuardianName) {
        String[] bNames = GuardianName.split(" ");
        F_lname = "";
        F_mname = "";
        if (bNames.length > 0) {
            F_fname = bNames[0];
        }
        if (bNames.length > 1) {
            F_lname = bNames[bNames.length - 1];
        }
        if (bNames.length > 2) {
            F_mname = "";
            for (int i = 1; i < bNames.length - 1; i++) {
                F_mname += (i > 1 ? " " : "") + bNames[i];
            }
        }
    }

    public String getBorrowerName() {
        return TextUtils.join(" ", new String[]{Utils.NullIf(Fname, ""), Utils.NullIf(Mname, ""), Utils.NullIf(Lname, "")}).replace("  ", " ").trim();
    }

    public String getGurName() {
        return TextUtils.join(" ", new String[]{Utils.NullIf(F_fname, ""), Utils.NullIf(F_mname, ""), Utils.NullIf(F_lname, "")}).replace("  ", " ").trim();
    }

    public String getPerAddress() {
        return TextUtils.join(" ", new String[]{Utils.NullIf(P_Add1, ""), Utils.NullIf(P_add2, ""), Utils.NullIf(P_add3, ""), Utils.NullIf(P_city, "")}).replace("  ", " ").trim();
    }

    public void updateFiGuarantors() {
        List<Guarantor> guarantors = SQLite.select()
                .from(Guarantor.class)
                .where(Guarantor_Table.borrowerForeignKeyContainer_FiID.eq(FiID))
                .queryList();
        for (Guarantor guarantor : guarantors) {
            guarantor.setFi_Code(Code);
            guarantor.save();
        }
        fiGuarantors = null;
    }

    public void setPicture(String imagePath) {
        DocumentStore documentStore = SQLite.select()
                .from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(this.FiID))
                .and(DocumentStore_Table.GuarantorSerial.eq(0))
                .and(DocumentStore_Table.checklistid.eq(0))
                .querySingle();
        if (documentStore == null) {
            documentStore = new DocumentStore();
            documentStore.FiID = this.FiID;
            documentStore.Creator = this.Creator;
            documentStore.ficode = this.Code;
            documentStore.fitag = this.Tag;
            documentStore.userid = this.UserID;
            documentStore.checklistid = 0;
            documentStore.remarks = "Picture";
            documentStore.imageTag = EnumImageTags.Borrower.getImageTag();
            documentStore.fieldname = EnumFieldName.Borrower.getFieldName();
            documentStore.apiRelativePath = EnumApiPath.BorrowerApi.getApiPath();
            documentStore.GuarantorSerial = 0;
        } else if (documentStore.ficode == 0) {
            documentStore.ficode = this.Code;
            documentStore.fitag = this.Tag;
        }
        documentStore.imagePath = imagePath;
        documentStore.save();
    }

//    public void setPicture(String imagePath,String imageShow) {
//        DocumentStore documentStore = SQLite.select()
//                .from(DocumentStore.class)
//                .where(DocumentStore_Table.FiID.eq(this.FiID))
//                .and(DocumentStore_Table.GuarantorSerial.eq(0))
//                .and(DocumentStore_Table.checklistid.eq(0))
//                .querySingle();
//        if (documentStore == null) {
//            documentStore = new DocumentStore();
//            documentStore.FiID = this.FiID;
//            documentStore.Creator = this.Creator;
//            documentStore.ficode = this.Code;
//            documentStore.fitag = this.Tag;
//            documentStore.userid = this.UserID;
//            documentStore.checklistid = 0;
//            documentStore.remarks = "Picture";
//            documentStore.imageTag = EnumImageTags.Borrower.getImageTag();
//            documentStore.fieldname = EnumFieldName.Borrower.getFieldName();
//            documentStore.apiRelativePath = EnumApiPath.BorrowerApi.getApiPath();
//            documentStore.GuarantorSerial = 0;
//        } else if (documentStore.ficode == 0) {
//            documentStore.ficode = this.Code;
//            documentStore.fitag = this.Tag;
//        }
//        documentStore.imagePath = imagePath;
//        documentStore.imageshow = imageShow;
//        documentStore.save();
//    }

    public void setPicture(Uri imageUri) {
        setPicture(imageUri.getPath());
    }

    public DocumentStore getPictureStore() {
        DocumentStore documentStore = SQLite.select()
                .from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(this.FiID))
                .and(DocumentStore_Table.GuarantorSerial.eq(0))
                .and(DocumentStore_Table.checklistid.eq(0))
                .querySingle();
        return documentStore;
    }


    public Uri getPicture() {
        DocumentStore documentStore = getPictureStore();
        return documentStore == null ? null : (documentStore.imagePath == null ? null : Uri.parse(documentStore.imagePath));
    }

//    public String getPictureborrower(){
//        DocumentStore documentStore = getPictureStore();
//
//        return documentStore == null ? null : (documentStore.imageshow == null ? null : documentStore.imageshow);
//    }

    public boolean getPictureUpdated() {
        DocumentStore documentStore = getPictureStore();
        return documentStore.updateStatus;
    }

    public Uri getPicture(Context context) {
        Uri uri = null;
        DocumentStore documentStore = getPictureStore();
        if (documentStore != null) {
            if (documentStore.updateStatus) {
                uri = Uri.parse("android.resource://" + context.getPackageName() + "/mipmap/picture_uploaded");
            } else if (documentStore.imagePath != null) {
                uri = Uri.parse(documentStore.imagePath);
            }
        }
        //return documentStore==null?null:Uri.parse(documentStore.imagePath==null?"":documentStore.imagePath);
        return uri;
    }


    @Override
    public void delete() {
        SQLite.delete().from(DocumentStore.class).where(DocumentStore_Table.FiID.eq(this.FiID)).execute();
        SQLite.delete().from(BorrowerExtra.class).where(BorrowerExtra_Table.FiID.eq(this.FiID)).execute();
        SQLite.delete().from(BorrowerExtraBank.class).where(BorrowerExtraBank_Table.FiID.eq(this.FiID)).execute();
        SQLite.delete().from(Guarantor.class).where(Guarantor_Table.FiID.eq(this.FiID)).execute();
        SQLite.delete().from(BorrowerFamilyExpenses.class).where(BorrowerFamilyExpenses_Table.FiID.eq(this.FiID)).execute();
        SQLite.delete().from(BorrowerFamilyLoan.class).where(BorrowerFamilyLoan_Table.FiID.eq(this.FiID)).execute();
        SQLite.delete().from(BorrowerFamilyMember.class).where(BorrowerFamilyMember_Table.FiID.eq(this.FiID)).execute();
        super.delete();
    }

    @Override
    public void save() {
        //updateFiCode(this.Code,this.Tag);
        //saveAssociations();
        super.save();
        //updateFiId();
    }

    public void updateFiCode(long FiCode, String FiTag) {
        this.Code = FiCode;
        this.Tag = FiTag;
        this.save();
        saveAssociations();
        SQLite.update(Guarantor.class)
                .set(Guarantor_Table.fi_Code.eq(this.Code))
                .where(Guarantor_Table.FiID.eq(this.FiID)).execute();
        SQLite.update(BorrowerExtra.class)
                .set(BorrowerExtra_Table.Code.eq(this.Code), BorrowerExtra_Table.Tag.eq(this.Tag))
                .where(BorrowerExtra_Table.FiID.eq(this.FiID)).execute();
        SQLite.update(BorrowerExtraBank.class)
                .set(BorrowerExtraBank_Table.Code.eq(this.Code), BorrowerExtraBank_Table.Tag.eq(this.Tag))
                .where(BorrowerExtraBank_Table.FiID.eq(this.FiID)).execute();
        SQLite.update(BorrowerFamilyExpenses.class)
                .set(BorrowerFamilyExpenses_Table.Code.eq(this.Code), BorrowerFamilyExpenses_Table.Tag.eq(this.Tag))
                .where(BorrowerFamilyExpenses_Table.FiID.eq(this.FiID)).execute();
        SQLite.update(DocumentStore.class)
                .set(DocumentStore_Table.ficode.eq(this.Code), DocumentStore_Table.fitag.eq(this.Tag))
                .where(DocumentStore_Table.FiID.eq(this.FiID)).execute();
    }

    private void saveAssociations() {
        //fiFamExpenses.save();
        //fiExtra.save();
        for (BorrowerFamilyMember fiFamMem : fiFamMems) {
            fiFamMem.save();
        }
        for (BorrowerFamilyLoan fiFamLoan : fiFamLoans) {
            fiFamLoan.save();
        }
    }

    private void updateFiId() {
        /*SQLite.update(Guarantor.class)
                .set(Guarantor_Table.FiID.eq(this.FiID))
                .where(Guarantor_Table.fi_Code.eq(this.Code)).and(Guarantor_Table.Creator.eq(this.Creator)).execute();*/
        SQLite.update(BorrowerExtra.class)
                .set(BorrowerExtra_Table.FiID.eq(this.FiID))
                .where(BorrowerExtra_Table.Code.eq(this.Code), BorrowerExtra_Table.Creator.eq(this.Creator))
                .execute();
        SQLite.update(BorrowerExtraBank.class)
                .set(BorrowerExtraBank_Table.FiID.eq(this.FiID))
                .where(BorrowerExtraBank_Table.Code.eq(this.Code), BorrowerExtraBank_Table.Creator.eq(this.Creator))
                .execute();
        SQLite.update(BorrowerFamilyExpenses.class)
                .set(BorrowerFamilyExpenses_Table.FiID.eq(this.FiID))
                .where(BorrowerFamilyExpenses_Table.Code.eq(this.Code), BorrowerFamilyExpenses_Table.Creator.eq(this.Creator))
                .execute();
        SQLite.update(BorrowerFamilyMember.class)
                .set(BorrowerFamilyMember_Table.FiID.eq(this.FiID))
                .where(BorrowerFamilyMember_Table.Code.eq(this.Code), BorrowerFamilyMember_Table.Creator.eq(this.Creator))
                .execute();
        SQLite.update(BorrowerFamilyLoan.class)
                .set(BorrowerFamilyLoan_Table.FiID.eq(this.FiID))
                .where(BorrowerFamilyLoan_Table.Code.eq(this.Code), BorrowerFamilyLoan_Table.Creator.eq(this.Creator))
                .execute();
        SQLite.update(DocumentStore.class)
                .set(DocumentStore_Table.FiID.eq(this.FiID))
                .where(DocumentStore_Table.ficode.eq(this.Code), DocumentStore_Table.Creator.eq(this.Creator))
                .execute();
    }

    public boolean getDocUpdateStatus() {
        boolean updateStatus;
        updateStatus = SQLite.select().from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(FiID))
                .and(DocumentStore_Table.updateStatus.notEq(true))
                .count() > 0;
        return updateStatus;
    }

    public Map<String, String> validateLoanApplication(Context context) {
        int docCount = 3;
        Map<String, String> messages = new HashMap<>();
        if (this.aadharid == null || this.aadharid.trim().length() == 0) {
            messages.put("Borrower Aadhar", "Aadhar number is missing");
        } else if (this.aadharid.toUpperCase().contains("X")) {
            messages.put("Borrower Aadhar", "Aadhar number is not Valid");
        } else if (!Verhoeff.validateVerhoeff(this.aadharid)) {
            messages.put("Borrower Aadhar", "Aadhar number is not Valid");
        }
        if (isCurrentAddressDifferent.equals("Y")) {
            if (this.O_Add1 == null || this.O_Add1.length() < 10) {
                messages.put("Current Address", "Current Address 1 is not specified");
            }
            if (this.O_City == null || this.O_City.length() < 4) {
                messages.put("Current Address", "Current Address City is not specified");
            }
        }
        if (this.voterid == null && this.drivinglic == null && this.PanNO == null) {
            messages.put("No Document ID", "A valid document ID must be filled in");
        } else {
            if (this.voterid != null && this.voterid.length() > 0 && this.voterid.length() < 10) {
                messages.put("VoterID", "VoterID Number should be of 10 Digits");
                docCount += 2;
            }
            if (this.drivinglic != null && this.drivinglic.length() > 0 && this.drivinglic.length() < 10) {
                messages.put("Driving License", "Driving License Number should be of 10 Digits");
                docCount += 2;
            }
            if (this.PanNO != null && this.PanNO.length() > 0 && this.PanNO.length() != 10) {
                messages.put("Pan No", "PAN Number should be of 10 Digits");
                docCount++;
            }
        }

        if (this.P_ph3 == null || this.P_ph3.length() < 10) {
            messages.put("Mobile", "Mobile Number should be of 10 Digits");
        }
        if (this.bank_ac_no == null || this.bank_ac_no.length() < 5) {
            messages.put("Bank Account", "Check Bank Account No");
        }
        if (this.Enc_Property == null || this.Enc_Property.length() < 11 && this.BankName.length() < 2) {
            messages.put("Bank IFSC", "Check Bank IFSC Code");
        }

        if (!this.getPictureUpdated()) {
            if (this.getPicture() == null) {
                messages.put("Borrower Picture", "Borrower Picture Must be captured");
            }
        }
        if (this.isAadharVerified == null) {
            this.isAadharVerified = "N";
            this.save();
        }
        if (!this.isAadharVerified.equals("Y")) docCount += 2;

        int guarantorCount = this.getFiGuarantors().size();
        if (guarantorCount > 0) {
            int cnt = 0;
            for (Guarantor guarantor : this.getFiGuarantors()) {
                cnt++;
                docCount += 1;
                if (guarantor.getPictureStore() == null) {
                    messages.put("Guarantor " + cnt + " Picture", "Picture is missing");
                }

                if (guarantor.getAadharid() == null || guarantor.getAadharid().length() == 0) {
                    messages.put("Guarantor " + cnt + " Aadhar", "Aadhar number is missing");
                } else if (guarantor.getAadharid().toUpperCase().contains("X")) {
                    messages.put("Guarantor " + cnt + " Aadhar", "Aadhar number is not Valid");
                } else {
                    if (!Verhoeff.validateVerhoeff(guarantor.getAadharid())) {
                        messages.put("Guarantor " + cnt + " Aadhar", "Aadhar number is not Valid");
                    }
                }

                if (guarantor.getIsAadharVerified() == null) {
                    guarantor.setIsAadharVerified("N");
                    guarantor.save();
                }

                if (BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing") {
                    if (!guarantor.getIsAadharVerified().equals("Y")) docCount += 2;
                }

                //if(guarantor.getIsAadharVerified().equals("Y")) docCount=docCount+1; else docCount=docCount+3;
                /*if (guarantor.getVoterid()==null || guarantor.getVoterid().length()<10){ messages.put("Guarantor VoterID","Guarantor VoterID Number should be of 10 Digits"+ " for Guarantor #"+String.valueOf(cnt));}
                if(guarantor.getPicture()==null){
                    messages.put("Guarantor Picture","Picture Must be captured"+ " for Guarantor #"+String.valueOf(cnt));
                }*/
                if (guarantor.getVoterid() == null && guarantor.getPANNo() == null && guarantor.getDrivinglic() == null) {
                    messages.put("No Guarantor Document ID", "A valid Guarantor document ID must be filled in");
                } else {
                    if (guarantor.getVoterid() != null && guarantor.getVoterid().length() > 0 && guarantor.getVoterid().length() < 10) {
                        messages.put("Guarantor VoterID", "Guarantor VoterID Number should be of 10 Digits");
                        docCount += 2;
                    }
                    if (guarantor.getDrivinglic() != null && guarantor.getDrivinglic().length() > 0 && guarantor.getDrivinglic().length() < 10) {
                        messages.put("Guarantor Driving License", "Guarantor Driving License Number should be of 10 Digits");
                        docCount += 2;
                    }
                    if (guarantor.getPANNo() != null && guarantor.getPANNo().length() > 0 && guarantor.getPANNo().length() != 10) {
                        messages.put("Guarantor Pan No", "Guarantor PAN Number should be of 10 Digits");
                        docCount += 1;
                    }
                }
            }
        } else {
            if (BuildConfig.APPLICATION_ID == "com.softeksol.paisalo.jlgsourcing")
                messages.put("Guarantor", "At least one Guarantor should be present");
        }
        if (getFiDocuments().size() < docCount) {
            messages.put("Loan Application KYC", "Capture all the required KYC Documents");
        }
        return messages;
    }

    public Map<String, String> validateKyc(Context context) {
        int kycIdsCount = 0;
        Map<String, String> messages = new HashMap<>();
        if (this.aadharid == null || this.aadharid.trim().length() == 0) {
            messages.put("Borrower Aadhar", "Aadhar number is missing");
        } else if (this.aadharid.toUpperCase().contains("X")) {
            messages.put("Borrower Aadhar", "Aadhar number is not Valid");
        } else {
            if (!Verhoeff.validateVerhoeff(this.aadharid)) {
                messages.put("Borrower Aadhar", "Aadhar number is not Valid");
            }
        }
        if (this.P_ph3 == null || this.P_ph3.length() < 10) {
            messages.put("Mobile", "Mobile Number should be of 10 Digits");
        }
        if (this.P_Add1 == null || this.P_Add1.length() < 10) {
            messages.put("Address", "Address 1 is not specified");
        }
        if (this.P_city == null || this.P_city.length() < 4) {
            messages.put("City", "Address City is not specified");
        }
        if (this.p_pin == 0 || String.valueOf(this.p_pin).length() != 6) {
            messages.put("Pin Code", "Address Pin Code is not specified");
        }

        if (this.voterid == null && this.drivinglic == null && this.PanNO == null) {
            messages.put("No Document ID", "At least two valid document IDs must be filled in");
        } else {
            boolean vid = false, pan = false, dl = false;
            if (this.voterid != null) vid = true;
            if (this.drivinglic != null) dl = true;
            if (this.PanNO != null) pan = true;
            if (vid) {
                if (this.voterid.length() > 0 && this.voterid.length() < 10) {
                    messages.put("VoterID", "VoterID Number should be of 10 Digits");
                } else kycIdsCount++;
            }
            if (dl) {
                if (this.drivinglic.length() > 0 && this.drivinglic.length() < 10) {
                    messages.put("Driving License", "Driving License Number should be of 10 Digits");
                } else kycIdsCount++;
            }
            if (pan) {
                if (this.PanNO.length() > 0 && this.PanNO.length() != 10) {
                    messages.put("Pan No", "PAN Number should be of 10 Digits");
                } else if (Verhoeff.validatePan(this.PanNO)) kycIdsCount++;
            }
            if (kycIdsCount < 2) {
                messages.put("KYC Documents", "At least two KYC Documents required");
            }
        }

        if (this.isAadharVerified == null) {
            this.isAadharVerified = "N";
        }
        return messages;
    }

    public List<DocumentStore> getFiDocuments() {
        ConditionGroup conditionGroup = ConditionGroup.clause();
        ConditionGroup conditionGroup1 = ConditionGroup.clause();
        conditionGroup1.and(DocumentStore_Table.updateStatus.eq(false)).and(DocumentStore_Table.imagePath.isNotNull());
        return SQLite.select().from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(this.FiID))
                .and(conditionGroup.or(DocumentStore_Table.updateStatus.eq(true)).or(conditionGroup1))
                .queryList();

    }

    public List<DocumentStore> getBorrowerDocuments() {
        return SQLite.select().from(DocumentStore.class)
                .where(DocumentStore_Table.FiID.eq(this.FiID)).and(DocumentStore_Table.GuarantorSerial.eq(0))
                .queryList();
    }

    public void associateBorrowerFamilyExpenses(BorrowerFamilyExpenses familyExpenses) {
        familyExpenses.setCode(this.Code);
        familyExpenses.setCreator(this.Creator);
        familyExpenses.setTag(this.Tag);
        familyExpenses.setFiID(this.FiID);
        this.fiFamExpenses = familyExpenses; // convenience conversion
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "Code=" + Code +
                ", Tag='" + Tag + '\'' +
                ", FI_NO='" + FI_NO + '\'' +
                ", DelCode='" + DelCode + '\'' +
                ", A_Code='" + A_Code + '\'' +
                ", Case_City='" + Case_City + '\'' +
                ", DT=" + DT +
                ", Approved='" + Approved + '\'' +
                ", Saved='" + Saved + '\'' +
                ", Loan_Amt=" + Loan_Amt +
                ", Loan_Duration='" + Loan_Duration + '\'' +
                ", Loan_Reason='" + Loan_Reason + '\'' +
                ", ini='" + ini + '\'' +
                ", Fname='" + Fname + '\'' +
                ", Mname='" + Mname + '\'' +
                ", Lname='" + Lname + '\'' +
                ", F_ini='" + F_ini + '\'' +
                ", F_fname='" + F_fname + '\'' +
                ", F_mname='" + F_mname + '\'' +
                ", F_lname='" + F_lname + '\'' +
                ", Business_Detail='" + Business_Detail + '\'' +
                ", Income=" + Income +
                ", DOB=" + DOB +
                ", Cast='" + Cast + '\'' +
                ", P_Add1='" + P_Add1 + '\'' +
                ", P_add2='" + P_add2 + '\'' +
                ", P_add3='" + P_add3 + '\'' +
                ", P_city='" + P_city + '\'' +
                ", P_ph1='" + P_ph1 + '\'' +
                ", P_ph2='" + P_ph2 + '\'' +
                ", P_ph3='" + P_ph3 + '\'' +
                ", T_Add1='" + T_Add1 + '\'' +
                ", T_add2='" + T_add2 + '\'' +
                ", T_add3='" + T_add3 + '\'' +
                ", T_city='" + T_city + '\'' +
                ", T_ph1='" + T_ph1 + '\'' +
                ", T_ph2='" + T_ph2 + '\'' +
                ", T_ph3='" + T_ph3 + '\'' +
                ", O_Add1='" + O_Add1 + '\'' +
                ", O_Add2='" + O_Add2 + '\'' +
                ", O_Add3='" + O_Add3 + '\'' +
                ", O_City='" + O_City + '\'' +
                ", O_Ph1='" + O_Ph1 + '\'' +
                ", O_Ph2='" + O_Ph2 + '\'' +
                ", O_Ph3='" + O_Ph3 + '\'' +
                ", Identity='" + Identity + '\'' +
                ", Identity_no='" + Identity_no + '\'' +
                ", bank_ac_no='" + bank_ac_no + '\'' +
                ", Bank_Address='" + Bank_Address + '\'' +
                ", Res_type='" + Res_type + '\'' +
                ", House_Owner='" + House_Owner + '\'' +
                ", Rent_of_House=" + Rent_of_House +
                ", Property_Det='" + Property_Det + '\'' +
                ", FAmily_member=" + FAmily_member +
                ", Enc_Property='" + Enc_Property + '\'' +
                ", Main_Title_Deed='" + Main_Title_Deed + '\'' +
                ", Lit_Det='" + Lit_Det + '\'' +
                ", Sec_Details='" + Sec_Details + '\'' +
                ", FI_REPORT='" + FI_REPORT + '\'' +
                ", Flat_Typ='" + Flat_Typ + '\'' +
                ", Near_House='" + Near_House + '\'' +
                ", House_Loan='" + House_Loan + '\'' +
                ", Loan_EMi=" + Loan_EMi +
                ", House_Identity='" + House_Identity + '\'' +
                ", Live_IN_City='" + Live_IN_City + '\'' +
                ", Live_In_Present_Place='" + Live_In_Present_Place + '\'' +
                ", Area_Of_House=" + Area_Of_House +
                ", House_Locality='" + House_Locality + '\'' +
                ", House_Interior='" + House_Interior + '\'' +
                ", Two_Wh_From='" + Two_Wh_From + '\'' +
                ", Two_Wh_Modal='" + Two_Wh_Modal + '\'' +
                ", Two_Wh_Make='" + Two_Wh_Make + '\'' +
                ", Two_Wh_Regdno='" + Two_Wh_Regdno + '\'' +
                ", Four_Wh_From='" + Four_Wh_From + '\'' +
                ", four_Wh_Modal='" + four_Wh_Modal + '\'' +
                ", four_Wh_Make='" + four_Wh_Make + '\'' +
                ", four_Wh_Regdno='" + four_Wh_Regdno + '\'' +
                ", Vehicle_USe_By='" + Vehicle_USe_By + '\'' +
                ", Oth_Prop_Det='" + Oth_Prop_Det + '\'' +
                ", person_Contact_place='" + person_Contact_place + '\'' +
                ", Oth_Contact_Place='" + Oth_Contact_Place + '\'' +
                ", Verified_phone='" + Verified_phone + '\'' +
                ", SEL='" + SEL + '\'' +
                ", CASE_NO='" + CASE_NO + '\'' +
                ", Creator='" + Creator + '\'' +
                ", UserID='" + UserID + '\'' +
                ", Auth_UserID='" + Auth_UserID + '\'' +
                ", Auth_Date=" + Auth_Date +
                ", Creation_Date=" + Creation_Date +
                ", Mod_Type='" + Mod_Type + '\'' +
                ", Last_Mod_UserID='" + Last_Mod_UserID + '\'' +
                ", Last_Mod_Date=" + Last_Mod_Date +
                ", GroupCode='" + GroupCode + '\'' +
                ", CityCode='" + CityCode + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Religion='" + Religion + '\'' +
                ", LandHolding=" + LandHolding +
                ", ExServiceMan='" + ExServiceMan + '\'' +
                ", Age=" + Age +
                ", p_pin=" + p_pin +
                ", t_pin=" + t_pin +
                ", o_pin=" + o_pin +
                ", p_state='" + p_state + '\'' +
                ", o_state='" + o_state + '\'' +
                ", t_state='" + t_state + '\'' +
                ", PanNO='" + PanNO + '\'' +
                ", aadharid='" + aadharid + '\'' +
                ", voterid='" + voterid + '\'' +
                ", drivinglic='" + drivinglic + '\'' +
                ", isCustvisited='" + isCustvisited + '\'' +
                ", SmCode='" + SmCode + '\'' +
                ", OldCaseCode='" + OldCaseCode + '\'' +
                ", isAadharVerified='" + isAadharVerified + '\'' +
                ", isMarried='" + isMarried + '\'' +
                ", BankName='" + BankName + '\'' +
                ", BankAcType='" + BankAcType + '\'' +
                ", BankAcOpenDt=" + BankAcOpenDt +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", GeoDateTime=" + GeoDateTime +
                ", RelationWBorrower='" + RelationWBorrower + '\'' +
                ", AMApproval='" + AMApproval + '\'' +
                ", AMRemarks='" + AMRemarks + '\'' +
                ", KYCUUID='" + KYCUUID + '\'' +
                ", fiExtra=" + fiExtra +
                ", fiExtraBank=" + fiExtraBank +
                ", FiID=" + FiID +
                ", fiGuarantors=" + fiGuarantors +
                ", fiFamMems=" + fiFamMems +
                ", fiFamLoans=" + fiFamLoans +
                ", fiFamExpenses=" + fiFamExpenses +
                ", isCurrentAddressDifferent='" + isCurrentAddressDifferent + '\'' +
                '}';
    }

    public Borrower(BorrowerDTO borrowerDTO) {
        this.Auth_Date = borrowerDTO.Auth_Date;
        this.BankAcOpenDt = borrowerDTO.BankAcOpenDt;
        this.Creation_Date = borrowerDTO.Creation_Date;
        this.DOB = borrowerDTO.DOB;
        this.DT = borrowerDTO.DT;
        this.GeoDateTime = borrowerDTO.GeoDateTime;
        this.Last_Mod_Date = borrowerDTO.Last_Mod_Date;
        this.Latitude = borrowerDTO.Latitude;
        this.Longitude = borrowerDTO.Longitude;
        this.Age = borrowerDTO.Age;
        this.Area_Of_House = borrowerDTO.Area_Of_House;
        this.FAmily_member = borrowerDTO.FAmily_member;
        this.Income = borrowerDTO.Income;
        this.LandHolding = borrowerDTO.LandHolding;
        this.Loan_Amt = borrowerDTO.Loan_Amt;
        this.Loan_EMi = borrowerDTO.Loan_EMi;
        this.o_pin = borrowerDTO.O_Pin;
        this.p_pin = borrowerDTO.P_Pin;
        this.Rent_of_House = borrowerDTO.Rent_of_House;
        this.t_pin = borrowerDTO.T_Pin;
        this.Code = borrowerDTO.Code;
        this.A_Code = borrowerDTO.A_Code;
        this.aadharid = borrowerDTO.AadharID;
        this.AMApproval = borrowerDTO.AMApproval;
        this.AMRemarks = borrowerDTO.AMRemarks;
        this.Approved = borrowerDTO.Approved;
        this.Auth_UserID = borrowerDTO.Auth_UserID;
        this.bank_ac_no = borrowerDTO.Bank_Ac_No;
        this.Bank_Address = borrowerDTO.Bank_Address;
        this.BankAcType = borrowerDTO.BankAcType;
        this.BankName = borrowerDTO.BankName;
        this.Business_Detail = borrowerDTO.Business_Detail;
        this.Case_City = borrowerDTO.Case_City;
        this.CASE_NO = borrowerDTO.CASE_NO;
        this.Cast = borrowerDTO.Cast;
        this.CityCode = borrowerDTO.CityCode;
        this.Creator = borrowerDTO.Creator;
        this.DelCode = borrowerDTO.DelCode;
        this.drivinglic = borrowerDTO.DrivingLic;
        this.Enc_Property = borrowerDTO.Enc_Property;
        this.ExServiceMan = borrowerDTO.ExServiceMan;
        this.F_fname = borrowerDTO.F_Fname;
        this.F_ini = borrowerDTO.F_Ini;
        this.F_lname = borrowerDTO.F_Lname;
        this.F_mname = borrowerDTO.F_Mname;
        this.FI_NO = borrowerDTO.FI_NO;
        this.FI_REPORT = borrowerDTO.FI_REPORT;
        this.Flat_Typ = borrowerDTO.Flat_Typ;
        this.Fname = borrowerDTO.Fname;
        this.Four_Wh_From = borrowerDTO.Four_Wh_From;
        this.four_Wh_Make = borrowerDTO.four_Wh_Make;
        this.four_Wh_Modal = borrowerDTO.four_Wh_Modal;
        this.four_Wh_Regdno = borrowerDTO.four_Wh_Regdno;
        this.Gender = borrowerDTO.Gender;
        this.GroupCode = borrowerDTO.GroupCode;
        this.House_Identity = borrowerDTO.House_Identity;
        this.House_Interior = borrowerDTO.House_Interior;
        this.House_Loan = borrowerDTO.House_Loan;
        this.House_Locality = borrowerDTO.House_Locality;
        this.House_Owner = borrowerDTO.House_Owner;
        this.Identity = borrowerDTO.Identity;
        this.Identity_no = borrowerDTO.Identity_No;
        this.ini = borrowerDTO.Ini;
        this.isAadharVerified = borrowerDTO.isAadharVerified;
        this.isCustvisited = borrowerDTO.isCustvisited;
        this.isMarried = borrowerDTO.isMarried;
        this.KYCUUID = borrowerDTO.KYCUUID;
        this.Last_Mod_UserID = borrowerDTO.Last_Mod_UserID;
        this.Lit_Det = borrowerDTO.Lit_Det;
        this.Live_IN_City = borrowerDTO.Live_IN_City;
        this.Live_In_Present_Place = borrowerDTO.Live_In_Present_Place;
        this.Lname = borrowerDTO.Lname;
        this.Loan_Duration = borrowerDTO.Loan_Duration;
        this.Loan_Reason = borrowerDTO.Loan_Reason;
        this.Main_Title_Deed = borrowerDTO.Main_Title_Deed;
        this.Mname = borrowerDTO.Mname;
        this.Mod_Type = borrowerDTO.Mod_Type;
        this.Near_House = borrowerDTO.Near_House;
        this.O_Add1 = borrowerDTO.O_Add1;
        this.O_Add2 = borrowerDTO.O_Add2;
        this.O_Add3 = borrowerDTO.O_Add3;
        this.O_City = borrowerDTO.O_City;
        this.O_Ph1 = borrowerDTO.O_Ph1;
        this.O_Ph2 = borrowerDTO.O_Ph2;
        this.O_Ph3 = borrowerDTO.O_Ph3;
        this.o_state = borrowerDTO.O_State;
        this.OldCaseCode = borrowerDTO.OldCaseCode;
        this.Oth_Contact_Place = borrowerDTO.Oth_Contact_Place;
        this.Oth_Prop_Det = borrowerDTO.Oth_Prop_Det;
        this.P_Add1 = borrowerDTO.P_Add1;
        this.P_add2 = borrowerDTO.P_Add2;
        this.P_add3 = borrowerDTO.P_Add3;
        this.P_city = borrowerDTO.P_City;
        this.P_ph1 = borrowerDTO.P_Ph1;
        this.P_ph2 = borrowerDTO.P_Ph2;
        this.P_ph3 = borrowerDTO.P_Ph3;

        this.p_state = borrowerDTO.P_State;
        this.PanNO = borrowerDTO.PanNO;
        this.person_Contact_place = borrowerDTO.person_Contact_place;
        this.Property_Det = borrowerDTO.Property_Det;
        this.RelationWBorrower = borrowerDTO.RelationWBorrower;
        this.Religion = borrowerDTO.Religion;
        this.Res_type = borrowerDTO.Res_type;
        this.Saved = borrowerDTO.Saved;
        this.Sec_Details = borrowerDTO.Sec_Details;
        this.SEL = borrowerDTO.SEL;
        this.SmCode = borrowerDTO.SmCode;
        this.T_Add1 = borrowerDTO.T_Add1;
        this.T_add2 = borrowerDTO.T_Add2;
        this.T_add3 = borrowerDTO.T_Add3;
        this.T_city = borrowerDTO.T_City;
        this.T_ph1 = borrowerDTO.T_Ph1;
        this.T_ph2 = borrowerDTO.T_Ph2;
        this.T_ph3 = borrowerDTO.T_Ph3;
        this.t_state = borrowerDTO.T_State;
        this.Tag = borrowerDTO.Tag;
        this.Two_Wh_From = borrowerDTO.Two_Wh_From;
        this.Two_Wh_Make = borrowerDTO.Two_Wh_Make;
        this.Two_Wh_Modal = borrowerDTO.Two_Wh_Modal;
        this.Two_Wh_Regdno = borrowerDTO.Two_Wh_Regdno;
        this.UserID = borrowerDTO.UserID;
        this.Vehicle_USe_By = borrowerDTO.Vehicle_USe_By;
        this.Verified_phone = borrowerDTO.Verified_phone;
        this.voterid = borrowerDTO.VoterID;

        this.save();

        if (borrowerDTO.fiExtra == null)
            this.fiExtra = new BorrowerExtra();
        else
            this.fiExtra = new BorrowerExtra(borrowerDTO.fiExtra);
        this.associateExtra(this.fiExtra);
        this.fiExtra.save();

        if (borrowerDTO.fiExtraBank == null)
            this.fiExtraBank = new BorrowerExtraBank();
        else
            this.fiExtraBank = new BorrowerExtraBank(borrowerDTO.fiExtraBank);
        this.associateExtraBank(this.fiExtraBank);
        this.fiExtraBank.save();

        if (borrowerDTO.fiFamExpenses == null)
            this.fiFamExpenses = new BorrowerFamilyExpenses();
        else {
            this.fiFamExpenses = borrowerDTO.fiFamExpenses;
        }
        this.associateBorrowerFamilyExpenses(this.fiFamExpenses);
        this.fiFamExpenses.save();

        this.fiFamLoans = this.getFamilyLoans(borrowerDTO.fiFamLoans);
        this.fiFamMems = this.getFamilyMembers(borrowerDTO.fiFamMems);
        this.save();
    }

    private List<BorrowerFamilyLoan> getFamilyLoans(List<BorrowerFamilyLoanDTO> fiFamLoans) {
        List<BorrowerFamilyLoan> borrowerFamilyLoans = null;
        if (fiFamLoans != null) {
            borrowerFamilyLoans = new ArrayList<>();
            BorrowerFamilyLoan borrowerFamilyLoan;
            for (BorrowerFamilyLoanDTO dto : fiFamLoans) {
                borrowerFamilyLoan = dto.getFamilyLoan();
                borrowerFamilyLoan.associateBorrower(this);
                borrowerFamilyLoan.save();
                borrowerFamilyLoans.add(borrowerFamilyLoan);
            }
        }
        return borrowerFamilyLoans;
    }

    private List<BorrowerFamilyMember> getFamilyMembers(List<BorrowerFamilyMemberDTO> fiFamMems) {
        List<BorrowerFamilyMember> borrowerFamilyLoans = null;
        if (fiFamMems != null) {
            borrowerFamilyLoans = new ArrayList<>();
            BorrowerFamilyMember borrowerFamilyMember;
            for (BorrowerFamilyMemberDTO dto : fiFamMems) {
                borrowerFamilyMember = dto.getBorrowerFamilyMember();
                borrowerFamilyMember.associateBorrower(this);
                borrowerFamilyMember.save();
                borrowerFamilyLoans.add(borrowerFamilyMember);
            }
        }
        return borrowerFamilyLoans;
    }
    /*private List<BorrowerFamilyExpenses> getFamilyExpenses(List<BorrowerFamilyMemberDTO> fiFamMems){
        List<BorrowerFamilyExpenses> borrowerFamilyLoans=null;
        if(fiFamMems!=null){
            borrowerFamilyLoans= new ArrayList<>();
            BorrowerFamilyMember borrowerFamilyMember;
            for(BorrowerFamilyMemberDTO dto:fiFamMems){
                borrowerFamilyMember=dto.getBorrowerFamilyMember();
                borrowerFamilyMember.associateBorrower(this);
                borrowerFamilyMember.save();
                borrowerFamilyLoans.add(borrowerFamilyMember);
            }
        }
        return borrowerFamilyLoans;
    }*/

}
