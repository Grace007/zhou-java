package com.zhou.util.pdf;

import com.lowagie.text.DocumentException;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfUtils {
	public static void main(String[] args) {
		try {

			Map<Object, Object> o = new HashMap<Object, Object>();
			// 存入一个集合
			List<String> list = new ArrayList<String>();
			list.add("小明");
			list.add("张三");
			list.add("李四");
			o.put("name", "http://www.xdemo.org/");
			o.put("nameList", list);

			String path=PdfHelper.getPath();
			System.out.println("path = " + path);

			generateToFile(path, "info.ftl", path + "/", o, "D:\\xdemo.pdf");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 生成PDF到文件
	 * 
	 * @param ftlPath
	 *            模板文件路径（不含文件名）
	 * @param ftlName
	 *            模板文件吗（不含路径）
	 * @param imageDiskPath
	 *            图片的磁盘路径
	 * @param data
	 *            数据
	 * @param outputFile
	 *            目标文件（全路径名称）
	 * @throws Exception
	 */
	public static void generateToFile(String ftlPath, String ftlName, String imageDiskPath, Object data,
			String outputFile) throws Exception {
		String html = PdfHelper.getPdfContent(ftlPath, ftlName, data);
		Document doc = Jsoup.parse(html);
		System.out.println("doc = " + doc);
		OutputStream out = null;
		//InputStream inputStream = PdfUtils.class.getResourceAsStream("/config/a.pfx");
		// FileOutputStream outStream = PdfUtils.class.getResource("");
		ITextRenderer render = null;
		out = new FileOutputStream(outputFile);
		render = PdfHelper.getRender();
		render.setDocumentFromString(html);
//		if (imageDiskPath != null && !imageDiskPath.equals("")) {
//			// html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
//			render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
//		}
		render.layout();
		render.createPDF(out);
		render.finishPDF();
		render = null;
		out.close();
	}

	/**
	 * 生成PDF到输出流中（ServletOutputStream用于下载PDF）
	 * 
	 * @param ftlPath
	 *            ftl模板文件的路径（不含文件名）
	 * @param ftlName
	 *            ftl模板文件的名称（不含路径）
	 * @param imageDiskPath
	 *            如果PDF中要求图片，那么需要传入图片所在位置的磁盘路径
	 * @param data
	 *            输入到FTL中的数据
	 * @param response
	 *            HttpServletResponse
	 * @return
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 * @throws TemplateException
	 * @throws DocumentException
	 */
//	public static OutputStream generateToServletOutputStream(String ftlPath, String ftlName, String imageDiskPath,
//                                                             Object data, HttpServletResponse response, String company_name) throws TemplateNotFoundException,
//			MalformedTemplateNameException, ParseException, IOException, TemplateException, DocumentException {
//		String html = PdfHelper.getPdfContent(ftlPath, ftlName, data);
//		OutputStream out = null;
//		ITextRenderer render = null;
//		out = response.getOutputStream();
//		render = PdfHelper.getRender();
//		render.setDocumentFromString(html);
//		if (imageDiskPath != null && !imageDiskPath.equals("")) {
//			// html中如果有图片，图片的路径则使用这里设置的路径的相对路径，这个是作为根路径
//			render.getSharedContext().setBaseURL("file:/" + imageDiskPath);
//		}
//		response.setHeader("Content-disposition",
//				"attachment;filename=" + URLEncoder.encode(company_name, "UTF-8") + new Date().getTime() + ".pdf");
//		response.setContentType("application/pdf");
//
//		render.layout();
//		render.createPDF(out);
//		render.finishPDF();
//		render = null;
//		return out;
//	}
}
