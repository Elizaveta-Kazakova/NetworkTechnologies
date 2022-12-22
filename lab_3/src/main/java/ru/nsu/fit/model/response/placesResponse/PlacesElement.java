package ru.nsu.fit.model.response.placesResponse;

public class PlacesElement {
    private String name;
    private String osm;
    private String xid;
    private String wikidata;
    private String kinds;
    private Point point;

    public String getName() {
        return name;
    }

    public String getOsm() {
        return osm;
    }

    public String getXid() {
        return xid;
    }

    public String getWikidata() {
        return wikidata;
    }

    public String getKind() {
        return kinds;
    }

    public Point getPoint() {
        return point;
    }
}
