package org.lynn.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Class Name : org.lynn.nio
 * Description :
 *
 * @author : cailinfeng
 * Date : 2018-12-18 16:51
 */
public class NioDemo {

    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("./data.txt","rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = fileChannel.read(byteBuffer);
        while (read != -1){
            System.out.println("Read bytes : " + read);
            //将buffer的数组指针下标移到初始位置0
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()){
                System.out.println(((char)byteBuffer.get()));
            }
            //清空缓冲区
            byteBuffer.clear();
            read = fileChannel.read(byteBuffer);
        }
        fileChannel.close();
        file.close();

    }

}
