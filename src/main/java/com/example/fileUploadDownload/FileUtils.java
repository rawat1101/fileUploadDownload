package com.example.fileUploadDownload;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    public static URI filePathToUrl(String filePath) throws MalformedURLException {
        return new File(filePath).toURI();
    }

    public static void downloadFileNIO(URL url, String outputFileName) throws IOException {
        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(outputFileName)) {
            System.out.println(fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE));
        }
    }

    public static void downloadFileApache(URL url, String fileName) throws IOException {
        org.apache.commons.io.FileUtils.copyURLToFile(url, new File(fileName));
    }

    public static void main(String[] args) {
        URI url = null;
        try {
            url = filePathToUrl("/home/mahendra/Desktop/liveDetails.csv");
            System.out.println("URL : " + url);
            downloadFileNIO(url.toURL(), "test123");
//            downloadFileApache(url.toURL(),"test123.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String baseUrl(ServerHttpRequest request) {
        URI uri = request.getURI();
        String port = "";
        if (uri.getPort() != 80) port = String.format(":%s", uri.getPort());
        return String.format("%s://%s%s", uri.getScheme(), uri.getHost(), port);
    }

    /**
     * @param filePart example can be obtained from @RequestPart("file") FilePart
     * @param folder   example :
     *                 ./folder : parent folder,
     *                 /folder : to system root folder
     * @return true if succeed false otherwise
     */
    public static Boolean createFile(FilePart filePart, String folder, String fileName) {
        try {
            String fullPath = folder + "/" + fileName;
            Path path = Files.createFile(Paths.get(fullPath).toAbsolutePath().normalize());
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
            DataBufferUtils.write(filePart.content(), channel, 0)
                    .doOnComplete(() -> {
                        try {
                            channel.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })
                    .subscribe()
            ;
            return true;
        } catch (IOException e) {
        }
        return false;
    }
}