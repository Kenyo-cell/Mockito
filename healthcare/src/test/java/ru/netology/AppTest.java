package ru.netology;

import  org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import ru.netology.entity.BloodPressure;
import ru.netology.entity.HealthInfo;
import ru.netology.entity.PatientInfo;

import ru.netology.repository.PatientInfoFileRepository;
import ru.netology.repository.PatientInfoRepository;

import ru.netology.service.alert.SendAlertService;
import ru.netology.service.alert.SendAlertServiceImpl;

import ru.netology.service.medical.MedicalService;
import ru.netology.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;


public class AppTest 
{
    @Test
    public void badPressureTest()
    {
        PatientInfo id1 = new PatientInfo(
                "1",
                "A",
                "A",
                LocalDate.of(1988, 2, 2),
                new HealthInfo(
                        new BigDecimal(36.6),
                        new BloodPressure(130, 70)
                )
        );

        PatientInfoRepository patientInfoRepository =
                Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(id1);

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkBloodPressure("1", new BloodPressure(120, 80));

        Mockito.verify(alertService, Mockito.times(1)).send(stringCaptor.capture());
        System.out.println(stringCaptor.getValue());
        Assertions.assertTrue(!stringCaptor.getValue().isEmpty());
    }

    @Test
    public void badTempTest()
    {
        PatientInfo id1 = new PatientInfo(
                "1",
                "A",
                "A",
                LocalDate.of(1988, 2, 2),
                new HealthInfo(
                        new BigDecimal(38.9),
                        new BloodPressure(120, 80)
                )
        );

        PatientInfoRepository patientInfoRepository =
                Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(id1);

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);

        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("1", new BigDecimal(36.6));

        Mockito.verify(alertService, Mockito.times(1)).send(stringCaptor.capture());
        System.out.println(stringCaptor.getValue());
        Assertions.assertTrue(!stringCaptor.getValue().isEmpty());
    }

    @Test
    public void allRightTest()
    {
        PatientInfo id1 = new PatientInfo(
                "1",
                "A",
                "A",
                LocalDate.of(1988, 2, 2),
                new HealthInfo(
                        new BigDecimal(36.9),
                        new BloodPressure(120, 80)
                )
        );

        PatientInfoRepository patientInfoRepository =
                Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(id1);

        SendAlertService alertService = Mockito.mock(SendAlertServiceImpl.class);


        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
        medicalService.checkTemperature("1", new BigDecimal(36.6));
        medicalService.checkBloodPressure("1", new BloodPressure(120, 80));

        Mockito.verify(alertService, Mockito.never()).send("Warning, patient with id: 1, need help");
    }
}
