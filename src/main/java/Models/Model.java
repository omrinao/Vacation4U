package Models;

import Users.User;
import Vacations.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class Model {
    //Const
    private final String DB_URL = "jdbc:sqlite:src/main/resources/DB/DataBase.db";

    // helpful attributes
    private ResultSet m_results;

    public Model() {
        // createUsersTable();
        // createVacationstable();
    }

    /**
     * a method to return a connection with the Database
     *
     * @return - a connection if success, null otherwise
     */
    private Connection make_connection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * checks if a given username already exist
     *
     * @param userName - the given username to check
     * @return - true if exist, false otherwise
     */
    private boolean user_exist(String userName) {
        String sql = "SELECT * FROM Users WHERE UserName = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userName);
            m_results = pstmt.executeQuery();

            if (!m_results.next()) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }

    /**
     * a method to create a user
     *
     * @param attributes - list of needed attributed by a specific order!
     */
    public String create_user(ArrayList<String> attributes) {

        if (user_exist(attributes.get(0))) {
            return "Username: " + attributes.get(0) + "\nalready exist!";
        }

        String sql = "INSERT INTO Users (UserName,Password,Birthday,FirstName,LastName,Hometown)"
                + " VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attributes.get(0)); // username
            pstmt.setString(2, attributes.get(1)); // password
            pstmt.setString(3, attributes.get(2)); // birthday
            pstmt.setString(4, attributes.get(3)); // first name
            pstmt.setString(5, attributes.get(4)); // last name
            pstmt.setString(6, attributes.get(5)); // hometown

            pstmt.executeUpdate();
            return "Created :)";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Failed :/";
        }
    }

    /**
     * a method to read a user tuple by it's UserName
     *
     * @param userName - the username desired
     */
    public ArrayList<String> read_user(String userName) {
        String sql = "SELECT * FROM Users WHERE UserName = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userName);
            m_results = pstmt.executeQuery();
            ArrayList<String> toReturn = new ArrayList<>();

            toReturn.add(m_results.getString(1));
            toReturn.add(m_results.getString(2));
            toReturn.add(m_results.getString(3));
            toReturn.add(m_results.getString(4));
            toReturn.add(m_results.getString(5));
            toReturn.add(m_results.getString(6));

            return toReturn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * a method to update the database
     *
     * @param toChange - username to be changed
     * @param newatt   - arraylist of attributes to update
     */
    public String update_user(String toChange, ArrayList<String> newatt) {
        // checking if the new username already exist
        if (!user_exist(toChange)) {
            return "Users: " + toChange + "\ndoes not exist!";
        }

        String sql = "UPDATE Users "
                + "SET UserName = ? , "
                + "Password = ? , "
                + "Birthday = ? , "
                + "FirstName = ? , "
                + "LastName = ? , "
                + "Hometown = ? "
                + "WHERE UserName = ?";

        try (Connection conn = this.make_connection()
        ) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, newatt.get(0));
            pstmt.setString(2, newatt.get(1));
            pstmt.setString(3, newatt.get(2));
            pstmt.setString(4, newatt.get(3));
            pstmt.setString(5, newatt.get(4));
            pstmt.setString(6, newatt.get(5));
            pstmt.setString(7, toChange);

            // update
            pstmt.executeUpdate();
            return "Update success";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Update failed";
        }
    }

    /**
     * a method to delete user
     *
     * @param toDelete - username to delete
     */
    public String delete_user(String toDelete) {
        if (!user_exist(toDelete)) {
            return "Username: " + toDelete + "\ndoes not exist";
        }

        String sql = "DELETE FROM Users WHERE UserName = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, toDelete);

            // execute the delete statement
            pstmt.executeUpdate();
            return "Delete success";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Delete failed";
        }
    }

    /**
     * method to check login
     *
     * @param username - given username
     * @param password - given password
     * @return - string of success or fail
     */
    public User login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE UserName = ? AND Password = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            m_results = pstmt.executeQuery();

            User user = null;
            if (m_results.next()) {
                user = new User();
                user.set_userName(m_results.getString(1));
                user.set_password(m_results.getString(2));
                user.set_birthday(m_results.getString(3));
                user.set_firstName(m_results.getString(4));
                user.set_lastName(m_results.getString(5));
                user.set_homeTown(m_results.getString(6));
            }

            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }


    /**
     * a method to create a vacation
     *
     * @param toPublish - object representing a vacation
     */
    public String publishVacation(Vacation toPublish) {

        String sql = "INSERT INTO Vacations " +
                "(SellerName,Destination,ArrivalDate,DepartureDate,Airline,TicketAmount,Price,Tradable,Status,Origin)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, toPublish._sellingUser);
            pstmt.setString(2, toPublish._destination);
            pstmt.setString(3, toPublish._returnDate);
            pstmt.setString(4, toPublish._departureDate);
            pstmt.setString(5, toPublish._airline);
            pstmt.setString(6, toPublish._quantity);
            pstmt.setString(7, toPublish._price);
            pstmt.setString(8, toPublish._forTrade);
            pstmt.setString(9, "Published");
            pstmt.setString(10, toPublish._origin);
            pstmt.executeUpdate();

            return "Created :)";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "Failed :/";
        }
    }

    /**
     * method to retrieve all vacations waiting for approval
     *
     * @param username - the username of which vacations are waiting for approval
     * @return - list of vacation requests
     */
    public ArrayList<VacationRequest> getVacationsForApproval(String username) {
        String sql = "SELECT * FROM pendingVacations WHERE SellerName = ? "
                + "AND VacationId NOT IN ( SELECT VacationId FROM pendingVacations WHERE SellerName = ? AND " +
                "status IN ('payment') )";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, username);
            m_results = pstmt.executeQuery();

            ArrayList<VacationRequest> retrieved = new ArrayList<>();

            while (m_results.next()) {
                String vacationId = m_results.getString(1);
                String buyer = m_results.getString(2);
                String date = m_results.getString(4);

                VacationRequest r = new VacationRequest(buyer, date, vacationId);
                retrieved.add(r);
            }


            return retrieved;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * method to approve a vacation of a buyer
     *
     * @param vacationId    - the vacation id
     * @param vacationBuyer - the buyer username
     * @return - success or fail
     */
    public String approveVacation(String username, String vacationId, String vacationBuyer) {
        if (check_approved(username, vacationId)){
            return "error\nYou already approved someone for that vacation\nPlease wait for him to respond";
        }

        String sql = "UPDATE pendingVacations " +
                "SET status = ? " +
                "WHERE VacationId = ? AND potentialBuyerName = ?";
        String sql2 = "DELETE FROM Trades WHERE vID1 = ? OR vID2 = ?";

        try (
                Connection conn = this.make_connection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                PreparedStatement deleteTrade = conn.prepareStatement(sql2)
        ) {
            int vid = Integer.valueOf(vacationId);
            pstmt.setString(1, "payment");
            pstmt.setInt(2, vid);
            pstmt.setString(3, vacationBuyer);


            deleteTrade.setInt(1, vid );
            deleteTrade.setInt(2, vid);

            deleteTrade.executeUpdate();
            pstmt.executeUpdate();


            return "Buyer approved!\nPlease update us once " + vacationBuyer + " have payed";
        } catch (SQLException e) {
            System.out.println("something bad happened while trying to update pendingVacations table :(");
            System.out.println(e.getMessage());
            return "error while updating the approval";
        }
    }

    /**
     * method to retrieve all available vacations
     * @return - list of available vacations
     */
    public ArrayList<Vacation> getAllVacations(String username) {
        String sql = "SELECT * FROM Vacations WHERE Status NOT IN ('sold')";

        try (
                Connection conn = make_connection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            m_results = pstmt.executeQuery();
            ArrayList<Vacation> vacations = new ArrayList<>();

            while (m_results.next()) {
                Vacation v = new Vacation();

                v._id = m_results.getString(1);
                v._sellingUser = m_results.getString(2);
                v._destination = m_results.getString(3);
                v._returnDate = m_results.getString(4);
                v._departureDate = m_results.getString(5);
                v._airline = m_results.getString(6);
                v._quantity = m_results.getString(7);
                v._price = m_results.getString(8);
                v._forTrade = m_results.getString(9);
                v._origin = m_results.getString(11);

                vacations.add(v);
            }

            return vacations;

        } catch (SQLException e) {
            System.out.println("Something bad happaned while retrieving data from Vacations");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * method used to make a bid on a published vacation
     * @param sellerName - the seller name of that vacation
     * @param bidderUsername - the username of the bidder
     * @param vacationId - the vacation id
     * @return - success or fail
     */
    public String bidVacation(String sellerName, String bidderUsername, String vacationId, String price) {

        String sql1 = "UPDATE Vacations "
                + "SET Status = 'bid' "
                + "WHERE VacationId = ?";

        String sql2 = "INSERT INTO pendingVacations (VacationId,SellerName,PotentialBuyerName,bidedAt,Price,Status)"
                + " VALUES(?,?,?,?,?,?)";


        try (
                Connection conn = make_connection();
                PreparedStatement ps1 = conn.prepareStatement(sql1);
                PreparedStatement ps2 = conn.prepareStatement(sql2)
        ){

            ps1.setInt(1, Integer.valueOf(vacationId));
            ps2.setInt(1, Integer.valueOf(vacationId));
            ps2.setString(2, sellerName);
            ps2.setString(3, bidderUsername);
            ps2.setString(4, LocalDate.now().toString());
            ps2.setString(5, price);
            ps2.setString(6, "waiting");

            ps1.executeUpdate();
            ps2.executeUpdate();

            return "Bid success!\nPlease wait for the seller to respond";

        }
        catch (SQLException e){
            if (e.getErrorCode() == 19){
                return "error! \nYou already placed a bid for that vacation!\nWait patiently for the seller to approve :)";
            }
            else{
                System.out.println("something bad happened while inserting into pendingVacations :(");
                System.out.println(e.getMessage() + "\n" + e.getErrorCode());
            }


            return "error";
        }
    }

    /**
     * checking if a seller already approved someone for a specific vacation
     * @param sellerName - the seller of the vacation
     * @param vacationId - id of that vacation
     * @return - true if already approved someone, false otherwise
     */
    private boolean check_approved(String sellerName, String vacationId) {

        String sql = "SELECT * FROM pendingVacations WHERE SellerName = ? AND VacationId = ? AND status IN('payment')";

        try (
                Connection conn = make_connection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ){

            pst.setString(1, sellerName);
            pst.setInt(2, Integer.valueOf(vacationId));

            m_results = pst.executeQuery();

            return m_results.next();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return true;
        }
    }


    /**
     *
     * NOT IN USE
     * @param username - the username of which to get the status
     * @return - list of vacations for payment
     */
    public ArrayList<VacationPayment> getVacationsForPayment(String username){
        String sql = "SELECT * FROM pendingVacations WHERE potentialBuyerName = ? AND " +
                "status IN ('payment')";

        try (
                Connection conn = make_connection();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ){

            pstm.setString(1, username);
            m_results = pstm.executeQuery();

            ArrayList<VacationPayment> forPayment = new ArrayList<>();
            while (m_results.next()){
                String vID = m_results.getString(1);
                String sellerName = m_results.getString(3);
                String date = m_results.getString(4);
                String price = m_results.getString(5);

                forPayment.add(new VacationPayment(vID,sellerName,date, price));

            }

            return forPayment;
        }
        catch (SQLException e){
            System.out.println("something bad happened while retrieving data from pendingVacations");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * method to retrieve all vacations needed to confirm pay
     * @param sellerName - the seller that need to confirm received payment
     * @return - list of vacation to confirm
     */
    public ArrayList<VacationSell> getVacationForApprovePayment(String sellerName){
        String sql = "SELECT * FROM pendingVacations WHERE SellerName = ? AND " +
                "status IN ('payment')";

        try (
                Connection conn = make_connection();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ){

            pstm.setString(1, sellerName);
            m_results = pstm.executeQuery();

            ArrayList<VacationSell> forPayment = new ArrayList<>();
            while (m_results.next()){
                String vID = m_results.getString(1);
                String buyerName = m_results.getString(2);
                String date = m_results.getString(4);
                String price = m_results.getString(5);

                forPayment.add(new VacationSell(vID, buyerName, date, price));

            }

            return forPayment;
        }
        catch (SQLException e){
            System.out.println("something bad happened while retrieving data from pendingVacations");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * NOT IN USE
     * method to set payment for a vacation
     * @param vacationId - id of a vacation
     * @param username - the username that payed
     * @param seller - the seller of the vacation
     * @param price - the price of which the buyer payed
     * @param payMethod - payment method visa/paypal
     * @return - success of fail
     */
    public String payForVacation(String vacationId, String username, String seller, String price, String payMethod, int payments){
        String sql = "DELETE FROM pendingVacations WHERE VacationId = ? AND potentialBuyerName = ?";
        String sql2 = "UPDATE Vacations SET Status = 'sold'"; /* why there is no where clause here ?? */
        String sqlInsertSold = "INSERT INTO SoldVacations (VacationId,SellerName,BuyerName,purchaseDay,Price,PayMethod, Payments)"
                +   " VALUES(?,?,?,?,?,?,?)";

        try (
                Connection conn = make_connection();
                PreparedStatement pst1 = conn.prepareStatement(sql);
                PreparedStatement pst2 = conn.prepareStatement(sql2);
                PreparedStatement pstSold = conn.prepareStatement(sqlInsertSold)
        ){

            pst1.setInt(1, Integer.valueOf(vacationId));
            pst1.setString(2, username);

            pstSold.setInt(1, Integer.valueOf(vacationId));
            pstSold.setString(2, seller);
            pstSold.setString(3, username);
            pstSold.setString(4, LocalDate.now().toString());
            pstSold.setString(5, price);
            pstSold.setString(6, payMethod);
            pstSold.setInt(7, payments);

            pst1.executeUpdate();
            pst2.executeUpdate();
            pstSold.executeUpdate();

            return "Payment received!\nEnjoy your vacation!";

        }
        catch (SQLException e){
            System.out.println("something bad happened while updating payment");
            System.out.println(e.getMessage());
            return "error";
        }
    }

    /**
     * method to confirm that payment has been made
     * @param vacationId - the vacation id
     * @param seller - the seller of the vacation
     * @param buyer - the buyer of the vacation
     * @param price - the price of the vacation
     * @return - response weather succeeded or failed
     */
    public String confirmPayment(String vacationId, String seller, String buyer, String price){
        String sql = "DELETE FROM pendingVacations WHERE VacationId = ? AND potentialBuyerName = ?";
        String sql2 = "UPDATE Vacations SET Status = 'sold' WHERE VacationId = ?";
        String sqlInsertSold = "INSERT INTO SoldVacations (VacationId,SellerName,BuyerName,purchaseDay,Price,PayMethod, Payments)"
                +   " VALUES(?,?,?,?,?,?,?)";

        try (
                Connection conn = make_connection();
                PreparedStatement pst1 = conn.prepareStatement(sql);
                PreparedStatement pst2 = conn.prepareStatement(sql2);
                PreparedStatement pstSold = conn.prepareStatement(sqlInsertSold)
        ){

            pst1.setInt(1, Integer.valueOf(vacationId));
            pst1.setString(2, buyer);

            pst2.setInt(1, Integer.valueOf(vacationId));

            pstSold.setInt(1, Integer.valueOf(vacationId));
            pstSold.setString(2, seller);
            pstSold.setString(3, buyer);
            pstSold.setString(4, LocalDate.now().toString());
            pstSold.setString(5, price);
            pstSold.setString(6, "cash");
            pstSold.setInt(7, 1);

            pst1.executeUpdate();
            pst2.executeUpdate();
            pstSold.executeUpdate();

            return "You have received payment!\nThanks letting us know";

        }
        catch (SQLException e){
            System.out.println("something bad happened while updating payment");
            System.out.println(e.getMessage());
            return "error";
        }
    }

    /**
     * method to get all vacation purchased by a user
     * @param username - the user of the purchasing
     * @return - list of vacations
     */
    public ArrayList<Vacation> getMyVacations(String username){
        String sql = "SELECT * FROM Vacations WHERE VacationId IN ( SELECT VacationId FROM SoldVacations WHERE " +
                "BuyerName = ? )";

        try (
                Connection conn = make_connection();
                PreparedStatement pst = conn.prepareStatement(sql)
        ){

            pst.setString(1, username);

            m_results = pst.executeQuery();

            ArrayList<Vacation> toReturn = new ArrayList<>();
            while (m_results.next()){
                Vacation v = new Vacation();

                v._id = m_results.getString(1);
                v._sellingUser = m_results.getString(2);
                v._destination = m_results.getString(3);
                v._returnDate = m_results.getString(4);
                v._departureDate = m_results.getString(5);
                v._airline = m_results.getString(6);
                v._quantity = m_results.getString(7);
                v._price = m_results.getString(8);
                v._forTrade = m_results.getString(9);
                v._origin = m_results.getString(11);

                toReturn.add(v);
            }

            return toReturn;
        }
        catch (SQLException e){
            //System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * method to retrieve all published and unsold vacations of a user
     * @param username - the user
     * @return - list of vacations
     */
    private ArrayList<Vacation> getMyPublishedNotSoldVacations(String username) {
        String sql = "SELECT * FROM Vacations WHERE SellerName = ? AND " +
                "status NOT IN ('sold')";

        try (
                Connection conn = make_connection();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ){

            pstm.setString(1, username);
            m_results = pstm.executeQuery();

            ArrayList<Vacation> forPayment = new ArrayList<>();
            while (m_results.next()){
                Vacation v = new Vacation();

                v._id = m_results.getString(1);
                v._sellingUser = m_results.getString(2);
                v._destination = m_results.getString(3);
                v._returnDate = m_results.getString(4);
                v._departureDate = m_results.getString(5);
                v._airline = m_results.getString(6);
                v._quantity = m_results.getString(7);
                v._price = m_results.getString(8);
                v._forTrade = m_results.getString(9);
                v._origin = m_results.getString(11);

                forPayment.add(v);
            }

            return forPayment;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * method to get vacation eligible for trade by a user
     * @param username - the user
     * @return - list of tradable vacations
     */
    public ArrayList<Vacation> getMyVacationForTrade(String username){
        ArrayList<Vacation> myVacations = getMyVacations(username);
        ArrayList<Vacation> publishVacations = getMyPublishedNotSoldVacations(username);
        ArrayList<Vacation> toReturn = new ArrayList<>();

        if (myVacations==null && publishVacations==null){
            return null;
        }
        if (myVacations != null){
            for (Vacation v :
                    myVacations){
                LocalDate departure = LocalDate.parse(v._departureDate);
                if (departure.isAfter(LocalDate.now())){
                    toReturn.add(v);
                }
            }
        }
        if (publishVacations!=null){
            toReturn.addAll(publishVacations);
        }

        return toReturn;

    }

    /**
     * method to make trade bids
     * @param vTrade - the trade vacation
     * @return - string if succeeded or not
     */
    public String bidTrade(VacationTrade vTrade) {
        try {
            if (alreadyMadeTradeBid(vTrade)){
                return "You already made a bid for that vacation with the selected vacation!";
            }
        }
        catch (SQLException e){
            return "Error while connecting to DB: " + e.getMessage();
        }


        String sql = "INSERT INTO Trades (vID1,user1,vID2,user2)"
                +   " VALUES(?,?,?,?)";
        //String updateSQL = "UPDATE Vacations SET SellerName = ? WHERE VacationId = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
             //PreparedStatement update = conn.prepareStatement(updateSQL)
             ) {

            pstmt.setInt(1, Integer.valueOf(vTrade.get_vID1()));
            pstmt.setString(2, vTrade.get_user1());
            pstmt.setInt(3, Integer.valueOf(vTrade.get_vID2()));
            pstmt.setString(4, vTrade.get_user2());

//            update.setString(1, vTrade.get_user2());
//            update.setInt(2 ,vTrade.getIntID2());

            pstmt.executeUpdate();
//            update.executeUpdate();

            return "ok";
        } catch (SQLException e) {
            return "Something bad happened during trade writing\n" + e.getMessage();
        }
    }

    /**
     * method to decide weather a given trade offer was already made
     * @param vTrade - the trade to examine
     * @return - true if exist, false otherwise
     */
    private boolean alreadyMadeTradeBid(VacationTrade vTrade) throws SQLException{
        String sql = "SELECT * FROM Trades WHERE vID1 = ? AND vID2 = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Integer.valueOf(vTrade.get_vID1()));
            pstmt.setInt(2, Integer.valueOf(vTrade.get_vID2()));

            m_results = pstmt.executeQuery();

            return m_results.next();
        }

    }

    /**
     * method to get all mappings of vacation to it's trade offers
     * in order to approve one of them
     * @param username - the user needs to approve
     * @return - mapping of vacation and it's trade offers
     */
    public Map<String, List<Vacation>> vacationsTradesForApprove(String username){
        TreeMap<String, List<Vacation>> toReturn = new TreeMap<>();
        ArrayList<VacationTrade> trades = null;

        try {
            trades = getTradeOffersForUser(username);
        }
        catch (SQLException e){
            return null;
        }

        /* by here trades will never be null */
        for (VacationTrade vTrade :
                trades){
            List<Vacation> list = toReturn.computeIfAbsent(vTrade.get_vID1(), k -> new ArrayList<>());
            Vacation offer = getVacation(vTrade.get_vID2());
            if (offer != null){
                offer._sellingUser = vTrade.get_user2();
                list.add(offer);
            }
        }

        return toReturn;
    }

    /**
     * method to get a vacation by it's id
     * @param vid2 - the requested vacation
     * @return - vacation object represents the record
     */
    private Vacation getVacation(String vid2) {
        String sql = "SELECT * FROM Vacations WHERE VacationId = " + vid2;

        try (
                Connection con = make_connection();
                PreparedStatement pstm = con.prepareStatement(sql)
                ){

            m_results = pstm.executeQuery();

            Vacation v = null;
            if (m_results.next()){ // should always get in here
                v = new Vacation();
                v._id = m_results.getString(1);
                v._sellingUser = m_results.getString(2);
                v._destination = m_results.getString(3);
                v._returnDate = m_results.getString(4);
                v._departureDate = m_results.getString(5);
                v._airline = m_results.getString(6);
                v._quantity = m_results.getString(7);
                v._price = m_results.getString(8);
                v._forTrade = m_results.getString(9);
                v._origin = m_results.getString(11);
            }

            return v;
        }
        catch (SQLException e){
            return null;
        }
    }

    /**
     * method to get offer trades of a given user
     * @param username - the user
     * @return - list of vacation trades
     * @throws SQLException - if an error occurred
     */
    private ArrayList<VacationTrade> getTradeOffersForUser(String username) throws SQLException{
        String vacationsSQL = "SELECT * FROM Trades WHERE user1 = ?";

        try(
                Connection conn = make_connection();
                PreparedStatement pstm = conn.prepareStatement(vacationsSQL)
        ){
            pstm.setString(1, username);

            m_results = pstm.executeQuery();

            ArrayList<VacationTrade> vacationTrades = new ArrayList<>();
            while (m_results.next()){
                VacationTrade v = new VacationTrade();
                v.set_vID1(m_results.getString(2));
                v.set_user1(m_results.getString(3));
                v.set_vID2(m_results.getString(4));
                v.set_user2(m_results.getString(5));

                vacationTrades.add(v);
            }

            return vacationTrades;
        }
    }

    /**
     * method used to approve trade between users
     * @param vTrade - the trade to approve
     * @return - string representation of status: success == ok, fail == otherwise
     */
    public String approveTrade(VacationTrade vTrade){
        /*
        1. set both vacations as sold
        2. insert both vacations into sold vacations table
        3. delete both vacations from trades
         */
        String updateSQL = "UPDATE Vacations SET Status = 'sold' WHERE VacationId = ? OR VacationId = ?";
        String insertSQL1 = "INSERT INTO SoldVacations (VacationId,SellerName,BuyerName,purchaseDay,Price,PayMethod, Payments)"
                +   " VALUES(?,?,?,?,?,?,?)";
        String insertSQL2 = "INSERT INTO SoldVacations (VacationId,SellerName,BuyerName,purchaseDay,Price,PayMethod, Payments)"
                +   " VALUES(?,?,?,?,?,?,?)";
        String deleteSQL = "DELETE FROM Trades WHERE vID1 = ? OR vID1 = ? " +
                "OR vID2 = ? OR vID2 = ?";
        String deleteSold = "DELETE FROM SoldVacations WHERE VacationId IN ( ? , ? ) AND BuyerName IN ( ? , ? )";
        String deletePending = "DELETE FROM pendingVacations WHERE VacationId IN ( ? , ? )";

        try (
                Connection conn = make_connection();
                PreparedStatement update = conn.prepareStatement(updateSQL);
                PreparedStatement insert1 = conn.prepareStatement(insertSQL1);
                PreparedStatement insert2 = conn.prepareStatement(insertSQL2);
                PreparedStatement delete = conn.prepareStatement(deleteSQL);
                PreparedStatement delete2 = conn.prepareStatement(deleteSold);
                PreparedStatement delete3 = conn.prepareStatement(deletePending)
                ){
            delete2.setInt(1, vTrade.getIntID1());
            delete2.setInt(2, vTrade.getIntID2());
            delete2.setString(3, vTrade.get_user1());
            delete2.setString(4, vTrade.get_user2());

            delete3.setInt(1, vTrade.getIntID1());
            delete3.setInt(2, vTrade.getIntID2());

            update.setInt(1, Integer.valueOf(vTrade.get_vID1()));
            update.setInt(2, Integer.valueOf(vTrade.get_vID2()));

            insert1.setInt(1, Integer.valueOf(vTrade.get_vID1()));
            insert1.setString(2, vTrade.get_user1()); // this user - the one accepted
            insert1.setString(3, vTrade.get_user2()); // user made the offer
            insert1.setString(4, LocalDate.now().toString());
            insert1.setString(5, "0.0");
            insert1.setString(6, "trade");
            insert1.setInt(7, 1);

            insert2.setInt(1, Integer.valueOf(vTrade.get_vID2()));
            insert2.setString(2, vTrade.get_user2()); // user made the offer
            insert2.setString(3, vTrade.get_user1()); // this user - the one accepted
            insert2.setString(4, LocalDate.now().toString());
            insert2.setString(5, "0.0");
            insert2.setString(6, "trade");
            insert2.setInt(7, 1);

            delete.setInt(1, vTrade.getIntID1());
            delete.setInt(2, vTrade.getIntID2());
            delete.setInt(3, vTrade.getIntID1());
            delete.setInt(4, vTrade.getIntID2());

            delete3.executeUpdate();
            delete2.executeUpdate();
            update.executeUpdate();
            insert1.executeUpdate();
            insert2.executeUpdate();
            delete.executeUpdate();

            return "ok";
        }
        catch (SQLException e){
            return "Error while preforming trade. Info: " + e.getMessage();
        }
    }
}
