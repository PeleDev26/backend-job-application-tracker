package com.pelegrin.job_application_tracker.service;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import com.pelegrin.job_application_tracker.dto.DtoCity;
import com.pelegrin.job_application_tracker.dto.DtoRegion;
import com.pelegrin.job_application_tracker.entity.City;
import com.pelegrin.job_application_tracker.entity.Region;
import com.pelegrin.job_application_tracker.repository.CityRepository;
import com.pelegrin.job_application_tracker.repository.RegionRepository;
import tools.jackson.databind.ObjectMapper;

@Component
public class JsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(JsonDataLoader.class);

    private final RegionRepository regionRepo;
    private final CityRepository cityRepo;
    private final ObjectMapper objectMapper;

    private final boolean enabled;
    private final String filePath;

    public JsonDataLoader(RegionRepository regionRepo, CityRepository cityRepo, ObjectMapper objectMapper,
            @Value("${app.cl.load:false}") boolean enabled,
            @Value("${app.cl.file:cl.json}") String filePath) {
        this.regionRepo = regionRepo;
        this.cityRepo = cityRepo;
        this.objectMapper = objectMapper;
        this.enabled = enabled;
        this.filePath = filePath;
    }

    @Override
    public void run(String... args) throws Exception {

        if (!enabled) {
            log.info("CL Json loader disabled (app.cl.load=false)");
            return;
        }

        // Path path = Paths.get(filePath);
        ClassPathResource resource = new ClassPathResource(filePath);

        // log.info("filePath recibido: '{}'", filePath);
        // log.info("resource exists: {}", resource.exists());
        // log.info("resource filename: {}", resource.getFilename());
        // log.info("resource description: {}", resource.getDescription());

        if (!resource.exists()) {
            log.warn("JSON loader file not found or empty:{}", filePath);
            return;
        }

        try (InputStream inputStream = resource.getInputStream()) {
            List<DtoRegion> regions = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<DtoRegion>>() {
                    });

            int regionCount = 0;
            int citiesCount = 0;

            for (DtoRegion dtoRegion : regions) {

                // log.info(
                //         "Region: {}, nro: {}, cities: {}",
                //         dtoRegion.getRegionName(),
                //         dtoRegion.getNroRegion(),
                //         dtoRegion.getCities() == null ? "NULL" : dtoRegion.getCities().size());

                Region region = regionRepo.findByNroRegion(dtoRegion.getNroRegion()).orElseGet(() -> regionRepo.save(
                        new Region(dtoRegion.getRegionName(), dtoRegion.getNroRegion(), dtoRegion.getCodeRoman())));

                regionCount++;

                for (DtoCity dtoCity : dtoRegion.getCities()) {
                    citiesCount++;
                    if (cityRepo.findByNameAndRegion(dtoCity.getName(), region).isEmpty()) {
                        cityRepo.save(new City(dtoCity.getName(), region));
                    }
                }
            }

            log.info("JSON loader finished. Regions processed: {},cities processed: {}", regionCount, citiesCount);
        } catch (Exception ex) {
            log.error("JSON loader failed_ {}", ex.getMessage(), ex);
        }
    }

}