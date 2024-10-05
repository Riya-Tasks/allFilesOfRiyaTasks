private RateForward parseRateForwardFromResponse(String response) {
    RateForward rateForward = new RateForward();
    List<String> startDates = new ArrayList<>();
    List<String> endDates = new ArrayList<>();
    List<Double> midRates = new ArrayList<>();
    List<Double> spreads = new ArrayList<>();

    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(response)));

        // Extract attributes from the <RateForward> element
        NodeList rateForwardNodes = doc.getElementsByTagName("RateForward");
        if (rateForwardNodes.getLength() > 0) {
            Element rateForwardElement = (Element) rateForwardNodes.item(0);

            // Set basic fields
            rateForward.setRateFixingIndex(rateForwardElement.getAttribute("rateFixingIndex"));
            rateForward.setRateType(rateForwardElement.getAttribute("rateType"));
            rateForward.setName(rateForwardElement.getAttribute("name"));
            rateForward.setCcy(rateForwardElement.getAttribute("ccy"));
            rateForward.setBusinessDate(rateForwardElement.getAttribute("businessDate"));
            rateForward.setLocation(rateForwardElement.getAttribute("location"));
            rateForward.setMarketTime(rateForwardElement.getAttribute("marketTime"));

            // Extract <Quote> elements
            NodeList quoteNodes = rateForwardElement.getElementsByTagName("Quote");
            for (int i = 0; i < quoteNodes.getLength(); i++) {
                Element quoteElement = (Element) quoteNodes.item(i);

                // Extract and store startDate, endDate, midRate, spread
                startDates.add(quoteElement.getAttribute("startDate"));
                endDates.add(quoteElement.getAttribute("endDate"));
                midRates.add(Double.parseDouble(quoteElement.getAttribute("midRate")));
                spreads.add(Double.parseDouble(quoteElement.getAttribute("spread")));
            }

            // Set lists in RateForward object
            rateForward.setStartDates(startDates);
            rateForward.setEndDates(endDates);
            rateForward.setMidRates(midRates);
            rateForward.setSpreads(spreads);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return rateForward;
}
