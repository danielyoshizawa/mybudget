package com.depaul.daniel.mybudget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;

public class ChartActivity extends Layout {
    private View mChart;
    private CategoryManager categoryManager;
    private EntryManager entryManager;
    private ArrayList<String> categoriesNames;
    private ArrayList<Double> spendsPerCategory;
    private ArrayList<Integer> colorsVector;
    private DefaultRenderer defaultRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup vg = (ViewGroup) findViewById(R.id.content);
        ViewGroup.inflate(this, R.layout.activity_chart, vg);

        initialize();
        configure();
        openChart();
    }

    private void initialize() {
        categoryManager = CategoryManager.getInstance();
        entryManager = EntryManager.getInstance();
        defaultRenderer = new DefaultRenderer();
        spendsPerCategory = new ArrayList<Double>();
        colorsVector = new ArrayList<Integer>();
    }

    private void configure() {
        categoriesNames = categoryManager.GetCategoriesName();
        populateSpendsPerCategory();
        populateColors();
    }

    private void populateSpendsPerCategory() {
        for (String name : categoriesNames) {
           spendsPerCategory.add(entryManager.GetSpendPerCategory(name));
        }
    }

    // TODO rethink this, it's horrible
    private void populateColors() {
        colorsVector.add(Color.BLUE);
        colorsVector.add(Color.GREEN);
        colorsVector.add(Color.MAGENTA);
        colorsVector.add(Color.CYAN);
        colorsVector.add(Color.RED);
        colorsVector.add(Color.GRAY);
        colorsVector.add(Color.YELLOW);
        colorsVector.add(Color.BLACK);
    }

    private void openChart() {

        CategorySeries distributionSeries = new CategorySeries("Spends per Category");

        for (int i = 0; i < categoriesNames.size(); i++) {
            distributionSeries.add(categoriesNames.get(i), spendsPerCategory.get(i));
        }

        for (int i = 0; i < categoriesNames.size(); i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer(); // TODO check if more than one is necessary
            seriesRenderer.setColor(colorsVector.get(i));
            defaultRenderer.setBackgroundColor(Color.WHITE); // TODO check if is necessary to reconfig every time
            defaultRenderer.setApplyBackgroundColor(true);
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Spends per Category"); // TODO change title
        defaultRenderer.setChartTitleTextSize(60); // TODO change title size
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setLabelsTextSize(30);



        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        chartContainer.removeAllViews();
        mChart = ChartFactory.getPieChartView(getBaseContext(), distributionSeries, defaultRenderer);
        chartContainer.addView(mChart);
        }

    }
