package Users;


import javafx.scene.image.Image;

/**
 * this is the user type class.
 */
public class User {
    private String _userName;
    private String _birthday;
    private String _homeTown;
    private String _firstName;
    private String _lastName;
    private String _password;
    private Image _image;


    public User(){}

    public  Image GetImage(){
        return _image;
    }

    public String GetName(){
        return _firstName;
    }

    public String GetLastName(){
        return _lastName;
    }

    public String get_password(){
        return _password;
    }

    public String get_firstName() {
        return _firstName;
    }

    public void set_firstName(String _firstName) {
        this._firstName = _firstName;
    }

    public String get_lastName() {
        return _lastName;
    }

    public void set_lastName(String _lastName) {
        this._lastName = _lastName;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public Image get_image() {
        return _image;
    }

    public void set_image(Image _image) {
        this._image = _image;
    }

    public String get_userName() {
        return _userName;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public String get_birthday() {
        return _birthday;
    }

    public void set_birthday(String _birthday) {
        this._birthday = _birthday;
    }

    public String get_homeTown() {
        return _homeTown;
    }

    public void set_homeTown(String _homeTown) {
        this._homeTown = _homeTown;
    }
}

