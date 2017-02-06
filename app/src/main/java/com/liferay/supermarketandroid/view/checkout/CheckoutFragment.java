package com.liferay.supermarketandroid.view.checkout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.model.CartItem;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.presenter.checkout.CheckoutContracts;
import com.liferay.supermarketandroid.presenter.checkout.CheckoutPresenter;
import com.liferay.supermarketandroid.view.base.fragment.BaseFragment;
import com.liferay.supermarketandroid.view.checkout.adapter.CartItemAdapter;

import java.util.List;

/**
 * The type Checkout fragment.
 */
public class CheckoutFragment extends BaseFragment implements CheckoutContracts.View {

    private OnCheckoutFragmentListener mListener;
    private CheckoutContracts.Presenter mPresenter;

    private View root;
    private ListView lstCartItens;
    private List<CartItem> cartItems;
    private TextView tx_value;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    /**
     * Instantiates a new Checkout fragment.
     */
    public CheckoutFragment() {
        mPresenter = new CheckoutPresenter(this);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_checkout, container, false);
        initView();
        mPresenter.getProducts();
        return root;
    }

    private void initView() {
        lstCartItens = (ListView) root.findViewById(R.id.lstCartItens);
        tx_value = (TextView) root.findViewById(R.id.tx_value);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getProducts());
        lstCartItens.setOnItemClickListener(this::onItemClick);
        lstCartItens.setOnItemLongClickListener(this::onItemLongClick);
    }


    /**
     * On item long click boolean.
     *
     * @param adapterView the adapter view
     * @param view        the view
     * @param position    the position
     * @param id          the id
     * @return the boolean
     */
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        mPresenter.deleteCartItem(cartItems.get(position));
        return true;
    }

    /**
     * On item click.
     *
     * @param parent   the parent
     * @param view     the view
     * @param position the position
     * @param id       the id
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            mListener.onCartItemSelected(cartItems.get(position));
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCheckoutFragmentListener) {
            mListener = (OnCheckoutFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showError(int resStringId) {
        Snackbar.make(root, resStringId, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSuccess() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSuccessDeleteItem(CartItem item) {
        Snackbar.make(root, R.string.msg_delete, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        mPresenter.getProducts();
    }

    @Override
    public void populateListProduct(List<CartItem> result, double total) {
        String totalStr = getContext().getString(R.string.currency_symbol) + total;
        tx_value.setText(totalStr);

        cartItems = result;
        lstCartItens.setAdapter(new CartItemAdapter(getContext(), cartItems));
        mSwipeRefreshLayout.setRefreshing(false);
    }


    /**
     * The interface On checkout fragment listener.
     */
    public interface OnCheckoutFragmentListener {

        /**
         * On cart item selected.
         *
         * @param item the item
         */
        void onCartItemSelected(CartItem item);
    }
}
