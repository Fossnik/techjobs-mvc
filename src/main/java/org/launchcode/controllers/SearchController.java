package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    @RequestMapping(value = "results", method = RequestMethod.GET)
    public String results(Model model, @RequestParam("searchType") String column,
                          @RequestParam("searchTerm") String value ) {
/*
for each case must model the addAttribute to pass a appropriate title for the case and the results of the jobs query
(arrayList)
 */
// if no search term then create arraylist with all of the jobs
        if (value == null) {
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();
            model.addAttribute("columns", ListController.columnChoices);
            model.addAttribute("jobs", jobs);
            model.addAttribute("title", "All " +
                    ListController.columnChoices.get(column) + " Values");
        }
// if radio button "all" selected then search by value
        else if (column.equals("all")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findByValue(value);
            model.addAttribute("columns", ListController.columnChoices);
            model.addAttribute("jobs", jobs);
            model.addAttribute("title", "Search " +
                    ListController.columnChoices.get(column) + " Values");
        }
// search by column and value
        else {
            ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(column, value);
            model.addAttribute("columns", ListController.columnChoices);
            model.addAttribute("jobs", jobs);
            model.addAttribute("title", "Search " +
                    ListController.columnChoices.get(column) + " Values");
        }
// search.html template
        return "search";
    }



    @RequestMapping(value = "results", method = RequestMethod.POST)
    public String result(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        model.addAttribute("columns", ListController.columnChoices);
        ArrayList<HashMap<String,String>> queryResults = JobData.findByColumnAndValue(searchType, searchTerm);
        model.addAttribute("jobs", queryResults);
        return "search";
    }
}
