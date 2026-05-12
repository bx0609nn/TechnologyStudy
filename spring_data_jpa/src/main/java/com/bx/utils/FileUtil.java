package com.bx.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lili
 * @version 1.0
 * @date 2026/4/21 17:09
 * @description 文件工具类
 */
@Slf4j
@Component
public class FileUtil extends cn.hutool.core.io.FileUtil {

    private static final long KB = 1024L;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;

    public static final String IMAGE = "图片";
    public static final String TXT = "文档";
    public static final String MUSIC = "音乐";
    public static final String VIDEO = "视频";
    public static final String OTHER = "其他";

    private static final Set<String> image = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg jfif".split(" "))));
    private static final Set<String> documents = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("txt doc pdf ppt pps xlsx xls docx".split(" "))));
    private static final Set<String> music = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("mp3 wav wma mpa ram ra aac aif m4a".split(" "))));
    private static final Set<String> video = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg".split(" "))));
    public static final String SYS_TEMP_DIR = System.getProperty("java.io.tmpdir") + File.separator;

    @Value("${file.windows-path}")
    private String windowsPath;

    @Value("${file.linux-path}")
    private String linuxPath;

    @Value("${file.mac-path}")
    private String macPath;

    public static String basePath;

    @PostConstruct
    public void init() {
        String os = System.getProperty("os.name").toLowerCase();
        String path;
        if (os.startsWith("win")) {
            path = windowsPath;
        } else if (os.startsWith("mac")) {
            path = macPath;
        } else {
            path = linuxPath;
        }
        if (path.endsWith("/") || path.endsWith("\\")) {
            path = path.substring(0, path.length() - 1);
        }
        basePath = path;
        checkDir(basePath);
        log.info("文件存储根路径初始化完成: {}", basePath);
    }

    //1.获取文件名：getName(File file)、getName(String filePath)
    //2.获取扩展名，不带"."：extName(File file)、extName(String fileName)、
    //3.获取文件名，不带.扩展名：getPrefix(File file)、getPrefix(String fileName)

    /**
     * @param type 文件扩展名（不带.，如 "jpg"）
     * @return String 文件类型分类
     * @description 根据文件扩展名获取文件类型分类
     */
    public static String getFileType(String type) {
        if (StrUtil.isBlank(type)) {
            return OTHER;
        }
        type = type.toLowerCase().trim();
        if (image.contains(type)) {
            return IMAGE;
        } else if (documents.contains(type)) {
            return TXT;
        } else if (music.contains(type)) {
            return MUSIC;
        } else if (video.contains(type)) {
            return VIDEO;
        } else {
            return OTHER;
        }
    }

    /**
     * @param multipartFile MultipartFile文件
     * @return String 文件大小
     * @description 获取 MultipartFile 文件大小，格式化为B、KB、MB、GB
     */
    public static String getSize(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return "0B";
        }
        return getSize(multipartFile.getSize());
    }

    /**
     * @param file 文件
     * @return String 文件大小
     * @description 获取文件大小，格式化为B、KB、MB、GB
     */
    public static String getSize(File file) {
        if (file == null || !file.exists()) {
            return "0B";
        }
        return getSize(file.length());
    }

    /**
     * @param size 文件大小
     * @return String 文件大小
     * @description 获取文件大小，格式化为B、KB、MB、GB
     */
    public static String getSize(long size) {
        if (size < KB) {
            return size + "B";
        } else if (size < MB) {
            return String.format("%.2fKB", size / (double) KB);
        } else if (size < GB) {
            return String.format("%.2fMB", size / (double) MB);
        } else {
            return String.format("%.2fGB", size / (double) GB);
        }
    }

    /**
     * @param multipartFile MultipartFile文件
     * @param maxMB 文件最大大小（MB）
     * @return boolean true：超过限制，false：未超过限制
     * @description 检查MultipartFile文件大小是否超过限制
     */
    public static boolean checkSize(MultipartFile multipartFile, long maxMB) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return false;
        }
        return multipartFile.getSize() > maxMB * MB;
    }

    /**
     * @param file1 文件1
     * @param file2 文件2
     * @return boolean true：内容完全相同，false：不同
     * @description 判断两个文件内容是否相同
     */
    public static boolean check(File file1, File file2) {
        // 同一个对象，直接返回 true
        if (file1 == file2) {
            return true;
        }
        // 任意一个不存在，直接返回 false
        if (file1 == null || !file1.exists() || file2 == null || !file2.exists()) {
            return false;
        }
        // 文件大小不等，内容绝对不可能相同，直接返回 false
        if (file1.length() != file2.length()) {
            return false;
        }
        // 4.计算 MD5 进行深度比对
        return getMd5(file1).equals(getMd5(file2));
    }

    /**
     * @param file 文件
     * @return String 文件的MD5值
     * @description 获取文件的MD5值
     */
    public static String getMd5(File file) {
        return SecureUtil.md5(file);
    }

    /**
     * @param dirPath 目录路径
     * @return File 目录对象
     * @description 确保目录存在，不存在则创建（含多级）
     */
    public static File checkDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * @param originalFileName 原文件名
     * @return String 唯一文件名
     * @description 使用UUID生成唯一文件名，保留原扩展名
     */
    public static String getUniqueFileName(String originalFileName) {
        // 获取文件扩展名
        String ext = extName(originalFileName);
        // 带.的文件扩展名
        String suffix = StrUtil.isBlank(ext) ? "" : "." + ext;
        return IdUtil.simpleUUID() + suffix;
    }

    /**
     * @param multipartFile 待上传文件
     * @param filePath 目标目录，如 "/data/upload/avatar"
     * @return File 上传后的文件对象
      * @description 将 MultipartFile 写入指定目录，文件名自动生成（UUID+原扩展名），已确保目录存在
     */
    public static File multipartFileUpload(MultipartFile multipartFile, String filePath) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        // 获取文件扩展名
        String ext = extName(multipartFile.getOriginalFilename());
        // 带.的文件扩展名
        String suffix = StrUtil.isBlank(ext) ? "" : "." + ext;
        // 用 UUID 生成唯一文件名，避免重复覆盖
        String fileName = IdUtil.simpleUUID() + suffix;
        File dest = null;
        try {
            // getCanonicalFile()：
            // 1.将路径解析为真实绝对路径（解析 ".."、"." 等相对路径符号）
            // 2.防止路径穿越攻击（如文件名中包含 "../../etc/passwd" 等）
            // 注意：file.transferTo() 对相对路径的行为依赖 JVM 工作目录，因此必须先通过 getCanonicalFile() 转为绝对路径再传入
            dest = new File(filePath, fileName).getCanonicalFile();
            // 目标目录不存在则递归创建
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            // 将 MultipartFile 内容写入目标文件
            multipartFile.transferTo(dest);
            return dest;
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            if (dest != null && dest.exists()) {
                dest.delete();
            }
        }
        return null;
    }

    /**
     * @param multipartFile MultipartFile
     * @return File 临时文件
     * @description MultipartFile 转临时 File，记得转完后删除临时文件
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }
        // 获取文件扩展名
        String ext = extName(multipartFile.getOriginalFilename());
        // 带.的文件扩展名
        String suffix = StrUtil.isBlank(ext) ? "" : "." + ext;
        // 用 UUID 生成唯一文件名，防止生成的临时文件重复
        File file = new File(SYS_TEMP_DIR, IdUtil.simpleUUID() + suffix);
        file.deleteOnExit();
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("MultipartFile to File失败: {}", e.getMessage(), e);
            if (file.exists()) {
                file.delete();
            }
            return null;
        }
        return file;
    }

    /**
     * @param is 输入流
     * @param fileName 文件名
     * @return File 临时文件
     * @description InputStream 转临时 File，记得转完后删除临时文件
     */
    public static File inputStreamToFile(InputStream is, String fileName) {
        // 获取文件扩展名
        String ext = extName(fileName);
        // 带.的文件扩展名
        String suffix = StrUtil.isBlank(ext) ? "" : "." + ext;
        // 用 UUID 生成唯一文件名，防止生成的临时文件重复
        File file = new File(SYS_TEMP_DIR, IdUtil.simpleUUID() + suffix);
        try (OutputStream os = new FileOutputStream(file)) {
            int len = 8192;
            byte[] buffer = new byte[len];
            int bytesRead;
            while ((bytesRead = is.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            file.deleteOnExit();
        } catch (Exception e) {
            log.error("InputStream to File失败: {}", e.getMessage(), e);
            if (file.exists()) {
                file.delete();
            }
            return null;
        } finally {
            IoUtil.close(is);
        }
        return file;
    }

    /**
     * @param response HttpServletResponse
     * @param file 文件
     * @param fileName 文件名
     * @return void
     * @description 下载文件，使用文件名作为下载文件名，支持中文文件名
     */
    public static void downloadFile(HttpServletResponse response, File file, String fileName) {
        // 设置下载进度条
        response.setContentLengthLong(file.length());
        try (InputStream inputStream = new FileInputStream(file)) {
            downloadFileWithInputStream(response, fileName, inputStream);
        } catch (IOException e) {
            log.error("下载文件失败，文件路径: {}, 文件名: {}", file.getAbsolutePath(), fileName, e);
        }
    }

    /**
     * @param response HttpServletResponse
     * @param fileName 文件名
     * @param inputStream 输入流
     * @return void
     * @description 下载文件，使用文件名作为下载文件名，支持中文文件名
     */
    public static void downloadFileWithInputStream(HttpServletResponse response, String fileName, InputStream inputStream) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            IoUtil.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     * @param response HttpServletResponse
     * @param fileMap  文件映射集合 (Key: 压缩包内的文件名, Value: 对应的物理文件)
     * @param zipName  下载时的压缩包名称 (如 "附件.zip")
     * @description 将多个文件打包成 ZIP 压缩流直接写入响应，支持中文名称，无需生成临时文件
     */
    public static void downloadZip(HttpServletResponse response, Map<String, File> fileMap, String zipName) {
        if (CollUtil.isEmpty(fileMap)) {
            return;
        }
        String encodedName;
        try {
            encodedName = URLEncoder.encode(zipName, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            encodedName = zipName;
        }
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedName);
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream(), StandardCharsets.UTF_8)) {
            for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                File file = entry.getValue();
                if (file == null || !file.exists()) {
                    log.warn("ZIP打包跳过不存在的文件: {}", entry.getKey());
                    continue;
                }
                zos.putNextEntry(new ZipEntry(entry.getKey()));
                try (InputStream is = new FileInputStream(file)) {
                    IoUtil.copy(is, zos);
                }
                zos.closeEntry();
            }
        } catch (IOException e) {
            log.error("批量下载ZIP失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 目录结构：分组名/文件名
     *
     * @param response  HttpServletResponse
     * @param folderMap key: 文件夹名(如单据编号), value: 该组下的文件列表 Map<String, File>(filename, File)
     * @param zipName 下载时的压缩包名称
     * @description 将多个文件按分组打包成 ZIP 压缩流直接写入响应，支持中文名称，无需生成临时文件
     */
    public static void downloadFolderZip(HttpServletResponse response, Map<String, Map<String, File>> folderMap, String zipName) {
        if (CollUtil.isEmpty(folderMap)) {
            return;
        }
        String encodedName;
        try {
            encodedName = URLEncoder.encode(zipName, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            encodedName = zipName;
        }
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedName);

        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream(), StandardCharsets.UTF_8)) {
            //遍历所有分组，处理每个分组的文件
            for (Map.Entry<String, Map<String, File>> folder : folderMap.entrySet()) {
                // 获取分组名
                String folderName = folder.getKey();
                Map<String, File> fileMap= folder.getValue();
                // 遍历分组下的所有文件
                for (Map.Entry<String, File> fileEntry : fileMap.entrySet()) {
                    // 获取文件
                    File file = fileEntry.getValue();
                    if (file == null || !file.exists()) {
                        log.warn("ZIP打包跳过不存在的文件: {}/{}", folderName, fileEntry.getKey());
                        continue;
                    }
                    zos.putNextEntry(new ZipEntry(folderName + "/" + fileEntry.getKey()));
                    try (InputStream is = new FileInputStream(file)) {
                        IoUtil.copy(is, zos);
                    }
                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            log.error("批量下载ZIP失败: {}", e.getMessage(), e);
        }
    }

    /**
     * @param response HttpServletResponse
     * @param file 文件
     * @param fileName 文件名
     * @return
     * @description 预览文件，使用文件名作为预览文件名，支持中文文件名
     */
    public static void previewFile(HttpServletResponse response, File file, String fileName) {
        // 设置预览进度条
        response.setContentLengthLong(file.length());
        try (InputStream inputStream = new FileInputStream(file)) {
            previewWithInputStream(response, fileName, inputStream);
        } catch (IOException e) {
            log.error("预览文件失败，文件路径: {}, 文件名: {}", file.getAbsolutePath(), fileName, e);
        }
    }

    /**
     * @param response HttpServletResponse
     * @param fileName 文件名
     * @param inputStream 输入流
     * @return void
     * @description 预览文件，使用文件名作为预览文件名，支持中文文件名
     */
    public static void previewWithInputStream(HttpServletResponse response, String fileName, InputStream inputStream) {
        // 获取文件扩展名
        String ext = extName(fileName);
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            // 根据扩展名获取 MIME 类型
            response.setContentType(getContentType(ext));
            // 设置 Content-Disposition 头，使用 inline 表示在浏览器中预览
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            IoUtil.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error("预览文件失败，文件名: {}", fileName, e);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     * @param response HttpServletResponse
     * @param fileName 文件名
     * @return ServletOutputStream 输出流
     * @throws IOException
     * @description 获取输出流
     */
    public static ServletOutputStream getOutputStream(HttpServletResponse response, String fileName) throws IOException {
        fileName = URLEncoder.encode(fileName, "UTF-8").replace("+", "%20");
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setBufferSize(4096);
        return response.getOutputStream();
    }

    /**
     * @param file 文件
     * @description 删除临时文件，只允许删除临时目录下的文件
     */
    public static void deleteTempFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        try {
            // 绝对路径
            String absolutePath = file.getCanonicalPath();
            String tempDir = new File(SYS_TEMP_DIR).getCanonicalPath();
            // 确保目录以分隔符结尾，防止 /tmp 匹配到 /tmp-evil
            if (!tempDir.endsWith(File.separator)) {
                tempDir += File.separator;
            }
            // 当文件路径确实在系统临时目录下时才执行删除
            if (absolutePath.equals(tempDir) || absolutePath.startsWith(tempDir)) {
                del(file);
                log.info("临时文件成功删除: {}", absolutePath);
            } else {
                log.warn("拒绝删除非临时目录文件，路径: {}", absolutePath);
            }
        } catch (IOException e) {
            log.error("删除临时文件异常: {}", e.getMessage());
        }
    }

    /**
     * @param ext 文件扩展名
     * @return String
     * @description 根据扩展名获取 MIME 类型
     */
    private static String getContentType(String ext) {
        switch (ext) {
            case "pdf": return "application/pdf";
            case "png": return "image/png";
            case "jpg":
            case "jpeg": return "image/jpeg";
            case "gif": return "image/gif";
            case "webp": return "image/webp";
            case "txt": return "text/plain; charset=UTF-8";
            case "mp4": return "video/mp4";
            case "mp3": return "audio/mpeg";
            default: return "application/octet-stream"; // 兜底，浏览器会触发下载
        }
    }
}
