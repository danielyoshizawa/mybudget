package com.depaul.daniel.mybudget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class ChartActivity extends Layout {
    private View mChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup vg = (ViewGroup) findViewById(R.id.content);
        ViewGroup.inflate(this, R.layout.activity_chart, vg);

        // Getting reference to the button btn_chart
        Button btnChart = (Button) findViewById(R.id.btn_chart);

            // Defining click event listener for the button btn_chart
            View.OnClickListener clickListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Draw the pie Chart
                    openChart();
                }
            };

            // Setting event click listener for the button btn_chart of the
            // MainActivity layout
            btnChart.setOnClickListener(clickListener);
    }

        private void openChart() {

            // Pie Chart Section Names
            String[] code = new String[] { "Froyo", "Gingerbread",
                    "IceCream Sandwich", "Jelly Bean", "KitKat" };

            // Pie Chart Section Value
            double[] distribution = { 0.5, 9.1, 7.8, 45.5, 33.9 };

            // Color of each Pie Chart Sections
            int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
                    Color.RED };

            // Instantiating CategorySeries to plot Pie Chart
            CategorySeries distributionSeries = new CategorySeries(
                    " Android version distribution as on October 1, 2012");
            for (int i = 0; i < distribution.length; i++) {
                // Adding a slice with its values and name to the Pie Chart
                distributionSeries.add(code[i], distribution[i]);
            }

            // Instantiating a renderer for the Pie Chart
            DefaultRenderer defaultRenderer = new DefaultRenderer();
            for (int i = 0; i < distribution.length; i++) {
                SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
                seriesRenderer.setColor(colors[i]);
                //seriesRenderer.setDisplayChartValues(true);
//Adding colors to the chart
                defaultRenderer.setBackgroundColor(Color.BLACK);
                defaultRenderer.setApplyBackgroundColor(true);
                // Adding a renderer for a slice
                defaultRenderer.addSeriesRenderer(seriesRenderer);
            }

            defaultRenderer
                    .setChartTitle("Android version distribution as on December 1, 2014. ");
            defaultRenderer.setChartTitleTextSize(20);
            defaultRenderer.setZoomButtonsVisible(false);

            // this part is used to display graph on the xml
            // Creating an intent to plot bar chart using dataset and
            // multipleRenderer
            // Intent intent = ChartFactory.getPieChartIntent(getBaseContext(),
            // distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");

            // Start Activity
            // startActivity(intent);

            LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
            // remove any views before u paint the chart
            chartContainer.removeAllViews();
            // drawing pie chart
            mChart = ChartFactory.getPieChartView(getBaseContext(),
                    distributionSeries, defaultRenderer);
            // adding the view to the linearlayout
            chartContainer.addView(mChart);

        }

    }
