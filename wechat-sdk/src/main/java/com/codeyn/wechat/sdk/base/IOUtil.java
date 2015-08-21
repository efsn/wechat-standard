package com.codeyn.wechat.sdk.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeyn.wechat.sdk.base.model.WxBase;

public class IOUtil {

	private static Logger logger = LoggerFactory.getLogger(IOUtil.class);

	public static String readUrl(String url, String charsetName) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream(), charsetName))) {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = br.readLine()) != null)
				sb.append(s);
			return sb.toString();
		} catch (Exception e) {
			logger.error("readUrl error:" + e.getMessage(), e);
			return null;
		}
	}

	public static String readFile(String path) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),
				WxBase.ENCODING))) {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = br.readLine()) != null)
				sb.append(s);
			return sb.toString();
		} catch (Exception e) {
			logger.error("readUrl error:" + e.getMessage(), e);
			return null;
		}
	}

	public static String readFile(String path, String charsetName) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),
				charsetName))) {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = br.readLine()) != null)
				sb.append(s);
			return sb.toString();
		} catch (Exception e) {
			logger.error("readUrl error:" + e.getMessage(), e);
			return null;
		}
	}

	public static String readFile(File f) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), WxBase.ENCODING))) {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = br.readLine()) != null)
				sb.append(s);
			return sb.toString();
		} catch (Exception e) {
			logger.error("readUrl error:" + e.getMessage(), e);
			return null;
		}
	}

	public static String readFile(File f, String charsetName) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charsetName))) {
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = br.readLine()) != null)
				sb.append(s);
			return sb.toString();
		} catch (Exception e) {
			logger.error("readUrl error:" + e.getMessage(), e);
			return null;
		}
	}

	public static boolean writeFile(String path, String content, boolean append) {
		return writeFile(new File(path), content, append);
	}

	public static boolean writeFile(File f, String content, boolean append) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, append))) {
			bw.write(content);
			return true;
		} catch (Exception e) {
			logger.error("readUrl error:" + e.getMessage(), e);
			return false;
		}
	}
}
