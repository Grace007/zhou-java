package com.zhou.util.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

public class PdfHelper {
	public static ITextRenderer getRender() throws DocumentException, IOException {

		ITextRenderer render = new ITextRenderer();

		String path = getPath();
		// 添加字体，以支持中文
		render.getFontResolver().addFont(path + "arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		render.getFontResolver().addFont(path + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		return render;
	}

	// 获取要写入PDF的内容
	public static String getPdfContent(String ftlPath, String ftlName, Object o) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		return useTemplate(ftlPath, ftlName, o);
	}

	// 使用freemarker得到html内容
	public static String useTemplate(String ftlPath, String ftlName, Object o) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {

		String html = null;

		Template tpl = getFreemarkerConfig(ftlPath).getTemplate(ftlName);
		tpl.setEncoding("UTF-8");

		StringWriter writer = new StringWriter();
		tpl.process(o, writer);
		writer.flush();
		html = writer.toString();
		return html;
	}

	/**
	 * 获取Freemarker配置
	 * 
	 * @param templatePath
	 * @return
	 * @throws IOException
	 */
	private static Configuration getFreemarkerConfig(String templatePath) throws IOException {
		freemarker.template.Version version = new freemarker.template.Version("2.3.22");
		Configuration config = new Configuration(version);
		// new File(templatePath)
		// "classpath:"+templatePath
		// File file = ResourceUtils.getFile("classpath:"+templatePath);

		// ResourcePatternResolver resolver = new
		// PathMatchingResourcePatternResolver();
		// Resource r = resolver.getResource(templatePath);
		// InputStream stream = r.getInputStream();
		// String targetFilePath = r.getFilename();

		// System.out.println("放置位置 [" + targetFilePath + "]");

		// File ttfFile = new File("D://"+targetFilePath);
		// FileUtils.copyInputStreamToFile(stream, ttfFile);
		// URL u = PdfHelper.class.getResource(templatePath);
		// System.out.println(u.getPath());

		// File ff = ResourceUtils.getFile(u);
		// System.out.println(ff.getAbsolutePath());
		config.setDirectoryForTemplateLoading(new File(templatePath));
		config.setEncoding(Locale.CHINA, "utf-8");
		return config;
	}

	/**
	 * 获取类路径
	 * 
	 * @return
	 */
	public static String getPath() {
		return PdfHelper.class.getResource("/").getPath().substring(1);
	}
}
