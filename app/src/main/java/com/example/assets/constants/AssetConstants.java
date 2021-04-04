package com.example.assets.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AssetConstants {

    public static final String ASSET_SYMBOL = "assetSymbol";
    public static final String ASSET_TYPE = "assetType";
    public static final String SPECIFIC_ASSETS = "specificAssets";
    public static final String EDITED_ASSET = "editedAsset";

    private static final String EUR = "EUR";
    private static final String USD = "USD";
    private static final String CHF = "CHF";
    private static final String JPY = "JPY";
    private static final String GBP = "GBP";
    private static final String PLN = "PLN";
    private static final String NOK = "NOK";
    private static final String SEK = "SEK";
    private static final String DDK = "DKK";

    private static final String BTC = "BTC";
    private static final String ETH = "ETH";
    private static final String LTC = "LTC";
    private static final String XRP = "XRP";
    private static final String ADA = "ADA";
    private static final String DOT = "DOT";
    private static final String LINK = "LINK";
    private static final String MATIC = "MATIC";
    private static final String AVAX = "AVAX";
    private static final String ATOM = "ATOM";
    private static final String UTK = "UTK";
    private static final String REEF = "REEF";

    private static final String GOLD = "Gold";
    private static final String SILVER = "Silver";
    private static final String PLATINUM = "Platinum";
    private static final String PALLADIUM = "Palladium";

    public static final String CURRENCIES = "Currencies";
    public static final String CRYPTOS = "Cryptocurrencies";
    public static final String METALS = "Metals";

    public static final ArrayList<String> ALL_CURRENCIES = new ArrayList<>(Arrays.asList(EUR, USD, CHF, JPY, GBP, PLN, NOK, SEK, DDK));
    public static final ArrayList<String> ALL_CRYPTOS = new ArrayList<>(Arrays.asList(BTC, ETH, LTC, XRP, ADA, DOT, LINK, MATIC, AVAX, ATOM, UTK, REEF));
    public static final ArrayList<String> ALL_METALS = new ArrayList<>(Arrays.asList(GOLD, SILVER, PLATINUM, PALLADIUM));

    public static final List<String> ALL_ASSETS = Arrays.asList(CURRENCIES, CRYPTOS, METALS);

    public static HashMap<String, ArrayList<String>> assetMap = new HashMap<>();

    static {
        assetMap.put(CURRENCIES, ALL_CURRENCIES);
        assetMap.put(CRYPTOS, ALL_CRYPTOS);
        assetMap.put(METALS, ALL_METALS);
    }
}
