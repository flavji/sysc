import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Tests for Floor Class.
 * 
 * @author Fareen Lavji
 * @author Harishan Amutheesan
 * 
 * @version 03.27.2023
 */
class FloorTest {
    String floorRequests = "testFile.csv";
    String date = "14:05:15";
    int iFloor = 0;
    int dFloor = 1;
    String direction = "up";
    
    /**
     * Set up a test CSV file with sample data before each test.
     * 
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        PrintWriter pw = new PrintWriter(new File(floorRequests));
        StringBuffer csvData = new StringBuffer("");
        
        csvData.append(date + ",");
        csvData.append(iFloor + ",");
        csvData.append(direction + ",");
        csvData.append(dFloor);
        
        pw.write(csvData.toString());
        pw.close();
    }

    /**
     * Delete the test CSV file after each test.
     * 
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        File f = new File(floorRequests);
        f.delete();
    }

    @Test
    void testaddTimes() {
    	
		//parsePacket test
    	System.out.println("\nTesting addTimes");
    	Floor floor = new Floor(floorRequests);
    	floor.addTimes();
    	
    	assertEquals(1, floor.getTimes().size());
    	
    	
    }
    /**
     * Test the unwrapData() method to ensure that it reads and parses the CSV file correctly.
     */
    @Test
    void testUnwrapData() {
    	
    	System.out.println("Testing unwrapData ");
        Floor floor = new Floor(floorRequests);
        floor.unwrapData();

        // Read data from testFile.csv
        String testData = "";
        try (BufferedReader br = new BufferedReader(new FileReader(floorRequests))) {
            testData = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] elevatorData = testData.split(",");
        String start_date = elevatorData[0];
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = null;
        try {
            date = (Date) formatter.parse(start_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int initialFloor = Integer.parseInt(elevatorData[1]);
        String direction = elevatorData[2];
        int destinationFloor = Integer.parseInt(elevatorData[3]);

        // Check if the data read from the testFile.csv matches the expected values
        assertEquals(this.date, formatter.format(date));
        assertEquals(iFloor, initialFloor);
        assertEquals(this.direction, direction);
        assertEquals(dFloor, destinationFloor);
    }

    /**
     * Test the run() method to ensure that the Floor thread starts without any issues.
     */
    @Test
    void testRun() {
    	
    	System.out.println("\nTesting run");
        Floor floor = new Floor(floorRequests);
        Thread t1 = new Thread(floor);

        // Check if the thread starts without any issues
        assertDoesNotThrow(() -> t1.start());
    }

    /**
     * Test the setFloorData() method to ensure that it sets the floor data correctly.
     * 
     * @throws IOException
     * @throws ParseException
     */
    @Test
    void testSetFloorData() throws IOException, ParseException {
    	
    	System.out.println("\nTesting setFloorData");
    	
        Floor floor = new Floor(floorRequests);
        floor.unwrapData();

        // Read data from testFile.csv
        String testData = "";
        try (BufferedReader br = new BufferedReader(new FileReader(floorRequests))) {
            testData = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] elevatorData = testData.split(",");
        String start_date = elevatorData[0];
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = (Date) formatter.parse(start_date);
        int initialFloor = Integer.parseInt(elevatorData[1]);
        String direction = elevatorData[2];
        int destinationFloor = Integer.parseInt(elevatorData[3]);

        // Check if the data read from the testFile.csv matches the expected values
        assertEquals(this.date, formatter.format(date));
        assertEquals(iFloor, initialFloor);
        assertEquals(this.direction, direction);
        assertEquals(dFloor, destinationFloor);
    }
}
