package controller;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.BoardDAO;
import vo.BoardVO;
import vo.UserVO;

public class ModifyController implements Controller {
	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		// prevent access to url addresses
		String ref = req.getHeader("REFERER");
		if (ref == null) {
			req.getSession().setAttribute("msg", "URL에 직접입력하지마세요.");
			return "redirect::/";
		} else {

			// Run on get
			if (req.getMethod().equalsIgnoreCase("get")) {
				// Passing parameters
				int id = Integer.parseInt(req.getParameter("id"));
				BoardVO data = BoardDAO.instance.selectBoard(id);
				req.setAttribute("data", data);
			}

			// Run on post
			if (req.getMethod().equalsIgnoreCase("post")) {
				// Passing parameters
				int id = Integer.parseInt(req.getParameter("id"));
				String title = req.getParameter("title");
				String content = req.getParameter("content");
				Part filePart = req.getPart("file");
				String filename = null;

				System.out.println(filePart);
				// Check for File Existence
				if (!Paths.get(filePart.getSubmittedFileName()).getFileName().toString().equals("")) {
					filename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
					filePart.write(req.getServletContext().getRealPath("/WEB-INF/upload" + filename));
				} else {
					filename = "";
				}

				// Paramerter reslut
				BoardVO data = new BoardVO();
				data.setId(id);
				data.setTitle(title);
				data.setContent(content);
				data.setFiles(filename);

				int res = BoardDAO.instance.modify(data);

				if (res != 1) {
					req.getSession().setAttribute("msg", "글수정중 오류 발생");
					return "modify?id=" + id;
				} else {
					req.getSession().setAttribute("msg", "성공적으로 글 수정");
					return "redirect::/board/view?id=" + id;
				}
			}

			return "modify";
		}
	}

}
