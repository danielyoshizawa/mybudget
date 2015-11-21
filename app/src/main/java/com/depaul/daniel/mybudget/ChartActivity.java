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
    private CategorySeries distributionSeries;
    private LinearLayout chartContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup vg = (ViewGroup) findViewById(R.id.content);
        ViewGroup.inflate(this, R.layout.activity_chart, vg);

        initialize();
        inflate();
        configure();
        openChart();
    }

    private void initialize() {
        categoryManager = CategoryManager.getInstance();
        entryManager = EntryManager.getInstance();
        defaultRenderer = new DefaultRenderer();
        spendsPerCategory = new ArrayList<Double>();
        colorsVector = new ArrayList<Integer>();
        distributionSeries = new CategorySeries("Spends per Category");
    }

    private void inflate() {
        chartContainer = (LinearLayout) findViewById(R.id.chart);
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

        if(entryManager.Size() == 0) {
            return;
        }

        populateDistributionSeries();
        configureDefaulRenderer();
        initializeChart();
    }

    private void populateDistributionSeries() {
        for (int i = 0; i < categoriesNames.size(); i++) {
            distributionSeries.add(categoriesNames.get(i), spendsPerCategory.get(i));
        }
    }

    private void configureDefaulRenderer() {

        SimpleSeriesRenderer seriesRenderer;

        for (int i = 0; i < categoriesNames.size(); i++) {
            seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colorsVector.get(i));
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }


        defaultRenderer.setBackgroundColor(Color.WHITE);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setChartTitle("Spends per Category");
        defaultRenderer.setChartTitleTextSize(60);
        defaultRenderer.setZoomButtonsVisible(false);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setLabelsTextSize(30);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setAxesColor(Color.BLACK);
    }

    private void initializeChart() {
        chartContainer.removeAllViews();
        mChart = ChartFactory.getPieChartView(getBaseContext(), distributionSeries, defaultRenderer);
        chartContainer.addView(mChart);
    }

}
