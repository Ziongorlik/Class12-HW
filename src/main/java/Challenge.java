import io.restassured.path.json.JsonPath;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;

public class Challenge {
    public static void main(String[] args) {
        String country = "usa";
        String countryDataURL = "https://restcountries.eu/rest/v2/name/" + country;
        String convertURL = "https://api.currencyscoop.com/v1/latest?api_key=d42128ef1ad4eeaeec81a165b9277fb9";

        JsonPath responseBody = get(countryDataURL).body().jsonPath();
        String region = responseBody.get("region[0]");
        ArrayList<String> callingCodes = responseBody.get("callingCodes[0]");
        String borders = responseBody.getString("borders[0]");
        ArrayList<String> symbol = responseBody.get("currencies.symbol");

        StringBuilder sb = new StringBuilder();
        sb.append("Country Data:\n")
                .append(String.format("Country : %s\n",country))
                .append(String.format("Region : %s\n",region))
                .append(String.format("Calling Codes : %s\n",callingCodes))
                .append(String.format("Borders : %s\n",borders))
                .append(String.format("Symbol : %s\n",symbol.get(0)));

        System.out.println(sb.toString());

        float dollarAmount = 2f;
        float shekelResult = get(convertURL).body().path("response.rates.ILS");
        System.out.println(String.format("%.2f USD = %.2f ILS\n", dollarAmount, shekelResult * dollarAmount));
    }
}
