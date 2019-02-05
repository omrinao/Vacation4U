package Vacations;

/**
 * this is the Vacations type class.
 */
public class Vacation {
    public String _id;
    public String _departureDate;
    public String _returnDate;
    public String _price;
    public String _sellingUser;
    public String _destination;
    public String _airline;
    public String _quantity;
    public String _forTrade;
    public String _origin;


    public Vacation(){}

    @Override
    public String toString() {
        return "Vacation:{" +
                "_id='" + _id + '\'' +
                ", _sellingUser='" + _sellingUser + '\'' +
                ", _destination='" + _destination + '\'' +
                ", _forTrade='" + _forTrade + '\'' +
                '}';
    }
}
