package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import ru.netology.entity.Country;
import ru.netology.entity.Location;

import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

public class LocationTest {
    @Test
    public void locationByIpTest()
    {
        GeoService geoService = new GeoServiceImpl();
        Location location = new Location("Moscow", Country.RUSSIA, null, 0);
        Assertions.assertEquals(location.getCity(), geoService.byIp("172.0.32.11").getCity());
    }

    @Test
    public void locationByIpCoordinates()
    {
        GeoService geoService = new GeoServiceImpl();
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(123.2, 89.9));
    }
}
