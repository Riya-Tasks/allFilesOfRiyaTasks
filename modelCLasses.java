// SwapRate.java (remains the same as before)
public class SwapRate {
    private String location;
    private String marketTime;
    private String currency;
    private String rateFixingIndex;
    private String indexTerm;
    private String name;
    private List<String> terms;
    private List<Double> midRates;
    private List<Double> spreads;
    // Constructors, getters, setters
}

// TurnOfYearSpread.java
public class TurnOfYearSpread {
    private String rateFixingIndex;
    private String name;
    private String ccy;
    private String businessDate;
    private String location;
    private String marketTime;
    private List<String> startDates;
    private List<String> endDates;
    private List<Double> spreads;
    // Constructors, getters, setters
}

// MoneyMarketQuotes.java
public class MoneyMarketQuotes {
    private String rateFixingIndex;
    private String name;
    private String ccy;
    private String businessDate;
    private String location;
    private String marketTime;
    private List<String> tenors;
    private List<Double> midRates;
    private List<Double> spreads;
    // Constructors, getters, setters
}

// RateForward.java
public class RateForward {
    private String rateFixingIndex;
    private String rateType;
    private String name;
    private String ccy;
    private String businessDate;
    private String location;
    private String marketTime;
    private List<String> startDates;
    private List<String> endDates;
    private List<Double> midRates;
    private List<Double> spreads;
    // Constructors, getters, setters
}

// FuturesChain.java
public class FuturesChain {
    private String contract;
    private String name;
    private String businessDate;
    private String location;
    private String marketTime;
    private List<String> dates;
    private List<Double> midRates;
    // Constructors, getters, setters
}
