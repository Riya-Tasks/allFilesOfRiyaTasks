@Service
public class XDSConnectionService {

    private final RestTemplate restTemplate;

    @Autowired
    public XDSConnectionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Object> getSwapRatesFromXds(Map<String, String> dataMap, String username, String password, String date) {
        List<Object> result = new ArrayList<>();
        String baseUrl = "https://xds-int.systems.uk.hsbc/api/v1/documents/";

        // Iterate through all keys and values in the map
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Form URL1 for each key
            String url1 = baseUrl + value.replace("${DATE}", date);
            String response = getDataFromXds(url1, username, password);

            // Parse the response for specific terms (SwapRate, TurnOfYearSpread, etc.)
            if (response.contains("SwapRate")) {
                result.addAll(processSwapRate(response, url1, username, password));
            }
            if (response.contains("TurnOfYearSpread")) {
                result.add(processTurnOfYearSpread(response, url1, username, password));
            }
            if (response.contains("MoneyMarketQuotes")) {
                result.add(processMoneyMarketQuotes(response, url1, username, password));
            }
            if (response.contains("RateForward")) {
                result.add(processRateForward(response, url1, username, password));
            }
            if (response.contains("FuturesChain")) {
                result.add(processFuturesChain(response, url1, username, password));
            }
        }
        return result;
    }

    private List<SwapRate> processSwapRate(String response, String url1, String username, String password) {
        List<SwapRate> swapRatesList = new ArrayList<>();
        List<String> assetNames = parseAssetNamesFromResponse(response);

        for (String assetName : assetNames) {
            String url2 = url1.replace("SwapCurve", "SwapRates").replace("MSSEOD", assetName + "/MSSEOD");
            String swapRatesResponse = getDataFromXds(url2, username, password);
            SwapRate swapRate = parseSwapRatesFromResponse(swapRatesResponse);
            swapRatesList.add(swapRate);
        }
        return swapRatesList;
    }

    private TurnOfYearSpread processTurnOfYearSpread(String response, String url1, String username, String password) {
        String url2 = url1.replace("SwapCurve", "TurnOfYearSpread").replaceFirst("/[^/]+/MSSEOD", "/MSSEOD");
        String turnOfYearSpreadResponse = getDataFromXds(url2, username, password);
        return parseTurnOfYearSpreadFromResponse(turnOfYearSpreadResponse);
    }

    private MoneyMarketQuotes processMoneyMarketQuotes(String response, String url1, String username, String password) {
        String url2 = url1.replace("SwapCurve", "MoneyMarketQuotes").replaceFirst("/[^/]+/MSSEOD", "/MSSEOD");
        String moneyMarketQuotesResponse = getDataFromXds(url2, username, password);
        return parseMoneyMarketQuotesFromResponse(moneyMarketQuotesResponse);
    }

    private RateForward processRateForward(String response, String url1, String username, String password) {
        String url2 = url1.replace("SwapCurve", "RateForward").replaceFirst("/[^/]+/MSSEOD", "/MSSEOD");
        String rateForwardResponse = getDataFromXds(url2, username, password);
        return parseRateForwardFromResponse(rateForwardResponse);
    }

    private FuturesChain processFuturesChain(String response, String url1, String username, String password) {
        String contract = extractContractFromResponse(response);
        String url2 = url1.replace("SwapCurve", "FuturesChain").replaceAll("/[^/]+/[^/]+/[^/]+/MSSEOD", "/" + contract + "/MSSEOD");
        String futuresChainResponse = getDataFromXds(url2, username, password);
        return parseFuturesChainFromResponse(futuresChainResponse);
    }

    // Existing methods for parsing SwapRates, extracting data, making requests (getDataFromXds) remain the same
}
