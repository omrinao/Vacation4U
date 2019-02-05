package Views;

import Users.User;
import Vacations.Vacation;
import Vacations.VacationRequest;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PendingVacationsView extends ARegisteredView {
    @Override
    public void prepareView(User username, boolean isManager) {
        _loggedUser = username;
        _manager = isManager;

        ArrayList<VacationRequest> vacations = _controller.getVacationsForApproval(username.get_userName());

        /*
        need to show the vacations here
         */
    }


    /**
     * method to accept a buyers offer
     * @param mouseEvent - mouse click on 'approve'
     */
    public void acceptPurchase(MouseEvent mouseEvent){
        mouseEvent.consume();
        /*
        get the proper vacation id
         */

        String vacationId = "";
        String vacationBuyer = "";

        String response = _controller.approveVacation(_loggedUser.get_userName(), vacationId, vacationBuyer);

        if (response.contains("error")){
            popProblem(response);
        }
        else{
            popInfo(response);
            /*
            set every other as unapproved
             */
        }
    }



}
