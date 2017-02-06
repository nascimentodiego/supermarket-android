package com.liferay.supermarketandroid.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jakewharton.rxbinding.view.RxView;
import com.liferay.supermarketandroid.App;
import com.liferay.supermarketandroid.R;
import com.liferay.supermarketandroid.model.model.Product;
import com.liferay.supermarketandroid.presenter.home.HomeContracts;
import com.liferay.supermarketandroid.presenter.home.HomePresenter;
import com.liferay.supermarketandroid.util.SuperMarketLog;
import com.liferay.supermarketandroid.view.base.fragment.BaseFragment;
import com.liferay.supermarketandroid.view.home.adapter.ProductsAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;

/**
 * The type Home fragment.
 */
public class HomeFragment extends BaseFragment implements HomeContracts.View {

    private View root;
    private ListView lstProducts;
    private Button btnAll, btnBakery, btnDairy, btnFruit, btnVegetable, btnMeat;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private HomeContracts.Presenter mPresenter;

    private OnProductListFragmentListener mOnProductListFragmentListener;

    /**
     * The Btn sub all.
     */
    Subscription btnSubAll,
    /**
     * The Btn sub bakery.
     */
    btnSubBakery,
    /**
     * The Btn sub dairy.
     */
    btnSubDairy,
    /**
     * The Btn sub fruit.
     */
    btnSubFruit,
    /**
     * The Btn sub vegetable.
     */
    btnSubVegetable,
    /**
     * The Btn sub meat.
     */
    btnSubMeat;


    /**
     * Instantiates a new Home fragment.
     */
    public HomeFragment() {
        mPresenter = new HomePresenter(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product_list, container, false);

        initView();
        return root;
    }

    private void initView() {
        lstProducts = (ListView) root.findViewById(R.id.lstProducts);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.ALL, true));
        lstProducts.setOnItemClickListener(this::onItemClick);

        mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.ALL, false);

        btnAll = (Button) root.findViewById(R.id.btnAll);
        btnSubAll = RxView.clicks(btnAll)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.ALL, true),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        btnBakery = (Button) root.findViewById(R.id.btnBakery);
        btnSubBakery = RxView.clicks(btnBakery)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.BAKERY, true),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        btnDairy = (Button) root.findViewById(R.id.btnDairy);
        btnSubDairy = RxView.clicks(btnDairy)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.DAIRY, true),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        btnFruit = (Button) root.findViewById(R.id.btnFruit);
        btnSubFruit = RxView.clicks(btnFruit)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.FRUIT, true),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        btnVegetable = (Button) root.findViewById(R.id.btnVegetable);
        btnSubVegetable = RxView.clicks(btnVegetable)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.VEGETABLE, true),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        btnMeat = (Button) root.findViewById(R.id.btnMeat);
        btnSubMeat = RxView.clicks(btnMeat)
                .throttleFirst(TIME_TO_CLICK, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.getProducts(HomeContracts.Presenter.TypeFilter.MEAT, true),
                        Throwable::printStackTrace,
                        () -> SuperMarketLog.print("OnCompleted"));

        registerSubscription(btnSubAll, btnSubBakery, btnSubDairy, btnSubFruit, btnSubVegetable, btnSubMeat);
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
        if (context instanceof OnProductListFragmentListener) {
            mOnProductListFragmentListener = (OnProductListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnProductListFragmentListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
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
        mPresenter.getProdutByPosition(position);

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showError(int resStringId) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSuccess() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void callProductDetails(Product prod) {
        mOnProductListFragmentListener.onProductSelected(prod);
    }

    @Override
    public void populateListProduct(List<Product> result) {
        lstProducts.setAdapter(new ProductsAdapter(App.getContext(), result));
    }

    /**
     * The interface On product list fragment listener.
     */
    public interface OnProductListFragmentListener {
        /**
         * On product selected.
         *
         * @param product the product
         */
        void onProductSelected(Product product);
    }
}
