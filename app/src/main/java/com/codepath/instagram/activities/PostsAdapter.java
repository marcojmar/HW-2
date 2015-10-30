package com.codepath.instagram.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramComment;
import com.codepath.instagram.models.InstagramPost;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by mmar on 10/26/15.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostItemViewHolder> {
    private List<InstagramPost> posts;
    private Context context;

    public PostsAdapter(List<InstagramPost> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public PostItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom layout
        View contactView = inflater.inflate(R.layout.activity_home, parent, false);

        //Return a new holder instance
        PostItemViewHolder viewHolder = new PostItemViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostItemViewHolder holder, int i) {
        InstagramPost post = posts.get(i);


        holder.tvDate.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000).toString());

        Uri uri = Uri.parse(post.user.profilePictureUrl);
        holder.sdvImage.setImageURI(uri);

        uri = Uri.parse(post.image.imageUrl);
        holder.sdvBigImage.setImageURI(uri);

        holder.tvLikes.setText(post.likesCount + " likes");

        ForegroundColorSpan blueForgroundColor = new ForegroundColorSpan(context.getResources().getColor(android.R.color.holo_blue_dark));
        SpannableStringBuilder ssb = new SpannableStringBuilder(post.user.fullName);
//        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new TypefaceSpan("bold"), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ssb.setSpan(blueForgroundColor,
                0,
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        holder.tvUserName.setText(ssb, TextView.BufferType.EDITABLE);
        holder.tvLikes.setText(post.likesCount + " likes");

        if (post.caption != null) {
            String[] captions = post.caption.split(" #");
            ssb.append(" " + captions[0]);
            holder.tvComment.setText(ssb, TextView.BufferType.EDITABLE);

            if (captions.length > 1) {
                holder.tvHashtag.setText("#" + captions[1]);
            }
            else {
                holder.tvHashtag.setText("");
            }
        }
        else {
            holder.tvComment.setText(ssb, TextView.BufferType.EDITABLE);
            holder.tvHashtag.setText("");
        }

        // populate the comments into llComments
        holder.llComments.removeAllViews();

        // comment count
        View countView = LayoutInflater.from(context).inflate(R.layout.layout_item_text_comment, holder.llComments, false);
        TextView vCount = (TextView) countView.findViewById(R.id.tvOneComment);
        vCount.setText("View all " + post.commentsCount + " comments");
        holder.llComments.addView(countView);

        if (post.comments.size() > 1) {
            for (int ind=0; ind<2; ind++) {
                View tempView = LayoutInflater.from(context).inflate(R.layout.layout_item_text_comment, holder.llComments, false);
                TextView tvOneComemnt = (TextView) tempView.findViewById(R.id.tvOneComment);
                tvOneComemnt.setText(post.comments.get(ind).user.toString() + " " + post.comments.get(ind).text);
                holder.llComments.addView(tempView);
            }
        }
        else {
            holder.llComments.setVisibility(View.INVISIBLE);
        }

    }

    public static class PostItemViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdvImage;
        TextView tvUserName;
        TextView tvDate;
        SimpleDraweeView sdvBigImage;
        TextView tvLikes;
        TextView tvComment;
        TextView tvHashtag;
        LinearLayout llComments;
//        TextView tvOneComment;

        public PostItemViewHolder(View v) {
            super(v);
            tvUserName = (TextView) v.findViewById(R.id.tvUserName);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            sdvImage = (SimpleDraweeView) v.findViewById(R.id.sdvImage);
            sdvBigImage = (SimpleDraweeView) v.findViewById(R.id.sdvBigImage);
            tvLikes = (TextView) v.findViewById(R.id.tvLikes);
            //tvUserName2 = (TextView) v.findViewById(R.id.tvUserName2);
            tvComment = (TextView) v.findViewById(R.id.tvComment);
            tvHashtag = (TextView) v.findViewById(R.id.tvHashtag);
//            tvOneComment = (TextView) v.findViewById(R.id.tvOneComment);

            llComments = (LinearLayout) v.findViewById(R.id.llComments);


        }
    }

}
