package ru.netology;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;


import ru.netology.entity.Country;
import ru.netology.entity.Location;

import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;


import java.util.HashMap;
import java.util.Map;


public class MessageSenderTest {
    @Test
    public void russianIpSender() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("127.0.0.1"))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));


        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");


        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "127.0.0.1");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Assertions.assertEquals("Добро пожаловать", messageSender.send(headers));
    }

    @Test
    public void usaIpSender() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.44.183.149"))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Assertions.assertEquals("Welcome", messageSender.send(headers));
    }
}
