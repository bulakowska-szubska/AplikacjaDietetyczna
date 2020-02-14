package com.vistula.aplikacja.web.rest;

import com.vistula.aplikacja.service.GmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST controller for managing GmailService.
 */
@RestController
@RequestMapping("/api")
public class GmailResource {

    private final Logger log = LoggerFactory.getLogger(GmailResource.class);
    public GmailService gmailService;

    public GmailResource(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @PostMapping("/wyslijMailKontakt")
    public void uploadFileUmowaRamowa(@RequestParam("imieNazwisko") String imieNazwisko,
                                      @RequestParam("temat") String temat,
                                      @RequestParam("email") String email,
                                      @RequestParam("wiadomosc") String wiadomosc) throws IOException {
        gmailService.sendEmailWithContactFromUser(imieNazwisko, temat, email, wiadomosc);
        log.info("Wysłano maila!");
    }
}
