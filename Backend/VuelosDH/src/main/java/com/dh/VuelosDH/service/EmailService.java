package com.dh.VuelosDH.service;

import com.dh.VuelosDH.entities.Reservations;
import com.dh.VuelosDH.mapper.ReservationsMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ReservationsMapper reservationsMapper;

    public EmailService(JavaMailSender mailSender,
                        ReservationsMapper reservationsMapper) {
        this.mailSender = mailSender;
        this.reservationsMapper = reservationsMapper;
    }

    @Async
    public void sendReservationEmail(String to, Reservations reservation) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(reservationsMapper.toSubject(reservation));
        message.setText(reservationsMapper.toBody(reservation));

        mailSender.send(message);
    }
}
