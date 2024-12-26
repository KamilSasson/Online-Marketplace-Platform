package AmitGarfel_KamilSason_DanSwirsky;

public class Address {
    private final String country;
    private final String city;
    private final String streetName;
    private final int houseNumber;

    public Address(String country, String city, String streetName, int houseNumber) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return houseNumber + " " + streetName + ", " + city + ", " + country;
    }
}
