package ui.controller;

import domain.db.SmokerDB;
import domain.exceptions.DomainException;
import domain.model.Smoker;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    // "connection" to the database
    SmokerDB sdb = new SmokerDB();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    /*
    * commands use a personal format that works best for me
    * for example the page named "add" will be called upon with the command get_add, as in GET the add page
    * but to post data inputted into the form on the add page, the command will be add, as in the verb to add
    * this makes sense to me and gives some structure to parameters passed through the http requests.
    * */

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // a session is created whenever the site is visited, so why not just getSession in processRequest
        // I could then use the switch statement to add to the logger, seeing as all actions available in the webapp pass through here
        HttpSession s = request.getSession();

        if (s.getAttribute("log") == null) {
            List<String> log = new ArrayList<>();
            s.setAttribute("log", log);
        }

        // defaults destination to the index page
        String command = "get_home";
        String destination;

        if(request.getParameter("command") != null)
            command = request.getParameter("command");

        switch (command){
            // GET overview
            case "get_overview":
                destination = getOverview(request,response);

                ((ArrayList<String>) s.getAttribute("log")).add("GET - Overview");
                break;
            // GET add
            case "get_add":
                destination = getAdd(request,response);

                ((ArrayList<String>) s.getAttribute("log")).add("GET - Add");
                break;
            // POST voegtoe
            case "add":
                destination = add(request,response);

                if (destination.equals("overview")) {
                    ((ArrayList<String>) s.getAttribute("log")).add("ADD - " + sdb.getSmokers().get(sdb.getSmokers().size()-1).toString());
                } else {
                    ((ArrayList<String>) s.getAttribute("log")).add("ADD - FAILED");
                }
                break;
            // GET zoek
            case "get_search":
                destination = getSearch(request, response);

                if  (request.getParameter("query") ==null){
                    ((ArrayList<String>) s.getAttribute("log")).add("GET - Search");
                } else {
                    ((ArrayList<String>) s.getAttribute("log")).add("GET - Search, query: " + (request.getParameter("query") == "" ? "\"\"" : request.getParameter("query"))+ ", result: " + (request.getAttribute("result") == null ? "(Unsuccessful)" : "(Successful)"));
                }
                break;

            // GET confirm
            case "get_confirm_delete":
                destination = getConfirmDelete(request, response);

                ((ArrayList<String>) s.getAttribute("log")).add("GET - Confirm Deletion of " + ((Smoker) request.getAttribute("item")).toString());
                break;

            case "delete":
                destination = delete(request, response);

                ((ArrayList<String>) s.getAttribute("log")).add("DELETE - See proceeding 'Confirm Deletion' log item.");
                break;

            case "toggle_hidden":
                destination = toggleHidden(request,response);

                ((ArrayList<String>) s.getAttribute("log")).add("Toggle Hidden - Average Value");
                break;
            // I don't think actions related to the logbook should be saved in the logbook, feels kinda too meta (until I'm proven otherwise)
            case "get_logbook":
                destination = getLogbook(request, response);
                break;
            case "clear_logbook":
                destination = clearLogbook(request, response, s);
                break;
            // GET home
            default:
                destination = getHome(request, response);

                ((ArrayList<String>) s.getAttribute("log")).add("GET - Home");
                break;
        }
        request.getRequestDispatcher(destination).forward(request, response);
    }

    private String toggleHidden(HttpServletRequest request, HttpServletResponse response) {
        Cookie hidden = getCookieByKey(request, "hidden");;

        if (hidden != null){
            if (hidden.getValue().equals("true")) {
                hidden.setValue("false");
            } else {
                hidden.setValue("true");
            }
        } else {
            hidden = new Cookie("hidden", "false");
        }
        response.addCookie(hidden);

        return getHome(request,response);
    }

    private String getHome (HttpServletRequest request, HttpServletResponse response) {

        Cookie hidden = getCookieByKey(request, "hidden");

        if (hidden == null){
            hidden = new Cookie("hidden", "false");
        }
        if (hidden.getValue().equals("false")) {
            request.setAttribute("averageDaysElapsed", String.format("%.01f", sdb.calculateAverageDaysElapsed()));
        }
        response.addCookie(hidden);

        return "home";
    }

    private String getOverview (HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");
        // add database "connection" to the HTTP request
        request.setAttribute("smokerDbObject", sdb);

        return "overview";
    }

    private String getAdd (HttpServletRequest request, HttpServletResponse response) {
        return "add";
    }

    private String add (HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> errors = new ArrayList<String>();
        Smoker add_smoker = new Smoker(); // just so that the variable is initialized and .add doesn't make the compiler give and error

        setfName(add_smoker, request, errors);
        setlName(add_smoker, request, errors);
        setStartDate(add_smoker, request, errors);

        if (errors.size() == 0) {
            try {
                sdb.add(add_smoker);
                return getOverview(request, response);
            } catch (DomainException exc) {
                errors.add(exc.getMessage());
            }
        }
        request.setAttribute("errors", errors);
        return "add";
    }

    private String getSearch(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("result", sdb.find(request.getParameter("query")));
        return "search";
    }

    private String delete(HttpServletRequest request, HttpServletResponse response){
        sdb.delete(Integer.parseInt(request.getParameter("id")));

        request.setAttribute("smokerDbObject", sdb);
        request.setAttribute("averageDaysElapsed", sdb.calculateAverageDaysElapsed());
        return "overview";
    }

    private String getConfirmDelete(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("item", sdb.find(Integer.parseInt(request.getParameter("id"))));
        return "confirm";
    }

    private String clearLogbook(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ((ArrayList<String>) session.getAttribute("log")).clear();
        return "logbook";
    }

    private String getLogbook(HttpServletRequest request, HttpServletResponse response) {
        return "logbook";
    }


    // helper functions aka functions that don't primarily add attributes to the request and return a destination
    public void setfName (Smoker s, HttpServletRequest request, ArrayList<String> e) {
        String firstname = request.getParameter("firstname");
        boolean firstnameHasErrors = false;
        try {
            request.setAttribute("firstnamePreviousValue", firstname);
            s.setfName(firstname);
        } catch (DomainException exc) {
            e.add(exc.getMessage());
            firstnameHasErrors = true;
        } finally {
            request.setAttribute("firstnameHasErrors", firstnameHasErrors);
        }
    }

    public void setlName (Smoker s, HttpServletRequest request, ArrayList<String> e) {
        String lastname = request.getParameter("lastname");
        boolean lastnameHasErrors = false;
        try {
            request.setAttribute("lastnamePreviousValue", lastname);
            s.setlName(lastname);
        } catch (DomainException exc) {
            e.add(exc.getMessage());
            lastnameHasErrors = true;
        } finally {
            request.setAttribute("lastnameHasErrors", lastnameHasErrors);
        }
    }

    public void setStartDate (Smoker s, HttpServletRequest request, ArrayList<String> e) {
        String startdate = request.getParameter("startdate");
        boolean startdateHasErrors = false;
        try {
            request.setAttribute("startdatePreviousValue", startdate);
            s.setStartDate(LocalDate.parse(startdate));
        } catch (DomainException exc) {
            e.add(exc.getMessage());
            startdateHasErrors = true;
        } catch (DateTimeException exc) {
            e.add("Please enter a valid start date.");
            startdateHasErrors = true;
        } finally {
            request.setAttribute("startdateHasErrors", startdateHasErrors);
        }
    }

    private Cookie getCookieByKey(HttpServletRequest request, String key){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie c :
                cookies) {
            if (c.getName().equals(key)){
                return c;
            }
        }
        return null;
    }

}
