@RestController
@RequestMapping("/api")
public class XDSConnectionController {

    private final XDSConnectionService xdsConnectionService;
    private final XMLParserService xmlParserService;

    @Value("${xds.username}")
    private String username;

    @Value("${xds.password}")
    private String password;

    @Autowired
    public XDSConnectionController(XDSConnectionService xdsConnectionService, XMLParserService xmlParserService) {
        this.xdsConnectionService = xdsConnectionService;
        this.xmlParserService = xmlParserService;
    }

    @GetMapping("/swap-rates")
    public ResponseEntity<List<Object>> getSwapRates(
        @RequestParam String fileName,
        @RequestParam String date) {
        try {
            // Get the parsed dataMap from XMLParserService
            Map<String, String> dataMap = xmlParserService.parseXMLFromResources(fileName);

            // Pass the dataMap to XDSConnectionService to process URLs and get all related data
            List<Object> result = xdsConnectionService.getSwapRatesFromXds(dataMap, username, password, date);

            // Return the combined list of SwapRate, TurnOfYearSpread, MoneyMarketQuotes, RateForward, FuturesChain
            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
