package com.liferay.supermarketandroid.view.productDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.api.ApiConstants;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.presenter.productDetail.ProductDetailContracts;
import com.liferay.supermarketandroid.presenter.productDetail.ProductDetailPresenter;
import com.liferay.supermarketandroid.util.StringUtils;
import com.liferay.supermarketandroid.view.home.HomeActivity;
import com.liferay.supermarketandroid.view.base.fragment.BaseFragment;

/**
 * The type Product detail fragment.
 */
public class ProductDetailFragment extends BaseFragment implements ProductDetailContracts.View {

    private OnProductDetailFragmentListener mListener;
    private Product product;
    private ProductDetailContracts.Presenter mPresenter;

    /**
     * The Img product.
     */
    ImageView imgProduct;
    /**
     * The Tx description.
     */
    TextView tx_description;
    /**
     * The Tx type.
     */
    TextView tx_type;
    /**
     * The Tx price.
     */
    TextView tx_price;
    /**
     * The Rat product.
     */
    RatingBar rat_product;
    /**
     * The Fab.
     */
    FloatingActionButton fab;

    private View root;

    /**
     * Instantiates a new Product detail fragment.
     */
    public ProductDetailFragment() {
        mPresenter = new ProductDetailPresenter(this);
    }

    /**
     * New instance product detail fragment.
     *
     * @param productId the productId
     * @return the product detail fragment
     */
    public static ProductDetailFragment newInstance(long productId) {
        Bundle bundle = new Bundle();
        bundle.putLong(HomeActivity.EXTRA_PRODUCT, productId);
        ProductDetailFragment fragment = new ProductDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product_detail, container, false);

        initView();
        return root;
    }

    private void initView() {
        imgProduct = (ImageView) root.findViewById(R.id.imgProduct);
        tx_description = (TextView) root.findViewById(R.id.tx_description);
        tx_type = (TextView) root.findViewById(R.id.tx_type);
        tx_price = (TextView) root.findViewById(R.id.tx_price);
        rat_product = (RatingBar) root.findViewById(R.id.rat_product);

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fab = (FloatingActionButton) root.findViewById(R.id.fab);

        long productId = getArguments().getLong(HomeActivity.EXTRA_PRODUCT, 0);

        mPresenter.getProductById(productId);
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
        if (context instanceof OnProductDetailFragmentListener) {
            mListener = (OnProductDetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnProductDetailFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            mListener.onShowProgress();
        } else {
            mListener.onHideProgress();
        }
    }

    @Override
    public void showError(int resStringId) {
        Snackbar.make(root, getResources().getText(resStringId), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        if (mListener != null) {
            mListener.onErrorProductDetailFragmentListener();
        }
    }

    @Override
    public void showSuccess() {
        Snackbar.make(root, "Item adicionado com sucesso !", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        if (mListener != null) {
            mListener.onHideProgress();
        }
    }

    @Override
    public void showProduct(Product product) {
        this.product = product;
        getActivity().setTitle(product.getTitle());
        tx_description.setText(product.getDescription());
        String valueFormat = getContext().getString(R.string.currency_symbol) +
                StringUtils.formatCurrency(product.getPrice());
        tx_price.setText(valueFormat);
        tx_type.setText(product.getType());
        rat_product.setNumStars(product.getRating());
        rat_product.setIsIndicator(true);


        Glide.with(getContext())
                .load(ApiConstants.PATH_PUBLIC_IMGS + product.getFilename())
                .centerCrop()
                .crossFade()
                .into(imgProduct);

        fab.setOnClickListener(view -> mPresenter.addItem(product));
    }

    /**
     * The interface On product detail fragment listener.
     */
    public interface OnProductDetailFragmentListener {
        /**
         * On show progress.
         */
        void onShowProgress();

        /**
         * On hide progress.
         */
        void onHideProgress();

        /**
         * On error product detail fragment listener.
         */
        void onErrorProductDetailFragmentListener();
    }
}
