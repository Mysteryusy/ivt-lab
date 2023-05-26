package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class GT4500Test {
  TorpedoStore primaryTorpedoStore;
  TorpedoStore secondaryTorpedoStore;  

  private GT4500 ship;

  @BeforeEach
  public void init(){
    primaryTorpedoStore = mock(TorpedoStore.class);
    secondaryTorpedoStore = mock(TorpedoStore.class); 
    this.ship = new GT4500(primaryTorpedoStore, secondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_Alternating(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1);
    
    // Act
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1);
    
    // Act
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(2)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Then_Single(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1); 

    // Act
    result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(2)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 

    // Act
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(3)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_Fire_NonEmpty(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(false); 
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1);
    
    // Act
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(2)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Primary_Only(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1); 
  }

  @Test
  public void fireTorpedo_All_Empty(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(false); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(false);
    when(primaryTorpedoStore.isEmpty()).thenReturn(true); 
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primaryTorpedoStore, times(0)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1); 
  }

  @Test
  public void fireTorpedo_All_Then_Secondary_Single(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(false);
    when(primaryTorpedoStore.isEmpty()).thenReturn(false); 
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1); 

    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    when(primaryTorpedoStore.isEmpty()).thenReturn(false); 
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false); 

    // Act
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_Single_Secondary_Success(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(false); 
    when(primaryTorpedoStore.isEmpty()).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(0)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
  }

  @Test
  public void fireTorpedo_Fire_Primary_Not_Secondary_When_Empty(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1); 

    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true); 
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(primaryTorpedoStore.fire(1)).thenReturn(false); 
    
    // Act
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(0)).fire(1); 
  }

  @Test
  public void fireTorpedo_Fire_Secondary_Not_Primary_When_Empty(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true); 
    when(secondaryTorpedoStore.fire(1)).thenReturn(true); 
    when(primaryTorpedoStore.isEmpty()).thenReturn(true); 

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore, times(0)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 

    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true); 
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(primaryTorpedoStore.fire(1)).thenReturn(false); 
    
    // Act
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryTorpedoStore, times(0)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
  }

  @Test
  public void fireLaser_Not_Working(){
    boolean result = ship.fireLaser(FiringMode.ALL);
    
    assertEquals(false, result);
  }  
}
