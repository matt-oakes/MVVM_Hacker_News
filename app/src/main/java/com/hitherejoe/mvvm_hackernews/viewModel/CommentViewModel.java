package com.hitherejoe.mvvm_hackernews.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hitherejoe.mvvm_hackernews.R;
import com.hitherejoe.mvvm_hackernews.model.Comment;
import com.hitherejoe.mvvm_hackernews.util.ViewUtils;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class CommentViewModel extends BaseObservable {

    private Context context;
    private Comment comment;

    public CommentViewModel(Context context, Comment comment) {
        this.context = context;
        this.comment = comment;
    }

    @BindingAdapter("commentDepth")
    public static void setCommentIndent(View view, int depth) {
        ViewTag viewTag = getViewTag(view);
        if (viewTag.depth == depth) return;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                view.getLayoutParams();
        float margin = ViewUtils.convertPixelsToDp(depth * 20, view.getContext());
        layoutParams.setMargins((int) margin, 0, 0, 0);
        view.setLayoutParams(layoutParams);

        viewTag.depth = depth;
    }

    public String getCommentText() {
        return Html.fromHtml(comment.text.trim()).toString();
    }

    public String getCommentAuthor() {
        return context.getResources().getString(R.string.text_comment_author, comment.by);
    }

    public String getCommentDate() {
        return new PrettyTime().format(new Date(comment.time * 1000));
    }

    public int getCommentDepth() {
        return comment.depth;
    }

    public boolean getCommentIsTopLevel() {
        return comment.isTopLevelComment;
    }

    private static ViewTag getViewTag(View view) {
        if (view.getTag() == null || !(view.getTag() instanceof ViewTag)) {
            view.setTag(new ViewTag());
        }
        return (ViewTag) view.getTag();
    }

    private static class ViewTag {
        int depth;
    }
}
