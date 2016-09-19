package StorageAssignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BlockFile implements BlockOrientedFile , AutoCloseable {

    FileInputStream input;
    FileOutputStream output;
    
    private FileCtrlBlk ctrlBlk = null;
    private DataBlkWriter writer = null;
    
    public BlockFile(File file) throws IOException {
        if(!file.exists()){
            create(file);
        }
        open(file);
    }
    
    private void create(File file) throws IOException {
        file.createNewFile();
    }
    
    private void open(File file) throws IOException {
        input = new FileInputStream(file);
        output = new FileOutputStream(file);
        
        ctrlBlk = new FileCtrlBlk(input, output);
        writer = new DataBlkWriter(input, output, ctrlBlk);
    }
    
    public int getBlockSize(){
        return ctrlBlk.getBlkSize();
    }
    


    @Override
    public void append(int noOfBlks) throws IOException {
        writer.append(noOfBlks);
    }



    @Override
    public void write(int blkNo, byte[] blkBuffer) throws IOException {
        assert(blkNo < ctrlBlk.getNoOfBlks());
        assert(blkBuffer.length == ctrlBlk.getBlkSize());
        writer.write(blkNo, blkBuffer);
    }


    @Override
    public byte[] read(int blkNo) throws IOException {
        // TODO Auto-generated method stub
        byte[] data =  writer.read(blkNo);
        assert(data.length == ctrlBlk.getBlkSize());
        return data;
    }

    @Override
    public int size() {
        writer.getNoOfBlks();
        return 0;
    }



    @Override
    public void drop(int noOfBlks) {
        writer.drop(noOfBlks);
    }
    
    @Override
    public void close() throws IOException {
        try{
            if(input != null){
                input.close();
            }
        } catch(IOException e){
            
        }
        try{
            if(output != null){
                output.close();
            }
        } catch(IOException e){
            
        }
    }
}

