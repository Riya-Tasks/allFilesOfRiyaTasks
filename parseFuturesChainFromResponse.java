private FuturesChain parseFuturesChainFromResponse(String response) {
    FuturesChain futuresChain = new FuturesChain();
    List<String> dates = new ArrayList<>();
    List<Double> midRates = new ArrayList<>();

    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(response)));

        // Call the method to parse contract
        String contract = parseContractFromResponse(response);
        futuresChain.setContract(contract);

        // Parsing other elements
        futuresChain.setName(doc.getElementsByTagName("name").item(0).getTextContent());

        NodeList quoteNodes = doc.getElementsByTagName("Quote");
        for (int i = 0; i < quoteNodes.getLength(); i++) {
            Element quoteElement = (Element) quoteNodes.item(i);
            dates.add(quoteElement.getAttribute("date"));
            midRates.add(Double.parseDouble(quoteElement.getAttribute("midRate")));
        }

        futuresChain.setDates(dates);
        futuresChain.setMidRates(midRates);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return futuresChain;
}
