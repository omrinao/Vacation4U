package Vacations;

public class VacationTrade {

    private String _vID1;//the one that approve
    private String _user1;
    private String _vID2;
    private String _user2;//the proposer

    public VacationTrade() {
    }

    public String get_vID1() {
        return _vID1;
    }

    public void set_vID1(String _vID1) {
        this._vID1 = _vID1;
    }

    public String get_user1() {
        return _user1;
    }

    public void set_user1(String _user1) {
        this._user1 = _user1;
    }

    public String get_user2() {
        return _user2;
    }

    public void set_user2(String _user2) {
        this._user2 = _user2;
    }

    public String get_vID2() {
        return _vID2;
    }

    public void set_vID2(String _vID2) {
        this._vID2 = _vID2;
    }

    public int getIntID1(){
        return Integer.valueOf(_vID1);
    }

    public int getIntID2(){
        return Integer.valueOf(_vID2);
    }
}
