package com.java.ssm.ticket.view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 验证码  控制器 
 * http://localhost:8088/ticket-ssm/captcha/img
 * 配置文件在 resource文件夹里面spring-kaptcha.xml
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	@Resource
	private Producer captchaProducer;

	// 图形验证码
	@RequestMapping("/img")
	public ModelAndView imageCode() {
		ServletOutputStream out = null;
		try {
			response.setDateHeader("Expires", 0);

			// Set standard HTTP/1.1 no-cache headers.

			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			// Set IE extended HTTP/1.1 no-cache headers (use addHeader).

			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			// Set standard HTTP/1.0 no-cache header.

			response.setHeader("Pragma", "no-cache");
			// return a jpeg

			response.setContentType("image/jpeg");
			// create the text for the image

			String capText = captchaProducer.createText();
			// store the text in the session

			request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
			// create the image with the text
			
			BufferedImage bi = captchaProducer.createImage(capText);
			out = response.getOutputStream();
			// write the data out

			ImageIO.write(bi, "jpg", out);

			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
