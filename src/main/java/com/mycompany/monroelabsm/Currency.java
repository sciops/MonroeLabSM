/*
 * The MIT License
 *
 * Copyright 2016 Stephen R. Williams.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mycompany.monroelabsm;

/**
 *
 * @author Stephen R. Williams
 * 
 * This enum describes various currencies.
 */
public enum Currency {

    aed(784, "United Arab Emirates dirham", 2),
    afn(971, "Afghan afghani", 2),
    all(8, "Albanian lek", 2),
    amd(51, "Armenian dram", 2),
    ang(532, "Netherlands Antillean guilder", 2),
    aoa(973, "Angolan kwanza", 2),
    ars(32, "Argentine peso", 2),
    aud(36, "Australian dollar", 2),
    awg(533, "Aruban florin", 2),
    azn(944, "Azerbaijani manat", 2),
    bam(977, "Bosnia and Herzegovina convertible mark", 2),
    bbd(52, "Barbados dollar", 2),
    bdt(50, "Bangladeshi taka", 2),
    bgn(975, "Bulgarian lev", 2),
    bhd(48, "Bahraini dinar", 3),
    bif(108, "Burundian franc", 0),
    bmd(60, "Bermudian dollar", 2),
    bnd(96, "Brunei dollar", 2),
    bob(68, "Boliviano", 2),
    brl(986, "Brazilian real", 2),
    bsd(44, "Bahamian dollar", 2),
    btn(64, "Bhutanese ngultrum", 2),
    bwp(72, "Botswana pula", 2),
    byr(974, "Belarusian ruble", 0),
    bzd(84, "Belize dollar", 2),
    cad(124, "Canadian dollar", 2),
    cdf(976, "Congolese franc", 2),
    che(947, "WIR Euro (complementary currency)", 2),
    chf(756, "Swiss franc", 2),
    chw(948, "WIR Franc (complementary currency)", 2),
    clp(152, "Chilean peso", 0),
    cny(156, "Chinese yuan", 2),
    cop(170, "Colombian peso", 2),
    crc(188, "Costa Rican colon", 2),
    cuc(931, "Cuban convertible peso", 2),
    cup(192, "Cuban peso", 2),
    cve(132, "Cape Verde escudo", 0),
    czk(203, "Czech koruna", 2),
    djf(262, "Djiboutian franc", 0),
    dkk(208, "Danish krone", 2),
    dop(214, "Dominican peso", 2),
    dzd(12, "Algerian dinar", 2),
    egp(818, "Egyptian pound", 2),
    ern(232, "Eritrean nakfa", 2),
    etb(230, "Ethiopian birr", 2),
    //negative values represent fractional denomations.
    //could use double, but would rather squeeze these inside a signed byte.
    eur(978, "Euro", 2, 
            (byte) -1, 
            (byte) -2, 
            (byte) -5, 
            (byte) -10, 
            (byte) -20, 
            (byte) -50, 
            (byte) 1, 
            (byte) 5, 
            (byte) 10, 
            (byte) 20, 
            (byte) 50, 
            (byte) 100, 
            (byte) 200  ),//missing 500 euro note, is this a problem?
    fjd(242, "Fiji dollar", 2),
    fkp(238, "Falkland Islands pound", 2),
    gbp(826, "Pound sterling", 2, 
            (byte) -1, 
            (byte) -2, 
            (byte) -5, 
            (byte) -10, 
            (byte) -20, 
            (byte) -50, 
            (byte) 1, 
            (byte) 2, 
            (byte) 5, 
            (byte) 10, 
            (byte) 20, 
            (byte) 50),
    gel(981, "Georgian lari", 2),
    ghs(936, "Ghanaian cedi", 2),
    gip(292, "Gibraltar pound", 2),
    gmd(270, "Gambian dalasi", 2),
    gnf(324, "Guinean franc", 0),
    gtq(320, "Guatemalan quetzal", 2),
    gyd(328, "Guyanese dollar", 2),
    hkd(344, "Hong Kong dollar", 2),
    hnl(340, "Honduran lempira", 2),
    hrk(191, "Croatian kuna", 2),
    htg(332, "Haitian gourde", 2),
    huf(348, "Hungarian forint", 2),
    idr(360, "Indonesian rupiah", 2),
    ils(376, "Israeli new shekel", 2),
    inr(356, "Indian rupee", 2),
    iqd(368, "Iraqi dinar", 3),
    irr(364, "Iranian rial", 2),
    isk(352, "Icelandic króna", 0),
    jmd(388, "Jamaican dollar", 2),
    jod(400, "Jordanian dinar", 3),
    //to represent yen, postive values are multiples of 1000, negative values represent 1-100 coins, 500 not supported
    jpy(392, "Japanese yen", 0, (byte) -1, (byte) -5, (byte) -10, (byte) -50, (byte) -100, (byte) 1, (byte) 5, (byte) 10),
    kes(404, "Kenyan shilling", 2),
    kgs(417, "Kyrgyzstani som", 2),
    khr(116, "Cambodian riel", 2),
    kmf(174, "Comoro franc", 0),
    kpw(408, "North Korean won", 2),
    krw(410, "South Korean won", 0),
    kwd(414, "Kuwaiti dinar", 3),
    kyd(136, "Cayman Islands dollar", 2),
    kzt(398, "Kazakhstani tenge", 2),
    lak(418, "Lao kip", 2),
    lbp(422, "Lebanese pound", 2),
    lkr(144, "Sri Lankan rupee", 2),
    lrd(430, "Liberian dollar", 2),
    lsl(426, "Lesotho loti", 2),
    lyd(434, "Libyan dinar", 3),
    mad(504, "Moroccan dirham", 2),
    mdl(498, "Moldovan leu", 2),
    mga(969, "Malagasy ariary", 1),
    mkd(807, "Macedonian denar", 2),
    mmk(104, "Myanmar kyat", 2),
    mnt(496, "Mongolian tögrög", 2),
    mop(446, "Macanese pataca", 2),
    mro(478, "Mauritanian ouguiya", 1),
    mur(480, "Mauritian rupee", 2),
    mvr(462, "Maldivian rufiyaa", 2),
    mwk(454, "Malawian kwacha", 2),
    mxn(484, "Mexican peso", 2),
    myr(458, "Malaysian ringgit", 2),
    mzn(943, "Mozambican metical", 2),
    nad(516, "Namibian dollar", 2),
    ngn(566, "Nigerian naira", 2),
    nio(558, "Nicaraguan córdoba", 2),
    nok(578, "Norwegian krone", 2),
    npr(524, "Nepalese rupee", 2),
    nzd(554, "New Zealand dollar", 2),
    omr(512, "Omani rial", 3),
    pab(590, "Panamanian balboa", 2),
    pen(604, "Peruvian Sol", 2),
    pgk(598, "Papua New Guinean kina", 2),
    php(608, "Philippine peso", 2),
    pkr(586, "Pakistani rupee", 2),
    pln(985, "Polish złoty", 2),
    pyg(600, "Paraguayan guaraní", 0),
    qar(634, "Qatari riyal", 2),
    ron(946, "Romanian leu", 2),
    rsd(941, "Serbian dinar", 2),
    rub(643, "Russian ruble", 2),
    rwf(646, "Rwandan franc", 0),
    sar(682, "Saudi riyal", 2),
    sbd(90, "Solomon Islands dollar", 2),
    scr(690, "Seychelles rupee", 2),
    sdg(938, "Sudanese pound", 2),
    sek(752, "Swedish krona/kronor", 2),
    sgd(702, "Singapore dollar", 2),
    shp(654, "Saint Helena pound", 2),
    sll(694, "Sierra Leonean leone", 2),
    sos(706, "Somali shilling", 2),
    srd(968, "Surinamese dollar", 2),
    ssp(728, "South Sudanese pound", 2),
    std(678, "São Tomé and Príncipe dobra", 2),
    syp(760, "Syrian pound", 2),
    szl(748, "Swazi lilangeni", 2),
    thb(764, "Thai baht", 2),
    tjs(972, "Tajikistani somoni", 2),
    tmt(934, "Turkmenistani manat", 2),
    tnd(788, "Tunisian dinar", 3),
    top(776, "Tongan paʻanga", 2),
    /*try ( 949, "Turkish lira", 2 ),*///why does turkey have to have a java keyword as their code?!
    ttd(780, "Trinidad and Tobago dollar", 2),
    twd(901, "New Taiwan dollar", 2),
    tzs(834, "Tanzanian shilling", 2),
    uah(980, "Ukrainian hryvnia", 2),
    ugx(800, "Ugandan shilling", 0),
    usd(840, "United States dollar", 2, 
            (byte) -1, 
            (byte) -5, 
            (byte) -10, 
            (byte) -25, 
            (byte) -50, 
            (byte) 1, 
            (byte) 2, 
            (byte) 5, 
            (byte) 10, 
            (byte) 20, 
            (byte) 50, 
            (byte) 100),
    uyu(858, "Uruguayan peso", 2),
    uzs(860, "Uzbekistan som", 2),
    vef(937, "Venezuelan bolívar", 2),
    vnd(704, "Vietnamese dong", 0),
    vuv(548, "Vanuatu vatu", 0),
    wst(882, "Samoan tala", 2),
    xaf(950, "CFA franc BEAC", 0),
    xag(961, "Silver (one troy ounce)", 0),
    xau(959, "Gold (one troy ounce)", 0),
    xcd(951, "East Caribbean dollar", 2),
    xdr(960, "Special drawing rights", 0),
    xof(952, "CFA franc BCEAO", 0),
    xpd(964, "Palladium (onetroy ounce)", 0),
    xpf(953, "CFP franc(franc Pacifique)", 0),
    xpt(962, "Platinum (onetroy ounce)", 0),
    xsu(994, "SUCRE", 0),
    xua(965, "ADB Unit of Account", 0),
    yer(886, "Yemeni rial", 2),
    zar(710, "South African rand", 2),
    zmw(967, "Zambian kwacha", 2);

    int countryCode;
    private String country;
    private int decimals;
    private byte[] denoms;

    private Currency(int countryCode, String country, int decimals, byte... denoms) {
        this.countryCode = countryCode;
        this.country = country;
        this.decimals = decimals;
        this.denoms = denoms;
    }

}
