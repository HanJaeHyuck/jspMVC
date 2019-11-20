package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import model.BoardDAO;
import vo.BoardVO;
import vo.UserVO;

public class WriteController implements Controller {
	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// prevent access to url addresses
		String ref = req.getHeader("REFERER");
		if (ref == null) {
			req.getSession().setAttribute("msg", "URL에 직접입력하지마세요.");
			return "redirect::/";
		} else {

			// Run on post
			if (req.getMethod().equalsIgnoreCase("post")) {
				String savePath = req.getRealPath("/upload");
				int sizeLimit = 10 * 1024 * 1024;
				String filename = "";
				String title = "";
				String content = "";
				MultipartRequest multi = null;
				try {
					multi = new MultipartRequest(req, savePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
					// Passing parameters
					title = multi.getParameter("title");
					content = multi.getParameter("content");
					filename = multi.getFilesystemName("file");

					if (filename == null) {
						filename = "";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				UserVO user = (UserVO) req.getSession().getAttribute("user");

				BoardVO data = new BoardVO();
				data.setTitle(title);
				data.setContent(content);
				data.setWriter(user.getId());
				data.setFiles(filename);

				int res = BoardDAO.instance.write(data);

				if (res != 1) {
					req.getSession().setAttribute("msg", "글쓰기중 오류 발생");
					return "write";
				} else {
					req.getSession().setAttribute("msg", "성공적으로 글 작성");
					return "redirect::/board";
				}
			}
		}

		return "write";
	}
}
