package com.softeksol.paisalo.jlgsourcing.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.DocumentStore;
import com.softeksol.paisalo.jlgsourcing.entities.PendingFi;
import com.softeksol.paisalo.jlgsourcing.fragments.FragmentLoanAppList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sachindra on 2016-10-01.
 */

/**
 * {@link RecyclerView.Adapter} that can display a {@link PendingFi} and makes a call to the
 * specified {@link FragmentLoanAppList.OnListFragmentLoanAppInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

public class AdapterRecViewListDocuments extends RecyclerView.Adapter<AdapterRecViewListDocuments.DucumentViewHolder> {

    private List<DocumentStore> mValues = new ArrayList<>();
    private Context mContext;
    private int mResId;

    protected ItemListener mListener;

    public AdapterRecViewListDocuments(Context context, int resId) {
        this.mContext = context;
        this.mResId = resId;
        mListener = null;
    }

    public AdapterRecViewListDocuments(Context context, int resId, ItemListener itemListener) {
        this.mContext = context;
        this.mResId = resId;
        mListener = itemListener;
    }

    @Override
    public DucumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.mResId, parent, false);
        return new DucumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DucumentViewHolder holder, final int position) {
        DocumentStore documentStore = mValues.get(position);
        holder.setData(documentStore);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public void updateList(List<DocumentStore> data) {
        mValues = data;
        notifyDataSetChanged();
    }

    public interface ItemListener {
        void onKycCapture(DocumentStore item);

        void onKycUpload(DocumentStore item, View view);
    }

    class DucumentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        public TextView docName;
        public TextView docRemarks;
        public ImageView docThumbnail;
        private CardView cardView;
        DocumentStore documentStore;
        private View mView;

        public DucumentViewHolder(View view) {
            super(view);
            mView = view;
            view.setOnClickListener(this);
            docName = (TextView) view.findViewById(R.id.tv_document_name);
            docRemarks = (TextView) view.findViewById(R.id.tv_document_remarks);
            docThumbnail = (ImageView) view.findViewById(R.id.document_snapshot);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }

        public void setData(DocumentStore data) {
            this.documentStore = data;
            if (documentStore != null) {
                if (documentStore.exists()) {
                    if (documentStore.updateStatus) {
                        cardView.setCardBackgroundColor(Color.LTGRAY);
                    } else {
                        cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorLightRed));
                    }
                } else {
                    cardView.setCardBackgroundColor(Color.YELLOW);
                }
                if (documentStore.checklistid > 0) {
                    docName.setText(DocumentStore.getDocumentName(documentStore.checklistid));
                    docRemarks.setText(documentStore.remarks);
                } else {
                    docName.setText("Picture");
                    docRemarks.setText("Borrower");
                }
                if (documentStore.updateStatus) {
                    docThumbnail.setImageResource(R.mipmap.picture_uploaded);
                } else {
                    if (documentStore.imagePath != null) {
                        int dim = docThumbnail.getMaxHeight();
                        Glide.with(mContext).load(documentStore.imagePath).override(dim, dim).into(docThumbnail);
                    } else {
                        docThumbnail.setImageResource(R.drawable.take_photograph);
                    }
                }
            }
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                if (documentStore.exists()) {
                    if (!documentStore.updateStatus) {
                        showPopupMenu();
                    }
                } else {
                    mListener.onKycCapture(documentStore);
                }
            }
        }

        private void showPopupMenu() {
            PopupMenu popup = new PopupMenu(mContext, mView);
            try {
                Field[] fields = popup.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popup);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            popup.setOnMenuItemClickListener(this);
            popup.inflate(R.menu.menu_kyc_img_capture_upload);

            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_capture_kyc:
                    mListener.onKycCapture(documentStore);
                    break;
                case R.id.action_upload_kyc:
                    mListener.onKycUpload(documentStore, mView);
                    break;
            }
            return false;
        }
    }


}
