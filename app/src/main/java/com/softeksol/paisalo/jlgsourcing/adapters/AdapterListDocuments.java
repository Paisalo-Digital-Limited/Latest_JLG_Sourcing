package com.softeksol.paisalo.jlgsourcing.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityGuarantorEntry;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower;
import com.softeksol.paisalo.jlgsourcing.entities.Borrower_Table;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor;
import com.softeksol.paisalo.jlgsourcing.entities.Guarantor_Table;
import com.softeksol.paisalo.jlgsourcing.entities.ViewHolders.DocumentStoreViewHolder;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AdapterListDocuments extends ArrayAdapter<DocumentStore> {
    private final Context context;
    private int resId;

	/*public AdapterListDocuments(Context context, List<DocumentStore> values) {
		super(context, resId, values);
		this.context = context;
	}*/

    public AdapterListDocuments(Context context, int resId, List<DocumentStore> values) {
        super(context, resId, values);
        this.resId = resId;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // create a ViewHolder reference
        DocumentStoreViewHolder holder;

        // check to see if the reused view is null or not, if is not null then
        // reuse it
        DocumentStore documentStore = getItem(position);
        if (view == null) {
            holder = new DocumentStoreViewHolder();
            view = inflater.inflate(resId, (ViewGroup) null);
            holder.Name = (TextView) view.findViewById(R.id.tvKycItemLayoutName);
            holder.Type = (TextView) view.findViewById(R.id.tvKycItemLayoutType);
            holder.DocName = (TextView) view.findViewById(R.id.tvKycItemLayoutDocType);
            holder.Remarks = (TextView) view.findViewById(R.id.tvKycItemLayoutRemarks);
            holder.Aadhar = (TextView) view.findViewById(R.id.tvKycItemLayoutAadhar);
            holder.DocThumbnail = (ImageView) view.findViewById(R.id.imgViewKycItemLayout);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.pbKycItemLayoutUploadStatus);
            holder.fiCode = (TextView) view.findViewById(R.id.tvKycItemLayoutFiCode);
            holder.cardViewForKycCaptureImage = (CardView) view.findViewById(R.id.cardViewForKycCaptureImage);

            view.setTag(holder);
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (DocumentStoreViewHolder) view.getTag();
        }

        if (documentStore != null) {

           // Log.e("DocumentStoreImagePath1",documentStore.imagePath+"");
            if (documentStore.GuarantorSerial == 0) {
                Borrower borrower = SQLite.select().from(Borrower.class).where(Borrower_Table.FiID.eq(documentStore.FiID)).querySingle();
                holder.Name.setText(borrower.getBorrowerName());
                holder.Aadhar.setText(borrower.aadharid);
            }
            else {
                Guarantor guarantor = SQLite.select().from(Guarantor.class)
                        .where(Guarantor_Table.FiID.eq(documentStore.FiID))
                        .and(Guarantor_Table.GrNo.eq(documentStore.GuarantorSerial))
                        .querySingle();
                holder.Name.setText(guarantor.getName());
                holder.Aadhar.setText(guarantor.getAadharid());
            }

            holder.Type.setText(documentStore.GuarantorSerial == 0 ? "B" : "G" +
                    "" +
                    "" +
                    "" + String.valueOf(documentStore.GuarantorSerial));

            holder.Remarks.setText(documentStore.remarks);

            if (!documentStore.exists()) holder.cardViewForKycCaptureImage.setBackgroundColor(Color.YELLOW);

            else holder.cardViewForKycCaptureImage.setBackgroundColor(Color.WHITE);

            if (documentStore.checklistid > 0) {
                holder.DocName.setText(DocumentStore.getDocumentName(documentStore.checklistid));
            }
            else {
                holder.DocName.setText("Picture");
            }

            Log.e("DocumentStoreImagePath",documentStore.imagePath+"");
//            Log.e("DocumentStoreImageSRC",documentStore.imageshow+"");

            if (holder.DocThumbnail != null) {
                if (documentStore.updateStatus) {
                    holder.DocThumbnail.setImageResource(R.mipmap.picture_uploaded);
                }
                else {
                    if (documentStore.imagePath != null) {
                        Log.e("DocumentStoreImagePath",documentStore.imagePath+"");

                       // Toast.makeText(context, "DocumentStoreImage: "+ documentStore.imagePath+"", Toast.LENGTH_SHORT).show();


                        //holder.DocThumbnail.setImageBitmap(StringToBitmap(documentStore.imageshow));


//                        Picasso.get().load(new File(documentStore.imagePath)).into(holder.DocThumbnail);
////                        holder.DocThumbnail.setImageBitmap(StringToBitmap(documentStore.imagePath));
//
                        setImagepath(new File(documentStore.imagePath),holder);


                        //Glide.with(context).load(documentStore.imagePath).override(100, 100).into(holder.DocThumbnail);
                    } else {
                        holder.DocThumbnail.setImageResource(R.drawable.take_photograph);
                    }
                }
            }
            if (holder.fiCode != null) {
                holder.fiCode.setText(String.valueOf(documentStore.ficode) + "/" + documentStore.Creator);
            }
        }
        return view;
    }



    private Bitmap StringToBitmap(String string){
        try {
            byte [] encodebyte  = Base64.decode(string, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodebyte,0,encodebyte.length);
        } catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    private void setImagepath(File file, DocumentStoreViewHolder holder) {

//        File imgFile = new  File("/sdcard/Images/test_image.jpg");

        Log.e("CheckingFileOnly",file.getAbsolutePath()+"");

        //Toast.makeText(context, "FileCheckingADAPTER: "+ file.getAbsolutePath()+"", Toast.LENGTH_SHORT).show();

        if(file.length() != 0){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            //ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

            if (myBitmap!=null){
                //Toast.makeText(context, "Bitmap: "+myBitmap+"", Toast.LENGTH_SHORT).show();
                holder.DocThumbnail.setImageBitmap(myBitmap);
            }else {
                Toast.makeText(context, "BitmapNull_ADAPTER", Toast.LENGTH_SHORT).show();
                Log.e("BitmapImage","Null");
            }
        }
        else  {
            Log.e("FileAdapter",file.getAbsolutePath()+"");
            //Toast.makeText(context, "FilepathAdapter Empty", Toast.LENGTH_SHORT).show();

        }
    }


}
