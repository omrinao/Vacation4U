package Vacations;

public class VacationSell {

    private String _vacationId;
    private String _buyer;
    private String _date;
    private String _price;

    public VacationSell() {
    }

    public VacationSell(String _vacationId, String _buyer, String _date, String _price) {
        this._vacationId = _vacationId;
        this._buyer = _buyer;
        this._date = _date;
        this._price = _price;
    }

    public String get_vacationId() {
        return _vacationId;
    }

    public void set_vacationId(String _vacationId) {
        this._vacationId = _vacationId;
    }

    public String get_buyer() {
        return _buyer;
    }

    public void set_buyer(String _buyer) {
        this._buyer = _buyer;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
    }
}
