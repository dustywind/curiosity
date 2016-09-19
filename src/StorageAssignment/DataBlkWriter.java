package StorageAssignment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class DataBlkWriter {
    
    private FileInputStream input;
    private FileOutputStream output;
    
    FileCtrlBlk ctrlBlk;

    public DataBlkWriter(FileInputStream input, FileOutputStream output, FileCtrlBlk ctrlBlk){
        this.input = input;
        this.output = output;
        
        this.ctrlBlk = ctrlBlk;
    }
    
     public void append(int noOfBlks) throws IOException {
         
         int totalByteCount = ctrlBlk.getTotalByteCount();
         int newByteCount =  totalByteCount + noOfBlks * ctrlBlk.getBlkSize();
         
         //output.getChannel().position(newByteCount);
         ctrlBlk.setNoOfBlks(noOfBlks + ctrlBlk.getNoOfBlks());
         
     }
     
     public void write(int blkNo, byte[] blkBuffer) throws IOException {
         
         int startPos = ctrlBlk.getByteOffsetOfBlk(blkNo);
         output.getChannel().position(startPos);
         
         output.write(blkBuffer, 0, ctrlBlk.getBlkSize());
     }
     
     public byte[] read(int blkNo) throws IOException{
         int startPos = ctrlBlk.getByteOffsetOfBlk(blkNo);
         input.getChannel().position(startPos);

         byte[] data = new byte[ctrlBlk.getBlkSize()];
         input.read(data, 0, ctrlBlk.getBlkSize());
         return data;
     }
     
     public int getNoOfBlks(){
         return ctrlBlk.getNoOfBlks();
     }
     
     public void drop(int noOfBlks){
         int currentBlkCount = ctrlBlk.getNoOfBlks();
         int newBlkCount = currentBlkCount - noOfBlks;
         assert(newBlkCount >= 0);
         ctrlBlk.setNoOfBlks(newBlkCount);
     }
}
