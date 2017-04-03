package io.charlag.kielet.common;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.charlag.kielet.R;

/**
 * Created by charlag on 03/04/2017.
 *
 * Adapter for the list of translations.
 */


public final class TranslationsListAdapter extends
        RecyclerView.Adapter<TranslationsListAdapter.ViewHolder> implements View.OnClickListener {

    private final List<TranslationItemViewModel> translations;

    @Nullable private OnFavPressedListener favPressedListener;

    public TranslationsListAdapter(List<TranslationItemViewModel> translations) {
        this.translations = translations;
    }

    public void setFavPressedListener(@Nullable OnFavPressedListener favPressedListener) {
        this.favPressedListener = favPressedListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_save) AppCompatImageButton saveButton;
        @BindView(R.id.tv_translation_orig) TextView original;
        @BindView(R.id.tv_translation_result) TextView result;
        @BindView(R.id.tv_translation_langs) TextView languages;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            saveButton.setOnClickListener(TranslationsListAdapter.this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_translation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.saveButton.setTag(position);
        TranslationItemViewModel vm = translations.get(position);
        holder.saveButton.setImageResource(vm.isFav() ?
                R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);
        holder.original.setText(vm.getOriginalText());
        holder.result.setText(vm.getTranslatedText());
        holder.languages.setText(vm.getLanguageFrom() + "-" + vm.getLanguageTo());
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    @Override
    public void onClick(View v) {
        if (favPressedListener != null) {
            favPressedListener.favSelected((Integer) v.getTag());
        }
    }

    public interface OnFavPressedListener {
        void favSelected(int position);
    }
}