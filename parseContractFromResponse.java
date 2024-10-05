private String parseContractFromResponse(String response) {
    String contract = "";

    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(response)));

        // Extracting the 'contract' attribute from the <FuturesChain> element
        NodeList futuresChainNodes = doc.getElementsByTagName("FuturesChain");
        if (futuresChainNodes.getLength() > 0) {
            Element futuresChainElement = (Element) futuresChainNodes.item(0);
            contract = futuresChainElement.getAttribute("contract");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return contract;
}
