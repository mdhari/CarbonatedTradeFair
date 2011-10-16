package edu.sjsu.carbonated.server;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class CSVtoChart {

	/** 
	 * JFreeChart implementation taken from:
	 * http://www.java2s.com/Code/Java/Chart/JFreeChartDualAxisDemo2.htm
	 * Modified By: Michael Hari
	 * Date: 10/15/2011
	 */

	/**
	 * @param fileName - Should be the dat file
	 * @return ArrayList<TimeSeriesCollection> - Has two datasets currently to pass for chart generation
	 */
	private ArrayList<TimeSeriesCollection> createDataset(String fileName) {

		BufferedReader br = null;

		int i = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TimeSeries series1 = new TimeSeries("Volume");
		TimeSeries series2 = new TimeSeries("Adj Close");

		try {
			
			br = new BufferedReader(new FileReader(System.getProperty("jboss.server.temp.dir") + "/" + fileName));
			
			//skip over the 12 lines of raw text in the dat file
			while (br.ready() && i < 12) {
				br.readLine();
				i++;
			}
			
			while (br.ready()) {
				String dataLine = br.readLine();
				// Date,Open,High,Low,Close,Volume,Adj Close
				// 2011-09-29,10.25,10.28,9.80,10.06,33805300,10.06
				// 0, 1, 2, 3, 4, 5, 6
				String[] dataSplit = dataLine.split(",");

				series1.add(new Day(sdf.parse(dataSplit[0])),
						Double.valueOf(dataSplit[5]));
				series2.add(new Day(sdf.parse(dataSplit[0])),
						Double.valueOf(dataSplit[6]));
			}
			
			br.close();
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();

		dataset1.addSeries(series1);
		dataset2.addSeries(series2);

		ArrayList<TimeSeriesCollection> toReturn = new ArrayList<TimeSeriesCollection>();
		toReturn.add(dataset1);
		toReturn.add(dataset2);
		
		return toReturn;

	}

	/**
	 * Creates a chart
	 */
	private JFreeChart createChart(ArrayList<TimeSeriesCollection> datasets, String title) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Date",
				"Volume", datasets.get(0), true, false, false);
		
		//setup another axis to help do comparisons
        final XYPlot plot = chart.getXYPlot();
        final NumberAxis axis2 = new NumberAxis("Adj Close");
        axis2.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, datasets.get(1));
        plot.mapDatasetToRangeAxis(1, 1);

        final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
        renderer2.setSeriesPaint(0, Color.blue);
        plot.setRenderer(1, renderer2);
        
		return chart;

	}

	/**
	 * @param args
	 */
	public void createChartImage(String fileName){

		String stockPath = fileName.split("\\.")[0];
		String[] fileNameSplit = stockPath.split("/");
		
		JFreeChart jFreeChart = createChart(createDataset(fileName),
				fileNameSplit[fileNameSplit.length-1]); // the title will be the stock name only

		try {
			ChartUtilities.saveChartAsPNG(new File(System.getProperty("jboss.server.temp.dir") + "/" + stockPath +".png"), jFreeChart,
					600, 400);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
	
}
