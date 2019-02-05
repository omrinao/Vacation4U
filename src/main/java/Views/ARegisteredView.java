package Views;

import Users.User;

public abstract class ARegisteredView extends AView {

    public User _loggedUser;
    public boolean _manager;

    public abstract void prepareView(User username, boolean isManager);
}
