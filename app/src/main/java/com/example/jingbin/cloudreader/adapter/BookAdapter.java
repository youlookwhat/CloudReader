package com.example.jingbin.cloudreader.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.bean.book.BooksBean;
import com.example.jingbin.cloudreader.databinding.FooterItemBookBinding;
import com.example.jingbin.cloudreader.databinding.ItemBookBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingbin on 2016/12/15.
 */

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    private static final int LOAD_END = 3;
    private static final int TYPE_TOP = -1;
    private static final int TYPE_FOOTER = -2;
    private List<BooksBean> list;

    public BookAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();

    }

    @Override
    public int getItemViewType(int position) {

        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return position;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            FooterItemBookBinding mBindFooter = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.footer_item_book, parent, false);
//            View view = View.inflate(parent.getContext(), R.layout.footer_item_book, null);
            return new FooterViewHolder(mBindFooter.getRoot());
        } else {
            ItemBookBinding mBindBook = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_book, parent, false);
//            View rootView = View.inflate(parent.getContext(), R.layout.item_book, null);
            return new BookViewHolder(mBindBook.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof BookViewHolder) {
            BookViewHolder bookViewHolder = (BookViewHolder) holder;
            bookViewHolder.bindItem(list.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
//                && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
                && (isFooter(holder.getLayoutPosition()))) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

//    public boolean isHeader(int position) {
//        return position >= 0 && position < mHeaderViews.size();
//    }

    public boolean isFooter(int position) {
        return position < getItemCount() && position >= getItemCount() - 1;
    }

    /**
     * footer view
     */
    private class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterItemBookBinding mBindFooter;

        FooterViewHolder(View itemView) {
            super(itemView);
            mBindFooter = DataBindingUtil.getBinding(itemView);
            mBindFooter.rlMore.setGravity(Gravity.CENTER);
//            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dipToPx(context, 40));
//            itemView.setLayoutParams(params);
        }

        private void bindItem() {
            switch (status) {
                case LOAD_MORE:
                    mBindFooter.progress.setVisibility(View.VISIBLE);
                    mBindFooter.tvLoadPrompt.setText("正在加载...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_PULL_TO:
                    mBindFooter.progress.setVisibility(View.GONE);
                    mBindFooter.tvLoadPrompt.setText("上拉加载更多");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NONE:
                    System.out.println("LOAD_NONE----");
                    mBindFooter.progress.setVisibility(View.GONE);
                    mBindFooter.tvLoadPrompt.setText("已无更多加载");
                    break;
                case LOAD_END:
                    itemView.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    }


    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        ItemBookBinding mBindBook;

        BookViewHolder(View view) {
            super(view);
            mBindBook = DataBindingUtil.getBinding(view);
        }

        private void bindItem(BooksBean book, int position) {
            mBindBook.setBean(book);
            mBindBook.executePendingBindings();
//            ViewGroup.LayoutParams params=iVFilm.getLayoutParams();
//            int width= ScreenUtils.getScreenWidthDp(context);
//            int ivWidth=(width-ScreenUtils.dipToPx(context,80))/3;
//            params.width=ivWidth;
//            double height=(420.0/300.0)*ivWidth;
//            params.height=(int)height;
//            iVFilm.setLayoutParams(params);
//            if(!TextUtils.isEmpty(book.getImages().getLarge())) {
//                DisplayImgUtis.getInstance().display(context, book.getImages().getLarge(), iVFilm);
//            }
//            if(!TextUtils.isEmpty(book.getRating().getAverage())) {
//                tvFilmGrade.setText("评分:" + book.getRating().getAverage());
//            }else{
//                tvFilmGrade.setText("暂无评分" );
//            }
//            if(!TextUtils.isEmpty(book.getTitle())) {
//                tvFilmName.setText(book.getTitle());
//            }
//            llBook.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(context, BookDetailActivity.class);
//                    intent.putExtra("id",book.getId());
//
//                    context.startActivity(intent);
//                }
//            });


        }
    }

    public List<BooksBean> getList() {
        return list;
    }

    public void setList(List<BooksBean> list) {
        this.list.clear();
        this.list = list;
    }

    public void addAll(List<BooksBean> list) {
        this.list.addAll(list);
    }

}
