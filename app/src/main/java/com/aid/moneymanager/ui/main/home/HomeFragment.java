/*
 * *
 *  * Created by Yadgarov Islombek on 2021
 *  * Copyright (c).  All rights reserved.
 *  * Last modified 21.01.21 23:42
 *  بِسْمِ ٱللّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيم  *
 *
 */

package com.aid.moneymanager.ui.main.home;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aid.moneymanager.R;
import com.aid.moneymanager.base.BaseFragment;
import com.aid.moneymanager.firebase.FirebaseElement;
import com.aid.moneymanager.firebase.FirebaseObserver;
import com.aid.moneymanager.firebase.ListDataSet;
import com.aid.moneymanager.firebase.models.User;
import com.aid.moneymanager.firebase.models.UserSettings;
import com.aid.moneymanager.firebase.models.Wallet;
import com.aid.moneymanager.firebase.models.WalletEnter;
import com.aid.moneymanager.firebase.viewModel_fact.TopWalletEntriesViewModelFactory;
import com.aid.moneymanager.firebase.viewModel_fact.UserProfileViewModelFactory;
import com.aid.moneymanager.lib.OlchovLibFromGit;
import com.aid.moneymanager.models.Category;
import com.aid.moneymanager.utils.CalendarHelper;
import com.aid.moneymanager.utils.CategorysHelper;
import com.aid.moneymanager.utils.CurrencyHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment  extends BaseFragment {
    private User user;
    private ListDataSet<WalletEnter> walletEntryListDataSet;

    public static final CharSequence TITLE = "Home";
    private OlchovLibFromGit olchov;

    private TopCategoryAdapter adapter;
    private ArrayList<TopCategoryListViewModel> categoryModelsHome;
    private TextView totalBalanceTextView;
    private TextView LeftBalanceTextView;
    private TextView LeftLine1TextView;
    private TextView LeftLine2TextView;
    private TextView RightBalanceTextView;
    private TextView RightLine1TextView;
    private TextView RightLine2TextView;
    private TextView BalanceLeftTextView;

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        categoryModelsHome = new ArrayList<>();

        olchov= view.findViewById(R.id.olchov);
        olchov.setValue(50);

        totalBalanceTextView = view.findViewById(R.id.total_balance_textview);
        LeftBalanceTextView = view.findViewById(R.id.left_balance_text_view);
        LeftLine1TextView = view.findViewById(R.id.left_line1_textview);
        LeftLine2TextView = view.findViewById(R.id.left_line2_textview);
        RightBalanceTextView = view.findViewById(R.id.right_balance_text_view);
        RightLine1TextView = view.findViewById(R.id.right_line1_textview);
        RightLine2TextView = view.findViewById(R.id.right_line2_textview);
        BalanceLeftTextView = view.findViewById(R.id.left_balance_textview);


        ListView favoriteListView = view.findViewById(R.id.favourite_categories_list_view);
        adapter = new TopCategoryAdapter(categoryModelsHome, getActivity().getApplicationContext());
        favoriteListView.setAdapter(adapter);


        TopWalletEntriesViewModelFactory.getModel(getUid(), getActivity()).observe(this, new FirebaseObserver<FirebaseElement<ListDataSet<WalletEnter>>>() {

            @Override
            public void onChanged(FirebaseElement<ListDataSet<WalletEnter>> firebaseElement) {
                if (firebaseElement.hasNoError()) {
                    HomeFragment.this.walletEntryListDataSet = firebaseElement.getElement();
                    dataUpdated();
                }
            }
        });


        UserProfileViewModelFactory.getModel(getUid(), getActivity()).observe(this, new FirebaseObserver<FirebaseElement<User>>() {
            @Override
            public void onChanged(FirebaseElement<User> firebaseElement) {
                if (firebaseElement.hasNoError()) {
                    HomeFragment.this.user = firebaseElement.getElement();
                    dataUpdated();

                    Calendar startDate = CalendarHelper.getUserPeriodStartDate(user);
                    Calendar endDate = CalendarHelper.getUserPeriodEndDate(user);
                    TopWalletEntriesViewModelFactory.getModel(getUid(), getActivity()).setDateFilter(startDate, endDate);
                }
            }
        });


    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.home_fragment_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_options:
//                startActivity(new Intent(getActivity(), OptionsActivity.class));
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void dataUpdated() {
        if (user == null || walletEntryListDataSet == null) return;

        List<WalletEnter> entryList = new ArrayList<>(walletEntryListDataSet.getList());

        Calendar startDate = CalendarHelper.getUserPeriodStartDate(user);
        Calendar endDate = CalendarHelper.getUserPeriodEndDate(user);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM");


        long expensesSumInDateRange = 0;
        long incomesSumInDateRange = 0;

        HashMap<Category, Long> categoryModels = new HashMap<>();
        for (WalletEnter walletEntry : entryList) {
            if (walletEntry.balanceDifference > 0) {
                incomesSumInDateRange += walletEntry.balanceDifference;
                continue;
            }
            expensesSumInDateRange += walletEntry.balanceDifference;
            Category category = CategorysHelper.searchCategory(user, walletEntry.categoryID);
            if (categoryModels.get(category) != null)
                categoryModels.put(category, categoryModels.get(category) + walletEntry.balanceDifference);
            else
                categoryModels.put(category, walletEntry.balanceDifference);

        }

        categoryModelsHome.clear();
        for (Map.Entry<Category, Long> categoryModel : categoryModels.entrySet()) {
            categoryModelsHome.add(new TopCategoryListViewModel(categoryModel.getKey(), categoryModel.getKey().getCategoryVisibleName(getContext()),
                    user.currency, categoryModel.getValue()));
        }

        Collections.sort(categoryModelsHome, new Comparator<TopCategoryListViewModel>() {
            @Override
            public int compare(TopCategoryListViewModel o1, TopCategoryListViewModel o2) {
                return Long.compare(o1.getMoney(), o2.getMoney());
            }
        });


        adapter.notifyDataSetChanged();
        totalBalanceTextView.setText(CurrencyHelper.formatCurrency(user.currency, user.wallet.sum));

        if (user.userSettings.homeCounter == UserSettings.HOME_COUNTER_TYPE_SHOW_LIMIT) {
            LeftBalanceTextView.setText(CurrencyHelper.formatCurrency(user.currency, 0));
            LeftLine1TextView.setText(dateFormat.format(startDate.getTime()));
            LeftLine2TextView.setVisibility(View.INVISIBLE);
            RightBalanceTextView.setText(CurrencyHelper.formatCurrency(user.currency, user.userSettings.limit));
            RightLine1TextView.setText(dateFormat.format(endDate.getTime()));
            RightLine2TextView.setVisibility(View.INVISIBLE);

            olchov.setPointStartColor(ContextCompat.getColor(getContext(), R.color.gauge_white));
            olchov.setPointEndColor(ContextCompat.getColor(getContext(), R.color.gauge_white));
            olchov.setStrokeColor(ContextCompat.getColor(getContext(), R.color.gauge_gray));

            long limit = user.userSettings.limit;
            long expenses = -expensesSumInDateRange;
            int percentage = (int) (expenses * 100 / (double) limit);
            if (percentage > 100) percentage = 100;
            olchov.setValue(percentage);
            BalanceLeftTextView.setText(CurrencyHelper.formatCurrency(user.currency, limit - expenses) + " left");


        } else {
          LeftBalanceTextView.setText(CurrencyHelper.formatCurrency(user.currency, incomesSumInDateRange));
            LeftLine1TextView.setText("Incomes");
            LeftLine2TextView.setVisibility(View.INVISIBLE);
            RightBalanceTextView.setText(CurrencyHelper.formatCurrency(user.currency, expensesSumInDateRange));
            RightLine1TextView.setText("Expenses");
            RightLine2TextView.setVisibility(View.INVISIBLE);

            olchov.setPointStartColor(ContextCompat.getColor(getContext(), R.color.gauge_income));
            olchov.setPointEndColor(ContextCompat.getColor(getContext(), R.color.gauge_income));
            olchov.setStrokeColor(ContextCompat.getColor(getContext(), R.color.gauge_expense));
            if (incomesSumInDateRange - expensesSumInDateRange != 0)
                olchov.setValue((int) (incomesSumInDateRange * 100 / (double) (incomesSumInDateRange - expensesSumInDateRange)));

            BalanceLeftTextView.setText(dateFormat.format(startDate.getTime()) + " - " +
                    dateFormat.format(endDate.getTime()));
        }
    }

}
