package com.trantiendat.Database;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //đêm những item xuất hiện lên trên view
        int visibleItemCount = linearLayoutManager.getChildCount();
        // tổng số bảng ghi trong 1 view
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (isLoading() || isLastPage()) {
            return;
        }
        if (firstVisibleItemPosition >= 0 && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
            loadMoreItem();
        }
    }

    public abstract void loadMoreItem();

    public abstract boolean isLoading();

    public abstract boolean isLastPage();

}
