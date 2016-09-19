package StorageAssignment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class FileCtrlBlk {
    
    public static final int CTRL_BLK_SIZE = 8;
    public static final int DEFAULT_BLK_SIZE = 4096;
    
    private FileInputStream input;
    private FileOutputStream output;
    
    private int dataBlkSize = 0;
    private int noOfBlks;
    
    public FileCtrlBlk(FileInputStream input, FileOutputStream output) throws IOException {
        this.input = input;
        this.output = output;
        
        if(!ctrlBlkExists()){
            initFile();
        }
        readState();
    }
    
    private void initFile() throws IOException{
        saveState(DEFAULT_BLK_SIZE, 0);
    }
    
    public int getBlkSize(){
        return dataBlkSize;
    }
    
    public int getNoOfBlks(){
        return noOfBlks;
    }
    
    public void setNoOfBlks(int noOfBlks){
        this.noOfBlks = noOfBlks;
    }
    
    private boolean ctrlBlkExists() throws IOException {
        byte[] data = new byte[CTRL_BLK_SIZE];
        
        int nReadBytes = input.read(data, 0, CTRL_BLK_SIZE);
        if(nReadBytes < CTRL_BLK_SIZE){
            return false;
        }
        return true;
    }
    
    public int getTotalByteCount(){
        return noOfBlks * dataBlkSize + CTRL_BLK_SIZE;
    }
    
    public int getByteOffsetOfBlk(int blkNo){
        return blkNo * dataBlkSize + CTRL_BLK_SIZE;
    }
    
    private void saveState(int blkSize, int noOfBlks) throws IOException{
        output.getChannel().position(0);
        DataOutputStream doutput = new DataOutputStream(output);
        doutput.writeInt(blkSize);
        doutput.writeInt(noOfBlks);
        
        doutput.flush();
    }
    
    private void readState() throws IOException {
        input.getChannel().position(0);
        
        DataInputStream dinput = new DataInputStream(input);
        dataBlkSize = dinput.readInt();
        noOfBlks = dinput.readInt();
    }
}
