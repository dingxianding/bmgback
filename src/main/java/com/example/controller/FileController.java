package com.example.controller;

import com.example.entity.FileEntity;
import com.example.repository.FileRepository;
import com.example.repository.MemberRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * https://blog.csdn.net/Coding13/article/details/54577076
 * http://www.cnblogs.com/cosyer/p/6683576.html
 */
@Controller
public class FileController {
    @Autowired
    private FileRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.fildUploadDir}")
    private String webDir;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    //文件上传相关代码
    //MultipartFile file这个变量名与前端相同
    @RequestMapping(value = "myapi/upload")
    @ResponseBody
    public Map upload(@RequestParam("file") MultipartFile file) {
        Map result = new HashMap();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
        String dateDir = df.format(new Date());// new Date()为获取当前系统时间
        String serviceName = UUID.randomUUID() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filePath = webDir + dateDir + File.separator + serviceName;
        File tempFile = new File(filePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile));
                // "d:/"+file.getOriginalFilename() 指定目录
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                result.put("msg", "上传失败," + e.getMessage());
                result.put("state", false);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                result.put("msg", "上传失败," + e.getMessage());
                result.put("state", false);
                return result;
            }
            result.put("msg", "上传成功");

            //保存文件信息至数据库
            FileEntity entity = new FileEntity();
            entity.setName(file.getOriginalFilename());
            entity.setPath(filePath);
            entity.setSize(file.getSize());
            entity.setInUser(userRepository.findByIdAndDeleteTime(1, null));
            entity = repository.save(entity);

            result.put("state", true);
            result.put("fileId", entity.getId());
            return result;
        } else {
            result.put("msg", "上传失败，文件不能为空");
            result.put("state", false);
            return result;
        }
    }

    //文件下载相关代码
    @GetMapping("/myapi/download/{id}")
    public String downloadFile(@PathVariable Integer id, HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {
        FileEntity fileEntity = repository.findByIdAndDeleteTime(id, null);
        if (fileEntity != null) {
            File file = new File(fileEntity.getPath());
            if (file.exists()) {
                //response.setContentType("application/force-download");// 设置强制下载不打开
                //response.addHeader("Content-Disposition", "attachment;" +
                //       "filename=\"" + fileEntity.getName() + "\"; filename*=utf-8''" + fileEntity.getName());// 设置文件名
                String fileName = new String(fileEntity.getName().getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    //System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    //多文件上传
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();

                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
