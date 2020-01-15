package com.android.app.view.adapter.helper;

public interface ItemTouchHelperAdapter {

    public boolean onItemMove(int fromPosition, int toPosition);

    public void onItemDismiss(int position);
}
