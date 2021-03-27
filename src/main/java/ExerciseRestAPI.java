import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class ExerciseRestAPI {
    public static void main(String[] args) throws IOException {
        getCountryData();
        convertUSDtoILS();
    }

    private static void getCountryData() throws IOException {
        String country;
        String apiURL = "https://restcountries.eu/rest/v2/name/";
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Input a country name : ");
            country = scanner.next();
            if (country.compareTo("exit") == 0)
                break;
            JSONObject jsonData = getObjectFromURL(apiURL + country);
            if (jsonData.has("status")) {
                System.out.println("Country does not exist!\n");
            } else {
                System.out.print("\n" + printCountryData(jsonData) + "\n");
            }
        }
    }

    private static void convertUSDtoILS() throws IOException {
        String apiURL = "https://api.currencyscoop.com/v1/latest?api_key=d42128ef1ad4eeaeec81a165b9277fb9";
        System.out.println("Welcome to currency converter");
        System.out.print("Please enter an amount of Dollars to convert : ");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        JSONObject jsonData = getObjectFromURL(apiURL).getJSONObject("response").getJSONObject("rates");
        double ilsRate = jsonData.getDouble("ILS");

        System.out.println(String.format("%.2f USD = %.2f ILS", amount, ilsRate * amount));
        System.out.println("Thanks for using our currency converter");
    }

    private static JSONObject getObjectFromURL(String apiURL) throws IOException, NullPointerException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiURL)
                .build();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        if (jsonData.indexOf("[") == 0)
            jsonData = jsonData.substring(1, jsonData.length() - 1);
        return new JSONObject(jsonData);
    }

    private static String printCountryData(JSONObject jsonObject) {
        String region = jsonObject.getString("region");
        JSONArray callingCodes = jsonObject.getJSONArray("callingCodes");
        JSONArray borders = jsonObject.getJSONArray("borders");
        String symbol = jsonObject.getJSONArray("currencies").getJSONObject(0).getString("symbol");

        StringBuilder sb = new StringBuilder();
        sb.append("Country Data:\n")
                .append(String.format("Region : %s\n",region))
                .append(String.format("Calling Codes : %s\n",callingCodes))
                .append(String.format("Borders : %s\n",borders))
                .append(String.format("Symbol : %s\n",symbol));
        return sb.toString();
    }
}
