package com.vishaldhawan.pilot.ticketservice.controllers;

import com.vishaldhawan.pilot.ticketservice.beans.Ticket;
import com.vishaldhawan.pilot.ticketservice.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/tickets")
public class TicketsController {

    @Autowired
    private Configuration configuration;

    @GetMapping
    public Ticket getTicket() {
        return new Ticket(configuration.getMinimum(), configuration.getMaximum());
    }
}
