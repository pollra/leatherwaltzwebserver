package com.pollra.http.files.controller;

import com.pollra.http.files.domain.UploadVO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

@RestController
@RequestMapping("/files")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 20mb
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 100,
        location = "C:/programing/pollrawebserver/src/main/webapp/static/files/"
)
public class FileServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(FileServlet.class);

    @PostMapping("upload")
    protected ResponseEntity<UploadVO> upload(HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 경로
        String fileUploadPath = "C:/programing/pollrawebserver/src/main/webapp/static/files/";
        System.out.println("upload: "+request.getParameter("upload"));
        final String path = fileUploadPath;

        // 파일
        final Part filePart = request.getPart("upload");
        for(Part part : request.getParts()){
            log.info("part.getName: "+ part.getName());
        }
        // file Name
        final String fileName = getFileName(filePart);
//        final PrintWriter writer = response.getWriter();

        try(OutputStream out = new FileOutputStream(new File(path + File.separator + fileName));
            InputStream filecontent = filePart.getInputStream()){
            int read = 0;
            final byte[] bytes = new byte[1024];
            while((read = filecontent.read(bytes))!= -1){
                out.write(bytes, 0, read);
            }
            UploadVO uploadVO = UploadVO.builder()
                    .uploaded(1)
                    .fileName(fileName)
                    .url("/files/"+fileName)
                    .build();
            return new ResponseEntity(uploadVO, HttpStatus.OK);
        }catch(FileNotFoundException e){
            log.info(e.getMessage());
            return null;
        }
    }

    @GetMapping("{fileName}.{extension}")
    public void getFilePage(@PathVariable String fileName, @PathVariable String extension,
                            HttpServletResponse response, HttpServletRequest request)
            throws IOException{
//        log.info("fileName: "+ fileName+"."+extension);
        setImg(fileName, extension, response, request, "/static/files/");
//        return new File("C:/programing/Pollra-webServer01/src/main/webapp/static/files/"+fileName+"."+extension);
    }

    @GetMapping("service/{filePath}/{fileName}.{extension}")
    public void getServiceFilePage(@PathVariable String fileName, @PathVariable String extension, @PathVariable String filePath,
                            HttpServletResponse response, HttpServletRequest request)
            throws IOException{
//        log.info("fileName: "+ filePath+"/"+fileName+"."+extension);
        setImg(fileName, extension, response, request, "/static/files/servicefiles/"+filePath+"/");
//        return new File("C:/programing/Pollra-webServer01/src/main/webapp/static/files/"+fileName+"."+extension);
    }

    private void setImg(String fileName, String extension,
                        HttpServletResponse response, HttpServletRequest request, String filePath)
            throws IOException {
        InputStream io = request.getServletContext()
                .getResourceAsStream(filePath+fileName+"."+extension);
        response.setContentType(MediaType.ALL_VALUE);
        IOUtils.copy(io
                , response.getOutputStream());
    }


    private String getFileName(Part part) {
//        log.info(part.getHeaderNames().toString());
        for(String name : part.getHeaderNames()){
            log.info("part name: "+name);
        }
        final String partHeader = part.getHeader("content-disposition");
        log.info("Part Header = {0}" + partHeader);
        for(String content : part.getHeader("content-disposition").split(";")){
            if(content.trim().startsWith("filename")){
                String fileName = content.substring(content.indexOf('=')+1).trim().replace("\"","");
                // 파일 이름
                //content.substring(content.indexOf('=')+1).trim().replace("\"","")
                return fileName;
//                return new ResponseEntity(uploadVO, HttpStatus.OK);
            }
        }
        return null;
    }
}
