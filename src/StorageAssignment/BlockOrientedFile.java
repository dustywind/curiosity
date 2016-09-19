package StorageAssignment;

import java.io.IOException;

public interface BlockOrientedFile {

    int getBlockSize();
    void append(int noOfBlks) throws IOException;
    void write(int blkNo, byte[] blkBuffer) throws IOException;
    byte[] read(int blkNo) throws IOException;
    int size();
    void drop(int noOfBlks);
}
