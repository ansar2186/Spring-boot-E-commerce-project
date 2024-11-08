package com.shopme.admin.util;

import com.shopme.commom.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AbstractExporter {

    public void setResponseHeader(HttpServletResponse response, String contentType,
                                  String extension) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = dateFormat.format(new Date());
        String fileName = "user_" + timeStamp + extension;
        response.setContentType(contentType);

        String headerKey = "Content.Disposition";
        String headerValue = "attachment; fileName=" + fileName;
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);
    }
}
