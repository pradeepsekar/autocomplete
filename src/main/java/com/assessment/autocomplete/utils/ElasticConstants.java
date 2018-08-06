package com.assessment.autocomplete.utils;

public class ElasticConstants {

  private ElasticConstants() {}

  /**
   * Index of data in CSV file.
   */
  public static final int CITY_NAME_INDEX = 0;
  public static final int PINCODE_INDEX = 1;
  public static final int CIRCLE_INDEX = 6;
  public static final int TALUK_INDEX = 7;
  public static final int DISTRICT_INDEX = 8;
  public static final int STATE_INDEX = 9;
  public static final int EXPECTED_LENGTH = 10;

  // data delimiter
  public static final String DATA_SEPERATOR = ",";
  // data encoding type
  public static final String DATA_ENCODING_TYPE = "UTF-8";
  // wild card search indicator
  public static final String WILD_CARD_SEARCH = "*";

  /**
   * Field names in elastic search city index
   */
  public static final String FIELD_HITS = "hits";
  public static final String FIELD_NAME = "name";

}
