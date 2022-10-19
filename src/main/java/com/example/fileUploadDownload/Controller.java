package com.example.fileUploadDownload;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
public class Controller {

    private CsvWriterService csvWriterService;

    public Controller(CsvWriterService csvWriterService) {
        this.csvWriterService = csvWriterService;
    }

    @GetMapping("/download/{fileName}")
    public Mono<Void> downloadFile(@PathVariable String fileName,
                                   ServerHttpResponse response) throws IOException {
        ZeroCopyHttpOutputMessage zeroCopyResponse =
                (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + fileName + "");
        response.getHeaders().setContentType(MediaType.
                APPLICATION_OCTET_STREAM);
        File file = new File(basePath.toAbsolutePath().toString() + "/" + fileName);
        return zeroCopyResponse.writeWith(file, 0,
                file.length());
    }

    private final Path basePath = Paths.
            get("/home/mahendra/workspace/IntelliJ");
    private String uploadDir = "/home/mahendra/workspace/IntelliJ";

    //single file upload
    @PostMapping("single/upload")
    public Mono<Void> uploadFile(@RequestPart("file")
                                 Mono<FilePart> filePartMono) {
        return filePartMono
                .doOnNext(fp -> System.out.println
                        ("Received File : " + fp.filename()))
                .flatMap(fp -> fp.
                        transferTo(basePath.resolve(fp.filename())))
                .then();
    }

    //multiple file upload
    @PostMapping("multi/upload")
    public Mono<Response> uploadMultipleFiles(@RequestPart("files") FilePart fp) {
                fp.transferTo(basePath.resolve(fp.filename()));
                        /*.map(unused -> {
                            Response res = new Response();
                            res.setStstus(200);
                            res.setMsg("success");
                            return res;
                        });*/
        Response res = new Response();
        res.setStstus(200);
        res.setMsg("success");
        return Mono.just(res);
    }
/*
    @PostMapping("multi/upload")
    public Mono<Void> uploadMultipleFiles(@RequestPart("files")Flux<FilePart> partFlux) {
        return partFlux
                .doOnNext(fp ->
                        System.out.println(fp.filename()))
                .flatMap(fp ->
                        fp.transferTo(basePath.resolve(fp.filename())))
                .then();
    }
*/

    //============================================================================================================
    @PostMapping("upload")
    public Mono<String> upload2(@RequestPart("file") FilePart filePart, ServerHttpRequest httpRequest) {
        String fileName = filePart.filename();
        return Mono.<Boolean>fromCallable(() -> {
                    System.out.println("filePart name : " +filePart.filename());
                    return FileUtils.createFile(filePart, uploadDir, fileName);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .<String>flatMap(createFileState -> {
                    Object resp = false;
                    if (createFileState) {
                        String baseUrl = FileUtils.baseUrl(httpRequest);
//                        resp = baseUrl +"/file/download/"+ fileName;
                        resp = baseUrl + uploadDir + "/" + fileName;
                    }
                    return Mono.just(resp.toString());
                });
    }

    @GetMapping(value = "/download2/{fileName}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public Mono<ResponseEntity<Resource>> downloadCsv(@PathVariable("fileName") String fileName)
            throws Exception {
        System.out.println("");
        return Mono.<Resource>fromCallable(() -> {
                    String fileLocation = uploadDir + "/" + fileName;
                    String path = Paths.get(fileLocation).toAbsolutePath().normalize().toString();
                    return new FileSystemResource(path);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(resource -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentDispositionFormData(fileName, fileName);
                    return Mono.just(ResponseEntity
                            .ok().cacheControl(CacheControl.noCache())
                            .headers(headers)
                            .body(resource));
                });
    }

    //==================================================================================
    @GetMapping(value = "/download")
//    @ResponseBody
//    public ResponseEntity<Mono<Resource>> downloadCsv() {
    public Mono<ResponseEntity<Resource>> downloadCsv() {
       String fileName = "csvFile";
       /*  return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(csvWriterService.generateCsv()
                        .flatMap(x -> {
                            Resource resource = new InputStreamResource(x);
                            return Mono.just(resource);
                        }));*/

                    return csvWriterService.generateCsv()
                            .flatMap(x -> {
                                return Mono.just(ResponseEntity
                                                .ok().cacheControl(CacheControl.noCache())
                                                .header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=" + fileName)
                                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                                                .body(new InputStreamResource(x))) ;
                                    }
                    );
    }
}
