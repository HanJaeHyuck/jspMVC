package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

import model.BoardDAO;

public class DeleteController implements Controller {
	@Override
	public String service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		//prevent access to url addresses
		String ref = req.getHeader("REFERER");
		if (ref == null) {
			req.getSession().setAttribute("msg", "URL에 직접입력하지마세요.");
			return "redirect::/";
		} else {
			// Passing parameters
			int id = Integer.parseInt(req.getParameter("id"));

			int res = BoardDAO.instance.delete(id);

			// res is null or not null check
			if (res == 1) {
				req.getSession().setAttribute("msg", "글 삭제 완료");
				// Hand over to redirect
				return "redirect::/board";
			} else {
				req.getSession().setAttribute("msg", "글 삭제중 오류");
				return "board/view?id=" + id;
			}	
		}

	}
}
