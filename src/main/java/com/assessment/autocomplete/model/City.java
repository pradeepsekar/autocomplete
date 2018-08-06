package com.assessment.autocomplete.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "city", type = "city", shards = 1)
public class City implements Serializable{

  @Id
  private String name;
  private Long pincode;
  private String circle;
  private String taluk;
  private String district;
  private String state;
  private int hits;

  public City() {
  }

  public City(String name, Long pincode, String circle, String taluk, String district, String state, int hits) {
    this.name = name;
    this.pincode = pincode;
    this.circle = circle;
    this.taluk = taluk;
    this.district = district;
    this.state = state;
    this.hits = hits;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getPincode() {
    return pincode;
  }

  public void setPincode(Long pincode) {
    this.pincode = pincode;
  }

  public String getCircle() {
    return circle;
  }

  public void setCircle(String circle) {
    this.circle = circle;
  }

  public String getTaluk() {
    return taluk;
  }

  public void setTaluk(String taluk) {
    this.taluk = taluk;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getHits() {
    return hits;
  }

  public void setHits(int hits) {
    this.hits = hits;
  }



}
