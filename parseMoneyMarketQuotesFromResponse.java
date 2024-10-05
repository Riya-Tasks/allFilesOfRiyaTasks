private MoneyMarketQuotes parseMoneyMarketQuotesFromResponse(String response) {
    MoneyMarketQuotes moneyMarketQuotes = new MoneyMarketQuotes();
    List<String> tenors = new ArrayList<>();
    List<Double> midRates = new ArrayList<>();
    List<Double> spreads = new ArrayList<>();

    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(response)));

        moneyMarketQuotes.setRateFixingIndex(doc.getElementsByTagName("rateFixingIndex").item(0).getTextContent());
        moneyMarketQuotes.setName(doc.getElementsByTagName("name").item(0).getTextContent());
        moneyMarketQuotes.setCcy(doc.getElementsByTagName("ccy").item(0).getTextContent());
        moneyMarketQuotes.setBusinessDate(doc.getElementsByTagName("businessDate").item(0).getTextContent());
        moneyMarketQuotes.setLocation(doc.getElementsByTagName("location").item(0).getTextContent());
        moneyMarketQuotes.setMarketTime(doc.getElementsByTagName("marketTime").item(0).getTextContent());

        NodeList quoteNodes = doc.getElementsByTagName("Quote");
        for (int i = 0; i < quoteNodes.getLength(); i++) {
            Element quoteElement = (Element) quoteNodes.item(i);
            tenors.add(quoteElement.getAttribute("tenor"));
            midRates.add(Double.parseDouble(quoteElement.getAttribute("midRate")));
            spreads.add(Double.parseDouble(quoteElement.getAttribute("spread")));
        }

        moneyMarketQuotes.setTenors(tenors);
        moneyMarketQuotes.setMidRates(midRates);
        moneyMarketQuotes.setSpreads(spreads);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return moneyMarketQuotes;
}
