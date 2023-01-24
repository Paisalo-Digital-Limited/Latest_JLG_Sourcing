package com.softeksol.paisalo.jlgsourcing;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.softeksol.paisalo.jlgsourcing.Utilities.IglPreferences;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityLogin;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerExtra;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyExpenses;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyLoan;
import com.softeksol.paisalo.jlgsourcing.entities.BorrowerFamilyMember;


/**
 * Created by sachindra on 2016-10-03.
 */


@Database(name = "PAISALO_SRC_DB", version = DbIGL.VERSION)
public class DbIGL {
    //public static final String NAME = BuildConfig.DATABASE_NAME;
   //public static final String NAME = IglPreferences.getPrefString(, SEILIGL.DATABASE_NAME, "");

    public static final int VERSION = 13;

    /*@Migration(version = 5, database = DbIGL.class)
    public static class Migration5 extends AlterTableMigration<Guarantor> {

        public Migration5(Class<Guarantor> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "isAadharVerified");
        }
    }*/

    /*@Migration(version = 5, database = DbIGL.class)
    public static class Migration5 extends AlterTableMigration<Guarantor> {

        public Migration5(Class<Guarantor> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "isAadharVerified");
        }
    }*/
    /*@Migration(version = 6, database = DbIGL.class)
    public static class Migration6 extends AlterTableMigration<Guarantor> {

        public Migration6(Class<Guarantor> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "isAadharVerified");
        }
    }*/
    @Migration(version = 8, database = DbIGL.class)
    public static class Migration8 extends AlterTableMigration<BorrowerFamilyExpenses> {

        public Migration8(Class<BorrowerFamilyExpenses> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "HomeType");
            addColumn(SQLiteType.TEXT, "HomeRoofType");
            addColumn(SQLiteType.TEXT, "ToiletType");
            addColumn(SQLiteType.TEXT, "LivingWSpouse");
        }
    }

    @Migration(version = 9, database = DbIGL.class)
    public static class Migration9 extends AlterTableMigration<Borrower> {

        public Migration9(Class<Borrower> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "isCurrentAddressDifferent");
        }
    }

    @Migration(version = 10, database = DbIGL.class)
    public static class Migration10 extends AlterTableMigration<BorrowerFamilyLoan> {

        public Migration10(Class<BorrowerFamilyLoan> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "AutoID");
        }
    }

    @Migration(version = 11, database = DbIGL.class)
    public static class Migration11 extends AlterTableMigration<BorrowerFamilyMember> {

        public Migration11(Class<BorrowerFamilyMember> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "AutoID");
        }
    }

    @Migration(version = 12, database = DbIGL.class)
    public static class Migration12 extends AlterTableMigration<Borrower> {

        public Migration12(Class<Borrower> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT, "FatherName");
            addColumn(SQLiteType.TEXT, "MotherName");
        }
    }
    @Migration(version = 13, database = DbIGL.class)
    public static class Migration13 extends AlterTableMigration<BorrowerExtra> {

        public Migration13(Class<BorrowerExtra> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.TEXT,"AGRICULTURAL_INCOME");
                    addColumn(SQLiteType.TEXT,"SOC_ATTR_2_INCOME");
            addColumn(SQLiteType.TEXT,"SOC_ATTR_3_LAND_HOLD");
                    addColumn(SQLiteType.TEXT,"SOC_ATTR_4_SPL_ABLD");
            addColumn(SQLiteType.TEXT,"SOC_ATTR_5_SPL_SOC_CTG");
                    addColumn(SQLiteType.TEXT,"EDUCATION_CODE");
            addColumn(SQLiteType.TEXT,"ANNUAL_INCOME");
                    addColumn(SQLiteType.TEXT,"PLACE_OF_BIRTH");
                addColumn(SQLiteType.TEXT,"EMAIL_ID");
                    addColumn(SQLiteType.TEXT,"VISUALLY_IMPAIRED_YN");
            addColumn(SQLiteType.TEXT,"FORM60_TNX_DT");
                    addColumn(SQLiteType.TEXT,"FORM60_SUBMISSIONDATE");
            addColumn(SQLiteType.TEXT,"FORM60_PAN_APPLIED_YN");
                    addColumn(SQLiteType.TEXT,"MOTHER_TITLE");
            addColumn(SQLiteType.TEXT,"MOTHER_FIRST_NAME");
                    addColumn(SQLiteType.TEXT,"MOTHER_MIDDLE_NAME");
            addColumn(SQLiteType.TEXT,"MOTHER_LAST_NAME");
                    addColumn(SQLiteType.TEXT,"MOTHER_MAIDEN_NAME");
            addColumn(SQLiteType.TEXT,"SPOUSE_TITLE");
                    addColumn(SQLiteType.TEXT,"SPOUSE_FIRST_NAME");
            addColumn(SQLiteType.TEXT,"SPOUSE_MIDDLE_NAME");
                    addColumn(SQLiteType.TEXT,"SPOUSE_LAST_NAME");
            addColumn(SQLiteType.TEXT,"FORM60SUBMISSIONDATE");
                    addColumn(SQLiteType.TEXT,"PAN_APPLIED_FLAG");
            addColumn(SQLiteType.TEXT,"OTHER_THAN_AGRICULTURAL_INCOME");
                    addColumn(SQLiteType.TEXT,"APPLICNT_TITLE");
            addColumn(SQLiteType.TEXT,"MARITAL_STATUS");
                    addColumn(SQLiteType.TEXT,"OCCUPATION_TYPE");
            addColumn(SQLiteType.TEXT,"RESERVATN_CATEGORY");
                    addColumn(SQLiteType.TEXT,"FATHER_TITLE");
            addColumn(SQLiteType.TEXT,"FATHER_FIRST_NAME");
                    addColumn(SQLiteType.TEXT,"FATHER_MIDDLE_NAME");
            addColumn(SQLiteType.TEXT,"FATHER_LAST_NAME");
        }
    }
}
