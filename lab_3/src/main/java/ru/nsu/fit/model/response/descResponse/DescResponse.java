package ru.nsu.fit.model.response.descResponse;

public class DescResponse {
    private String xid;
    private String name;
    private String kinds;
    private String osm;
    private String wikidata;
    private String rate;
    private Sources sources;
    private Bbox bbox;
    private Point point;
    private String otm;
    private String wikipedia;
    private String image;
    private Info info;
    private String error;
    private Address address;

    public String getKinds() {
        return kinds;
    }

    public Sources getSources() {
        return sources;
    }

    public Bbox getBbox() {
        return bbox;
    }

    public Point getPoint() {
        return point;
    }

    public String getOsm() {
        return osm;
    }

    public String getOtm() {
        return otm;
    }

    public String getXid() {
        return xid;
    }

    public String getName() {
        return name;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public String getImage() {
        return image;
    }

    public String getWikidata() {
        return wikidata;
    }

    public String getRate() {
        return rate;
    }

    public Info getInfo() {
        return info;
    }

    public String getError() {
        return error;
    }

    public Address getAddress() {
        return address;
    }
}
