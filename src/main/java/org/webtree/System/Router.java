package org.webtree.System;

import org.webtree.Language.Controller.LanguageController;
import org.webtree.Link.Model.LinkModel;
import org.webtree.Site.Controller.FrontController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * @author lucifer
 *         Date: 02.05.12
 *         Time: 8:18
 */
public class Router extends HttpServlet {
	protected FrontController front;
	protected LanguageController languageController;

	public static void redirect(String url, int status) throws Redirect {
		throw new Redirect(url, status);
	}

	public static void redirect(String url) throws Redirect {
		throw new Redirect(url);
	}

	public static void redirect(LinkModel link) throws Redirect {
		throw new Redirect(link.buildUrl());
	}

	public static void redirectPageNotFound() throws RedirectPageNotFound {
		throw new RedirectPageNotFound();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		long start = System.currentTimeMillis();
		Log.getInst().debug(request.getServletPath());
		List<String> path = Arrays.asList(request.getServletPath().split("/"));
		front = new FrontController(request, response, path);

		PrintWriter out = getWriter();

		String result;
		try {
			front.process();
			result = front.parsePage();
			out.print(result + "\n <!--Process time: " + (System.currentTimeMillis() - start)+" ms-->");
		} catch (BaseForward e) {
			Log.getInst().debug("page not found. url: {} \n exception: {}", e.getUrl(), e);
			forward(e.getUrl(), e.getStatus());
		} catch (BaseRedirect e) {
			Log.getInst().debug("Redirect. url: {} \n exception: {}", e.getUrl(), e);
			doRedirect(e.getUrl(), e.getStatus());
		} catch (Error | Exception e) {
			Log.getInst().error("System error exception: {}", e);
			throw new IOException(e);
//			forward("/500.jsp", 500);
		} finally {
			front.destruct();
		}
		Log.getInst().debug("Process time: {} ms", System.currentTimeMillis() - start);
	}

	protected void doRedirect(String url) throws IOException {
		doRedirect(url, HttpServletResponse.SC_MOVED_TEMPORARILY);
	}

	protected void doRedirect(String url, int status) throws IOException {
		HttpServletResponse response = Registry.getInst().getResponse();
		response.setStatus(status);
		response.setHeader("Location", url);
	}

	protected void forward(String dispatcher, int status) throws IOException, ServletException {
		Registry.getInst().getResponse().setStatus(status);
		Registry.getInst().getRequest().getRequestDispatcher(dispatcher).forward(Registry.getInst().getRequest(), Registry.getInst().getResponse());
	}

//	protected void Redirect(int status){
//		response.setStatus(status);
//	}

	protected PrintWriter getWriter() throws IOException {
		HttpServletRequest request = Registry.getInst().getRequest();
		HttpServletResponse response = Registry.getInst().getResponse();

		String encodings = request.getHeader("Accept-Encoding");
		String encodeFlag = request.getParameter("encoding");
		PrintWriter out;
		if (false && encodings != null) { //TODO
			// и если это поле содержит значение "gzip", а кодировка ещё не была установлена,
			if (encodings.contains("gzip") && (encodeFlag == null || !encodeFlag.equals("none"))) {
				// то то, куда будем выводит, будет за одним и сжимать текст с помощью GZIP
				out = new PrintWriter(new GZIPOutputStream(response.getOutputStream()),
				false);

				// и устанавливаем флаг для браузера, что документ будет сжат
				response.setHeader("Content-Encoding", "gzip");
			} else    // в противном случае выводить будем без сжатия
				out = response.getWriter();
		} else    // в противном случае выводить будем без сжатия
			out = response.getWriter();
		return out;
	}

	public static class BaseRedirect extends Exception {
		String url;
		int status = HttpServletResponse.SC_MOVED_TEMPORARILY;

		public String getUrl() {
			return url;
		}

		public int getStatus() {
			return status;
		}
	}

	public static class Redirect extends BaseRedirect {
		public Redirect(String url) {
			this.url = url;
		}

		public Redirect(String url, int status) {
			this.status = status;
			this.url = url;
		}
	}

	public static class BaseForward extends BaseRedirect {

	}

	public static class RedirectSystemError extends BaseForward {
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		protected String url = "/500.jsp";
		protected int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

		public RedirectSystemError() {

		}
	}

	public static class RedirectPageNotFound extends BaseForward {
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		protected String url = "/404.jsp";
		protected int status = HttpServletResponse.SC_NOT_FOUND;
	}
}