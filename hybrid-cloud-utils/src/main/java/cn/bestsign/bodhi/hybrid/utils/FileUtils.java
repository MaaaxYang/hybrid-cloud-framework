package org.github.bodhi.hybrid.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 17:13
 **/
public class FileUtils {

    private static StandardOpenOption openOption = StandardOpenOption.CREATE;

    public static void write(Path path,String content){
        boolean exist = exist(path);

        if (!exist){
            flushDisk(path,openOption,content);
        }else{
            flushDisk(path,StandardOpenOption.APPEND,content);
        }
    }


    public static void override(Path path,String content){
        boolean exist = exist(path);
        if (!exist){
            flushDisk(path,openOption,content);
        }else{
            flushDisk(path,StandardOpenOption.TRUNCATE_EXISTING,content);
        }
    }


    public static void save(Path path,byte[] bytes){
        try(OutputStream os = Files.newOutputStream(path,openOption)){
            os.write(bytes);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Path path,InputStream inputStream){
        try(OutputStream os = Files.newOutputStream(path,openOption)){
            os.write(IOUtils.toByteArray(inputStream));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] read (Path path){

        try(InputStream inputStream = Files.newInputStream(path,openOption)){
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean exist(Path path){
        return path.toFile().exists();
    }

    private static void flushDisk(Path path,StandardOpenOption openOption,String content){
        try(BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8, openOption)){
            bw.write(content);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {

        byte[] file = read(Paths.get("/Users/yanghaoran/Downloads/存储架构.jpg"));

        save(Paths.get("/Users/yanghaoran/Downloads/存储架构a.jpg"),file);

    }

}
