package Vacations;

public class VacationRequest {

    public String _buyer;
    public String _date;
    public String _vacationId;

    public VacationRequest(String buyer, String requestDate, String vacationId){
        _buyer = buyer;
        _date = requestDate;
        _vacationId = vacationId;
    }
}
