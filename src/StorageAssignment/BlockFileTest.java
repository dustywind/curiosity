package StorageAssignment;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BlockFileTest {
    
    private File inputFile;
    
    private static byte[] prepareDataBlk(int blkSize){

        byte[] blk = new byte[blkSize];
        
        for(int i = 0; i < blkSize; i++){
            blk[i] = (byte)(Math.random() * Math.pow(2,  8));
        }
        
        return blk;
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        inputFile = File.createTempFile("curiosity_storageassignment_blockfile", "db");
    }

    @After
    public void tearDown() throws Exception {
        inputFile.delete();
        inputFile = null;
    }

    
    @Test
    public void canCreateEmptyBlockFile() throws IOException {
        try(BlockFile blockFile = new BlockFile(inputFile)){
            Assert.assertEquals(0, blockFile.size());
            Assert.assertTrue(inputFile.exists());
        }
    }
    
    
    @Test
    public void canWriteSingleBlock() throws IOException {
        try(BlockFile blockFile = new BlockFile(inputFile)){
            byte[] blk = new byte[blockFile.getBlockSize()];

            blockFile.append(1);
            blockFile.write(0,  blk);
        }
    }
    
    @Test
    public void writeReadBlockAtPositionGreaterZero() throws IOException {
        
        int blkNr = (int) (Math.random() * 100) + 1;
        
        try(BlockFile blockFile = new BlockFile(inputFile)){
            byte[] writeBlk = new byte[blockFile.getBlockSize()];

            blockFile.append(blkNr +1);
            blockFile.write(blkNr,  writeBlk);
            
            byte[] readBlk = blockFile.read(blkNr);
            
            Assert.assertArrayEquals(writeBlk, readBlk);
            
        }
    }

    @Test
    public void canWriteReadSingleBlock() throws IOException {
        final int blockNr = 0;
        
        try(BlockFile blockFile = new BlockFile(inputFile)){

            byte[] writeBlk = prepareDataBlk(blockFile.getBlockSize());
            
            // write
            blockFile.append(1);
            blockFile.write(blockNr,  writeBlk);
            
            // read written bytes
            byte[] readBlk = blockFile.read(blockNr);
            
            Assert.assertArrayEquals(writeBlk, readBlk);
            
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }    
}












