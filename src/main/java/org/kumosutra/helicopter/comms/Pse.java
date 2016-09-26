package org.kumosutra.helicopter.comms;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kumosutra.helicopter.model.SecurityInfo;
import org.kumosutra.helicopter.model.StockInfo;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Created by chazz on 9/7/2016.
 */
public class Pse {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMdd");

	private static final SimpleDateFormat PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	public static boolean isMarketDayToday() {
		return false;
	}

	public static StockInfo data(int companyId, int symbolId) throws Exception {
		JSONObject responseJson = Unirest.post("http://www.pse.com.ph/stockMarket/companyInfo.html?method=fetchHeaderData&ajax=true")
				                    .field("company", companyId)
				                    .field("security", symbolId)
				                    .asJson().getBody().getObject();

		JSONArray records = responseJson.getJSONArray("records");
		JSONObject json = records.getJSONObject(0);

		StockInfo stockInfo = new StockInfo();
		stockInfo.setOpen(Float.parseFloat(json.getString("headerSqOpen").replaceAll(",","")));
		stockInfo.setHigh(Float.parseFloat(json.getString("headerSqHigh").replaceAll(",", "")));
		stockInfo.setLow(Float.parseFloat(json.getString("headerSqLow").replaceAll(",", "")));
		stockInfo.setClose(Float.parseFloat(json.getString("headerLastTradePrice").replaceAll(",", "")));
		stockInfo.setVolume(Float.parseFloat(json.getString("headerTotalVolume").replaceAll(",", "")));
		stockInfo.setSymbol(json.getString("securitySymbol"));
		stockInfo.setDate(PARSER.parse(json.getString("lastTradedDate")));

		return stockInfo;
	}

	public static List<SecurityInfo> securities() throws Exception {
		JSONArray records = Unirest.post("http://www.pse.com.ph/stockMarket/companyInfoSecurityProfile.html?method=getListedRecords&common=yes&ajax=true")
				                          .asJson().getBody().getObject().getJSONArray("records");

		List<SecurityInfo> securities = new ArrayList<>();


		for (int i = 0; i < records.length(); i++) {
			JSONObject json = records.getJSONObject(i);

			SecurityInfo securityInfo = new SecurityInfo();
			securityInfo.setStatus(json.getString("securityStatus"));
			securityInfo.setListingDate(PARSER.parse(json.getString("listingDate")));
			securityInfo.setType(json.getString("securityType"));
			securityInfo.setSymbol(json.getString("securitySymbol"));
			securityInfo.setName(json.getString("securityName"));
			securityInfo.setCompanyId(json.getInt("companyId"));
			securityInfo.setCompanyName(json.getString("companyName"));
			securityInfo.setSymbolId(json.getInt("securitySymbolId"));

			securities.add(securityInfo);
		}

		return securities;
	}

    public static List<StockInfo> allData(String symbol) throws IOException, UnirestException, DataFormatException, ParseException {
        HttpResponse<InputStream> inputStreamHttpResponse = Unirest.get("http://www.pse.com.ph/stockMarket/companyInfoCharts.html")
                .queryString("method", "getAnyStockStockData")
                .queryString("symbol", symbol.toUpperCase())
                .queryString("XMLCallDate", new Date().getTime() + "")
                .asBinary();

	    System.out.println("Successfully retrieved the data.");

        InputStream body = inputStreamHttpResponse.getBody();

        Inflater decompressor = new Inflater();
	    byte[] ytes = IOUtils.toByteArray(body);
	    if(ytes.length < 1) {
		    return Collections.emptyList();
	    }

	    decompressor.setInput(ytes);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(ytes.length);
	    byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            int count = decompressor.inflate(buf);
            bos.write(buf, 0, count);
        }

        bos.close();
        byte[] decompressedData = bos.toByteArray();

        String response = new String(decompressedData);

        Scanner scanner = new Scanner(response);
        scanner.useDelimiter("\n");

        List<StockInfo> stocks = new ArrayList<>();

        while (scanner.hasNext()) {
            StockInfo stock = new StockInfo();
            stock.setSymbol(symbol);

            String[] values = scanner.next().split(",");

            int i = 0;
            stock.setDate(FORMATTER.parse(values[i++]));
            stock.setOpen(Float.parseFloat(values[i++]));
            stock.setHigh(Float.parseFloat(values[i++]));
            stock.setLow(Float.parseFloat(values[i++]));
            stock.setClose(Float.parseFloat(values[i++]));
            stock.setVolume(Float.parseFloat(values[i++]));

            stocks.add(stock);
        }

        return stocks;
    }

}
