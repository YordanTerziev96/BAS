//package com.brokerage_agency_system.ServiceTests;
//
//import com.brokerage_agency_system.Entity.Estate;
//import com.brokerage_agency_system.repository.EstateRepository;
//import com.brokerage_agency_system.service.EstateService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class EstateServiceTest {
//
//    @Mock
//    private EstateRepository estateRepository;
//
//    @InjectMocks
//    private EstateService estateService;
//
//    public final String STATUS_AVAILABLE = "available";
//    public final String STATUS_TAKEN = "taken";
//    public final String STATUS_FORECLOSED = "foreclosed";
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getAllEstates_ShouldReturnListOfEstates() {
//        // Arrange: Create mock data
//        Estate estate1 = new Estate();
//        estate1.setId(1L);
//        estate1.setStatus(STATUS_AVAILABLE);
//
//        Estate estate2 = new Estate();
//        estate2.setId(2L);
//        estate2.setStatus(STATUS_TAKEN);
//
//        List<Estate> mockEstates = Arrays.asList(estate1, estate2);
//
//        when(estateRepository.findAll()).thenReturn(mockEstates);
//
//        List<Estate> result = estateService.getAllEstates();
//
//        assertEquals(2, result.size());
//        assertEquals(1, result.get(0).getId());
//        assertEquals(STATUS_AVAILABLE, result.get(0).getStatus());
//        assertEquals(2, result.get(1).getId());
//        assertEquals(STATUS_TAKEN, result.get(1).getStatus());
//
//        verify(estateRepository, times(1)).findAll();
//    }
//
//    @Test
//    void getAllEstates_ShouldReturnEmptyList_WhenNoEstates() {
//        when(estateRepository.findAll()).thenReturn(List.of());
//
//        List<Estate> result = estateService.getAllEstates();
//
//        assertEquals(0, result.size());
//
//        verify(estateRepository, times(1)).findAll();
//    }
//
//    @Test
//    void saveEstate_ShouldSaveEstate() {
//        // Arrange: Create a mock estate
//        Estate estate = new Estate();
//        estate.setId(1L);
//        estate.setStatus(STATUS_AVAILABLE);
//
//        // Mock the repository to return the estate when saved
//        when(estateRepository.save(estate)).thenReturn(estate);
//
//        // Act: Call the saveEstate method
//        Estate savedEstate = estateService.saveEstate(estate);
//
//        // Assert: Verify the estate is saved correctly
//        assertEquals(1L, savedEstate.getId());
//        assertEquals(STATUS_AVAILABLE, savedEstate.getStatus());
//
//        // Verify that the save() method was called once
//        verify(estateRepository, times(1)).save(estate);
//    }
//
//}
//
